(ns shouter.models.shout
    (:use ring.util.response)
    (:require [clojure.java.jdbc :as j]
              [clojure.tools.logging :as log]
              [compojure.core :as comp]
              [clj-time.coerce :as tco]
              [shouter.models.db :as db]))

(defn make-uuid [] (java.util.UUID/randomUUID))

(defn query [spec [sql & params :as statement] & opts]
  (log/info (str "executing query: " sql))
  (log/debug
    (with-out-str
      (println "sql params: ")
      (clojure.pprint/pprint params)))
  (let [result (apply j/query spec statement (concat opts [:identifiers #(.replace % \_ \-)]))]
    (log/info "query executed.")
    (log/debug
      (with-out-str
        (println "query result:")
        (clojure.pprint/pprint result)))
    result))

(defn execute! [spec sql & params]
  (log/info (str "executing statement: " sql))
  (log/debug
    (with-out-str
      (println "sql params:")
      (clojure.pprint/pprint params)))
  (let [result (apply j/db-do-prepared spec sql params)]
    (log/info "sql executed.")
    (log/debug
      (with-out-str
        (println "sql result:")
        (clojure.pprint/pprint result)))
    result))


(defn all-users
  []
  (let [res (query
              db/db-connection
              (str "select * from users")
              response (:result-set-fn first))]
    (map (fn [row]
           {:fn (row :first-name)
            :ln (row :last-name)
            :nick-name (row :nick-name)
            :entity-id (row :entity-id)
            }
           )res)))

(defn add-user
  [body]
  (execute!
    db/db-connection
    (str "insert into users (entity_id, first_name, last_name, nick_name, team_name) "
         "VALUES (?,?,?,?,?) ")
    [(str (make-uuid))
     (get body :first_name)
     (get body :last_name)
     (get body :nick_name)
     (get body :team_name)
     ]))

(defn delete-user
  [body]
  (execute!
    db/db-connection
    (str "delete from users where entity_id = ? ")
    [(get body :id )]))







