package com.niit.collaboration.DAO;

import java.util.List;

import com.niit.collaboration.model.ApplyJob;
import com.niit.collaboration.model.Job;

public interface JobDAO 
{
	public boolean addJob(Job job);
	
	/*public List<Job> getJobByPosition(String position);*/
	
	public Job getJobById(int job_id);
	
	public List<Job> listJobs();
	
	public boolean deleteJob(int job_id);
	
	public boolean updateJob(Job job);
	
	public boolean saveapplyJob(ApplyJob applyjob);
	
	public boolean updateJobApply(ApplyJob applyjob);
	
	public List<ApplyJob> getMyAppliedJobs(String username);
	
	public ApplyJob getApplyJob(String username, int job_id);
	
	public boolean deleteApplyJob(int id);
}
	