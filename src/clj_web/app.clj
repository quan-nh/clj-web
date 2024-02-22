(ns clj-web.app
  (:require [clojure.tools.logging :as log]
            [com.stuartsierra.component :as component]
            [next.jdbc :as jdbc]
            [reitit.ring :as ring]
            [muuntaja.core :as m]
            [reitit.ring.middleware.muuntaja :as muuntaja]))

(defn ok-handler [_]
  {:status 200, :body "ok"})

(defn db-handler [req db]
  (log/debug req)
  {:status 200, :body (jdbc/execute! db ["SELECT 3*5 AS result"])})

(defn app-handler [db]
  (ring/ring-handler
    (ring/router
      [["/" ok-handler]
       ["/db" #(db-handler % db)]]
      {:data {:muuntaja m/instance
              :middleware [muuntaja/format-middleware]}})
    (ring/create-default-handler)))

(defrecord App [handler db]
  component/Lifecycle
  (start [this]
    (assoc this :handler (handler db)))
  (stop [this]
    (assoc this :handler nil)))

(defn new-app []
  (map->App {:handler app-handler}))
