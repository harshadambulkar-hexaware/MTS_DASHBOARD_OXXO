package com.hexaware.insight360DataPull.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.hexaware.insight360DataPull.model.SubBusinessLines;

public class SubBusinessLinesDAOImpl implements SubBusinessLinesDAO {

	private SessionFactory sessionFactory;

    @SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(SubBusinessLinesDAOImpl.class);
		
	public SubBusinessLinesDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
	
	@Override
	@Transactional
	public List<SubBusinessLines> SubBusinessLinesList() {
		@SuppressWarnings("unchecked")
        List<SubBusinessLines> listSubBusinessLines = (List<SubBusinessLines>) sessionFactory.getCurrentSession()
                .createCriteria(SubBusinessLines.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
 
        return listSubBusinessLines;
		
	}

	@Override
	@Transactional
	public Integer createSubBusinessLines(SubBusinessLines subBusinessLines) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<SubBusinessLines> listSubBusinessLines = (List<SubBusinessLines>)session.createQuery("from SubBusinessLines D where D.sub_business_line_id = '"+ subBusinessLines.getSub_business_line_id() + "'").list();
		
		if(listSubBusinessLines.isEmpty()) {
			Integer id = (Integer) session.save(subBusinessLines);
			return id;
		}
		else {
			return null;
		}
	}

	@Override
	@Transactional
	public boolean deleteSubBusinessLines(int SubBusinessLinesId) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<SubBusinessLines> listSubBusinessLines = (List<SubBusinessLines>)session.createQuery("from SubBusinessLines D where D.sub_business_line_id = '"+ SubBusinessLinesId +"'").list();
		if(listSubBusinessLines.isEmpty()) {
			return false;
		}
		else {
			session.delete(listSubBusinessLines.get(0));
			return true;
		}
	}

	@Override
	@Transactional
	public boolean updateSubBusinessLines(SubBusinessLines subBusinessLines) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<SubBusinessLines> listSubBusinessLines = (List<SubBusinessLines>)session.createQuery("from SubBusinessLines D where D.sub_business_line_id = '"+ subBusinessLines.getSub_business_line_id() +"'").list();
		if(listSubBusinessLines.isEmpty()) {
			return false;
		}
		else {
			SubBusinessLines SubBusinessLinesToUpdate = listSubBusinessLines.get(0);
			SubBusinessLinesToUpdate.setSub_business_line_health(subBusinessLines.getSub_business_line_health());
			SubBusinessLinesToUpdate.setSub_business_line_name(subBusinessLines.getSub_business_line_name());
			session.update(SubBusinessLinesToUpdate);
			return true;
		}
	}

}
