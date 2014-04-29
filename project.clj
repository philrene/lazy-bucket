(defproject lazy-bucket "0.1.0-SNAPSHOT"
  :description "TODO"
  :url "TODO"
  :license {:name "TODO: Choose a license"
            :url "http://choosealicense.com/"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [clj-jgit "0.7.0-alpha2"]
                 [clj-http "0.9.1"]
                 [cheshire "5.3.1"]
                 [environ "0.5.0"]
                 [im.chit/ribol "0.4.0"]
                 [com.taoensso/timbre "3.1.6"]]
  :profiles {:dev {:dependencies [[org.clojure/tools.namespace "0.2.4"]]
                   :source-paths ["dev"]}})
