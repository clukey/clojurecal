(ns bridge-calendar-service.core
  (:require [com.stuartsierra.component :as component]
            [bridge-calendar-service.message-handler :as mh]))

(defrecord Kinesis [config]
  component/Lifecycle

  (start [this])
  (stop [this]))

(defn new-kinesis-client [config]
  (->Kinesis config))

(defn system [config]
  (component/system-map
    :kinesis (new-kinesis-client config)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Public Fns

(defn -main [])
