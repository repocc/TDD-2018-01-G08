(ns reglas.reglas-test
  (:require
    [clojure.test :refer :all]
    [reglas.reglas :as reg]
    [setup.reglas-ejemplo :as regEj]
    :reload-all))

(deftest test-formatoCorrectoListaDeReglasEnDSL?
  (testing "formatoCorrectoListaDeReglasEnDSL? devuelve"
    (testing "true"
      (testing "para una lista vacía."
        (let [listaDeReglasEnDSL '()]
          (is (true? (reg/formatoCorrectoListaDeReglasEnDSL? listaDeReglasEnDSL)))))
      (testing "para una lista con una regla."
        (let [listaDeReglasEnDSL '(regEj/reglaContadorLlamadoTotalSinParametrosYCondicionTrue)]
          (is (true? (reg/formatoCorrectoListaDeReglasEnDSL? listaDeReglasEnDSL)))))
      (testing "para una lista con dos reglas."
        (let [listaDeReglasEnDSL '(
          regEj/reglaContadorLlamadoTotalSinParametrosYCondicionTrue
          regEj/reglaContadorLlamadoAcumSinParametrosYCondicionTrue)]
          (is (true? (reg/formatoCorrectoListaDeReglasEnDSL? listaDeReglasEnDSL)))))
      (testing "para una lista con strings."
        (let [listaDeReglasEnDSL '("string1" "string2" "string3")]
          (is (true? (reg/formatoCorrectoListaDeReglasEnDSL? listaDeReglasEnDSL))))))
    (testing "false"
      (testing "para un vector."
        (let [listaDeReglasEnDSL []]
          (is (false? (reg/formatoCorrectoListaDeReglasEnDSL? listaDeReglasEnDSL)))))
      (testing "para un mapa."
        (let [listaDeReglasEnDSL {}]
          (is (false? (reg/formatoCorrectoListaDeReglasEnDSL? listaDeReglasEnDSL)))))
      (testing "para un set."
        (let [listaDeReglasEnDSL #{}]
          (is (false? (reg/formatoCorrectoListaDeReglasEnDSL? listaDeReglasEnDSL)))))
      (testing "para un número."
        (let [listaDeReglasEnDSL 4]
          (is (false? (reg/formatoCorrectoListaDeReglasEnDSL? listaDeReglasEnDSL))))))))

; TODO(Iván): Se cambió la forma de validar el formato, hay que re-hacer estos tests:
; (deftest test-formatoCorrectoUnaReglaEnDSL?
;   (testing "formatoCorrectoUnaReglaEnDSL? devuelve"
;     (testing "true"
;       (testing "para una regla contador."
;         (let [unaReglaEnDSL regEj/reglaContadorLlamadoTotalSinParametrosYCondicionTrue]
;           (is (true? (reg/formatoCorrectoUnaReglaEnDSL? unaReglaEnDSL))))
;       (testing "para una regla contadorPaso."
;         (let [unaReglaEnDSL regEj/reglaContadorPasoLlamadoTotalPaso11SinParametrosYCondicionTrue]
;           (is (true? (reg/formatoCorrectoUnaReglaEnDSL? unaReglaEnDSL))))
;       (testing "para una regla señal."
;         (let [unaReglaEnDSL regEj/reglaSenyalLlamadaTotalConFuncion1YCondicionTrue]
;           (is (true? (reg/formatoCorrectoUnaReglaEnDSL? unaReglaEnDSL))))))
;     (testing "false"
;       (testing "para una lista vacía."
;         (let [unaReglaEnDSL '()]
;           (is (false? (reg/formatoCorrectoUnaReglaEnDSL? unaReglaEnDSL)))))
;       (testing "para un vector vacío."
;         (let [unaReglaEnDSL []]
;           (is (false? (reg/formatoCorrectoUnaReglaEnDSL? unaReglaEnDSL)))))
;       (testing "para un mapa vacío."
;         (let [unaReglaEnDSL {}]
;           (is (false? (reg/formatoCorrectoUnaReglaEnDSL? unaReglaEnDSL)))))
;       (testing "para un vector con la estructura de una regla contador."
;         (let [unaReglaEnDSL (vector regEj/reglaContadorLlamadoTotalSinParametrosYCondicionTrue)]
;           (is (false? (reg/formatoCorrectoUnaReglaEnDSL? unaReglaEnDSL)))))
;
;       (testing "para un número."
;         (let [unaReglaEnDSL 4]
;           (is (false? (reg/formatoCorrectoUnaReglaEnDSL? unaReglaEnDSL))))))))))
