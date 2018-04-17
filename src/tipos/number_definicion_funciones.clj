(ns tipos.number-definicion-funciones)

(defmulti number-funciones-implementa-funcion? (fn [funcion] funcion))

(defmethod number-funciones-implementa-funcion? '= [funcion] true)

(defmethod number-funciones-implementa-funcion? '!= [funcion] true)

(defmethod number-funciones-implementa-funcion? '+ [funcion] true)

(defmethod number-funciones-implementa-funcion? '- [funcion] true)

(defmethod number-funciones-implementa-funcion? '* [funcion] true)

(defmethod number-funciones-implementa-funcion? '/ [funcion] true)

(defmethod number-funciones-implementa-funcion? 'mod [funcion] true)

(defmethod number-funciones-implementa-funcion? '< [funcion] true)

(defmethod number-funciones-implementa-funcion? '> [funcion] true)

(defmethod number-funciones-implementa-funcion? '<= [funcion] true)

(defmethod number-funciones-implementa-funcion? '>= [funcion] true)

(defmethod number-funciones-implementa-funcion? :default [funcion] false)
