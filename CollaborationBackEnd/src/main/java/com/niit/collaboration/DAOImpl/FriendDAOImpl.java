package com.niit.collaboration.DAOImpl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.niit.collaboration.DAO.FriendDAO;
import com.niit.collaboration.model.Friend;

@Repository("friendDAO")
@EnableTransactionManagement
public class FriendDAOImpl implements FriendDAO
{

	private static final Logger log = LoggerFactory.getLogger(FriendDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	public FriendDAOImpl(SessionFactory sessionFactory)
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

	private Integer getMaxId()
	{
		log.debug("->->Starting of the method getMaxId");

		String hql = "select max(id) from Friend";
		Query query = sessionFactory.openSession().createQuery(hql);
		Integer maxID;
		try 
		{
			maxID = (Integer) query.uniqueResult();
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 100;
		}
		log.debug("Max id :" + maxID);
		return maxID;

	}

	@Transactional
	public boolean save(Friend friend)
	{

		try {
			log.debug("*********************88Previous id " + getMaxId());
			friend.setId(getMaxId() + 1);
			log.debug("***********************generated id:" + getMaxId());
			sessionFactory.getCurrentSession().save(friend);
			return true;
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	@Transactional
	public boolean update(Friend friend) 
	{

		try {
			log.debug("Starting of the method update");
			log.debug("user ID : " + friend.getUsername() + " Friend id :" + friend.getFriendid());
			sessionFactory.getCurrentSession().update(friend);
			log.debug("Successfully updated");
			return true;
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.debug("Not able to update the status");
			return false;
		}

	}

	@Transactional
	public void delete(String username, String friendid) 
	{
		Friend friend = new Friend();
		friend.setFriendid(friendid);
		friend.setUsername(username);
		sessionFactory.openSession().delete(friend);
	}
	
	@Transactional
	public List<Friend> getMyFriends(String username)
	{
		String hql="from Friend where username='"+username+"' and status='A'";
		String hql2="from Friend where friendid='"+username+"' and status='A'";
		log.debug("getMyFriends hql= "+hql);
		log.debug("getMyFriends hql2= "+hql2);
        Query query=sessionFactory.openSession().createQuery(hql);
        Query query2=sessionFactory.openSession().createQuery(hql2);
        List<Friend> list=(List<Friend>)query.list();
        List<Friend> list2=(List<Friend>)query2.list();
        list.addAll(list2);
        return list;
	}

	@Transactional
	public List<Friend> getNewFriendRequests(String friendid)
	{
		String hql = "from Friend where username='" +friendid+ "' and status ='N'";
		log.debug("getNewFriendRequestsSendByMe hql : " + hql);
		Query query = sessionFactory.openSession().createQuery(hql);
		List<Friend> list = (List<Friend>)query.list();
		return list;
	}
	
	@Transactional
	public List<Friend> getRequestsSendByMe(String username) 
	{
		String hql = "from Friend where friendid='" +username+ "' and status ='N'";
		log.debug("getRequestsSendByMe hql : " + hql);
		Query query = sessionFactory.openSession().createQuery(hql);
		List<Friend> list = (List<Friend>)query.list();
		return list;
	}

	@Transactional
	public Friend get(String username, String friendid) 
	{
		String hql="from Friend where username='"+username+"' and friendid='"+friendid+"'";
		log.debug(" two parameter method hql=" +hql);
		Query query=sessionFactory.openSession().createQuery(hql);
		return (Friend) query.uniqueResult();
	}
	
	@Transactional
	public Friend get(String username)
	{
		String hql = "from Friend where username=" + "'" + username + "' and friendid= '" + username + "'";

		log.debug("one paramter method hql: " + hql);
		Query query = sessionFactory.openSession().createQuery(hql);

		return (Friend) query.uniqueResult();

	}

	@Transactional
	public void setOnline(String friendid)
	{
		log.debug("Starting of the method setOnline");
		String hql="UPDATE Friend SET isOnline='Y' where friendid='" +friendid+"'";
		log.debug("hql: " +hql);
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		query.executeUpdate();
	}

	@Transactional
	public void setOffLine(String friendid)
	{
		log.debug("Starting of the method setOnline");
		String hql="UPDATE Friend SET isOnline='N' where friendid='" +friendid+"'";
		log.debug("hql: " +hql);
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		query.executeUpdate();
	}
}
