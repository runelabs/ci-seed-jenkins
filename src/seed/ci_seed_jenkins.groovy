/**
  * Simple provisional Groovy Jenkins DSL jobs generator.
  * Only echoes the name of each branch on job execution.
**/

import groovy.json.JsonSlurper

String folderPath = 'rune'
//String repo = 'runelabs/ci-seed-jenkins'
//String jobPrefix = 'dsl-seed'
String repo = 'NetlifeBackupSolutions/CaptureXamarin'
String jobPrefix = 'uniwin'

folder(folderPath) {
  description 'Rune - initial folder/job-branch creation.'
}
folder "$folderPath/$jobPrefix"

URL gitBranchUrl = "https://api.github.com/repos/$repo/branches".toURL()
List gitBranches = new JsonSlurper().parse(gitBranchUrl.newReader())

gitBranches.each { branch ->
  String branchName = branch.name.replaceAll('/', '-')
  String jobBase = "$jobPrefix/$branchName"
  String jobPath = "$folderPath/$jobBase"

  folder "$jobPath"

  String jobName = "build-seed"
  String jobFullPath = "$jobPath/$jobName"

  job("$jobFullPath") {
    scm {
      github repo, branch.name, 'ssh'
      credentials('AutotesterNetlife')
    }
    triggers {
      scm 'H/5 * * * *'
    }
    steps {
      shell "echo $jobFullPath"
      shell 'date'
      shell 'git branch -va'
    }
  }
}
