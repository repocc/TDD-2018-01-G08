;API provista con el enunciado del trabajo práctico.

(ns com.ar.fiuba.tdd.clojure.data_processor
  (:require
    [com.ar.fiuba.tdd.clojure.estado.estado :as est] ;consultarContador
    [com.ar.fiuba.tdd.clojure.estado.inicializar :as inic] ;cargarListaDeReglas
    [com.ar.fiuba.tdd.clojure.estado.procesar :as proc] ;procesar
    :reload-all)
  (:gen-class 
    :name com.ar.fiuba.tdd.clojure.data_processor
    :methods [#^{:static true} [prueba_vk [] String], 
              #^{:static true} [initialize_processor [String] com.ar.fiuba.tdd.clojure.estado.estado.Estado],
              #^{:static true} [process_data [String String] String],
              #^{:static true} [query_counter [String String String] String]]
    )
  )

(defn -initialize_processor
  "Recibe una lista de reglas expresadas en el lenguage específico de dominio (DSL), cada una de las cuales se representa como una lista. Retorna un \"estado\" con las reglas cargadas."
  [rules]
  (inic/cargarListaDeReglas rules))

(defn -process_data
  "Analiza el dato pasado por parámetros aplicándole las reglas que están cargadas en el estado, y generando las salidas que correspondan, así como un nuevo estado actualizado. Devuelve un vector con el estado actualizado en la posición 0 y los resultados (salidas) en la posición 1."
  [state new-data]
  (proc/procesar state new-data))

(defn -query_counter
  "Recibe un estado, el nombre de un contador, y un vector de parámetros. Retorna el valor de dicho contador (para la combinación de parámetros suministrada) en el estado suministrado."
  ;TODO(Iván): No se puede permitir llamarla con comandos en los parámetros que implican un dato (como por ejemplo "[(current "spam")]", ya que el dato no es suministrado a la función para poder examinarlo. De momento, se intenta ejecutar todos los comandos con un dato vacío ("{}").
  [state counter-name counter-args]
  (est/consultarContador state counter-name counter-args))

(defn -main [& args]
  (println "Hello, VK8!")
)

(defn -prueba_vk []
  (println "Hello, VK888!")
)