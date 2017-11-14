(ns shouter.models.db
  (:use ring.util.response)
  (:require [clojure.java.jdbc :as j]
            [clojure.tools.logging :as log]
            [compojure.core :as comp]
            [clj-time.coerce :as tco]))

(def db-connection {:classname "com.mysql.jdbc.Driver"
                    :subprotocol "mysql"
                    :subname (str "//localhost/shouter")
                    :user "root" :password "root"})


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