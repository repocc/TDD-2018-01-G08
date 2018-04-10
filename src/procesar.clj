(ns procesar
  (:require
    [estado.estado :refer :all]
    [definiciones :refer :all]
    [procesamiento :refer :all]
    [bool :refer :all]
    [string :refer :all]
    [number :refer :all] :reload-all))

(defn incrementarAcumuladorCorrespondiente
  "TODO(Iván): Agregar descripción."
  [estado contadorNombre parametrosEvaluados]
  TODO(Iván): ERROR: el 2 no funciona acá, no puede haber una lista en el medio de la navegacion que hacen los comandos "*-in".
  ;(update-in estado [':reglas 'define-counter contadorNombre 2 parametrosEvaluados] inc)
  (println "FOO")
  (println (get-in estado [':reglas 'define-counter contadorNombre]))
  (println "BAR")
)

(defn obtenerParametrosEvaluados
  "TODO(Iván): Agregar descripción."
  [estado parametros]
  (map (fn [parametro] (ejecutar-funcion parametro {} estado)) parametros) ;Me genero una lista con los valores de la evaluación de los parámetros del contador. Listas y vectores son intercambiables al usarlos como clave de un mapa.
)

(defn procesarParametrosDeUnaRegla
  "TODO(Iván): Agregar descripción."
  [estado contadorNombre contadorResto] ;"contadorResto" es una lista de tres elementos: vector de parámetros, condición, mapa de acumuladores.
  (if (every? true? (map (fn [parametro] (expresion-valida? parametro {} estado)) (first contadorResto)))
    (let [parametrosEvaluados (obtenerParametrosEvaluados estado (first contadorResto))]
      (incrementarAcumuladorCorrespondiente estado contadorNombre parametrosEvaluados)
    )
    estado))

(defn procesarUnaReglaContador
  "TODO(Iván): Agregar descripción."
  [estado contadorNombre contadorResto] ;"contadorResto" es una lista de tres elementos: vector de parámetros, condición, mapa de acumuladores.
  (if
    (and
      (expresion-valida? (nth contadorResto 1) {} estado)
      (ejecutar-funcion (nth contadorResto 1) {} estado)) ;"and" cortocircuita, así que no hay error acá. Si no es válida, no la evalúa.
    (procesarParametrosDeUnaRegla estado contadorNombre contadorResto) ;Evaluar parámetros e incrementar acumulador correspondiente.
    estado)
)

(defn procesar
  "TODO(Iván): Agregar descripción."
  [estado datos]
  ;TODO(Iván): Obtengo todas las señales, y genero todos los resultados y los adjunto al estado.
  (let [mapaDeReglas (get (:reglas estado) 'define-counter)] ;El mapa de "nombre de regla" a "resto de la regla".
    (reduce-kv procesarUnaReglaContador estado mapaDeReglas) ;"estado" se va transformando en el nuevo estado actualizado con el reduce."
  )
)
