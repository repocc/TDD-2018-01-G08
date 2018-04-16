; funciones requeridas en este paquete:
;; (defmethod procesarUnaRegla 'define-step-counter [estado unContadorPasoEnDSL])
; DSL:
; (define-step-counter
;   nombre     <string>
;    paso       <number>
;   parámetros <vector de un tipo básico o listas ejecutables>
;   condición  <booleano o lista ejecutable>
; )

(ns estado.contadorPaso
  (:require
    [estado :refer :all] :reload-all))

(defn agregarContadorPaso
  "Agrega al mapa de contadores el contador que se está procesando. Devuelve un nuevo estado con el contador agregado. No se permiten contadores con nombres repetidos, las agregaciones de repetidos posteriores pisan a las anteriores."
  [estado unContadorPasoEnDSL]
  (assoc-in
    estado
    [':reglas 'define-counter (nth unContadorPasoEnDSL 1)] ;Path de claves: la última es el string "nombre" del contadorPaso.
    {
      :paso         (nth unContadorPasoEnDSL 2)
      :parametros   (nth unContadorPasoEnDSL 3) ;El vector de parámetros del contadorPaso.
      :condicion    (last unContadorPasoEnDSL) ;La condición del contadorPaso.
      :acumuladores {}})) ;Un mapa de acumuladores vacío (usarán un vector de parámetros (evaluados) como clave, proveyendo distintas claves para un mismo contador si los parámetros al evaluarse producen distintos valores de tipos básicos).

; Multimétodo que matchea para las reglas que definen contadoresPaso, se fija si ya está creado el mapa de contadoresPaso (si no, entonces lo crea), y se fija si el contadorPaso específico que se está procesando ya había sido agregado (si no, entonces lo agrega al mapa de contadoresPaso). Devuelve un nuevo estado con el contadorPaso agregado.
(defmethod procesarUnaRegla
  'define-step-counter [estado unContadorPasoEnDSL]
  (if (contains? (:reglas estado) 'define-counter) ;Si contiene el mapa de contadoresPaso...
    (if (contains? (:define-counter (:reglas estado)) (nth unContadorPasoEnDSL 1)) ;Si contiene este contadorPaso en particular en el mapa de contadores.
      (estado) ;Ya hay un contadorPaso idéntico.
      (agregarContadorPaso estado unContadorPasoEnDSL))
    (agregarContadorPaso (agregarMapaDeReglasEspecificas estado 'define-counter) unContadorPasoEnDSL)))
