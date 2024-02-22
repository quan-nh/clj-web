(defproject clj-web "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [com.stuartsierra/component "1.1.0"]
                 [reloaded.repl "0.2.4"]
                 ; web
                 [ring/ring-core "1.11.0"]
                 [ring/ring-jetty-adapter "1.11.0"]
                 [ring-jetty-component "0.3.1"]
                 [metosin/reitit "0.6.0"]
                 [metosin/muuntaja "0.6.4"]
                 [metosin/ring-http-response "0.9.1"]
                 ; config
                 [aero "1.1.3"]
                 ; db
                 [com.github.seancorfield/next.jdbc "1.3.909"]
                 [com.h2database/h2 "2.2.224"]
                 [com.zaxxer/HikariCP "5.1.0"]
                 ; logging
                 [org.clojure/tools.logging "0.5.0"]
                 [ch.qos.logback/logback-classic "1.2.3"]]
  :main ^:skip-aot clj-web.core
  :target-path "target/%s"
  :profiles {:dev {:source-paths ["dev"]}
             :uberjar {:aot :all}}
  :repl-options {:init-ns user})
