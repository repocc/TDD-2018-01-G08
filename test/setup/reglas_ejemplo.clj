(ns setup.reglas-ejemplo)

(def reglaContadorLlamadoTotalSinParametrosYCondicionTrue '(define-counter "Total" [] true))
(def reglaContadorLlamadoTotalSinParametrosYCondicionFalse '(define-counter "Total" [] false))
(def reglaContadorLlamadoAcumSinParametrosYCondicionTrue '(define-counter "Acum" [] true))
(def reglaContadorLlamadoTOTALSinParametrosYCondicionTrue '(define-counter "TOTAL" [] true))

(def reglaContadorPasoLlamadoTotalPaso11SinParametrosYCondicionTrue '(define-counter "Total" 11 [] true))
(def reglaContadorPasoLlamadoTotalPaso17SinParametrosYCondicionFalse '(define-counter "Total" 17 [] false))
(def reglaContadorPasoLlamadoAcumPaso37SinParametrosYCondicionTrue '(define-counter "Acum" 37 [] true))
(def reglaContadorPasoLlamadoTOTALPaso59SinParametrosYCondicionTrue '(define-counter "TOTAL" 59 [] true))

(def reglaSemaforoLlamadoTotalSinParametrosYCondicionTrue '(define-semaphore "Total" [] true))
(def reglaSemaforoLlamadoTOTALSinParametrosYCondicionTrue '(define-semaphore "TOTAL" [] true))

(def reglaSenyalLlamadaTotalConFuncion1YCondicionTrue '(define-signal {"Total" 1} true))
(def reglaSenyalLlamadaTOTALConFuncion1YCondicionTrue '(define-signal {"TOTAL" 1} true))
