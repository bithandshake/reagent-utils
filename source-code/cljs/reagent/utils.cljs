
(ns reagent.utils)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn component?
  ; @param (*)
  ;
  ; @example
  ; (component? [:div "..."])
  ; =>
  ; false
  ;
  ; @example
  ; (defn my-component [] ...)
  ; (component? [my-component "..."])
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n]
  (and (-> n vector?)
       (-> n first fn?)))
