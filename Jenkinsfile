
pipeline {
    agent any

    options {
        timestamps()
        disableConcurrentBuilds()
    }

    environment {
        IMAGE_NAME = 'orbit-banking'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Verify Java and Maven') {
            steps {
                sh 'java -version'
                sh './mvnw -version'
            }
        }

        stage('Build and Test') {
            steps {
                sh './mvnw -B clean verify'
            }
            post {
                always {
                    junit(
                        testResults: 'target/surefire-reports/*.xml',
                        allowEmptyResults: true
                    )
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                sh '''
                    docker build \
                      -t ${IMAGE_NAME}:${BUILD_NUMBER} \
                      .
                '''
            }
        }

        stage('Verify Docker Image') {
            steps {
                sh 'docker image inspect ${IMAGE_NAME}:${BUILD_NUMBER}'
            }
        }
    }

    post {
        success {
            echo 'CI pipeline completed successfully!'
        }
        failure {
            echo 'CI pipeline failed. Check the stage logs.'
        }
        always {
            archiveArtifacts(
                artifacts: 'target/*.jar',
                allowEmptyArchive: true
            )
        }
    }
}
