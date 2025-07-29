pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Build') {
            steps {
                sh '/opt/homebrew/bin/mvn clean install'
            }
        }
        // Optional: Add test, deploy stages here
    }
}
