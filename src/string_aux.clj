(ns string-aux)


(defmulti string-implementa-funcion? (fn [funcion] funcion))

(defmethod string-implementa-funcion? '= [funcion]
  true
)

(defmethod string-implementa-funcion? '!= [funcion]
  true
)

(defmethod string-implementa-funcion? 'concat [funcion]
  true
)

(defmethod string-implementa-funcion? 'includes? [funcion]
  true
)

(defmethod string-implementa-funcion? 'starts-with? [funcion]
  true
)

(defmethod string-implementa-funcion? 'ends-with? [funcion]
  true
)

(defmethod string-implementa-funcion? 'current [funcion]
  true
)

(defmethod string-implementa-funcion? 'past [funcion]
  true
)

(defmethod string-implementa-funcion? 'counter-value [funcion]
  true
)

(defmethod string-implementa-funcion? :default [funcion]
  false
)
