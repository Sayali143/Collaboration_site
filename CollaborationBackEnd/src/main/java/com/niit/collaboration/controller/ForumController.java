package com.niit.collaboration.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.niit.collaboration.DAO.ForumDAO;
import com.niit.collaboration.model.Forum;
import com.niit.collaboration.model.ForumComment;
import com.niit.collaboration.model.Forum;
import com.niit.collaboration.util.DATE_TIME;

@RestController
public class ForumController 
{
	
	@Autowired
	private ForumDAO forumDAO;

	@Autowired
	private Forum forum;
	
	@Autowired
	private ForumComment forumcomment;
	
	@Autowired
	private HttpSession session;

	@GetMapping("/getAllForums")//done
	public ResponseEntity<List<Forum>> getAllForums()
	{
		List<Forum> list = forumDAO.admingetAllForums();
		if(list.isEmpty())
		{
			forum.setErrorCode("100");
			forum.setErrorMessage("No forums are available");
			return new ResponseEntity<List<Forum>>(HttpStatus.NO_CONTENT);
		}
		else
		{
			forum.setErrorCode("200");
			forum.setErrorMessage("Success");
			return new ResponseEntity<List<Forum>>(list, HttpStatus.OK);
		}
	}
	
	@PostMapping("/approveForum-{forum_id}")//done
	public ResponseEntity<Forum> approveForum(@PathVariable("forum_id") int forum_id)
	{
		boolean flag = forumDAO.approveForum(forum_id, 'A');
		if(!flag)
		{
			forum.setErrorCode("404");
			forum.setErrorMessage("Approving Forum Unsuccessful");
			return new ResponseEntity<Forum>(HttpStatus.BAD_REQUEST);
		}
		else
		{
			Forum userForum = forumDAO.getForum(forum_id);
			forum.setErrorCode("100");
			forum.setErrorMessage("Forum has been approved");
			return new ResponseEntity<Forum>(userForum, HttpStatus.OK);
		}	
	}
	
	@PostMapping("/rejectForum-{forum_id}")//done
	public ResponseEntity<Forum> rejectForum(@PathVariable("forum_id") int forum_id)
	{
		boolean flag = forumDAO.rejectForum(forum_id, 'R');
		if(!flag)
		{
			forum.setErrorCode("404");
			forum.setErrorMessage("Approving Forum Unsuccessful");
			return new ResponseEntity<Forum>(HttpStatus.BAD_REQUEST);
		}
		else
		{
			Forum userForum = forumDAO.getForum(forum_id);
			forum.setErrorCode("100");
			forum.setErrorMessage("Forum has been approved");
			return new ResponseEntity<Forum>(userForum, HttpStatus.OK);
		}
		
	}
	
	@GetMapping("/getAllForums-{forum_id}")//done
	public ResponseEntity<Forum> getAllForums(@PathVariable("forum_id") int forum_id)
	{
		Forum forum = forumDAO.getAllForums(forum_id);
		if(forum == null)
		{
			forum.setErrorCode("100");
			forum.setErrorMessage("Users Forums are not available");
		}
		else
		{
			forum.setErrorCode("200");
			forum.setErrorMessage("Success");
			return new ResponseEntity<Forum>(forum,HttpStatus.OK);
		}
		return null;
		
	}
	
	@GetMapping("/getForum-{forum_id}")
	public ResponseEntity<Forum> getForum(@PathVariable ("forum_id") int forum_id)
	{
		if(session.getAttribute("username")==null)
		{
			return null;
		}
		else
		{
			forum = forumDAO.getForum(forum_id);
			if(forum==null)
			{
				return null;
			}
			else
			{
				return new ResponseEntity<Forum>(forum, HttpStatus.OK);
			}
		}
	}
	
	@GetMapping("/getAllApprovedForums")//done
	public ResponseEntity<List<Forum>> getAllApprovedForums()
	{
		List<Forum> list = forumDAO.getAllApprovedForums();
		forum.setErrorCode("200");
		forum.setErrorMessage("Success");
		return new ResponseEntity<List<Forum>>(list, HttpStatus.OK);
	}
	
