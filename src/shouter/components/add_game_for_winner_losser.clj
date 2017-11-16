
(ns shouter.components.add-game-for-winner-losser
  (:use ring.util.response)
  (:require [clojure.java.jdbc :as j]
            [clojure.tools.logging :as log]
            [compojure.core :as comp]
            [clj-time.coerce :as tco]
            [shouter.models.db :as db]))



(defn add-game
  [game-id body] (db/execute! db/db-connection
     (str "insert into games (entity_id, location_state, location_city, winner_person_id, losser_person_id, date_sent) VALUES (?,?,?,?,?,?) ")
     [game-id
      (get body :location_state)
      (get body :location_city)
      (get body :winner_person_id)
      (get body :losser_person_id)
      (get body :date_sent)
      ]))


(defn add-winner-att
  [game-id body] (db/execute! db/db-connection
    (str "insert into att (entity_id, person_id, game_id, att_num) VALUES (?,?,?,?) ")
    [(str (db/make-uuid))
     (get body :winner_person_id)
     game-id
     (get body :winner_att_num)
     ]))



(defn add-game-to-system
  [body]

  (let [game-id (str (db/make-uuid))]
    {
     :add-game (add-game game-id body)
     :add-winner-att (add-winner-att game-id body)
     }
    )


  )