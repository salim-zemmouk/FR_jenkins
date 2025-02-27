pipeline {
    agent {
        docker { image 'node:16' } // Utilise une image Docker Node.js officielle
    }

    stages {
        stage('Check Node.js') {
            steps {
                sh 'node -v'
            }
        }
    }
}