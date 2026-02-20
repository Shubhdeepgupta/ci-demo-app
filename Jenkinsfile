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