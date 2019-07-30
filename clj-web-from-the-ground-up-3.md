## component

add deps
```
[com.stuartsierra/component "0.4.0"]
[ring-jetty-component "0.3.1"]
```

```clj
(:require [ring.component.jetty :refer [jetty-server]]
          [com.stuartsierra.component :as component])

(defn app-system
  []
  (component/system-map
   :http (jetty-server {:app {:handler (wrap-defaults app-routes site-defaults)} :port 3000})))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (component/start (app-system)))
```

`lein run`

## reloadable workflow
add dep `[reloaded.repl "0.2.4"]`

add dev profile
```clj
:profiles {:dev {:source-paths ["dev"]}
           ..} 
:repl-options {:init-ns user}
```

`dev/user.clj`
```clj
(ns user
  (:require [reloaded.repl :refer [system init start stop go reset reset-all]]
            [clj-web.core :refer [app-system]]))

(reloaded.repl/set-init! app-system)
```

`lein repl`
You can now manipulate the system in the REPL: `(go)/(start)`, `(reset)` or `(stop)`

Change the code & `(reset)` to see if it affect!
