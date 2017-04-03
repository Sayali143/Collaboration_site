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

import com.niit.collaboration.DAO.EventDAO;
import com.niit.collaboration.model.Event;
import com.niit.collaboration.model.Job;

@SuppressWarnings("deprecation")
@Repository("eventDAO")
@EnableTransactionManagement
public class EventDAOImpl implements EventDAO
{

	private static final Logger log = LoggerFactory.getLogger(EventDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	public EventDAOImpl(SessionFactory sessionFactory)
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
	public boolean addEvent(Event event) 
	{
		try
		{
			sessionFactory.getCurrentSession().save(event);
			return true;
		}
		catch(HibernateException e){
			e.printStackTrace();
		return false;
		}
	}

	@Transactional
	public Event getEventById(int id) 
	{
		log.debug("Starting of Method Get");
		try
		{
			Event event = sessionFactory.getCurrentSession().get(Event.class,id);
			event.setErrorCode("200");
			event.setErrorMessage("Job Found");
			return event;
		}
		catch(Exception ex)
		{
			Event event = new Event();
			ex.printStackTrace();
			event.setErrorCode("404");
			event.setErrorMessage("Job Not Found");
			return null;
		}
	}

	@Transactional
	public List<Event> listEvents()
	{
		@SuppressWarnings("unchecked")
		String hql_string = "FROM Event";
		Query query = sessionFactory.getCurrentSession().createQuery(hql_string);
		return query.list();
	}

	@Transactional
	public boolean deleteEvent(int id)
	{
		try{
			Session session = sessionFactory.getCurrentSession();
	        Query query = session.createQuery("delete from Event where id = " +id);
			return query.executeUpdate()==1 ? true : false;
		}
		catch(HibernateException ex){
			log.debug("Data delete Error :" + ex.getMessage());
			ex.printStackTrace();
			return false;
		}
	}

	@Transactional
	public boolean updateEvent(Event event) 
	{
		try
		{
			sessionFactory.getCurrentSession().update(event);
			return true;
		}
		catch(HibernateException e){
			e.printStackTrace();
		return false;
		}
	}

}
