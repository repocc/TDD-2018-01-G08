(ns tipos.string-funciones
  (require  [definiciones :refer :all])
  (require  [procesamiento :refer :all])
  (require  [funciones-globales :refer :all])
  (require  [clojure.string :as str])
  (require  [estado :as est]))

; Definicion de funcion concat
(defmethod funcion? 'concat [funcion] true)

(defmethod ejecutar-funcion 'concat [funcion-con-argumentos dato estado]
  (let [
          argumentos (obtener-argumentos-ejecutables funcion-con-argumentos dato estado)
          resultado  (apply str argumentos)
        ]
    resultado
  )
)

(defmethod precondiciones-validas? 'concat [funcion argumentos dato estado]
  (validar-tipos funcion argumentos dato estado)
)

; Definicion de funcion includes?
(defmethod funcion? 'includes? [funcion] true)

(defmethod ejecutar-funcion 'includes? [funcion-con-argumentos dato estado]
  (let [
          argumentos (obtener-argumentos-ejecutables funcion-con-argumentos dato estado)
          resultado  (apply str/includes? argumentos)
        ]
    resultado
  )
)

(defmethod precondiciones-validas? 'includes? [funcion argumentos dato estado]
  (validar-tipos funcion argumentos dato estado)
)

; Definicion de funcion starts-with?
(defmethod funcion? 'starts-with? [funcion] true)

(defmethod ejecutar-funcion 'starts-with? [funcion-con-argumentos dato estado]
  (let [
          argumentos (obtener-argumentos-ejecutables funcion-con-argumentos dato estado)
          resultado  (apply str/starts-with? argumentos)
        ]
    resultado
  )
)

(defmethod precondiciones-validas? 'starts-with? [funcion argumentos dato estado]
  (validar-tipos funcion argumentos dato estado)
)

; Definicion de funcion ends-with?
(defmethod funcion? 'ends-with? [funcion] true)

(defmethod ejecutar-funcion 'ends-with? [funcion-con-argumentos dato estado]
  (let [
          argumentos (obtener-argumentos-ejecutables funcion-con-argumentos dato estado)
          resultado  (apply str/ends-with? argumentos)
        ]
    resultado
  )
)

(defmethod precondiciones-validas? 'ends-with? [funcion argumentos dato estado]
  (validar-tipos funcion argumentos dato estado)
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

(defmethod ejecutar-funcion 'current [funcion-con-argumentos dato estado]
  (let [
          argumentos (obtener-argumentos-ejecutables funcion-con-argumentos dato estado)
          resultado  (current argumentos dato estado)
        ]
    resultado
  )
)

(defmethod precondiciones-validas? 'current [funcion argumentos dato estado]
 (let [
         existe-campo  (contains? dato (first argumentos))
         cumple-tipos  (validar-tipos funcion argumentos dato estado)
       ]
     (and existe-campo cumple-tipos)
 )
)

; Definicion de funcion counter-value
(defmethod funcion? 'counter-value [funcion] true)

(defmethod ejecutar-funcion 'counter-value [funcion-con-argumentos dato estado]
  (let [
        argumentos (obtener-argumentos funcion-con-argumentos)
        resultado  (est/consultarContador estado (first argumentos) (second argumentos))
       ]
    resultado
 )
)

(defmethod precondiciones-validas? 'counter-value [funcion argumentos dato estado] true)

; Definicion de funcion past
(defmethod funcion? 'past [funcion] true)

(defmethod ejecutar-funcion 'past [funcion-con-argumentos dato estadoConUnDatoPasado]
  (let [
          argumentos   (obtener-argumentos-ejecutables funcion-con-argumentos dato estadoConUnDatoPasado)
          unDatoPasado (est/obtenerElDatoPasado estadoConUnDatoPasado)
          resultado    (get unDatoPasado (first argumentos))
        ]
    resultado
  )
)

(defmethod precondiciones-validas? 'past [funcion argumentos dato estadoConUnDatoPasado]
  (let [
    validar-tipos           (validar-tipos funcion argumentos dato estadoConUnDatoPasado)
    unDatoPasado            (est/obtenerElDatoPasado estadoConUnDatoPasado)
    existeCampoEnDatoPasado (est/datoPasadoContieneCampo (first argumentos) unDatoPasado)]
    (and
      (= validar-tipos true)
      existeCampoEnDatoPasado))
)
