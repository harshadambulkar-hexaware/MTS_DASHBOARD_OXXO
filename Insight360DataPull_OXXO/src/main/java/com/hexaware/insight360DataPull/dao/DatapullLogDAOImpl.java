package com.hexaware.insight360DataPull.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.hexaware.insight360DataPull.model.DatapullLog;

public class DatapullLogDAOImpl implements DatapullLogDAO{

	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(DatapullLogDAOImpl.class);
	
	public DatapullLogDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

	
	@Override
	@Transactional
	public boolean createDatapullLog(DatapullLog datapullLog) {
		
		Session session = this.sessionFactory.getCurrentSession();
		logger.info("createDatapulllog method started");		
		
		session.save(datapullLog);
		return true;
		
	}

}
