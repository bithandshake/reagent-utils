
# reagent-api

### Overview

The <strong>reagent-api</strong> library is a set of simple development tools made
for working with Reagent.

### deps.edn

```
{:deps {bithandshake/reagent-api {:git/url "https://github.com/bithandshake/reagent-api"
                                  :sha     "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"}}
```

### Current version

Check out the latest commit on the [release branch](https://github.com/bithandshake/reagent-api/tree/release).

### Documentation

The <strong>reagent-api</strong> functional documentation is [available here](documentation/COVER.md).

### Changelog

You can track the changes of the <strong>reagent-api</strong> library [here](CHANGES.md).

# Usage

### Index

- [How to typecheck a Reagent component?](#how-to-typecheck-a-reagent-component)

- [How to read the updated component parameters when they get changed?](#how-to-read-the-updated-component-parameters-when-they-get-changed)

- [How to prevent hot reloading a component?](#how-to-prevent-hot-reloading-a-component)

### How to typecheck a Reagent component?

The [`component?`](documentation/cljs/reagent/API.md/#component) function
checks if the input parameter might be a Reagent component (a vector containing
the first element as a function).

```
(defn my-component [] [:div "Hello World!"])
(component? [my-component])
; =>
; true
```

### How to read the updated component parameters when they get changed?

The [`arguments`](documentation/cljs/reagent/API.md/#arguments) function
retrieves the component's arguments and returns them as a list.

```
(defn my-did-update-f [this] (-> this arguments println))
(defn my-component []
  (reagent.core/create-class {:component-did-update (fn [this] (my-did-update-f this))
                              :reagent-render       (fn [] [:div "Hello World!"])}))
```

### How to prevent hot reloading a component?

The [`lifecycles`](documentation/cljs/reagent/API.md/#lifecycles) function
implements the Reagent 'create-class' function and extends it with additional
options such as ':prevent-hot-reload?' that could prevent the component being
reloaded by a hot code reloader.

The component ID as first parameter is required if the hot reload preventing is turned on.

```
(lifecycles :my-component
            {:component-did-mount (fn [] ...)
             :reagent-render      (fn [] [:div "Hello World!"])}
            {:prevent-hot-reload? true})
```
