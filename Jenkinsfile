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