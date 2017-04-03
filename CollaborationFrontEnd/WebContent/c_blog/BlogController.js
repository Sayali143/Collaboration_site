app.controller('BlogController', function($scope, $location, BlogService, $rootScope, $cookieStore, $http)
{
		console.log("Entering Blog Controller")
		var self = this;
		$scope.blogs;
		$scope.blog={blog_id:'', blog_title:'', date_time:'', description:'', rejected:'', status:'', username:'', likes:'', errorMessage:'', errorCode:''};
		self.blogs=[];
		$scope.message;
		$scope.message1;
		$scope.registerBlog=function()
		{
			console.log("Entering Register Blog")
			BlogService.registerBlog($scope.blog)
			.then
			(
				function(response)
				{
					console.log("Registeration Successful "+response.status)
					$location.path("/home")
				}
			);
		};
		
		self.getLike = function(blog_id)
		   {
			console.log("in like controller blog");
			   BlogService.getLike(blog_id)
		       	.then(
		       				self.listBlog,
		       				function(errResponse)
		           			{
		       					console.log("in like controller blog");
		           				console.error('Error while like blog');
		           			}
		           		);
		    };
		    
	    self.likeclick = function(blog_id)
	    {
		 	   self.getLike(blog_id);
		 };
		 
		 self.approveBlog = function(blog_id) 
		 {
				console.log("accept..." +blog_id)
				BlogService.approveBlog(blog_id)
						.then(
								function(d) {
									self.blog = d;
									self.getAllBlogs
									$location.path="/AdminBlogList";
									
								},
								
								function(errResponse) {
									console.error('Error while updating blog.');
								});
			};
			
			self.rejectBlog = function(blog_id) {
				console.log("reject..."+blog_id)
				BlogService.rejectBlog(blog_id)
						.then(
								function(d) {
									self.blog = d;
									self.getAllBlogs
									$location.path="/";
									
								},
								null );
			};
	
			self.deleteBlog=function(blog_id)
			{
				console.log("Entering delete Event")
				BlogService.deleteBlog(blog_id)
				.then
				(
						self.listBlog,
					function(response)
					{
						console.log("delete Successful "+response.status)
						$location.path="/home";
						alert("successfully deleted");
					}
				);
			};
			
			getAllBlogs=function()
			{
				console.log("Entering List Blog Method")
				BlogService.getAllBlogs()
				.then
				(
					function(response)
					{
						console.log("List of Blogs Successful"+response.status)
						$scope.blogs=response
					}
				)
			};
			
			listBlog=function()
			{
				console.log("Entering List Blog Method")
				BlogService.listBlog()
				.then
				(
					function(response)
					{
						console.log("List of Blogs Successful"+response.status)
						$scope.blogs=response
					}
				)
			}
		listBlog();
		getAllBlogs();
})