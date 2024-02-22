add db layer

add deps
```
[com.github.seancorfield/next.jdbc "1.3.909"]
[com.h2database/h2 "2.2.224"]
[com.zaxxer/HikariCP "5.1.0"]
```

`db.clj`
```clj
(ns clj-web.db
  (:import (com.stuartsierra.component Lifecycle)
           (com.zaxxer.hikari HikariDataSource)))

(def db-spec {:dbtype "h2" :dbname "example"})

(defrecord Database [db-spec datasource]
  Lifecycle
  (start [this]
    (assoc this :datasource (connection/->pool HikariDataSource db-spec)))
  (stop [this]
    (.close datasource)
    (assoc this :datasource nil)))

(defn new-database []
  (map->Database {:db-spec db-spec}))
```

split `core.clj` to `app.clj` & `main.clj`

`app.clj`
```clj
(ns clj-web.app
  (:require [com.stuartsierra.component :as component]
            [next.jdbc :as jdbc]
            [reitit.ring :as ring]))

(defn ok-handler [_]
  {:status 200, :body "ok"})

(defn db-handler [req db]
  {:status 200, :body (prn-str (jdbc/execute! db ["SELECT 3*5 AS result"]))})

(defn app-handler [db]
  (ring/ring-handler
    (ring/router
      [["/" ok-handler]
       ["/db" #(db-handler % db)]])
    (ring/create-default-handler)))

(defrecord App [handler db]
  component/Lifecycle
  (start [this]
    (assoc this :handler (handler db)))
  (stop [this]
    (assoc this :handler nil)))

(defn new-app []
  (map->App {:handler app-handler}))
```

`main.clj`
```clj
(ns clj-web.main
  (:gen-class)
  (:require [ring.component.jetty :refer [jetty-server]]
            [com.stuartsierra.component :as component]
            [clj-web.app :refer [new-app]]
            [clj-web.db :refer [new-database]]))

(defn app-system
  []
  (-> (component/system-map
        :db (new-database)
        :app (new-app)
        :http (jetty-server {:port 3000}))
      (component/system-using
        {:http [:app]
         :app [:db]})))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (component/start (app-system)))
```
