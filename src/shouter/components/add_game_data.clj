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
         (get body :date_sent)]))

;WINNER FUNCTIONS //////////////////////////////////////////////////////////////////////////////////////////////////////

(defn post-att-winner
      [game-id person-id att-num]
      (db/execute! db/db-connection
         (str "insert into att (entity_id, game_id, person_id, att_num) VALUES (?,?,?,?) ")
         [(str (db/make-uuid))
          game-id
          person-id
          att-num]))

(defn post-comp-winner
      [game-id person-id comp-num]
      (db/execute! db/db-connection
       (str "insert into comp (entity_id, game_id, person_id, comp_num) VALUES (?,?,?,?) ")
       [(str (db/make-uuid))
        game-id
        person-id
        comp-num]))

(defn post-yds-winner
      [game-id person-id yds-num]
      (db/execute! db/db-connection
       (str "insert into yds (entity_id, game_id, person_id, yds_num) VALUES (?,?,?,?) ")
       [(str (db/make-uuid))
        game-id
        person-id
        yds-num]))


(defn post-td-winner
      [game-id person-id td-num]
      (db/execute! db/db-connection
       (str "insert into td (entity_id, game_id, person_id, td_num) VALUES (?,?,?,?) ")
       [(str (db/make-uuid))
        game-id
        person-id
        td-num]))

(defn post-int-winner
      [game-id person-id int-num]
      (db/execute! db/db-connection
       (str "insert into inc (entity_id, game_id, person_id, int_num) VALUES (?,?,?,?) ")
       [(str (db/make-uuid))
        game-id
        person-id
        int-num]))


(defn add-game-to-winner
      [body]
      (let [game-id (get body :game_id)
            person-id (get body :person_id)
            att-num (get body :att_num)
            comp-num (get body :comp_num)
            yds-num (get body :yds_num)
            td-num (get body :td_num)
            int-num (get body :int_num)]
       {:post-att-winner (post-att-winner game-id person-id att-num)
        :post-comp-winner (post-comp-winner game-id person-id comp-num)
        :post-yds-winner (post-yds-winner game-id person-id yds-num)
        :post-td-winner (post-td-winner game-id person-id td-num)
        :post-int-winner (post-int-winner game-id person-id int-num)}))



;LOSER FUNCTIONS  //////////////////////////////////////////////////////////////////////////////////////////////////////

(defn post-att-loser
      [game-id person-id att-num]
      (db/execute! db/db-connection
                   (str "insert into att (entity_id, game_id, person_id, att_num) VALUES (?,?,?,?) ")
                   [(str (db/make-uuid))
                    game-id
                    person-id
                    att-num]))

(defn post-comp-loser
      [game-id person-id comp-num]
      (db/execute! db/db-connection
                   (str "insert into comp (entity_id, game_id, person_id, comp_num) VALUES (?,?,?,?) ")
                   [(str (db/make-uuid))
                    game-id
                    person-id
                    comp-num]))

(defn post-yds-loser
      [game-id person-id yds-num]
      (db/execute! db/db-connection
                   (str "insert into yds (entity_id, game_id, person_id, yds_num) VALUES (?,?,?,?) ")
                   [(str (db/make-uuid))
                    game-id
                    person-id
                    yds-num]))


(defn post-td-loser
      [game-id person-id td-num]
      (db/execute! db/db-connection
                   (str "insert into td (entity_id, game_id, person_id, td_num) VALUES (?,?,?,?) ")
                   [(str (db/make-uuid))
                    game-id
                    person-id
                    td-num]))

(defn post-int-loser
      [game-id person-id int-num]
      (db/execute! db/db-connection
                   (str "insert into inc (entity_id, game_id, person_id, int_num) VALUES (?,?,?,?) ")
                   [(str (db/make-uuid))
                    game-id
                    person-id
                    int-num]))


(defn add-game-to-loser
      [body]
      (let [game-id (get body :game_id)
            person-id (get body :person_id)
            att-num (get body :att_num)
            comp-num (get body :comp_num)
            yds-num (get body :yds_num)
            td-num (get body :td_num)
            int-num (get body :int_num)]
           {:post-att-loser (post-att-loser game-id person-id att-num)
            :post-comp-loser (post-comp-loser game-id person-id comp-num)
            :post-yds-loser (post-yds-loser game-id person-id yds-num)
            :post-td-loser (post-td-loser game-id person-id td-num)
            :post-int-loser (post-int-loser game-id person-id int-num)}))