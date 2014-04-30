(ns ^{:doc "Create and update pull request based on your project"
      :author "Phil Rene"}
  leiningen.lazy-bucket
  (:require [leiningen.core.main :as main]
            [lazy-bucket.core :refer :all]))

(def global-config "~/.lein/lazy-bucket.clj")

(defn  ^:higher-order ^:no-project-needed lazy-bucket
  [project & args]
  (let [args (apply hash-map args)
        options [(read-file global-config)
                 (.getAbsolutePath (clojure.java.io/as-file ""))
                 (get args ":title")
                 (get args ":description")]
        all-options (reduce-kv
                     (fn [init k v]
                       (conj init (keyword (apply str (drop 1 k))) (set (if (coll? v) v [v]))))
                     options (select-keys args [":excluded" ":included"]))]
    (println (apply pull-request all-options))))
