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
                sh 'gradle build -x test'
            }
        }
        stage('Test') {
            agent {
                docker {
                    image 'gradle:jdk15'
                }
            }
            steps {
                sh 'apt-get install docker -y'
                sh 'service docker start'
                sh 'usermod -aG docker jenkins-user'
                sh 'curl -L "https://github.com/docker/compose/releases/download/1.28.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose'
                sh 'chmod +x /usr/local/bin/docker-compose'
                sh 'ln -s /usr/local/bin/docker-compose /usr/bin/docker-compose'
                sh 'docker-compose up -d'
                sh 'gradle test'
            }
        }
        stage('Deploy') {
            steps {
                script {
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
