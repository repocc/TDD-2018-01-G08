;Funciones requeridas de este archivo:
;;(crearEstadoVacio [])
;;(agregarMapaDeReglasEspecificas [estado clave])
;;(cargarUnaRegla [])
;;(consultarContador [estado contadorNombre contadorParametros])
;;(obtenerElDatoPasado [estadoConUnDatoPasado])
;;(obtenerDatosPasados [estado])

(ns com.ar.fiuba.tdd.clojure.estado.estado
  (:require
    [com.ar.fiuba.tdd.clojure.interprete.procesamiento :as proc] ;expresionValida?
    [com.ar.fiuba.tdd.clojure.interprete.definiciones :as def] ;ejecutarFuncion
    :reload-all))

(defrecord Estado [reglas datosPasados]) ;Estado será un registro con un campo "reglas" (que será un mapa donde se almacenan los contadores y/o señales), y un campo "datosPasados" (que será una lista donde se almacenen los datos ya procesados).

(defn crearEstadoVacio
  "Para que no se manipule el registro \"Estado\" directamente desde otros archivos, se provee la funcionalidad mediante funciones."
  []
  (Estado. {} '()))

;TODO(Iván): agregar funciones para chequear si existe el mapa de reglas específicas, si ya existe el contador específico... evitar que se acceda a los campos del "Estado" desde otros archivos.

(defn agregarMapaDeReglasEspecificas
  "Agrega al estado el mapa que tendrá todas las reglas de un determinado tipo (contadores, señales, etc.). Devuelve un nuevo estado con dicho mapa agregado."
  [estado clave]
  (assoc-in estado [':reglas clave] {}))

(defmulti cargarUnaRegla
  "Agrega una regla conocida al estado a partir de la lista que la expresa en DSL, o deja el estado tal y como está si la regla no es conocida. Devuelve un nuevo estado con la regla agregada."
  (fn [estado unaReglaEnDSL] (first unaReglaEnDSL)))

;Multimétodo que matchea para reglas desconocidas. Deja el estado tal cual estaba, no introduce cambios. Devuelve el mismo estado que recibió y consume la regla sin modificar nada.
(defmethod cargarUnaRegla
  :default [estado unaReglaEnDSL] estado)

; Los parámetros de esta función son: el estado, el contadorNombre (que es un string), y el contadorParametros (que es un vector de parámetros, los parámetros pueden ser listas o tipos básicos, si un parámetro del vector es una lista, es porque es una función ejecutable que devuelve uno de los tipos básicos, si es un tipo básico se lo usa tal cual (string, booleano, o número).
(defn consultarContador
  "Devuelve el valor del acumulador correspondiente a \"contadorNombre\" para los parámetros \"contadorParametros\"en el \"estado\"."
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
  "Devuelve el único dato pasado que hay en un estado con un único dato pasado."
  [estadoConUnDatoPasado]
  (first (:datosPasados estadoConUnDatoPasado))
)

(defn datoPasadoContieneCampo
  "Devuelve \"true\" si el datoPasado contiene el \"campo\", devueelve \"false\" en caso contrario."
  [campo unDatoPasado]
  (contains? unDatoPasado campo))

(defn obtenerDatosPasados
  "Devuelve la lista de datosPasados del \"estado\"."
  [estado] (:datosPasados estado))
