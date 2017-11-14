(ns shouter.views.get-all-users
  (:use ring.util.response)
  (:require [clojure.java.jdbc :as j]
            [clojure.tools.logging :as log]
            [compojure.core :as comp]
            [clj-time.coerce :as tco]
            [shouter.models.db :as db]))


(defn all-users
  [] (let [res (db/query db/db-connection
                      (str "select *, t.team_name as teamName from users u left join team t on u.team_id = t.entity_id where u.state_name = 'active' ")
                      response (:result-set-fn first))]
       (map (fn [row]
              {:fn (row :first-name)
               :ln (row :last-name)
               :nick-name (row :nick-name)
               :entity-id (row :entity-id)
               :team-id (row :team-id)
               :team-name (row :teamName)
               :state-name (row :state-name)})res)))