package com.hexaware.insight360DataPull.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.hexaware.insight360DataPull.model.DefectDevAnalysis;

public class DefectDevAnalysisDAOImpl implements DefectDevAnalysisDAO {
	
	private SessionFactory sessionFactory;
    
    @SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(DefectDevAnalysisDAOImpl.class);
		
	public DefectDevAnalysisDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

	@Override
	@Transactional
	public List<DefectDevAnalysis> DefectDevAnalysisList() {
		@SuppressWarnings("unchecked")
        List<DefectDevAnalysis> listDefectDevAnalysis = (List<DefectDevAnalysis>) sessionFactory.getCurrentSession()
                .createCriteria(DefectDevAnalysis.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
 
        return listDefectDevAnalysis;
	}

	@Override
	@Transactional
	public List<DefectDevAnalysis> DefectDevAnalysisListForRelease(int ReleaseID) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<DefectDevAnalysis> listDefectDevAnalysis = (List<DefectDevAnalysis>)session.createQuery("from DefectDevAnalysis D where D.defect_dev_release_id = '"+ ReleaseID + "'").list();
		return listDefectDevAnalysis;
	}

}
