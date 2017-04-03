package com.niit.collaboration.DAO;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.niit.collaboration.model.User;

@Repository("userDAO")
public interface UserDAO 
{
	
	public List<User> getAllUsers();

	public User get(String username);

	public boolean save(User user);

	public boolean update(User user);

	public void delete(String username);

	public User authenticate(String username, String name);

	public void setOnline(String username);
	
	public void setOfline(String username);

}