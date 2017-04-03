'use strict';
 
app.factory('FriendService', ['$http', '$q','$rootScope', function($http, $q,$rootScope){
	
	console.log("FriendService...")
	
	var BASE_URL="http://localhost:8081/CollaborationBackEnd/"
    return {
		
		fetchAllFriends:function()
		{
			console.log("calling fetchAllFriends ")
            return $http.get(BASE_URL+"myFriends")
                    .then
                    (
                            function(response)
                            {
                            	console.log("In FriendService function ")
                                return response.data;
                            }, 
                           null
                    );
		},
		getMyFriends: function() {
                    return $http.get(BASE_URL+"myFriends")
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                   null
                            );
            },
             
            sendFriendRequest: function(username){
                    return $http.get(BASE_URL+"addFriend-"+username)
                            .then(
                                    function(response){
                                    	if(response.data.errorCode==404)
                                    	{
                                    		alert(response.data.errorMessage)
                                    	}
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while creating friend');
                                        return $q.reject(errResponse);
                                    }
                            );
            },
            
            getMyFriendRequests: function(){
                return $http.get(BASE_URL+"getMyFriendRequests")
                        .then(
                                function(response){
                                    return response.data;
                                }, 
                                function(errResponse){
                                    console.error('Error while creating friend');
                                    return $q.reject(errResponse);
                                }
                        );
        },
        
        getRequestsSendByMe: function(){
            return $http.get(BASE_URL+"getRequestsSendByMe")
                    .then(
                            function(response){
                                return response.data;
                            }, 
                            function(errResponse){
                                console.error('Error while creating friend');
                                return $q.reject(errResponse);
                            }
                    );
        },
        
        acceptFriend: function(friendid){
        	console.log("Starting of the method acceptFriendRequest")
        	console.log("calling acceptFriend "+friendid)
            return $http.post(BASE_URL+"acceptFriend-"+friendid)
                    .then(
                            function(response)
                            {
                                return response.data;
                            }, 
                            function(errResponse){
                                console.error('Error while creating acceptFriendRequest');
                                return $q.reject(errResponse);
                            }
                    );
    },
         
    rejectFriend: function(friendid){
    	console.log("Starting of the method rejectFriendRequest")
        return $http.post(BASE_URL+"rejectFriend-"+friendid)
                .then(
                        function(response){
                            return response.data;
                        }, 
                        function(errResponse){
                            console.error('Error while rejectFriendRequest');
                            return $q.reject(errResponse);
                        }
                );
},
     
		unFriend: function(friendid){
			console.log("Starting of the method unFriend")
		    return $http.post(BASE_URL+"unFriend-"+friendid)
		            .then(
		                    function(response){
		                        return response.data;
		                    }, 
		                    function(errResponse){
		                        console.error('Error while unFriend ');
		                        return $q.reject(errResponse);
		                    }
		            );
		},
 
             
        //Not required because we are not deleting the record
            deleteFriend: function(id){
                    return $http.delete(BASE_URL+"friend-"+id)
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while deleting friend');
                                        return $q.reject(errResponse);
                                    }
                            );
            }      
         
    };
 
}]);