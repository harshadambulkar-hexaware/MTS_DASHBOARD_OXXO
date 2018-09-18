package com.hexaware.insight360DataPull.dao;
 
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.hexaware.insight360DataPull.model.User;
 
public class UserDAOImpl implements UserDAO {
    
	private SessionFactory sessionFactory;
    
    @SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(UserDAOImpl.class);
    
    public UserDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
 
    @Override
    @Transactional
    public List<User> list() {
        @SuppressWarnings("unchecked")
        List<User> listUser = (List<User>) sessionFactory.getCurrentSession()
                .createCriteria(User.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
 
        return listUser;
    }
    
    @Override
    @Transactional
    public List<User> UserList() {
    	
    	Session session = this.sessionFactory.getCurrentSession();
        @SuppressWarnings("unchecked")
        List<User> listUser = (List<User>) session.createQuery("from User").list();
 
        return listUser;
    }
    
    
    @Override
	@Transactional
	public List<User> getUserByUsername(String usernames) {
    	Session session = this.sessionFactory.getCurrentSession();
        @SuppressWarnings("unchecked")
        List<User> listUser = (List<User>) session.createQuery("from User U where U.id in ("+ usernames +")").list();
        return listUser;
	}
    
    @Override
    @Transactional
    public User getUserByUsername(String username, String password) {
    	System.out.println("Inside getUserByUsername method");
    	Session session = this.sessionFactory.getCurrentSession();
    	@SuppressWarnings("unchecked")
		List<User> listUser = (List<User>)session.createQuery("from User U where U.username = '"+ username +"'and U.password = '" + password + "'").list();
    	
    	return listUser.isEmpty() ? null : listUser.get(0);
    	
    	
    }

	@Override
	@Transactional
	public boolean createUser(User user) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<User> listUser = (List<User>)session.createQuery("from User U where U.username = '"+ user.getusername() + "'").list();
		
		if(listUser.isEmpty()) {
			session.save(user);
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	@Transactional
	public boolean deleteUser(String username) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<User> listUser = (List<User>)session.createQuery("from User U where U.username = '"+ username + "'").list();
		if(listUser.isEmpty()) {
			return false;
		}
		else {
			session.delete(listUser.get(0));
			return true;
		}
	}

	@Override
	@Transactional
	public boolean updateUser(User user) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<User> listUser = (List<User>)session.createQuery("from User U where U.username = '"+ user.getusername() + "'").list();
		if(listUser.isEmpty()) {
			return false;
		}
		else {
			User userToUpdate = listUser.get(0);
			userToUpdate.setusername(user.getusername());
			userToUpdate.setemail(user.getemail());
			userToUpdate.setpassword(user.getpassword());
			userToUpdate.setProjectname(user.getProjectname());
			userToUpdate.setUserrole(user.getUserrole());
			userToUpdate.setStatus(user.getStatus());
			session.update(userToUpdate);
			return true;
		}
	}

	
}