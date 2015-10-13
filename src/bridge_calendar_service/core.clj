(ns bridge-calendar-service.core
  (:require [gapi.core :as gapi]))

(def code "code")
(def state "state")

(def secret "secret")
(def client-id "client-id")
(def callback "callback-url")

(def auth (gapi.auth/create-auth client-id secret callback-url))

(def auth-url (gapi.auth/generate-auth-url
                auth
                ["https://www.googleapis.com/auth/calendar"]
                {:approval_prompt "force" :access_type "offline"}))

(gapi.auth/exchange-token auth code state)
(def service (gapi/build "https://www.googleapis.com/discovery/v1/apis/calendar/v3/rest"))

;; (filter #(re-matches #".*\/list" %) (gapi/list-methods service))
;; (gapi/call auth service "calendar.calendarList/list" nil)
;; (service "calendar.calendarList/list")

(def event {:summary "weed the garden"
            :description "just 'cause"
            :location "my house"
            :start {"dateTime" "2015-10-29T15:00:00-06:00"}
            :end {"dateTime" "2015-10-29T16:00:00-06:00"}})

(def result
  (let [params {"calendarId" "primary"}
        action "calendar.events/insert"]
    (gapi/call auth service action params event)))

;; be sure to add event ids from the result
;; i.e., (result "id") ;; => "luh31f86ivk7lsodauv3aam2vo"
(println
  (let [params {"calendarId" "primary"
                "eventId" "event-id"}
        action "calendar.events/update"]
    (gapi/call auth service action params event)))

(let [params {"calendarId" "primary"
              "eventId" "event-id"}
      action "calendar.events/delete"]
  (gapi/call auth service action params))
