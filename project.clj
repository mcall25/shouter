(defproject shouter "0.0.1"
    :description "Shouter app"
    :url "http://github.com/abadongutierrez/shouter"
    :dependencies [
        [org.clojure/clojure "1.4.0"]
        [mysql/mysql-connector-java "5.1.18"]
        [org.clojure/java.jdbc "0.2.3"]
        [ring/ring-jetty-adapter "1.1.6"]
        [compojure "1.1.3"]
        [hiccup "1.0.2"]]
    :main shouter.core)