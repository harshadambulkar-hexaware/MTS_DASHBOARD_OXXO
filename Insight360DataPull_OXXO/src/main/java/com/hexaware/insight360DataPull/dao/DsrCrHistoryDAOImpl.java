package com.hexaware.insight360DataPull.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.hexaware.insight360DataPull.HpAlmQc.AlmConstants;
import com.hexaware.insight360DataPull.model.DsrCrHistory;

public class DsrCrHistoryDAOImpl implements DsrCrHistoryDAO {
	
	private SessionFactory sessionFactory;
    
    @SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(DsrCrHistoryDAOImpl.class);
		
	public DsrCrHistoryDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

	@Override
	@Transactional
	public List<DsrCrHistory> DsrCrHistoryList() {
		@SuppressWarnings("unchecked")
        List<DsrCrHistory> listDsrCrHistory = (List<DsrCrHistory>) sessionFactory.getCurrentSession()
                .createCriteria(DsrCrHistory.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
 
        return listDsrCrHistory;
	}


	@Override
	@Transactional
	public List<Map<String, Object>> DsrCRHistoryGraph(int ReleaseID) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List <DsrCrHistory> DsrCrHistoryList = session.createQuery("from DsrCrHistory D where D.history_release_id = '"+ ReleaseID + "' and D.history_phase='IST' order by D.history_date").list();
		@SuppressWarnings("unchecked")
		List <Date> DsrCrHistoryDateList = session.createQuery("select D.history_date from DsrCrHistory D where D.history_release_id = '"+ ReleaseID + "' and D.history_phase='IST' group by D.history_date order by D.history_date").list();
		
		List<Map<String, Object>> historyRecordMapList = new ArrayList<Map<String, Object>>();
		//Format Date 
		DateFormat dateFormat = new SimpleDateFormat("MM/dd");
		
		for(Date historyDate : DsrCrHistoryDateList) {
			Map<String, Object> historyRecordMap = new HashMap<String, Object>();
			int crNotStartedCount = 0;
			int crFailedCount = 0;
			int crPassedCount = 0;
			int crNotCoveredCount = 0;
			int crNotCompletedCount = 0;
			int crBlockedCount = 0;
			
			for(DsrCrHistory historyRecord : DsrCrHistoryList) {
				if(historyRecord.getHistory_date().compareTo(historyDate)==0){					
					if(historyRecord.getCr_execution_status().compareTo(AlmConstants.STATE_NOT_STARTED)==0)
						crNotStartedCount++;
					if(historyRecord.getCr_execution_status().compareTo(AlmConstants.STATE_FAILED)==0)
						crFailedCount++;
					if(historyRecord.getCr_execution_status().compareTo(AlmConstants.STATE_PASSED)==0)
						crPassedCount++;
					if(historyRecord.getCr_execution_status().compareTo(AlmConstants.STATE_NOT_COVERED)==0)
						crNotCoveredCount++;
					if(historyRecord.getCr_execution_status().compareTo(AlmConstants.STATE_NOT_COMPLETED)==0)
						crNotCompletedCount++;
					if(historyRecord.getCr_execution_status().compareTo(AlmConstants.STATE_BLOCKED)==0)
						crBlockedCount++;	
				}
			}
			
			historyRecordMap.put(AlmConstants.STATE_NOT_STARTED,crNotStartedCount);
			historyRecordMap.put(AlmConstants.STATE_FAILED,crFailedCount);
			historyRecordMap.put(AlmConstants.STATE_PASSED,crPassedCount);
			historyRecordMap.put(AlmConstants.STATE_NOT_COVERED,crNotCoveredCount);
			historyRecordMap.put(AlmConstants.STATE_NOT_COMPLETED,crNotCompletedCount);
			historyRecordMap.put(AlmConstants.STATE_BLOCKED,crBlockedCount);
			historyRecordMap.put("history_date",dateFormat.format(historyDate));
			historyRecordMapList.add(historyRecordMap);
		}
		
		return historyRecordMapList;
	}
	
