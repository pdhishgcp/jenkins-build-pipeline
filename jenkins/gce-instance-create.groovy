pipeline {
    agent any

    environment {
    SVC_ACCOUNT_KEY = credentials('terraform-auth')
  }
     
    stages {
      	stage('Set creds') {
            steps {
              
                sh 'echo $SVC_ACCOUNT_KEY | base64 -d > ./jenkins/jenkins.json'
		            sh 'pwd' 
               
            }
        }

	
	stage('Auth-Project') {
	 steps {
		 dir('jenkins')
		 {
    
        sh 'gcloud auth activate-service-account jenkins@mi-dev-lab.iam.gserviceaccount.com --key-file=jenkins.json'
    }
    }
	}
 	 
	stage('Create Instance') {
	 steps {
    
    sh 'gcloud compute instances $ACTION $VM --zone=$ZONES'
        
    }
    }
	    
	    
	stage('List Instance') {
	 steps {
    
    sh 'gcloud compute instances list'
        
    }
    }
     
   }
}
