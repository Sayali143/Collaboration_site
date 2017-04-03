app.factory('EventService', function($http, $q, $rootScope)
{
	console.log("Entering EventService")
	var BASE_URL = "http://localhost:8081/CollaborationBackEnd/"
		return{
	
	registerEvent: function(event)
	{
		console.log("Entering Function Register Event")
		return $http.post(BASE_URL + "addEvent", event)
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
	
	deleteEvent: function(id)
	{
		console.log("Entering Function delete Event")
		return $http.post(BASE_URL +"deleteEvent-"+id)
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
		
	listEvent: function()
	{
		console.log("Entering Function List Event")
		return $http.get(BASE_URL + "listEvents")
		.then(function(response)
			{
				console.log(response.data)
				console.log(response.status)
				return response.data
			})
	},
	
	updateEvent: function(event){
     	console.log("calling UpdateEvent")
             return $http.post(BASE_URL+"updateEvent",event)  
                     .then(
                             function(response){
                                 return response.data;
                             }, 
                             function(errResponse){
                                 console.error('Error while updating event');
                                 return $q.reject(errResponse);
                             }
                     );
	 },
	
	getEventById: function(id)
	{
		console.log("Entering Function List MyEvent")
		return $http.get(BASE_URL + "getEventById-",id)
		.then(function(response)
			{
				console.log(response.data)
				console.log(response.status)
				return response.data
			})
	}
	}
})