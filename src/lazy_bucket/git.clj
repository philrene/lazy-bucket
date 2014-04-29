(ns lazy-bucket.git
  (:require [clojure.string :as st]
            [clj-jgit.porcelain :as git]
            [clj-jgit.querying :as qgit]))

(defn git-repo-authors
  "usage:
  (git-repo-authors \"/path/to/repo\""
  [repo-path]
  (git/with-repo repo-path
    (->> (git/git-log repo)
         (take 500)
         (map (partial qgit/commit-info repo))
         (map :author)
         set)))

(defn git-current-branch
  [repo-path]
  (git/with-repo repo-path
    (.. repo (getRepository) (getBranch))))

(defn- git-url-parse
  [repo-path remote]
  (-> (git/with-repo repo-path
        (.. repo (getRepository) (getConfig) (getString "remote" remote "url")))
      (st/split #":")
      second
      (st/split #"/")))

(defn git-owner
  [repo-path & {:keys [remote] :or {remote "origin"}}]
  (first (git-url-parse repo-path remote)))

(defn git-repo
  [repo-path & {:keys [remote] :or {remote "origin"}}]
  (-> (git-url-parse repo-path remote)
      second
      (st/split #"\.")
      first))
