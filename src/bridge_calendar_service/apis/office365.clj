(ns bridge-calendar-service.apis.office365
  (:require [gapi.core :as gapi]))

(defmulti call (fn [_ _ m] (:action m)))

(defmethod call :create [auth service m])
(defmethod call :update [auth service m])
(defmethod call :delete [auth service m])

