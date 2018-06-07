(ns com.ar.fiuba.tdd.clojure.tipos.tipos)

(defmulti tipoBasico? (fn [arg] (type arg)))

(defmethod tipoBasico? java.lang.String [arg] true)

(defmethod tipoBasico? java.lang.Long [arg] true)

(defmethod tipoBasico? clojure.lang.Ratio [arg] true)

(defmethod tipoBasico? java.lang.Double [arg] true)

(defmethod tipoBasico? java.math.BigInteger [arg] true)

(defmethod tipoBasico? java.math.BigDecimal [arg] true)

(defmethod tipoBasico? java.lang.Boolean [arg] true)

(defmethod tipoBasico? :default [arg] false)
