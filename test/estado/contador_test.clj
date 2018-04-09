(ns estado.contador-test
  (:require
    [clojure.test :refer :all]
    [estado.contador :refer :all]
    [estado.estado :refer :all]
    [setup.reglas-ejemplo :as reglasEjemplo] :reload-all)
  (:import [estado.estado Estado]))

(deftest test-agregarContador-en-un-estado-sin-ningun-contador-agrega-un-contador-en-el-mapa-de-contadores
  (let [estado (assoc-in (Estado. {}) [':reglas 'define-counter] {})]
    (is (=
      1
      (count (keys (get
        (:reglas (agregarContador estado reglasEjemplo/reglaContadorLlamadoTotalSinParametrosYCondicionTrue))
        'define-counter)))))))

(deftest test-agregarContador-en-un-estado-sin-ningun-contador-agrega-un-contador-con-el-nombre-y-la-lista-de-parametros-como-clave
  (let [estado (assoc-in (Estado. {}) [':reglas 'define-counter] {})]
    (is (=
      (keys (get
        (:reglas (agregarContador estado reglasEjemplo/reglaContadorLlamadoTotalSinParametrosYCondicionTrue))
        'define-counter))
      (list (rest (butlast reglasEjemplo/reglaContadorLlamadoTotalSinParametrosYCondicionTrue)))))))

(deftest test-agregarContador-en-un-estado-sin-ningun-contador-agrega-un-contador-con-una-lista-de-la-condicion-y-un-mapa-vacio-de-acumuladores-como-valor
  (let [estado (assoc-in (Estado. {}) [':reglas 'define-counter] {})]
    (is (=
      (vals (get
        (:reglas (agregarContador estado reglasEjemplo/reglaContadorLlamadoTotalSinParametrosYCondicionTrue))
        'define-counter))
      (list (list (last reglasEjemplo/reglaContadorLlamadoTotalSinParametrosYCondicionTrue) {}))))))

(deftest test-procesarUnaRegla-de-contador-con-estado-vacio-crea-el-mapa-de-contadores
  (let [estado (Estado. {})]
    (is (not (empty? (:reglas (procesarUnaRegla estado reglasEjemplo/reglaContadorLlamadoTotalSinParametrosYCondicionTrue)))))))

(deftest test-procesarUnaRegla-de-contador-con-estado-vacio-crea-el-mapa-de-contadores-con-nombre-define-counter
  (let [estado (Estado. {})]
    (is (= 'define-counter (first (keys (:reglas (procesarUnaRegla estado reglasEjemplo/reglaContadorLlamadoTotalSinParametrosYCondicionTrue))))))))

(deftest test-procesarUnaRegla-de-contador-con-estado-vacio-crea-un-solo-mapa-de-reglas-especificas
  (let [estado (Estado. {})]
    (is (= 1 (count (keys (:reglas (procesarUnaRegla estado reglasEjemplo/reglaContadorLlamadoTotalSinParametrosYCondicionTrue))))))))
