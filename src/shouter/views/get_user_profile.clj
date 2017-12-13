
(ns shouter.views.get-user-profile
    (:use ring.util.response)
    (:require [clojure.java.jdbc :as j]
      [clojure.tools.logging :as log]
      [compojure.core :as comp]
      [clj-time.coerce :as tco]
      [shouter.models.db :as db]))


(defn user-profile-by-id
      [id]
      (let [res (db/query db/db-connection
              [(str "select winner_person_id as wins, losser_person_id as loses, entity_id from games where winner_person_id =? or losser_person_id =?  ") id id]
                response (:result-set-fn first))

            yards (db/query db/db-connection
                            [(str "select y.yds_num as yds from users u "
                                  "left join yds y on u.entity_id = y.person_id "
                                  "where u.entity_id =? ")id]
                            response (:result-set-fn first))

            total-yards (reduce + 0 (map (fn [row] (if (= (nil? (row :yds)) true) 0 (row :yds))) yards))

            att (db/query db/db-connection
                            [(str "select at.att_num as att from users u "
                                  "left join att at on u.entity_id = at.person_id "
                                  "where u.entity_id =? ")id]
                            response (:result-set-fn first))

            total-att (reduce + 0 (map (fn [row] (if (= (nil? (row :att)) true) 0 (row :att))) att))

            comp (db/query db/db-connection
                          [(str "select cm.comp_num as comp from users u "
                                "left join comp cm on u.entity_id = cm.person_id "
                                "where u.entity_id =? ")id]
                          response (:result-set-fn first))

            total-comp (reduce + 0 (map (fn [row] (if (= (nil? (row :comp)) true) 0 (row :comp))) comp))

            td (db/query db/db-connection
                           [(str "select t.td_num as td from users u "
                                 "left join td t on u.entity_id = t.person_id "
                                 "where u.entity_id =? ")id]
                           response (:result-set-fn first))

            total-td (reduce + 0 (map (fn [row] (if (= (nil? (row :td)) true) 0 (row :td))) td))

            inc (db/query db/db-connection
                         [(str "select i.int_num as inct from users u "
                               "left join inc i on u.entity_id = i.person_id "
                               "where u.entity_id =? ")id]
                         response (:result-set-fn first))

            total-inc (reduce + 0 (map (fn [row] (if (= (nil? (row :inct)) true) 0 (row :inct))) inc))

            a (if (= total-att 0) 0 (* (- (/ total-comp total-att) 0.3) 0.5))
            b (if (= total-att 0) 0 (* (- (/ total-yards total-att) 3) 0.25))
            c (if (= total-att 0) 0 (* (/ total-td total-att) 20))
            d (if (= total-att 0) 2.375 (- 2.375 (* (/ total-inc total-att ) 25)))
            ]

          {:games-played (count res)
           :wins (reduce + 0 (map (fn [row] (if (= (row :wins) id) 1 0)) res))
           :loses (reduce + 0 (map (fn [row] (if (= (row :loses) id) 1 0)) res))
           :total-yrds total-yards
           :total-td total-td
           :total-inc total-inc
           :total-comp total-comp
           :total-att total-att
           :comp-percentage (if (= total-att 0) 0 (/ total-comp total-att))
           :a a
           :b b
           :c c
           :d d



           }))


