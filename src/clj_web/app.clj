(ns clj-web.app
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [com.stuartsierra.component :as component]
            [ring.middleware.defaults :refer :all]
            [muuntaja.middleware :as mw]
            [clj-web.handler.foo :as foo-handler]
            [toucan.db]))

(defn app-routes []
  (routes
   (GET "/" [] "Hello World!")
   (GET "/db" req (foo-handler/bar req))
   (route/not-found "404")))

(defrecord App [handler db]
  component/Lifecycle
  (start [this]
    (toucan.db/set-default-db-connection! db)
    (assoc this :handler (-> handler
                             (mw/wrap-format)
                             (wrap-defaults api-defaults))))
  (stop [this]
    (assoc this :handler nil)))

(defn new-app []
  (map->App {:handler app-routes}))
