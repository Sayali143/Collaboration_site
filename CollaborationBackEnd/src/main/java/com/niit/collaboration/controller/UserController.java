package com.niit.collaboration.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.niit.collaboration.DAO.FriendDAO;
import com.niit.collaboration.DAO.UserDAO;
import com.niit.collaboration.model.User;
import com.niit.collaboration.util.DATE_TIME;

@RestController
public class UserController {

	@Autowired
	private UserDAO UserDAO;

	@Autowired
	private User user;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	FriendDAO friendDAO;
	

	@GetMapping("/hello")//done
	public String sayHello() 
	{
		return "Hello";
	}

	@GetMapping("/getAllUsers")//All done
	public ResponseEntity<List<User>> getAllUsers()
	{
		List users = UserDAO.getAllUsers();
		if (users.isEmpty()) {
			user.setErrorCode("100");
			user.setErrorMessage("Not users are available");
			users.add(user);
			return new ResponseEntity<List<User>>(users, HttpStatus.OK);
		}
		user.setErrorCode("200");
		user.setErrorMessage("Successfully fetched the user");

		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

	@GetMapping("/getUser-{username}")//All done
	public ResponseEntity<User> getUser(@PathVariable("username") String id) {
		User user = UserDAO.get(id);
		if (user == null) {
			user = new User();
			user.setErrorCode("404");
			user.setErrorMessage("User does not exist");
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
		user.setErrorCode("200");
		user.setErrorCode("User " + id + " is found.");
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@PostMapping("/Validate")//All done
	public ResponseEntity<User> Validate(@RequestBody User user)
	{
		user = UserDAO.authenticate(user.getUsername(), user.getPassword());
			
		if (user == null) {
			user = new User(); // Do wee need to create new user?
			user.setErrorCode("404");
			user.setErrorMessage("Invalid Credentials.  Please enter valid credentials");
			

		} else

		{
			user.setErrorCode("200");
			user.setErrorMessage("You have successfully logged in.");
			user.setIsonline('Y');
			session.setAttribute("loggedInUser", user);
			session.setAttribute("loggedInUserID", user.getUsername());
			session.setAttribute("loggedInUserRole", user.getRole());
			
			friendDAO.setOnline(user.getUsername());
			UserDAO.setOnline(user.getUsername());
		}
		/*}*/
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@PostMapping("/addUser")//All done
	public ResponseEntity<User> addUser(@RequestBody User user)
	{
		 user.setStatus('N');
		 user.setIsonline('N');
		 boolean value = UserDAO.save(user);
		if (UserDAO.get(user.getUsername()) == null) {
			UserDAO.save(user);
			user.setErrorCode("404");
			user.setErrorMessage("User exist with this id : " + user.getUsername());			
		} 
		else
		{
			user.setErrorCode("200");
			user.setErrorMessage("You have successfully registered...");
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	
	@PutMapping("/updateUser")//All done
	public ResponseEntity<User> updateUser(@RequestBody User user) {
		if (UserDAO.get(user.getUsername()) == null) {
			user = new User(); //To avoid NLP - NullPointerException
			user.setErrorCode("404");
			user.setErrorMessage("User does not exist with id " + user.getUsername());
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
		else{
			user.setIsonline('Y');
			user.setStatus('A');
			UserDAO.update(user);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@GetMapping("/myProfile")//All done
	public ResponseEntity<User> myProfile(HttpSession session) {
		String loggedInUserID = (String) session.getAttribute("loggedInUserID");
		User user = UserDAO.get(loggedInUserID);
		if (user == null) {
			user = new User(); // It does not mean that we are inserting new row
			user.setErrorCode("404");
			user.setErrorMessage("User does not exist");
			return new ResponseEntity<User>(user, HttpStatus.NOT_FOUND);
		}
		user.setErrorMessage("User successfull");
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@GetMapping("/accept-{username}")//All done
	public ResponseEntity<User> accept(@PathVariable("username") String username)
	{
		user = updateStatus(username, 'A');
		return new ResponseEntity<User>(user, HttpStatus.OK);

	}

	@GetMapping("/reject-{username}")//All done
	public ResponseEntity<User> reject(@PathVariable("username") String username)
	{
		user = updateStatus(username, 'R');
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	private User updateStatus(String username, char status) 
	{
		user = UserDAO.get(username);

		if (user == null) {
			user = new User();
			user.setErrorCode("404");
			user.setErrorMessage("UserID does not exist.Could not update the status to " + status);
		} else {

			user.setStatus(status);
			UserDAO.update(user);
			
			user.setErrorCode("200");
			user.setErrorMessage("Updated the status successfully");
		}
		
		return user;

	}
	
	@GetMapping("/logout")
	public ResponseEntity<User> logout()
	{
		String loggedInUserID = (String) session.getAttribute("loggedInUserID");
		friendDAO.setOnline(loggedInUserID);
		UserDAO.setOfline(loggedInUserID);
		user.setIsonline('N');
		session.invalidate();
		user.setErrorCode("200");
		user.setErrorMessage("You have successfully logged");
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

}

