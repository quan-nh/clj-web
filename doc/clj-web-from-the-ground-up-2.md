Add the following dependency to your `project.clj` file:

```clj
[metosin/reitit "0.6.0"]
```

```clj
  (:require [reitit.ring :as ring])

# handler ..

(def app
  (ring/ring-handler
    (ring/router
      ["/" {:get handler}])
    (ring/create-default-handler)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (run-jetty app {:port 3000}))
```

adding middleware

Middleware can be mounted using a `:middleware` key - either to top-level or under request method submap.
```clj
(defn wrap [handler id]
  (fn [request]
    (handler (update request ::acc (fnil conj []) id))))

(defn handler [{::keys [acc]}]
  {:status 200, :body  (prn-str (conj acc :handler)) })

(def app
  (ring/ring-handler
    (ring/router
      ;; a middleware function
      ["/api" {:middleware [#(wrap % :api)]}
       ["/ping" handler]
       ;; a middleware vector at top level
       ["/admin" {:middleware [[wrap :admin]]}
        ["/db" {:middleware [[wrap :db]]
                ;; a middleware vector at under a method
                :delete {:middleware [[wrap :delete]]
                         :handler handler}}]]])
    (ring/create-default-handler)))
```

GET http://localhost:3000/api/ping ==> [:api :handler]
DELETE http://localhost:3000/api/admin/db ==> [:api :admin :db :delete :handler]
