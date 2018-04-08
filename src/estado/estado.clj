(ns estado.estado)

(defrecord Estado [reglas]) ;Estado será un record con un campo "reglas", que será un mapa donde se almacenan los contadores y/o señales.

(defmulti procesarUnaRegla
  "Agrega una regla conocida al estado a partir de la lista que la expresa en DSL, o deja el estado tal y como está si está vacía la lista que expresa en DSL la regla. Devuelve un nuevo estado con la regla agregada."
  (fn [estado identificadorDeRegla] (first identificadorDeRegla)))
