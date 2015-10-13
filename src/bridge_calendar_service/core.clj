(ns bridge-calendar-service.core
  (:require [bridge-calendar-service.google :as g]
            [clj-time.core :as time]
            [clj-time.coerce :as tc]
            [gapi.auth :refer [refresh-token]]
            [gapi.core :as gapi]))

(def config {:client-id "174644610201-jfee74afq6dm4nt358an0lce948b206u.apps.googleusercontent.com"
             :secret "TWNeqONfkOS2gQ2AEV0iIHPJ"
             :callback-url "https://6e0d67fc.ngrok.io/oauthcallback"
             :expires 1444774535327
             :refresh-token "1/elLntyTBJAM3-WMAlMLMuFfCPHyUJUv9bykcsXt3oZ5IgOrJDtdun6zK6XiATCKT"
             :token "ya29.CwKrB7m2GxpsxaZCWNM7Yl5qK7EzYqvUxE6p96pg0wA0fR2Z8DzBorjOvDLE96FultkX"})

(defn expired? [expires]
  (<= expires (tc/to-long (time/now))))

(defn get-auth
  [{:keys [client-id secret redirect-url token refresh-token expires]}]
  (let [auth (atom {:client_id client-id
                    :client_secret secret
                    :redirect_url redirect-url
                    :token token
                    :refresh refresh-token})]
  (if (expired? expires)
    (do (refresh-token auth)
        auth)
    auth)))

(defn get-service []
  (gapi/build "https://www.googleapis.com/discovery/v1/apis/calendar/v3/rest"))

(defn handle-message [m]
  (let [auth (get-auth config)
        service (get-service)]
  (g/call auth service m)))
