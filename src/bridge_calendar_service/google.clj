(ns bridge-calendar-service.google
  (:require [gapi.core :as gapi]))

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

(def ^:const insert "calendar.events/insert")
(def ^:const update "calendar.events/update")
(def ^:const delete "calendar.events/delete")

(defmulti call (fn [_ _ m] (:action m)))

(defmethod call :create [auth service m]
  (let [params {"calendarId" "primary"}]
    (gapi/call auth service insert params (:event m))))

(defmethod call :update [auth service m]
  (let [params {"calendarId" "primary"
                "eventId" (-> m :event :event-id)}
        ]
    (gapi/call auth service update params (:event m))))

(defmethod call :delete [auth service m]
  (let [params {"calendarId" "primary"
                "eventId" (-> m :event :event-id)}]
    (gapi/call auth service delete params)))
