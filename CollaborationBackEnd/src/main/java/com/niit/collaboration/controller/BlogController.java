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

import com.niit.collaboration.DAO.BlogDAO;
import com.niit.collaboration.model.Blog;
import com.niit.collaboration.model.BlogComment;
import com.niit.collaboration.util.DATE_TIME;

@RestController
public class BlogController 
{
	
	@Autowired
	private BlogDAO blogDAO;

	@Autowired
	private Blog blog;
	
	@Autowired
	private BlogComment blogcomment;
	
	@Autowired
	private HttpSession session;

	@GetMapping("/getAllBlogs")//done
	public ResponseEntity<List<Blog>> getAllBlogs()
	{
		List<Blog> list = blogDAO.admingetAllBlogs();
		if(list.isEmpty())
		{
			blog.setErrorCode("100");
			blog.setErrorMessage("No blogs are available");
			return new ResponseEntity<List<Blog>>(HttpStatus.NO_CONTENT);
		}
		else
		{
			blog.setErrorCode("200");
			blog.setErrorMessage("Success");
			return new ResponseEntity<List<Blog>>(list, HttpStatus.OK);
		}
	}
	
	@PostMapping("/approveBlog-{blog_id}")//done
	public ResponseEntity<Blog> approveBlog(@PathVariable("blog_id") int blog_id)
	{
		boolean flag = blogDAO.approveBlog(blog_id, 'A');
		if(!flag)
		{
			blog.setErrorCode("404");
			blog.setErrorMessage("Approving Blog Unsuccessful");
			return new ResponseEntity<Blog>(HttpStatus.BAD_REQUEST);
		}
		else
		{
			Blog userblog = blogDAO.getBlog(blog_id);
			blog.setErrorCode("100");
			blog.setErrorMessage("Blog has been approved");
			return new ResponseEntity<Blog>(userblog, HttpStatus.OK);
		}
		
	} 
	
	@PostMapping("/rejectBlog-{blog_id}")//done
	public ResponseEntity<Blog> rejectBlog(@PathVariable("blog_id") int blog_id)
	{
		boolean flag = blogDAO.rejectBlog(blog_id, 'R');
		if(!flag)
		{
			blog.setErrorCode("404");
			blog.setErrorMessage("Approving Blog Unsuccessful");
			return new ResponseEntity<Blog>(HttpStatus.BAD_REQUEST);
		}
		else
		{
			Blog userblog = blogDAO.getBlog(blog_id);
			blog.setErrorCode("100");
			blog.setErrorMessage("Blog has been approved");
			return new ResponseEntity<Blog>(userblog, HttpStatus.OK);
		}
		
	}
	
	@GetMapping("/getAllBlogs-{blog_id}")//done
	public ResponseEntity<Blog> getAllBlogs(@PathVariable("blog_id") int blog_id)
	{
		Blog blog = blogDAO.getAllBlogs(blog_id);
		if(blog == null)
		{
			blog.setErrorCode("100");
			blog.setErrorMessage("Users Blogs are not available");
		}
		else
		{
			blog.setErrorCode("200");
			blog.setErrorMessage("Success");
			return new ResponseEntity<Blog>(blog,HttpStatus.OK);
		}
		return null;
		
	}
	
	@GetMapping("/getAllApprovedBlogs")//done
	public ResponseEntity<List<Blog>> getAllApprovedBlogs()
	{
		List<Blog> list = blogDAO.getAllApprovedBlogs();
		blog.setErrorCode("200");
		blog.setErrorMessage("Success");
		return new ResponseEntity<List<Blog>>(list, HttpStatus.OK);
	}
	
	@PostMapping("/deleteBlog-{blog_id}")//done
	public ResponseEntity<Blog> deleteblog(@PathVariable("blog_id") int blog_id)
	{
		boolean flag = blogDAO.deleteBlog(blog_id);
		if(!flag)
		{
			blog.setErrorCode("100");
			blog.setErrorMessage("blog not deleted");
			return new ResponseEntity<Blog>(HttpStatus.BAD_REQUEST);
		}
		else
		{
			Blog userblog = blogDAO.getBlog(blog_id);
			blog.setErrorCode("200");
			blog.setErrorMessage("Success");
			return new ResponseEntity<Blog>(userblog, HttpStatus.OK);			
		}
	} 
	
