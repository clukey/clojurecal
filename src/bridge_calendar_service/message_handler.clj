(ns bridge-calendar-service.message-handler
  (:require [bridge-calendar-service.apis.google :as google]
            [bridge-calendar-service.apis.office365 :as office365]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Private Helpers

(defmulti call (fn [m _] (:service m)))

(defmethod call "google" [m config]
  (let [auth (google/get-auth config)
        service @(google/get-service)]
    (google/call auth service m)))

(defmethod call "office365" [m config])

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Public Fns

(defn handle-message
  [{:keys [service type] :as m} config]
  (let [calendar-message? #(= "calendar" %)]
    (when (calendar-message? type)
      (call m config))))
