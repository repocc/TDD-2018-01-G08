(ns inicializar-auxiliares)

(defn agregarMapaDeReglasEspecificas
  "Agrega al estado el mapa que tendrá todas las reglas de un determinado tipo (contadores, señales, etc.). Devuelve un nuevo esta con dicho mapa agregado (la clave del mapa es la sentencia de DSL usada para definir la señal)."
  [estado clave]
  (assoc-in estado [':reglas clave] {}))
