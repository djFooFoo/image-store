pipeline {
    agent any
    stages {
        stage('Install') {
            agent {
                docker {
                    image 'gradle:jdk15'
                }
            }
            steps {
                sh 'gradle clean build -x test'
            }
        }
        stage('Test') {
            agent {
                docker {
                    image 'gradle:jdk15'
                }
            }
            steps {
                sh 'gradle test'
            }
        }
        stage('Deploy') {
            steps {
                script {
                        sh './gradlew clean build -x test'
                        // configure registry
                        docker.withRegistry('https://082272919318.dkr.ecr.eu-west-3.amazonaws.com', 'ecr:eu-west-3:aws.dieter.jordens') {
                        def myImage = docker.build('dj-website-image-store')
                        myImage.push('latest')
                    }
                }
            }
        }
    }
}
