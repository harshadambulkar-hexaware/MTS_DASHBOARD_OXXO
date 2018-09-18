package com.hexaware.insight360DataPull.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.hexaware.insight360DataPull.model.Release;

public class ReleaseDAOImpl implements ReleaseDAO {
	
	private SessionFactory sessionFactory;
    
    private static final Logger logger = Logger.getLogger(ReleaseDAOImpl.class);
		
	public ReleaseDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

	@Override
	@Transactional
	public List<Release> ReleaseList() {
		@SuppressWarnings("unchecked")
        List<Release> listRelease = (List<Release>) sessionFactory.getCurrentSession()
                .createCriteria(Release.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
 
        return listRelease;
	}


	@Override
	@Transactional
	public Release getRelease(int releaseID) {
		Session session = this.sessionFactory.getCurrentSession();
    	@SuppressWarnings("unchecked")
		List<Release> listRelease = (List<Release>)session.createQuery("from Release R where R.release_entity_id = '"+ releaseID +"'").list();
    	
    	return listRelease.isEmpty() ? null : listRelease.get(0);
	}
	
	@Override
	@Transactional
	public Release getRelease(String releaseName, int projectID) {
		Session session = this.sessionFactory.getCurrentSession();
    	@SuppressWarnings("unchecked")
    	List<Release> listRelease = (List<Release>)session.createQuery("from Release R where R.release_name = '"+ releaseName + "' and R.release_project_id = " + projectID + "").list();
    	
    	return listRelease.isEmpty() ? null : listRelease.get(0);
	}

	
	@Override
	@Transactional
	public boolean createRelease(Release release) {
		Session session = this.sessionFactory.getCurrentSession();
		logger.info("createRelease method started");
		@SuppressWarnings("unchecked")
		List<Release> listRelease = (List<Release>)session.createQuery("from Release R where R.release_name = '"+ release.getRelease_name() + "' and R.release_project_id = '" + release.getRelease_project_id() + "'").list();
		
		if(listRelease.isEmpty()) {
			session.save(release);
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	@Transactional
	public boolean deleteRelease(int releaseID) {
		System.out.println(releaseID);
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Release> listRelease = (List<Release>)session.createQuery("from Release R where R.release_entity_id = '"+ releaseID +"'").list();
		if(listRelease.isEmpty()) {
			return false;
		}
		else {
			session.delete(listRelease.get(0));
			return true;
		}
	}

	@Override
	@Transactional
	public boolean updateRelease(Release release) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Release> listRelease = (List<Release>)session.createQuery("from Release R where R.release_entity_id = '"+ release.getRelease_entity_id() + "'").list();
		if(listRelease.isEmpty()) {
			return false;
		}
		else {
			Release releaseToUpdate = listRelease.get(0);
			releaseToUpdate.setGo_live_date(release.getGo_live_date());
			releaseToUpdate.setIst_planned_start(release.getIst_planned_start());
			releaseToUpdate.setIst_planned_end(release.getIst_planned_end());
			releaseToUpdate.setIst_actual_start(release.getIst_actual_start());
			releaseToUpdate.setIst_actual_end(release.getIst_actual_end());
			releaseToUpdate.setIst_first_report_date(release.getIst_first_report_date());
			releaseToUpdate.setIst_last_report_date(release.getIst_last_report_date());
			releaseToUpdate.setIs_active(release.getIs_active());
			releaseToUpdate.setIst_health(release.getIst_health());		 
			return true;
		}
	}
	
	@Override
	@Transactional
	public boolean updatePullRelease(Release release) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Release> listRelease = (List<Release>)session.createQuery("from Release R where R.release_project_id = '"+ release.getRelease_project_id() + "' and R.release_id = '"+ release.getRelease_id() + "'").list();
		if(listRelease.isEmpty()) {
			return false;
		}
		else {
			Release releaseToUpdate = listRelease.get(0);
			releaseToUpdate.setGo_live_date(release.getGo_live_date());
			releaseToUpdate.setIst_planned_start(release.getIst_planned_start());
			releaseToUpdate.setIst_planned_end(release.getIst_planned_end());
			releaseToUpdate.setUat_planned_start(release.getUat_planned_start());
			releaseToUpdate.setUat_planned_end(release.getUat_planned_end());
			releaseToUpdate.setIst_actual_start(release.getIst_actual_start());
			releaseToUpdate.setIst_actual_end(release.getIst_actual_end());
			releaseToUpdate.setIst_first_report_date(release.getIst_first_report_date());
			releaseToUpdate.setIst_last_report_date(release.getIst_last_report_date());
			releaseToUpdate.setPhase(release.getPhase());
			// Below commented field values comes directly from UI
			//releaseToUpdate.setIs_active(release.getIs_active());
			//releaseToUpdate.setIst_health(release.getIst_health());		 
			return true;
		}
	}
	
	@Override
	@Transactional
	public List<Release> getReleaseListForProject(int projectID) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Release> listRelease = (List<Release>)session.createQuery("from Release R where R.release_project_id = '"+ projectID + "'").list();
		
		return listRelease;
	}


	@Override
	@Transactional
	public List<Release> getActiveReleaseListForProject(int projectID) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Release> listRelease = (List<Release>)session.createQuery("from Release R where R.is_active = 1 and R.release_project_id = '"+ projectID + "'").list();
		
		return listRelease;
	}
}
