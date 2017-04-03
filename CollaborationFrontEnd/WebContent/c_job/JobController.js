app.controller('JobController', function($scope, $location, JobService, $rootScope, $cookieStore, $http)
{
		console.log("Entering Job Controller")
		var self = this;
		$scope.jobs;
		$scope.jobs={job_id:'', company:'', date_added:'', description:'', location:'', position:'', q_10:'', q_12:'', q_ug:'', salary:'', status:'', title:'', username:'', vacancy:'', errorMessage:'', errorCode:''};
		self.applyjobs = {id : '',job_id : '',company : '',username : '',date_time : '',location : '',position : '',status : '',title : ''};
		$scope.message;
		$scope.message1;
		$scope.jobs = [];
		$scope.applyjobs=[];
		
		self.addJob = function()
		{
			console.log("submit a new job");
			JobService.addJob($scope.job)
			.then( function(d)
			{
				alert("You successfully posted the job")
				$location.path("/home")
			}, 
			function (errResponse)
			{	
				console.error("Error while posting the job");
			});
		};				
			self.getJobById = function getJobById(job_id)
			{
				console.log("get Job details of the id",job_id);
				JobService.getJobById(job_id)
				    .then(
				    		function(d){
				    			self.job =d;
				    			$location.path("/jobList")
				    		},
				    		function(errResponse){
				    			console.error("Error while fetching the job Details");
				    			
				    		});
			};
		
		self.applyJob = function applyJob(job_id)
		{
			console.log("apply for job");
			var currentUser = $rootScope.currentUser
			console.log("currentUser.username:" +currentUser.username)
			if(typeof currentUser.username == 'undefined')
				{
					alert("please login to apply for the job")
					console.log("User is not logged in can not apply for the job")
					return
				}
					console.log("username:" +currentUser.username+ "applying for the job:" +job_id)
				    JobService.applyJob(job_id)
				           .then(
				        		   function(d)
				        		   {
				        			   self.job =d;
				        			   alert("You have successfully applied for the job")
				        			   
				        		   },
				        		   function(errResponse){
				        			   console.error("Error while applying for Job request")
				        		   });
		};
		
		self.deleteJob=function(job_id)
		{
			console.log("Entering delete Event")
			JobService.deleteJob(job_id)
			.then
			(
					self.listJob,
				function(response)
				{
					console.log("delete Successful "+response.status)
					$location.path("/AdminJobList")
				}
			);
		};
		
		getMyAppliedJobs = function()
		{
			console.log("calling the method getMyAppliedJobs");
			JobService.getMyAppliedJobs()
			       .then(
			    		   
			    	   function(response)
						{
							console.log("Applied Jobs Recieved")
							$scope.myjobs = response.data;
						}
			    	)
			
		}
	
		getMyAppliedJobs();
	
		listJob=function()
		{
			console.log("Entering List Job Method")
			JobService.listJob()
			.then
			(
				function(response)
				{
					console.log("List of Jobs Successful"+response.status)
					$scope.jobs=response
				}
			)
		}
		listJob();
})