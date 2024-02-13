
(ns reagent.dev.lifecycles.views
    (:require [reagent.core :as reagent]
              [reagent.dev.lifecycles.side-effects :as lifecycles.side-effects]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn create-class
  ; @description
  ; Implements the Reagent 'create-class' function and extends it with additional options such as hot reload preventing.
  ;
  ; @param (keyword)(opt) component-id
  ; @param (map) component-spec
  ; {:hot-reload-threshold (ms)(opt)
  ;   Default: 10
  ;  :prevent-hot-reload? (boolean)(opt)
  ;   Prevents the ':component-did-mount' and ':component-will-unmount' lifecycles fireing when the component rapidly remounts.
  ;   Requires a constant component ID!
  ;   Default: false
  ;  ...}
  ;
  ; @usage
  ; (create-class {:component-did-mount (fn [_] ...)
  ;                :reagent-render      (fn [_] [:div "My content"])
  ;                ...})
  ;
  ; @usage
  ; (create-class :my-component
  ;               {:component-did-mount (fn [_] ...)
  ;                :reagent-render      (fn [_] [:div "Hello World!"])
  ;                :prevent-hot-reload? true})
  ([component-spec]
   (reagent.core/create-class component-spec))

  ([component-id component-spec]
   (let [mount-id (random-uuid)]
        (-> component-spec (assoc :component-did-mount    (lifecycles.side-effects/component-did-mount-f    component-id component-spec mount-id))
                           (assoc :component-will-unmount (lifecycles.side-effects/component-will-unmount-f component-id component-spec mount-id))
                           (reagent/create-class)))))
