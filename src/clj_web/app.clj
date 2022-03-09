(ns clj-web.app
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [com.stuartsierra.component :as component]
            [ring.middleware.defaults :refer :all]
            [muuntaja.middleware :as mw]
            [clj-web.handler.foo :as foo-handler]))

(defn app-routes [db]
  (routes
   (GET "/" [] "Hello World!")
   (GET "/db" req (foo-handler/bar req db))
   (POST "/data" {:keys [body-params]} (foo-handler/data body-params))
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
