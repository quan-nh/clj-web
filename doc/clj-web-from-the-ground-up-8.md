add db layer

add deps
```
[org.clojure/java.jdbc "0.7.9"]
[com.h2database/h2 "1.4.199"]
[com.mchange/c3p0 "0.9.5.4"]
```

`db.clj`
```clj
(ns clj-web.db
  (:require [com.stuartsierra.component :as component])
  (:import (com.mchange.v2.c3p0 ComboPooledDataSource)))

(def db-spec
  {:classname   "org.h2.Driver"
   :subprotocol "h2:mem"                  ; the prefix `jdbc:` is added automatically
   :subname     "demo;DB_CLOSE_DELAY=-1"  ; `;DB_CLOSE_DELAY=-1` very important!!!
                    ; http://www.h2database.com/html/features.html#in_memory_databases
   :user        "sa"                      ; default "system admin" user
   :password    ""                        ; default password => empty string
   })

(defrecord Database [db-spec datasource]
  component/Lifecycle
  (start [this]
    (let [cpds (doto (ComboPooledDataSource.)
                 (.setDriverClass (:classname db-spec))
                 (.setJdbcUrl (str "jdbc:" (:subprotocol db-spec) ":" (:subname db-spec)))
                 (.setUser (:user db-spec))
                 (.setPassword (:password db-spec))
               ;; expire excess connections after 30 minutes of inactivity:
                 (.setMaxIdleTimeExcessConnections (* 30 60))
               ;; expire connections after 3 hours of inactivity:
                 (.setMaxIdleTime (* 3 60 60)))]
      (assoc this :datasource cpds)))
  (stop [this]
    (.close datasource)
    (assoc this :datasource nil)))

(defn new-database []
  (map->Database {:db-spec db-spec}))
```

`app.clj`
```clj
(ns clj-web.app
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [com.stuartsierra.component :as component]
            [ring.middleware.defaults :refer :all]
            [clojure.java.jdbc :as jdbc]))

(defn app-routes [db]
  (routes
   (GET "/" [] "Hello World!")
   (GET "/db" [] {:status 200
                  :headers {"Content-Type" "text/html"}
                  :body (jdbc/query db ["SELECT 3*5 AS result"])})
   (route/not-found "404")))

(defrecord App [handler db]
  component/Lifecycle
  (start [this]
    (assoc this :handler (-> (handler db)
                             (wrap-defaults site-defaults))))
  (stop [this]
    (assoc this :handler nil)))

(defn new-app []
  (map->App {:handler app-routes}))
```

system
```clj
(defn app-system
  []
  (-> (component/system-map
       :db (new-database)
       :app (new-app)
       :http (jetty-server {:port 3000}))
      (component/system-using
       {:http [:app]
        :app [:db]})))
```
