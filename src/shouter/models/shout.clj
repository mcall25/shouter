(ns shouter.models.shout
    (:use ring.util.response)
    (:require [clojure.java.jdbc :as j]
              [clojure.tools.logging :as log]
              [compojure.core :as comp]
              [clj-time.coerce :as tco]
              [shouter.models.db :as db]))

;(defn all []
;    (sql/with-connection db/db-connection
;        (sql/with-query-results results
;            ["select * from shouts order by id desc"]
;            (into [] results))))
;

;(defn all-users []
;    (sql/with-connection db/db-connection
;         (sql/with-query-results results
;             ["select * from users"]
;                                 response (results first))))

;(defn all-users []
;  (let [result (sql/query db/db-connection
;                          [(str "select * from users")]
;                          response (:result-set-fn first))]
;      (println result)
;      {:r result}
;    )
; )

;(defn all-users []
;  (let [r (sql/query db/db-connection
;                     ["select * from users"])]
;    {:row-fn r}
;    )
;  )




;(defn all-users []
;  (sql/query db/db-connection
;             (str "select * from users")
;             response (:result-set-fn first)))


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


(defn all-users
  []
  (query
    db/db-connection
    (str "select * from users")
   response (:result-set-fn first)))


;(defn create [shout]
;    (sql/with-connection db/db-connection
;        (sql/insert-values :shouts [:body] [shout])))