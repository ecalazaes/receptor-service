pipeline {
    agent any
    stages {
        stage('Verificar Repositorio') {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: '*/main']],
                          userRemoteConfigs: [
                                [
                                    url: 'https://github.com/ecalazaes/receptor-service.git',
                                    credentialsId: 'emissor-service']]])
            }
        }

      stage('Construir Imagem Docker') {
          steps {
              script {
                  def appName = 'receptor-service'
                  def imageTag = "${appName}:${env.BUILD_ID}"
                  bat "docker build -t ${imageTag} ."
              }
          }
      }


        stage('Fazer Deploy') {
            steps {
                script {
                    def appName = 'receptor-service'
                    def imageTag = "${appName}:${env.BUILD_ID}"
                    bat "docker-compose up -d --build"
                }
            }
        }
    }

    post {
        success {
            echo 'Deploy realizado com sucesso!'
        }
        failure {
            echo 'Houve um erro durante o deploy.'
        }
    }
}