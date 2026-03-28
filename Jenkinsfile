pipeline {

    agent {label 'mac' }

    environment {
        DOCKER_IMAGE = "shubhdeep06/ci-demo-app"
        ec2_ip = "3.27.61.243"
    }

    stages {

        stage('Build')
            steps { 
                sh 'mvn clean package'
            }
        }

        stage('Docker Build & Push') {
            steps {
                sh '''
                docker buildx build \
                --platform linux/amd64 \
                -t $DOCKER_IMAGE:latest \
                --push .
                '''
            }
        }

        stage('Deploy to EC2') {
        steps {
            sshagent(['ec2-key']) {
                sh '''
                ssh -o StrictHostKeyChecking=no ubuntu@$EC2_IP "
                docker stop ci-container || true &&
                docker rm ci-container || true &&
                docker pull $DOCKER_IMAGE:latest &&
                docker run -d -p 8081:8080 --name ci-container $DOCKER_IMAGE:latest
                "
                '''
            }
        }
    }

    post {
        success {
            echo 'Deployment Successful !!'
        }
        failure {
            echo 'Deployment Failed !!'
        }
    }
}
