package com.niit.collaboration.DAO;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.niit.collaboration.model.Friend;

@Repository("friendDAO")
public interface FriendDAO 
{
	
	public boolean save(Friend friend);
	
	public boolean update(Friend friend);
	
	public void delete(String username, String friendid);
	
	public List<Friend> getMyFriends(String username);
	
	public List<Friend> getNewFriendRequests(String friendid);
	
	public List<Friend> getRequestsSendByMe(String username);
	
	public Friend get(String username, String friendid);
	
	public Friend get(String username);
	
	public void setOnline(String friendid);
	
	public void setOffLine(String friendid);	
	
}
