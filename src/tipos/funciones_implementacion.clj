(ns tipos.funciones-implementacion
  (require  [interprete.definiciones :refer :all])
  (require  [tipos.number-definicion-funciones :refer :all])
  (require  [tipos.string-definicion-funciones :refer :all])
  (require  [tipos.bool-definicion-funciones :refer :all]))

; Multimetodo implementaFuncion?
; Pasa la pregunta a cada uno de los tipos, ya que ellos mismos saben las funciones que implementan

(defmethod implementaFuncion? java.lang.Boolean [funcion argumento dato estado]
  (booleanImplementaFuncion? funcion)
)

(defmethod implementaFuncion? java.lang.Long [funcion argumento dato estado]
  (numberImplementaFuncion? funcion)
)

(defmethod implementaFuncion? clojure.lang.Ratio [funcion argumento dato estado]
  (numberImplementaFuncion? funcion)
)

(defmethod implementaFuncion? java.lang.Double [funcion argumento dato estado]
  (numberImplementaFuncion? funcion)
)

(defmethod implementaFuncion? java.math.BigInteger [funcion argumento dato estado]
  (numberImplementaFuncion? funcion)
)

(defmethod implementaFuncion? java.math.BigDecimal [funcion argumento dato estado]
  (numberImplementaFuncion? funcion)
)

(defmethod implementaFuncion? java.lang.String [funcion argumento dato estado]
  (stringImplementaFuncion? funcion)
)
