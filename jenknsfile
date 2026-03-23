pipeline {

    agent { label 'mac' }

    tools {
        maven 'maven-3'
        jdk 'jdk17'
    }

    environment {
        DOCKER_IMAGE = "shubhdeep06/ci-demo-app"
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
                docker build -t $DOCKER_IMAGE:${BUILD_NUMBER} .
                docker tag $DOCKER_IMAGE:${BUILD_NUMBER} $DOCKER_IMAGE:latest
                '''
            }
        }

        stage('Docker Login') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'dockerhub-creds',
                    usernameVariable: 'USER',
                    passwordVariable: 'PASS'
                )]) {
                    sh '''
                    echo $PASS | docker login -u $USER --password-stdin
                    '''
                }
            }
        }

        stage('Push Image') {
            steps {
                sh '''
                docker push $DOCKER_IMAGE:${BUILD_NUMBER}
                docker push $DOCKER_IMAGE:latest
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
                $DOCKER_IMAGE:${BUILD_NUMBER}
                '''
            }
        }

    }

    post {

        success {
            echo 'Production CI/CD Successful'
        }

        failure {
            echo 'CI/CD Failed'
        }

    }
}