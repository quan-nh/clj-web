(ns clj-web.handler.foo
  (:require [clojure.tools.logging :as log]
            [clj-web.db.foo :as foo-db]
            [ring.util.http-response :refer :all]
            [clojure.spec.alpha :as s]
            [clj-web.handler.spec :as hs]))

(defn bar [req db]
  (log/debug req)
  (ok (foo-db/bar db)))

(defn data [body]
  (if (s/valid? ::hs/data-request body)
    (ok (s/conform ::hs/data-request body))
    (bad-request (s/explain-str ::hs/data-request body))))
