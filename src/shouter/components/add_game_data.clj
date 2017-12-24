(ns shouter.components.add-game-data
    (:use ring.util.response)
    (:require [clojure.java.jdbc :as j]
      [clojure.tools.logging :as log]
      [compojure.core :as comp]
      [clj-time.coerce :as tco]
      [shouter.models.db :as db]))


;GAME FUNCTIONS //////////////////////////////////////////////////////////////////////////////////////////////////////

(defn add-game-played
      [game-id home-team-id date]
      (db/execute! db/db-connection
        (str "insert into games (entity_id, home_team_id, date_played) VALUES (?,?,?) ")
        [game-id
         home-team-id
         date
         ]))


(defn post-game-assignments
      [game-id person-id outcome]
      (db/execute! db/db-connection
                   (str "insert into game_assignments (entity_id, person_id, outcome, game_id) VALUES (?,?,?,?) ")
                   [(str (db/make-uuid))
                    person-id
                    outcome
                    game-id]))


(defn add-game
      [body]
      (let [game-id (get body :game_id)
            winner-id (get body :winner_id)
            loser-id (get body :loser_id)
            date (get body :date_played)
            home-team-id  (get body :home_team_id)]
           {

            :post-game (add-game-played game-id home-team-id date)
            :post-game-assignments-winner (post-game-assignments game-id winner-id (num 1))
            :post-game-assignments-loser (post-game-assignments game-id loser-id (num 0))

            })
      )


;STATS FUNCTIONS //////////////////////////////////////////////////////////////////////////////////////////////////////

(defn post-stat-results
      [stat-id num game-id person-id]
      (db/execute! db/db-connection
                   (str "insert into stat_assignments (entity_id, person_id, num, game_id, stat_id) VALUES (?,?,?,?, ?) ")
                   [(str (db/make-uuid))
                    person-id
                    num
                    game-id
                    stat-id
                    ]
                   ))



(defn add-game-stat-assignments
      [body]
      (let [game-id (get body :game_id)
            winner-id (get body :winner_id)
            loser-id (get body :loser_id)
            watt-num (get body :watt_num)
            wcomp-num (get body :wcomp_num)
            wyds-num (get body :wyds_num)
            wtd-num (get body :wtd_num)
            wint-num (get body :wint_num)
            latt-num (get body :latt_num)
            lcomp-num (get body :lcomp_num)
            lyds-num (get body :lyds_num)
            ltd-num (get body :ltd_num)
            lint-num (get body :lint_num)

            ]

           {
            :post-winner-att (post-stat-results (str "e9e862f9-fab1-4e0a-947b-63c9b8ea401a") watt-num game-id winner-id)
            :post-winner-comp (post-stat-results (str "4cd25712-6203-4226-b864-13c8e04d8835") wcomp-num game-id winner-id)
            :post-winner-yds (post-stat-results (str "5e689fc1-d7ed-4858-9a10-887ac7c0859f") wyds-num game-id winner-id)
            :post-winner-td (post-stat-results (str "c110eadb-d52d-4a73-8c28-49708f4ea361") wtd-num game-id winner-id)
            :post-winner-inc (post-stat-results (str "9ade9a0e-e584-47a8-a753-bd09e596281b") wint-num game-id winner-id)


            :post-loser-att (post-stat-results (str "e9e862f9-fab1-4e0a-947b-63c9b8ea401a") latt-num game-id loser-id)
            :post-loser-comp (post-stat-results (str "4cd25712-6203-4226-b864-13c8e04d8835") lcomp-num game-id loser-id)
            :post-loser-yds (post-stat-results (str "5e689fc1-d7ed-4858-9a10-887ac7c0859f") lyds-num game-id loser-id)
            :post-loser-td (post-stat-results (str "c110eadb-d52d-4a73-8c28-49708f4ea361") ltd-num game-id loser-id)
            :post-loser-inc (post-stat-results (str "9ade9a0e-e584-47a8-a753-bd09e596281b") lint-num game-id loser-id)
            }
           )



      )

;// insert into stats (entity_id, name, state_name) values ('e9e862f9-fab1-4e0a-947b-63c9b8ea401a', 'att', 'active');
;// insert into stats (entity_id, name, state_name) values ('4cd25712-6203-4226-b864-13c8e04d8835', 'comp', 'active');
;// insert into stats (entity_id, name, state_name) values ('5e689fc1-d7ed-4858-9a10-887ac7c0859f', 'yds', 'active');
;// insert into stats (entity_id, name, state_name) values ('c110eadb-d52d-4a73-8c28-49708f4ea361', 'td', 'active');
;// insert into stats (entity_id, name, state_name) values ('9ade9a0e-e584-47a8-a753-bd09e596281b', 'int', 'active');

