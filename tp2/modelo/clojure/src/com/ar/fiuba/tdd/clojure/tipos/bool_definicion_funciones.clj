(ns com.ar.fiuba.tdd.clojure.tipos.bool-definicion-funciones)

(defmulti booleanImplementaFuncion? (fn [funcion] funcion))

(defmethod booleanImplementaFuncion? '= [funcion] true)

(defmethod booleanImplementaFuncion? '!= [funcion] true)

(defmethod booleanImplementaFuncion? 'or [funcion] true)

(defmethod booleanImplementaFuncion? 'and [funcion] true)

(defmethod booleanImplementaFuncion? 'not [funcion] true)

(defmethod booleanImplementaFuncion? 'true [funcion] true)

(defmethod booleanImplementaFuncion? 'false [funcion] true)

(defmethod booleanImplementaFuncion? :default [funcion] false)
