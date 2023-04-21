
(ns reagent.api
    (:require [reagent.core       :as core]
              [reagent.dom.client :as dom.client]
              [reagent.dom.server :as dom.server]
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

; reagent.dom.client
(def create-root dom.client/create-root)

; reagent.dom.server
(def render-to-string dom.server/render-to-string)

; reagent.utils
(def component? utils/component?)
