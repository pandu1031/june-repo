//Declarative pipeline
pipeline{
    agent any 
    parameters {
        string(name: 'BRANCH_NAME', defaultValue: 'source', description: 'Enter the source code branch')
        string(name: 'BRANCH_PIPE', defaultValue: '', description: 'Enter the pipeline branch')
        string(name: 'SERVER_IP', defaultValue: '', description: 'Enter the server ip of Tomcat')
        }
    stages{
        stage("clone code"){
            steps{
                println "here im clonning the code from git hub"
                git branch: '${BRANCH_NAME}', 
                url: "https://github.com/pandu1031/boxfuse-sample-java-war-hello.git"
            }
        }
        stage("Build"){
            steps{
                println "here im building the code"
                sh "mvn clean package"
                sh "ls -l target"
            }
        }
        stage("uploding artifacts"){
            steps{
                println "here im uploading artifacts to s3 bucket"
                sh "aws s3 ls"
                sh "aws s3 cp target/hello-${BUILD_NUMBER}.war s3://mamuu/June/${BRANCH_NAME}/${BUILD_NUMBER}"
            }
        }
        stage("deploy"){
            steps{
                println "here im deploying the war file to tomcat server"
                //sh "scp -i /tmp/mamu1031.pem /tmp/tomcatinstall.sh ec2-user@${SERVER_IP}:/tmp/"
                //sh "ssh -i /tmp/mamu1031.pem ec2-user@${SERVER_IP} \"bash /tmp/tomcatinstall.sh systemctl status tomcat\""
                sh "scp -i /tmp/mamu1031.pem target/hello-${BUILD_NUMBER}.war ec2-user@${SERVER_IP}:/var/lib/tomcat/webapps/"
            }
        }
    }
}