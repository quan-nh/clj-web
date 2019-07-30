(ns clj-web.db
  (:require [com.stuartsierra.component :as component])
  (:import (com.mchange.v2.c3p0 ComboPooledDataSource)))

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
