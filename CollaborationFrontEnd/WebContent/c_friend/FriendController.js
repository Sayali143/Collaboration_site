'use strict';
 
app.controller('FriendController', ['UserService','$scope', 'FriendService','$location',
   '$rootScope',function(UserService,$scope, FriendService,$location,$routeParams,$rootScope) {
	console.log("FriendController...")
          var self = this;
		  $scope.friends;
          self.friend={id:'',username:'',friendid:'',status:'',isonline:'',errorCode : '', errorMessage : ''};
          $scope.friends=[];
          $scope.users = [];
          $scope.getMyFriendRequests=[];
          $scope.getRequestsSendByMe=[];
  			
          self.getMyFriendRequests = FriendService.getMyFriendRequests()
  		                      .then(
  		                    		function(response)
  		                    		{
	  		              				console.log("Excecuting Pending request")
	  		              				console.log(response.status)
	  		              				self.getMyFriendRequests = response.data;
  		              			   },
  		              			function(errResponse)
  		              			{
  		              				console.log("Error getting frienf list")
  		              				console.log(errResponse.data)
  		              })
  		 
         self.sendFriendRequest=function sendFriendRequest(username)
         {
       	  console.log("sendFriendRequest :"+username)
             FriendService.sendFriendRequest(username)
                 .then(
                              function(d)
                              {
                            	   self.friend = d;
                                   self.fetchAllUsers
                                  alert("Friend Request : " + self.friend.errorMessage)
                              },
                               function(errResponse)
                               {
                                   console.error('Error while sending friend request');
                               }
                      );
         };
             
          self.getMyFriends = function(){
        	  console.log("Getting my friends")
              FriendService.getMyFriends()
                  .then(
                               function(d) {
                                    self.friends = d;
                                    console.log("Got the friends list"+ self.friends)
                                     	/* $location.path('/view_friend');*/
                               },
                                function(errResponse){
                                    console.error('Error while fetching Friends');
                                }
                       );
          };
          
          
          self.getRequestsSendByMe = FriendService.getRequestsSendByMe()
                  .then(
                		  function(response)
      					{
      						console.log("Entering Get Sent Request")
      						console.log(response.status)
      						$scope.getRequestsSendByMe = response.data;
      					}, function(errResponse)
      					{
      						console.log("Error getting sent friend list")
      						console.log(errResponse.data)
      					}
      					
                       )
       
         self.updateFriendRequest = function(friend, username){
              FriendService.updateFriendRequest(friend, username)
                      .then(
                              self.fetchAllFriends, 
                              function(errResponse){
                                   console.error('Error while updating Friend.');
                              } 
                  );
          };
 
         self.acceptFriend = function(friendid){
              FriendService.acceptFriend(friendid)
              .then(
						function(d) 
						{
							self.friend = d;
							self.fetchAllUsers
							alert(self.friend.errorMessage)
						},
						
						function(errResponse)
						{
							console.error('Error while accepting Friend.');
							alert('error while accepting friend')
						});
          };
          
          self.rejectFriend = function(friendid){
              FriendService.rejectFriend(friendid)
              .then(
						function(d) {
							self.friend = d;
							self.fetchAllUsers
							alert(self.friend.errorMessage)
							
						},
						
						function(errResponse) {
							console
									.error('Error while rejecting Friend.');
						});
          };
          
          self.unFriend = function(username){
              FriendService.unFriend(username)
              .then(
						function(d) {
							self.friend = d;
							self.fetchAllUsers
							alert(self.friend.errorMessage)
							
						},
						
						function(errResponse) {
							console
									.error('Error while unFriending Friend.');
						});
          };
          
          self.fetchAllUsers = function() {
				console.log("fetchAllUsers...")
				UserService
						.fetchAllUsers()
						.then(
								function(d) {
									self.users = d;
									console.log("Users Length..." +self.users.length)
								},
								function(errResponse) {
									console
											.error('Error while fetching Users');
								});
          };
          
          self.fetchAllFriends = function() {
				console.log("fetchAllFriends...")
				FriendService.fetchAllFriends()
						.then(
								function(d) {
									console.log("function Friends...")
									self.friends = d;
									
								},
								function(errResponse) {
									console
											.error('Error while fetching Friends');
								});
          };
			
          self.fetchAllFriends();
          self.fetchAllUsers();
          self.getMyFriends();
      }]);