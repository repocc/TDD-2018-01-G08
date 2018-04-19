(ns inicializar-test
  (:require
    [clojure.test :refer :all]
    [estado.inicializar :as inic]
    [estado.estado :as est]
    [setup.reglas-ejemplo :as reglasEjemplo]
    :reload-all)
  (:import [estado.estado Estado]))

(deftest test-cargarListaDeReglas-1
  (testing "Cargar lista de reglas usando un vector en lugar de una lista de reglas (expresadas en DSL corrrecto) no carga ninguna regla y devuelve un estado vacío."
    (testing "Usando un vector vacío."
      (is (empty? (:reglas (inic/cargarListaDeReglas [])))))))

(deftest test-cargarListaDeReglas-2
  (testing "Cargar lista de reglas usando un vector en lugar de una lista de reglas (expresadas en DSL corrrecto) no carga ninguna regla y devuelve un estado vacío."
    (testing "Usando un vector con una regla correcta."
      (is (empty? (:reglas (inic/cargarListaDeReglas [reglasEjemplo/reglaContadorLlamadoTotalSinParametrosYCondicionTrue])))))))

(deftest test-cargarListaDeReglas-3
  (testing "Cargar lista de reglas usando un vector en lugar de una lista de reglas (expresadas en DSL corrrecto) no carga ninguna regla y devuelve un estado vacío."
    (testing "Usando un vector con dos reglas correctas."
      (is (empty? (:reglas (inic/cargarListaDeReglas [
        reglasEjemplo/reglaContadorLlamadoTotalSinParametrosYCondicionTrue
        reglasEjemplo/reglaContadorLlamadoAcumSinParametrosYCondicionTrue])))))))





(deftest test-cargarUnaRegla-de-desconocido-con-estado-vacio-no-crea-ningun-mapa
  (let [estado (Estado. {} '())]
    (is (empty? (:reglas (est/cargarUnaRegla estado reglasEjemplo/reglaSemaforoLlamadoTotalSinParametrosYCondicionTrue))))))

(deftest test-agregarMapaDeReglasEspecificas-en-estado-vacio-agrega-un-solo-mapa
  (let [
    estado (Estado. {} '())
    clave 'clave]
    (is (=
      1
      (count (keys (:reglas (est/agregarMapaDeReglasEspecificas estado clave))))))))
