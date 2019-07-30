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
