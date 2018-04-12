(ns procesamiento
  (require  [definiciones :refer :all])
  (require  [tipos :refer :all]))

(defrecord Contador [nombre argumentos expresion parametros])
(defrecord Senal [nombre funcion-valor expresion valor estado-senal])
(defrecord Parametro [nombre valor])

(defn nombre-correcto? [regla nombre-contador]
  (= (:nombre regla) nombre-contador)
)

(defn buscar-valor-por-parametro [parametros contador-argumentos] 
  (let [
          valor-argumentos (if (= contador-argumentos []) 'default contador-argumentos)
          parametro-valor  (filter (fn[param] (= valor-argumentos (:nombre param))) parametros)
          valor            (:valor (first parametro-valor))
         ]
    valor
  )
)

(defn consultar-contador [estado nombre-contador contador-argumentos] 
   (let [
          contador            (first (filter (fn [cont] (nombre-correcto? cont nombre-contador)) estado))
          contador-parametros (:parametros contador)
        ]
        (if (empty? contador-parametros)
          0
          (buscar-valor-por-parametro contador-parametros contador-argumentos)
        )  
   )
)

(defn counter-value [nombre-contador contador-argumentos estado]
  (consultar-contador estado nombre-contador contador-argumentos)
)

(defn obtener-argumentos-no-validos [funcion argumentos dato estado]
  (filter (fn [argumento] (= (implementa-funcion? funcion argumento dato estado) false)) argumentos) 
)

; Encargada de validar que los tipos de los argumentos sean correctos de acuerdo a la funcion
(defn validar-tipos [funcion argumentos dato estado]
  (let [
          argumentos-no-validos (obtener-argumentos-no-validos funcion argumentos dato estado)
          existen-no-validos    (empty? argumentos-no-validos)
        ]
    (if (= existen-no-validos true)
      true
      ERROR
    )
  )
)

(defn obtener-ceros [argumentos dato estado]
  (filter (fn [argumento] 
            
              (if(seq? argumento)    
                (if (funcion? (first argumento))  
                  (= (ejecutar-funcion argumento dato estado) 0))
                (= argumento 0)  
              )) argumentos)
  
  
)

(defn validar-no-existencia-cero [funcion argumentos dato estado]
  (let [
          divisores        (drop 1 argumentos)
          argumentos-ceros (obtener-ceros divisores dato estado)
          existen-ceros    (empty? argumentos-ceros)
        ]
    existen-ceros
  )
)

(defn obtener-nombre-funcion [funcion-con-argumentos]
  (first funcion-con-argumentos)
)

(defn obtener-argumentos [funcion-con-argumentos]
  (let [
          funcion (obtener-nombre-funcion funcion-con-argumentos)
        ]
    (filter (fn [elemento-funcion] (not= funcion elemento-funcion)) funcion-con-argumentos)
  )
)

; En caso de que un argumento no sea un tipo basico, se lleva a cabo su ejecución
(defn resolver-argumento [argumento dato estado]
  (if (= (tipo-basico? argumento) true)
    argumento
    (ejecutar-funcion argumento dato estado)
  )
)

(defn obtener-argumentos-ejecutables [funcion-con-argumentos dato estado]
  (let [  
          funcion     (obtener-nombre-funcion funcion-con-argumentos)
          argumentos  (obtener-argumentos funcion-con-argumentos)
        ]
    (map (fn [argumento] (resolver-argumento argumento dato estado)) argumentos)
  )
)

; Para que una expresion sea valida, deben ser validas todas sus sub-expresiones. 
; Se deben cumplir cada una de las precondiciones.
(defn expresion-valida? [funcion-con-argumentos dato estado]
  ;Verifico si no se trata de un valor de un tipo basico
  (if (not (seq? funcion-con-argumentos))
    (if (= funcion-con-argumentos true)
      true
      false
    )
    (let [ 
          funcion     (obtener-nombre-funcion funcion-con-argumentos)
          argumentos  (obtener-argumentos funcion-con-argumentos)
        ]
    (if (= (funcion? funcion) true)
      (if(= (precondiciones-validas? funcion argumentos dato estado) true)
        true
        false
      )
    )
  )
 )
)

(defn obtener-contador [regla]
  (let [
          nombre      (nth regla 1)
          argumentos  (nth regla 2)
          expresion   (nth regla 3)
          parametros  [(new Parametro 'default 0)]
        ]
    (new Contador nombre argumentos expresion parametros)
  )
)

(defn obtener-senal [regla]
  (let [
          nombre        (first (keys (nth regla 1)))
          funcion-valor (first (vals (nth regla 1)))
          expresion     (nth regla 2)
          valor         0
          estado-senal  OFF
        ]
    (new Senal nombre funcion-valor expresion valor estado-senal)
  )
)

(defn obtener-contadores [reglas]
  (let [
          contadores (filter (fn[regla] (= (first regla) 'define-counter)) reglas)
        ]
    (map (fn[regla](obtener-contador regla)) contadores)
  )
)

(defn obtener-senales [reglas]
  (let [
          senales (filter (fn[regla] (= (first regla) 'define-signal)) reglas)
        ]
    (map (fn[regla](obtener-senal regla)) senales)
  )
)

(defn inicializar-proceso [reglas]
   (map (fn[regla] (if (= (first regla) 'define-counter)
                      (obtener-contador regla)
                      (obtener-senal regla)
                    ))reglas)
)

(defn obtener-valores-argumentos [argumentos nuevo-dato estado]
  (let [
          expresiones-test     (map (fn[expr] (expresion-valida? expr nuevo-dato estado)) argumentos)
          expresiones-validas? (every? true? expresiones-test)
          valor-a-impactar     (if (true? expresiones-validas?)
                                    (map (fn[expr] (ejecutar-funcion expr nuevo-dato estado)) argumentos)
                                    ERROR
                                )
        ]
    valor-a-impactar
  )
)

(defn obtener-parametros-act [parametros valor-argumentos]
  (map (fn[param] (if (= (:nombre param) valor-argumentos)
                      (new Parametro valor-argumentos (inc (:valor param)))
                      param
                    )) parametros)
)

(defn agregar-parametro [parametros valor-argumentos]
  (conj parametros (new Parametro valor-argumentos 1))
)

(defn existe-parametro? [parametros valor-argumentos]
  (let [
         parametros-resultantes (filter (fn[param] (if (= (:nombre param) valor-argumentos) param)) parametros)
        ]
    
    (not (empty? parametros-resultantes))
  )
  
)

(defmethod actualizar-valor procesamiento.Contador [contador nuevo-dato estado valor-argumentos]
  (let [
          nombre            (:nombre contador)
          argumentos        (:argumentos contador)
          expresion         (:expresion contador)
          parametros        (:parametros contador)
          existe-param?     (existe-parametro? parametros valor-argumentos)
          parametro-res     (if (true? existe-param?)
                                  (obtener-parametros-act parametros valor-argumentos)
                                  (agregar-parametro parametros valor-argumentos)
                             )
        ]
    (new Contador nombre argumentos expresion parametro-res)
  )
)

(defmethod actualizar-valor procesamiento.Senal [senal nuevo-dato estado valor-argumentos] 
  (let [
          nombre            (:nombre senal)
          funcion-valor     (:funcion-valor senal)
          expresion         (:expresion senal)
          valor             (:valor senal)
          estado-senal      (:estado-senal senal)
          funcion-valida?   (expresion-valida? (:funcion-valor senal) nuevo-dato estado)
          nuevo-valor       (if (true? funcion-valida?)
                                  (ejecutar-funcion (:funcion-valor senal) nuevo-dato estado)
                                  valor
                             )
                             
          nuevo-estado-senal (if (true? funcion-valida?)
                                ON
                                estado-senal
                             )
        ]
    (new Senal nombre funcion-valor expresion nuevo-valor nuevo-estado-senal)
  )
)

(defmethod actualizar-regla procesamiento.Contador [contador nuevo-dato estado]
  (let [
          argumentos-contador (:argumentos contador)
          valor-argumentos    (if (= argumentos-contador [])
                                  'default
                                  (obtener-valores-argumentos argumentos-contador nuevo-dato estado)
                                )
        ]
    (if (= valor-argumentos ERROR)
      contador
      (actualizar-valor contador nuevo-dato estado valor-argumentos)
    )
  )
)

(defmethod actualizar-regla procesamiento.Senal [senal nuevo-dato estado]
  (actualizar-valor senal nuevo-dato estado [])
)

(defmethod actualizar-regla :default [regla nuevo-dato estado]
  true    
)

(defn existe-en-reglas-a-actualizar? [reglas-a-actualizar regla]
  (let[
          nombre-regla  (:nombre regla)
          existe        (filter (fn[regla] (= nombre-regla (:nombre regla))) reglas-a-actualizar)
        ]
    (not (empty? existe))
  )
)

(defn obtener-senales-activas [estado]
  (let [
         senales-activas (filter (fn[regla] (and (= (type regla) procesamiento.Senal) (= (:estado-senal regla) ON))) estado)
         resultado (map (fn[regla] {(:nombre regla) (:valor regla)}) senales-activas)
        ]
        resultado
  )
)

(defn procesar-datos [estado nuevo-dato]
  (let [
          reglas                (map (fn[regla] regla) estado)
          reglas-a-aplicar      (filter (fn[regla] (expresion-valida? (:expresion regla) nuevo-dato estado)) reglas)
          reglas-a-actualizar   (filter (fn[regla] (ejecutar-funcion (:expresion regla) nuevo-dato estado)) reglas-a-aplicar)
          nuevo-estado          (map (fn[regla] (if (existe-en-reglas-a-actualizar? reglas-a-actualizar regla)
                                                  (actualizar-regla regla nuevo-dato estado)
                                                   regla
                                                  )) estado)
          estado-senales        (obtener-senales-activas nuevo-estado)
        ]
    [nuevo-estado estado-senales]
    
  )
)
