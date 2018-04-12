(ns bool
  (require  [definiciones :refer :all])
  (require  [procesamiento :refer :all]))

(defmulti boolean-implementa-funcion? (fn [funcion] funcion))

(defmethod boolean-implementa-funcion? '= [funcion]
  true
)

(defmethod boolean-implementa-funcion? '!= [funcion]
  true
)

(defmethod boolean-implementa-funcion? 'or [funcion]
  true
)

(defmethod boolean-implementa-funcion? 'and [funcion]
  true
)

(defmethod boolean-implementa-funcion? 'not [funcion]
  true
)

(defmethod boolean-implementa-funcion? :default [funcion]
  false
)

; Definicion de funcion or
(defmethod funcion? 'or [funcion]
  true
)

(defmethod ejecutar-funcion 'or [funcion-con-argumentos dato estado]
  (let [
          argumentos (obtener-argumentos-ejecutables funcion-con-argumentos dato estado)
          resultado  (apply some true? [argumentos])
        ]
    
    (if (= resultado nil)
      false
      true
    )
  )
)

(defmethod precondiciones-validas? 'or [funcion argumentos dato estado]
  (validar-tipos funcion argumentos dato estado)
)

; Definicion de funcion and
(defmethod funcion? 'and [funcion]
  true
)

(defmethod ejecutar-funcion 'and [funcion-con-argumentos dato estado]
  (let [
          argumentos (obtener-argumentos-ejecutables funcion-con-argumentos dato estado)
          resultado  (apply every? true? [argumentos])
        ]
    (if (= resultado nil)
      false
      true
    )
  )
)

(defmethod precondiciones-validas? 'and [funcion argumentos dato estado]
  (validar-tipos funcion argumentos dato estado)
)

; Definicion de funcion not
(defmethod funcion? 'not [funcion]
  true
)

(defmethod ejecutar-funcion 'not [funcion-con-argumentos dato estado]
  (let [
          argumentos (obtener-argumentos-ejecutables funcion-con-argumentos dato estado)
          resultado  (apply not argumentos)
        ]
    resultado
  )
)

(defmethod precondiciones-validas? 'not [funcion argumentos dato estado]
  (validar-tipos funcion argumentos dato estado)
)
