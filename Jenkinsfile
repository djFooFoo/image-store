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
                sh 'sudo apt-get install docker -y'
                sh 'sudo service docker start'
                sh 'sudo usermod -aG docker jenkins-user'
                sh 'sudo curl -L "https://github.com/docker/compose/releases/download/1.28.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose'
                sh 'sudo chmod +x /usr/local/bin/docker-compose'
                sh 'sudo ln -s /usr/local/bin/docker-compose /usr/bin/docker-compose'
                sh 'sudo docker-compose up -d'
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
