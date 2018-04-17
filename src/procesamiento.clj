(ns procesamiento
  (require  [definiciones :refer :all])
  (require  [funciones :refer :all])
  (require  [funciones_globales :refer :all])
  (require  [aux :refer :all])
  (require  [tipos.tipos :refer :all])
  (require  [estado_auxiliar :as est_aux]))

(defn obtener-ceros [argumentos dato estado]
  ;(filter (fn [argumento] (= argumento 0)) argumentos)
  (filter (fn [argumento]

              (if(seq? argumento)
                (if (funcion? (first argumento))
                  (= (ejecutar-funcion argumento dato estado) 0))
                (= argumento 0)
              )) argumentos)
)

(defn validar-no-existencia-cero [funcion argumentos dato estado]
  (let [
          divisores        (drop 1 argumentos)
          argumentos-ceros (obtener-ceros divisores dato estado)
          existen-ceros    (empty? argumentos-ceros)
        ]
    existen-ceros
  )
)


; Para que una expresion sea valida, deben ser validas todas sus sub-expresiones.
; Se deben cumplir cada una de las precondiciones.
(defn expresion-valida? [funcion-con-argumentos dato estado]
  ;Verifico si no se trata de un valor de un tipo basico
  (if (not (seq? funcion-con-argumentos))
    (if (tipo-basico? funcion-con-argumentos)
      true
      false
    )
    (let [
          funcion     (obtener-nombre-funcion funcion-con-argumentos)
          argumentos  (obtener-argumentos funcion-con-argumentos)
        ]
    (if (= (funcion? funcion) true)
      (if(= (precondiciones-validas? funcion argumentos dato estado) true)
        true
        false
      )
    )
  )
 )
)

; En el caso que no sea un tipo basico, es decir, otra expresion. Esta es analizada.
(defmethod implementa-funcion? :default [funcion argumento dato estado]
  (expresion-valida? argumento dato estado)
)

(defn ejecutar-funcion-ciclando-por-datosPasados
  "TODO(Iván): agregar descripción."
  [funcion-con-args dato estado]
  (let [
    datosPasadosLista (est_aux/obtenerDatosPasados estado)
    listaEjecuciones
      (for
        [unDatoPasado datosPasadosLista]
        (let [estadoConUnDatoPasado (assoc estado :datosPasados (list unDatoPasado))]
          (if (expresion-valida? funcion-con-args dato estadoConUnDatoPasado) ;TODO: (1)Probablemente esta consulta acá esté de más, se debería llamar "expresion-valida?" antes de llamar "ejecutar-funcion"...(2) Si, ya se llamó, pero la lista de datosPsados no se filtra, se provee completa, así que si no filtrás en este momento, vas a estar llamando a la funcion past con un con datos pasados que no tiene ese campo.
            (ejecutar-funcion funcion-con-args dato estadoConUnDatoPasado)
            false)))]
    (not-every? false? listaEjecuciones)))

(defn hay-past?
  "TODO(Iván): agregar descripción."
  [funcion-con-args]
  (if (seq? funcion-con-args)
    (if (= (first funcion-con-args) 'past)
      true
      (let [hayPast (not-every? false? (map hay-past? (rest funcion-con-args)))]
        hayPast))
    false))

(defn ejecutar-funcion-contemplando-past
  ;TODO (Iván) Este defn lo tuve que poner acá porque es el mejor lugar que encontré sin tener que poner otro require en procesar.clj.
  "TODO(Iván): agregar descripción."
  [funcion-con-args dato estado]
  ;Si hay algun past en funcion-con-args, entonces sacar todos los datosPasados de estado, y hacer un foreach de cada dato metiendo ese dato en el historial de estado, y llamando al ejecutar-funcion comun. El foreach se corta cuando da true la evaluacion o se acaban los datosPasados.
  (if (hay-past? funcion-con-args)
    (ejecutar-funcion-ciclando-por-datosPasados funcion-con-args dato estado)
    (ejecutar-funcion funcion-con-args dato estado)))

(defn expresion-valida-ciclando-por-datosPasados?
  "TODO(Iván): agregar descripción."
  [funcion-con-args dato estado]
  (let [
    datosPasadosLista (est_aux/obtenerDatosPasados estado)
    listaEjecuciones
      (for
        [unDatoPasado datosPasadosLista]
        (let [estadoConUnDatoPasado (assoc estado :datosPasados (list unDatoPasado))]
          (expresion-valida? funcion-con-args dato estadoConUnDatoPasado)))]
    (not-every? false? listaEjecuciones)))

(defn expresion-valida-contemplando-past? [expresion dato estado]
  (if (hay-past? expresion)
    (expresion-valida-ciclando-por-datosPasados? expresion dato estado)
    (expresion-valida? expresion dato estado)))
