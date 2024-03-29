(ns clj-web.main
  (:gen-class)
  (:require [ring.component.jetty :refer [jetty-server]]
            [com.stuartsierra.component :as component]
            [clj-web.app :refer [new-app]]
            [clj-web.config :refer [new-config]]
            [clj-web.db :refer [new-database]]))

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

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (component/start (app-system)))
