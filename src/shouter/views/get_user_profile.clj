
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
        response (:result-set-fn first))]
          {:games-played (count res)
           :wins (reduce + 0 (map (fn [row] (if (= (row :wins) id) 1 0)) res))
           :loses (reduce + 0 (map (fn [row] (if (= (row :loses) id) 1 0)) res))
           }))


