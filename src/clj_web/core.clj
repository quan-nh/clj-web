(ns clj-web.core
  (:gen-class)
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.adapter.jetty :refer [run-jetty]]))

(defroutes app-routes
  (GET "/" [] "Hello World")
  (route/not-found "Page not found"))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (-> app-routes
      (run-jetty {:port 3000})))
