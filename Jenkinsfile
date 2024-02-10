pipeline {
  agent any

  environment {
    DOCKERHUB_CREDENTIALS = credentials('DOCKERHUB_CREDENTIALS')
    VERSION = "${env.BUILD_ID}"
  }

  tools {
    maven "Maven"
  }

  stages {

    stage('Maven Build'){
        steps{
        sh 'mvn clean package  -DskipTests'
        }
    }

     stage('Run Tests') {
      steps {
        sh 'mvn test'
      }
    }

    stage('SonarQube Analysis') {
  steps {
    sh 'mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent install sonar:sonar -Dsonar.host.url=http://192.168.1.165:9000/ -Dsonar.login=squ_c61ca4a64a8d3b5da680ef2ab8f6664b9096f69d'
  }
}


   stage('Check code coverage') {
            steps {
                script {
                    def token = "squ_c61ca4a64a8d3b5da680ef2ab8f6664b9096f69d"
                    def sonarQubeUrl = "http://192.168.1.165:9000/api"
                    def componentKey = "com.codeddecode:restaurantlisting"
                    def coverageThreshold = 80.0

                    def response = sh (
                        script: "curl -H 'Authorization: Bearer ${token}' '${sonarQubeUrl}/measures/component?component=${componentKey}&metricKeys=coverage'",
                        returnStdout: true
                    ).trim()

                    def coverage = sh (
                        script: "echo '${response}' | jq -r '.component.measures[0].value'",
                        returnStdout: true
                    ).trim().toDouble()

                    echo "Coverage: ${coverage}"

                    if (coverage < coverageThreshold) {
                        error "Coverage is below the threshold of ${coverageThreshold}%. Aborting the pipeline."
                    }
                }
            }
        }


      stage('Docker Build and Push') {
      steps {
          script {
          // Injecting credentials from DOCKERHUB_CREDENTIALS
          withCredentials([usernamePassword(credentialsId: 'DOCKERHUB_CREDENTIALS', passwordVariable: 'DOCKERHUB_PSW', usernameVariable: 'DOCKERHUB_USR')]) {
              // Now DOCKERHUB_USR and DOCKERHUB_PSW are available as environment variables within this block
              sh 'echo $DOCKERHUB_PSW | docker login -u $DOCKERHUB_USR --password-stdin'
              sh 'docker build -t cristianchira/restaurant-listing-service:${VERSION} .'
              sh 'docker push cristianchira/restaurant-listing-service:${VERSION}'
          }
      }
    }
   }


     stage('Cleanup Workspace') {
      steps {
        deleteDir()

      }
    }


    stage('Update Image Tag in GitOps') {
      steps {
         checkout scmGit(branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[ credentialsId: 'git-ssh', url: 'https://github.com/cristianchira/deployment-folder.git']])
        script {
       sh '''
          sed -i "s/image:.*/image: cristianchira\\/restaurant-listing-service:${VERSION}/" aws/restaurant-manifest.yml
        '''
          sh 'git checkout master'
          // Set Git user email and name for this repository
                sh 'git config user.email "cristianchira@gmail.com"'
                sh 'git config user.name "Cristian CHIRA"'
          sh 'git add .'
          sh 'git commit -m "Update image tag"'
        sshagent(['git-ssh'])
            {
                  sh('git push')
            }
        }
      }
    }

  }

}

