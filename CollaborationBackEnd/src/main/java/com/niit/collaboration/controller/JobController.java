package com.niit.collaboration.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.niit.collaboration.DAO.JobDAO;
import com.niit.collaboration.model.ApplyJob;
import com.niit.collaboration.model.Blog;
import com.niit.collaboration.model.BlogComment;
import com.niit.collaboration.model.Job;
import com.niit.collaboration.util.DATE_TIME;


@RestController
public class JobController
{
	Logger log = LoggerFactory.getLogger(JobController.class);
	
	@Autowired
	private JobDAO jobDAO;

	@Autowired
	private Job job;
	
	@Autowired
	private ApplyJob applyjob;
	
	@Autowired
	private HttpSession session;
	
	@PostMapping("/addJob")//done
	public ResponseEntity<Job> addJob(@RequestBody Job job)
	{	
		DATE_TIME dt = new DATE_TIME();
		String date = dt.getDateTime();
		job.setDate_added(date);
		job.setStatus("Y");// V-vacant , F-Filled , P- pending
		
		boolean flag = jobDAO.addJob(job);
		if(!flag)
		{
			job.setErrorCode("404");
			job.setErrorMessage("Blog not added");
			return new ResponseEntity<Job>(job, HttpStatus.CONFLICT);
		}
		else
		{
			job.setErrorCode("200");
			job.setErrorMessage("Blog has been added");
		}
		return new ResponseEntity<Job>(job, HttpStatus.OK);
	}

	/*@GetMapping("/getJobByPosition-{position}")//error
	public ResponseEntity<List<Job>>getJobByPosition(@PathVariable("position") String position)
	{
		job.setErrorCode("200");
		job.setErrorMessage("Success");
		List<Job> list = jobDAO.getJobByPosition(position);
		return new ResponseEntity<List<Job>>(list, HttpStatus.OK);
	}*/
	
	@GetMapping("/getJobById-{job_id}")//done
	public ResponseEntity<Job> getJobById(@PathVariable("job_id") int job_id)
	{
		Job job = jobDAO.getJobById(job_id);
		if(job == null)
		{
			job.setErrorCode("100");
			job.setErrorMessage("job not available");
		}
		else
		{
			job.setErrorCode("200");
			job.setErrorMessage("job found");
			return new ResponseEntity<Job>(job,HttpStatus.OK);
		}
		return null;
	}
	
	@GetMapping("/listJobs")//done
	public ResponseEntity<List<Job>> listJobs()
	{
		List<Job> list = jobDAO.listJobs();
		job.setErrorCode("200");
		job.setErrorMessage("Success");
		return new ResponseEntity<List<Job>>(list, HttpStatus.OK);
	}
	
	@PostMapping("/deleteJob-{job_id}")//done
	public ResponseEntity<Job> deleteJob(@PathVariable("job_id") int job_id)
	{
		Job job = jobDAO.getJobById(job_id);
		boolean flag = jobDAO.deleteJob(job_id);
		if(!flag)
		{
			job.setErrorCode("100");
			job.setErrorMessage("blog not deleted");
			return new ResponseEntity<Job>(job,HttpStatus.BAD_REQUEST);
		}
		else
		{						
			job.setErrorCode("200");
			job.setErrorMessage("Success");
			return new ResponseEntity<Job>(job, HttpStatus.OK);	
		}
	}
	
	@PostMapping("/updateJob")//done
	public ResponseEntity<Job> updateJob(@RequestBody Job job)
	{	
		DATE_TIME dt = new DATE_TIME();
		String date = dt.getDateTime();
		job.setDate_added(date);
		job.setStatus("Y");
		
		boolean flag = jobDAO.updateJob(job);
		
		if(!flag)
		{
			job.setErrorCode("404");
			job.setErrorMessage("job not added");
			return new ResponseEntity<Job>(job, HttpStatus.CONFLICT);
			
		}
		else
		{
			job.setErrorCode("100");
			job.setErrorMessage("job has been added");
			return new ResponseEntity<Job>(job, HttpStatus.OK);
		}
	}
	
