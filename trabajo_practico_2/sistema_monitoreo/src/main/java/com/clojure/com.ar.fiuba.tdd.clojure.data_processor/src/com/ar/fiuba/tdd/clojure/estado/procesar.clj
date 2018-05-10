;Funciones requeridas de este archivo:
;;(defn procesar [estado dato])

(ns com.ar.fiuba.tdd.clojure.estado.procesar
  (:require
    [com.ar.fiuba.tdd.clojure.estado.estado :as est] ;obtenerDatosPasados
    [com.ar.fiuba.tdd.clojure.interprete.definiciones :as def] ;ejecutarFuncion
    [com.ar.fiuba.tdd.clojure.interprete.procesamiento :as proc] ;expresionValidaContemplandoPast?, ejecutarFuncionContemplandoPast, expresionValida?
    [com.ar.fiuba.tdd.clojure.tipos.bool-funciones :refer :all] ;ejecutarFuncion
    [com.ar.fiuba.tdd.clojure.tipos.number-funciones :refer :all] ;ejecutarFuncion
    [com.ar.fiuba.tdd.clojure.tipos.string-funciones :refer :all] ;ejecutarFuncion
    :reload-all))

(defn incrementarAcumuladorCorrespondiente
  "Se actualiza el valor del acumulador correspondiente a \"contadorNombre\", para los \"parametrosEvaluados\" en el \"estado\", según el \"dato\". Devuelve un estado con el valor del contador actualizado."
  [estado dato contadorNombre parametrosEvaluados]
  (update-in
    estado
    [:reglas 'define-counter contadorNombre :acumuladores parametrosEvaluados] ;Llego hasta el acumulador específico para esta combinación de parámetros.
    (fn
      [acumulador]
      (if (nil? acumulador) ;Cuando el acumulador es cero, es porque nunca se había dado esta combinación de parámetros, por lo cual la clave no existe en el mapa (porque nunca fue incrementada antes y no se cargan en cero al principio, se espera a que haya que incrementarlas para meter la combinación de parámetros al mapa) y retorna "nil".
        (if (contains? (get-in estado [:reglas 'define-counter contadorNombre]) :paso)
          (def/ejecutarFuncion (get-in estado [:reglas 'define-counter contadorNombre :paso]) dato estado) ;TODO(Iván): CHEQUEAR QUE SEA FUNCION.
          1)
        (if (contains? (get-in estado [:reglas 'define-counter contadorNombre]) :paso)
          (+ (def/ejecutarFuncion (get-in estado [:reglas 'define-counter contadorNombre :paso]) dato estado) acumulador) ;TODO(Iván): CHEQUEAR QUE SEA FUNCION.
          (inc acumulador))))))

(defn obtenerParametrosEvaluados
  "Se transforma el vector de expresiones en un vector de tipos básicos."
  [estado dato parametros]
  (map (fn [parametro] (def/ejecutarFuncion parametro dato estado)) parametros) ;Me genero una lista con los valores de la evaluación de los parámetros del contador. Listas y vectores son intercambiables al usarlos como clave de un mapa.
)

(defn procesarParametrosDeUnContador
  "Se resuelve qué acumulador exactamente se debe actualizar, en función de la combinación de valores de parámetros dada."
  [estado dato contadorNombre contadorResto] ;"contadorResto" es un mapa de tres elementos: :parametros, :condicion, :acumuladores.
  (if (every? true? (map (fn [parametro] (proc/expresionValida? parametro dato estado)) (contadorResto :parametros)))
    (let [
      parametrosEvaluados (obtenerParametrosEvaluados estado dato (contadorResto :parametros))]
      (incrementarAcumuladorCorrespondiente estado dato contadorNombre parametrosEvaluados)
    )
    estado))

(defn procesarUnContador
  "Se procesa un dato a través de un contador."
  [estadoYDato contadorNombre contadorResto] ;"contadorResto" es un mapa de tres elementos: :parametros, :condicion, :acumuladores.
  (let [
    estado       (first estadoYDato)
    dato         (last estadoYDato)
    datosPasados (est/obtenerDatosPasados estado)]
  (if
    (and
      (proc/expresionValidaContemplandoPast? (contadorResto :condicion) dato estado datosPasados)
      (proc/ejecutarFuncionContemplandoPast (contadorResto :condicion) dato estado datosPasados)) ;"and" cortocircuita, así que no hay error acá. Si no es válida, no la evalúa.
    (list (procesarParametrosDeUnContador estado dato contadorNombre contadorResto) dato) ;Evaluar parámetros e incrementar acumulador correspondiente.
    (list estado dato))))

(defn procesarFormulaDeUnaSenyal
  "Se genera el/los resultado/s de una señal. Devuelve un estado actualizado con el resultado incorporado."
  [estado dato senyalNombre senyalFormula]
  (if (proc/expresionValida? senyalFormula dato estado)
    (let [
      formulaEvaluada (def/ejecutarFuncion senyalFormula dato estado)]
      (update-in
        estado
        [:senyales] ;TODO(Iván): cambiar el nombre a "resultados".
        (fn [listaDeSenyalesAnteriores formulaEvaluada] (cons {senyalNombre formulaEvaluada} listaDeSenyalesAnteriores))
        formulaEvaluada)
    )
    estado))

(defn procesarUnaSenyal
  "Se procesa un dato a través de una señal."
  [estadoYDato senyalNombre senyalResto]
  (let [
    estado       (first estadoYDato)
    dato         (last estadoYDato)
    datosPasados (est/obtenerDatosPasados estado)]
  (if
    (and
      (proc/expresionValidaContemplandoPast? (senyalResto :condicion) dato estado datosPasados)
      (proc/ejecutarFuncionContemplandoPast (senyalResto :condicion) dato estado datosPasados)) ;"and" cortocircuita, así que no hay error acá. Si no es válida, no la evalúa.
    (list (procesarFormulaDeUnaSenyal estado dato senyalNombre (senyalResto :formula)) dato)
    (list estado dato))))

(defn procesar
  "Procesa el \"dato\", actualizando el \"estado\" según corresponda. Devuelve un vector con el \"estado\" actualizado en la primer posición, y la lista de resultados en la segunda posición."
  [estado dato]
  ;TODO(Iván): Obtengo todas las señales, y genero todos los resultados y los adjunto al estado.
  (let [
    mapaDeSenyales   (get (:reglas estado) 'define-signal) ;El mapa de "nombre de la señal" a "resto de la señal".
    mapaDeContadores (get (:reglas estado) 'define-counter)] ;El mapa de "nombre del contador" a "resto del contador".
    (let [
      nuevoEstadoConResultadosYDato (reduce-kv procesarUnaSenyal (list estado dato) mapaDeSenyales)
      nuevoEstadoYDato              (reduce-kv procesarUnContador (list estado dato) mapaDeContadores) ;"estado" se va transformando en el nuevo estado actualizado con el reduce. Se pone el estado en una lista con el dato para poder aprovechar la recursividad de la funcion "reduce-kv".
      ;La salida esperada es un vector con el estado en la primera posición, y los resultado en la segunda.
      nuevoEstado  ;TODO(Iván): No tocar datosPasados desde acá, crear una función en "estado".
        (update-in
          (first nuevoEstadoYDato)
          [:datosPasados]
          (fn
            [datosPasados dato]
            (cons dato datosPasados))
          dato)
      resultado
        (if
          (nil? (get (first nuevoEstadoConResultadosYDato) :senyales))
          '()
          (get (first nuevoEstadoConResultadosYDato) :senyales))]
      [nuevoEstado resultado])))
