; funciones requeridas en este paquete:
;; (defmethod procesarUnaRegla 'define-signal [estado unaSenyalEnDSL])
; DSL:
; (define-signal
;   nombreSeñalYResultado <mapa>{
;     clave: nombreSeñal<string>
;     valor: resultado<tipo básico o lista ejecutable>
;   }
;   condición             <booleano o lista ejecutable>
; )

(ns estado.senyal
  (:require
    [estado :refer :all] :reload-all))

(defn agregarSenyal
  "Agrega al mapa de senyales la senyal que se está procesando. Devuelve un nuevo estado con la senyal agregada. No se permiten señales con nombres repetidos, las agregaciones de repetidos posteriores pisan a las anteriores."
  [estado unaSenyalEnDSL]
  (assoc-in estado
    [':reglas 'define-signal (first (keys (first (rest unaSenyalEnDSL))))]
    {
      :formula   (first (vals (first (rest unaSenyalEnDSL)))) ;La fórmula de la señal (tipo básico o lista ejecutable).
      :condicion (last unaSenyalEnDSL)})) ;La condición de la señal (tipo básico o lista ejecutable).

;Multimétodo que matchea para las reglas que definen senyales, se fija si ya está creado el mapa de senyales (si no, entonces lo crea), y se fija si la senyal específica que se está procesando ya había sido agregada (si no, entonces la agrega al mapa de senyales). Devuelve un nuevo estado con la senyal agregada.
(defmethod procesarUnaRegla
  'define-signal [estado unaSenyalEnDSL]
  (if (contains? (:reglas estado) 'define-signal) ;Si contiene el mapa de señales...
    (if (contains? (:define-signal (:reglas estado)) (first (keys (first (rest unaSenyalEnDSL))))) ; Si contiene esta señal en particular en el mapa de señales.
      (estado) ;Ya hay una señal idéntica.
      (agregarSenyal estado unaSenyalEnDSL))
    (agregarSenyal (agregarMapaDeReglasEspecificas estado 'define-signal) unaSenyalEnDSL)))
