(ns bridge-calendar-service.utils
  (:require [clojure.java.io :as io]
            [clojure.edn :as edn]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Public fns

(defn load-config []
  (let [files ["resources/config-dev.clj" "resources/config.clj"]
        merge-files (fn [m path]
                      (if (.exists (io/as-file path))
                        (merge m (edn/read-string (slurp path)))
                        m))]
    (reduce merge-files {} files)))
