(ns tipos.bool-funciones
  (require  [definiciones :refer :all])
  (require  [aux :refer :all]))

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

; Definicion de funcion true
(defmethod funcion? 'true [funcion]
  true
)

(defmethod ejecutar-funcion 'true [funcion-con-argumentos dato estado]
  true
)

(defmethod precondiciones-validas? 'true [funcion argumentos dato estado]
  true
)

; Definicion de funcion false
(defmethod funcion? 'false [funcion]
  true
)

(defmethod ejecutar-funcion 'false [funcion-con-argumentos dato estado]
  false
)

(defmethod precondiciones-validas? 'false [funcion argumentos dato estado]
  true
)
