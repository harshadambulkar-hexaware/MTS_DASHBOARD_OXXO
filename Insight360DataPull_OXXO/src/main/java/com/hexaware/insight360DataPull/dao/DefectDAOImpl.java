package com.hexaware.insight360DataPull.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.hexaware.insight360DataPull.HpAlmQc.AlmConstants;
import com.hexaware.insight360DataPull.model.Defect;
import com.hexaware.insight360DataPull.services.Constants;

public class DefectDAOImpl implements DefectDAO {

	private SessionFactory sessionFactory;
    
    @SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(DefectDAOImpl.class);
		
	public DefectDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
	
	@Override
	@Transactional
	public List<Defect> DefectList() {
		@SuppressWarnings("unchecked")
        List<Defect> listDefect = (List<Defect>) sessionFactory.getCurrentSession()
                .createCriteria(Defect.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
 
        return listDefect;
	}


	@Override
	@Transactional
	public Defect getDefect(int DefectID) {
		Session session = this.sessionFactory.getCurrentSession();
    	@SuppressWarnings("unchecked")
		List<Defect> listDefect = (List<Defect>)session.createQuery("from Defect D where D.defect_entity_id = '"+ DefectID +"'").list();
    	
    	return listDefect.isEmpty() ? null : listDefect.get(0);
	}

	@Override
	@Transactional
	public boolean createDefect(Defect defect) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Defect> listDefect = (List<Defect>)session.createQuery("from Defect D where D.defect_id = '"+ defect.getDefect_id() + "' and D.defect_release_id = '"+ defect.getDefect_release_id() + "'").list();
		
		if(listDefect.isEmpty()) {
			session.save(defect);
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	@Transactional
	public boolean deleteDefect(int DefectID) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Defect> listDefect = (List<Defect>)session.createQuery("from Defect D where D.Defect_entity_id = '"+ DefectID +"'").list();
		if(listDefect.isEmpty()) {
			return false;
		}
		else {
			session.delete(listDefect.get(0));
			return true;
		}
	}

	@Override
	@Transactional
	public boolean updateDefect(Defect defect) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Defect> listDefect = (List<Defect>)session.createQuery("from Defect D where D.defect_id = '"+ defect.getDefect_id() + "' and D.defect_release_id = '"+ defect.getDefect_release_id() + "'").list();
		if(listDefect.isEmpty()) {
			return false;
		}
		else {
			Defect DefectToUpdate = listDefect.get(0);
			DefectToUpdate.setState(defect.getState());
			DefectToUpdate.setSeverity(defect.getSeverity());
			DefectToUpdate.setTimestamp(defect.getTimestamp());
			DefectToUpdate.setEstimated_fix_time(defect.getEstimated_fix_time());
			DefectToUpdate.setTitle(defect.getTitle());
			DefectToUpdate.setFunctional_area(defect.getFunctional_area());
			DefectToUpdate.setPhase_raised(defect.getPhase_raised());
			DefectToUpdate.setDetected_in_release(defect.getDetected_in_release());
			DefectToUpdate.setRoot_cause_analysis(defect.getRoot_cause_analysis());
			DefectToUpdate.setPassed_first_ist_retest(defect.getPassed_first_ist_retest());
			DefectToUpdate.setClosed_date(defect.getClosed_date());
			DefectToUpdate.setPriority(defect.getPriority());
			DefectToUpdate.setIst_release_eta(defect.getIst_release_eta());
			DefectToUpdate.setIst_retest_status(defect.getIst_retest_status());
			DefectToUpdate.setRaised_during(defect.getRaised_during());
			DefectToUpdate.setIst_classification(defect.getIst_classification());
			DefectToUpdate.setPassed_first_uat_retest(defect.getPassed_first_uat_retest());
			
			session.update(DefectToUpdate);
			return true;
		}
	}
	
