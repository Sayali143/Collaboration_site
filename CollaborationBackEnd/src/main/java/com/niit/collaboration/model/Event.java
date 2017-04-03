package com.niit.collaboration.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.stereotype.Component;

@Entity
@Table(name="C_EVENT")
@Component
public class Event extends BaseDomain
{
	
	@Id
	@GenericGenerator(name="Event" , strategy ="increment")
	@GeneratedValue(generator="Event")
	@Column(name="ID")
	private int id;
	
	@Lob
	private String description;
	
	private String name;
	private String username;
	private String time;
	private String venue;
	private String event_date;
	private String on_date;
	
	public String getOn_date() {
		return on_date;
	}
	public void setOn_date(String on_date) {
		this.on_date = on_date;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEvent_date() {
		return event_date;
	}
	public void setEvent_date(String event_date) {
		this.event_date = event_date;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getVenue() {
		return venue;
	}
	public void setVenue(String venue) {
		this.venue = venue;
	}
	
}
