
(ns reagent.api
    (:require [reagent.core             :as core]
              [reagent.core.utils       :as core.utils]
              [reagent.dom.client       :as dom.client]
              [reagent.lifecycles.views :as lifecycles.views]
              [reagent.references.utils :as references.utils]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @redirect (reagent.core)
(def adapt-react-class core/adapt-react-class)
(def after-render      core/after-render)
(def as-element        core/as-element)
(def ratom             core/atom)

; @redirect (reagent.core.utils)
(def component? core.utils/component?)

; @redirect (reagent.lifecycles.views)
(def lifecycles lifecycles.views/component)

; @redirect (reagent.dom.client)
(def create-root dom.client/create-root)

; @redirect (reagent.references.utils)
(def arguments references.utils/arguments)
