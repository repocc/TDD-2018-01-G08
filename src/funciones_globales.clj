(ns funciones_globales
  (require  [definiciones :refer :all])
  (require  [tipos :refer :all])
  (require  [aux :refer :all]))

; Para cada una de las funciones que se definen es necesario implementar los sig multimetodos:
; + funcion?
; + ejecutar-funcion
; + precondiciones-validas?

; Definicion de funcion =
(defmethod funcion? '= [funcion] true)

(defmethod ejecutar-funcion '= [funcion-con-argumentos dato estado]
  (let [
          argumentos (obtener-argumentos-ejecutables funcion-con-argumentos dato estado)
          resultado  (apply = argumentos)
        ]
    resultado
  )
)

(defmethod precondiciones-validas? '= [funcion argumentos dato estado]
  (validar-tipos funcion argumentos dato estado)
)

(defmethod ejecutar-funcion :default [funcion-con-argumentos dato estado] funcion-con-argumentos)

; Definicion de funcion =
(defmethod funcion? '!= [funcion] true)

(defmethod ejecutar-funcion '!= [funcion-con-argumentos dato estado]
  (let [
          argumentos (obtener-argumentos-ejecutables funcion-con-argumentos dato estado)
          resultado  (apply not= argumentos)
        ]
    resultado
  )
)

(defmethod precondiciones-validas? '!= [funcion argumentos dato estado]
  (validar-tipos funcion argumentos dato estado)
)

(defmethod ejecutar-funcion :default [funcion-con-argumentos dato estado] 
  (if (tipo-basico? funcion-con-argumentos)
    funcion-con-argumentos
    false
  )
  ;false
)

(defmethod funcion? :default [funcion] false)

(defmethod precondiciones-validas? :default [funcion argumentos dato estado] false)
