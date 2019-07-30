(ns clj-web.core
  (:gen-class)
  (:require [ring.adapter.jetty :refer [run-jetty]]))

(defn handler [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "Hello World"})

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (run-jetty handler {:port 3000}))
