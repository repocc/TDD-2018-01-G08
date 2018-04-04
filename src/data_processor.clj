(ns data-processor)

(defn get-format [x]
   (cond 
      (= (first x) 'define-counter) (zipmap [:value :apply :function :name :args :expr] (conj x '(inc_counter) '0))
      (= (first x) 'define-signal)  (zipmap [:value :apply :function :name :args :expr] ['0 (first (vals (nth x 1))) (nth x 0) (first (keys (nth x 1))) [] (nth x 2)])
    )
)

(defn get-counters [rules]
   (map (fn [v] (get-format v)) rules) 
)

(defn initialize-processor [rules]
   (get-counters rules) 
)

(defn check-requirements [reg rule-name rule-args]
  (and (= (reg :name) rule-name) (= (reg :args) rule-args))
)

(defn query-counter [state counter-name counter-args] 
   (let [res (filter (fn [v] (check-requirements v counter-name counter-args)) state)]
        (if (empty? res)
          0
          ((first res) :value)  
        )
   )
)

(defn current [obj-type data] 
   (not (empty? (filter (fn [[k v]] (and (= k obj-type) (= v true))) data)))
)

(defn analize-expr [expr data] 
   (cond
      (= expr true) true
      (= (nth expr 0) 'current) (current (nth expr 1) data)
   )
)

(defn div [a b]
  (if (= b 0)
      0
      (/ a b)
  )
)

(defn apply-fun [status x st]
   (if (= (seq? st) true)
    (cond 
      (= (first st) '/) (div (apply-fun status x (nth st 1)) (apply-fun status x (nth st 2)))
      (= (first st) 'counter-value) (query-counter status (nth st 1) (nth st 2))
      (= (first st) 'inc_counter) (inc (x :value))
      :else st
    )
    st
  )
)

(defn analize-new-data [v new-data state]
  (if (= (analize-expr (v :expr) new-data) true)
    (zipmap [:value :apply :function :name :args :expr] [(apply-fun state v (v :apply)) (v :apply) (v :function) (v :name) (v :args) (v :expr)]) 
    (zipmap [:value :apply :function :name :args :expr] [(v :value) (v :apply) (v :function) (v :name) (v :args) (v :expr)]) 
  )
)

(defn process-data [state new-data]
  (let
       [ 
         counters (map (fn [v] (analize-new-data v new-data state)) state)
         signals  (filter (fn [v] (=  'define-signal (v :function))) state)
       ]
       (list 
          counters
          (map (fn [v] (analize-new-data v new-data counters)) signals)
          ;signals
        )
  )
)
