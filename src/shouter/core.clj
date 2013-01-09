(ns shouter.core
    (:use [compojure.core :only (defroutes GET)]
          [ring.adapter.jetty :as ring])
    (:use [hiccup.page :only [html5]]))

(defn index []
    (html5
        [:head
            [:title "Hello World"]]
        [:body
            [:div {:id "content"} "Hello World by Rafa"]]))

(defroutes routes
    (GET "/" [] (index)))

(defn -main []
  (run-jetty routes {:port 9090 :join? false}))

(comment
(defn start []
    (ring/run-jetty #'routes {:port 9090 :join? false}))
)