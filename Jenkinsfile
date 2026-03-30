pipeline {

    agent {label 'mac' }

    environment {
        DOCKER_IMAGE = "shubhdeep06/ci-demo-app"
        ec2_ip = "3.27.61.243"
    }

    stages {
        stage('Deploy to EC2') {
            steps {
                sshagent(['ec2-key']) {
                    sh '''
                    echo "Connecting to EC2..."
                    ssh -o StrictHostKeyChecking=no ubuntu@$ec2_ip "
 
                    echo 'Stopping old container'
                    docker stop ci-container || true
 
                    echo 'Removing old container'
                    docker rm ci-container || true
 
                    echo 'Pulling latest image'
                    docker pull $DOCKER_IMAGE:latest
 
                    echo 'Running container'
                    docker run -d -p 8081:8080 --name ci-container $DOCKER_IMAGE:latest
 
                    echo 'Done'
                    "
                    '''
                }
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
