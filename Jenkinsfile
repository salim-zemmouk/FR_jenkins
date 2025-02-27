pipeline {
    agent any

    stages {
        stage('Check if npm is installed') {
            steps {
                sh 'which npm || echo "npm non installé"'
            }
        }

        stage('Check npm version') {
            steps {
                sh 'npm -v'
            }
        }
    }
}