(ns funciones-globales
  (require  [definiciones :refer :all])
  (require  [tipos.tipos :refer :all]))

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

; Para cada una de las funciones que se definen es necesario implementar los sig multimetodos:
; + funcion?
; + ejecutar-funcion
; + precondiciones-validas?

; Definicion de funcion =
(defmethod funcion? '= [funcion] true)

(defmethod ejecutar-funcion '= [funcion-con-argumentos dato estado]
  (let [
          argumentos (obtener-argumentos-ejecutables funcion-con-argumentos dato estado)
          resultado  (apply = argumentos)
        ]
    resultado
  )
)

(defmethod precondiciones-validas? '= [funcion argumentos dato estado]
  (validar-tipos funcion argumentos dato estado)
)

(defmethod ejecutar-funcion :default [funcion-con-argumentos dato estado] funcion-con-argumentos)

; Definicion de funcion =
(defmethod funcion? '!= [funcion] true)

(defmethod ejecutar-funcion '!= [funcion-con-argumentos dato estado]
  (let [
          argumentos (obtener-argumentos-ejecutables funcion-con-argumentos dato estado)
          resultado  (apply not= argumentos)
        ]
    resultado
  )
)

(defmethod precondiciones-validas? '!= [funcion argumentos dato estado]
  (validar-tipos funcion argumentos dato estado)
)

(defmethod ejecutar-funcion :default [funcion-con-argumentos dato estado] 
  (if (tipo-basico? funcion-con-argumentos)
    funcion-con-argumentos
    false
  )
)

(defmethod funcion? :default [funcion] false)

(defmethod precondiciones-validas? :default [funcion argumentos dato estado] false)
