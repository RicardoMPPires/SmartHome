pipeline {
    agent any

    stages {
        stage('Clone repository') {
            steps {
                git 'https://github.com/username/repository.git'
            }
        }

        stage('Build frontend') {
            steps {
                dir('frontend/smarthome') {
                    bat 'npm install'
                    bat 'npm run build'
                }
            }
        }

        stage('Copy frontend build') {
            steps {
                bat 'pscp -r frontend/smarthome/build root@10.9.24.206:/var/www/smarthome'
            }
        }

        stage('Build backend') {
            steps {
                bat 'mvn clean package'
            }
        }

        stage('Copy backend build') {
            steps {
                bat 'pscp target/myapp.war root@10.9.24.206:/opt/tomcat/smarthome'
            }
        }
    }
}