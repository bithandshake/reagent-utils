
(ns reagent.tools.api
    (:require [reagent.tools.core.utils       :as core.utils]
              [reagent.tools.references.utils :as references.utils]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @redirect (reagent.tools.core.utils/*)
(def component? core.utils/component?)

; @redirect (reagent.tools.references.utils/*)
(def arguments references.utils/arguments)
