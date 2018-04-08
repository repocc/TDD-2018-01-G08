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

(defn check-requirements
  "TODO: add description."
  [reg rule-name rule-args]
  (and (= (reg :name) rule-name) (= (reg :args) rule-args)))

(defn query-counter
"TODO: add description."
  [state counter-name counter-args]
  (let [res (filter (fn [v] (check-requirements v counter-name counter-args)) state)]
    (if (empty? res)
      0
      ((first res) :value))))

(defn current
  "TODO: add description."
  [obj-type data]
  (not (empty? (filter (fn [[k v]] (and (= k obj-type) (= v true))) data))))

(defn analize-expr
  "TODO: add description."
  [expr data]
  (cond
    (= expr true) true
    (= (nth expr 0) 'current) (current (nth expr 1) data)))

(defn div
  "TODO: add description."
  [a b]
  (if (= b 0)
    0
    (/ a b)))

(defn apply-fun
  "TODO: add description."
  [status x st]
  (if (= (seq? st) true)
    (cond
      (= (first st) '/) (div (apply-fun status x (nth st 1)) (apply-fun status x (nth st 2)))
      (= (first st) 'counter-value) (query-counter status (nth st 1) (nth st 2))
      (= (first st) 'inc_counter) (inc (x :value))
      :else st)
    st))

(defn analize-new-data
  "TODO: add description."
  [v new-data state]
  (if (= (analize-expr (v :expr) new-data) true)
    (zipmap
      [:value :apply :function :name :args :expr]
      [(apply-fun state v (v :apply)) (v :apply) (v :function) (v :name) (v :args) (v :expr)])
    (zipmap
      [:value :apply :function :name :args :expr]
      [(v :value) (v :apply) (v :function) (v :name) (v :args) (v :expr)])))

(defn process-data
  "TODO: add description."
  [state new-data]
  (let [
    counters (map (fn [v] (analize-new-data v new-data state)) state)
    signals  (filter (fn [v] (=  'define-signal (v :function))) state)]
    (list
      counters
      (map (fn [v] (analize-new-data v new-data counters)) signals)
      ;signals
)))
