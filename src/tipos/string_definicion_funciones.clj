(ns tipos.string-definicion-funciones)

(defmulti stringImplementaFuncion? (fn [funcion] funcion))

(defmethod stringImplementaFuncion? '= [funcion] true)

(defmethod stringImplementaFuncion? '!= [funcion] true)

(defmethod stringImplementaFuncion? 'concat [funcion] true)

(defmethod stringImplementaFuncion? 'includes? [funcion] true)

(defmethod stringImplementaFuncion? 'starts-with? [funcion] true)

(defmethod stringImplementaFuncion? 'ends-with? [funcion] true)

(defmethod stringImplementaFuncion? 'current [funcion] true)

(defmethod stringImplementaFuncion? 'past [funcion] true)

(defmethod stringImplementaFuncion? 'counter-value [funcion] true)

(defmethod stringImplementaFuncion? :default [funcion] false)
