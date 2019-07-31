api

`[metosin/muuntaja "0.6.4"]`

`app.clj`
```clj
(:require [muuntaja.middleware :as mw])

(defrecord App [handler db]
  component/Lifecycle
  (start [this]
    (assoc this :handler (-> (handler db)
                             (mw/wrap-format)
                             (wrap-defaults api-defaults))))
  (stop [this]
    (assoc this :handler nil)))
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
