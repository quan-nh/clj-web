(ns clj-web.db
  (:require [next.jdbc.connection :as connection])
  (:import (com.stuartsierra.component Lifecycle)
           (com.zaxxer.hikari HikariDataSource)))

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