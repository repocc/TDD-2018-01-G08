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
  (println "4incrementarAcumuladorCorrespondiente")
  (println "LOL QUIERE "
  estado 3
  contadorNombre 4
  parametrosEvaluados 5)
  (update-in
    estado
    [:reglas 'define-counter contadorNombre :acumuladores parametrosEvaluados] ;Llego hasta el acumulador específico para esta combinación de parámetros.
    (fn
      [acumulador]
      (if (nil? acumulador) ;Cuando el acumulador es cero, es porque nunca se había dado esta combinación de parámetros, por lo cual la clave no existe en el mapa (porque nunca fue incrementada antes y no se cargan en cero al principio, se espera a que haya que incrementarlas para meter la combinación de parámetros al mapa) y retorna "nil".
        1
        (inc acumulador)
        ))))

(defn obtenerParametrosEvaluados
  "TODO(Iván): Agregar descripción."
  [estado parametros]
  (println "3obtenerParametrosEvaluados")
  (map (fn [parametro] (ejecutar-funcion parametro {} estado)) parametros) ;Me genero una lista con los valores de la evaluación de los parámetros del contador. Listas y vectores son intercambiables al usarlos como clave de un mapa.
  ;(println "EJECUTADO" parametros)
)

(defn procesarParametrosDeUnaRegla
  "TODO(Iván): Agregar descripción."
  [estado contadorNombre contadorResto] ;"contadorResto" es un mapa de tres elementos: :parametros, :condicion, :acumuladores.
  (println "2procesarParametrosDeUnaRegla")
  (if (every? true? (map (fn [parametro] (expresion-valida? parametro {} estado)) (contadorResto :parametros)))
    (do (println "ACA1")
    (let [
      parametrosEvaluados (obtenerParametrosEvaluados estado (contadorResto :parametros))]
      (incrementarAcumuladorCorrespondiente estado contadorNombre parametrosEvaluados)
    ))
    (do (println "ACA2")
    estado)))

(defn procesarUnaReglaContador
  "TODO(Iván): Agregar descripción."
  [estadoYDato contadorNombre contadorResto] ;"contadorResto" es un mapa de tres elementos: :parametros, :condicion, :acumuladores.
  (println "1procesarUnaReglaContador")
  (let [
    estado (first estadoYDato)
    dato   (last estadoYDato)]
  (println "estado: "estado)
  (println "dato:   "dato)
  (println (type (first(keys dato))))
  (println (contadorResto :condicion))
  (if
    (and
      (expresion-valida? (contadorResto :condicion) dato estado)
      (println "LLEGO HASTA ACA")) ;(ejecutar-funcion (contadorResto :condicion) dato estado)) ;"and" cortocircuita, así que no hay error acá. Si no es válida, no la evalúa.
      (do (println "xx")
    (list (procesarParametrosDeUnaRegla estado contadorNombre contadorResto) dato) ;Evaluar parámetros e incrementar acumulador correspondiente.
    ) (do (println "YY")
    (list estado dato)))))

(defn procesar
  "TODO(Iván): Agregar descripción."
  [estado datos]
  ;TODO(Iván): Obtengo todas las señales, y genero todos los resultados y los adjunto al estado.
  (let [mapaDeReglas (get (:reglas estado) 'define-counter)] ;El mapa de "nombre de regla" a "resto de la regla".
    (println estado)
    (reduce-kv procesarUnaReglaContador (list estado datos) mapaDeReglas) ;"estado" se va transformando en el nuevo estado actualizado con el reduce."
  )
)
