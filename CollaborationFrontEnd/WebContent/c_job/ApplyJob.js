app.controller(	'ApplyJobs',[	'$scope','JobService','$location','$rootScope',
		function($scope, JobService, $location, $rootScope)
		{
			console.log("ApplyJobs controller...")
			var self = this;
			self.applyjobs = {id : '',job_id : '',company : '',username : '',date_time : '',location : '',position : '',status : '',title : ''};
			
			self.applyjobs=[];
			
			self.getMyAppliedJobs = function()
			{
				console.log("calling the method getMyAppliedJobs");
				JobService.getMyAppliedJobs()
				       .then(function (d)
				      {
				    	   self.applyjobs=d;
				    	  console.log(d) 
				       }, 
				       function(errResponse){
				    	   console.error("Error while fetching the jobs");
				       });
			};
			
			self.getMyAppliedJobs();
} ]);