(ns shouter.views.get-all-teams-by-id
    (:use ring.util.response)
    (:require [clojure.java.jdbc :as j]
      [clojure.tools.logging :as log]
      [compojure.core :as comp]
      [clj-time.coerce :as tco]
      [shouter.models.db :as db]))


(defn get-all-teams-for-winner-loser
      [winner-id loser-id]
      (println (str "testing") winner-id)
      (println (str "testing") loser-id)
      )

