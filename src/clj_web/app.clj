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
