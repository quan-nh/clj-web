(ns clj-web.handler.spec
  (:require [clojure.spec.alpha :as s]))

(s/def ::first-name string?)
(s/def ::last-name string?)
(s/def ::phone string?)

(s/def ::data-request
  (s/keys :req-un [::first-name ::last-name]
          :opt-un [::phone]))