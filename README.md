# Jenkins Shared Library


This repo demonstrate the minimal example for Jenkins Shared Library.

## Setup your Jenkins before playing with this Repo

Jenkins -> Manage Jenkins -> Configure System -> Search: Global Pipeline Libraries.

Fill in git parameters.
Check "Load Implicitly" or declare @Library("${LibraryName}") at begin of your Jenkinsfile. 

## Update your repo

Put `exportOptions.plist` and `updateExportOptions.rb` to root directory in your repository. These files are rquired by archive step.

## Sample Usage

`
    
    @Library('jenkins-shared-lib-example') _
    pipeline {
        agent any

        stages {
            stage("iOS Unity Build") {     
                steps {
                    iOSUnityBuild pwd(), params.build_type, "./builds"
                }
            }
            ...
        }
    }
`
