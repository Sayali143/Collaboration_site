package com.niit.collaboration.DAOImpl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.niit.collaboration.DAO.BlogDAO;
import com.niit.collaboration.model.Blog;
import com.niit.collaboration.model.BlogComment;
import com.niit.collaboration.model.User;

@SuppressWarnings("deprecation")
@Repository("blogDAO")
@EnableTransactionManagement
public class BlogDAOImpl implements BlogDAO
{
	private static final Logger log = LoggerFactory.getLogger(BlogDAOImpl.class);

	@Autowired(required = true)
	private SessionFactory sessionFactory;
	
	public BlogDAOImpl(SessionFactory sessionFactory)
	{
		try 
		{
			log.info("connection establish");
			this.sessionFactory = sessionFactory;
		} 
		catch (Exception ex) 
		{
			log.error("failed establish connection");
			ex.printStackTrace();
		}
	}

	@Transactional
	public Blog getBlog(int blog_id) 
	{
		log.debug("Starting of Method Get");
		try
		{
			Blog blog =  sessionFactory.getCurrentSession().get(Blog.class, blog_id);
			return blog;
		}
		catch(Exception ex)
		{
			Blog blog = new Blog();
			ex.printStackTrace();
			return null;
		}
	}

	@Transactional
	public Blog getAllBlogs(int blog_id)
	{
		try
		{
			log.debug("Method getAllBlogs of user is starting");
			return (Blog) sessionFactory.getCurrentSession().get(Blog.class,blog_id);
		}
		catch(HibernateException ex){
			log.debug("Error :" + ex.getMessage());
			ex.printStackTrace();
			return null;
		}
	}

	@Transactional
	public boolean addBlog(Blog Blog)
	{
		try{
			sessionFactory.getCurrentSession().save(Blog);
			return true;
		}
		catch(HibernateException e){
			e.printStackTrace();
		return false;
		}
	}

	@Transactional
	public boolean updateBlog(Blog Blog)
	{
		try{
			sessionFactory.getCurrentSession().update(Blog);
			return true;
		}
		catch(HibernateException e){
			e.printStackTrace();
		return false;
		}
	}

	@Transactional
	public boolean deleteBlog(int blog_id)
	{
		try{
			Session session = sessionFactory.getCurrentSession();
	        Query query = session.createQuery("delete from Blog where blog_id = " +blog_id);
			return query.executeUpdate()==1 ? true : false;
		}
		catch(HibernateException ex){
			log.debug("Data delete Error :" + ex.getMessage());
			ex.printStackTrace();
			return false;
		}
	}

	@Transactional
	public boolean approveBlog(int blog_id, char flag) 
	{
		try{
			Session session = sessionFactory.getCurrentSession();
	        Query query = session.createQuery("update Blog set status = '" + flag + "' where blog_id= " + blog_id);
			return query.executeUpdate()==1 ? true : false;
		}
		catch(HibernateException ex){
			log.debug("approve not done :" + ex.getMessage());
			ex.printStackTrace();
			return false;
		}
	}

	@Transactional
	public boolean rejectBlog(int blog_id, char flag) 
	{
		try
		{
			Session session = sessionFactory.getCurrentSession();
	        Query query = session.createQuery("update Blog set status = '" + flag + "' where blog_id= " + blog_id);
			return query.executeUpdate()==1 ? true : false;
		}
		catch(HibernateException ex)
		{
			log.debug("approve not done :" + ex.getMessage());
			ex.printStackTrace();
			return false;
		}
	}

	@Transactional
	public List<Blog> getAllApprovedBlogs()
	{
		@SuppressWarnings("unchecked")
		String hql_string = "FROM Blog WHERE status = 'A'";
		Query query = sessionFactory.getCurrentSession().createQuery(hql_string);
		return query.list();
	}
	
	@Transactional
	public List<Blog> admingetAllBlogs()
	{
		@SuppressWarnings("unchecked")
		String hql_string = "FROM Blog";
		Query query = sessionFactory.getCurrentSession().createQuery(hql_string);
		return query.list();
	}
	
	@Transactional
	public boolean addBlogComment(BlogComment blogcomment) 
	{
		try{
			sessionFactory.getCurrentSession().save(blogcomment);
			return true;
		}
		catch(HibernateException e){
			e.printStackTrace();
		return false;
		}
	}
	
	@Transactional
	public List<BlogComment> getAllBlogComment()
	{
		@SuppressWarnings("unchecked")
		String hql_string = "FROM BlogComment";
		Query query = sessionFactory.getCurrentSession().createQuery(hql_string);
		return query.list();
	}

	@Transactional
	public BlogComment getBlogCommentByID(int id)
	{
		try
		{
			log.debug("Method getAllBlogComments of user is starting");
			return (BlogComment) sessionFactory.getCurrentSession().get(BlogComment.class,id);
		}
		catch(HibernateException ex){
			log.debug("Error :" + ex.getMessage());
			ex.printStackTrace();
			return null;
		}
	}

	@Transactional
	public boolean getLike(int blog_id)
	{
		try
		{
			Session session = sessionFactory.getCurrentSession();
	        Query query = session.createQuery("update Blog set likes = likes + 1 where blog_id = " + blog_id);
			return query.executeUpdate()==1 ? true : false;
		}
		catch(HibernateException ex)
		{
			log.debug("Error :" + ex.getMessage());
			ex.printStackTrace();
			return false;
		}
	}

	@Transactional
	public boolean getDeleteComment(int id)
	{
		try{
			Session session = sessionFactory.getCurrentSession();
	        Query query = session.createQuery("delete from BlogComment where id = " + id);
			return query.executeUpdate()==1 ? true : false;
		}
		catch(HibernateException ex){
			log.debug("Data update Error :" + ex.getMessage());
			ex.printStackTrace();
			return false;
		}

	}

}
