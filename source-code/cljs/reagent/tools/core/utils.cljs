
(ns reagent.tools.core.utils)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn component?
  ; @description
  ; Returns TRUE if the given 'n' value is a vector containing the first element as a function (indicating it might be a Reagent component).
  ;
  ; @param (*)
  ;
  ; @usage
  ; (component? [:div "..."])
  ; =>
  ; false
  ;
  ; @usage
  ; (defn my-component [] ...)
  ; (component? [my-component "..."])
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n]
  (and (-> n vector?)
       (-> n first fn?)))
