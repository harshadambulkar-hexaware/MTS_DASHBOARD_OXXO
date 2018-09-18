package com.hexaware.insight360DataPull.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.hexaware.insight360DataPull.model.ScopeAnalysis;

public class ScopeAnalysisDAOImpl implements ScopeAnalysisDAO {
	
	private SessionFactory sessionFactory;
    
    @SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(ScopeAnalysisDAOImpl.class);
		
	public ScopeAnalysisDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

	@Override
	@Transactional
	public List<ScopeAnalysis> ScopeAnalysisList() {
		@SuppressWarnings("unchecked")
        List<ScopeAnalysis> listScopeAnalysis = (List<ScopeAnalysis>) sessionFactory.getCurrentSession()
                .createCriteria(ScopeAnalysis.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
 
        return listScopeAnalysis;
	}

	@Override
	@Transactional
	public List<ScopeAnalysis> ScopeAnalysisListForRelease(int ReleaseID) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<ScopeAnalysis> listScopeAnalysis = (List<ScopeAnalysis>)session.createQuery("from ScopeAnalysis S where S.scope_release_id = '"+ ReleaseID + "'").list();
		return listScopeAnalysis;
	}

}
