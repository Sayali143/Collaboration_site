app.factory('JobService', function($http, $q, $rootScope)
{
	console.log("Entering JobService")
	var BASE_URL = "http://localhost:8081/CollaborationBackEnd/"
		return{
	
	addJob: function(job)
	{
		console.log("Entering Function Register Job")
		return $http.post(BASE_URL +"addJob",job)
			.then(
					function(response)
			{
				console.log(response.data);
				return response.data;
			},
			function(errResponse)
			{
				console.error('Error while adding job');
				console.log(errResponse.status)
				return $q.reject(errResponse);
			});
	},
	
	applyJob: function(job_id) {
    	console.log("applyForJob.... ")
            return $http.post(BASE_URL+"applyJob-"+job_id)
                    .then(
                            function(response)
                            {
                                return response.data;
                            }, 
                           function(errResponse)
                           {
                            	
                            	return $q.reject(errResponse);
                            }
                    );
	},
	
	deleteJob: function(job_id)
	{
		console.log("Entering Function delete Event")
		return $http.post(BASE_URL +"deleteJob-"+job_id)
		.then(function(response)
			{
				console.log(response.status)
				return response.status
			},function(errResponse)
			{
				console.log(errResponse.status)
				return errResponse.status
			});
	},
	
	getJobById: function(job_id) {
    	console.log("calling job details of " +job_id)
            return $http.get(BASE_URL+"getJobById-"+job_id)
                    .then(
                            function(response){
                            	$rootScope.selectedJob = response.data
                                return response.data;
                            }, 
                            function(errResponse){
                            	console.error('Error while applying for the job');
                            	return $q.reject(errResponse);
                            });
	},
	
	getMyAppliedJobs: function() {
    	console.log("calling getMyAppliedJobs ")
            return $http.get(BASE_URL+"getMyAppliedJobs")
                    .then(
                            function(response){
                                return response.data;
                            }, 
                            function(errResponse){
                            	console.error('Error while applying for the job');
                            	return $q.reject(errResponse);
                            });
	},
		
	listJob: function()
	{
		console.log("Entering Function List Job")
		return $http.get(BASE_URL + "listJobs")
		.then(function(response)
			{
				console.log(response.data)
				console.log(response.status)
				return response.data
			})
	}
	}
})