	@Override
	@Transactional
	public List<Defect> getDefectListForRelease(int ReleaseID) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Defect> listDefect = (List<Defect>)session.createQuery("from Defect D where D.defect_release_id = '"+ ReleaseID + "'").list();
		return listDefect;
	}

	@Override
	@Transactional
	public List<Defect> getDefectsBySeverity(int ReleaseID, String defectSeverity) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Defect> listDefect = (List<Defect>)session.createQuery("from Defect D where D.defect_release_id = '"+ ReleaseID + "' and D.severity in ("+ defectSeverity + ")").list();
		return listDefect;
	}

	@Override
	@Transactional
	public List<Defect> getDefectsByState(int ReleaseID, String defectState) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Defect> listDefect = (List<Defect>)session.createQuery("from Defect D where D.defect_release_id = '"+ ReleaseID + "' and D.state in ("+ defectState + ")").list();
		return listDefect;
	}

	@Override
	@Transactional
	public List<Defect> getDefectsBySeverityState(int ReleaseID, String defectState, String defectSeverity) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Defect> listDefect = (List<Defect>)session.createQuery("from Defect D where D.defect_release_id = '"+ ReleaseID + "' and D.severity in ("+ defectSeverity + ") and D.state in("+ defectState + ")").list();
		return listDefect;
	}

	@Override
	@Transactional
	public Map<String, Object> getAgeingData(int ReleaseID, int fromDays, int toDays) throws ParseException {
		
		String defectStates  = "'"+AlmConstants.STATE_NEW+"','"+AlmConstants.STATE_OPEN+"','"+AlmConstants.STATE_REOPEN+"','"+AlmConstants.STATE_FIXED+"'";
		
		Session session = this.sessionFactory.getCurrentSession();
		
		//Getting all defects which are in open state for that release
		@SuppressWarnings("unchecked")
		List<Defect> listAllDefect = (List<Defect>)session.createQuery("from Defect D where D.defect_release_id = '"+ ReleaseID + "' and D.state in("+ defectStates + ")").list();
		
		//Formatting todays date
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date currentDate = dateFormat.parse(dateFormat.format(new Date()));
		
		Map<String, Object> ageingDataMap = new HashMap<String, Object>();
		
		String defectAgeingRange = "";
		long DefSev_1_Count = 0;
		long DefSev_2_Count = 0;
		long DefSev_3_Count = 0;
		long DefSev_4_Count = 0;
		long DefSev_5_Count = 0;
		
		//Create Range key for adding into map
		if(toDays == 0)
			defectAgeingRange = fromDays + "+";
		else
			defectAgeingRange = fromDays + "-" + toDays;
		
		//Iterating through all the defects
		for(Defect defectSelected : listAllDefect) {
			
			//Getting Severity and Raised date for the selected defect
			Date defectRaisedDate = defectSelected.getSubmit_date();
			String defctSeverity = defectSelected.getSeverity();
			
			//Condition to calculate for range and Greater than days alone(if todays==0 means we have to calculate only for greater than given days)
			if(toDays == 0) {
				//Calculating difference between two dates in days
				long diffInDays = TimeUnit.MILLISECONDS.toDays(currentDate.getTime() - defectRaisedDate.getTime());
				
				//Counting the defects based on severity and given date alone 
				if(diffInDays > fromDays) {
					
					DefSev_1_Count += defctSeverity.compareTo(AlmConstants.SEVERITY_1)==0 ? 1 : 0;
					DefSev_2_Count += defctSeverity.compareTo(AlmConstants.SEVERITY_2)==0 ? 1 : 0;
					DefSev_3_Count += defctSeverity.compareTo(AlmConstants.SEVERITY_3)==0 ? 1 : 0;
					DefSev_4_Count += defctSeverity.compareTo(AlmConstants.SEVERITY_4)==0 ? 1 : 0;
					DefSev_5_Count += defctSeverity.compareTo(AlmConstants.SEVERITY_5)==0 ? 1 : 0;
				}
			}
			else {
				//Calculating difference between two dates in days
				long diffInDays = TimeUnit.MILLISECONDS.toDays(currentDate.getTime() - defectRaisedDate.getTime());
				
				//Counting the defects based on severity and given range 
				if(diffInDays >= fromDays && diffInDays <= toDays) {
					DefSev_1_Count += defctSeverity.compareTo(AlmConstants.SEVERITY_1)==0 ? 1 : 0;
					DefSev_2_Count += defctSeverity.compareTo(AlmConstants.SEVERITY_2)==0 ? 1 : 0;
					DefSev_3_Count += defctSeverity.compareTo(AlmConstants.SEVERITY_3)==0 ? 1 : 0;
					DefSev_4_Count += defctSeverity.compareTo(AlmConstants.SEVERITY_4)==0 ? 1 : 0;
					DefSev_5_Count += defctSeverity.compareTo(AlmConstants.SEVERITY_5)==0 ? 1 : 0;
				}
			}
			
		}
		
		//Initializing map for the aging data
		ageingDataMap.put("AgeRange", defectAgeingRange);
		ageingDataMap.put(AlmConstants.SEVERITY_1, DefSev_1_Count);
		ageingDataMap.put(AlmConstants.SEVERITY_2, DefSev_2_Count);
		ageingDataMap.put(AlmConstants.SEVERITY_3, DefSev_3_Count);
		ageingDataMap.put(AlmConstants.SEVERITY_4, DefSev_4_Count);
		ageingDataMap.put(AlmConstants.SEVERITY_5, DefSev_5_Count);
		ageingDataMap.put("TotalDefects", DefSev_1_Count + DefSev_2_Count + DefSev_3_Count + DefSev_4_Count + DefSev_5_Count);
		
		return ageingDataMap;
	}
	
	//Method to get the Open Defects by Root Cause Analysis
	@Override
    @Transactional
    public List<Defect> getDefectsByRootCause(int ReleaseID, String defectState, String rootCauseAnalysis) {
          
		Session session = this.sessionFactory.getCurrentSession();
        @SuppressWarnings("unchecked")
        List<Defect> lstRootCauseDefect = (List<Defect>)session.createQuery("from Defect D where D.defect_release_id = '"+ ReleaseID + "' and D.state in("+ defectState + ") and D.root_cause_analysis in ("+ rootCauseAnalysis + ")").list();
          
        return lstRootCauseDefect;          
    
    }
	
	@Override
	@Transactional
	public List<String> getRCAValuesForRelease(int releaseIDs) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<String> listDefectRCA = session.createQuery("select distinct D.root_cause_analysis from Defect D where D.defect_release_id in ("+ releaseIDs + ")").list();
		return listDefectRCA;
	}
	
	@Override
	@Transactional
	public List<String> getModuleValuesForRelease(int releaseIDs) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<String> listDefectRCA = session.createQuery("select distinct D.functional_area from Defect D where D.defect_release_id in ("+ releaseIDs + ")").list();
		return listDefectRCA;
	}

	@Override
	@Transactional
	public int getDefectsCountByRootCauseForTrendsHistory(int releaseId, String releaseName, String rootCause, String confirmedDefctsStates, String phase) {
		Session session = this.sessionFactory.getCurrentSession();
		int defectCountForRootCause = session.createQuery("from Defect D where D.defect_release_id = '"+ releaseId +"' and D.root_cause_analysis = '"+ rootCause + "' and D.state in("+ confirmedDefctsStates + ") and D.phase_raised in ('"+ phase + "')").list().size();
		
		return defectCountForRootCause;
	}
	
	@Override
	@Transactional
	public int getDefectsCountByModuleForTrendsHistory(int releaseId, String releaseName, String module, String confirmedDefctsStates, String phase) {
		Session session = this.sessionFactory.getCurrentSession();
		int defectCountForRootCause = session.createQuery("from Defect D where D.defect_release_id = '"+ releaseId +"' and D.functional_area = '"+ module + "' and D.state in("+ confirmedDefctsStates + ") and D.phase_raised in ('"+ phase + "')").list().size();
		
		return defectCountForRootCause;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Defect> getDefectCountBySeverityForPhase(int releaseId, String selectedSeverity, String Phase) {
		Session session = this.sessionFactory.getCurrentSession();
		List<Defect> list = (List<Defect>)session.createQuery("from Defect D where D.defect_release_id = '"+ releaseId + "' and D.severity = '"+ selectedSeverity +"' and D.phase_raised in ('"+ Phase + "')").list();
		return list;
	}
	
	@Override
	@Transactional
	public List<Defect> getConfirmDefectsforReleaseForPhase(int ReleaseID, String phase) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Defect> listDefect = (List<Defect>)session.createQuery("from Defect D where D.defect_release_id = '"+ ReleaseID + "' and D.state in("+ Constants.ConfirmedDefctsStates + ") and D.phase_raised in ('"+ phase + "') ").list();
		return listDefect;
	}
	
	@Override
	@Transactional
	public List<Defect> getDefectsByRootCauseforReleaseForPhase(int ReleaseID, String defectStates, String rootCauseList, String Phase) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Defect> listDefect = (List<Defect>)session.createQuery("from Defect D where D.defect_release_id = '"+ ReleaseID + "'and D.root_cause_analysis in("+ rootCauseList + ")  and D.state in("+ defectStates + ") and D.phase_raised in ('"+ Phase + "') ").list();
		return listDefect;
	}

}
