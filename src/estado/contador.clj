;funciones requeridas en este paquete:
;;(defmethod procesarUnaRegla 'define-counter [estado unContadorEnDSL])

(ns estado.contador
  (:require
    [inicializar-auxiliares :refer :all]
    [estado.estado :refer :all] :reload-all))

(defn agregarContador
  "Agrega al mapa de contadores el contador que se está procesando. Devuelve un nuevo estado con el contador agregado."
  [estado unContadorEnDSL]
  (assoc-in estado [':reglas 'define-counter (rest (butlast unContadorEnDSL))] (last unContadorEnDSL)))

;Multimétodo que matchea para las reglas que definen contadores, se fija si ya está creado el mapa de contadores (si no, entonces lo crea), y se fija si el contador específico que se está procesando ya había sido agregado (si no, entonces lo agrega al mapa de contadores). Devuelve un nuevo estado con el contador agregado.
(defmethod procesarUnaRegla
  'define-counter [estado unContadorEnDSL]
  (if (contains? (:reglas estado) 'define-counter) ;Si contiene el mapa de contadores...
    (if (contains? (:define-counter (:reglas estado)) (rest (butlast unContadorEnDSL))) ; Si contiene este contador en particular en el mapa de contadores.
      (estado) ;Ya hay un contador idéntico.
      (agregarContador estado unContadorEnDSL))
    (agregarContador (agregarMapaDeReglasEspecificas estado 'define-counter) unContadorEnDSL)))
