(ns number-aux)

(defmulti number-implementa-funcion? (fn [funcion] funcion))

(defmethod number-implementa-funcion? '= [funcion] true)

(defmethod number-implementa-funcion? '!= [funcion] true)

(defmethod number-implementa-funcion? '+ [funcion] true)

(defmethod number-implementa-funcion? '- [funcion] true)

(defmethod number-implementa-funcion? '* [funcion] true)

(defmethod number-implementa-funcion? '/ [funcion] true)

(defmethod number-implementa-funcion? 'mod [funcion] true)

(defmethod number-implementa-funcion? '< [funcion] true)

(defmethod number-implementa-funcion? '> [funcion] true)

(defmethod number-implementa-funcion? '<= [funcion] true)

(defmethod number-implementa-funcion? '>= [funcion] true)

(defmethod number-implementa-funcion? :default [funcion] false)
