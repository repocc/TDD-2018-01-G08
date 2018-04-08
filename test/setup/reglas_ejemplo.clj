(ns setup.reglas-ejemplo)

(def reglaContadorLlamadoTotalSinParametrosYCondicionTrue '(define-counter "Total" [] true))
(def reglaContadorLlamadoTotalSinParametrosYCondicionFalse '(define-counter "Total" [] false))
(def reglaContadorLlamadoAcumSinParametrosYCondicionTrue '(define-counter "Acum" [] true))
(def reglaContadorLlamadoTOTALSinParametrosYCondicionTrue '(define-counter "TOTAL" [] true))

(def reglaSemaforoLlamadoTotalSinParametrosYCondicionTrue '(define-semaphore "Total" [] true))
(def reglaSemaforoLlamadoTOTALSinParametrosYCondicionTrue '(define-semaphore "TOTAL" [] true))

(def reglaSeñalLlamadaTotalConFuncion1YCondicionTrue '(define-signal {"Total" 1} true))
(def reglaSeñalLlamadaTOTALConFuncion1YCondicionTrue '(define-signal {"TOTAL" 1} true))
