
(ns shouter.components.add-active-user
    (:use ring.util.response)
    (:require [clojure.java.jdbc :as j]
      [clojure.tools.logging :as log]
      [compojure.core :as comp]
      [clj-time.coerce :as tco]
      [shouter.models.db :as db]))


(defn get-team-by-id
      [id]
      (first (filter #(= (get % :entity-id) id)
                     [
                      {:entity-id "1235831d-4a46-42bd-a16c-69a748q5q1w2"
                       :team-name "Falcons"
                       :team-state "Georgia"
                       :team-city "Atlanta"
                       :state-name "active"
                       }
                      {:entity-id "2335831d-4b46-42bd-a16c-61a748q5as13"
                       :team-name "Ravens"
                       :team-state "Maryland"
                       :team-city "Baltimore"
                       :state-name "active"
                       }
                      {:entity-id "4125931c-4a46-42bd-a96c-69a748e4b7d6"
                       :team-name "Patriots"
                       :team-state "Massachusetts"
                       :team-city "Boston"
                       :state-name "active"
                       }
                      {:entity-id "3c0bce60-c1cc-4110-980d-e894ef647c41"
                       :team-name "Bills"
                       :team-state "New York"
                       :team-city "Buffalo"
                       :state-name "active"
                       }
                      {:entity-id "e0b6ac74-92c9-4610-af4b-f40511fa5ce1"
                       :team-name "Panthers"
                       :team-state "North Carolina"
                       :team-city "Charlotte"
                       :state-name "active"
                       }
                      {:entity-id "00eb06da-a188-421f-807e-79ece879949e"
                       :team-name "Bears"
                       :team-state "Illinois"
                       :team-city "Chicago"
                       :state-name "active"
                       }
                      {:entity-id "995cd551-b92b-4f8f-b3f2-72e7d275c870"
                       :team-name "Bengals"
                       :team-state "Ohio"
                       :team-city "Cincinatti"
                       :state-name "active"
                       }
                      {:entity-id "11c89d21-e612-4b15-9993-f44ef02ef369"
                       :team-name "Browns"
                       :team-state "Ohio"
                       :team-city "Cleveland"
                       :state-name "active"
                       }
                      {:entity-id "aed984b5-4ea4-4736-89a5-5597c6c17aa9"
                       :team-name "Cowboys"
                       :team-state "Texas"
                       :team-city "Dallas"
                       :state-name "active"
                       }
                      {:entity-id "5ef0916c-943d-4520-af1d-1dac65e6c168"
                       :team-name "Broncos"
                       :team-state "Colorado"
                       :team-city "Denver"
                       :state-name "active"
                       }
                      {:entity-id "7648e151-0a74-4537-801a-64c848edf6ce"
                       :team-name "Lions"
                       :team-state "Michigan"
                       :team-city "Detroit"
                       :state-name "active"
                       }
                      {:entity-id "1f938839-9cce-43d9-ac84-c403a51b2599"
                       :team-name "Colts"
                       :team-state "Indiana"
                       :team-city "Indianapolis"
                       :state-name "active"
                       }
                      {:entity-id "c915d87d-be4f-4f99-a904-b9160bc0bbee"
                       :team-name "Chiefs"
                       :team-state "Missouri"
                       :team-city "Kansas City"
                       :state-name "active"
                       }
                      {:entity-id "0354e4d7-ece6-4da2-8a80-e1e7679e6531"
                       :team-name "Dolphins"
                       :team-state "Florida"
                       :team-city "Miami"
                       :state-name "active"
                       }
                      {:entity-id "d0572de4-0gc2-438d-812b-601d3691622m"
                       :team-name "Packers"
                       :team-state "Wisconsin"
                       :team-city "Green Bay"
                       :state-name "active"
                       }
                      {:entity-id "d0582dd4-0fc6-438d-812b-601d269252sd"
                       :team-name "Vikings"
                       :team-state "Minnessota"
                       :team-city "Minneapolis"
                       :state-name "active"
                       }
                      {:entity-id "d0582dd4-0fc6-438d-812b-601d365152fv"
                       :team-name "Titans"
                       :team-state "Tennessee"
                       :team-city "Nashville"
                       :state-name "active"
                       }
                      {:entity-id "d0582dd6-0fc6-438d-812b-601d369152zx"
                       :team-name "Eagles"
                       :team-state "Pennsylvania"
                       :team-city "Philadelphia"
                       :state-name "active"
                       }
                      {:entity-id "d0582dd4-0fc6-438d-812b-601d369152ec"
                       :team-name "Raiders"
                       :team-state "California"
                       :team-city "Oakland"
                       :state-name "active"
                       }
                      {:entity-id "b1685d73-7788-462d-9ead-3201a435b93b"
                       :team-name "Jets"
                       :team-state "New York"
                       :team-city "New York"
                       :state-name "active"
                       }
                      {:entity-id "edc7d710-a275-4237-bdd3-15d9d983da46"
                       :team-name "Giants"
                       :team-state "New York"
                       :team-city "New York"
                       :state-name "active"
                       }
                      {:entity-id "90b22a20-54ff-4f30-bbe9-0e187aff1fc3"
                       :team-name "Seahawks"
                       :team-state "Washington"
                       :team-city "Seattle"
                       :state-name "active"
                       }
                      {:entity-id "fa922a7c-6166-4468-a6a4-699efb47fadf"
                       :team-name "49ers"
                       :team-state "California"
                       :team-city "San Francisco"
                       :state-name "active"
                       }
                      {:entity-id "ef0c2640-35a5-4c42-8fa0-8a1f2e27ecb3"
                       :team-name "Chargers"
                       :team-state "California"
                       :team-city "San Diego"
                       :state-name "active"
                       }
                      {:entity-id "ce5e74fd-881a-4ca7-9ac6-b7cdce6b2b47"
                       :team-name "Rams"
                       :team-state "Missouri"
                       :team-city "St Louis"
                       :state-name "active"
                       }
                      {:entity-id "53fd6fde-1c8b-4f49-9b85-c4f7ba47aeae"
                       :team-name "Steelers"
                       :team-state "Pennsylvania"
                       :team-city "Pittsburgh"
                       :state-name "active"
                       }
                      {:entity-id "c0ee33a8-3340-4890-829b-3d8b2ed3f410"
                       :team-name "Cardinals"
                       :team-state "Arizona"
                       :team-city "Phoenix"
                       :state-name "active"
                       }
                      {:entity-id "5ea3f0b7-b45b-4f9f-abc4-419ecca08eed"
                       :team-name "Saints"
                       :team-state "Louisiana"
                       :team-city "New Orleans"
                       :state-name "active"
                       }
                      {:entity-id "47431c13-d68f-4cde-ad09-a28516d7cce0"
                       :team-name "Buccaneers"
                       :team-state "Florida"
                       :team-city "Tampa"
                       :state-name "active"
                       }
                      {:entity-id "bfec398e-9f9e-45fc-82f0-c05c23dbc20d"
                       :team-name "Redskins"
                       :team-state "DC"
                       :team-city "Washington"
                       :state-name "active"
                       }
                      {:entity-id "1e11489b-409b-49ab-b715-f02ebff666f8"
                       :team-name "Texans"
                       :team-state "Texas"
                       :team-city "Houston"
                       :state-name "active"
                       }
                      ])
      ))


