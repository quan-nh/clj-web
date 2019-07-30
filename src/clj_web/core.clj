(ns clj-web.core
  (:gen-class)
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.component.jetty :refer [jetty-server]]
            [com.stuartsierra.component :as component]
            [ring.middleware.defaults :refer :all]))

(defroutes app-routes
  (GET "/" [] "Hello World!")
  (route/not-found "Page not found"))

(defn app-system
  []
  (component/system-map
   :http (jetty-server {:app {:handler (wrap-defaults app-routes site-defaults)} :port 3000})))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (component/start (app-system)))
