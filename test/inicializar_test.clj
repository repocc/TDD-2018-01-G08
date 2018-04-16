(ns inicializar-test
  (:require
    [clojure.test :refer :all]
    [inicializar :refer :all]
    [estado :refer :all]
    [setup.reglas-ejemplo :as reglasEjemplo] :reload-all)
  (:import [estado Estado]))

(deftest test-procesarUnaRegla-de-desconocido-con-estado-vacio-no-crea-ningun-mapa
  (let [estado (Estado. {} '())]
    (is (empty? (:reglas (procesarUnaRegla estado reglasEjemplo/reglaSemaforoLlamadoTotalSinParametrosYCondicionTrue))))))

(deftest test-agregarMapaDeReglasEspecificas-en-estado-vacio-agrega-un-solo-mapa
  (let [
    estado (Estado. {} '())
    clave 'clave]
    (is (=
      1
      (count (keys (:reglas (agregarMapaDeReglasEspecificas estado clave))))))))

;TODO(Iván): Test procesarListaDeReglas.
