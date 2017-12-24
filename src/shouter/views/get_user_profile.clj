
(ns shouter.views.get-user-profile
    (:use ring.util.response)
    (:require [clojure.java.jdbc :as j]
      [clojure.tools.logging :as log]
      [compojure.core :as comp]
      [clj-time.coerce :as tco]
      [shouter.models.db :as db]))



(defn user-profile-by-id
      [id]
      (println (str "hit user profile") id)

      (let [qb-profile (db/query db/db-connection
                                     [(str "select s.name as statName, sa.num as stat from users u  "
                                           "left join stat_assignments sa on u.entity_id = sa.person_id "
                                           "left join stats s on sa.stat_id = s.entity_id "
                                           "where u.entity_id = ? ")id]
                                     response (:result-set-fn first))

            total-att (reduce + 0 (map (fn [row] (if (= (row :statName) (str "att")) (row :stat) 0)) qb-profile))
            total-yds (reduce + 0 (map (fn [row] (if (= (row :statName) (str "yds")) (row :stat) 0)) qb-profile))
            total-int (reduce + 0 (map (fn [row] (if (= (row :statName) (str "int")) (row :stat) 0)) qb-profile))
            total-td (reduce + 0 (map (fn [row] (if (= (row :statName) (str "td")) (row :stat) 0)) qb-profile))
            total-comp (reduce + 0 (map (fn [row] (if (= (row :statName) (str "comp")) (row :stat) 0)) qb-profile))
            comp-percentage (if (= total-att 0) 0 (/ total-comp total-att))
            a (if (= total-att 0) 0 (* (- (/ total-comp total-att) 0.3) 0.5))
            b (if (= total-att 0) 0 (* (- (/ total-yds total-att) 3) 0.25))
            c (if (= total-att 0) 0 (* (/ total-td total-att) 20))
            d (if (= total-att 0) 2.375 (- 2.375 (* (/ total-int total-att ) 25)))
            d (if (= total-att 0) 2.375 (- 2.375 (* (/ total-int total-att ) 25)))
            passer-rating (* (/ (+ a b c d) 6) 100)


            qb-profile-games (db/query db/db-connection
                                 [(str "select ga.outcome as outcome, u.last_name from users u  "
                                       "left join game_assignments ga on u.entity_id = ga.person_id "
                                       "left join games g on ga.game_id = g.entity_id "
                                       "where u.entity_id = ? ")id]
                                 response (:result-set-fn first))
            wins (reduce + 0 (map (fn [row] (if (= (row :outcome) 1) 1 0)) qb-profile-games))

            ]


           {
            :games-played (count qb-profile-games)
            :wins wins
            :total-yards total-yds
            :total-td total-td
            :passer-rating passer-rating

            }

           )

      )


