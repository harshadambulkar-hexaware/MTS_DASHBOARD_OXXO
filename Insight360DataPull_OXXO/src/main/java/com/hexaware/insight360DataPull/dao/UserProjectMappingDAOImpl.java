package com.hexaware.insight360DataPull.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.hexaware.insight360DataPull.model.Project;
import com.hexaware.insight360DataPull.model.User;
import com.hexaware.insight360DataPull.model.UserProjectMapping;

public class UserProjectMappingDAOImpl implements UserProjectMappingDAO {
	
	@Autowired
    private ProjectDAO projectDao;
	
	@Autowired
    private UserDAO userDao;
	
	private SessionFactory sessionFactory;
    
    @SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(UserProjectMappingDAOImpl.class);
    
    public UserProjectMappingDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

	@Override
	@Transactional
	public List<User> userListForProject(int projectID) {
		
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<String> listUserIds = (List<String>)session.createQuery("select U.user_id from UserProjectMapping U where U.project_id = '"+ projectID +"'").list();
		List<User> listOfUserForProject = (List<User>)userDao.getUserByUsername(listUserIds.toString().substring(1, listUserIds.toString().length() - 1));
    	return listOfUserForProject;
	}

	@Override
	@Transactional
	public List<Project> projectListForUser(int userID) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<String> listProjectIds = (List<String>)session.createQuery("select U.project_id from UserProjectMapping U where U.user_id = '"+ userID +"'").list();
		List<Project> listOfProjectForUser = (List<Project>)projectDao.getProjectList(listProjectIds.toString().substring(1, listProjectIds.toString().length() - 1));
    	return listOfProjectForUser;
	}

	@Override
	@Transactional
	public boolean subscribeProject(int userID, int projectID) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<UserProjectMapping> listOfMappings = (List<UserProjectMapping>)session.createQuery("from UserProjectMapping U where U.user_id = "+ userID +" and U.project_id = "+ projectID).list();
		if(listOfMappings.isEmpty()) {
			UserProjectMapping projectToMap = new UserProjectMapping();
			projectToMap.setProject_id(projectID);
			projectToMap.setUser_id(userID);
			session.save(projectToMap);
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	@Transactional
	public boolean unsubscribeProject(int userID, int projectID) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<UserProjectMapping> listOfMappings = (List<UserProjectMapping>)session.createQuery("from UserProjectMapping U where U.user_id = "+ userID +" and U.project_id = "+ projectID).list();
		if(listOfMappings.isEmpty()) {
			return false;
		}
		else {
			session.delete(listOfMappings.get(0));
			return true;
		}
	}

}
