new app
```
$ lein new app clj-web
```

update dependencies on `project.clj`
```clj
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [ring/ring-core "1.11.0-RC1"]
                 [ring/ring-jetty-adapter "1.11.0-RC1"]]
```

add basic handler to `src\clj_web\core.clj`
```clj
(defn handler [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "Hello World"})
```

start on `-main`
```clj
(ns clj-web.core
  (:gen-class)
  (:require [ring.adapter.jetty :refer [run-jetty]]))

..

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (run-jetty handler {:port 3000}))
```

`lein run` & the app can be accessed at http://localhost:3000/
