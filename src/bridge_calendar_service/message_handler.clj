(ns bridge-calendar-service.message-handler
  (:gen-class :implements [com.amazonaws.services.lambda.runtime.RequestStreamHandler])
  (:require [clojure.string :as s]
            [clojure.java.io :as io]

            [cheshire.core :as json]

            [bridge-calendar-service.google :as google]
            [bridge-calendar-service.office365 :as office365]
            [bridge-calendar-service.utils :as u]))

;; should we be implementing com.amazonaws.services.lambda.runtime.events.KinesisEvent instead of RequestStreamHandler?

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Private Helpers

;; prolly a better way to do this
(defn config []
  (delay (u/load-config)))

(defmulti call (fn [m _] (:service m)))

(defmethod call "google" [m config]
  (let [auth (google/get-auth config)
        service @(google/get-service)]
    (google/call auth service m)))

(defmethod call "office365" [m config])

;; need to add schema validation!
(defn handle-message
  [{:keys [service type] :as m} config]
  (let [calendar-message? #(= "calendar" %)]
    (when (calendar-message? type)
      (call m config))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Public Fns

(defn -handleRequest [this is os context]
  (let [event (io/reader is)]
    (-> event
      (json/parse-string true)
      (handle-message @(config)))))
