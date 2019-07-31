(ns clj-web.db.foo
  (:require [clojure.java.jdbc :as jdbc]))

(defn bar [db]
  (first (jdbc/query db ["SELECT 3*5 AS result"])))
