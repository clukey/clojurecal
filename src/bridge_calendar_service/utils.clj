(ns bridge-calendar-service.utils
  (:require [clojure.java.io :as io]
            [clojure.edn :as edn]))

(def configs ["resources/config-dev.clj" "resources/config.clj"])

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Public fns

(defn load-config []
  (let [merge-files (fn [m path]
                      (if (.exists (io/as-file path))
                        (merge m (edn/read-string (slurp path)))
                        m))]
    (reduce merge-files {} configs)))
