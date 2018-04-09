(ns inicializar-test
  (:require
    [clojure.test :refer :all]
    [inicializar :refer :all]
    [estado.estado :refer :all]
    [setup.reglas-ejemplo :as reglasEjemplo] :reload-all)
  (:import [estado.estado Estado]))

(deftest test-procesarUnaRegla-de-desconocido-con-estado-vacio-no-crea-ningun-mapa
  (let [estado (Estado. {})]
    (is (empty? (:reglas (procesarUnaRegla estado reglasEjemplo/reglaSemaforoLlamadoTotalSinParametrosYCondicionTrue))))))

;TODO(Iván): Test procesarListaDeReglas.
