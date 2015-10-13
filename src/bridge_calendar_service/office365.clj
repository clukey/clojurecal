(ns bridge-calendar-service.office365
  (:require [gapi.core :as gapi]))

(defmulti call :action)

(defmethod call :create [m])
(defmethod call :update [m])
(defmethod call :delete [m])

