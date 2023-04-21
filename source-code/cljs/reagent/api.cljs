
(ns reagent.api
    (:require [reagent.core       :as core]
              [reagent.dom        :as dom]
              [reagent.lifecycles :as lifecycles]
              [reagent.references :as references]
              [reagent.utils      :as utils]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; reagent.lifecycles
(def lifecycles lifecycles/lifecycles)

; reagent.references
(def arguments references/arguments)

; reagent.core
(def adapt-react-class core/adapt-react-class)
(def after-render      core/after-render)
(def as-element        core/as-element)
(def ratom             core/atom)

; reagent.dom
(def render dom/render)

; reagent.utils
(def component? utils/component?)
