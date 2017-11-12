(ns shouter.models.shout
    (:use ring.util.response)
    (:require [clojure.java.jdbc :as j]
              [clojure.tools.logging :as log]
              [compojure.core :as comp]
              [clj-time.coerce :as tco]
              [shouter.models.db :as db]))

(defn make-uuid [] (java.util.UUID/randomUUID))

(defn query [spec [sql & params :as statement] & opts]
  (log/info (str "executing query: " sql))
  (log/debug
    (with-out-str
      (println "sql params: ")
      (clojure.pprint/pprint params)))
  (let [result (apply j/query spec statement (concat opts [:identifiers #(.replace % \_ \-)]))]
    (log/info "query executed.")
    (log/debug
      (with-out-str
        (println "query result:")
        (clojure.pprint/pprint result)))
    result))

(defn execute! [spec sql & params]
  (log/info (str "executing statement: " sql))
  (log/debug
    (with-out-str
      (println "sql params:")
      (clojure.pprint/pprint params)))
  (let [result (apply j/db-do-prepared spec sql params)]
    (log/info "sql executed.")
    (log/debug
      (with-out-str
        (println "sql result:")
        (clojure.pprint/pprint result)))
    result))


(defn all-users
  [] (let [res (query db/db-connection
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

(defn add-user
  [body] (execute! db/db-connection
   (str "insert into users (entity_id, first_name, last_name, nick_name, state_name, team_id) VALUES (?,?,?,?,?,?)")
    [(str (make-uuid))
     (get body :first_name)
     (get body :last_name)
     (get body :nick_name)
     (get body :state_name)
     (get body :team_id)]))


(defn users-profile-wins-by-id
  [id]
  (let [wins (query db/db-connection
   [(str "select u.first_name, u.last_name, t.team_name, g.entity_id from games g left join users u on g.winner_person_id = u.entity_id left join team t on u.team_id = t.entity_id where u.entity_id =? ")id]
    response (:result-set-fn first))]
    {
     :count (count wins)
     :first-name (get (into {} wins) :first-name)
     :last-name (get (into {} wins) :last-name)
     :team-name (get (into {} wins) :team-name)
     }))

(defn users-profile-loss-by-id
  [id]
  (let [loss (query db/db-connection
   [(str "select u.first_name, u.last_name, t.team_name, g.entity_id from games g left join users u on g.losser_person_id = u.entity_id left join team t on u.team_id = t.entity_id where u.entity_id =? ")id]
    response (:result-set-fn first))]
    {
     :count (count loss)
     }))

(defn users-profile-by-id
  [id]
  (let [wins (users-profile-wins-by-id id)
        loss (users-profile-loss-by-id id)]
    {
     :win-count (get wins :count)
     :loss-count (get loss :count)
     :first-name (get wins :first-name)
     :last-name (get wins :last-name)
     :team-name (get wins :team-name)
     }))

(defn delete-user
  [body] (execute! db/db-connection
   (str "delete from users where entity_id = ? ")
    [(get body :id )]))

(defn all-teams
  [] (let [res (query db/db-connection
   (str "select * from team")
    response (:result-set-fn first))]
      (map (fn [row]
       {:team-name (row :team-name)
        :team-state (row :team-state)
        :team-city (row :team-city)
        :state-name (row :state-name)
        :entity-id (row :entity-id)})res)))

(defn add-team
  [body] (execute! db/db-connection
   (str "insert into team (entity_id, team_name, team_state, team_city, state_name) VALUES (?,?,?,?,?) ")
    [(str (make-uuid))
     (get body :team_name)
     (get body :team_state)
     (get body :team_city)
     (get body :state_name)
     ]))

(defn games-add
  [body] (execute! db/db-connection
   (str "insert into games (entity_id, location_state, location_city, winner_person_id, losser_person_id, date_sent) VALUES (?,?,?,?,?,?) ")
    [(str (make-uuid))
     (get body :location_state)
     (get body :location_city)
     (get body :winner_person_id)
     (get body :losser_person_id)
     (get body :date_sent)
     ]))









