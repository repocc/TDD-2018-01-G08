(ns com.ar.fiuba.tdd.clojure.tipos.number-funciones
  (require  [com.ar.fiuba.tdd.clojure.interprete.definiciones :refer :all])
  (require  [com.ar.fiuba.tdd.clojure.interprete.funciones-globales :refer :all])
  (require  [com.ar.fiuba.tdd.clojure.interprete.procesamiento :refer :all]))

; Definicion de funcion +
(defmethod funcion? '+ [funcion] true)

(defmethod ejecutarFuncion '+ [funcionConArgumentos dato estado]
  (let [
          argumentos (obtenerArgumentosEjecutables funcionConArgumentos dato estado)
          resultado  (apply + argumentos)
        ]
    resultado
  )
)

(defmethod precondicionesValidas? '+ [funcion argumentos dato estado]
  (validarTipos funcion argumentos dato estado))

; Definicion de funcion -
(defmethod funcion? '- [funcion] true)

(defmethod ejecutarFuncion '- [funcionConArgumentos dato estado]
  (let [
          argumentos (obtenerArgumentosEjecutables funcionConArgumentos dato estado)
          resultado  (apply - argumentos)
        ]
    resultado
  )
)

(defmethod precondicionesValidas? '- [funcion argumentos dato estado]
  (validarTipos funcion argumentos dato estado)
)

; Definicion de funcion *
(defmethod funcion? '* [funcion] true)

(defmethod ejecutarFuncion '* [funcionConArgumentos dato estado]
  (let [
          argumentos (obtenerArgumentosEjecutables funcionConArgumentos dato estado)
          resultado  (apply * argumentos)
        ]
    resultado
  )
)

(defmethod precondicionesValidas? '* [funcion argumentos dato estado]
  (validarTipos funcion argumentos dato estado)
)

; Definicion de funcion /
(defmethod funcion? '/ [funcion] true)

(defmethod ejecutarFuncion '/ [funcionConArgumentos dato estado]
  (let [
          argumentos (obtenerArgumentosEjecutables funcionConArgumentos dato estado)
          resultado  (apply / argumentos)
        ]
    resultado
  )
)

(defmethod precondicionesValidas? '/ [funcion argumentos dato estado]
  (let [
          tiposValidos (validarTipos funcion argumentos dato estado)
          sinCeros     (validarNoExistenciaCero funcion argumentos dato estado)
        ]

    (and tiposValidos sinCeros)
  )
)

; Definicion de funcion mod
(defmethod funcion? 'mod [funcion] true)

(defmethod ejecutarFuncion 'mod [funcionConArgumentos dato estado]
  (let [
          argumentos (obtenerArgumentosEjecutables funcionConArgumentos dato estado)
          resultado  (apply mod argumentos)
        ]
    resultado
  )
)

(defmethod precondicionesValidas? 'mod [funcion argumentos dato estado]
  (validarTipos funcion argumentos dato estado)
)

; Definicion de funcion <
(defmethod funcion? '< [funcion] true)

(defmethod ejecutarFuncion '< [funcionConArgumentos dato estado]
  (let [
          argumentos (obtenerArgumentosEjecutables funcionConArgumentos dato estado)
          resultado  (apply < argumentos)
        ]
    resultado
  )
)

(defmethod precondicionesValidas? '< [funcion argumentos dato estado]
  (validarTipos funcion argumentos dato estado)
)

; Definicion de funcion >
(defmethod funcion? '> [funcion] true)

(defmethod ejecutarFuncion '> [funcionConArgumentos dato estado]
  (let [
          argumentos (obtenerArgumentosEjecutables funcionConArgumentos dato estado)
          resultado  (apply > argumentos)
        ]
    resultado
  )
)

(defmethod precondicionesValidas? '> [funcion argumentos dato estado]
  (validarTipos funcion argumentos dato estado)
)

; Definicion de funcion <=
(defmethod funcion? '<= [funcion] true)

(defmethod ejecutarFuncion '<= [funcionConArgumentos dato estado]
  (let [
          argumentos (obtenerArgumentosEjecutables funcionConArgumentos dato estado)
          resultado  (apply <= argumentos)
        ]
    resultado
  )
)

(defmethod precondicionesValidas? '<= [funcion argumentos dato estado]
  (validarTipos funcion argumentos dato estado)
)

; Definicion de funcion >=
(defmethod funcion? '>= [funcion] true)

(defmethod ejecutarFuncion '>= [funcionConArgumentos dato estado]
  (let [
          argumentos (obtenerArgumentosEjecutables funcionConArgumentos dato estado)
          resultado  (apply >= argumentos)
        ]
    resultado
  )
)

(defmethod precondicionesValidas? '>= [funcion argumentos dato estado]
  (validarTipos funcion argumentos dato estado)
)