	@PostMapping("/deleteForum-{forum_id}")//done
	public ResponseEntity<Forum> deleteforum(@PathVariable("forum_id") int forum_id)
	{
		boolean flag = forumDAO.deleteForum(forum_id);
		if(!flag)
		{
			forum.setErrorCode("100");
			forum.setErrorMessage("forum not deleted");
			return new ResponseEntity<Forum>(HttpStatus.BAD_REQUEST);
		}
		else
		{
			Forum userforum = forumDAO.getForum(forum_id);
			forum.setErrorCode("200");
			forum.setErrorMessage("Success");
			return new ResponseEntity<Forum>(userforum, HttpStatus.OK);			
		}
	} 
	
	@PostMapping("/addForum")//done
	public ResponseEntity<Forum> addForum(@RequestBody Forum forum)
	{	
		
		String loggedInUserID = (String) session.getAttribute("loggedInUserID");
		DATE_TIME dt = new DATE_TIME();
		String date = dt.getDateTime();
		forum.setDate_time(date);
		forum.setStatus("N");
		forum.setUsername(loggedInUserID);
		
		boolean flag = forumDAO.addForum(forum);
		
		if(!flag)
		{
			forum.setErrorCode("404");
			forum.setErrorMessage("Forum not added");
			return new ResponseEntity<Forum>(forum, HttpStatus.CONFLICT);
		}
		else
		{
			forum.setErrorCode("100");
			forum.setErrorMessage("Forum has been added");
			return new ResponseEntity<Forum>(forum, HttpStatus.OK);
		}
	}
	
	@PostMapping("/updateForum")//done
	public ResponseEntity<Forum> updateForum(@RequestBody Forum forum)
	{	
		DATE_TIME dt = new DATE_TIME();
		String date = dt.getDateTime();
		forum.setDate_time(date);
		forum.setStatus("A");
		
		boolean flag = forumDAO.updateForum(forum);
		
		if(!flag)
		{
			forum.setErrorCode("404");
			forum.setErrorMessage("Forum not added");
			return new ResponseEntity<Forum>(forum, HttpStatus.CONFLICT);
			
		}
		else
		{
			forum.setErrorCode("100");
			forum.setErrorMessage("Forum has been added");
			return new ResponseEntity<Forum>(forum, HttpStatus.OK);
		}
	}
	
	//comment start here.
	
	@PostMapping("/addForumComment")//done
	public ResponseEntity<ForumComment> addForumComment(@RequestBody ForumComment forumcomment)
	{	
		String loggedInUserID = (String) session.getAttribute("loggedInUserID");
		DATE_TIME dt = new DATE_TIME();
		String date = dt.getDateTime();
		forumcomment.setPostdate(date);
		forumcomment.setUsername(loggedInUserID);
		
		boolean flag = forumDAO.addForumComment(forumcomment);
		
		if(!flag)
		{
			forumcomment.setErrorCode("404");
			forumcomment.setErrorMessage("forum not added");
			return new ResponseEntity<ForumComment>(forumcomment, HttpStatus.CONFLICT);
		}
		else
		{
			forumcomment.setErrorCode("100");
			forumcomment.setErrorMessage("forum has been added");
			return new ResponseEntity<ForumComment>(forumcomment, HttpStatus.OK);
		}
	}
	
	@GetMapping("/getForumCommentByID-{forum_id}")//done
	public ResponseEntity<List<ForumComment>> getForumCommentByID(@PathVariable("forum_id") int forum_id)
	{
		List<ForumComment> list = forumDAO.getAllForumComment(forum_id);
		if(list.isEmpty() || list==null)
		{
			forumcomment.setErrorCode("100");
			forumcomment.setErrorMessage("Users forums are not available");
			return new ResponseEntity<List<ForumComment>>(list,HttpStatus.CONFLICT);
		}
		else
		{
			forumcomment.setErrorCode("200");
			forumcomment.setErrorMessage("Success");
			return new ResponseEntity<List<ForumComment>>(list, HttpStatus.OK);
		}
	}
	
	@PostMapping("/getDeleteForumComment-{id}")//done
	public ResponseEntity<ForumComment> getDeleteForumComment(@PathVariable("id") int id)
	{
		try
		{
			forumcomment = forumDAO.getForumCommentByID(id);
		}	catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
		boolean value = forumDAO.getDeleteForumComment(forumcomment);
		if(value)
		{
			forumcomment.setErrorCode("200");
			forumcomment.setErrorMessage("Forum has been deleted");
		}
		else
		{
			return null;
		}
		return new ResponseEntity<ForumComment>(forumcomment, HttpStatus.OK);
	}
}
