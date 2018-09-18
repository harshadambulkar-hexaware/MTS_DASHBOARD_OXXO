package com.hexaware.insight360DataPull.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.hexaware.insight360DataPull.model.DefectsHistory;

public class DefectsHistoryDAOImpl implements DefectsHistoryDAO {
	
	private SessionFactory sessionFactory;
    
    private static final Logger logger = Logger.getLogger(DefectsHistoryDAOImpl.class);
		
	public DefectsHistoryDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

	@Override
	@Transactional
	public List<DefectsHistory> DefectsHistoryList() {
		@SuppressWarnings("unchecked")
        List<DefectsHistory> listDefectsHistory = (List<DefectsHistory>) sessionFactory.getCurrentSession()
                .createCriteria(DefectsHistory.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
 
        return listDefectsHistory;
	}

	@Override
	@Transactional
	public List<DefectsHistory> DefectsHistoryListForRelease(int ReleaseID) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		//List<DefectsHistory> listDefectsHistory = (List<DefectsHistory>)session.createQuery("from DefectsHistory D where D.def_hist_rel_id = '"+ ReleaseID + "'").list();
		List<DefectsHistory> listDefectsHistory = (List<DefectsHistory>)session.createQuery("from DefectsHistory D where D.def_hist_rel_id = '"+ ReleaseID + "' and D.phase='IST' group by D.def_hist_timestamp, D.def_hist_rel_id").list();
		return listDefectsHistory;
	}

	@Override
	@Transactional
	public boolean createDefectsHistory(DefectsHistory defectsHistory,String phase) {
		
		Session session = this.sessionFactory.getCurrentSession();
		
		DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD");
		Date DefectHistoryTS = defectsHistory.getDef_hist_timestamp();
		
		logger.info("Parsed date :" + DefectHistoryTS);
		
		String DefectFormatDate = dateFormat.format(DefectHistoryTS);
		
		@SuppressWarnings("unchecked")
		List<DefectsHistory> defectHistoryList = (List<DefectsHistory>)session.createQuery("from DefectsHistory D where D.def_hist_rel_id = '"+ defectsHistory.getDef_hist_rel_id() + "' and D.phase = '"+ phase + "'").list();
		Date dateinQuery;
		String queryDateFormatDate = ""; 
		for (DefectsHistory date : defectHistoryList)
		{
			dateinQuery = date.getDef_hist_timestamp();
			queryDateFormatDate =  dateFormat.format(dateinQuery);
			
		}
		
		if (queryDateFormatDate.compareToIgnoreCase(DefectFormatDate)!=0)
		{
			//List<DefectsHistory> listDefect = (List<DefectsHistory>)session.createQuery("from DefectsHistory D where D.def_hist_rel_id = '"+ defectsHistory.getDef_hist_rel_id() + "' and D.def_hist_timestamp ='"+defectsHistory.getDef_hist_timestamp()+"'").list();	
				session.save(defectsHistory);
				return true;
			}
		
		
				
		//List<DefectsHistory> listDefect = (List<DefectsHistory>)session.createQuery("from DefectsHistory D where D.def_hist_rel_id = '"+ defectsHistory.getDef_hist_rel_id() + "' and D.def_hist_timestamp ='"+defectsHistory.getDef_hist_timestamp()+"'").list();	
		//if(listDefect.isEmpty()) {
			//session.save(defectsHistory);
			//return true;
		//}
		else {
			return false;
		}
	}
	
	@Override
	@Transactional
	public boolean updateDefectsHistory(DefectsHistory defectsHistory,String phase)	{		
		Session session = this.sessionFactory.getCurrentSession();
		
		@SuppressWarnings("unchecked")		
		List<DefectsHistory> listDefect = (List<DefectsHistory>)session.createQuery("from DefectsHistory D where D.def_hist_rel_id = '"+ defectsHistory.getDef_hist_rel_id() + "'  and D.phase = '"+ phase + "' order by D.def_hist_timestamp desc").list();	
		DefectsHistory historyDefect = listDefect.get(0);
		historyDefect.setDef_hist_timestamp(defectsHistory.getDef_hist_timestamp());
		historyDefect.setDef_hist_rel_id(defectsHistory.getDef_hist_rel_id());
		historyDefect.setDef_hist_severity_sev1(defectsHistory.getDef_hist_severity_sev1());
		historyDefect.setDef_hist_severity_sev2(defectsHistory.getDef_hist_severity_sev2());
		historyDefect.setDef_hist_severity_sev3(defectsHistory.getDef_hist_severity_sev3());
		historyDefect.setDef_hist_severity_sev4(defectsHistory.getDef_hist_severity_sev4());
		historyDefect.setDef_hist_severity_sev5(defectsHistory.getDef_hist_severity_sev5());
		historyDefect.setDef_totl_defects(defectsHistory.getDef_totl_defects());
		historyDefect.setDef_hist_closed_severity_sev1(defectsHistory.getDef_hist_closed_severity_sev1());
		historyDefect.setDef_hist_closed_severity_sev2(defectsHistory.getDef_hist_closed_severity_sev2());
		historyDefect.setDef_hist_closed_severity_sev3(defectsHistory.getDef_hist_closed_severity_sev3());
		historyDefect.setDef_hist_closed_severity_sev4(defectsHistory.getDef_hist_closed_severity_sev4());
		historyDefect.setDef_hist_closed_severity_sev5(defectsHistory.getDef_hist_closed_severity_sev5());
		historyDefect.setDef_totl_closed_defects(defectsHistory.getDef_totl_closed_defects());
		historyDefect.setPhase(phase);
		session.update(historyDefect);		
				
		return true;
		
	}
	
}
