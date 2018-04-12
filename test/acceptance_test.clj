(ns acceptance-test
  (:require
    [clojure.test :refer :all]
    [data-processor :refer :all] :reload-all))

;Defines a list of rules (using the Domain Specific Language) for testing the data.
(def rules '(
  (define-counter
    "email-count" ;Counts every processed piece of data.
    [] ;Only one value will be stored for this counter, because it has no parameters.
    true) ;Increments it's value with every piece of data.
  (define-counter
    "spam-count" ;Counts only pieces of data with a key called "spam".
    []
    (current "spam")) ;Only increments it's value if the piece of data being currently processed has a key called "spam".
  (define-signal
    {"spam-fraction" ;Signals the ratio of counter "spam-count" to counter "email-count".
      (/
        (counter-value "spam-count" []) ;Present value of counter "spam-count" (before processing this piece of data).
        (counter-value "email-count" []))} ;Present value of counter "email-count" (before processing this piece of data).
    true) ;Signals with every piece of data.
  (define-counter
    "spam-important-table" ;Counts only pieces of data with a key called "spam".
    [(current "spam") (current "important")] ;Since "current returns a boolean, 4 values will be stored for this counter (True-False; F-T, T-T, and F-F)".
    true)))

(defn process-data-dropping-signals
  [state new-data]
  (first (process-data state new-data)))


;Counter "spam" must have value = 0 right after processor initialization.
(deftest initial-state-test
  (testing "Query counter from initial state"
    (is (= 0 (query-counter (initialize-processor rules) "spam" []))))) ;Probably the intended counter name was "spam-count", counter "spam" wasn't defined in "rules".


(deftest unconditional-counter-test
  (testing "Counter \"email-count\" has no parameters, so it should increment it's only value every time the condition is met."
  (let [
      st0 (initialize-processor rules)
      st1 (process-data-dropping-signals st0 {"spam" true})
      st2 (process-data-dropping-signals st1 {"spam" true})]
    (is (= 2 (query-counter st2 "email-count" []))))))

(deftest conditional-counter-test
  (testing "Count incoming data by current condition"
    (testing "when repeated"
      (let [
          st0 (initialize-processor rules)
          st1 (process-data-dropping-signals st0 {"spam" true})
          st2 (process-data-dropping-signals st1 {"spam" true})
          st3 (process-data-dropping-signals st2 {"spam" true})]
        (is (= 3 (query-counter st3 "spam-count" [])))))
    (testing "when ignored field varies"
      (let [
          st0 (initialize-processor rules)
          st1 (process-data-dropping-signals st0 {"spam" true, "noise" 1})
          st2 (process-data-dropping-signals st1 {"spam" true, "noise" 2})
          st3 (process-data-dropping-signals st2 {"spam" true, "noise" 3})]
        (is (= 3 (query-counter st3 "spam-count" [])))))
    (testing "when considered field varies"
      (let [
          st0 (initialize-processor rules)
          st1 (process-data-dropping-signals st0 {"spam" true})
          st2 (process-data-dropping-signals st1 {"spam" false})
          st3 (process-data-dropping-signals st2 {"spam" true})]
        (is (= 2 (query-counter st3 "spam-count" []))))))) ;Considered field varies but it is not a parameter of the counter, and the querying function is called with no parameters, so there's no counting of "non-spam emails" and only one value to return.

;TODO: (contingency-table-counter-test) is failing all 4 asserts.
(deftest contingency-table-counter-test
  (let [
      st0       (initialize-processor rules)
      st1       (process-data-dropping-signals st0 {"spam" true, "important" true})
      st2       (process-data-dropping-signals st1 {"spam" true, "important" false})
      st3       (process-data-dropping-signals st2 {"spam" true, "important" false})
      st4       (process-data-dropping-signals st3 {"spam" false, "important" true})
      st5       (process-data-dropping-signals st4 {"spam" false, "important" true})
      st6       (process-data-dropping-signals st5 {"spam" false, "important" true})
      st7       (process-data-dropping-signals st6 {"spam" false, "important" false})
      st8       (process-data-dropping-signals st7 {"spam" false, "important" false})
      st9       (process-data-dropping-signals st8 {"spam" false, "important" false})
      end-state (process-data-dropping-signals st9 {"spam" false, "important" false})]
   (is (= 1 (query-counter end-state "spam-important-table" [true true]))) ;TODO: fallaba antes de refactor.
   (is (= 2 (query-counter end-state "spam-important-table" [true false]))) ;TODO: fallaba antes de refactor.
   (is (= 3 (query-counter end-state "spam-important-table" [false true]))) ;TODO: fallaba antes de refactor.
   (is (= 4 (query-counter end-state "spam-important-table" [false false]))) ;TODO: fallaba antes de refactor.
))

;TODO: (signal-skip-on-error-test) is failing.
(deftest signal-skip-on-error-test
  (let [
      st0       (initialize-processor rules)
      [st1 sg1] (process-data st0 {})]
    (is (= '() sg1)) ;TODO: fallaba antes de refactor.
))

;TODO: (signal-launch-test) is failing and erroring.
(deftest signal-launch-test
  (let [
      st0       (initialize-processor rules)
      [st1 sg1] (process-data st0 {"spam" true})
      [st2 sg2] (process-data st1 {"spam" false})
      [st3 sg3] (process-data st2 {})]
    (is (= 0 (count sg1))) ;TODO: fallaba antes de refactor.
    (is (= 1 (count sg2)))
    (is (= 1 (get (first sg2) "spam-fraction"))) ;TODO: fallaba antes de refactor.
    (is (= 1 (count sg3)))
    (is (<
      0.49 (get (first sg3) "spam-fraction")
      0.51)) ;TODO: erroreaba antes de refactor.
))

;TODO: (past-value-test) is failing all 5 asserts.
(deftest past-value-test
  (let [
      st0
        (initialize-processor '(
          (define-signal
            {"repeated"
            (current "value")}
            (=
              (current "value")
              (past "value")))))
            [st1 sg1] (process-data st0 {"value" 1})
      [st2 sg2] (process-data st1 {"value" 2})
      [st3 sg3] (process-data st2 {"value" 1})
      [st4 sg4] (process-data st3 {"value" 1})
      [st5 sg5] (process-data st4 {"value" 2})]
    (is (= 0 (count sg1))) ;TODO: fallaba antes de refactor.
    (is (= 0 (count sg2))) ;TODO: fallaba antes de refactor.
    (is (=
      '({"repeated" 1})
      sg3)) ;TODO: fallaba antes de refactor.
    (is (=
      '({"repeated" 1})
      sg4)) ;TODO: fallaba antes de refactor.
    (is (=
      '({"repeated" 2})
      sg5)) ;TODO: fallaba antes de refactor.
))

