
(ns reagent.api
    (:require [reagent.core             :as core]
              [reagent.core.utils       :as core.utils]
              [reagent.dom.client       :as dom.client]
              [reagent.dom.server       :as dom.server]
              [reagent.lifecycles.views :as lifecycles.views]
              [reagent.references.utils :as references.utils]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; reagent.core
(def adapt-react-class core/adapt-react-class)
(def after-render      core/after-render)
(def as-element        core/as-element)
(def ratom             core/atom)

; reagent.core.utils
(def component? core.utils/component?)

; reagent.lifecycles.views
(def lifecycles lifecycles.views/component)

; reagent.dom.client
(def create-root dom.client/create-root)

; reagent.dom.server
(def render-to-string dom.server/render-to-string)

; reagent.references.utils
(def arguments references.utils/arguments)
