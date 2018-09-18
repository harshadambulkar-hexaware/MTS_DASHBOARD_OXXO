package com.hexaware.insight360DataPull.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.hexaware.insight360DataPull.model.TrendsHistoryStorage;

public class TrendsHistoryStorageDAOImpl implements TrendsHistoryStorageDAO {
	
private SessionFactory sessionFactory;
    
    @SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(DefectDAOImpl.class);
		
	public TrendsHistoryStorageDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

	@Override
	@Transactional
	public boolean createTrendsHistoryStorage(TrendsHistoryStorage trendsHistoryStorage) {
		
		Session session = this.sessionFactory.getCurrentSession();
		/*@SuppressWarnings("unchecked")
		List<TrendsHistoryStorage> listTrendsHistoryStorage = (List<TrendsHistoryStorage>)session.createQuery("from TrendsHistoryStorage T where T.releaseId = '"+ trendsHistoryStorage.getReleaseId() + "' and T.releaseName = '"+trendsHistoryStorage.getReleaseName()+"' and T.phase = '"+trendsHistoryStorage.getPhase()+"' and T.ipr = '"+trendsHistoryStorage.getIpr()+"' and T.defectScore = '"+trendsHistoryStorage.getDefectScore()+"' ").list();*/
		
		//if(listTrendsHistoryStorage.isEmpty()) {
			session.save(trendsHistoryStorage);
			return true;
		/*}
		else {
			return false;
		}
		*/
	}
	
	@Override
	@Transactional
	public boolean createTrendsHistoryStorageForRootCause(TrendsHistoryStorage trendsHistoryStorage) {
		
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<TrendsHistoryStorage> listTrendsHistoryStorage = (List<TrendsHistoryStorage>)session.createQuery("from TrendsHistoryStorage T where T.releaseId = '"+ trendsHistoryStorage.getReleaseId() + "' and T.releaseName = '"+trendsHistoryStorage.getReleaseName()+"' and T.phase = '"+trendsHistoryStorage.getPhase()+"' and T.rootCause = '"+trendsHistoryStorage.getRootCause()+"')").list();
		
		if(listTrendsHistoryStorage.isEmpty()) {
			session.save(trendsHistoryStorage);
			return true;
		}
		else {
			return false;
		}
		
	}
	
	@Override
	@Transactional
	public boolean createTrendsHistoryStorageForModule(TrendsHistoryStorage trendsHistoryStorage) {
		
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<TrendsHistoryStorage> listTrendsHistoryStorage = (List<TrendsHistoryStorage>)session.createQuery("from TrendsHistoryStorage T where T.releaseId = '"+ trendsHistoryStorage.getReleaseId() + "' and T.releaseName = '"+trendsHistoryStorage.getReleaseName()+"' and T.phase = '"+trendsHistoryStorage.getPhase()+"' and T.module = '"+trendsHistoryStorage.getModule()+"') ").list();
		
		if(listTrendsHistoryStorage.isEmpty()) {
			session.save(trendsHistoryStorage);
			return true;
		}
		else {
			return false;
		}
		
	}
	
	@Override
	@Transactional
	public boolean updateTrendsHistoryStorage(TrendsHistoryStorage trendsHistoryStorage) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<TrendsHistoryStorage> listTrendsHistoryStorage = (List<TrendsHistoryStorage>)session.createQuery("from TrendsHistoryStorage T where T.trendsHistoryStorageEntityId = '"+ trendsHistoryStorage.getTrendsHistoryStorageEntityId() + "' and T.releaseId = '"+ trendsHistoryStorage.getReleaseId() + "'").list();
		if(listTrendsHistoryStorage.isEmpty()) {
			return false;
		}
		else {
			TrendsHistoryStorage trendsHistoryStorageToUpdate = listTrendsHistoryStorage.get(0);
			
			trendsHistoryStorageToUpdate.setPhase(trendsHistoryStorage.getPhase());
			trendsHistoryStorageToUpdate.setCodeDeliverySlipScore(trendsHistoryStorage.getCodeDeliverySlipScore());
			trendsHistoryStorageToUpdate.setRootCause(trendsHistoryStorage.getRootCause());
			trendsHistoryStorageToUpdate.setModule(trendsHistoryStorage.getModule());
			trendsHistoryStorageToUpdate.setDefectCountByModule(trendsHistoryStorage.getDefectCountByModule());
			trendsHistoryStorageToUpdate.setDefectCountByRootCause(trendsHistoryStorage.getDefectCountByRootCause());
			return true;
		}
	}

	@Override
	@Transactional
	public TrendsHistoryStorage getTrendsHistoryData(int releaseId,String Phase) {
		TrendsHistoryStorage trendsHistoryStorage = null;
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<TrendsHistoryStorage> trendsIPRHistoryDataList = (List<TrendsHistoryStorage>)session.createQuery("from TrendsHistoryStorage T where T.releaseId = '"+ releaseId +"' and T.phase = '"+ Phase +"'").list();
		
		if(trendsIPRHistoryDataList != null){
		trendsHistoryStorage = trendsIPRHistoryDataList.get(0);
		}
		
		return trendsHistoryStorage;
		
	}
}
