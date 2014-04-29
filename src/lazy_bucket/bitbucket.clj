(ns lazy-bucket.bitbucket
  (:require [clj-http.client :as client]
            [ribol.core :refer :all]
            [cheshire.core :refer :all]))

(defn create-pr
  [& {:keys [owner repo basic-auth title description source-branch destination-branch reviewers close-branch]
      :or {destination-branch "integration"
           close-branch true}}]
  (client/post (format "https://bitbucket.org/api/2.0/repositories/%s/%s/pullrequests/" owner repo)
               {:basic-auth basic-auth
                :content-type :json
                :body (generate-string {"source" {"branch" {"name" source-branch}}
                                        "destination" {"branch" {"name" destination-branch}}
                                        "title" title
                                        "description" description
                                        "reviewers" (map #(hash-map "username" %) reviewers)
                                        "close_source_branch" close-branch})}))

(defn users-for-team
  [& {:keys [team basic-auth]}]
  (-> (client/get (format "https://bitbucket.org/api/2.0/teams/%s/members" team)
                  {:basic-auth basic-auth})
      :body
      parse-string
      (get "values")
      ((fn [users]
         (map #(select-keys % ["username" "display_name"]) users)))))
