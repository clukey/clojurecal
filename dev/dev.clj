(ns dev
  "Tools for interactive development with the REPL. This file should
  not be included in a production build of the application."
  (:require
    [clojure.java.javadoc :refer [javadoc]]
    [clojure.pprint :refer [pprint print-table]]
    [clojure.reflect :refer [reflect]]
    [clojure.repl :refer [apropos dir doc find-doc pst source]]
    [clojure.tools.namespace.repl :refer [refresh refresh-all]]

    [gapi.core :as gapi]
    [clj-time.core :as time]
    [clj-time.format :as f]

    [cheshire.core :as cheshire]

    [bridge-calendar-service.core :as c]
    [bridge-calendar-service.message-handler :as mh]
    [bridge-calendar-service.utils :as u]))

(def google-details
  (as-> "resources/client-details.json" <>
      (slurp <>)
      (cheshire/parse-string <> (fn [i] (keyword i)))
      (:google <>)))

(def code "")
(def state "")

(def secret (:secret google-details))
(def client-id (:client-id google-details))
(def callback-url (:callback-url google-details))

(def auth (gapi.auth/create-auth client-id secret callback-url))

(def auth-url
  (let [scope ["https://www.googleapis.com/auth/calendar"]
        params {:approval_prompt "force"
                :access_type "offline"}]
    (gapi.auth/generate-auth-url auth scope params)))

(gapi.auth/exchange-token auth code state)
(def service (gapi/build "https://www.googleapis.com/discovery/v1/apis/calendar/v3/rest"))

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
