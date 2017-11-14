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
              [shouter.views.get-all-users :as users]
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
             (let [respData (users/all-users)]
               {:status 200
                :body {:result :success
                       :data respData
                       :desc (str "get request for all users")}}))
           (POST "/users/add" userinfo
             (let [postData (model/add-user (get userinfo :body) )]
               {:status 200
                :body {:result :success
                       :data postData
                       :desc (str "add user")}}))
           (GET "/users/v1/profile/:id" [id]
             (let [data (model/users-profile-by-id id)]
               {:status 200
                :body {:result :success
                       :data data
                       :desc (str "get user profile by id parms")}}))
           (POST "/users/delete" id
             (let [deleteData (model/delete-user (get id :body))]
               {:status 200
                :body {:result :success
                       :data deleteData
                       :desc (str "delete user")}}))
           (POST "/games/add" games
             (let [data (model/games-add (get games :body))]
               {:status 200
                :body {:result :success
                       :data data
                       :desc (str "add game")}}))
           (GET "/teams" []
             (let [respData (model/all-teams)]
               {:status 200
                :body {:result :success
                       :data respData
                       :desc (str "get request for all teams")}}))
           (POST "/teams/add" teamData
             (let [post-data (model/add-team (get teamData :body))]
               {:status 200
                :body {:result :success
                       :data post-data
                       :desc (str "add a team")}}))
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
    (json/wrap-json-body {:keywords? true })))




