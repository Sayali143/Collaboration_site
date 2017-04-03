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

import com.niit.collaboration.DAO.JobDAO;
import com.niit.collaboration.model.ApplyJob;
import com.niit.collaboration.model.Job;

@SuppressWarnings("deprecation")
@Repository("jobDAO")
@EnableTransactionManagement
public class JobDAOImpl implements JobDAO
{
	private static final Logger log = LoggerFactory.getLogger(JobDAOImpl.class);

	@Autowired(required = true)
	private SessionFactory sessionFactory;
	
	public JobDAOImpl(SessionFactory sessionFactory)
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
	public boolean addJob(Job job)
	{
		try{
			sessionFactory.getCurrentSession().save(job);
			return true;
		}
		catch(HibernateException e){
			e.printStackTrace();
		return false;
		}
	}

	/*@Transactional
	public List<Job> getJobByPosition(String position) 
	{
		String hql_string = "FROM Job where position='position'";
		Query query = sessionFactory.getCurrentSession().createQuery(hql_string);
		return query.list();
	}*/

	@Transactional
	public Job getJobById(int job_id)
	{
		log.debug("Starting of Method Get");
		try
		{
			Job job = sessionFactory.getCurrentSession().get(Job.class,job_id);
			job.setErrorCode("200");
			job.setErrorMessage("Job Found");
			return job;
		}
		catch(Exception ex)
		{
			Job job = new Job();
			ex.printStackTrace();
			job.setErrorCode("404");
			job.setErrorMessage("Job Not Found");
			return null;
		}
	}

	@Transactional
	public List<Job> listJobs()
	{
		@SuppressWarnings("unchecked")
		String hql_string = "FROM Job";
		Query query = sessionFactory.getCurrentSession().createQuery(hql_string);
		return query.list();
	}

	@Transactional
	public boolean deleteJob(int job_id)
	{
		try{
			Session session = sessionFactory.getCurrentSession();
	        Query query = session.createQuery("delete from Job where job_id = " +job_id);
			return query.executeUpdate()==1 ? true : false;
		}
		catch(HibernateException ex){
			log.debug("Data delete Error :" + ex.getMessage());
			ex.printStackTrace();
			return false;
		}
	}

	@Transactional
	public boolean updateJob(Job job)
	{
		try
		{
			sessionFactory.getCurrentSession().update(job);
			return true;
		}
		catch(HibernateException e){
			e.printStackTrace();
		return false;
		}
	}
	
	@Transactional
	public boolean saveapplyJob(ApplyJob applyjob)
	{
		try
		{
			sessionFactory.getCurrentSession().save(applyjob);
			return true;
		}
		catch(HibernateException e){
			e.printStackTrace();
		return false;
		}
	}

	@Transactional
	public boolean updateJobApply(ApplyJob applyjob)
	{
		try
		{
			sessionFactory.getCurrentSession().update(applyjob);
			return true;
		}
		catch(HibernateException e){
			e.printStackTrace();
		return false;
		}
	}

	@Transactional
	public List<ApplyJob> getMyAppliedJobs(String username)
	{
		try
		{
			String hql = "From ApplyJob where username = '"+username+"'";
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			log.info("List displyed");
			return query.list();
		} catch(Exception ex)
		{
			log.error("Error found fetching List");
			ex.printStackTrace();
			return null;
		}
	}

	@Transactional
	public boolean deleteApplyJob(int id)
	{
		try{
			Session session = sessionFactory.getCurrentSession();
	        Query query = session.createQuery("delete from ApplyJob where id = " + id);
			return query.executeUpdate()==1 ? true : false;
		}
		catch(HibernateException ex){
			log.debug("Data update Error :" + ex.getMessage());
			ex.printStackTrace();
			return false;
		}

	}

	@Transactional
	public ApplyJob getApplyJob(String username, int job_id) 
	{
		log.debug("Starting of the method getapplyjob");
		String hql = "from ApplyJob where username ='"+username+"' and job_id ='" +job_id+ "'";
		log.debug("get apply job hql" +hql);
		return (ApplyJob) sessionFactory.getCurrentSession().createQuery(hql).uniqueResult();
	}

}