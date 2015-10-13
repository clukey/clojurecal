(ns bridge-calendar-service.schemas
  (:require [schema.core :as s]
            [schema.macros :as sm]))

(defschema Event
  {:action (s/enum :create :update :delete)})

(defschema Enrollment
  (merge Event
    {:title s/Str
     :end_date s/Str}))

(defschema LiveTraining
  (merge Event
    {:title s/Str
     :start_date s/Str
     :end_date s/Str
     :location s/Str
     :description s/Str}))

(defschema Message
  {:source :bridge
   :service (s/enum "google" "office365")
   :type (s/enum "calendar")
   :timestamp s/Str
   :event (s/either Enrollment LiveTraining)})
