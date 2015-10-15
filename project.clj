(defproject bridge-calendar-service "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.7.0"]
                 [clj-time "0.5.1"]
                 [com.amazonaws/aws-lambda-java-core "1.0.0"]
                 [com.amazonaws/aws-lambda-java-events "1.0.0"]
                 
                 [cheshire "5.5.0"]
                 [com.stuartsierra/component "0.3.0"]
                 [gapi "1.0.2"]
                 [prismatic/schema "1.0.1"]
                 [amazonica "0.3.34"]
                 [byte-streams "0.2.0"]]

  :profiles {:uberjar {:aot :all}
             :dev {:repl-options
                   {:init-ns user
                    :init (do (set! *print-length* 100)
                              (->> "Run `(dev)` to enter the dev namespace."
                                println))}
                   :plugins [[lein-expectations "0.0.7"]]
                   :dependencies [[org.clojure/tools.namespace "0.2.4"]
                                  [com.cemerick/pomegranate "0.2.0"]
                                  [criterium "0.4.3"]
                                  [expectations "2.0.9"]
                                  [spyscope "0.1.4"]]
                   :source-paths ["dev"]}})
