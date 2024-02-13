
(ns reagent.tools.references.utils
    (:require [reagent.core :as core]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn arguments
  ; @description
  ; Takes the arguments of a Reagent component and returns them as a list.
  ;
  ; @param (Reagent component object) this
  ; Expected to be a reference to a Reagent component.
  ;
  ; @usage
  ; (reagent.core/create-class {:component-did-update (fn [this] (arguments this))})
  ;
  ; @return (list)
  [this]
  (-> this core/argv rest))
