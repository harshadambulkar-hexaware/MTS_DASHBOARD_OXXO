package com.hexaware.insight360DataPull.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.hexaware.insight360DataPull.model.TestingAnalysis;

public class TestingAnalysisDAOImpl implements TestingAnalysisDAO {
	
	private SessionFactory sessionFactory;
    
    @SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(TestingAnalysisDAOImpl.class);
		
	public TestingAnalysisDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

	@Override
	@Transactional
	public List<TestingAnalysis> DefectList() {
		@SuppressWarnings("unchecked")
        List<TestingAnalysis> listTestingAnalysis = (List<TestingAnalysis>) sessionFactory.getCurrentSession()
                .createCriteria(TestingAnalysis.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
 
        return listTestingAnalysis;
	}

	@Override
	@Transactional
	public List<TestingAnalysis> DefectListForRelease(int ReleaseID) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<TestingAnalysis> listTestingAnalysis = (List<TestingAnalysis>)session.createQuery("from TestingAnalysis T where T.testing_analysis_release_id = '"+ ReleaseID + "'").list();
		return listTestingAnalysis;
	}

}
