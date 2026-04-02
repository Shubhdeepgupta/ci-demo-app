pipeline {
 
    agent { label 'mac-agent' }
 
    environment {

        DOCKER_IMAGE = "shubhdeep06/ci-demo-app"

        EC2_IP = "3.27.61.243"

    }
 
    stages {
 
        stage('Checkout Code') {

            steps {

                checkout scm

            }

        }
 
        stage('Build JAR') {

            steps {

                sh 'mvn clean package'

            }

        }
 
        stage('Build & Push Docker Image') {

            steps {

                withCredentials([usernamePassword(

                    credentialsId: 'dockerhub-creds',

                    usernameVariable: 'USER',

                    passwordVariable: 'PASS'

                )]) {

                    sh '''

                    # Login to DockerHub

                    echo $PASS | docker login -u $USER --password-stdin
 
                    # Create and use buildx builder

                    docker buildx create --use || true
 
                    # Build for amd64 and push

                    docker buildx build --platform linux/amd64 -t $DOCKER_IMAGE:latest --push .

                    '''

                }

            }

        }
 
        stage('Deploy to EC2') {

            steps {

                sshagent(['ec2-key']) {

                    sh '''

                    ssh -o StrictHostKeyChecking=no ubuntu@$EC2_IP "

                    docker rm -f ci-container || true &&

                    docker pull $DOCKER_IMAGE:latest &&

                    docker run -d -p 8081:8080 --name ci-container $DOCKER_IMAGE:latest

                    "

                    '''

                }

            }

        }

    }
 
    post {

        success {

            echo 'Deployment Successful 🚀'

        }

        failure {

            echo 'Deployment Failed ❌'

        }

    }

}
 
