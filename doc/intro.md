# TP1 75.10 Técnicas de diseño
1° Cuatrimestre de 2018

Integrantes:
Robert Diaz Bejar(97259)
Iván Andrés Fatur (84491)
Viken Masrian (96438)


## Estructura de los datos
La estructura de los datos se encuentra documentada en los archivos del paquete "estado". Básicamente se interpretó el DSL como:

(define-counter
    nombre     <string>
    parámetros <vector de un tipo básico o listas ejecutables>
    condición  <booleano o lista ejecutable>
)


(define-signal
    nombreSeñalYResultado <mapa>{
                                 clave: nombreSeñal<string>
                                 valor: resultado<tipo básico o lista ejecutable>
  }
  condición               <booleano o lista ejecutable>
)

Se utilizó un "record" con un campo "reglas" para representar el estado. En él se almacena un mapa de contadores bajo la clave "define-counter" y un mapa de señales, bajo la clave "define-signal".
El mapa de señales, almacena las señales usando el string "nombre" de cada señal como clave. El valor correspondiente al "nombre" de cada señal, es a su vez un mapa que tiene tres claves: "parámetros", "condición" , y "acumuladores". En "parámetros" se almacena el vector de parámetros del contador, en "condición" está la expresión evaluable que constituye la condición del contador, y en "parámetros" hay un mapa que usa un vector de parámetros (evaluados) como clave, proveyendo distintas claves para un mismo contador si los parámetros al evaluarse producen distintos valores de tipos básicos. Así en parámetros se almacenan los valores de los acumuladores para cada una de las combinaciones posibles de parámetros que se pueden dar al evaluar las expresiones que los definen.


## Consideraciones de la solución elegida
Se utilizaron multimétodos para manejar la carga de las reglas, en función de si se trata de contadores o señales.
Se utilizaron multimétodos para manejar la ejecución de las distintas expresiones y los distintos tipos de datos que usa el sistema.
Se utilizaron las funciones de clojure "[...]-in" a fin de "modificar" los estados, que son inmutables.


## Estructura del código
* Se utilizó el archivo provisto "data_processor.clj" como una interfaz entre el código que nosotros escribimos y las funciones que se nos requerían desde "acceptance_test.clj"
* La inicialización del estado se realiza desde "inicializar.clj", con una función auxiliar en "inicializar_auxiliares.clj".
* El procesamiento de los datos comienza en "procesar.clj".
* El estado se manipula mediante las funciones provistas por los archivos "estado.clj", "contador.clj", y"senyal.clj".
* Los archivos "tipos.clj", "bool.clj", "number.clj", y "strng.clj", junto con sus auxiliares, realizan el manejo de los distintos tipos de datos que usa el sistema.
* La evaluación de expresiones, se realiza mediante las funciones provistas por "procesamiento.clj", "funciones.clj", y "funciones_globales.clj"
* Se proveen archivos con tests generados para la prueba de la inicialización del estado.
