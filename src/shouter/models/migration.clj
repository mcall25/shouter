(ns shouter.models.migration
    (:require [clojure.java.jdbc :as sql]
              [shouter.models.db :as db]))

(defn create-shouts []
    (sql/with-connection db/db-connection
        (sql/create-table :shouts
            [:id :integer "PRIMARY KEY" "AUTO_INCREMENT"]
            [:body "varchar(150)" "NOT NULL"]
            [:created_at :timestamp "NOT NULL" "DEFAULT CURRENT_TIMESTAMP"])))

(defn -main []
    (print "Creating database structure...") (flush)
    (create-shouts)
    (println " done"))