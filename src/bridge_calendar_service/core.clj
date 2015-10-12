(ns bridge-calendar-service.core
  (:require [gapi.core :as gapi]
            [clojure.pprint :refer :all]))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(def api-key "AIzaSyCwGJKjfXSgV61lj-sAPw1SU2n4QhvUZIs")

(def auth (gapi.auth/create-auth "clojurecal" api-key "http://6e0d67fc.ngrok.io"))
(gapi.auth/generate-auth-url auth ["https://www.googleapis.com/auth/calendar"])
(gapi.auth/exchange-token auth "CODE" "STATE")
