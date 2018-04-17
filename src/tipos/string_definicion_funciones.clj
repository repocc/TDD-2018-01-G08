(ns tipos.string-definicion-funciones)

(defmulti string-funciones-implementa-funcion? (fn [funcion] funcion))

(defmethod string-funciones-implementa-funcion? '= [funcion] true)

(defmethod string-funciones-implementa-funcion? '!= [funcion] true)

(defmethod string-funciones-implementa-funcion? 'concat [funcion] true)

(defmethod string-funciones-implementa-funcion? 'includes? [funcion] true)

(defmethod string-funciones-implementa-funcion? 'starts-with? [funcion] true)

(defmethod string-funciones-implementa-funcion? 'ends-with? [funcion] true)

(defmethod string-funciones-implementa-funcion? 'current [funcion] true)

(defmethod string-funciones-implementa-funcion? 'past [funcion] true)

(defmethod string-funciones-implementa-funcion? 'counter-value [funcion] true)

(defmethod string-funciones-implementa-funcion? :default [funcion] false)
