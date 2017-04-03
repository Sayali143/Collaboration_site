app.factory('ForumService', function($http, $q, $rootScope)
{
	console.log("Entering ForumService")
	var BASE_URL = "http://localhost:8081/CollaborationBackEnd/"
		return{
	
	registerForum: function(forum)
	{
		console.log("Entering Function Register Forum")
		return $http.post(BASE_URL + "addForum", forum)
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
	  
	  approveForum: function(forum_id) {
	     	console.log("calling approve " +forum_id)
	             return $http.post(BASE_URL+"approveForum-"+forum_id)
	                     .then(
	                             function(response)
	                             {
	                                 return response.data;
	                             }, 
	                             function(errResponse)
	                             {
	                                 console.error('Error while accept registration');
	                                
	                             }
	                     );
		 },
		 
		 rejectForum: function(forum_id) {
	     	console.log("calling reject ")
	             return $http.post(BASE_URL+"rejectForum-"+forum_id)
	                     .then(
	                             function(response)
	                             {
	                                 return response.data;
	                             }, 
	                             function(errResponse)
	                             {
	                                 console.error('Error while reject registration');
	                                 return $q.reject(errResponse);
	                             }
	                     );
		 },
		 
		 deleteForum: function(forum_id)
			{
				console.log("Entering Function delete forum")
				return $http.post(BASE_URL +"deleteForum-"+forum_id)
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
		
		 getAllForums: function()
			{
				console.log("Entering Function List Forum")
				return $http.get(BASE_URL + "getAllForums")
				.then(function(response)
					{
						console.log(response.data)
						console.log(response.status)
						return response.data
					});
			},
		
		listForum: function()
		{
			console.log("Entering Function List Forum")
			return $http.get(BASE_URL + "getAllApprovedForums")
			.then(function(response)
				{
					console.log(response.data)
					console.log(response.status)
					return response.data
				});
		},
		
		getForum: function(forum_id)
		{
			console.log("Entering Get Forum "+forum_id)
			console.log(BASE_URL + "getForum-" +forum_id)
			return $http.get(BASE_URL + "getForum-" +forum_id)
			.then(function(response)
			{
				console.log("Forum Recieved")
				if(response.data != null)
					$rootScope.gforum=response.data
				return response
			})
		},
		
		getForumComment: function(forum_id)
		{
			console.log("Getting Forum Comments "+BASE_URL + "getForumCommentByID-"+forum_id)
			return $http.get(BASE_URL + "getForumCommentByID-"+forum_id)
			.then(
				function(response)
				{
					console.log(response)
					$rootScope.forumcomments=response.data;
					return response;
				});
		},
		
		addForumComment: function(forum_id, forumcomment)
		{
			console.log("Adding Forum Reply")
			return $http.post(BASE_URL + "addForumComment",forumcomment)
			.then(
				function(response)
				{
					console.log(response)
					console.log("Comments Added")
					return response;
				}
			)
		}
	}
})