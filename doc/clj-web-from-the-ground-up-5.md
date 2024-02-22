add config

`[aero "1.1.6"]`

`resources\config.edn`
```clj
{:db-spec {:dbtype "h2" :dbname "example"}}
```

`config.clj`
```clj
(ns clj-web.config
  (:require [com.stuartsierra.component :as component]
            [aero.core :refer (read-config)]))

(defrecord Config [config-file config]
  component/Lifecycle
  (start [this]
    (assoc this :config (read-config (clojure.java.io/resource config-file))))
  (stop [this]
    (assoc this :config nil)))

(defn new-config []
  (map->Config {:config-file "config.edn"}))
```

`db.clj`
```clj
(defrecord Database [datasource config]
  Lifecycle
  (start [this]
    (let [db-spec (get-in config [:config :db-spec])]
      (assoc this :datasource (connection/->pool HikariDataSource db-spec))))
  (stop [this]
    (.close datasource)
    (assoc this :datasource nil)))

(defn new-database []
  (map->Database {}))
```

system
```clj
(defn app-system
  []
  (-> (component/system-map
       :config (new-config)
       :db (new-database)
       :app (new-app)
       :http (jetty-server {:port 3000}))
      (component/system-using
       {:http [:app]
        :app [:db]
        :db [:config]})))
```