	@PostMapping("/addBlog")//done
	public ResponseEntity<Blog> addBlog(@RequestBody Blog blog)
	{	
		String loggedInUserID = (String) session.getAttribute("loggedInUserID");
		DATE_TIME dt = new DATE_TIME();
		String date = dt.getDateTime();
		blog.setDate_time(date);
		blog.setStatus("N");
		blog.setUsername(loggedInUserID);
		
		boolean flag = blogDAO.addBlog(blog);
		
		if(!flag)
		{
			blog.setErrorCode("404");
			blog.setErrorMessage("Blog not added");
			return new ResponseEntity<Blog>(blog, HttpStatus.CONFLICT);
		}
		else
		{
			blog.setErrorCode("100");
			blog.setErrorMessage("Blog has been added");
			return new ResponseEntity<Blog>(blog, HttpStatus.OK);
		}
	}
	
	@PostMapping("/updateBlog")//done
	public ResponseEntity<Blog> updateBlog(@RequestBody Blog blog)
	{	
		DATE_TIME dt = new DATE_TIME();
		String date = dt.getDateTime();
		blog.setDate_time(date);
		blog.setStatus("Blog updated");
		
		boolean flag = blogDAO.updateBlog(blog);
		
		if(!flag)
		{
			blog.setErrorCode("404");
			blog.setErrorMessage("Blog not added");
			return new ResponseEntity<Blog>(blog, HttpStatus.CONFLICT);
			
		}
		else
		{
			blog.setErrorCode("100");
			blog.setErrorMessage("Blog has been added");
			return new ResponseEntity<Blog>(blog, HttpStatus.OK);
		}
	}
	
	@PostMapping("/addBlogComment")//done
	public ResponseEntity<BlogComment> addBlogComment(@RequestBody BlogComment blogcomment)
	{	
		DATE_TIME dt = new DATE_TIME();
		String date = dt.getDateTime();
		blogcomment.setPostdate(date);
		
		boolean flag = blogDAO.addBlogComment(blogcomment);
		
		if(!flag)
		{
			blogcomment.setErrorCode("404");
			blogcomment.setErrorMessage("Blog not added");
			return new ResponseEntity<BlogComment>(blogcomment, HttpStatus.CONFLICT);
		}
		else
		{
			blogcomment.setErrorCode("100");
			blogcomment.setErrorMessage("Blog has been added");
			return new ResponseEntity<BlogComment>(blogcomment, HttpStatus.OK);
		}
	}
	
	@GetMapping("/getAllBlogComment")//done
	public ResponseEntity<List<BlogComment>> getAllBlogComment()
	{
		List<BlogComment> list = blogDAO.getAllBlogComment();
		blogcomment.setErrorCode("200");
		blogcomment.setErrorMessage("Success");
		return new ResponseEntity<List<BlogComment>>(list, HttpStatus.OK);
	}
	
	@GetMapping("/getBlogCommentByID-{id}")//done
	public ResponseEntity<BlogComment> getBlogCommentByID(@PathVariable("id") int id)
	{
		BlogComment blogComment = blogDAO.getBlogCommentByID(id);
		if(blogcomment == null)
		{
			blogComment.setErrorCode("100");
			blogComment.setErrorMessage("Users Blogs are not available");
			return new ResponseEntity<BlogComment>(blogComment,HttpStatus.CONFLICT);
		}
		else
		{
			blogComment.setErrorCode("200");
			blogComment.setErrorMessage("Success");
			return new ResponseEntity<BlogComment>(blogComment,HttpStatus.OK);
		}
	}
	
	@PostMapping("/getLike-{blog_id}")//done
	public ResponseEntity<Blog> getLike(@PathVariable("blog_id") int blog_id)
	{
		Blog blog = blogDAO.getBlog(blog_id);
		boolean flag = blogDAO.getLike(blog_id);
		if(!flag)
		{
			blog.setErrorCode("100");
			blog.setErrorMessage("blog does not containe loke");
			return new ResponseEntity<Blog>(blog,HttpStatus.BAD_REQUEST);
		}
		else
		{
			blog.setErrorCode("200");
			blog.setErrorMessage("Like Success");
			return new ResponseEntity<Blog>(blog, HttpStatus.OK);
		}	
	} 
	
	@PostMapping("/getDeleteComment-{id}")//done
	public ResponseEntity<BlogComment> getDeleteComment(@PathVariable("id") int id)
	{
		BlogComment blogcomment = blogDAO.getBlogCommentByID(id);
		boolean flag = blogDAO.getDeleteComment(id);
		if(!flag)
		{
			blogcomment.setErrorCode("100");
			blogcomment.setErrorMessage("blog not deleted");
			return new ResponseEntity<BlogComment>(blogcomment,HttpStatus.BAD_REQUEST);
		}
		else
		{						
			blogcomment.setErrorCode("200");
			blogcomment.setErrorMessage("Success");
			return new ResponseEntity<BlogComment>(blogcomment, HttpStatus.OK);	
		}
		
	}
}
