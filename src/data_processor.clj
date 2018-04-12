(ns data-processor
(require  [definiciones :refer :all])
(require  [procesamiento :refer :all])
(require  [funciones :refer :all]))

(defn process-data [state new-data]
  (procesar-datos state new-data)
)

(defn query-counter [state counter-name counter-args] 
  (consultar-contador state counter-name counter-args)
)

(defn initialize-processor [rules]
  (inicializar-proceso rules)
)