;
;;WINNER FUNCTIONS //////////////////////////////////////////////////////////////////////////////////////////////////////
;
;(defn post-att-winner
;      [game-id person-id att-num]
;      (db/execute! db/db-connection
;         (str "insert into att (entity_id, game_id, person_id, att_num) VALUES (?,?,?,?) ")
;         [(str (db/make-uuid))
;          game-id
;          person-id
;          att-num]))
;
;(defn post-comp-winner
;      [game-id person-id comp-num]
;      (db/execute! db/db-connection
;       (str "insert into comp (entity_id, game_id, person_id, comp_num) VALUES (?,?,?,?) ")
;       [(str (db/make-uuid))
;        game-id
;        person-id
;        comp-num]))
;
;(defn post-yds-winner
;      [game-id person-id yds-num]
;      (db/execute! db/db-connection
;       (str "insert into yds (entity_id, game_id, person_id, yds_num) VALUES (?,?,?,?) ")
;       [(str (db/make-uuid))
;        game-id
;        person-id
;        yds-num]))
;
;
;(defn post-td-winner
;      [game-id person-id td-num]
;      (db/execute! db/db-connection
;       (str "insert into td (entity_id, game_id, person_id, td_num) VALUES (?,?,?,?) ")
;       [(str (db/make-uuid))
;        game-id
;        person-id
;        td-num]))
;
;(defn post-int-winner
;      [game-id person-id int-num]
;      (db/execute! db/db-connection
;       (str "insert into inc (entity_id, game_id, person_id, int_num) VALUES (?,?,?,?) ")
;       [(str (db/make-uuid))
;        game-id
;        person-id
;        int-num]))
;
;
;(defn add-game-to-winner
;      [body]
;      (let [game-id (get body :game_id)
;            person-id (get body :person_id)
;            num (get body :num)
;            ;comp-num (get body :comp_num)
;            ;yds-num (get body :yds_num)
;            ;td-num (get body :td_num)
;            ;int-num (get body :int_num)
;            ]
;       {:post-att-winner (post-att-winner game-id person-id att-num)
;        :post-comp-winner (post-comp-winner game-id person-id comp-num)
;        :post-yds-winner (post-yds-winner game-id person-id yds-num)
;        :post-td-winner (post-td-winner game-id person-id td-num)
;        :post-int-winner (post-int-winner game-id person-id int-num)}))
;
;
;
;;LOSER FUNCTIONS  //////////////////////////////////////////////////////////////////////////////////////////////////////
;
;(defn post-att-loser
;      [game-id person-id att-num]
;      (db/execute! db/db-connection
;                   (str "insert into att (entity_id, game_id, person_id, att_num) VALUES (?,?,?,?) ")
;                   [(str (db/make-uuid))
;                    game-id
;                    person-id
;                    att-num]))
;
;(defn post-comp-loser
;      [game-id person-id comp-num]
;      (db/execute! db/db-connection
;                   (str "insert into comp (entity_id, game_id, person_id, comp_num) VALUES (?,?,?,?) ")
;                   [(str (db/make-uuid))
;                    game-id
;                    person-id
;                    comp-num]))
;
;(defn post-yds-loser
;      [game-id person-id yds-num]
;      (db/execute! db/db-connection
;                   (str "insert into yds (entity_id, game_id, person_id, yds_num) VALUES (?,?,?,?) ")
;                   [(str (db/make-uuid))
;                    game-id
;                    person-id
;                    yds-num]))
;
;
;(defn post-td-loser
;      [game-id person-id td-num]
;      (db/execute! db/db-connection
;                   (str "insert into td (entity_id, game_id, person_id, td_num) VALUES (?,?,?,?) ")
;                   [(str (db/make-uuid))
;                    game-id
;                    person-id
;                    td-num]))
;
;(defn post-int-loser
;      [game-id person-id int-num]
;      (db/execute! db/db-connection
;                   (str "insert into inc (entity_id, game_id, person_id, int_num) VALUES (?,?,?,?) ")
;                   [(str (db/make-uuid))
;                    game-id
;                    person-id
;                    int-num]))
;
;
;(defn add-game-to-loser
;      [body]
;      (let [game-id (get body :game_id)
;            person-id (get body :person_id)
;            att-num (get body :att_num)
;            comp-num (get body :comp_num)
;            yds-num (get body :yds_num)
;            td-num (get body :td_num)
;            int-num (get body :int_num)]
;           {:post-att-loser (post-att-loser game-id person-id att-num)
;            :post-comp-loser (post-comp-loser game-id person-id comp-num)
;            :post-yds-loser (post-yds-loser game-id person-id yds-num)
;            :post-td-loser (post-td-loser game-id person-id td-num)
;            :post-int-loser (post-int-loser game-id person-id int-num)}))