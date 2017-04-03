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
@Table(name="C_JOB")
@Component
public class Job extends BaseDomain
{
	
	@Id
	@GenericGenerator(name="Job" , strategy ="increment")
	@GeneratedValue(generator="Job")
	@Column(name="JOB_ID")
	private int job_id;
	
	private String company;
	private String date_added;
	
	@Lob
	private String description;
	
	private String location;
	private String position;
	private String q_10;
	private String q_12;
	private String q_ug;
	
	@Column(name="package")
	private String salary;
	
	private String status;
	private String title;
	private String username;
	private int vacancy;
	
	
	public int getJob_id() {
		return job_id;
	}
	public void setJob_id(int job_id) {
		this.job_id = job_id;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getDate_added() {
		return date_added;
	}
	public void setDate_added(String date_added) {
		this.date_added = date_added;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getQ_10() {
		return q_10;
	}
	public void setQ_10(String q_10) {
		this.q_10 = q_10;
	}
	public String getQ_12() {
		return q_12;
	}
	public void setQ_12(String q_12) {
		this.q_12 = q_12;
	}
	public String getQ_ug() {
		return q_ug;
	}
	public void setQ_ug(String q_ug) {
		this.q_ug = q_ug;
	}
	public String getSalary() {
		return salary;
	}
	public void setSalary(String salary) {
		this.salary = salary;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getVacancy() {
		return vacancy;
	}
	public void setVacancy(int vacancy) {
		this.vacancy = vacancy;
	}
	
}
