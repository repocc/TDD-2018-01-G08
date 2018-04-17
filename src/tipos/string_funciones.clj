(ns tipos.string-funciones
  (require  [interprete.definiciones :refer :all])
  (require  [interprete.procesamiento :refer :all])
  (require  [interprete.funciones-globales :refer :all])
  (require  [clojure.string :as str])
  (require  [estado :as est]))

; Definicion de funcion concat
(defmethod funcion? 'concat [funcion] true)

(defmethod ejecutarFuncion 'concat [funcionConArgumentos dato estado]
  (let [
          argumentos (obtenerArgumentosEjecutables funcionConArgumentos dato estado)
          resultado  (apply str argumentos)
        ]
    resultado
  )
)

(defmethod precondicionesValidas? 'concat [funcion argumentos dato estado]
  (validarTipos funcion argumentos dato estado)
)

; Definicion de funcion includes?
(defmethod funcion? 'includes? [funcion] true)

(defmethod ejecutarFuncion 'includes? [funcionConArgumentos dato estado]
  (let [
          argumentos (obtenerArgumentosEjecutables funcionConArgumentos dato estado)
          resultado  (apply str/includes? argumentos)
        ]
    resultado
  )
)

(defmethod precondicionesValidas? 'includes? [funcion argumentos dato estado]
  (validarTipos funcion argumentos dato estado)
)

; Definicion de funcion starts-with?
(defmethod funcion? 'starts-with? [funcion] true)

(defmethod ejecutarFuncion 'starts-with? [funcionConArgumentos dato estado]
  (let [
          argumentos (obtenerArgumentosEjecutables funcionConArgumentos dato estado)
          resultado  (apply str/starts-with? argumentos)
        ]
    resultado
  )
)

(defmethod precondicionesValidas? 'starts-with? [funcion argumentos dato estado]
  (validarTipos funcion argumentos dato estado)
)

; Definicion de funcion ends-with?
(defmethod funcion? 'ends-with? [funcion] true)

(defmethod ejecutarFuncion 'ends-with? [funcionConArgumentos dato estado]
  (let [
          argumentos (obtenerArgumentosEjecutables funcionConArgumentos dato estado)
          resultado  (apply str/ends-with? argumentos)
        ]
    resultado
  )
)

(defmethod precondicionesValidas? 'ends-with? [funcion argumentos dato estado]
  (validarTipos funcion argumentos dato estado)
)

; Definicion de funcion current
(defmethod funcion? 'current [funcion] true)

(defn current [argumento dato estado]
  (let [
          campo       (first argumento)
          valor_campo (get dato campo)
        ]
  valor_campo
  )
)

(defmethod ejecutarFuncion 'current [funcionConArgumentos dato estado]
  (let [
          argumentos (obtenerArgumentosEjecutables funcionConArgumentos dato estado)
          resultado  (current argumentos dato estado)
        ]
    resultado
  )
)

(defmethod precondicionesValidas? 'current [funcion argumentos dato estado]
 (let [
         existeCampo  (contains? dato (first argumentos))
         cumpleTipos  (validarTipos funcion argumentos dato estado)
       ]
     (and existeCampo cumpleTipos)
 )
)

; Definicion de funcion counter-value
(defmethod funcion? 'counter-value [funcion] true)

(defmethod ejecutarFuncion 'counter-value [funcionConArgumentos dato estado]
  (let [
        argumentos (obtenerArgumentos funcionConArgumentos)
        resultado  (est/consultarContador estado (first argumentos) (second argumentos))
       ]
    resultado
 )
)

(defmethod precondicionesValidas? 'counter-value [funcion argumentos dato estado] true)

; Definicion de funcion past
(defmethod funcion? 'past [funcion] true)

(defmethod ejecutarFuncion 'past [funcionConArgumentos dato estadoConUnDatoPasado]
  (let [
          argumentos   (obtenerArgumentosEjecutables funcionConArgumentos dato estadoConUnDatoPasado)
          unDatoPasado (est/obtenerElDatoPasado estadoConUnDatoPasado)
          resultado    (get unDatoPasado (first argumentos))
        ]
    resultado
  )
)

(defmethod precondicionesValidas? 'past [funcion argumentos dato estadoConUnDatoPasado]
  (let [
    validarTipos           (validarTipos funcion argumentos dato estadoConUnDatoPasado)
    unDatoPasado            (est/obtenerElDatoPasado estadoConUnDatoPasado)
    existeCampoEnDatoPasado (est/datoPasadoContieneCampo (first argumentos) unDatoPasado)]
    (and
      (= validarTipos true)
      existeCampoEnDatoPasado))
)
