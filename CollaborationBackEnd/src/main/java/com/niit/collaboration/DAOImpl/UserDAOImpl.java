package com.niit.collaboration.DAOImpl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.niit.collaboration.DAO.UserDAO;
import com.niit.collaboration.model.User;

@SuppressWarnings("deprecation")
@Repository("userDAO")
@EnableTransactionManagement

public class UserDAOImpl implements UserDAO
{
	private static final Logger log = LoggerFactory.getLogger(UserDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	public UserDAOImpl(SessionFactory sessionFactory)
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
	public List<User> getAllUsers() 
	{
		@SuppressWarnings("unchecked")
		String hql_string = "FROM User";
		Query query = sessionFactory.getCurrentSession().createQuery(hql_string);
		return query.list();
	}
	
	@Transactional
	public User get(String username)
	{
		
		log.debug("Starting of Method Get");
		try
		{
			User user =  sessionFactory.getCurrentSession().get(User.class, username);
			user.setErrorCode("200");
			user.setErrorCode("User Found");
			return user;
		}
		catch(Exception ex)
		{
			User user = new User();
			ex.printStackTrace();
			user.setErrorCode("404");
			user.setErrorCode("User Not Found");
			return null;
		}
	}

	@Transactional
	public boolean save(User user) 
	{
		try{
			sessionFactory.getCurrentSession().save(user);
			return true;
		}
		catch(HibernateException e){
			e.printStackTrace();
		return false;
		}
	}

	@Transactional
	public boolean update(User user)
	{
		try{
			sessionFactory.getCurrentSession().update(user);
			return true;
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
		return false;
		}
	}

	@Transactional
	public void delete(String username) 
	{
		User userToDelete = new User();
        userToDelete.setUsername(username);
        sessionFactory.getCurrentSession().delete(userToDelete);
		
	}

	@Transactional
	public User authenticate(String username, String password)
	{
		log.debug("Starting of the method isValidUserDetails");
		String hql = "from User where username= '" + username + "' and  password ='" + password+"'";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		
		@SuppressWarnings("unchecked")
		List<User> list = (List<User>) query.list();
		
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		
		return null;
	}

	@Transactional
	public void setOnline(String username)
	{
		
		String hql =" UPDATE User SET isOnline = 'Y' where username='" +  username + "'" ;
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.executeUpdate();
		
	}

	@Transactional
	public void setOfline(String username)
	{
		String hql =" UPDATE User SET isOnline = 'N' where username='" +  username + "'" ;
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.executeUpdate();
		
	}

}
