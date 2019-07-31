(ns clj-web.app
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [com.stuartsierra.component :as component]
            [ring.middleware.defaults :refer :all]
            [muuntaja.middleware :as mw]
            [clojure.java.jdbc :as jdbc]))

(defn app-routes [db]
  (routes
   (GET "/" [] "Hello World!")
   (GET "/db" [] {:status 200
                  :body (jdbc/query db ["SELECT 3*5 AS result"])})
   (route/not-found "404")))

(defrecord App [handler db]
  component/Lifecycle
  (start [this]
    (assoc this :handler (-> (handler db)
                             (mw/wrap-format)
                             (wrap-defaults api-defaults))))
  (stop [this]
    (assoc this :handler nil)))

(defn new-app []
  (map->App {:handler app-routes}))
