
(ns reagent.references
    (:require [reagent.core :as core]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn arguments
  ; @param (?) this
  ;
  ; @usage
  ; (reagent.core/create-class {:component-did-update (fn [this] (arguments this))})
  ;
  ; @return (*)
  [this]
  (-> this core/argv rest))
