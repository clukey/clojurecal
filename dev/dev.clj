(ns dev
  "Tools for interactive development with the REPL. This file should
  not be included in a production build of the application."
  (:require
    [clojure.java.javadoc :refer [javadoc]]
    [clojure.pprint :refer [pprint print-table]]
    [clojure.reflect :refer [reflect]]
    [clojure.repl :refer [apropos dir doc find-doc pst source]]
    [clojure.tools.namespace.repl :refer [refresh refresh-all]]

    [amazonica.aws.kinesis :as k]
    [gapi.core :as gapi]
    [clj-time.core :as time]
    [clj-time.format :as f]
    [byte-streams :as bs]

    [bridge-calendar-service.core :as c]
    [bridge-calendar-service.message-handler :as mh]
    [bridge-calendar-service.utils :as u]))

(def aws-cred {:access-key "1"
           :secret-key "2"
           :endpoint "http://localhost:4567"})

(def code "")
(def state "")

(def google-config (:google (u/load-config)))
(def secret (:secret google-config))
(def client-id (:client-id google-config))
(def callback-url (:callback-url google-config))

(def auth (gapi.auth/create-auth client-id secret callback-url))

(def auth-url
  (let [scope ["https://www.googleapis.com/auth/calendar"]
        params {:approval_prompt "force"
                :access_type "offline"}]
    (gapi.auth/generate-auth-url auth scope params)))

(gapi.auth/exchange-token auth code state)
(def service (gapi/build "https://www.googleapis.com/discovery/v1/apis/calendar/v3/rest"))

;; grab records from kinesalite
(def records (k/get-records aws-cred
                            :deserializer bs/to-string
                            :shard-iterator (k/get-shard-iterator aws-cred "CalendarEvents" "shardId-000000000000" "TRIM_HORIZON")))


;; Here's the data
(map :data (:records records))

;; (filter #(re-matches #".*\/list" %) (gapi/list-methods service))
;; (gapi/call auth service "calendar.calendarList/list" nil)
;; (service "calendar.calendarList/list")

;; (u/load-config)

(def config {:client-id client-id
             :secret secret
             :callback-url callback-url
             :expires (-> auth deref :expires)
             :refresh-token (-> auth deref :refresh)
             :token (-> auth deref :token)})

(def event {:source "bridge"
            :service "google"
            :type "calendar"
            :timestamp (f/unparse (f/formatters :basic-date-time) (time/now))
            :event {:summary "weed the garden"
                    :description "just 'cause"
                    :location "my house"
                    :event-id "8h7nhqjk2l4dutbt1b9b31u8fk"
                    :start {"dateTime" "2015-10-28T15:00:00-06:00"}
                    :end {"dateTime" "2015-10-28T16:00:00-06:00"}}})

;; create
;; (def create-response (mh/handle-message (assoc event :action :create) config))
;; (create-response "id")

;; ;; ;; update
;; (mh/handle-message (assoc event :action :update) config)

;; ;; ;; delete
;; (mh/handle-message (assoc event :action :delete) config)
