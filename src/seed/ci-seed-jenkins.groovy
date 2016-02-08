/**
  * Simple provisional Groovy Jenkins DSL jobs generator.
  * Only echoes the name of each branch on job execution.
**/

import groovy.json.JsonSlurper

String folderPath = 'rune'
String repo = 'runelabs/ci-seed-jenkins'
String jobPrefix = 'dsl-seed'

folder(folderPath) {
  description 'Rune - initial folder/job-branch creation.'
}

URL gitBranchUrl = "https://api.github.com/repos/$repo/branches".toURL()
List gitBranches = new JsonSlurper().parse(branchUrl.newReader())

branches.each { branch ->
  String branchName = branch.name.replaceAll('/', '-')
  String jobName = "$jobPrefix/$branchName"
  String jobPath = "$folderPath/$jobName"
    
  folder "$jobPath"

  job("$jobPath/build-seed") {
    scm {
      github repo, branch.name
    }
    triggers {
      scm 'H/5 * * * *'
    }
    steps {
      shell 'echo job placeholder ; date ; git branch -va'
    }
  }
}
