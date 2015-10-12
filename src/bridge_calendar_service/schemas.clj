(ns bridge-calendar-service.core
  (:require [schema.core :as s]
            [schema.macros :as sm]))

(defschema Enrollment
  {:title "The Bridge Menace"
   :end_date "2015-10-20T05:59:59.999Z"})

(defschema LiveTraining
  {:title "The Bridge Menace"
   :start_date "2015-10-15T23:59:59.999-06:00"
   :end_date "2015-10-20T23:59:59.999-06:00"
   :location "Phobos"
   :description "A new learning training system, without Jar Jar"})

(defschema Event
  {:source :bridge
   :type :calendar
   :timestamp "2015-10-15T12:00:00-06:00"
   :event (s/either Enrollment LiveTraining)})
