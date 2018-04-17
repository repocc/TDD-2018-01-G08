(ns estado
  (:require
    [procesamiento :refer :all]
    [definiciones :refer :all] :reload-all))

(defrecord Estado [reglas datosPasados]) ;Estado será un record con un campo "reglas" (que será un mapa donde se almacenan los contadores y/o señales), y un campo "datosPasados" (que será donde se almacenen los datos ya procesados).

(defn agregarMapaDeReglasEspecificas
  "Agrega al estado el mapa que tendrá todas las reglas de un determinado tipo (contadores, señales, etc.). Devuelve un nuevo esta con dicho mapa agregado (la clave del mapa es la sentencia de DSL usada para definir la señal)."
  [estado clave]
  (assoc-in estado [':reglas clave] {}))

; Los parámetros de esta función son: el estado, el contadorNombre (que es un string), y el contadorParametros (que es un vector de parámetros, los parámetros pueden ser listas o tipos.tipos básicos, si un parámetro del vector es una lista, es porque es una función ejecutable que devuelve uno de los tipos.tipos básicos, si es un tipo básico se lo usa tal cual (string, booleano, o número).
(defn consultarContador
  "TODO(Iván): Agregar descripción."
  [estado contadorNombre contadorParametros]
  (if (every? true? (map (fn [parametro] (expresionValida? parametro {} estado)) contadorParametros))
    (let [
      acumulador
        (get
          (get-in estado [:reglas 'define-counter contadorNombre :acumuladores]) ;Agarro el mapa de acumuladores que tiene por clave.
          (map (fn [parametro] (ejecutarFuncion parametro {} estado)) contadorParametros) ;Me genero una lista con los valores de la evaluación de los parámetros del contador. Listas y vectores son intercambiables al usarlos como clave de un mapa.
        )] ; Obtengo el valor del acumulador del contador para la combinación específica de parámetros.
      (if (nil? acumulador)
        0 ;Si el acumulador no existía (para esa combinación de parámetros no se contó ningun dato aún.
        acumulador)) ;Si el acumulador si existía (tip: el valor va a ser >0.
    ERROR)) ;TODO(Iván): esto es temporal definir algo como ERROR.

(defmulti procesarUnaRegla
  "Agrega una regla conocida al estado a partir de la lista que la expresa en DSL, o deja el estado tal y como está si está vacía la lista que expresa en DSL la regla. Devuelve un nuevo estado con la regla agregada."
  (fn [estado identificadorDeRegla] (first identificadorDeRegla)))

(defn obtenerElDatoPasado
  [estadoConUnDatoPasado]
  (first (:datosPasados estadoConUnDatoPasado))
)

(defn datoPasadoContieneCampo
  [campo unDatoPasado]
  (contains? unDatoPasado campo))
