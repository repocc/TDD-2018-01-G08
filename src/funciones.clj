(ns funciones
  (require  [definiciones :refer :all])
  (require  [procesamiento :refer :all])
  (require  [tipos :refer :all])
  (require  [funciones_globales :refer :all])
  (require  [number :refer :all])
  (require  [string :refer :all])
  (require  [bool :refer :all]))

; Multimetodo implementa-funcion?
; Pasa la pregunta a cada uno de los tipos, ya que ellos mismos saben las funciones que implementan

(defmethod implementa-funcion? java.lang.Boolean [funcion argumento dato estado]
  (boolean-implementa-funcion? funcion)
)

(defmethod implementa-funcion? java.lang.Long [funcion argumento dato estado]
  (number-implementa-funcion? funcion)
)

(defmethod implementa-funcion? java.lang.String [funcion argumento dato estado]
  (string-implementa-funcion? funcion)
)

; En el caso que no sea un tipo basico, es decir, otra expresion. Esta es analizada.
(defmethod implementa-funcion? :default [funcion argumento dato estado]
  (expresion-valida? argumento dato estado)
)
