(ns tipos.number-definicion-funciones)

(defmulti numberImplementaFuncion? (fn [funcion] funcion))

(defmethod numberImplementaFuncion? '= [funcion] true)

(defmethod numberImplementaFuncion? '!= [funcion] true)

(defmethod numberImplementaFuncion? '+ [funcion] true)

(defmethod numberImplementaFuncion? '- [funcion] true)

(defmethod numberImplementaFuncion? '* [funcion] true)

(defmethod numberImplementaFuncion? '/ [funcion] true)

(defmethod numberImplementaFuncion? 'mod [funcion] true)

(defmethod numberImplementaFuncion? '< [funcion] true)

(defmethod numberImplementaFuncion? '> [funcion] true)

(defmethod numberImplementaFuncion? '<= [funcion] true)

(defmethod numberImplementaFuncion? '>= [funcion] true)

(defmethod numberImplementaFuncion? :default [funcion] false)
