;funciones requeridas en este paquete:
;;(cargarListaDeReglas [listaDeReglasEnDSL])

(ns estado.inicializar
  (:require
    [estado.estado :as est] ;crearEstadoVacio, cargarUnaRegla
    [reglas.reglas :as reg] ;formatoCorrectoListaDeReglasEnDSL?
    [reglas.contador :as cont] ;cargarUnaRegla
    [reglas.contadorPaso :as contP] ;cargarUnaRegla
    [reglas.senyal :as sen] ;cargarUnaRegla
    :reload-all))

; Esta función se puede generalizar a un "filtrar usando una función que recibe no solo el párametro que es el elemento de la lista a filtrar, sino también parámetros extra que son pasados a la función a continuación del elemento de la lista a comprobar. Esta función recibiría la función testeadora que devuelve true o false, y los parámetros extra que se pasan a la función filtradora, y la lista a filtrar. Si se hace, moverla a un archivo probablemente en src, con todas las demás funciones genéricas.
(defn filtrarReglasConFormatoIncorrecto
  "TODO"
  [listaDeReglasEnDSL setVerificador]
  (filter
    (fn
      [unaReglaEnDSL]
      (reg/formatoCorrectoUnaReglaEnDSL? unaReglaEnDSL setVerificador))
    listaDeReglasEnDSL))

(defn crearSetVerificadorDeReglasYCargarReglasSoportadas
  "TODO"
  []
  (reg/crearSetVerificadorReglaEnFormatoDSL
    cont/formatoCorrectoUnaReglaContadorEnDSL?
    contP/formatoCorrectoUnaReglaContadorPasoEnDSL?
    sen/formatoCorrectoUnaReglaSenyalEnDSL?))

(defn cargarListaDeReglas
  "Recibe una lista de reglas expresadas en el lenguage específico de dominio (DSL), cada una de las cuales se representa como una lista, y las carga en el estado. Retorna el estado de un sistema recién creado."
  ;Formato de listaDeReglasEnDSL: ((<regla en DSL>)*)
  ;listaDeReglasEnDSL debe ser una lista de listas, es válido que sea una lista vacía, si alguna de las reglas no es una lista o no tiene el formato DSL correcto, la misma será ignorada.
  [listaDeReglasEnDSL]
  (let [
    estado               (est/crearEstadoVacio) ;Parto de un estado vacío (sin ninguna regla cargada ni datos cargados en el historial de datosPasados).
    setVerificadorReglas (crearSetVerificadorDeReglasYCargarReglasSoportadas)]
    (if (reg/formatoCorrectoListaDeReglasEnDSL? listaDeReglasEnDSL)
      (let [
        listaDeReglasEnDSLFiltrada (filtrarReglasConFormatoIncorrecto listaDeReglasEnDSL setVerificadorReglas)]
        (reduce
          (fn [estado unaReglaEnDSL] (est/cargarUnaRegla estado unaReglaEnDSL))
          estado
          listaDeReglasEnDSLFiltrada))
      estado))) ;Si listaDeReglasEnDSL no tiene el formato correcto devuelvo un estado vacío (sin ninguna regla cargada).
