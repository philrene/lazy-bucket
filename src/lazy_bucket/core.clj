(ns lazy-bucket.core
  (:require [lazy-bucket.git :refer :all]
            [lazy-bucket.bitbucket :refer :all]
            [taoensso.timbre :as log]
            [environ.core :refer :all]))

(def global-config "~/.lein/lazy-bucket.clj")

(defn- basic-auth
  [config]
  [(:username config) (:password config)])

(defn read-file
  [file-url]
  (let [file-url (if (= "~" (str (first file-url)))
                   (apply str (:home env) (drop 1 file-url))
                   file-url)]
    (binding [*read-eval* false]
      (when (.exists (clojure.java.io/as-file file-url))
        (read-string (slurp file-url))))))

(defn- reviewers
  [repo-path config]
  ;; fetch on bitbucket the users and match names with users in bitbucket for the team
  (let [transform (fn [x]
                    (clojure.string/replace
                     (clojure.string/lower-case x) #"\s" ""))
        u-from-repo (set (map transform (git-repo-authors repo-path)))
        u-from-config (group-by #(transform (get % :name))
                                (:username-matches config))
        u-from-bitbucket (group-by #(transform (get % "display_name"))
                                   (users-for-team :team (:team config)
                                                   :basic-auth (basic-auth config)))]
    (clojure.set/difference (set (concat
                                  (:reviewers config)
                                  (remove nil? (map
                                                #(or (-> (get u-from-bitbucket %)
                                                         first
                                                         (get "username"))
                                                     (-> (get u-from-config %)
                                                         first
                                                         :username)
                                                     (do (log/info "Could not find a match for " %)
                                                         nil))
                                                u-from-repo))))
                            (set [(:username config)]))))

(defn pull-request
  [config repo-path title description]
  (create-pr :basic-auth (basic-auth config)
             :owner (git-owner repo-path)
             :repo (git-repo repo-path)
             :source-branch (git-current-branch repo-path)
             :title title
             :description description
             :reviewers (reviewers repo-path config)))
