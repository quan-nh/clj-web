new app
```
$ lein new app clj-web
```

update dependencies
```clj
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [ring/ring-core "1.8.0"]
                 [ring/ring-jetty-adapter "1.8.0"]]
```

add basic handler
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

`lein run` http://localhost:3000/
