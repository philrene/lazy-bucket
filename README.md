# lazy-bucket
Test

Create a pull request for any project hosted on bitbucket

It looks in the current project and open a pull request
against integration for the current branch.

The reviewers are specified either from :

~/.lein/lazy-bucket.clj
<project>/.lazy-bucket.clj
or in command line with :excluded and :included

please follow examples and templates at the root of the project

You will need to setup at lest ~/.lein/lazy-bucket.clj from
lazy-bucket_example.clj


## Releases and Dependency Information

put [lazy-bucket "0.1.0-SNAPSHOT] in you plugins section
of you ~/.lein/profiles.clj

## Change Log

* Version 0.1.0-SNAPSHOT

## TODO

- use OAuth instead of basic auth
- make description 'git commit msg' like
- make README.md nicer with some markdowns


## Copyright and License

Copyright © 2014 Philippe Rene
