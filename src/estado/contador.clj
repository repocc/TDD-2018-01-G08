; funciones requeridas en este paquete:
;; (defmethod procesarUnaRegla 'define-counter [estado unContadorEnDSL])
; DSL:
; (define-counter
;   nombre     <string>
;   parámetros <vector de tipos básicos o listas ejecutables>
;   condición  <booleano o lista ejecutable>
; )

(ns estado.contador
  (:require
    [inicializar-auxiliares :refer :all]
    [estado.estado :refer :all] :reload-all))

(defn agregarContador
  "Agrega al mapa de contadores el contador que se está procesando. Devuelve un nuevo estado con el contador agregado."
  [estado unContadorEnDSL]
  (assoc-in
    estado
    [':reglas 'define-counter (nth unContadorEnDSL 1)] ;Path de claves: la última es el string "nombre" del contador.
    (list
      (nth unContadorEnDSL 2) ;El vector de parámetros del contador.
      (last unContadorEnDSL) ;La condición del contador.
      {}))) ;Un mapa de acumuladores vacío (usarán un vector de parámetros (evaluados) como clave, proveyendo distintas claves para un mismo contador si los parámetros al evaluarse producen distintos valores de tipos básicos).

; Multimétodo que matchea para las reglas que definen contadores, se fija si ya está creado el mapa de contadores (si no, entonces lo crea), y se fija si el contador específico que se está procesando ya había sido agregado (si no, entonces lo agrega al mapa de contadores). Devuelve un nuevo estado con el contador agregado.
(defmethod procesarUnaRegla
  'define-counter [estado unContadorEnDSL]
  (if (contains? (:reglas estado) 'define-counter) ;Si contiene el mapa de contadores...
    (if (contains? (:define-counter (:reglas estado)) (nth unContadorEnDSL 1)) ;Si contiene este contador en particular en el mapa de contadores.
      (estado) ;Ya hay un contador idéntico.
      (agregarContador estado unContadorEnDSL))
    (agregarContador (agregarMapaDeReglasEspecificas estado 'define-counter) unContadorEnDSL)))
