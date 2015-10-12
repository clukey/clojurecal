(ns bridge-calendar-service.core
  (:require [gapi.core :as gapi]
            [clojure.pprint :refer :all]))

(def auth (gapi.auth/create-auth client-id secret "http://6e0d67fc.ngrok.io/oauthcallback"))

(gapi.auth/generate-auth-url
  auth
  ["https://www.googleapis.com/auth/calendar"]
  {:approval_prompt "force" :access_type "offline"})

(gapi.auth/exchange-token auth code state)

(def service (gapi/build "https://www.googleapis.com/discovery/v1/apis/calendar/v3/rest"))
(gapi/list-methods service)

;; methods
;; "calendar.events/insert"
;; "calendar.events/patch"
;; "calendar.events/delete"

(def event {:summary "watch 'the life of brian'"
            :description "because it's sacreligious"
            :location "defender"
            :start {"dateTime" "2015-10-29T12:00:00-06:00"}
            :end {"dateTime" "2015-10-29T13:00:00-06:00"}})

;; insert: required params are "calendarId" and the event map
;; udpate: required params are "calendarId", "eventId", and the event map
;; -- "eventId" is the "id"

(gapi/call auth service "calendar.events/delete" {"calendarId" "primary", "eventId" event-id} nil)

;; enrollments
{:source :bridge
 :type :calendar
 :timestamp "2015-10-15T12:00:00-06:00"
 :event {:title "The Bridge Menace"
         :end_date "2015-10-20T05:59:59.999Z"}}

;; live training
{:title "The Bridge Menace"
 :start_date "2015-10-15T23:59:59.999-06:00"
 :end_date "2015-10-20T23:59:59.999-06:00"
 :location "Phobos"
 :description "A new learning training system, without Jar Jar"}