	@Override
	@Transactional
	public List<Map<String, Object>> DsrTCHistoryGraph(int ReleaseID) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List <Object[]> DsrTCHistoryList = session.createQuery("select sum(cr_planned_cases), sum (cr_passed_cases), sum(cr_failed_cases), history_date from DsrCrHistory D where D.history_release_id = '"+ ReleaseID + "' and D.history_phase='IST' group by D.history_date order by D.history_date").list();
		List<Map<String, Object>> historyRecordMapList = new ArrayList<Map<String, Object>>();
		
		//Format Date 
		DateFormat dateFormat = new SimpleDateFormat("MM/dd");
		for(Object[] historyRecord : DsrTCHistoryList) {
			Map<String, Object> historyRecordMap = new HashMap<String, Object>();
			historyRecordMap.put("cr_planned_cases",historyRecord[0]);
			historyRecordMap.put("cr_passed_cases",historyRecord[1]);
			historyRecordMap.put("cr_failed_cases",historyRecord[2]);
			historyRecordMap.put("history_date",dateFormat.format(historyRecord[3]));
			historyRecordMapList.add(historyRecordMap);
		}
		
		return historyRecordMapList;
	}

	@Override
	@Transactional
	public boolean createDsrHistory(DsrCrHistory dsrCrHistory,String Phase) {
		
		Session session = this.sessionFactory.getCurrentSession();
		
		DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD");
		Date dsrHistoryTS = dsrCrHistory.getHistory_date();
		
		logger.info("dsrCrHistory before: " + dsrHistoryTS);
		String dsrFormatDate = dateFormat.format(dsrHistoryTS);
		
		@SuppressWarnings("unchecked")
		List<DsrCrHistory> dsrHistoryList = (List<DsrCrHistory>)session.createQuery("from DsrCrHistory D where D.history_release_id = '"+ dsrCrHistory.getHistory_release_id() + "' and D.history_requirement_id = '"+ dsrCrHistory.getHistory_requirement_id() + "' and D.history_phase = '"+ Phase + "'").list();
		Date dateinQuery;
		String queryDateFormatDate = ""; 
		for (DsrCrHistory date : dsrHistoryList) {
			dateinQuery = date.getHistory_date();
			queryDateFormatDate =  dateFormat.format(dateinQuery);
		}
		logger.info("dsrCrHistory: " + dsrCrHistory);
		if (queryDateFormatDate.compareToIgnoreCase(dsrFormatDate)!=0) {
				session.save(dsrCrHistory);
				return true;
		}
		
		else {
			
			return false;
		}
	}
	
	@Override
	@Transactional
	public boolean updateDsrHistory(DsrCrHistory dsrCrHistory,String Phase)	{	
		
		Session session = this.sessionFactory.getCurrentSession();
		
		@SuppressWarnings("unchecked")		
		List<DsrCrHistory> dsrHistoryList = (List<DsrCrHistory>)session.createQuery("from DsrCrHistory D where D.history_release_id = '"+ dsrCrHistory.getHistory_release_id() + "' and D.history_requirement_id = '"+ dsrCrHistory.getHistory_requirement_id() + "'  and D.history_phase = '"+ Phase + "' order by D.history_date desc").list();		
		DsrCrHistory historyDsrCr = dsrHistoryList.get(0);
		
		historyDsrCr.setHistory_requirement_id(dsrCrHistory.getHistory_requirement_id());
		historyDsrCr.setHistory_release_id(dsrCrHistory.getHistory_release_id());
		historyDsrCr.setCr_execution_status(dsrCrHistory.getCr_execution_status());
		historyDsrCr.setCr_planned_cases(dsrCrHistory.getCr_planned_cases());
		//entityToAdd.setCr_planned_cases(requirement.get);
		historyDsrCr.setCr_passed_cases(dsrCrHistory.getCr_passed_cases());
		historyDsrCr.setCr_failed_cases(dsrCrHistory.getCr_failed_cases());
		historyDsrCr.setHistory_phase(Phase);
		historyDsrCr.setHistory_date(dsrCrHistory.getHistory_date());

		session.update(historyDsrCr);		
		
		return true;
	}
	
}
