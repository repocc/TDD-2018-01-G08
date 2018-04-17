(ns interprete.definiciones)

(def ERROR 'ERROR)

(defmulti funcion? (fn [funcion] funcion))

(defmulti precondicionesValidas? (fn [funcion args dato estado] funcion))

(defmulti implementaFuncion? (fn [funcion arg dato estado] (type arg)))

(defmulti ejecutarFuncion (fn [funcionConArgumentos dato estado]
  (if (seq? funcionConArgumentos)
    (first funcionConArgumentos)
    funcionConArgumentos)))
