(defproject bridge-calendar-service "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [cheshire "5.5.0"]
                 [gapi "1.0.2"]
                 [prismatic/schema "1.0.1"]]

  :profiles {:uberjar {:aot :all}
             :dev {:repl-options
                   {:init-ns user
                    :init (do (set! *print-length* 100)
                              (require '[colorize.core :refer [color]])
                              (->> "Run `(dev)` to enter the dev namespace."
                                (color :cyan)
                                println))}
                   :plugins [[lein-midje "3.1.1"]]
                   :dependencies [[org.clojure/tools.namespace "0.2.4"]
                                  [com.cemerick/pomegranate "0.2.0"]
                                  [criterium "0.4.3"]
                                  [midje "1.6.3"]]
                   :source-paths ["dev"]}})
