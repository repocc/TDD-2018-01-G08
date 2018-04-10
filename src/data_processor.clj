;wrapper en inglés del archivo de la cátedra.
;funciones requeridas en este paquete:
;;(process-data [estado datos])
;;(query-counter [estado contadorNombre contadorParametros])
;;(initialize-processor [listaDeReglasEnDSL])


(ns data-processor
  (:require
    [inicializar :refer :all]
    [estado.estado :refer :all] :reload-all))

(defn process-data
  "TODO(Iván): add description."
  [estado datos]
  ;TODO(Iván)
  ; Obtengo todas las señales, y genero todos los resultados y los adjunto al estado.
  ; Obtengo todos los contadores, y genero todos los incrementos "en" el estado.
  ()
)

(defn query-counter
  "TODO(Iván): add description."
  [estado contadorNombre contadorParametros]
  (estado.estado/consultarContador estado contadorNombre contadorParametros))

(defn initialize-processor
  "Recibe una lista de reglas expresadas en el lenguage específico de dominio (DSL), cada una de las cuales se representa como una lista. Retorna el estado de un sistema recién creado."
  [listaDeReglasEnDSL]
  (inicializar/procesarListaDeReglas listaDeReglasEnDSL))
