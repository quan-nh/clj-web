(ns user
  (:require [reloaded.repl :refer [system init start stop go reset reset-all]]
            [clj-web.main :refer [app-system]]))

(reloaded.repl/set-init! app-system)
