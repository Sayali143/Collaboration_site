app.controller('EventController', function($scope, $location, EventService, $rootScope, $cookieStore, $http)
{
		console.log("Entering Event Controller")
		var self = this;
		$scope.events;
		$scope.event={id:'',event_date:'', description:'', name:'', username:'', time:'', venue:'', on_date:'', errorMessage:'', errorCode:''};
		$scope.message;
		$scope.message1;
		
		$scope.registerEvent=function()
		{
			console.log("Entering Register Event")
			EventService.registerEvent($scope.event)
			.then
			(
				function(response)
				{
					console.log("Registeration Successful "+response.status)
					$location.path("/home")
				}
			);
		};
		
		self.deleteEvent=function(id)
		{
			console.log("Entering delete Event")
			EventService.deleteEvent(id)
			.then
			(
					self.listEvent,
				function(response)
				{
					console.log("delete Successful "+response.status)
					$location.path("/MyEvent")
				}
			);
		};
		
		self.updateEvent = function() {
			console.log("updateEvent...")
			EventService.updateEvent($scope.event)
					.then(
							self.listEvent,
							function(response)
							{
								console.log("update Successful "+response.status)
								$location.path("#/UpdateEvent")
							});
		};
	
		self.getEventById=function getEventById(id)
        {
      	  console.log("getEventById :"+id)
            EventService.getEventById(id)
                .then(
                             function(d)
                             {
                           	   self.event = d;
                           	   
                             },
                              function(errResponse)
                              {
                                  console.error('Error while sending friend request');
                              }
                     );
        };
		
		listEvent=function()
		{
			console.log("Entering List Event Method")
			EventService.listEvent()
			.then
			(
				function(response)
				{
					console.log("List of Events Successful"+response.status)
					$scope.events=response
				}
			)
		}
		listEvent();
})