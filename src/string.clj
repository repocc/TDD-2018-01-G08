(ns string
  (require  [definiciones :refer :all])
  (require  [procesamiento :refer :all])
  (require  [clojure.string :as str]))

(defmulti string-implementa-funcion? (fn [funcion] funcion))

(defmethod string-implementa-funcion? '= [funcion]
  true
)

(defmethod string-implementa-funcion? '!= [funcion]
  true
)

(defmethod string-implementa-funcion? 'concat [funcion]
  true
)

(defmethod string-implementa-funcion? 'includes? [funcion]
  true
)

(defmethod string-implementa-funcion? 'starts-with? [funcion]
  true
)

(defmethod string-implementa-funcion? 'ends-with? [funcion]
  true
)

(defmethod string-implementa-funcion? 'current [funcion]
  true
)

(defmethod string-implementa-funcion? :default [funcion]
  false
)

; Definicion de funcion concat
(defmethod funcion? 'concat [funcion]
  true
)

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
(defmethod funcion? 'includes? [funcion]
  true
)

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
(defmethod funcion? 'starts-with? [funcion]
  true
)

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
(defmethod funcion? 'ends-with? [funcion]
  true
)

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
(defmethod funcion? 'current [funcion]
  true
)

(defn current [argumento dato estado]
  (let [
          campo       (first argumento)
          valor_campo (get dato campo)
        ]
    (= valor_campo true)
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
  (validar-tipos funcion argumentos dato estado)
)
