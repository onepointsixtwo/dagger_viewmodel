# Release

## Pre-Requisites

The pre-requisites for releasing this are as follows:

1. The GPG key must be set (this is backed up).

2. There must be a global or local gradle configuration setup with the values nexusUsername, nexusPassword for your sonatype username and password.


## Upload

Simply run the command `./gradlew uploadArchives`


## Release

- Login to Sonatype and then go to "Staging Repositories"
- Find the one matching 'onepointsixtwo' and check the version etc. is what you're expecting
- Click close to deploy to maven central