pipeline {
 
    agent { label 'mac' }
 
    environment {

        DOCKER_IMAGE = "shubhdeep06/ci-demo-app"

        EC2_IP = "3.27.61.243"

    }
 
    stages {
 
        stage('Build JAR') {

            steps {

                sh 'mvn clean package'

            }

        }
 
        stage('Build Docker Image') {

            steps {

                sh '''

                docker buildx create --use || true

                docker buildx build \

                --platform linux/amd64 \

                -t $DOCKER_IMAGE:latest \

                --push .

                '''
            }

        }
 
        stage('Push to DockerHub') {

            steps {

                withCredentials([usernamePassword(credentialsId: 'dockerhub-creds', usernameVariable: 'USER', passwordVariable: 'PASS')]) {

                    sh '''

                    echo $PASS | docker login -u $USER --password-stdin

                    docker push $DOCKER_IMAGE:latest

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
 
