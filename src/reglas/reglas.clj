;funciones requeridas de este archivo:
;;(formatoCorrectoListaDeReglasEnDSL? [listaDeReglasEnDSL])
;;(formatoCorrectoUnaReglaEnDSL? [unaReglaEnDSL])

(ns reglas.reglas
  (:require
    [reglas.contador :as cont]
    [reglas.contadorPaso :as contP]
    [reglas.senyal :as sen]
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
  "TODO"
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
