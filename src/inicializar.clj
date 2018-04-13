;funciones requeridas en este paquete:
;;(procesarListaDeReglas [listaDeReglasEnDSL])

(ns inicializar
  (:require
    [estado :refer :all]
    [estado.contador :refer :all]
    [estado.contadorPaso :refer :all]
    [estado.senyal :refer :all] :reload-all))

;Multimétodo que matchea para reglas desconocidas. Deja el estado tal cual estaba, no introduce cambios. Devuelve el mismo estado que recibió y consume la regla sin modificar nada.
(defmethod procesarUnaRegla
  :default [estado unaSenyalEnDSL] estado)

(defn cargarListaDeReglas
  "Recibe una lista de reglas expresadas en el lenguage específico de dominio (DSL), cada una de las cuales se representa como una lista. Retorna el estado de un sistema recién creado."
  ;Formato de lista de reglas: ((<regla en DSL>)*)
  [listaDeReglasEnDSL]
  ;TODO(Iván): comprobar que sea una lista.
  (let [estado (estado.Estado. {})] ;Empiezo con un estado vacío.
    (reduce
      (fn [estado unaReglaEnDSL] (procesarUnaRegla estado unaReglaEnDSL)) ;TODO(Iván): antes del multimétodo, ver que tenga el formato DSL apropiado.
      estado
      listaDeReglasEnDSL)))
