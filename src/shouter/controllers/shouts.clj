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
              [shouter.components.add-game-for-winner-losser :as gm]
              [shouter.components.add-active-user :as addActiveUserComponent]
              [shouter.views.list-nfl-teams :as listNflTeamsComponent]
              [shouter.views.get-all-users :as users]
              [shouter.views.get-all-teams-by-id :as getAllTeamsByIdView]
              [cors :as cors]
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
           (GET "/users/v1/active-user-names" []
                (let [respData (users/all-users-names)]
                     {:status 200
                      :body {:result :success
                             :data respData
                             :desc (str "get request for all users names and assoc entity_id")}}))
           (POST "/users/add" userinfo
                 (println (str "test first") userinfo)
             (let [postData (addActiveUserComponent/add-user (get userinfo :body) )]
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
           (POST "/games/v1/add/stats" game
             (let [data (gm/add-game-to-system (get game :body))]
               {:status 200
                :body {:result :success
                       :data data
                       :desc (str "add game to the system with stats for the winner and the losser")}}))
           (GET "/teams" []
             (let [respData (model/all-teams)]
               {:status 200
                :body {:result :success
                       :data respData
                       :desc (str "get request for all teams")}}))
           (GET "/teams/all/active" []
                (let [respData (listNflTeamsComponent/get-all-active-teams)]
                     {:status 200
                      :body {:result :success
                             :data respData
                             :desc (str "get request for all active teams")}}))

           (GET "/teams/v1/associated/:winnerId/:loserId" [winnerId loserId]
                (let [respData (getAllTeamsByIdView/get-all-teams-for-winner-loser winnerId loserId)]
                     {:status 200
                      :body {:result :success
                             :data respData
                             :desc (str "get request for teams associated to the winning and losing players")}}))
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
    (cors/wrap-cors #".*")
    (json/wrap-json-body {:keywords? true })))




