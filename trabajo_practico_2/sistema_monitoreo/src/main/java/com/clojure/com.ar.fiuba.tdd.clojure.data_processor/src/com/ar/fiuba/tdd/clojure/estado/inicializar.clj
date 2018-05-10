;Funciones requeridas de este archivo:
;;(cargarListaDeReglas [listaDeReglasEnDSL])

(ns com.ar.fiuba.tdd.clojure.estado.inicializar
  (:require
    [com.ar.fiuba.tdd.clojure.estado.estado :as est] ;crearEstadoVacio, cargarUnaRegla
    [com.ar.fiuba.tdd.clojure.reglas.reglas :as reg] ;formatoCorrectoListaDeReglasEnDSL?, crearSetVerificadorReglaEnFormatoDSL, formatoCorrectoUnaReglaEnDSL
    [com.ar.fiuba.tdd.clojure.reglas.contador :as cont] ;cargarUnaRegla
    [com.ar.fiuba.tdd.clojure.reglas.contadorPaso :as contP] ;cargarUnaRegla
    [com.ar.fiuba.tdd.clojure.reglas.senyal :as sen] ;cargarUnaRegla
    :reload-all))

; TODO(Iván): Esta función se puede generalizar a un "filtrar usando una función que recibe no solo el párametro que es el elemento de la lista a filtrar, sino también parámetros extra que son pasados a la función a continuación del elemento de la lista a comprobar. Esta función recibiría la función testeadora que devuelve true o false, y los parámetros extra que se pasan a la función filtradora, y la lista a filtrar. Si se hace, moverla a un archivo probablemente en src, con todas las demás funciones genéricas.
; TODO(Iván): Hay un tema: cada regla se chequea con todos los verificadores para ver si alguno da "true", y si así lo es, se la acepta, pero luego, al intentar cargarla, hay un defmulti "cargarUnaRegla" que decide qué tipo de regla es, con lo cual solo se la va  a intentar cargar una vez. Si dos reglas tienen una estructura similar, pero necesitan diferentes defmethod, y uno de los defmethods "captura" la regla que en realidaad es de otro tipo, va a fallar, o en el mejor de los casos se da cuenta del fallo y tira la regla sin cargarla. Clojure no avisa si hay dos defmethods con el mismo "dispatch value", el segundo simplemente pisa al primero.
(defn filtrarReglasConFormatoIncorrecto
  "Filtra las reglas de listaDeReglasEnDSL que no tienen el formato DSL correcto según los verificadores cargados en el setVerificador. Cada regla se chequea contra todos los verificadores y si alguno devuelve \"true\", entonces la regla es aceptada."
  [listaDeReglasEnDSL setVerificador]
  (filter
    (fn
      [unaReglaEnDSL]
      (reg/formatoCorrectoUnaReglaEnDSL? unaReglaEnDSL setVerificador))
    listaDeReglasEnDSL))

(defn crearSetVerificadorDeReglasYCargarReglasSoportadas
  "Se cargan las reglas a soportar en el setVerificador y se lo devuelve. Aquí se deben agregar nuevos tipos de reglas cuando deban ser incorporados al sistema."
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
