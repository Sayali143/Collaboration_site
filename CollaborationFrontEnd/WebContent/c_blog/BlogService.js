app.factory('BlogService', function($http, $q, $rootScope)
{
	console.log("Entering BlogService")
	var BASE_URL = "http://localhost:8081/CollaborationBackEnd/"
		return{
	
	registerBlog: function(blog)
	{
		console.log("Entering Function Register Blog")
		return $http.post(BASE_URL + "addBlog", blog)
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
	
	getLike: function(blog_id)
	  {
		console.log("Entering Function like Blog")
	       return $http.post(BASE_URL+"getLike-"+blog_id)
	       .then(
	    		function (response) {
	    			console.log(response.status)
		             return response.data;
		        },
		        function(errResponse){
		                console.error('Error while like Blogs');
		                return $q.reject(errResponse);
		            }
		        );
	  },
	  
	  approveBlog: function(blog_id) {
	     	console.log("calling approve " +blog_id)
	             return $http.post(BASE_URL+"approveBlog-"+blog_id)
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
		 
		 rejectBlog: function(blog_id) {
	     	console.log("calling reject ")
	             return $http.post(BASE_URL+"rejectBlog-"+blog_id)
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
		 
		 deleteBlog: function(blog_id)
			{
				console.log("Entering Function delete blog")
				return $http.post(BASE_URL +"deleteBlog-"+blog_id)
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
		
		 getAllBlogs: function()
			{
				console.log("Entering Function List Blog")
				return $http.get(BASE_URL + "getAllBlogs")
				.then(function(response)
					{
						console.log(response.data)
						console.log(response.status)
						return response.data
					});
			},
		
		listBlog: function()
		{
			console.log("Entering Function List Blog")
			return $http.get(BASE_URL + "getAllApprovedBlogs")
			.then(function(response)
				{
					console.log(response.data)
					console.log(response.status)
					return response.data
				});
		}
	}
})