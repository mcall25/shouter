(ns shouter.views.get-all-teams-by-id
    (:use ring.util.response)
    (:require [clojure.java.jdbc :as j]
      [clojure.tools.logging :as log]
      [compojure.core :as comp]
      [clj-time.coerce :as tco]
      [shouter.models.db :as db]))



(defn get-all-teams-for-winner-loser
      [winner-id loser-id]
      (let [res (db/query db/db-connection
           [(str "select t.team_name, t.entity_id, t.team_city, t.team_state, u.first_name, u.last_name from users u left join teams t on u.team_id = t.entity_id where u.entity_id =? or u.entity_id =? ") winner-id loser-id]
           response (:result-set-fn first))]
              (map (fn [row]
                   {:fn (row :first-name)
                    :ln (row :last-name)
                    :team-name (row :team-name)
                    :team-entity-id (row :entity-id)
                    :team-city (row :team-city)
                    :team-state (row :team-state)})res)))

