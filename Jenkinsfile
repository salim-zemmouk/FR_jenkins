pipeline {
    agent any

    stages {
        stage('Check if npm is installed') {
            steps {
                command -v npm > /dev/null 2>&1 || echo "npm non installé"
            }
        }

        stage('Check npm version') {
            steps {
                sh 'npm -v'
            }
        }
    }
}