(ns tipos)

(defmulti tipo-basico? (fn [arg] (type arg)))

(defmethod tipo-basico? java.lang.String [arg]
  true
)

(defmethod tipo-basico? java.lang.Long [arg]
  true
)

(defmethod tipo-basico? java.lang.Boolean [arg]
  true
)

(defmethod tipo-basico? :default [arg]
  false
)
