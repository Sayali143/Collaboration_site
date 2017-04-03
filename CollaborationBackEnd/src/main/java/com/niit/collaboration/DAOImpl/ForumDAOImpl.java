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

import com.niit.collaboration.DAO.ForumDAO;
import com.niit.collaboration.model.Forum;
import com.niit.collaboration.model.ForumComment;

@SuppressWarnings("deprecation")
@Repository("forumDAO")
@EnableTransactionManagement
public class ForumDAOImpl implements ForumDAO 
{

	private static final Logger log = LoggerFactory.getLogger(ForumDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	public ForumDAOImpl(SessionFactory sessionFactory)
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
	public Forum getForum(int forum_id)
	{
		log.debug("Starting of Method Get");
		try
		{
			Forum forum =  sessionFactory.getCurrentSession().get(Forum.class, forum_id);
			return forum;
		}
		catch(Exception ex)
		{
			Forum forum = new Forum();
			ex.printStackTrace();
			return null;
		}
	}

	@Transactional
	public List<Forum> getAllApprovedForums() 
	{
		@SuppressWarnings("unchecked")
		String hql_string = "FROM Forum WHERE status = 'A'";
		Query query = sessionFactory.getCurrentSession().createQuery(hql_string);
		return query.list();
	}

	@Transactional
	public Forum getAllForums(int forum_id)
	{
		try
		{
			log.debug("Method getAllBlogs of user is starting");
			return (Forum) sessionFactory.getCurrentSession().get(Forum.class,forum_id);
		}
		catch(HibernateException ex){
			log.debug("Error :" + ex.getMessage());
			ex.printStackTrace();
			return null;
		}
	}

	@Transactional
	public List<Forum> admingetAllForums()
	{
		@SuppressWarnings("unchecked")
		String hql_string = "FROM Forum";
		Query query = sessionFactory.getCurrentSession().createQuery(hql_string);
		return query.list();
	}

	@Transactional
	public boolean addForum(Forum forum)
	{
		try
		{
			sessionFactory.getCurrentSession().save(forum);
			return true;
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
		return false;
		}
	}

	@Transactional
	public boolean updateForum(Forum forum)
	{
		try
		{
			sessionFactory.getCurrentSession().update(forum);
			return true;
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
		return false;
		}
	}

	@Transactional
	public boolean deleteForum(int forum_id) //done
	{
		try
		{
			Session session = sessionFactory.getCurrentSession();
	        Query query = session.createQuery("delete from Forum where forum_id = " +forum_id);
			return query.executeUpdate()==1 ? true : false;
		}
		catch(HibernateException ex){
			log.debug("Data delete Error :" + ex.getMessage());
			ex.printStackTrace();
			return false;
		}
	}

	@Transactional
	public boolean approveForum(int forum_id, char flag)
	{
		try{
			Session session = sessionFactory.getCurrentSession();
	        Query query = session.createQuery("update Forum set status = '" + flag + "' where forum_id= " + forum_id);
			return query.executeUpdate()==1 ? true : false;
		}
		catch(HibernateException ex){
			log.debug("approve not done :" + ex.getMessage());
			ex.printStackTrace();
			return false;
		}
	}

	@Transactional
	public boolean rejectForum(int forum_id, char flag)
	{
		try
		{
			Session session = sessionFactory.getCurrentSession();
	        Query query = session.createQuery("update Forum set status = '" + flag + "' where forum_id= " + forum_id);
			return query.executeUpdate()==1 ? true : false;
		}
		catch(HibernateException ex)
		{
			log.debug("reject not done :" + ex.getMessage());
			ex.printStackTrace();
			return false;
		}
	}
	
	//add comment start here
	
	@Transactional
	public boolean addForumComment(ForumComment forumcomment)
	{
		try
		{
			sessionFactory.getCurrentSession().save(forumcomment);
			return true;
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
		return false;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional
	public List<ForumComment> getAllForumComment(int forum_id) 
	{
		log.info("Getting list of replies for "+forum_id);
		try
		{
			String sql = "FROM ForumComment where forum_id = "+forum_id;
			Query query = sessionFactory.getCurrentSession().createQuery(sql);
			if(query != null)
			{
				return query.list();
			}
		}	catch(Exception ex)
		{
			log.error("Error retrieving list");
			ex.printStackTrace();
		}
		return null;
	}

	@Transactional
	public ForumComment getForumCommentByID(int id)
	{
		try
		{
			log.debug("Method getAllForumComments of user is starting");
			return (ForumComment) sessionFactory.getCurrentSession().get(ForumComment.class,id);
		}
		catch(HibernateException ex){
			log.debug("Error :" + ex.getMessage());
			ex.printStackTrace();
			return null;
		}
	}

	@Transactional
	public boolean getDeleteForumComment(ForumComment forumcomment)
	{
		try
		{
			sessionFactory.getCurrentSession().delete(forumcomment);
			log.info("comment deleted");
			return true;
		}	
		catch(Exception ex)
		{
			log.info("Error deleting reply");
			ex.printStackTrace();
			return false;
		}
	}
}
