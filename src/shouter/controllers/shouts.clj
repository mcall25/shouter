(ns shouter.controllers.shouts
    (:use [compojure.core :only (defroutes GET POST)])
    (:use [ring.middleware.json :only (wrap-json-body)])
    (:use ring.util.response)
    (:require [clojure.string :as str]
              [compojure.core :as comp]
              [compojure.handler :as handler]
              [compojure.route :as route]
              [shouter.views.shouts :as view]
              [shouter.models.shout :as model]
              [cheshire.core :as ch]
    (ring.middleware [session :as ses]
                     [json :as json]
                     [params :as params]
                     [keyword-params :as kparams]
                     [content-type :as ct])
    (ring.util [response :as resp]
               [mime-type :as mime])
              ))

(defroutes routes
           (GET "/users" [] (model/all-users))
           )

(defn wrap-json-response [handler]
  (fn [request]
    (let [response (handler request)]
      (if (coll? (:body response))
        (let [json-response (update-in response [:body] ch/generate-string)]
          (if (contains? (:headers response) "Content-Type")
            json-response
            (assoc-in json-response [:headers "Content-Type"] "application/json; charset=utf-8")))
        response))))

(def app
  (->
    routes
    (wrap-json-response)
    (json/wrap-json-body {:keywords? true }) )
  )

