;funciones requeridas en este paquete:
;;(defmethod procesarUnaRegla 'define-signal [estado unaSenyalEnDSL])
;DSL:
;(define-signal   <mapa>{<string> -> <símbolo o lista>fórmulaSeñal}   <símbolo o lista>fórmulaCondición)

(ns estado.senyal
  (:require
    [inicializar-auxiliares :refer :all]
    [estado.estado :refer :all] :reload-all))

(defn agregarSenyal
  "Agrega al mapa de senyales la senyal que se está procesando. Devuelve un nuevo estado con la senyal agregada."
  [estado unaSenyalEnDSL]
  (assoc-in estado
    [':reglas 'define-signal (first (keys (first (rest unaSenyalEnDSL))))]
    (list (first (vals (first (rest unaSenyalEnDSL)))) (last unaSenyalEnDSL))))

;Multimétodo que matchea para las reglas que definen senyales, se fija si ya está creado el mapa de senyales (si no, entonces lo crea), y se fija si la senyal específica que se está procesando ya había sido agregada (si no, entonces la agrega al mapa de senyales). Devuelve un nuevo estado con la senyal agregada.
(defmethod procesarUnaRegla
  'define-signal [estado unaSenyalEnDSL]
  (if (contains? (:reglas estado) 'define-signal) ;Si contiene el mapa de contadores...
    (if (contains? (:define-signal (:reglas estado)) (first (keys (first (rest unaSenyalEnDSL)))))) ; Si contiene esta señal en particular en el mapa de señales.
      (estado) ;Ya hay una señal idéntica.
      (agregarSenyal estado unaSenyalEnDSL))
    (agregarSenyal (agregarMapaDeReglasEspecificas estado 'define-signal) unaSenyalEnDSL))))
