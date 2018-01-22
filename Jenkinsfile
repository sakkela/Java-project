pipeline {

    agent any
    options{
        buildDiscarder(logRotator(numToKeepStr: '2', artifactNumToKeepStr: '1'))
    }

    stages {
        stage('Unit Test')
        stage('build'){
            steps{
                sh 'ant -f test.xml -v'
                junit 'Reports/result.xml'
            }

        }
            steps{
              sh 'ant -f build.xml -v'
            }
        }
    }

    post{
        always{
            archiveArtifacts artifacts: 'dist/*.jar', fingerprint: true
        }
    }

}