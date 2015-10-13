(ns user
  "Bootstrap fn for dev env. By having the dev ns separate we avoid having the REPL
  blow up on start when it hits compilation errors of required files."
  (:require clojure.stacktrace))


(defn dev
  "Run (dev) to go into the dev nameapce. Use (dev :reload) to reload dev ns first."
  [& [reload]]
  (try
    (if reload
      (require 'dev :reload-all)
      (require 'dev))
    (in-ns 'dev)
    ;; (println "Run (tools-help) to see a list of useful functions.")
    :ok
    (catch Throwable t
      (println "ERROR: There was a problem loading the dev namespace\n")
      (clojure.stacktrace/print-cause-trace t)
      (println))))
