
(ns reagent.references.utils
    (:require [reagent.core :as core]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn arguments
  ; @description
  ; It retrieves the component's arguments and returns them as a list.
  ; This function is useful for accessing the arguments passed to a Reagent
  ; component during its lifecycle methods, like component-did-update.
  ;
  ; @param (?) this
  ; Expected to be a reference to a Reagent component
  ;
  ; @usage
  ; (reagent.core/create-class {:component-did-update (fn [this] (arguments this))})
  ;
  ; @return (*)
  [this]
  (-> this core/argv rest))
