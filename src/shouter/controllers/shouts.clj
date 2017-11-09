(ns shouter.controllers.shouts
    (:use [compojure.core :only (defroutes GET POST PUT DELETE context)])
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
           (GET "/users" []
             (let [respData (model/all-users)]
               {:status 200
                :body {:result :success
                       :data respData
                       :desc (str "get request for all users")}}))
           (POST "/adduser" userinfo
             (let [postData (model/add-user (get userinfo :body) )]
               {:status 200
                :body {:result :success
                       :data postData
                       :desc (str "add user")}}))
           (POST "/deleteuser" id
             (let [deleteData (model/delete-user (get id :body))]
               {:status 200
                :body {:result :success
                       :data deleteData
                       :desc (str "delete user")}}))
           (route/resources "/")
           (route/not-found "Not Found"))


(defn wrap-json-response [handler]
  (fn [request]
    (let [response (handler request)
          json-response (update-in response [:body] ch/generate-string)]
      (assoc-in json-response [:headers "Content-Type"] "application/json; charset=utf-8")
      )))

(def app
  (->
    routes
    (wrap-json-response)
    (json/wrap-json-body {:keywords? true }) )
  )

