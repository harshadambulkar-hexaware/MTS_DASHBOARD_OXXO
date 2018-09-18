package com.hexaware.insight360DataPull.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.hexaware.insight360DataPull.model.Test;

public class TestDAOImpl implements TestDAO {
	
	
	private SessionFactory sessionFactory;
    
    @SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(TestDAOImpl.class);
		
	public TestDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

	@Override
	@Transactional
	public List<Test> testList() {
		@SuppressWarnings("unchecked")
        List<Test> listTest = (List<Test>) sessionFactory.getCurrentSession()
                .createCriteria(Test.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
 
        return listTest;
	}

	@Override
	@Transactional
	public List<Test> getTestListForRelease(int ReleaseID) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Test> listTest = (List<Test>)session.createQuery("from Test T where T.test_release_id = '"+ ReleaseID + "'").list();
		return listTest;
	}

	@Override
	@Transactional
	public List<Test> getTestListForRequirement(int ReleaseID, int RequirementID) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Test> listTest = (List<Test>)session.createQuery("from Test T where T.test_release_id = '"+ ReleaseID + "' and T.test_req_id = '"+ RequirementID + "'").list();
		return listTest;
	}

	@Override
	@Transactional
	public Test getTest(int TestID) {
		Session session = this.sessionFactory.getCurrentSession();
    	@SuppressWarnings("unchecked")
		List<Test> listTest = (List<Test>)session.createQuery("from Test T where D.test_entity_id = '"+ TestID +"'").list();
    	
    	return listTest.isEmpty() ? null : listTest.get(0);
	}

	@Override
	@Transactional
	public boolean createTest(Test test, String Phase) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Test> listTest = (List<Test>)session.createQuery("from Test T where T.test_id = '"+ test.getTest_id() + "' and T.test_release_id = '"+ test.getTest_release_id() + "'").list();
		
		if(listTest.isEmpty()) {
			session.save(test);
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	@Transactional
	public boolean deleteTest(int testID) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Test> listTest = (List<Test>)session.createQuery("from Test T where T.test_entity_id = '"+ testID +"'").list();
		if(listTest.isEmpty()) {
			return false;
		}
		else {
			session.delete(listTest.get(0));
			return true;
		}
	}

	@Override
	@Transactional
	public boolean updateTest(Test test, String Phase) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Test> listTest = (List<Test>)session.createQuery("from Test T where T.test_id = '"+ test.getTest_id() + "' and T.test_release_id = '"+ test.getTest_release_id() + "'").list();
		if(listTest.isEmpty()) {
			return false;
		}
		else {
			Test TestToUpdate = listTest.get(0);
			TestToUpdate.setExec_status(test.getExec_status());
			TestToUpdate.setStatus(test.getStatus());
			TestToUpdate.setLast_modified(test.getLast_modified());
			session.update(TestToUpdate);
			return true;
		}
	}

}
