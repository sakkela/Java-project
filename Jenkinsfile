pipeline {

    agent {
        label 'none'
    } 

    options{
        buildDiscarder(logRotator(numToKeepStr: '2', artifactNumToKeepStr: '1'))
    }

    stages {
        agent{
            label 'apache'
        }
        stage('Unit Test'){
             steps{
                sh 'ant -f test.xml -v'
                junit 'reports/result.xml'
            }
        }
        stage('build'){
             agent{
                label 'apache'
            }
                   
            steps{
              sh 'ant -f build.xml -v'
            }
        }
        stage('deploy') {
             agent{
                label 'apache'
            }

            steps{
                sh "cp dist/rectangle_${env.BUILD_NUMBER}.jar /var/www/html/rectangles/all/"
            }
        }
    
    stage('Running on CentOS') {
         agent{
            label 'centOS'
        }

        steps{
            sh "wget http://sakkela3.mylabserver.com/rectangles/all/rectangle_${env.BUILD_NUMBER}.jar"
            sh "java -jar rectanlge__${env.BUILD_NUMBER}.jar 3 4"

        }
    }
    }
    post{
        always{
            archiveArtifacts artifacts: 'dist/*.jar', fingerprint: true
        }
    }

}