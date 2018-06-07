;Funciones requeridas de este archivo:
;;(defmethod cargarUnaRegla 'define-signal [estado unaSenyalEnDSL])
; DSL:
; (define-signal
;   nombreSeñalYResultado <mapa>{
;     clave: nombreSeñal<string>
;     valor: resultado<tipo básico o lista ejecutable>
;   }
;   condición             <bool o lista ejecutable>
; )

(ns com.ar.fiuba.tdd.clojure.reglas.senyal
  (:require
    [com.ar.fiuba.tdd.clojure.funcionesGenerales :refer :all]
    [com.ar.fiuba.tdd.clojure.estado.estado :as est] :reload-all))

(defn expresion?
  [expresion]
  (or
    (boolean? expresion)
    (list? expresion)))

(defn mapaDeFuncionesTieneFormatoCorrectoEnDSL?
  [resultados]
  (let [
    nombres    (keys resultados)
    resultados (vals resultados)]
    (and
      (every? true? (map string? nombres))
      (every? true? (map expresion? resultados)))))

(defn formatoCorrectoUnaReglaSenyalEnDSL?
  "Comprueba si una regla señal tiene el formato DSL correcto."
  [unaReglaSenyalEnDSL]
  (if (= 3 (count unaReglaSenyalEnDSL))
    (let [
      simbolo    (nth unaReglaSenyalEnDSL 0)
      resultados (nth unaReglaSenyalEnDSL 1)
      condicion  (nth unaReglaSenyalEnDSL 2)]
      (and
        (= simbolo 'define-signal)
        (map? resultados)
        (mapaDeFuncionesTieneFormatoCorrectoEnDSL? resultados)
        (or
          (boolean? condicion)
          (list? condicion))))
    false))

(defn agregarSenyal
  "Agrega al mapa de senyales la senyal que se está procesando. Devuelve un nuevo estado con la senyal agregada. No se permiten señales con nombres repetidos, las agregaciones de repetidos posteriores pisan a las anteriores."
  [estado unaSenyalEnDSL]
  (assoc-in estado
    [':reglas 'define-signal (first (keys (first (rest unaSenyalEnDSL))))]
    {
      :formula   (first (vals (first (rest unaSenyalEnDSL)))) ;La fórmula de la señal (tipo básico o lista ejecutable).
      :condicion (last unaSenyalEnDSL)})) ;La condición de la señal (tipo básico o lista ejecutable).

;Multimétodo que matchea para las reglas que definen senyales, se fija si ya está creado el mapa de senyales (si no, entonces lo crea), y se fija si la senyal específica que se está procesando ya había sido agregada (si no, entonces la agrega al mapa de senyales). Devuelve un nuevo estado con la senyal agregada.
(defmethod est/cargarUnaRegla
  'define-signal [estado unaSenyalEnDSL]
  (if (contains? (:reglas estado) 'define-signal) ;Si contiene el mapa de señales...
    (if (contains? (:define-signal (:reglas estado)) (first (keys (first (rest unaSenyalEnDSL))))) ; Si contiene esta señal en particular en el mapa de señales.
      (estado) ;Ya hay una señal idéntica.
      (agregarSenyal estado unaSenyalEnDSL))
    (agregarSenyal (est/agregarMapaDeReglasEspecificas estado 'define-signal) unaSenyalEnDSL)))
