(ns interprete.procesamiento
  (require  [interprete.definiciones :refer :all])
  (require  [tipos.funciones-implementacion :refer :all])
  (require  [interprete.funciones-globales :refer :all])
  (require  [tipos.tipos :refer :all])
  (require  [estado.estado_auxiliar :as est_aux]))

(defn obtenerCeros [argumentos dato estado]
  (filter (fn [argumento]

              (if(seq? argumento)
                (if (funcion? (first argumento))
                  (= (ejecutarFuncion argumento dato estado) 0))
                (= argumento 0)
              )) argumentos)
)

(defn validarNoExistenciaCero [funcion argumentos dato estado]
  (let [
          divisores        (drop 1 argumentos)
          argumentosCeros (obtenerCeros divisores dato estado)
          existenCeros    (empty? argumentosCeros)
        ]
    existenCeros
  )
)

; Para que una expresion sea valida, deben ser validas todas sus sub-expresiones.
; Se deben cumplir cada una de las precondiciones.
(defn expresionValida? [funcionConArgumentos dato estado]
  ;Verifico si no se trata de un valor de un tipo basico
  (if (not (seq? funcionConArgumentos))
    (if (tipoBasico? funcionConArgumentos)
      true
      false
    )
    (let [
          funcion     (obtenerNombreFuncion funcionConArgumentos)
          argumentos  (obtenerArgumentos funcionConArgumentos)
        ]
    (if (= (funcion? funcion) true)
      (if(= (precondicionesValidas? funcion argumentos dato estado) true)
        true
        false
      )
    )
  )
 )
)

; En el caso que no sea un tipo basico, es decir, otra expresion. Esta es analizada.
(defmethod implementaFuncion? :default [funcion argumento dato estado]
  (expresionValida? argumento dato estado)
)

(defn ejecutarFuncion-ciclando-por-datosPasados
  "TODO(Iván): agregar descripción."
  [funcionConArgumentos dato estado]
  (let [
    datosPasadosLista (est_aux/obtenerDatosPasados estado)
    listaEjecuciones
      (for
        [unDatoPasado datosPasadosLista]
        (let [estadoConUnDatoPasado (assoc estado :datosPasados (list unDatoPasado))]
          (if (expresionValida? funcionConArgumentos dato estadoConUnDatoPasado) ;TODO: (1)Probablemente esta consulta acá esté de más, se debería llamar "expresionValida?" antes de llamar "ejecutarFuncion"...(2) Si, ya se llamó, pero la lista de datosPsados no se filtra, se provee completa, así que si no filtrás en este momento, vas a estar llamando a la funcion past con un con datos pasados que no tiene ese campo.
            (ejecutarFuncion funcionConArgumentos dato estadoConUnDatoPasado)
            false)))]
    (not-every? false? listaEjecuciones)))

(defn hay-past?
  "TODO(Iván): agregar descripción."
  [funcionConArgumentos]
  (if (seq? funcionConArgumentos)
    (if (= (first funcionConArgumentos) 'past)
      true
      (let [hayPast (not-every? false? (map hay-past? (rest funcionConArgumentos)))]
        hayPast))
    false))

(defn ejecutarFuncion-contemplando-past
  ;TODO (Iván) Este defn lo tuve que poner acá porque es el mejor lugar que encontré sin tener que poner otro require en procesar.clj.
  "TODO(Iván): agregar descripción."
  [funcionConArgumentos dato estado]
  ;Si hay algun past en funcionConArgumentos, entonces sacar todos los datosPasados de estado, y hacer un foreach de cada dato metiendo ese dato en el historial de estado, y llamando al ejecutarFuncion comun. El foreach se corta cuando da true la evaluacion o se acaban los datosPasados.
  (if (hay-past? funcionConArgumentos)
    (ejecutarFuncion-ciclando-por-datosPasados funcionConArgumentos dato estado)
    (ejecutarFuncion funcionConArgumentos dato estado)))

(defn expresion-valida-ciclando-por-datosPasados?
  "TODO(Iván): agregar descripción."
  [funcionConArgumentos dato estado]
  (let [
    datosPasadosLista (est_aux/obtenerDatosPasados estado)
    listaEjecuciones
      (for
        [unDatoPasado datosPasadosLista]
        (let [estadoConUnDatoPasado (assoc estado :datosPasados (list unDatoPasado))]
          (expresionValida? funcionConArgumentos dato estadoConUnDatoPasado)))]
    (not-every? false? listaEjecuciones)))

(defn expresion-valida-contemplando-past? [expresion dato estado]
  (if (hay-past? expresion)
    (expresion-valida-ciclando-por-datosPasados? expresion dato estado)
    (expresionValida? expresion dato estado)))
