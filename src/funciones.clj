(ns funciones
  (require  [definiciones :refer :all])
  (require  [tipos.number-definicion-funciones :refer :all])
  (require  [tipos.string-definicion-funciones :refer :all])
  (require  [tipos.bool-definicion-funciones :refer :all]))

; Multimetodo implementa-funcion?
; Pasa la pregunta a cada uno de los tipos.tipos, ya que ellos mismos saben las funciones que implementan

(defmethod implementa-funcion? java.lang.Boolean [funcion argumento dato estado]
  (boolean-implementa-funcion? funcion)
)

(defmethod implementa-funcion? java.lang.Long [funcion argumento dato estado]
  (number-implementa-funcion? funcion)
)

(defmethod implementa-funcion? clojure.lang.Ratio [funcion argumento dato estado]
  (number-implementa-funcion? funcion)
)

(defmethod implementa-funcion? java.lang.Double [funcion argumento dato estado]
  (number-implementa-funcion? funcion)
)

(defmethod implementa-funcion? java.math.BigInteger [funcion argumento dato estado]
  (number-implementa-funcion? funcion)
)

(defmethod implementa-funcion? java.math.BigDecimal [funcion argumento dato estado]
  (number-implementa-funcion? funcion)
)

(defmethod implementa-funcion? java.lang.String [funcion argumento dato estado]
  (string-implementa-funcion? funcion)
)
