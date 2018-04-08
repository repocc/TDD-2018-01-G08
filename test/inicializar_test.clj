(ns inicializar-test
  (:require
    [clojure.test :refer :all]
    [inicializar :refer :all]
    [estado.estado :refer :all]
    [setup.reglas-ejemplo :as reglasEjemplo] :reload-all)
  (:import [estado.estado Estado]))

(deftest test-procesarUnaRegla-de-contador-con-estado-vacio-crea-el-mapa-de-contadores
  (let [estado (Estado. {})]
    (is (not (empty? (:reglas (procesarUnaRegla estado reglasEjemplo/reglaContadorLlamadoTotalSinParametrosYCondicionTrue)))))))

(deftest test-procesarUnaRegla-de-contador-con-estado-vacio-crea-el-mapa-de-contadores-con-nombre-define-counter
  (let [estado (Estado. {})]
    (is (= 'define-counter (first (keys (:reglas (procesarUnaRegla estado reglasEjemplo/reglaContadorLlamadoTotalSinParametrosYCondicionTrue))))))))

(deftest test-procesarUnaRegla-de-contador-con-estado-vacio-crea-un-solo-mapa-de-reglas-especificas
  (let [estado (Estado. {})]
    (is (= 1 (count (keys (:reglas (procesarUnaRegla estado reglasEjemplo/reglaContadorLlamadoTotalSinParametrosYCondicionTrue))))))))

(deftest test-procesarUnaRegla-de-desconocido-con-estado-vacio-no-crea-ningun-mapa
  (let [estado (Estado. {})]
    (is (empty? (:reglas (procesarUnaRegla estado reglasEjemplo/reglaSemaforoLlamadoTotalSinParametrosYCondicionTrue))))))
