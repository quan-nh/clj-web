(defproject clj-web "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 ; ring
                 [ring/ring-core "1.11.0"]
                 [ring/ring-jetty-adapter "1.11.0"]
                 [metosin/reitit "0.6.0"]
                 ; component
                 [com.stuartsierra/component "1.1.0"]
                 [ring-jetty-component "0.3.1"]
                 [reloaded.repl "0.2.4"]
                 ; db
                 [com.github.seancorfield/next.jdbc "1.3.909"]
                 [com.h2database/h2 "2.2.224"]
                 [com.zaxxer/HikariCP "5.1.0"]
                 ; config
                 [aero "1.1.6"]
                 ; logging
                 [org.clojure/tools.logging "1.3.0"]
                 [ch.qos.logback/logback-classic "1.5.0"]]
  :main ^:skip-aot clj-web.core
  :target-path "target/%s"
  :profiles {:dev {:source-paths ["dev"]}
             :uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}}
  :repl-options {:init-ns user})
