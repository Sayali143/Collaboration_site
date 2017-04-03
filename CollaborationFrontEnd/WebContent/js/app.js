var app = angular.module('Collaboration',['ngRoute', 'ngCookies']);

app.config(function($routeProvider)
	{
	$routeProvider
	
		.when('/home',
		{
			templateUrl : 'views/home.html',
		})
		.when('/Contact_us',
	{
		templateUrl : 'views/Contact_us.html',
	})
		.when('/login',
	{
		templateUrl : 'views/login.html',
		controller : 'UserController'
	})
		.when('/logout',
	{
		templateUrl : 'views/home.html',
		controller : 'UserController'
	})
		.when('/register',
	{
		templateUrl : 'views/register.html',
		controller : 'UserController'
	})
		.when('/viewUsers',
	{
		templateUrl : 'c_user/userList.html',
		controller : 'UserController'	
	})
		.when('/MyProfile',
	{
		templateUrl : 'c_user/my_profile.html',
		controller : 'UserController'	
	})
		.when('/updateuser',
	{
		templateUrl : 'c_user/UpdateUser.html',
		controller : 'UserController'	
	})
		.when('/acceptUser',
	{
		templateUrl : 'c_user/acceptedUsers.html',
		controller : 'UserController'	
	})
		.when('/rejectUser',
	{
		templateUrl : 'c_user/rejectedUsers.html',
		controller : 'UserController'	
	})
		.when('/upload',
	{
		templateUrl : 'c_user/FileUpload.html',
		//controller : 'UserController'	
	})
		.when('/viewJobs',
	{
		templateUrl : 'c_job/jobList.html',
		controller : 'JobController'	
	})
		.when('/MyAppliedJobs',
	{
		templateUrl : 'c_job/MyAppliedJobs.html',
		controller : 'JobController'	
	})
		.when('/AdminJobList',
	{
		templateUrl : 'c_job/AdminJobList.html',
		controller : 'JobController'	
	})
		.when('/AddJob',
	{
		templateUrl : 'c_job/AddJob.html',
		controller : 'JobController'	
	})
		.when('/viewBlogs',
	{
		templateUrl : 'c_blog/blogList.html',
		controller : 'BlogController'	
	})
		.when('/AddBlog',
	{
		templateUrl : 'c_blog/AddBlog.html',
		controller : 'BlogController'	
	})
		.when('/MyBlog',
	{
		templateUrl : 'c_blog/MyBlog.html',
		controller : 'BlogController'	
	})
		.when('/viewAdminBlogs',
	{
		templateUrl : 'c_blog/AdminBlogList.html',
		controller : 'BlogController'	
	})
		.when('/viewForums',
	{
		templateUrl : 'c_forum/forumList.html',
		controller : 'ForumController'	
	})
		.when('/AddForum',
	{
		templateUrl : 'c_forum/AddForum.html',
		controller : 'ForumController'	
	})
		.when('/MyForum',
	{
		templateUrl : 'c_forum/MyForum.html',
		controller : 'ForumController'	
	})
		.when('/viewAdminForums',
	{
		templateUrl : 'c_forum/AdminForumList.html',
		controller : 'ForumController'	
	})
		.when('/viewFriend',
	{
		templateUrl : 'c_friend/view_friend.html',
		controller : 'FriendController'	
	})
		.when('/viewFriendRequest',
	{
		templateUrl : 'c_friend/FriendRequest.html',
		controller : 'FriendController'	
	})
		.when('/viewSearchFriend',
	{
		templateUrl : 'c_friend/search_friend.html',
		controller : 'FriendController'	
	})
		.when('/SendByMe',
	{
		templateUrl : 'c_friend/SendByMe.html',
		controller : 'FriendController'	
	})
		.when('/viewEvents',
	{
		templateUrl : 'c_event/eventList.html',
		controller : 'EventController'	
	})
		.when('/UpdateEvent',
	{
		templateUrl : 'c_event/UpdateEvent.html',
		controller : 'EventController'	
	})
		.when('/AddEvent',
	{
		templateUrl : 'c_event/AddEvent.html',
		controller : 'EventController'	
	})
		.when('/MyEvent',
	{
		templateUrl : 'c_event/MyEvent.html',
		controller : 'EventController'	
	})
		.when('/chat_forum',
	{
		templateUrl : 'c_chat/chat_forum.html',
		controller : 'ChatController'	
	})
		.when('/myProfile', {
		templateUrl : 'c_upload/imageupload.html',
		})
	.otherwise({redirectTo: '/'});
});

app.run( function ($rootScope, $location, $cookieStore, $http) {

	 $rootScope.$on('$locationChangeStart', function (event, next, current)
	{
		 console.log("$locationChangeStart")
		
		 var userPages = ["/viewFriend","/viewFriends","/viewEvents","/viewJobs","/FileUpload","/viewJobs","/MyAppliedJobs","/viewBlogs","/AddBlog","/MyBlog","/viewForums","/AddForum","/MyForum","/viewFriend","/viewFriendRequest","/viewSearchFriend","/SendByMe","/viewEvents","/UpdateEvent","/AddEvent","/MyEvent","/chat_forum","/myProfile"]
		 var adminPages = ["/viewBlogs","/viewUsers","/acceptUser","/rejectUser","/AdminJobList","/AddJob","/viewAdminBlogs","/viewAdminForums"]
		 
		 var currentPage = $location.path()
		 
		 var isUserPage = $.inArray(currentPage, userPages) ==1;
		 var isAdminPage = $.inArray(currentPage, adminPages) ==1;
		 
		 var isLoggedIn = $rootScope.currentUser.username;
	        
	     console.log("isLoggedIn:" +isLoggedIn)
	     console.log("isUserPage:" +isUserPage)
	     console.log("isAdminPage:" +isAdminPage)
	        
	        if(!isLoggedIn)
	        	{
	        	
	        	 if (isUserPage || isAdminPage) {
		        	  console.log("Navigating to login page:")
		        	  alert("You need to loggin to do this operation")

						            $location.path('/');
		                }
	        	}
	        
			 else //logged in
	        	{
				 var role = $rootScope.currentUser.role;
				 
				 if(isAdminPage && role!='Admin' )
					 {
					 
					  alert("You can not do this operation as you are logged as : " + role )
					   $location.path('/login');
					 
					 }
	        	}
	 }
	       );
	 // keep user logged in after page refresh
    $rootScope.currentUser = $cookieStore.get('currentUser') || {};
    if ($rootScope.currentUser) {
        $http.defaults.headers.common['Authorization'] = 'Basic' + $rootScope.currentUser; 
    }
});