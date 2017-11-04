(ns shouter.models.db)

(def db-connection {:classname "com.mysql.jdbc.Driver"
                    :subprotocol "mysql"
                    :subname (str "//localhost/shouter")
                    :user "root" :password "root"})