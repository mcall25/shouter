(ns shouter.components.add-game-data
    (:use ring.util.response)
    (:require [clojure.java.jdbc :as j]
      [clojure.tools.logging :as log]
      [compojure.core :as comp]
      [clj-time.coerce :as tco]
      [shouter.models.db :as db]))

(defn add-game
      [body]
      (println (str "what is game data") body)
      (db/execute! db/db-connection
        (str "insert into games (entity_id, home_team_id, winner_person_id, losser_person_id, date_sent) VALUES (?,?,?,?,?) ")
        [(str (db/make-uuid))
         (get body :home_team_id)
         (get body :winner_person_id)
         (get body :losser_person_id)
         (get body :date_sent)
         ])
      )


