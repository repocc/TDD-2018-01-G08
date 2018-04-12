(ns definiciones)

(def ERROR 'ERROR)
(def ON 'ON)
(def OFF 'OFF)

(defmulti funcion? (fn [funcion] funcion))
(defmulti precondiciones-validas? (fn [funcion args dato estado] funcion))
(defmulti implementa-funcion? (fn [funcion arg dato estado] (type arg)))
(defmulti actualizar-regla (fn [regla nuevo-dato estado] (type regla)))
(defmulti actualizar-valor (fn [regla nuevo-dato estado valor-argumentos] (type regla)))
(defmulti ejecutar-funcion (fn [funcion-con-args dato estado]
  (if (seq? funcion-con-args)
    (first funcion-con-args)
    funcion-con-args)))
