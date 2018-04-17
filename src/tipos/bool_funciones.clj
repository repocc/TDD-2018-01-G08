(ns tipos.bool-funciones
  (require  [definiciones :refer :all])
  (require  [funciones-globales :refer :all]))

; Definicion de funcion or
(defmethod funcion? 'or [funcion] true)

(defmethod ejecutarFuncion 'or [funcionConArgumentos dato estado]
  (let [
          argumentos (obtenerArgumentosEjecutables funcionConArgumentos dato estado)
          resultado  (apply some true? [argumentos])
        ]

    (if (= resultado nil)
      false
      true
    )
  )
)

(defmethod precondicionesValidas? 'or [funcion argumentos dato estado]
  (validarTipos funcion argumentos dato estado)
)

; Definicion de funcion and
(defmethod funcion? 'and [funcion] true)

(defmethod ejecutarFuncion 'and [funcionConArgumentos dato estado]
  (let [
          argumentos (obtenerArgumentosEjecutables funcionConArgumentos dato estado)
          resultado  (apply every? true? [argumentos])
        ]
    (if (= resultado nil)
      false
      true
    )
  )
)

(defmethod precondicionesValidas? 'and [funcion argumentos dato estado]
  (validarTipos funcion argumentos dato estado)
)

; Definicion de funcion not
(defmethod funcion? 'not [funcion] true)

(defmethod ejecutarFuncion 'not [funcionConArgumentos dato estado]
  (let [
          argumentos (obtenerArgumentosEjecutables funcionConArgumentos dato estado)
          resultado  (apply not argumentos)
        ]
    resultado
  )
)

(defmethod precondicionesValidas? 'not [funcion argumentos dato estado]
  (validarTipos funcion argumentos dato estado)
)

; Definicion de funcion true
(defmethod funcion? 'true [funcion] true)

(defmethod ejecutarFuncion 'true [funcionConArgumentos dato estado] true)

(defmethod precondicionesValidas? 'true [funcion argumentos dato estado] true)

; Definicion de funcion false
(defmethod funcion? 'false [funcion] true)

(defmethod ejecutarFuncion 'false [funcionConArgumentos dato estado] false)

(defmethod precondicionesValidas? 'false [funcion argumentos dato estado] true)
