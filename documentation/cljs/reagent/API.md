
# reagent.api ClojureScript namespace

##### [README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > reagent.api

### Index

- [arguments](#arguments)

- [component?](#component)

- [lifecycles](#lifecycles)

### arguments

```
@param (?) this
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
@warning
In product releases don't use the component-id parameter! Using the component-id
parameter is designed for prevents components reloading when they rapidly remount.
```

```
@param (keyword or string)(opt) component-id
@param (map) lifecycles
{...}
```

```
@usage
(lifecycles {...})
```

```
@usage
(lifecycles :my-component {...})
```

```
@return (?)
```

<details>
<summary>Source code</summary>

```
(defn lifecycles
  ([lifecycles]
   (reagent.core/create-class lifecycles))

  ([component-id {:keys [component-did-update reagent-render] :as lifecycles}]
   (let [mount-id (random-uuid)]
        (reagent.core/create-class {:component-did-mount    (fn []  (mount-f   component-id lifecycles mount-id))
                                    :component-will-unmount (fn []  (unmount-f component-id lifecycles mount-id))
                                    :component-did-update   (fn [%] (if component-did-update (component-did-update %)))
                                    :reagent-render         (fn []  (reagent-render))}))))
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

