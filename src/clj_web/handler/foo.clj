(ns clj-web.handler.foo
  (:require [clojure.tools.logging :as log]
            [clj-web.db.foo :as foo-db]
            [ring.util.http-response :refer :all]))

(defn bar [req db]
  (log/debug req)
  (ok (foo-db/bar db)))
