(ns procesar
  (:require
    [estado :refer :all]
    [definiciones :refer :all]
    [procesamiento :refer :all]
    [tipos.bool-funciones :refer :all]
    [tipos.string-funciones :refer :all]
    [tipos.number-funciones :refer :all] :reload-all))

(defn incrementarAcumuladorCorrespondiente
  "TODO(Iván): Agregar descripción."
  [estado dato contadorNombre parametrosEvaluados]
  (update-in
    estado
    [:reglas 'define-counter contadorNombre :acumuladores parametrosEvaluados] ;Llego hasta el acumulador específico para esta combinación de parámetros.
    (fn
      [acumulador]
      (if (nil? acumulador) ;Cuando el acumulador es cero, es porque nunca se había dado esta combinación de parámetros, por lo cual la clave no existe en el mapa (porque nunca fue incrementada antes y no se cargan en cero al principio, se espera a que haya que incrementarlas para meter la combinación de parámetros al mapa) y retorna "nil".
        (if (contains? (get-in estado [:reglas 'define-counter contadorNombre]) :paso)
          (ejecutarFuncion (get-in estado [:reglas 'define-counter contadorNombre :paso]) dato estado) ;TODO CHEQUEAR QUE SEA FUNCION.
          1)
        (if (contains? (get-in estado [:reglas 'define-counter contadorNombre]) :paso)
          (+ (ejecutarFuncion (get-in estado [:reglas 'define-counter contadorNombre :paso]) dato estado) acumulador) ;TODO CHEQUEAR QUE SEA FUNCION.
          (inc acumulador))))))

(defn obtenerParametrosEvaluados
  "TODO(Iván): Agregar descripción."
  [estado dato parametros]
  (map (fn [parametro] (ejecutarFuncion parametro dato estado)) parametros) ;Me genero una lista con los valores de la evaluación de los parámetros del contador. Listas y vectores son intercambiables al usarlos como clave de un mapa.
)

(defn procesarParametrosDeUnContador
  "TODO(Iván): Agregar descripción."
  [estado dato contadorNombre contadorResto] ;"contadorResto" es un mapa de tres elementos: :parametros, :condicion, :acumuladores.
  (if (every? true? (map (fn [parametro] (expresionValida? parametro dato estado)) (contadorResto :parametros)))
    (let [
      parametrosEvaluados (obtenerParametrosEvaluados estado dato (contadorResto :parametros))]
      (incrementarAcumuladorCorrespondiente estado dato contadorNombre parametrosEvaluados)
    )
    estado))

(defn procesarUnContador
  "TODO(Iván): Agregar descripción."
  [estadoYDato contadorNombre contadorResto] ;"contadorResto" es un mapa de tres elementos: :parametros, :condicion, :acumuladores.
  (let [
    estado (first estadoYDato)
    dato   (last estadoYDato)]
  (if
    (and
      (expresion-valida-contemplando-past? (contadorResto :condicion) dato estado)
      (ejecutarFuncion-contemplando-past (contadorResto :condicion) dato estado)) ;"and" cortocircuita, así que no hay error acá. Si no es válida, no la evalúa.
    (list (procesarParametrosDeUnContador estado dato contadorNombre contadorResto) dato) ;Evaluar parámetros e incrementar acumulador correspondiente.
    (list estado dato))))

(defn procesarFormulaDeUnaSenyal
  "TODO(Iván): Agregar descripción."
  [estado dato senyalNombre senyalFormula]
  (if (expresionValida? senyalFormula dato estado)
    (let [
      formulaEvaluada (ejecutarFuncion senyalFormula dato estado)]
      (update-in
        estado
        [:senyales]
        (fn [listaDeSenyalesAnteriores formulaEvaluada] (cons {senyalNombre formulaEvaluada} listaDeSenyalesAnteriores))
        formulaEvaluada)
    )
    estado))

(defn procesarUnaSenyal
  "TODO(Iván): Agregar descripción."
  [estadoYDato senyalNombre senyalResto]
  (let [
    estado (first estadoYDato)
    dato   (last estadoYDato)]
  (if
    (and
      (expresion-valida-contemplando-past? (senyalResto :condicion) dato estado)
      (ejecutarFuncion-contemplando-past (senyalResto :condicion) dato estado)) ;"and" cortocircuita, así que no hay error acá. Si no es válida, no la evalúa.
    (list (procesarFormulaDeUnaSenyal estado dato senyalNombre (senyalResto :formula)) dato)
    (list estado dato))))

(defn procesar
  "TODO(Iván): Agregar descripción."
  [estado dato]
  ;TODO(Iván): Obtengo todas las señales, y genero todos los resultados y los adjunto al estado.
  (let [
    mapaDeSenyales   (get (:reglas estado) 'define-signal) ;El mapa de "nombre de la señal" a "resto de la señal".
    mapaDeContadores (get (:reglas estado) 'define-counter)] ;El mapa de "nombre del contador" a "resto del contador".
    (let [
      nuevoEstadoConResultadosYDato (reduce-kv procesarUnaSenyal (list estado dato) mapaDeSenyales)
      nuevoEstadoYDato              (reduce-kv procesarUnContador (list estado dato) mapaDeContadores) ;"estado" se va transformando en el nuevo estado actualizado con el reduce. Se pone el estado en una lista con el dato para poder aprovechar la recursividad de la funcion "reduce-kv".
      ;La salida esperada es un vector con el estado en la primera posición, y los resultado en la segunda.
      nuevoEstado  ;TODO(Iván): METER EL DATO EN EL ESTADO PARA EL PAST.
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
