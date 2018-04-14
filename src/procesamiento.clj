(ns procesamiento
  (require  [definiciones :refer :all])
  (require  [funciones :refer :all])
  (require  [funciones_globales :refer :all]) ;COMENTADO POR IVÁN PARA HACER DEBUG.
  (require  [aux :refer :all]) 
  (require  [tipos :refer :all]))

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


; Para que una expresion sea valida, deben ser validas todas sus sub-expresiones.
; Se deben cumplir cada una de las precondiciones.
(defn expresion-valida? [funcion-con-argumentos dato estado]
  ;Verifico si no se trata de un valor de un tipo basico
  (if (not (seq? funcion-con-argumentos))
    (if (tipo-basico? funcion-con-argumentos)
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

; En el caso que no sea un tipo basico, es decir, otra expresion. Esta es analizada.
(defmethod implementa-funcion? :default [funcion argumento dato estado]
  (expresion-valida? argumento dato estado)
)
