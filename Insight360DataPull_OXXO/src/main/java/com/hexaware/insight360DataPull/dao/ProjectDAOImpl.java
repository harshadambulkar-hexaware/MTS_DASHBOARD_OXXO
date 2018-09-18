package com.hexaware.insight360DataPull.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.hexaware.insight360DataPull.model.Project;

public class ProjectDAOImpl implements ProjectDAO {
	
	private SessionFactory sessionFactory;
    
    private static final Logger logger = Logger.getLogger(ProjectDAOImpl.class);
		
	public ProjectDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
	
	@Override
	@Transactional
	public List<Project> ProjectList() {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Project> listProject = (List<Project>)session.createQuery("from Project P where P.data_src_tool not in ('insight')" ).list();
        /*List<Project> listProject = (List<Project>) sessionFactory.getCurrentSession()
                .createCriteria(Project.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();*/
 
		return listProject;
	}

	@Override
	@Transactional
	public Project getProject(int projectID) {
		Session session = this.sessionFactory.getCurrentSession();
    	@SuppressWarnings("unchecked")
		List<Project> listProject = (List<Project>)session.createQuery("from Project P where P.projectId = '"+ projectID +"'").list();
    	
    	return listProject.isEmpty() ? null : listProject.get(0);
	}
	
	@Override
	@Transactional
	public List<Project> getActiveProjectListForUser() {
		Session session = this.sessionFactory.getCurrentSession();
    	@SuppressWarnings("unchecked")
		List<Project> listProject = (List<Project>)session.createQuery("from Project P where P.isActive = "+ 1 ).list();
    	
    	return listProject;
	}

	@Override
	@Transactional
	public boolean createProject(Project project) {
		Session session = this.sessionFactory.getCurrentSession();
		logger.info("CreateProject method started");
		@SuppressWarnings("unchecked")
		List<Project> listProject = (List<Project>)session.createQuery("from Project P where P.projectName = '"+ project.getprojectName() + "' and P.domainName = '"+ project.getdomainName() + "'").list();
		
		if(listProject.isEmpty()) {
			session.save(project);
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	@Transactional
	public boolean deleteProject(int projectID) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Project> listProject = (List<Project>)session.createQuery("from Project P where P.projectId = '"+ projectID + "'").list();
		if(listProject.isEmpty()) {
			return false;
		}
		else {
			session.delete(listProject.get(0));
			return true;
		}
	}

	@Override
	@Transactional
	public boolean updateProject(Project project) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Project> listProject = (List<Project>)session.createQuery("from Project P where P.projectName = '"+ project.getprojectName() + "' and P.domainName = '"+ project.getdomainName() + "'").list();
		if(listProject.isEmpty()) {
			return false;
		}
		else {
			Project projectToUpdate = listProject.get(0);
			projectToUpdate.setprojectName(project.getprojectName());
			projectToUpdate.setdisplayName(project.getdisplayName());
			projectToUpdate.setisActive(project.getisActive());
			projectToUpdate.setisArchived(project.getisArchived());
			projectToUpdate.setisPlatform(project.getisPlatform());
			projectToUpdate.setdomainName(project.getdomainName());
			session.update(projectToUpdate);
			return true;
		}
	}

	@Override
	@Transactional
	public List<Project> getProjectList(String projectIDs) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Project> listProject = (List<Project>)session.createQuery("from Project P where P.isActive = 1 and P.projectId in (" + projectIDs + ")").list();
    	return listProject;
	}

	@Override
	@Transactional
	public Project getProject(String projectName, String domainName) {
		Session session = this.sessionFactory.getCurrentSession();
    	@SuppressWarnings("unchecked")
    	List<Project> listProject = (List<Project>)session.createQuery("from Project P where P.projectName = '"+ projectName + "' and P.domainName = '"+ domainName + "'").list();
    	
    	return listProject.isEmpty() ? null : listProject.get(0);
	}
	
	@Override
	@Transactional
	public Project getProject(String projectName) {
		Session session = this.sessionFactory.getCurrentSession();
    	@SuppressWarnings("unchecked")
    	List<Project> listProject = (List<Project>)session.createQuery("from Project P where P.projectName = '"+ projectName + "'").list();
    	
    	return listProject.isEmpty() ? null : listProject.get(0);
	}
}
