;wrapper en inglés del archivo de la cátedra.
;funciones requeridas en este paquete:
;;(initialize-processor [reglas])
;;(process-data [estado datos])
;;(query-counter [estado contadorNombre contadorParametros])

(ns data-processor
  (:require
    [inicializar :refer :all] :reload-all))

(defn initialize-processor
  "Recibe una lista de reglas expresadas en el lenguage específico de dominio (DSL), cada una de las cuales se representa como una lista. Retorna el estado de un sistema recién creado."
  [reglas]
  (inicializar/procesarListaDeReglas reglas))

(defn query-counter
"TODO: add description."
  [state counter-name counter-args]
)

(defn process-data
  "TODO: add description."
  [state new-data]
)
