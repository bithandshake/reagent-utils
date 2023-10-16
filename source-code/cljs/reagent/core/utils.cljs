
(ns reagent.core.utils)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn component?
  ; @description
  ; Checks if the input 'n' is a vector containing the first element as a function
  ; (indicating it might be a component).
  ; Returns TRUE if the conditions are met, and FALSE otherwise.
  ;
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
