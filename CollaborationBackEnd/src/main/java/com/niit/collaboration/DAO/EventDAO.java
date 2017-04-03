package com.niit.collaboration.DAO;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.niit.collaboration.model.Event;

@Repository("eventDAO")
public interface EventDAO
{
	public boolean addEvent(Event event);
	
	public Event getEventById(int id);
	
	public List<Event> listEvents();
	
	public boolean deleteEvent(int id);
	
	public boolean updateEvent(Event event);

}
