;funciones requeridas en este paquete:
;;(defmethod procesarUnaRegla 'define-signal [estado unaSenyalEnDSL])

(ns estado.senyal
  (:require
    [inicializar-auxiliares :refer :all]
    [estado.estado :refer :all] :reload-all))

(defn agregarSenyal
  "Agrega al mapa de senyales la senyal que se está procesando. Devuelve un nuevo estado con la senyal agregada."
  [estado unaSenyalEnDSL]
  ;TODO (Iván): cambiar por el de señal:
  ;(assoc-in estado [':reglas 'define-counter (rest (butlast unContadorEnDSL))] (last unContadorEnDSL)))
)

;Multimétodo que matchea para las reglas que definen senyales, se fija si ya está creado el mapa de senyales (si no, entonces lo crea), y se fija si la senyal específica que se está procesando ya había sido agregada (si no, entonces la agrega al mapa de senyales). Devuelve un nuevo estado con la senyal agregada.
(defmethod procesarUnaRegla
  'define-signal [estado unaSenyalEnDSL]
  TODO (Iván): Cambiar por lo de senyales.
  ; (if (contains? (:reglas estado) 'define-counter) ;Si contiene el mapa de contadores...
  ;   (if (contains? (:define-counter (:reglas estado)) (rest (butlast unContadorEnDSL))) ; Si contiene este contador en particular en el mapa de contadores.
  ;     (estado) ;Ya hay un contador idéntico.
  ;     (agregarContador estado unContadorEnDSL))
  ;   (agregarContador (agregarMapaDeReglasEspecificas estado 'define-counter) unContadorEnDSL)))
)
