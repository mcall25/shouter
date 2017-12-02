(defproject shouter "0.0.1"
    :description "Shouter app"
    :url "http://github.com/abadongutierrez/shouter"
    :dependencies [
        [org.clojure/clojure "1.4.0"]
        [mysql/mysql-connector-java "5.1.18"]
        [org.clojure/java.jdbc "0.3.3"]
        [org.clojure/tools.logging "0.3.1"]
        [ring/ring-jetty-adapter "1.1.6"]
        [compojure "1.1.3"]
        [hiccup "1.0.2"]
        [ring/ring-json "0.4.0"]
        [ring-cors "0.1.11"]
                   ]
    :plugins [[lein-ring "0.7.3"]]
    :ring {:handler shouter.controllers.shouts/app}
    :main shouter.core)