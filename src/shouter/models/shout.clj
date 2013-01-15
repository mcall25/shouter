(ns shouter.models.shout
    (:require [clojure.java.jdbc :as sql]
              [shouter.models.db :as db]))

(defn all []
    (sql/with-connection db/db-connection
        (sql/with-query-results results
            ["select * from shouts order by id desc"]
            (into [] results))))

(defn create [shout]
    (sql/with-connection db/db-connection
        (sql/insert-values :shouts [:body] [shout])))