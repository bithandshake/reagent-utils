
(ns reagent.dev.lifecycles.views
    (:require [reagent.core :as reagent]
              [reagent.dev.lifecycles.side-effects :as lifecycles.side-effects]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn create-class
  ; @description
  ; Implements the Reagent 'create-class' function and extends it with additional options such as hot reload preventing.
  ;
  ; @param (map) spec
  ; {:component-id (keyword)(opt)
  ;  :hot-reload-threshold (ms)(opt)
  ;   Default: 10
  ;  :prevent-hot-reload? (boolean)(opt)
  ;   Prevents the ':component-did-mount' and ':component-will-unmount' lifecycles fireing when the component rapidly remounts.
  ;   Requires a constant component ID!
  ;   Default: false
  ;  ...}
  ; @param (Reagent compiler object)(opt) compiler
  ;
  ; @usage
  ; (create-class {:component-did-mount (fn [_] ...)
  ;                :reagent-render      (fn [_] [:div "My content"])
  ;                ...})
  ;
  ; @usage
  ; (create-class {:component-did-mount (fn [_] ...)
  ;                :reagent-render      (fn [_] [:div "Hello World!"])
  ;                :component-id        :my-component
  ;                :prevent-hot-reload? true
  ;                ...})
  ;
  ; @return (function)
  [spec & [compiler]]
  (let [mount-id (random-uuid)]
       (-> spec (assoc :component-did-mount    (lifecycles.side-effects/component-did-mount-f    mount-id spec))
                (assoc :component-will-unmount (lifecycles.side-effects/component-will-unmount-f mount-id spec))
                (reagent/create-class))))
