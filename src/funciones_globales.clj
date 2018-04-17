(ns funciones-globales
  (require  [definiciones :refer :all])
  (require  [tipos.tipos :refer :all]))

(defn obtenerArgumentosNoValidos [funcion argumentos dato estado]
  (filter (fn [argumento] (= (implementaFuncion? funcion argumento dato estado) false)) argumentos)
)

; Encargada de validar que los tipos de los argumentos sean correctos de acuerdo a la funcion
(defn validarTipos [funcion argumentos dato estado]
  (let [
          argumentosNoValidos (obtenerArgumentosNoValidos funcion argumentos dato estado)
          existenNoValidos    (empty? argumentosNoValidos)
        ]
    (if (= existenNoValidos true)
      true
      ERROR
    )
  )
)

(defn obtenerNombreFuncion [funcionConArgumentos]
  (first funcionConArgumentos)
)

(defn obtenerArgumentos [funcionConArgumentos]
  (let [
          funcion (obtenerNombreFuncion funcionConArgumentos)
        ]
    (filter (fn [elementoFuncion] (not= funcion elementoFuncion)) funcionConArgumentos)
  )
)

; En caso de que un argumento no sea un tipo basico, se lleva a cabo su ejecución
(defn resolverArgumento [argumento dato estado]
  (if (= (tipoBasico? argumento) true)
    argumento
    (ejecutarFuncion argumento dato estado)
  )
)

(defn obtenerArgumentosEjecutables [funcionConArgumentos dato estado]
  (let [
          funcion     (obtenerNombreFuncion funcionConArgumentos)
          argumentos  (obtenerArgumentos funcionConArgumentos)
        ]
    (map (fn [argumento] (resolverArgumento argumento dato estado)) argumentos)
  )
)

; Para cada una de las funciones que se definen es necesario implementar los sig multimetodos:
; + funcion?
; + ejecutarFuncion
; + precondicionesValidas?

; Definicion de funcion =
(defmethod funcion? '= [funcion] true)

(defmethod ejecutarFuncion '= [funcionConArgumentos dato estado]
  (let [
          argumentos (obtenerArgumentosEjecutables funcionConArgumentos dato estado)
          resultado  (apply = argumentos)
        ]
    resultado
  )
)

(defmethod precondicionesValidas? '= [funcion argumentos dato estado]
  (validarTipos funcion argumentos dato estado)
)

(defmethod ejecutarFuncion :default [funcionConArgumentos dato estado] funcionConArgumentos)

; Definicion de funcion =
(defmethod funcion? '!= [funcion] true)

(defmethod ejecutarFuncion '!= [funcionConArgumentos dato estado]
  (let [
          argumentos (obtenerArgumentosEjecutables funcionConArgumentos dato estado)
          resultado  (apply not= argumentos)
        ]
    resultado
  )
)

(defmethod precondicionesValidas? '!= [funcion argumentos dato estado]
  (validarTipos funcion argumentos dato estado)
)

(defmethod ejecutarFuncion :default [funcionConArgumentos dato estado] 
  (if (tipoBasico? funcionConArgumentos)
    funcionConArgumentos
    false
  )
)

(defmethod funcion? :default [funcion] false)

(defmethod precondicionesValidas? :default [funcion argumentos dato estado] false)