	@PostMapping("/applyJob-{job_id}")//done
	public ResponseEntity<ApplyJob> applyJob(@PathVariable("job_id") int job_id)
	{	
		log.debug("In apply job");
		String loggedInUserID = (String) session.getAttribute("loggedInUserID");
		DATE_TIME dt = new DATE_TIME();
		String date = dt.getDateTime();
		applyjob.setDate_time(date);
		if (loggedInUserID == null || loggedInUserID.isEmpty())
		{
			log.debug("In apply job check username empty");
			applyjob.setErrorCode("404");
			applyjob.setErrorMessage("You have to login to apply for the  job");
		} 
		else
		{
			 if(isUserAppliedForTheJob(loggedInUserID , job_id)== false)
			 {
				 log.debug("In apply job set username");
				 applyjob.setJob_id(job_id);
				 applyjob.setUsername(loggedInUserID);
				 applyjob.setStatus("N");// N-newly applied; , C-> Call for
											// Interview, S- Selected
					if (jobDAO.saveapplyJob(applyjob))
					{
						log.debug("In apply job save");
						applyjob.setErrorCode("200");
						applyjob.setErrorMessage("You have successfully applied for the Job :" + job_id);
					}
			}  
			else 
			{
				log.debug("In apply job error");
				applyjob.setErrorCode("404");
				applyjob.setErrorMessage("You already applied for the job" + job_id);
			}
		}
		return new ResponseEntity<ApplyJob>(applyjob, HttpStatus.OK);
	}
	
	private boolean isUserAppliedForTheJob(String username, int job_id)
	{
		if (jobDAO.getApplyJob(username, job_id) == null)
		{
			return false;
		}

		return true;
	}

	@PostMapping("/updateJobApply")//done
	public ResponseEntity<ApplyJob> updateJobApply(@RequestBody ApplyJob applyjob)
	{	
		DATE_TIME dt = new DATE_TIME();
		String date = dt.getDateTime();
		applyjob.setDate_time(date);
		applyjob.setStatus("Y");
		
		boolean flag = jobDAO.updateJobApply(applyjob);
		
		if(!flag)
		{
			applyjob.setErrorCode("404");
			applyjob.setErrorMessage("job not added");
			return new ResponseEntity<ApplyJob>(applyjob, HttpStatus.CONFLICT);
		}
		else
		{
			applyjob.setErrorCode("100");
			applyjob.setErrorMessage("job has been added");
			return new ResponseEntity<ApplyJob>(applyjob, HttpStatus.OK);
		}
	}

	@GetMapping("/getMyAppliedJobs")//done
	public ResponseEntity<List<ApplyJob>> getMyAppliedJobs()
	{
		String loggedInUserID = (String)session.getAttribute("loggedInUserID");
		log.info("retriving Jobs by usersname");
		List<ApplyJob> jobs = new ArrayList<ApplyJob>();
		if (loggedInUserID == null || loggedInUserID.isEmpty()) 
		{
			log.info("Job list seems to be empty");
			applyjob = new ApplyJob();
			applyjob.setErrorCode("400");
			applyjob.setErrorMessage("Seems like you have not enrolled for any job");
			jobs.add(applyjob);
		}
		else
		{
				log.info("Job list has been found");
				applyjob.setErrorCode("200");
				applyjob.setErrorMessage("You have applied for this Job");
				jobs = jobDAO.getMyAppliedJobs(loggedInUserID);
		}
		return new ResponseEntity<List<ApplyJob>> (jobs, HttpStatus.OK);	
	}
	
	@PostMapping("/deleteApplyJob-{id}")//done
	public ResponseEntity<ApplyJob> deleteApplyJob(@PathVariable("id") int id)
	{
		boolean flag = jobDAO.deleteApplyJob(id);
		if(!flag)
		{
			applyjob.setErrorCode("404");
			applyjob.setErrorMessage("Job not deleted");
			return new ResponseEntity<ApplyJob>(applyjob, HttpStatus.CONFLICT);
		}
		else
		{
			applyjob.setErrorCode("200");
			applyjob.setErrorMessage("Job has been deleted");
			return new ResponseEntity<ApplyJob>(applyjob, HttpStatus.OK);
		}
	}
}