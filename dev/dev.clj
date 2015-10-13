(ns dev
  "Tools for interactive development with the REPL. This file should
  not be included in a production build of the application."
  (:require
    [clojure.java.javadoc :refer [javadoc]]
    [clojure.pprint :refer [pprint print-table]]
    [clojure.reflect :refer [reflect]]
    [clojure.repl :refer [apropos dir doc find-doc pst source]]
    [clojure.tools.namespace.repl :refer [refresh refresh-all]]

    [bridge-calendar-service.core :as c]
    [gapi.core :as gapi]
    [clj-time.core :as time]
    [clj-time.format :as f]))

(def code "4/glVxWlBnBOdoSjsnf--EdzCBCCYKBPEKp_iRh5xJAC8")
(def state "GitpHPyfZPci7A==")

(def secret "TWNeqONfkOS2gQ2AEV0iIHPJ")
(def client-id "174644610201-jfee74afq6dm4nt358an0lce948b206u.apps.googleusercontent.com")
(def callback-url "https://6e0d67fc.ngrok.io/oauthcallback")

(def auth (gapi.auth/create-auth client-id secret callback-url))

(def auth-url
  (let [scope ["https://www.googleapis.com/auth/calendar"]
        parens {:approval_prompt "force"
                :access_type "offline"}]
    (gapi.auth/generate-auth-url auth scope parens)))

auth
(gapi.auth/exchange-token auth code state)
(def service (gapi/build "https://www.googleapis.com/discovery/v1/apis/calendar/v3/rest"))

;; (filter #(re-matches #".*\/list" %) (gapi/list-methods service))
;; (gapi/call auth service "calendar.calendarList/list" nil)
;; (service "calendar.calendarList/list")

(def event {:source "bridge"
            :service "google"
            :type "calendar"
            :timestamp (f/unparse (f/formatters :basic-date-time) (time/now))
            :event {:summary "weed the garden"
                    :description "just 'cause"
                    :location "my house"
                    :event-id "pf96s97ribad1eekmkqj919n5c"
                    :start {"dateTime" "2015-10-28T15:00:00-06:00"}
                    :end {"dateTime" "2015-10-28T16:00:00-06:00"}}})

;; create
(c/handle-message (assoc event :action :create))

;; update
(c/handle-message (assoc event :action :update))

;; delete
(c/handle-message (assoc event :action :delete))

; (def result
;   (let [params {"calendarId" "primary"}
;   action "calendar.events/insert"]
;   (gapi/call auth service action params event)))
;
; ;; be sure to add event ids from the result
; ;; i.e., (result "id") ;; => "luh31f86ivk7lsodauv3aam2vo"
; (println
;   (let [params {"calendarId" "primary"
;   "eventId" "event-id"}
;   action "calendar.events/update"]
;   (gapi/call auth service action params event)))
;
; (let [params {"calendarId" "primary"
; "eventId" "event-id"}
; action "calendar.events/delete"]
; (gapi/call auth service action params))
