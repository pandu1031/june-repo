// Declarative pipeline
pipeline{
    agent any
    parameters {
         string( name: 'BRANCH_NAME', defaultValue: 'master', description: 'pass here deployment branch name')
         //string( name: 'BUILD_NUM', defaultValue: '', description: 'here im passing deployment build number')
         string( name: 'SERVER_IP', defaultValue: '', description: 'here im passing server ip')
  }
    stages{
        stage("clone code"){
            steps{
            println "here im cloning the code"
            git branch: "$BRANCH",
                url:"https://github.com/pandu1031/boxfuse-sample-java-war-hello.git"
            }
        }
        
         stage("build"){
            steps{
            println "here im building code "
            sh "mvn clean package"
        }
    }
        stage("upload artifacts"){
           steps{
           println "here im uploading artifacts to s3 bucket"
           sh"aws s3 cp target/hello-${BUILD_NUM}.war s3://mamuuu/${BRANCH_NAME}/${BUILD_NUM}"
    }
    }

    stage("Deploy"){
        steps{
            println "here im deploying from jenkins to tomcat location"
            sh """aws s3 ls
               scp -i /tmp/mamu1031.pem/tmp/tomcatinstall.sh ec2-user@${SERVER_IP}:/tmp/
               ssh -i /tmp/mamu1031.pem ec2-user@${SERVER_IP} "bash /tmp/tomcatinstall.sh && systemctl status tomcat"
            //scp -i /tmp/mamu1031.pem /target/hello-${BUILD_NUM}.war ec2-user@${SERVER_IP}:/var/lib/tomcat/webapps
             """
        }
        }
    }
    }
