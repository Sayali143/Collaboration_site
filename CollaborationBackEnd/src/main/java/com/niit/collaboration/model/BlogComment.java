package com.niit.collaboration.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Table(name="C_BLOGCOMMENT")
@Component
public class BlogComment extends BaseDomain
{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private int blog_id;
	
	@Lob
	private String blog_comment;
	
	private String postdate;
	private String username;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getBlog_id() {
		return blog_id;
	}
	public void setBlog_id(int blog_id) {
		this.blog_id = blog_id;
	}
	public String getBlog_comment() {
		return blog_comment;
	}
	public void setBlog_comment(String blog_comment) {
		this.blog_comment = blog_comment;
	}
	public String getPostdate() {
		return postdate;
	}
	public void setPostdate(String postdate) {
		this.postdate = postdate;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
}
