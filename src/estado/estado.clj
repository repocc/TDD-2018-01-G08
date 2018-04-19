;Funciones requeridas de este archivo:
;;(defn consultarContador [estado contadorNombre contadorParametros])

(ns estado.estado
  (:require
    [interprete.procesamiento :as proc] ;expresionValida?
    [interprete.definiciones :as def] ;ejecutarFuncion
    :reload-all))

(defrecord Estado [reglas datosPasados]) ;Estado será un registro con un campo "reglas" (que será un mapa donde se almacenan los contadores y/o señales), y un campo "datosPasados" (que será una lista donde se almacenen los datos ya procesados).

(defn agregarMapaDeReglasEspecificas
  "Agrega al estado el mapa que tendrá todas las reglas de un determinado tipo (contadores, señales, etc.). Devuelve un nuevo esta con dicho mapa agregado (la clave del mapa es la sentencia de DSL usada para definir la señal)."
  [estado clave]
  (assoc-in estado [':reglas clave] {}))

(defmulti cargarUnaRegla
  "Agrega una regla conocida al estado a partir de la lista que la expresa en DSL, o deja el estado tal y como está si está vacía la lista que expresa en DSL la regla. Devuelve un nuevo estado con la regla agregada."
  (fn [estado identificadorDeRegla] (first identificadorDeRegla)))

;Multimétodo que matchea para reglas desconocidas. Deja el estado tal cual estaba, no introduce cambios. Devuelve el mismo estado que recibió y consume la regla sin modificar nada.
(defmethod cargarUnaRegla
  :default [estado unaSenyalEnDSL] estado)

; Los parámetros de esta función son: el estado, el contadorNombre (que es un string), y el contadorParametros (que es un vector de parámetros, los parámetros pueden ser listas o tipos básicos, si un parámetro del vector es una lista, es porque es una función ejecutable que devuelve uno de los tipos básicos, si es un tipo básico se lo usa tal cual (string, booleano, o número).
(defn consultarContador
  "TODO(Iván): Agregar descripción."
  [estado contadorNombre contadorParametros]
  (if (every? true? (map (fn [parametro] (proc/expresionValida? parametro {} estado)) contadorParametros)) ;TODO(Iván): puedo contemplar past en "expresionValida?" pero como estoy usando "map" no contemplaría varios past al mismo tiempo, sino cada uno por separado.
    (let [
      acumulador
        (get
          (get-in estado [:reglas 'define-counter contadorNombre :acumuladores]) ;Agarro el mapa de acumuladores que tiene por clave.
          (map (fn [parametro] (def/ejecutarFuncion parametro {} estado)) contadorParametros) ;Me genero una lista con los valores de la evaluación de los parámetros del contador. Listas y vectores son intercambiables al usarlos como clave de un mapa.
        )] ; Obtengo el valor del acumulador del contador para la combinación específica de parámetros.
      (if (nil? acumulador)
        0 ;Si el acumulador no existía (para esa combinación de parámetros no se contó ningun dato aún.
        acumulador)) ;Si el acumulador si existía (tip: el valor va a ser >0).
    def/ERROR)) ;TODO(Iván): esto es temporal definir algo como ERROR.

(defn obtenerElDatoPasado
  [estadoConUnDatoPasado]
  (first (:datosPasados estadoConUnDatoPasado))
)

(defn datoPasadoContieneCampo
  [campo unDatoPasado]
  (contains? unDatoPasado campo))

(defn obtenerDatosPasados [estado] (:datosPasados estado))
