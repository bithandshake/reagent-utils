
(ns reagent.dev.lifecycles.side-effects
    (:require [reagent.dev.lifecycles.state :as lifecycles.state]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn component-did-mount-f
  ; @ignore
  ;
  ; @description
  ; Prevents firing the given 'component-did-mount' function if ...
  ; ... the component (identified by the given component ID) is already mounted,
  ; ... the ':prevent-hot-reload?' property is provided as TRUE.
  ;
  ; @param (keyword) mount-id
  ; @param (map) component-spec
  ; {:component-did-mount (function)(opt)
  ;  :component-id (keyword)(opt)
  ;  :prevent-hot-reload? (boolean)(opt)
  ;  ...}
  [mount-id {:keys [component-did-mount component-id prevent-hot-reload?]}]
  ; @param (list of *) params
  (fn [& params]
      (let [mounted-as (get @lifecycles.state/MOUNTED-COMPONENTS component-id)]
           (cond (-> prevent-hot-reload? not) (if component-did-mount (apply component-did-mount params))
                 (-> mounted-as nil?)         (if component-did-mount (apply component-did-mount params))
                 :else :component-already-mounted)
           (swap! lifecycles.state/MOUNTED-COMPONENTS assoc component-id mount-id))))

(defn component-will-unmount-f
  ; @ignore
  ;
  ; @description
  ; Prevents firing the given 'component-will-unmount' function if ...
  ; ... the component (identified by the given component ID) has been re-mounted during the hot reload threshold period,
  ; ... the ':prevent-hot-reload?' property is provided as TRUE.
  ;
  ; @param (keyword) mount-id
  ; @param (map) component-spec
  ; {:component-id (keyword)(opt)
  ;  :component-will-unmount (function)(opt)
  ;  :hot-reload-threshold (ms)(opt)
  ;   Default: 10
  ;  :prevent-hot-reload? (boolean)(opt)
  ;  ...}
  [mount-id {:keys [component-id component-will-unmount hot-reload-threshold prevent-hot-reload?] :or {hot-reload-threshold 10}}]
  ; @param (list of *) params
  (fn [& params]
      (letfn [(f0 [] (let [mounted-as (get @lifecycles.state/MOUNTED-COMPONENTS component-id)]
                          (cond (-> prevent-hot-reload? not) (if component-will-unmount (apply component-will-unmount params))
                                (-> mount-id (= mounted-as)) (if component-will-unmount (apply component-will-unmount params)))
                          (cond (-> prevent-hot-reload? not) (swap! lifecycles.state/MOUNTED-COMPONENTS dissoc component-id)
                                (-> mount-id (= mounted-as)) (swap! lifecycles.state/MOUNTED-COMPONENTS dissoc component-id))))]
             (cond (-> prevent-hot-reload? not) (.setTimeout js/window f0 0)
                   :wait                        (.setTimeout js/window f0 hot-reload-threshold)))))
