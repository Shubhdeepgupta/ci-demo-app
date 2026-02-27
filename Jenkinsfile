pipeline {

    agent any

    tools {
        maven 'maven-3'
        jdk 'jdk17'
    }

    stages {

        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Docker Build') {
            steps {
                sh '''
                docker build -t ci-demo-app:${BUILD_NUMBER} .
                '''
            }
        }

        stage('Deploy') {
            steps {
                sh '''
                docker stop ci-container || true
                docker rm ci-container || true

                docker run -d \
                --name ci-container \
                -p 8081:8080 \
                ci-demo-app:${BUILD_NUMBER}
                '''
            }
        }

    }

    post {

        success {
            echo 'Deployment Successful'
        }

        failure {
            echo 'Deployment Failed'
        }

    }
}