(defn check-for-team-in-db
      [id]
      (db/query db/db-connection
        [(str "select entity_id from team where entity_id =? ")id]
        response (:result-set-fn first)))

(defn check-for-user-ln
      [ln]
      (db/query db/db-connection
                [(str "select last_name from users where last_name =? ")ln]
                response (:result-set-fn first)))

(defn post-team
      [selectTeam]
      (db/execute! db/db-connection
       (str "insert into team (entity_id, team_name, team_state, team_city, state_name) VALUES (?,?,?,?,?)")
       [(get selectTeam :entity-id)
        (get selectTeam :team-name)
        (get selectTeam :team-state)
        (get selectTeam :team-city)
        (get selectTeam :state-name)]))

(defn post-user
      [body] (db/execute! db/db-connection
       (str "insert into users (entity_id, first_name, last_name, nick_name, state_name, team_id) VALUES (?,?,?,?,?,?)")
       [(str (db/make-uuid))
        (get body :first_name)
        (get body :last_name)
        (get body :nick_name)
        (get body :state_name)
        (get body :team_id)]))


(defn add-user
      [body]
      (let [team? (check-for-team-in-db (get body :team_id))
            selectTeam (get-team-by-id (get body :team_id))
            user? (check-for-user-ln (get body :last_name))]

           (if (= user? [])
             (if (= team? [])
               {:team (post-team selectTeam)
                :user (post-user body)}

               {:user (post-user body)}
               )
             {:response (str "duplicate user")}
             )
       ))




