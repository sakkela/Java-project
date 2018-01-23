pipeline {

    agent none
    
    environment{
        MAJOR_VERSION =1
    }

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
                //sh " if ![ -d "/var/www/html/rectangles/all/${env.BRANCH_NAME}" ]; then mkdir -p /var/www/html/rectangles/all/${env.BRANCH_NAME}; fi"
                sh "mkdir -p /var/www/html/rectangles/all/${env.BRANCH_NAME}"
                sh "cp dist/rectangle_${env.MAJOR_VERSION}.${env.BUILD_NUMBER}.jar /var/www/html/rectangles/all/${env.BRANCH_NAME}"
            }
        }
    
        stage('Running on CentOS') {
            agent{
                label 'centOS'
        
            }

            steps{
                sh "wget http://sakkela3.mylabserver.com/rectangles/all/${env.BRANCH_NAME}/rectangle_${env.MAJOR_VERSION}.${env.BUILD_NUMBER}.jar"
                sh "java -jar rectangle_${env.MAJOR_VERSION}.${env.BUILD_NUMBER}.jar 3 4"

            }
        }
        
        stage("Test on Debian"){
           
            agent{
                docker 'openjdk:8u121-jre'
            }

             steps{
                sh "wget http://sakkela3.mylabserver.com/rectangles/all/${env.BRANCH_NAME}/rectangle_${env.MAJOR_VERSION}.${env.BUILD_NUMBER}.jar"
                sh "java -jar rectangle_${env.MAJOR_VERSION}.${env.BUILD_NUMBER}.jar 3 4"
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
                sh "cp /var/www/html/rectangles/all/${env.BRANCH_NAME}/rectangle_${env.MAJOR_VERSION}.${env.BUILD_NUMBER}.jar /var/www/html/rectangles/green/rectangle_${env.MAJOR_VERSION}.${env.BUILD_NUMBER}.jar" 
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
                sh 'git pull --rebase origin master'
                sh 'git merge development'
                sh 'git commit -m "Merging development and prod ${env.MAJOR_VERSION}.${env.BUILD_NUMBER}"' 
                sh 'git push origin master'
                echo 'tagging release'
                sh "git tag rectangle-${env.MAJOR_VERSION}.${env.BUILD_NUMBER}"
                sh "git push origin rectangle-${env.MAJOR_VERSION}.${env.BUILD_NUMBER}"
            }
        }

        
    
    }
   

}