
(ns reagent.lifecycles.side-effects
    (:require [reagent.core             :as core]
              [reagent.lifecycles.state :as lifecycles.state]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn mount-f
  ; @ignore
  ;
  ; @description
  ; First checks if the component with the given 'component-id' has already been
  ; mounted. If it has, it returns ':component-already-mounted'. If not, it proceeds
  ; to execute the 'component-did-mount' function if it exists. Afterward, it adds
  ; the component's identifier along with the 'mount-id' to a list of mounted
  ; components stored in the state.
  ;
  ; This function is designed for handling component mounting and ensuring that
  ; the 'component-did-mount' function is executed only once for a given component.
  ;
  ; @param (keyword or string) component-id
  ; @param (map) lifecycles
  ; {:component-did-mount (function)(opt)}
  ; @param (map) options
  ; @param (string) mount-id
  [component-id {:keys [component-did-mount]} _ mount-id]
  (if-let [mounted-as (get @lifecycles.state/MOUNTED-COMPONENTS component-id)]
          :component-already-mounted
          (if component-did-mount (component-did-mount)))
  (swap! lifecycles.state/MOUNTED-COMPONENTS assoc component-id mount-id))

(defn unmount-f
  ; @ignore
  ;
  ; @description
  ; Sets up a delayed unmounting process, waiting for 10 milliseconds to see
  ; if the 'mount-id' has changed during that time. If it hasn't changed,
  ; it proceeds to execute the 'component-will-unmount' function if it exists,
  ; and then removes the component's identifier from a list of mounted components.
  ;
  ; This function is designed for handling component unmounting with a safeguard
  ; against rapid remounting.
  ;
  ; @param (keyword or string) component-id
  ; @param (map) lifecycles
  ; {:component-will-unmount (function)(opt)}
  ; @param (map) options
  ; {:hot-reload-threshold (ms)}
  ; @param (string) mount-id
  [component-id {:keys [component-will-unmount]} {:keys [hot-reload-threshold] :or {hot-reload-threshold 10}} mount-id]
  ; To recognise rapid remounting ...
  ; ... the unmounting function has to be delayed (e.g., 10ms).
  ; ... if the mount-id (it's not the same as the component-id) changed during
  ;     this short while, it's declared as a rapid remounting event and the
  ;     unmounting function will be ignored.
  (letfn [(f0 [] (when (= mount-id (get @lifecycles.state/MOUNTED-COMPONENTS component-id))
                       (if component-will-unmount (component-will-unmount))
                       (swap! lifecycles.state/MOUNTED-COMPONENTS dissoc component-id)))]
         (.setTimeout js/window f0 hot-reload-threshold)))
