(ns number
  (require  [definiciones :refer :all])
  (require  [procesamiento :refer :all]))

; Definicion de funcion +
(defmethod funcion? '+ [funcion] true)

(defmethod ejecutar-funcion '+ [funcion-con-argumentos dato estado]
  (let [
          argumentos (obtener-argumentos-ejecutables funcion-con-argumentos dato estado)
          resultado  (apply + argumentos)
        ]
    resultado
  )
)

(defmethod precondiciones-validas? '+ [funcion argumentos dato estado]
  (validar-tipos funcion argumentos dato estado))

; Definicion de funcion -
(defmethod funcion? '- [funcion] true)

(defmethod ejecutar-funcion '- [funcion-con-argumentos dato estado]
  (let [
          argumentos (obtener-argumentos-ejecutables funcion-con-argumentos dato estado)
          resultado  (apply - argumentos)
        ]
    resultado
  )
)

(defmethod precondiciones-validas? '- [funcion argumentos dato estado]
  (validar-tipos funcion argumentos dato estado)
)

; Definicion de funcion *
(defmethod funcion? '* [funcion] true)

(defmethod ejecutar-funcion '* [funcion-con-argumentos dato estado]
  (let [
          argumentos (obtener-argumentos-ejecutables funcion-con-argumentos dato estado)
          resultado  (apply * argumentos)
        ]
    resultado
  )
)

(defmethod precondiciones-validas? '* [funcion argumentos dato estado]
  (validar-tipos funcion argumentos dato estado)
)

; Definicion de funcion /
(defmethod funcion? '/ [funcion] true)

(defmethod ejecutar-funcion '/ [funcion-con-argumentos dato estado]
  (let [
          argumentos (obtener-argumentos-ejecutables funcion-con-argumentos dato estado)
          resultado  (apply / argumentos)
        ]
    resultado
  )
)

(defmethod precondiciones-validas? '/ [funcion argumentos dato estado]
  (let [
          tipos-validos (validar-tipos funcion argumentos dato estado)
          sin-ceros     (validar-no-existencia-cero funcion argumentos dato estado)
        ]

    (and tipos-validos sin-ceros)
  )
)

; Definicion de funcion mod
(defmethod funcion? 'mod [funcion] true)

(defmethod ejecutar-funcion 'mod [funcion-con-argumentos dato estado]
  (let [
          argumentos (obtener-argumentos-ejecutables funcion-con-argumentos dato estado)
          resultado  (apply mod argumentos)
        ]
    resultado
  )
)

(defmethod precondiciones-validas? 'mod [funcion argumentos dato estado]
  (validar-tipos funcion argumentos dato estado)
)

; Definicion de funcion <
(defmethod funcion? '< [funcion] true)

(defmethod ejecutar-funcion '< [funcion-con-argumentos dato estado]
  (let [
          argumentos (obtener-argumentos-ejecutables funcion-con-argumentos dato estado)
          resultado  (apply < argumentos)
        ]
    resultado
  )
)

(defmethod precondiciones-validas? '< [funcion argumentos dato estado]
  (validar-tipos funcion argumentos dato estado)
)

; Definicion de funcion >
(defmethod funcion? '> [funcion] true)

(defmethod ejecutar-funcion '> [funcion-con-argumentos dato estado]
  (let [
          argumentos (obtener-argumentos-ejecutables funcion-con-argumentos dato estado)
          resultado  (apply > argumentos)
        ]
    resultado
  )
)

(defmethod precondiciones-validas? '> [funcion argumentos dato estado]
  (validar-tipos funcion argumentos dato estado)
)

; Definicion de funcion <=
(defmethod funcion? '<= [funcion] true)

(defmethod ejecutar-funcion '<= [funcion-con-argumentos dato estado]
  (let [
          argumentos (obtener-argumentos-ejecutables funcion-con-argumentos dato estado)
          resultado  (apply <= argumentos)
        ]
    resultado
  )
)

(defmethod precondiciones-validas? '<= [funcion argumentos dato estado]
  (validar-tipos funcion argumentos dato estado)
)

; Definicion de funcion >=
(defmethod funcion? '>= [funcion] true)

(defmethod ejecutar-funcion '>= [funcion-con-argumentos dato estado]
  (let [
          argumentos (obtener-argumentos-ejecutables funcion-con-argumentos dato estado)
          resultado  (apply >= argumentos)
        ]
    resultado
  )
)

(defmethod precondiciones-validas? '>= [funcion argumentos dato estado]
  (validar-tipos funcion argumentos dato estado)
)
