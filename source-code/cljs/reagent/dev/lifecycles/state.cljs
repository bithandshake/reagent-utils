
(ns reagent.dev.lifecycles.state)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @ignore
;
; @atom (map)
; {:my-component (keyword)}
(defonce MOUNTED-COMPONENTS (atom {}))
