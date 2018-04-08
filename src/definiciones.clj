(ns definiciones)

(def ERROR 'ERROR)

(defmulti funcion? (fn [funcion] funcion))
(defmulti precondiciones-validas? (fn [funcion args dato estado] funcion))
(defmulti implementa-funcion? (fn [funcion arg dato estado] (type arg)))
(defmulti ejecutar-funcion (fn [funcion-con-args dato estado] (first funcion-con-args)))