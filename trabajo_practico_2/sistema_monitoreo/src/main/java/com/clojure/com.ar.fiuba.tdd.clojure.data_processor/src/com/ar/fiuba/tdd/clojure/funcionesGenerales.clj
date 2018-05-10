(ns com.ar.fiuba.tdd.clojure.funcionesGenerales)

(defn boolean?
  "Equivalente a la función \"boolean?\" de clojure 1.9."
  [x]
  (or (true? x) (false? x)))
