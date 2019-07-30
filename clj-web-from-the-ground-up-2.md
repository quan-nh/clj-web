Add the following dependency to your `project.clj` file:

```clj
[compojure "1.6.1"]
```

```clj
  (:require [compojure.core :refer :all]
            [compojure.route :as route])

(defroutes app-routes
  (GET "/" [] "Hello World")
  (route/not-found "Page not found"))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (-> app-routes
      (run-jetty {:port 3000})))
```
