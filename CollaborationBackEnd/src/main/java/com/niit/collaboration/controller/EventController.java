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

import com.niit.collaboration.DAO.EventDAO;
import com.niit.collaboration.model.Event;
import com.niit.collaboration.util.DATE_TIME;


@RestController
public class EventController
{

	@Autowired
	private EventDAO eventDAO;

	@Autowired
	private Event event;
	
	@Autowired
	private HttpSession session;
	
	@PostMapping("/addEvent")//done
	public ResponseEntity<Event> addEvent(@RequestBody Event event)
	{	
		DATE_TIME dt = new DATE_TIME();
		String date = dt.getDateTime();
		System.out.println("Event date occure date"+date);
		event.setEvent_date(date);
		String loggedInUserID = (String) session.getAttribute("loggedInUserID");
		event.setUsername(loggedInUserID);
		boolean flag = eventDAO.addEvent(event);
		if(!flag)
		{
			event.setErrorCode("404");
			event.setErrorMessage("Event not added");
			return new ResponseEntity<Event>(event, HttpStatus.CONFLICT);
		}
		else
		{
			event.setErrorCode("200");
			event.setErrorMessage("Event has been added");
			return new ResponseEntity<Event>(event, HttpStatus.OK);
		}
	}

	@GetMapping("/getEventById-{event_id}")//done
	public ResponseEntity<Event> getEventById(@PathVariable("event_id") int event_id)
	{
		Event event = eventDAO.getEventById(event_id);
		if(event == null)
		{
			event.setErrorCode("100");
			event.setErrorMessage("event not available");
		}
		else
		{
			event.setErrorCode("200");
			event.setErrorMessage("event found");
			return new ResponseEntity<Event>(event,HttpStatus.OK);
		}
		return null;
	}
	
	@GetMapping("/listEvents")//done
	public ResponseEntity<List<Event>> listEvents()
	{
		List<Event> list = eventDAO.listEvents();
		event.setErrorCode("200");
		event.setErrorMessage("Success");
		return new ResponseEntity<List<Event>>(list, HttpStatus.OK);
	}
	
	@PostMapping("/deleteEvent-{id}")//done
	public ResponseEntity<Event> deleteEvent(@PathVariable("id") int id)
	{
		Event event = eventDAO.getEventById(id);
		boolean flag = eventDAO.deleteEvent(id);
		if(!flag)
		{
			event.setErrorCode("100");
			event.setErrorMessage("blog not deleted");
			return new ResponseEntity<Event>(event,HttpStatus.BAD_REQUEST);
		}
		else
		{						
			event.setErrorCode("200");
			event.setErrorMessage("Success");
			return new ResponseEntity<Event>(event, HttpStatus.OK);	
		}
	}
	
	@PostMapping("/updateEvent")//done
	public ResponseEntity<Event> updateEvent(@RequestBody Event event)
	{	
		DATE_TIME dt = new DATE_TIME();
		String date = dt.getDateTime();
		event.setEvent_date(date);
		
		boolean flag = eventDAO.updateEvent(event);
		
		if(!flag)
		{
			event.setErrorCode("404");
			event.setErrorMessage("event not added");
			return new ResponseEntity<Event>(event, HttpStatus.CONFLICT);
			
		}
		else
		{
			event.setErrorCode("100");
			event.setErrorMessage("event has been added");
			return new ResponseEntity<Event>(event, HttpStatus.OK);
		}
	}
}
