pipeline {
    agent any

    tools {
        maven 'maven-3'
        jdk 'jdk17'
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Package') {
            steps {
                sh 'mvn package'
            }
        }
        stage('Docker Build') {
            steps {
                sh 'docker build -t ci-demo-app:${BUILD_NUMBER} .'
            }
        }

	stage('Deploy') {
    	    steps {
        	sh '''
                docker stop ci-container || true
                docker rm ci-container || true
                docker run -d -p 8081:8080 --name ci-container ci-demo-app:${BUILD_NUMBER}
        '''
	    }
	}
    }


    post {
        success {
            archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            echo 'Build completed successfully!'
        }
        failure {
            echo 'Build failed!'
        }
        always {
            echo 'Pipeline finished.'
        }
    }
}