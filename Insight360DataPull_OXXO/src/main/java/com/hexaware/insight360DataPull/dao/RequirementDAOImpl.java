package com.hexaware.insight360DataPull.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.hexaware.insight360DataPull.model.Requirement;

public class RequirementDAOImpl implements RequirementDAO {
	
	private SessionFactory sessionFactory;
    
    private static final Logger logger = Logger.getLogger(RequirementDAOImpl.class);
		
	public RequirementDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

	@Override
	@Transactional
	public List<Requirement> RequirementList() {
		@SuppressWarnings("unchecked")
        List<Requirement> listRequirement = (List<Requirement>) sessionFactory.getCurrentSession()
                .createCriteria(Requirement.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
 
        return listRequirement;
	}

	
	@Override
	@Transactional
	public Requirement getRequirement(int requirementEntityID) {
		Session session = this.sessionFactory.getCurrentSession();
    	@SuppressWarnings("unchecked")
		List<Requirement> listRequirement = (List<Requirement>)session.createQuery("from Requirement R where R.requirement_entity_id = '"+ requirementEntityID +"'").list();
    	
    	return listRequirement.isEmpty() ? null : listRequirement.get(0);
	}
	
	@Override
	@Transactional
	public Requirement getRequirement(int RequirementID, int releaseID) {
		Session session = this.sessionFactory.getCurrentSession();
    	@SuppressWarnings("unchecked")
		List<Requirement> listRequirement = (List<Requirement>)session.createQuery("from Requirement R where R.req_id = '"+ RequirementID +"' and R.req_release_id = '"+ releaseID +"'").list();
    	
    	return listRequirement.isEmpty() ? null : listRequirement.get(0);
	}

	@Override
	@Transactional
	public boolean createRequirement(Requirement requirement) {
		Session session = this.sessionFactory.getCurrentSession();
		
		@SuppressWarnings("unchecked")
		List<Requirement> listRequirement = (List<Requirement>)session.createQuery("from Requirement R where R.req_id = '"+ requirement.getReq_id() + "' and R.req_release_id = '"+ requirement.getReq_release_id() + "'").list();
		
		if(listRequirement.isEmpty()) {
			session.save(requirement);
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	@Transactional
	public boolean deleteRequirement(int RequirementID) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Requirement> listRequirement = (List<Requirement>)session.createQuery("from Requirement R where R.requirement_entity_id = '"+ RequirementID +"'").list();
		if(listRequirement.isEmpty()) {
			return false;
		}
		else {
			session.delete(listRequirement.get(0));
			return true;
		}
	}

	@Override
	@Transactional
	public boolean updateRequirement(Requirement requirement) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Requirement> listRequirement = (List<Requirement>)session.createQuery("from Requirement R where R.req_id = '"+ requirement.getReq_id() + "' and R.req_release_id = '"+ requirement.getReq_release_id() +"'").list();
		if(listRequirement.isEmpty()) {
			return false;
		}
		else {
			
			Requirement requirementToUpdate = listRequirement.get(0);

			//logger.info("TC Count Req:" + requirementToUpdate.getReq_id() + " " + requirementToUpdate.getNum_of_test_cases() + " " +requirementToUpdate.getTcs_passed_first_run() +" "+ requirementToUpdate.getTcs_failed_first_run() + " " + requirementToUpdate.getTcs_passed_retest());
			
			requirementToUpdate.setReq_id(requirement.getReq_id());
            requirementToUpdate.setReq_release_id(requirement.getReq_release_id());
            requirementToUpdate.setTitle(requirement.getTitle());
            requirementToUpdate.setModule(requirement.getModule());
            requirementToUpdate.setDirect_coverage(requirement.getDirect_coverage());
            
          
			session.update(requirementToUpdate);
			return true;
		}
	}
	
	@Override
	@Transactional
	public List<Requirement> getRequirementListForRelease(int ReleaseID) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Requirement> listRequirement = (List<Requirement>)session.createQuery("from Requirement R where R.req_release_id = '"+ ReleaseID + "'").list();
		return listRequirement;
	}

	@Override
	@Transactional
	public List<Requirement> getCertifiedCR(int ReleaseID)	{
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Requirement> listCertifyCR = (List<Requirement>)session.createQuery("from Requirement R where R.req_release_id = '" + ReleaseID + "' and direct_coverage='PASSED'").list();
		return listCertifyCR;
	}

}
