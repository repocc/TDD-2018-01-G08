(ns inicializar-auxiliares-test
  (:require
    [clojure.test :refer :all]
    [inicializar-auxiliares :refer :all]
    [estado.estado :refer :all] :reload-all)
  (:import [estado.estado Estado]))

(deftest test-agregarMapaDeReglasEspecificas-en-estado-vacio-agrega-un-solo-mapa
  (let [
    estado (Estado. {})
    clave 'clave]
    (is (=
      1
      (count (keys (:reglas (agregarMapaDeReglasEspecificas estado clave))))))))
