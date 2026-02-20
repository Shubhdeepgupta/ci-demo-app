pipeline {
    agent any

    tools {
        maven 'maven-3'
        jdk 'jdk17'
    }

    options {
        timestamps()
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
            echo 'Build completed successfully !!!'
        }
        failure {
            echo 'Build failed!'
        }
        always {
            echo 'Cleaning workspace'
            cleanWs()
        }
    }
}
