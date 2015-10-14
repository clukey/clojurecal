(ns bridge-calendar-service.schemas
  (:require [schema.core :as s]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Sample Messages
;;
;; enrollments
;; {:source :bridge
;;  :type :calendar
;;  :timestamp "2015-10-15T12:00:00-06:00"
;;  :event {:action :create
;;          :title "The Bridge Menace"
;;          :end_date "2015-10-20T05:59:59.999Z"}}
;;
;; live training
;; {:action :update
;;  :title "The Bridge Menace"
;;  :start_date "2015-10-15T23:59:59.999-06:00"
;;  :end_date "2015-10-20T23:59:59.999-06:00"
;;  :location "Phobos"
;;  :description "A new learning training system, without Jar Jar"}
;;
;; insert: required params are "calendarId" and the event map
;; udpate: required params are "calendarId", "eventId", and the event map
;; -- "eventId" is the "id"

(s/defschema Event
  {:action (s/enum :create :update :delete)})

(s/defschema Enrollment
  (merge Event
    {:title s/Str
     :end_date s/Str}))

(s/defschema LiveTraining
  (merge Event
    {:title s/Str
     :start_date s/Str
     :end_date s/Str
     :location s/Str
     :description s/Str}))

(s/defschema Message
  {:source :bridge
   :service (s/enum "google" "office365")
   :type (s/enum "calendar")
   :timestamp s/Str
   :event (s/either Enrollment LiveTraining)})
