;Funciones requeridas de este archivo:
;;(formatoCorrectoListaDeReglasEnDSL? [listaDeReglasEnDSL])
;;(formatoCorrectoUnaReglaEnDSL? [unaReglaEnDSL])

(ns com.ar.fiuba.tdd.clojure.reglas.reglas
  (:require
    [com.ar.fiuba.tdd.clojure.reglas.contador :as cont]
    [com.ar.fiuba.tdd.clojure.reglas.contadorPaso :as contP]
    [com.ar.fiuba.tdd.clojure.reglas.senyal :as sen]
    :reload-all))

(defn formatoCorrectoListaDeReglasEnDSL?
  "Comprueba si la lista de reglas en DSL es en verdad una lista."
  [listaDeReglasEnDSL]
  (list? listaDeReglasEnDSL))

(defn crearSetVerificadorReglaEnFormatoDSL
  "Genera un set de funciones que verifican si una regla está correctamente expresada en DSL."
  [& verificadores]
  (set verificadores))

(defn agregarVerificadorReglaEnFormatoDSL
  "Se agregan los verificadoresAAgregar al setDeVerificadores. Devuelve un nuevo setDeVerificadores actualizado."
  [setDeVerificadores & verificadoresAAgregar]
  (reduce conj setDeVerificadores verificadoresAAgregar))

(defn formatoCorrectoUnaReglaEnDSL?
  "Comprueba si una regla tiene un formato DSL correcto."
  [unaReglaEnDSL setVerificador]
  (let [
    verificaciones (for
      [funcion (vec setVerificador)]
      (funcion unaReglaEnDSL))]
    (not-every? false? verificaciones)))
