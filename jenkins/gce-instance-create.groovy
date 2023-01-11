pipeline {
    agent any

    environment {
    SVC_ACCOUNT_KEY = credentials('jenkins-auth')
  }
     
    stages {
      	stage('Set creds') {
            steps {
              
                sh 'echo $SVC_ACCOUNT_KEY | base64 -d > ./jenkins/dhish.json'
		            sh 'pwd' 
               
            }
        }

	
	stage('Auth-Project') {
	 steps {
		 dir('jenkins')
		 {
    
        sh 'gcloud auth activate-service-account dpterraform@dp2023.iam.gserviceaccount.com --key-file=dhish.json'

    }
    }
	}
 	 
	stage('Create Instance') {
	 steps {
    
    sh 'gcloud compute instances create hello-1 --zone=us-central1-a'
        
    }
    }
	    
	    
	stage('List Instance') {
	 steps {
    
    sh 'gcloud compute instances list'
        
    }
    }
     
   }
}
