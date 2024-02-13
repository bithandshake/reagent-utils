
(ns reagent.tools.references.utils
    (:require [reagent.core :as core]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn arguments
  ; @description
  ; Returns the argument list of the given Reagent component object.
  ;
  ; @param (Reagent component object) this
  ; Expected to be a reference to a Reagent component.
  ;
  ; @usage
  ; (reagent.core/create-class {:component-did-update (fn [this] (arguments this))})
  ;
  ; @return (list)
  [n]
  (-> n core/argv rest))
