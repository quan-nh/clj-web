add config

`[aero "1.1.3"]`

`resources\config.edn`
```clj
{:db-spec
 {:classname   "org.h2.Driver"
  :subprotocol "h2:mem"                  ; the prefix `jdbc:` is added automatically
  :subname     "demo;DB_CLOSE_DELAY=-1"  ; `;DB_CLOSE_DELAY=-1` very important!!!
                    ; http://www.h2database.com/html/features.html#in_memory_databases
  :user        "sa"                      ; default "system admin" user
  :password    ""                        ; default password => empty string
  }
}
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
  component/Lifecycle
  (start [this]
    (let [db-spec (get-in config [:config :db-spec])
          cpds (doto (ComboPooledDataSource.)
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

