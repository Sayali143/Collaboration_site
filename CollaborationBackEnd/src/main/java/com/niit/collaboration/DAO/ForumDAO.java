package com.niit.collaboration.DAO;

import java.util.List;

import com.niit.collaboration.model.Forum;
import com.niit.collaboration.model.ForumComment;

public interface ForumDAO
{

	public Forum getForum(int forum_id);
	
	public List<Forum> getAllApprovedForums();
	
	public Forum getAllForums(int forum_id);
	
	public List<Forum> admingetAllForums();
	
	public boolean addForum(Forum forum);
	
	public boolean updateForum(Forum forum);
	
	public boolean deleteForum(int forum_id);
	
	public boolean approveForum(int forum_id, char flag);
	
	public boolean rejectForum(int forum_id, char flag);
	
	
	
	public boolean addForumComment(ForumComment forumcomment);
	
	public List<ForumComment> getAllForumComment(int forum_id);
	
	public ForumComment getForumCommentByID(int id);
	
	public boolean getDeleteForumComment(ForumComment forumcomment);
	
	
}
