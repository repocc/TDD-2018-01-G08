(ns tipos.bool-definicion-funciones)

(defmulti boolean-implementa-funcion? (fn [funcion] funcion))

(defmethod boolean-implementa-funcion? '= [funcion] true)

(defmethod boolean-implementa-funcion? '!= [funcion] true)

(defmethod boolean-implementa-funcion? 'or [funcion] true)

(defmethod boolean-implementa-funcion? 'and [funcion] true)

(defmethod boolean-implementa-funcion? 'not [funcion] true)

(defmethod boolean-implementa-funcion? 'true [funcion] true)

(defmethod boolean-implementa-funcion? 'false [funcion] true)

(defmethod boolean-implementa-funcion? :default [funcion] false)
