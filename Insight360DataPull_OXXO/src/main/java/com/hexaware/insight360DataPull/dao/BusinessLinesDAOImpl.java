package com.hexaware.insight360DataPull.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.hexaware.insight360DataPull.model.BusinessLines;

public class BusinessLinesDAOImpl implements BusinessLinesDAO {

	private SessionFactory sessionFactory;

    @SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(BusinessLinesDAOImpl.class);
		
	public BusinessLinesDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
	
	@Override
	@Transactional
	public List<BusinessLines> BusinessLinesList() {
		@SuppressWarnings("unchecked")
        List<BusinessLines> listBusinessLines = (List<BusinessLines>) sessionFactory.getCurrentSession()
                .createCriteria(BusinessLines.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
 
        return listBusinessLines;
		
	}

	@Override
	@Transactional
	public Integer createBusinessLines(BusinessLines businessLines) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<BusinessLines> listBusinessLines = (List<BusinessLines>)session.createQuery("from BusinessLines D where D.business_line_id = '"+ businessLines.getBusiness_line_id() + "'").list();
		
		if(listBusinessLines.isEmpty()) {
			Integer id = (Integer) session.save(businessLines);
			return id;
		}
		else {
			return null;
		}
	}

	@Override
	@Transactional
	public boolean deleteBusinessLines(int BusinessLinesId) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<BusinessLines> listBusinessLines = (List<BusinessLines>)session.createQuery("from BusinessLines D where D.business_line_id = '"+ BusinessLinesId +"'").list();
		if(listBusinessLines.isEmpty()) {
			return false;
		}
		else {
			session.delete(listBusinessLines.get(0));
			return true;
		}
	}

	@Override
	@Transactional
	public boolean updateBusinessLines(BusinessLines businessLines) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<BusinessLines> listBusinessLines = (List<BusinessLines>)session.createQuery("from BusinessLines D where D.business_line_id = '"+ businessLines.getBusiness_line_id() +"'").list();
		if(listBusinessLines.isEmpty()) {
			return false;
		}
		else {
			BusinessLines BusinessLinesToUpdate = listBusinessLines.get(0);
			BusinessLinesToUpdate.setBusiness_line_health(businessLines.getBusiness_line_health());
			BusinessLinesToUpdate.setBusiness_line_name(businessLines.getBusiness_line_name());
			session.update(BusinessLinesToUpdate);
			return true;
		}
	}

}
