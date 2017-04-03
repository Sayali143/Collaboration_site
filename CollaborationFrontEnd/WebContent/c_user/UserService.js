app.factory('UserService', function($http, $q, $rootScope)
{
	console.log("Entering UserService")
	var BASE_URL = "http://localhost:8081/CollaborationBackEnd/"
		return{
	//var userService = this;
	
	registerUser: function(user)
	{
		console.log("Entering Function Register User")
		return $http.post(BASE_URL + "addUser", user)
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
	
	updateUser: function(user)
	{
		console.log("calling Update ")
        return $http.put(BASE_URL+"updateUser",user )
                .then(
                        function(response){
                            return response.data;
                        }, 
                        function(errResponse){
                            console.error('Error while updating user');
                            return $q.reject(errResponse);
                        }
                );
	},
	
	myProfile: function() {
    	console.log("calling myprofile ")
            return $http.get(BASE_URL+"myProfile")
                    .then(
                            function(response){
                                return response.data;
                            }, 
                           null
                    );
	},
	
	 accept: function(username) {
     	console.log("calling approve " +username)
             return $http.get(BASE_URL+"accept-"+username)
                     .then(
                             function(response){
                                 return response.data;
                             }, 
                             function(errResponse){
                                 console.error('Error while accept registration');
                                
                             }
                     );
	 },
	 
	 reject: function(username) {
     	console.log("calling reject ")
             return $http.get(BASE_URL+"reject-"+username)
                     .then(
                             function(response){
                                 return response.data;
                             }, 
                             function(errResponse){
                                 console.error('Error while reject registration');
                                 return $q.reject(errResponse);
                             }
                     );
	 },
	
	 authenticate: function(user){
  	   console.log("Calling the method authenticate with the user :"+user)
		 
      return $http.post(BASE_URL+"Validate",user)
              .then(
                      function(response){
                          return response.data;
                      }, 
                     null
              );
	 },
	
	 logout: function(){
     	console.log('logout....')
         return $http.get(BASE_URL+"logout")
                 .then(
                         function(response){
                             return response.data;
                             console.log(response.data)
             				console.log(response.status)
                         }, 
                       null
                 );
	 },
	
	fetchAllUsers: function()
	{
		console.log("Entering Function List User")
		return $http.get(BASE_URL + "getAllUsers")
		.then(function(response)
			{
				console.log(response.data)
				console.log(response.status)
				return response.data
			})
	},
	
	 showDetails: function(username) {
     	console.log("calling showDetails " +username)
             return $http.get(BASE_URL+"getUser-"+username)
                     .then(
                             function(response){
                                 return response.data;
                             }, 
                             function(errResponse){
                                 console.error('Error while showing friends details');
                                
                             }
                     );
	 },
	
	listUser: function()
	{
		console.log("Entering Function List User")
		return $http.get(BASE_URL + "getAllUsers")
		.then(function(response)
			{
				console.log(response.data)
				console.log(response.status)
				return response.data
			})
	}
	}
})