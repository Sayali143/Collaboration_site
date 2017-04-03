package com.niit.collaboration.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.niit.collaboration.DAO.FriendDAO;
import com.niit.collaboration.DAO.UserDAO;
import com.niit.collaboration.model.Friend;
import com.niit.collaboration.model.User;
import com.niit.collaboration.model.Friend;

@RestController
public class FriendController {

	private static final Logger logger = LoggerFactory.getLogger(FriendController.class);

	@Autowired
	FriendDAO friendDAO;

	@Autowired
	Friend friend;
	
	@Autowired
	HttpSession httpSession;
	
	@Autowired
	UserDAO userDAO;

	@GetMapping("/myFriends")//done show my friends(accept by me)
	public ResponseEntity<List<Friend>> getMyFriends()
	{
		logger.debug("Starting of getMyFriends method");
		String loggedInUserID=(String)httpSession.getAttribute("loggedInUserID");
		System.out.println(" abc ="+loggedInUserID);
		List<Friend> myFriends=new ArrayList<Friend>();
		if(loggedInUserID==null)
		{
			friend.setErrorCode("404");
			friend.setErrorMessage("Please login to know your friends");
			myFriends.add(friend);
			return new ResponseEntity<List<Friend>>(myFriends,HttpStatus.OK);
		}
		logger.debug("getting friends of: "+loggedInUserID);
		myFriends=friendDAO.getMyFriends(loggedInUserID);
		if(myFriends.isEmpty()){
			logger.debug("Friends does not exist for user: "+loggedInUserID);
			friend.setErrorCode("404");
			friend.setErrorMessage("No friends available for this user");
			myFriends.add(friend);
			
		}
		logger.debug("Ending of getMyFriends method");
		return new ResponseEntity<List<Friend>>(myFriends,HttpStatus.OK);
	}

	@GetMapping("/addFriend-{friendid}")//done
	public ResponseEntity<Friend> sendFriendRequest(@PathVariable("friendid") String friendid) 
	{
		logger.debug("Starting of addFriend method");
		String loggedInUserID=(String) httpSession.getAttribute("loggedInUserID");
        friend.setUsername(loggedInUserID);
        friend.setFriendid(friendid);
        friend.setStatus('N');//N-new friend request
        friend.setIsonline('N');
        
        if(isUserExist(friendid)==false)//check whether the friend exist in user table or not
        {
        	friend.setErrorCode("404");
        	friend.setErrorMessage("No User exist with this id: "+friendid);
        }
       
        if(friendDAO.get(loggedInUserID, friendid)!=null)//If user had already send a friend request
        {
        	friend.setErrorCode("404");
        	friend.setErrorMessage("You already sent the friend request");
        }
        else
        {
        	friendDAO.save(friend);
        	friend.setErrorCode("200");
        	friend.setErrorMessage("Friend request successfully sended to: "+friendid);
        }
		
        logger.debug("Ending of addFriend method");
		
		return new ResponseEntity<Friend>(friend,HttpStatus.OK);
	}
	
	private boolean isUserExist(String id)
	{
		if(userDAO.get(id)==null)
			return false;
		else
			return true;
	}
	
	private boolean isFriendRequestAvailabe(String friendid)
	{
		String loggedInUserID = (String) httpSession.getAttribute("loggedInUserID");
		
		if(friendDAO.get(loggedInUserID,friendid)==null)
			return false;
		else
			return true;
	}

	@PostMapping("/unFriend-{friendid}")//done
	public ResponseEntity<Friend> unFriend(@PathVariable("friendid") String friendid)
	{
		logger.debug("calling method unFriend");
		updateRequest(friendid,'U');
		return new ResponseEntity<Friend>(friend, HttpStatus.OK);

	}

	@PostMapping("/rejectFriend-{friendid}")//done
	public ResponseEntity<Friend> rejectFriend(@PathVariable("friendid") String friendid) 
	{
		logger.debug("calling method rejectFriendFriendRequest");
		updateRequest(friendid,'R');
		return new ResponseEntity<Friend>(friend, HttpStatus.OK);

	}

	@PostMapping("/acceptFriend-{friendid}")//done
	public ResponseEntity<Friend> acceptFriend(@PathVariable("friendid") String friendid)
	{
		logger.debug("Starting of acceptFriend method");
		updateRequest(friendid,'A');//A-Accept Friend request
		logger.debug("Ending of acceptFriend method");
		return new ResponseEntity<Friend>(friend,HttpStatus.OK);
	}

	private Friend updateRequest(String friendid, char status) 
	{
		logger.debug("Starting of updateRequest method");
		String loggedInUserID=(String) httpSession.getAttribute("loggedInUserID");
		if(isUserExist(friendid)==false){
			friend.setErrorCode("404");
			friend.setErrorMessage("The friend does not exist");
		}
		if(status=='A'||status=='R')
		{
			friend=friendDAO.get(loggedInUserID,friendid);
			logger.debug("friend and username");
		}
		else
		{
			friend=friendDAO.get(loggedInUserID,friendid);
			logger.debug("username and friend");
		}
		friend.setStatus(status);
		friendDAO.update(friend);
		friend.setErrorCode("200");
		friend.setErrorMessage("Request from= "+friend.getUsername()+" to "+friend.getFriendid()+" has updated to: "+status);
		logger.debug("Ending of updateRequest method");
		return friend;

	}

	@GetMapping("/getMyFriendRequests")//done
	public ResponseEntity<List<Friend>> getMyFriendRequests() 
	{
		logger.debug("Starting of getMyFriendRequest method");
		String loggedInUserID=(String) httpSession.getAttribute("loggedInUserID");
		List<Friend> myFriendRequests = friendDAO.getNewFriendRequests(loggedInUserID);
		logger.debug("Ending of getMyFriendRequest method");
		return new ResponseEntity<List<Friend>>(myFriendRequests,HttpStatus.OK);
		
	}
		
	@GetMapping("/getRequestsSendByMe")//done
	public ResponseEntity<List<Friend>>  getRequestsSendByMe()
	{
		logger.debug("calling method getRequestsSendByMe");
		String loggedInUserID = (String) httpSession.getAttribute("loggedInUserID");
		List<Friend> requestSendByMe = friendDAO.getRequestsSendByMe(loggedInUserID);
		logger.debug("Ending method getRequestsSendByMe");
		return new ResponseEntity<List<Friend>>(requestSendByMe, HttpStatus.OK);
		
	}

}