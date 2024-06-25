job('Java-Maven-App-DSL-v7') {
    description('Java Maven App con DSL para el curso de Jenkins sin espacios version 7')
    scm {
        git('https://github.com/josebaezae/simple-java-maven-app.git', 'master') { node ->
            node / gitConfigName('josebaezae')
            node / gitConfigEmail('josebaezae@gmail.com')
        }
    }
    steps {
        maven {
          mavenInstallation('mavenjenkins')
          goals('-B -DskipTests clean package')
        }
        maven {
          mavenInstallation('mavenjenkins')
          goals('test')
        }
        shell('''
          echo "Entrega 7: Desplegando la aplicación"
          java -jar "/var/jenkins_home/workspace/Java-App-Con-Maven/target/my-app-1.0-SNAPSHOT.jar"
        ''') 
    }
    publishers {
        archiveArtifacts('target/*.jar')
        archiveJunit('target/surefire-reports/*.xml')
	slackNotifier {
            notifyAborted(true)
            notifyEveryFailure(true)
            notifyNotBuilt(false)
            notifyUnstable(false)
            notifyBackToNormal(true)
            notifySuccess(true)
            notifyRepeatedFailure(false)
            startNotification(false)
            includeTestSummary(false)
            includeCustomMessage(false)
            customMessage(null)
            sendAs(null)
            commitInfoChoice('NONE')
            teamDomain(null)
            authToken(null)
       }
    }
}
