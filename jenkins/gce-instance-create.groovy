pipeline {
    agent any

    environment {
    SVC_ACCOUNT_KEY = credentials('jenkins-auth')
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
    
        sh 'gcloud auth activate-service-account dpterraform@dp2023.iam.gserviceaccount.com --key-file=jenkins.json'
       sh ' gcloud projects add-iam-policy-binding dpterraform --member="serviceAccount:tdpterraform@dp2023.iam.gserviceaccount.com" --role=roles/storage.objectViewer'
       sh ' gcloud projects add-iam-policy-binding dpterraform --member="serviceAccount:tdpterraform@dp2023.iam.gserviceaccount.com" --role=roles/compute.instanceAdmin.v1'

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
