app.controller('ForumController', function($scope, $location, ForumService, $rootScope, $cookieStore, $http)
{
		console.log("Entering Forum Controller")
		var self = this;
		$scope.forums;
		$scope.forum={forum_id:'', forum_title:'', date_time:'', description:'', rejected:'', status:'', username:'', errorMessage:'', errorCode:''};
		$scope.forumcomment= {id:'', forum_id:'', forum_comment:'', postdate:'', username:''};
		$scope.forumcomments = [];
		$scope.message;
		$scope.message1;
		$scope.registerForum=function()
		{
			console.log("Entering Register Forum")
			ForumService.registerForum($scope.forum)
			.then
			(
				function(response)
				{
					console.log("Registeration Successful "+response.status)
					$location.path("/home")
				}
			);
		};
		 
		 self.approveForum = function(forum_id) 
		 {
				console.log("accept..." +forum_id)
				ForumService.approveForum(forum_id)
						.then(
								function(d) {
									self.forum = d;
									self.getAllForums
									$location.path="/AdminForumList";
									
								},
								
								function(errResponse) {
									console.error('Error while updating forum.');
								});
			};
			
			self.rejectForum = function(forum_id) {
				console.log("reject..."+forum_id)
				ForumService.rejectForum(forum_id)
						.then(
								function(d) {
									self.forum = d;
									self.getAllForums
									$location.path="/";
									
								},
								null );
			};
	
			self.deleteForum=function(forum_id)
			{
				console.log("Entering delete Event")
				ForumService.deleteForum(forum_id)
				.then
				(
						self.listForum,
					function(response)
					{
						console.log("delete Successful "+response.status)
						$location.path="/home";
						alert("successfully deleted");
					}
				);
			};
			
			getAllForums=function()
			{
				console.log("Entering List Forum Method")
				ForumService.getAllForums()
				.then
				(
					function(response)
					{
						console.log("List of Forums Successful"+response.status)
						$scope.forums=response
					}
				)
			};
			
			self.getForum = function(forum_id)
			{
				console.log("Entering Get Forum "+forum_id)
				ForumService.getForum(forum_id)
				.then
				(
					function(response)
					{
						console.log("Forum Recieved")
						$location.path("/forumList")
					}
				)
				ForumService.getForumComment(forum_id)
				.then
				(
					function(response)
					{
						console.log("Get Comments for "+forum_id)
						console.log(response.data)
						$scope.forumcomments = response.data;
					}
				)
			}
			
			self.addcomment = addcomment
			function addcomment(forum_id)
			{
				console.log(forum_id)
				$scope.forumcomment.forum_id = forum_id;
				ForumService.addForumComment(forum_id, $scope.forumcomment)
				.then
				(
					function(response)
					{
						console.log("Forum COmment added "+response.status)
					}
				)
				ForumService.getForum(forum_id)
				.then
				(
					function(response)
					{
						console.log("Forum Recieved")
					}
				)
				ForumService.getForumComment(forum_id)
				.then
				(
					function(response)
					{
						console.log("Get Comments for "+forum_id)
						console.log(response.data)
					}
				)
				$location.path("/home")
			};
			
			listForum=function()
			{
				console.log("Entering List Forum Method")
				ForumService.listForum()
				.then
				(
					function(response)
					{
						console.log("List of Forums Successful"+response.status)
						$scope.forums=response
					}
				)
			}
		listForum();
		getAllForums();
})