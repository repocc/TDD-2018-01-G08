(ns procesamiento
  (require  [definiciones :refer :all])
  (require  [tipos :refer :all]))

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
  ;(filter (fn [argumento] (= argumento 0)) argumentos)
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

