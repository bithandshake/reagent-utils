
# reagent.api ClojureScript namespace

##### [README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > reagent.api

### Index

- [arguments](#arguments)

- [component?](#component)

- [lifecycles](#lifecycles)

### arguments

```
@description
It retrieves the component's arguments and returns them as a list.
This function is useful for accessing the arguments passed to a Reagent
component during its lifecycle methods, like component-did-update.
```

```
@param (?) this
Expected to be a reference to a Reagent component
```

```
@usage
(reagent.core/create-class {:component-did-update (fn [this] (arguments this))})
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn arguments
  [this]
  (-> this core/argv rest))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [reagent.api :refer [arguments]]))

(reagent.api/arguments ...)
(arguments             ...)
```

</details>

---

### component?

```
@description
Checks if the input 'n' is a vector containing the first element as a function
(indicating it might be a component).
Returns TRUE if the conditions are met, and FALSE otherwise.
```

```
@param (*) 
```

```
@example
(component? [:div "..."])
=>
false
```

```
@example
(defn my-component [] ...)
(component? [my-component "..."])
=>
true
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn component?
  [n]
  (and (-> n vector?)
       (-> n first fn?)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [reagent.api :refer [component?]]))

(reagent.api/component? ...)
(component?             ...)
```

</details>

---

### lifecycles

```
@description
Implements the Reagent 'create-class' function and extends it with additional
options such as ':prevent-hot-reload?'.
The 'component-id' parameter is only required if the hot reload preventing
is turned on.
```

```
@param (keyword or string)(opt) component-id
@param (map) lifecycles
{...}
@param (map)(opt) options
{:hot-reload-threshold (ms)(opt)
  Default: 10
 :prevent-hot-reload? (boolean)(opt)
  Prevents the component reloading when it rapidly remounts by a hot code
  reloader (e.g. Shadow-CLJS).
  Requires a constant component ID!
  Default: false}
```

```
@usage
(lifecycles {...})
```

```
@usage
(lifecycles :my-component {...} {...})
```

```
@usage
(lifecycles {:component-did-mount (fn [] ...)
             :reagent-render      (fn [] [:div "Hello World!"])})
```

```
@usage
(lifecycles :my-component
            {:component-did-mount (fn [] ...)
             :reagent-render      (fn [] [:div "Hello World!"])}
            {:prevent-hot-reload? true})
```

```
@return (Reagent component)
```

<details>
<summary>Source code</summary>

```
(defn component
  ([lifecycles]
   (reagent.core/create-class lifecycles))

  ([_ lifecycles]
   (reagent.core/create-class lifecycles))

  ([component-id {:keys [component-did-update reagent-render] :as lifecycles}
                 {:keys [prevent-hot-reload?]                 :as options}]

   (if-not prevent-hot-reload? (reagent.core/create-class lifecycles)

           (let [mount-id (random-uuid)]
                (reagent.core/create-class {:component-did-mount    (fn []  (lifecycles.side-effects/mount-f   component-id lifecycles options mount-id))
                                            :component-will-unmount (fn []  (lifecycles.side-effects/unmount-f component-id lifecycles options mount-id))
                                            :component-did-update   (fn [%] (if component-did-update (component-did-update %)))
                                            :reagent-render         (fn []  (reagent-render))})))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [reagent.api :refer [lifecycles]]))

(reagent.api/lifecycles ...)
(lifecycles             ...)
```

</details>

---

This documentation is generated with the [clj-docs-generator](https://github.com/bithandshake/clj-docs-generator) engine.

