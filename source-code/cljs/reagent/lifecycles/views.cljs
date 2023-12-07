
(ns reagent.lifecycles.views
    (:require [reagent.core                    :as core]
              [reagent.lifecycles.side-effects :as lifecycles.side-effects]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn component
  ; @description
  ; Implements the Reagent 'create-class' function and extends it with additional
  ; options such as ':prevent-hot-reload?'.
  ;
  ; The 'component-id' parameter is only required if the hot reload preventing
  ; is turned on.
  ;
  ; @param (keyword or string)(opt) component-id
  ; @param (map) lifecycles
  ; {...}
  ; @param (map)(opt) options
  ; {:hot-reload-threshold (ms)(opt)
  ;   Default: 10
  ;  :prevent-hot-reload? (boolean)(opt)
  ;   Prevents the component reloading when it rapidly remounts by a hot code
  ;   reloader (e.g., Shadow-CLJS).
  ;   Requires a constant component ID!
  ;   Default: false}
  ;
  ; @usage
  ; (lifecycles {...})
  ;
  ; @usage
  ; (lifecycles :my-component {...} {...})
  ;
  ; @usage
  ; (lifecycles {:component-did-mount (fn [] ...)
  ;              :reagent-render      (fn [] [:div "Hello World!"])})
  ;
  ; @usage
  ; (lifecycles :my-component
  ;             {:component-did-mount (fn [] ...)
  ;              :reagent-render      (fn [] [:div "Hello World!"])}
  ;             {:prevent-hot-reload? true})
  ;
  ; @return (Reagent component)
  ([lifecycles]
   (reagent.core/create-class lifecycles))

  ([_ lifecycles]
   (reagent.core/create-class lifecycles))

  ([component-id {:keys [component-did-update reagent-render] :as lifecycles}
                 {:keys [prevent-hot-reload?]                 :as options}]

   (if-not prevent-hot-reload? (reagent.core/create-class lifecycles)

           ; By passing the 'component-id' parameter and the ':prevent-hot-reload?' option
           ; to this function it can store the ID in the MOUNTED-COMPONENTS atom and when
           ; the component rapidly remounts it can prevent the 'component-did-mount' and
           ; 'component-will-unmount' lifecycles to be happened repeatedly.
           ; This function only prevents lifecycles to be happened in case of the remounting
           ; happens rapidly.
           ;
           ; This solution has nothing to do with product releases, it only helps if you want
           ; to prevent the component lifecycles to be happened repeatedly by a hot code reloader.
           (let [mount-id (random-uuid)]
                (reagent.core/create-class {:component-did-mount    (fn []  (lifecycles.side-effects/mount-f   component-id lifecycles options mount-id))
                                            :component-will-unmount (fn []  (lifecycles.side-effects/unmount-f component-id lifecycles options mount-id))
                                            :component-did-update   (fn [%] (if component-did-update (component-did-update %)))
                                            :reagent-render         (fn []  (reagent-render))})))))
