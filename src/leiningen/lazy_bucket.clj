(ns ^{:doc "Create and update pull request based on your project"
      :author "Phil Rene"}
  leiningen.lazy-bucket
  (:require [leiningen.core.main :as main]
            [lazy-bucket.core :refer :all]))

(def global-config "~/.lein/lazy-bucket.clj")

(defn  ^:higher-order ^:no-project-needed lazy-bucket
  [project & args]
  (println (pull-request (read-file global-config)
                         (.getAbsolutePath (clojure.java.io/as-file ""))
                         "blah"
                         "description")))
