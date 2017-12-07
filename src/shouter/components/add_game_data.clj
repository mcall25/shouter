(ns shouter.components.add-game-data
    (:use ring.util.response)
    (:require [clojure.java.jdbc :as j]
      [clojure.tools.logging :as log]
      [compojure.core :as comp]
      [clj-time.coerce :as tco]
      [shouter.models.db :as db]))

(defn add-game
      [body]
      (db/execute! db/db-connection
        (str "insert into games (entity_id, home_team_id, winner_person_id, losser_person_id, date_sent) VALUES (?,?,?,?,?) ")
        [(get body :entity_id)
         (get body :home_team_id)
         (get body :winner_person_id)
         (get body :losser_person_id)
         (get body :date_sent)
         ])
      )

(defn post-att-winner
      [game-id person-id att-num]
      (db/execute! db/db-connection
                   (str "insert into att (entity_id, game_id, person_id, att_num) VALUES (?,?,?,?) ")
                   [(str (db/make-uuid))
                    game-id
                    person-id
                    att-num])
      )



(defn add-game-to-winner
      [body]
      (println (str "what is game data for winner") body)

      (let [game-id (get body :game_id)
            person-id (get body :person_id)
            att-num (get body :att_num)
            ]
           {:post-att-winner (post-att-winner game-id person-id att-num)
        
            }
           )

      )
