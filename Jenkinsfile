pipeline {
    agent any
    environment {
        PROJECT_NAME = readMavenPom().getArtifactId()
        PROJECT_VERSION = readMavenPom().getVersion()
        
        MAVEN_DOWNLOAD_URL = "https://dlcdn.apache.org/maven/maven-3/3.9.8/binaries/apache-maven-3.9.8-bin.tar.gz"
        
        NEXUS_URL = 'http://nexus:9000/repository/docker-images/'
        NEXUS_CRED = 'nexus-credentials'
        
        STACK_WEBHOOK = 'portainer-stack-webhook'
        
        IMAGE_USERNAME = 'aardwolf'
    }
    stages {

        stage('Package') {
            agent {
                docker {
                    image 'quay.io/quarkus/ubi-quarkus-graalvmce-builder-image:jdk-21'
                    args '-u root --entrypoint='
                }
            }
            steps {
                sh 'mkdir /opt/maven'
                sh "curl -fL ${MAVEN_DOWNLOAD_URL} --silent | tar zx -C /opt/maven --strip-components=1"
                sh '/opt/maven/bin/mvn -B -ntp clean install -Dnative'
            }
        }
        
        stage('Push') {
            agent any
            steps {
                script {
                    docker.withRegistry("${NEXUS_URL}", "${NEXUS_CRED}") {
                        def image = docker.build("${IMAGE_USERNAME}/${PROJECT_NAME}")
                        image.push("${PROJECT_VERSION}")
                        image.push('latest')
                    }
                }
            }
        }

        stage('Update Stack') {
            steps {
                withCredentials([string(credentialsId: "${STACK_WEBHOOK}", variable: 'webhook')]) {
                    sh "curl -X POST ${webhook} --http1.1"
                }
            }
        }
    }
    post { 
        always { 
            sh 'docker system prune -af'
            cleanWs()
        }
    }
    options {
        skipStagesAfterUnstable()
        buildDiscarder(
            logRotator(
                artifactNumToKeepStr: '10',
                numToKeepStr: '10'
            )
        )
    }
}