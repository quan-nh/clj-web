api

https://cljdoc.org/d/metosin/reitit/0.6.0/doc/ring/content-negotiation

`app.clj`
```clj
(:require [muuntaja.core :as m]
            [reitit.ring.middleware.muuntaja :as muuntaja])

(defn db-handler [req db]
  {:status 200, :body (jdbc/execute! db ["SELECT 3*5 AS result"])})

(defn app-handler [db]
  (ring/ring-handler
    (ring/router
      [["/" ok-handler]
       ["/db" #(db-handler % db)]]
      {:data {:muuntaja m/instance
              :middleware [muuntaja/format-middleware]}})
    (ring/create-default-handler)))
```

json request
```sh
$ http http://localhost:3000/db 
HTTP/1.1 200 OK
Content-Length: 15
Content-Type: application/json; charset=utf-8
Date: Wed, 31 Jul 2019 03:54:25 GMT
Server: Jetty(9.2.10.v20150310)

[
    {
        "result": 15
    }
]
```

edn request
```sh
$ http http://localhost:3000/db Accept:application/edn
HTTP/1.1 200 OK
Content-Length: 14
Content-Type: application/edn; charset=utf-8
Date: Wed, 31 Jul 2019 03:55:32 GMT
Server: Jetty(9.2.10.v20150310)

({:result 15})
```
