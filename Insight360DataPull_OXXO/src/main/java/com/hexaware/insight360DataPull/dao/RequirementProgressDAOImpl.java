package com.hexaware.insight360DataPull.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.hexaware.insight360DataPull.model.RequirementProgress;

public class RequirementProgressDAOImpl implements RequirementProgressDAO {
	
private SessionFactory sessionFactory;
    
    private static final Logger logger = Logger.getLogger(RequirementProgressDAOImpl.class);
		
	public RequirementProgressDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
	
	@Override
	@Transactional
	public int getTotalTCs(int RequirementID, int ReleaseID,String Phase) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Long> caseList = (List<Long>)session.createQuery("select sum(R.num_of_test_cases) from RequirementProgress R where R.req_id = '"+ RequirementID + "' and R.req_release_id = '"+ ReleaseID +"' and R.test_phase = '"+ Phase +"'").list();
		if(caseList.get(0) == null)
			return 0;
		else
			return caseList.get(0).intValue();
	}

	@Override
	@Transactional
	public int getPassedTCs(int RequirementID, int ReleaseID,String Phase) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Long> caseList = (List<Long>)session.createQuery("select sum(R.tcs_passed_first_run) from RequirementProgress R where R.req_id = '"+ RequirementID + "' and R.req_release_id = '"+ ReleaseID +"' and R.test_phase = '"+ Phase +"'").list();
		if(caseList.get(0) == null)
			return 0;
		else
			return caseList.get(0).intValue();
	}
	
	@Override
	@Transactional
	public int getPassedTCsForTrendsHistory(int ReleaseID) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Long> caseList = (List<Long>)session.createQuery("select sum(R.tcs_passed_first_run) from RequirementProgress R where R.req_release_id = '"+ ReleaseID + "'").list();
		if(caseList.get(0) == null)
			return 0;
		else
			return caseList.get(0).intValue();
	}
	
	@Override
	@Transactional
	public int getTotalTCs(int ReleaseID) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Long> caseList = (List<Long>)session.createQuery("select sum(R.num_of_test_cases) from RequirementProgress R where R.req_release_id = '"+ ReleaseID + "'").list();
		if(caseList.get(0) == null)
			return 0;
		else
			return caseList.get(0).intValue();
	}
	
	@Override
	@Transactional
	public int getFailedTCsForTrendsHistory(int ReleaseID) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Long> caseList = (List<Long>)session.createQuery("select sum(R.tcs_failed_first_run) from RequirementProgress R where R.req_release_id = '"+ ReleaseID + "'").list();
		if(caseList.get(0) == null)
			return 0;
		else
			return caseList.get(0).intValue();
	}
	
	@Override
	@Transactional
	public int getIPCaseCasesForTrendsHistory(int ReleaseID) {
        Session session = this.sessionFactory.getCurrentSession();
        @SuppressWarnings("unchecked")
        List<Long> caseList = (List<Long>)session.createQuery("select sum(R.tcs_passed_retest) from RequirementProgress R where R.req_release_id = '"+ ReleaseID + "'").list();
        if(caseList.get(0) == null)
            return 0;
        else
            return caseList.get(0).intValue();
	}

	@Override
	@Transactional
	public int getFailedTCs(int RequirementID, int ReleaseID,String Phase) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Long> caseList = (List<Long>)session.createQuery("select sum(R.tcs_failed_first_run) from RequirementProgress R where R.req_id = '"+ RequirementID + "' and R.req_release_id = '"+ ReleaseID +"' and R.test_phase = '"+ Phase +"'").list();
		if(caseList.get(0) == null)
			return 0;
		else
			return caseList.get(0).intValue();
	}

	@Override
	@Transactional
	public boolean createRequirement(RequirementProgress requirement_progress, String testing_phase) {
		// TODO Auto-generated method stub
		
        Session session = this.sessionFactory.getCurrentSession();
		
		@SuppressWarnings("unchecked")
		List<RequirementProgress> listRequirementProgress = (List<RequirementProgress>)session.createQuery("from RequirementProgress R where R.req_id = '"+ requirement_progress.getReq_id() + "' and R.req_release_id = '"+ requirement_progress.getReq_release_id() + "' and R.test_phase = '"+ requirement_progress.getTest_phase() +"'").list();
		
		if(listRequirementProgress.isEmpty()) {
			session.save(requirement_progress);
			return true;
		}
		else {
			return false;
		}
		
	}

	@Override
	@Transactional
	public boolean updateRequirement(RequirementProgress requirement_progress) {
		
		// TODO Auto-generated method stub
		
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<RequirementProgress> listRequirementProgress = (List<RequirementProgress>)session.createQuery("from RequirementProgress R where R.req_id = '"+ requirement_progress.getReq_id() + "' and R.req_release_id = '"+ requirement_progress.getReq_release_id() +"' and R.test_phase = '"+ requirement_progress.getTest_phase() +"'").list();
		if(listRequirementProgress.isEmpty()) {
			return false;
		}
		else {
			RequirementProgress requirementToUpdate = listRequirementProgress.get(0);
			requirementToUpdate.setReq_id(requirement_progress.getReq_id());
			requirementToUpdate.setReq_release_id(requirement_progress.getReq_release_id());
			requirementToUpdate.setTest_phase(requirement_progress.getTest_phase());
			if(requirementToUpdate.getOrig_cert_date() == null)
	            requirementToUpdate.setOrig_cert_date(requirement_progress.getOrig_cert_date());
			requirementToUpdate.setTimestamp(requirement_progress.getTimestamp());
			requirementToUpdate.setNum_of_test_cases(requirement_progress.getNum_of_test_cases());
	            requirementToUpdate.setTcs_with_iterations(requirement_progress.getTcs_with_iterations());
	            
	            requirementToUpdate.setPlanned_execution_start(requirement_progress.getPlanned_execution_start());
	            requirementToUpdate.setPlanned_execution_end(requirement_progress.getPlanned_execution_end());
	            requirementToUpdate.setActual_execution_end(requirement_progress.getActual_execution_end());
	            requirementToUpdate.setActual_execution_start(requirement_progress.getActual_execution_start());
	            requirementToUpdate.setActual_dev_certification(requirement_progress.getActual_dev_certification());
	            requirementToUpdate.setPlanned_dev_certification(requirement_progress.getPlanned_dev_certification());
	            requirementToUpdate.setTcs_passed_first_run(requirement_progress.getTcs_passed_first_run());
	            requirementToUpdate.setTcs_failed_first_run(requirement_progress.getTcs_failed_first_run());
	            requirementToUpdate.setDev_failures(requirement_progress.getDev_failures());
	            requirementToUpdate.setTsc_pending_fun_request(requirement_progress.getTsc_pending_fun_request());
	            requirementToUpdate.setTcs_pending(requirement_progress.getTcs_pending());
	            requirementToUpdate.setTcs_passed_retest(requirement_progress.getTcs_passed_retest());
	            requirementToUpdate.setDirect_coverage(requirement_progress.getDirect_coverage());
	            requirementToUpdate.setPlanning_estimate(requirement_progress.getPlanning_estimate());
	            requirementToUpdate.setExecution_estimate(requirement_progress.getExecution_estimate());
	            requirementToUpdate.setOrig_plnd_tcs_count(requirement_progress.getOrig_plnd_tcs_count());
	            requirementToUpdate.setRequirement_defn_percentage(requirement_progress.getRequirement_defn_percentage());
	            requirementToUpdate.setUx_progress_percentage(requirement_progress.getUx_progress_percentage());
	            requirementToUpdate.setReq_progress_module(requirement_progress.getReq_progress_module());
	            
				session.update(requirementToUpdate);
				return true;
			
		}
		
		
	
	}

	@Override
	@Transactional
	public RequirementProgress getRequirement(int requirementID, int releaseID,String Phase) {
		// TODO Auto-generated method stub
		
		Session session = this.sessionFactory.getCurrentSession();
    	@SuppressWarnings("unchecked")
		List<RequirementProgress> listRequirement = (List<RequirementProgress>)session.createQuery("from RequirementProgress R where R.req_id = '"+ requirementID +"' and R.req_release_id = '"+ releaseID +"'and R.test_phase = '"+ Phase +"'").list();
    	
    	return listRequirement.isEmpty() ? null : listRequirement.get(0);
		
	}
	
	@Override
	@Transactional
	public List<RequirementProgress> getRequirementListForReleaseByScope(int releaseId) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<RequirementProgress> listRequirement = (List<RequirementProgress>)session.createQuery("from RequirementProgress R where R.req_release_id = '"+ releaseId + "' and R.in_scope !='N' ").list();
		return listRequirement;
	}

	@Override
	public boolean deleteRequirement(int RequirementID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public RequirementProgress getRequirement(int requirementEntityID) {
		// TODO Auto-generated method stub
		return null;
	}

}
