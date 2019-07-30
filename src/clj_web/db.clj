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
