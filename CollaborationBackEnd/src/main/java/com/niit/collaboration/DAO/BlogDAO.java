package com.niit.collaboration.DAO;

import java.util.List;

import com.niit.collaboration.model.Blog;
import com.niit.collaboration.model.BlogComment;

public interface BlogDAO 
{
	
	public Blog getBlog(int blog_id);
	
	public List<Blog> getAllApprovedBlogs();
	
	public Blog getAllBlogs(int blog_id);
	
	public List<Blog> admingetAllBlogs();
	
	public boolean addBlog(Blog Blog);
	
	public boolean updateBlog(Blog Blog);
	
	public boolean deleteBlog(int blog_id);
	
	public boolean approveBlog(int blog_id, char flag);
	
	public boolean rejectBlog(int blog_id, char flag);
	
	public boolean addBlogComment(BlogComment blogcomment);
	
	public List<BlogComment> getAllBlogComment();
	
	public BlogComment getBlogCommentByID(int id);
	
	public boolean getLike(int blog_id);
	
	public boolean getDeleteComment(int id);

}
