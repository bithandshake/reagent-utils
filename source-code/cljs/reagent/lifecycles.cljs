
(ns reagent.lifecycles
    (:require [reagent.core  :as core]
              [reagent.state :as state]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- mount-f
  ; @ignore
  ;
  ; @param (keyword or string) component-id
  ; @param (map) lifecycles
  ; {:component-did-mount (function)(opt)}
  ; @param (string) mount-id
  [component-id {:keys [component-did-mount]} mount-id]
  (if-let [mounted-as (get @state/MOUNTED-COMPONENTS component-id)]
          :component-already-mounted
          (if component-did-mount (component-did-mount)))
  (swap! state/MOUNTED-COMPONENTS assoc component-id mount-id))

(defn- unmount-f
  ; @ignore
  ;
  ; @param (keyword or string) component-id
  ; @param (map) lifecycles
  ; {:component-will-unmount (function)(opt)}
  ; @param (string) mount-id
  [component-id {:keys [component-will-unmount]} mount-id]
  ; To recognise rapid remounting ...
  ; ... the unmounting function has to be delayed (e.g. 10ms).
  ; ... if the mount-id (it's not the same as the component-id) changed during
  ;     this short while, it's declared as a rapid remounting event and the
  ;     unmounting function will be ignored.
  (letfn [(f [] (when (= mount-id (get @state/MOUNTED-COMPONENTS component-id))
                      (if component-will-unmount (component-will-unmount))
                      (swap! state/MOUNTED-COMPONENTS dissoc component-id)))]
         (.setTimeout js/window f 10)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn lifecycles
  ; @warning
  ; In product releases don't use the component-id parameter! You can find more
  ; informations below.
  ;
  ; @param (keyword or string)(opt) component-id
  ; @param (map) lifecycles
  ; {...}
  ;
  ; @usage
  ; (lifecycles {...})
  ;
  ; @usage
  ; (lifecycles :my-component {...})
  ;
  ; @return (?)
  ([lifecycles]
   (reagent.core/create-class lifecycles))

  ([component-id {:keys [component-did-update reagent-render] :as lifecycles}]
   ; By passing the component-id to this function it can stores the ID in the MOUNTED-COMPONENTS
   ; atom and when the component rapidly remounts (e.g. because of the Shadow CLJS) it can
   ; prevent the component-did-mount and component-will-unmount lifecycles to be happened repeatedly.
   ; This function only prevents lifecycles to be happened in case of the remounting happens
   ; rapidly (if max. 10ms elapses between the unmount and the remount).
   ;
   ; This solution has nothing to do with product releases, it only helps when you want
   ; to prevent the lifecycles (of a Reagent component) to be happened when a development
   ; tool updates the React tree (e.g. in case of the source code changes).
   (let [mount-id (random-uuid)]
        (reagent.core/create-class {:component-did-mount    (fn []  (mount-f   component-id lifecycles mount-id))
                                    :component-will-unmount (fn []  (unmount-f component-id lifecycles mount-id))
                                    :component-did-update   (fn [%] (if component-did-update (component-did-update %)))
                                    :reagent-render         (fn []  (reagent-render))}))))
