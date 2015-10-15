(ns bridge-calendar-service.google
  (:require [clj-time.core :as time]
            [clj-time.coerce :as tc]
            [gapi.core :as gapi]
            [gapi.auth :as gapi-auth]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Defs

(def ^:const service-url "https://www.googleapis.com/discovery/v1/apis/calendar/v3/rest")

;;; Calendar event actions
(def ^:const insert "calendar.events/insert")
(def ^:const update "calendar.events/update")
(def ^:const delete "calendar.events/delete")

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Private Helpers

(defn expired? [expires]
  (let [current-time (tc/to-long (time/now))]
    (< expires current-time)))

(defn generate-auth
  [{:keys [client-id
           secret
           redirect-url
           token
           refresh-token]}]
  (atom {:client_id client-id
         :client_secret secret
         :redirect_url redirect-url
         :token token
         :refresh refresh-token}))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Public Fns

(defmulti call (fn [_ _ m] (:action m)))

(defmethod call :create [auth service m]
  (let [params {"calendarId" "primary"}]
    (gapi/call auth service insert params (:event m))))

(defmethod call :update [auth service m]
  (let [params {"calendarId" "primary"
                "eventId" (-> m :event :event-id)}]
    (gapi/call auth service update params (:event m))))

(defmethod call :delete [auth service m]
  (let [params {"calendarId" "primary"
                "eventId" (-> m :event :event-id)}]
    (gapi/call auth service delete params)))

(defn get-auth [m]
  (let [auth (generate-auth m)]
    (if (expired? (:expires m))
      (do (gapi-auth/refresh-token auth)
          auth)
      auth)))

(defn get-service []
  (delay (gapi/build service-url)))
