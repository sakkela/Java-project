pipeline {

    agent none
    

    options{
        buildDiscarder(logRotator(numToKeepStr: '2', artifactNumToKeepStr: '1'))
    }

    stages {
        
        stage('Unit Test'){
            agent {
                label 'apache'
            }
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
            post{
                success{
                    archiveArtifacts artifacts: 'dist/*.jar', fingerprint: true
                }
            }
        }
        stage('deploy') {
             agent{
                label 'apache'
            }

            steps{
                sh "mkdir -p /var/www/html/rectangles/all/${env.BRANCH_NAME}"
                sh "cp dist/rectangle_${env.BUILD_NUMBER}.jar /var/www/html/rectangles/all/${env.BRANCH_NAME}"
            }
        }
    
        stage('Running on CentOS') {
            agent{
                label 'centOS'
        
            }
            steps{
                sh "wget http://sakkela3.mylabserver.com/rectangles/all/${env.BRANCH_NAME}/rectangle_${env.BUILD_NUMBER}.jar"
                sh "java -jar rectangle_${env.BUILD_NUMBER}.jar 3 4"

            }
        }
        stage("Test on Debian"){
            agent{
                docker 'openjdk:8u121-jre'
            }
             steps{
                sh "wget http://sakkela3.mylabserver.com/rectangles/all/${env.BRANCH_NAME}/rectangle_${env.BUILD_NUMBER}.jar"
                sh "java -jar rectangle_${env.BUILD_NUMBER}.jar 3 4"
            }
        }
        
        stage("Promote to green"){
            
            agent{
                label 'apache'
            }

            when {
                branch 'master'
            }

            steps{
                sh "cp /var/www/html/rectangles/all/${env.BRANCH_NAME}/rectangle_${env.BUILD_NUMBER}.jar /var/www/html/rectangles/green/rectangle_${env.BUILD_NUMBER}.jar" 
               // sh "cp dist/rectangle_${env.BUILD_NUMBER}.jar /var/www/html/rectangles/green/rectangle_${env.BUILD_NUMBER}.jar"
            }
        }
        stage('promote development branch to master'){

            agent{
                label 'apache'
            }
            when{
                branch 'development'
            }
            steps{
                echo "Stashing Any Local Changes"
                sh 'git stash'
                echo 'Cheching Out Development Branch'
                sh 'git checkout development'
                echo 'checking out master'
                sh 'git checkout master'
                echo 'checking development into master'
                sh 'git merge development'
                sh 'git push origin master'
            }
        }

        
    
    }
   

}