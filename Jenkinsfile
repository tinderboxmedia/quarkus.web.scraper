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
                // Install Maven
                sh 'mkdir /opt/maven'
                sh "curl -fL ${MAVEN_DOWNLOAD_URL} --silent | tar zx -C /opt/maven --strip-components=1"
                sh '/opt/maven/bin/mvn -ntp clean install'

                // Get Driver Bundle
                sh 'mkdir /driver-bundle'
                sh "unzip -o ${WORKSPACE}/target/quarkus-app/lib/main/com.microsoft.playwright.driver-bundle-1.46.0.jar -d driver-bundle"

                // Create Native
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