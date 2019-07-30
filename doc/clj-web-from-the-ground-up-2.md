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

add some middlewares
```clj
[ring/ring-defaults "0.3.2"]
```

```clj
  (:require [ring.middleware.defaults :refer :all])

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (-> app-routes
      (wrap-defaults site-defaults)
      (run-jetty {:port 3000})))
```
