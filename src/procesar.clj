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
  (update-in
    estado
    [:reglas 'define-counter contadorNombre :acumuladores parametrosEvaluados] ;Llego hasta el acumulador específico para esta combinación de parámetros.
    (fn
      [acumulador]
      (if (nil? acumulador) ;Cuando el acumulador es cero, es porque nunca se había dado esta combinación de parámetros, por lo cual la clave no existe en el mapa (porque nunca fue incrementada antes y no se cargan en cero al principio, se espera a que haya que incrementarlas para meter la combinación de parámetros al mapa) y retorna "nil".
        1
        (inc acumulador)))))

(defn obtenerParametrosEvaluados
  "TODO(Iván): Agregar descripción."
  [estado dato parametros]
  (map (fn [parametro] (ejecutar-funcion parametro dato estado)) parametros) ;Me genero una lista con los valores de la evaluación de los parámetros del contador. Listas y vectores son intercambiables al usarlos como clave de un mapa.
)

(defn procesarParametrosDeUnaRegla
  "TODO(Iván): Agregar descripción."
  [estado dato contadorNombre contadorResto] ;"contadorResto" es un mapa de tres elementos: :parametros, :condicion, :acumuladores.
  (if (every? true? (map (fn [parametro] (expresion-valida? parametro {} estado)) (contadorResto :parametros)))
    (let [
      parametrosEvaluados (obtenerParametrosEvaluados estado dato (contadorResto :parametros))]
      (incrementarAcumuladorCorrespondiente estado contadorNombre parametrosEvaluados)
    )
    estado))

(defn procesarUnaReglaContador
  "TODO(Iván): Agregar descripción."
  [estadoYDato contadorNombre contadorResto] ;"contadorResto" es un mapa de tres elementos: :parametros, :condicion, :acumuladores.
  (let [
    estado (first estadoYDato)
    dato   (last estadoYDato)]
  (if
    (and
      (expresion-valida? (contadorResto :condicion) dato estado)
      (ejecutar-funcion (contadorResto :condicion) dato estado)) ;"and" cortocircuita, así que no hay error acá. Si no es válida, no la evalúa.
    (list (procesarParametrosDeUnaRegla estado dato contadorNombre contadorResto) dato) ;Evaluar parámetros e incrementar acumulador correspondiente.
    (list estado dato))))

(defn procesar
  "TODO(Iván): Agregar descripción."
  [estado datos]
  ;TODO(Iván): Obtengo todas las señales, y genero todos los resultados y los adjunto al estado.
  (let [mapaDeReglas (get (:reglas estado) 'define-counter)] ;El mapa de "nombre de regla" a "resto de la regla".
    (reduce-kv procesarUnaReglaContador (list estado datos) mapaDeReglas) ;"estado" se va transformando en el nuevo estado actualizado con el reduce."
  )
)
