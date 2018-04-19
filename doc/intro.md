# Trabajo Práctico N°1: Validador de Reglas
#### 75.10 Técnicas de diseño - 1° Cuatrimestre de 2018
---
##### **Integrantes:**
|Nombre|Padrón|
|:---|:---|
| **FATUR**, Iván Andrés | (84491) |
| **MASRIAN**, Viken     | (96438) |
---
### Estructura de los datos
#### -Reglas
La estructura de las reglas se encuentra documentada en los archivos del paquete "reglas". Básicamente se interpretó el DSL (Lenguaje Específico de Dominio) como:
```
(define-counter
    nombre     <string>
    parámetros <vector de tipos básicos y/o listas ejecutables>
    condición  <bool o lista ejecutable>
)
```
```
(define-step-counter
    nombre     <string>
    paso       <number o lista ejecutable>
    parámetros <vector de tipos básicos y/o listas ejecutables>
    condición  <bool o lista ejecutable>
)
```
```
(define-signal
    nombreSeñalYResultado <mapa>{
        clave: nombreSeñal<string>
        valor: resultado<tipo básico o lista ejecutable>
    }
    condición             <bool o lista ejecutable>
)
```
Las reglas son almacenadas en el "estado" que se describe a continuación.
#### -Estado
Se utilizó para representar el estado un "**record**" con un campo llamado "***reglas***" (dónde se almacenan las reglas), y otro de nombre "***datosPasados***" (dónde se almacena el historial de datos ya procesados).
##### reglas:
En él se almacena un mapa de **contadores** bajo la clave "***define-counter***" y un mapa de **señales** (se usa el nombre "*senyal*" en el código), bajo la clave "***define-signal***".
El mapa de señales, almacena las señales usando el string "***nombre***" de cada señal como clave. El valor correspondiente al "nombre" de cada señal, es a su vez un mapa que tiene tres claves: "***parámetros***", "***condición***" , y "***acumuladores***". En "***parámetros***" se almacena el vector de parámetros del contador, en "***condición***" está la expresión evaluable que constituye la condición del contador, y en "***acumuladores***" hay un mapa que usa un vector de parámetros (evaluados) como clave, proveyendo distintas claves para un mismo contador si los parámetros al evaluarse producen distintos valores de tipos básicos. Así, en "***acumuladores***" se almacenan los valores de los acumuladores para cada una de las combinaciones posibles de parámetros que se pueden dar al evaluar las expresiones que los definen.
##### datosPasados:
TODO(Iván)

## Consideraciones de la solución elegida
Se utilizaron multimétodos para manejar la carga de las reglas, en función de si se trata de contadores o señales.
Se utilizaron multimétodos para manejar la ejecución de las distintas expresiones y los distintos tipos de datos que usa el sistema.
Se utilizaron las funciones de clojure "[...]-in" a fin de "modificar" los estados, que son inmutables.

### -Interprete
Para llevar a cabo la interpretación del código clojure, se determino hacer uso de multimetodos.  
La anterior decisión se debe a que de acuerdo a nuestras opiniones, los multimetodos nos permitirán una manera clara, extensible y especializada a la hora de resolver la interpretación y ejecución del código.
  
Los multimetodos más representativos son:  
  
1.funcion?  
2.precondicionesValidas?  
3.implementaFuncion?  
4.ejecutarFuncion  

Cabe destacar una función muy importante para nuestro modelo: expresionValida?  
Esta utiliza el multimetodo precondicionesValidas? para responder si una expresión es válida o no.  
Esta se utilizo para determinar la posterior ejecución o no de una expresión.  

## Estructura del código
* Se utilizó el archivo provisto "data_processor.clj" como una interfaz entre el código que nosotros escribimos y las funciones que se nos requerían desde "acceptance_test.clj"
* La inicialización del estado se realiza desde "inicializar.clj", con una función auxiliar en "inicializar_auxiliares.clj".
* El procesamiento de los datos comienza en "procesar.clj".
* El estado se manipula mediante las funciones provistas por los archivos "estado.clj", "contador.clj", y"senyal.clj".
* Los archivos "tipos.clj", "bool.clj", "number.clj", y "strng.clj", junto con sus auxiliares, realizan el manejo de los distintos tipos de datos que usa el sistema.
* La evaluación de expresiones, se realiza mediante las funciones provistas por "procesamiento.clj", "funciones.clj", y "funciones_globales.clj"
* Se proveen archivos con tests generados para la prueba de la inicialización del estado.

## Notas
1. Se dejó el archivo `data_processor.clj` tal como fue provisto por la cátedra; se implementa toda la funcionalidad en archivos separados con el código escrito en castellano.
2. Se agregaron comentarios a los tests de `acceptance_test.clj` para entender el funcionamiento del sistema a medida que se iba desarrollando; no se cambió el código de los tests.
### Hipótesis
1) No se puede llamar a `data-processor/query-counter`con comandos en los parámetros que implican un dato (como por ejemplo `[(current "spam")]`, ya que el dato no es suministrado a la función `query-counter` como para poder examinarlo. De momento, se intenta ejecutar todos los comandos con un dato vacío ("`{}`").
```
(defn query-counter
  "Recibe un estado, el nombre de un contador, y un vector de parámetros. Retorna el valor de dicho contador (para la combinación de parámetros suministrada) en el estado suministrado."
  [state counter-name counter-args]
  (est/consultarContador state counter-name counter-args))
```
2) TODO(Iván)
### Listado de defmultis
###### estado/estado.clj
* `defmulti procesarUnaRegla` determina el tipo de regla a cargar en el estado (contador, contadorPaso, o senyal)
Los defmethod están en:
    * `estado/inicializar.clj`
    * `reglas/contador.clj`
    * `reglas/contadorPaso.clj`
    * `reglas/senyal.clj`
###### interprete/definiciones.clj
* `defmulti funcion?` nos permite conocer si una funcion es soportada por el procesador de reglas.
    * `interprete/funciones_globales.clj`
    * `tipos/bool_funciones.clj`
    * `tipos/number_funciones.clj`
    * `tipos/string_funciones.clj`
* `defmulti precondicionesValidas?` nos permite verificar que las precondiciones sean las adecuadas para cada función, se obtiene especialización para cada una de las funciones soportadas. 
    * `interprete/funciones_globales.clj`
    * `tipos/bool_funciones.clj`
    * `tipos/number_funciones.clj`
    * `tipos/string_funciones.clj`
* `defmulti ejecutarFuncion` nos permite llevar a cabo la forma en que se ejecuta cada una de las funciones soportadas.
    * `interprete/funciones_globales.clj`
    * `tipos/bool_funciones.clj`
    * `tipos/number_funciones.clj`
    * `tipos/string_funciones.clj`
* `defmulti implementaFuncion?` nos permite conocer si un determinado tipo de argumento puede ser aplicado a una función.
    * `interprete/procesamiento.clj`
    * `interprete/funciones_implementacion.clj`
###### tipos/bool_definicion_funciones.clj
* `defmulti booleanImplementaFuncion?`
Los defmethod están en:
    * `tipos/bool_definicion_funciones.clj`
###### tipos/number_definicion_funciones.clj
* `defmulti numberImplementaFuncion?`
Los defmethod están en:
    * `tipos/number_definicion_funciones.clj`
###### tipos/string_definicion_funciones.clj
* `defmulti stringImplementaFuncion?`
Los defmethod están en:
    * `tipos/string_definicion_funciones.clj`
###### tipos/tipos.clj
* `defmulti tipoBasico?`
Los defmethod están en:
    * `tipos/tipos.clj`
