;funciones requeridas en este paquete:
;;(procesarListaDeReglas [listaDeReglasEnDSL])

(ns inicializar
  (:require
    [estado.estado :refer :all]
    [estado.contador :refer :all] :reload-all))

;Multimétodo que matchea para las reglas que definen señales. TODO(Iván): Explicar más.
(defmethod procesarUnaRegla
  'define-signal [estado unaSenyalEnDSL])

;Multimétodo que matchea para reglas desconocidas. Deja el estado tal cual estaba, no introduce cambios. Devuelve el mismo estado que recibió y consume la regla sin modificar nada.
(defmethod procesarUnaRegla
  :default [estado unaSenyalEnDSL] estado)

(defn procesarListaDeReglas
  "Recibe una lista de reglas expresadas en el lenguage específico de dominio (DSL), cada una de las cuales se representa como una lista. Retorna el estado de un sistema recién creado."
  ;Formato de lista de reglas: ((<regla en DSL>)*)
  [listaDeReglasEnDSL]
  ;TODO(Iván): comprobar que sea una lista.
  (let [estado (estado.estado.Estado. {})] ;Empiezo con un estado vacío.
    (reduce
      (fn [estado unaReglaEnDSL] (procesarUnaRegla estado unaReglaEnDSL)) ;TODO(Iván): antes del multimétodo, ver que tenga el formato DSL apropiado.
      estado
      listaDeReglasEnDSL)))

(load "inicializar_auxiliares")
