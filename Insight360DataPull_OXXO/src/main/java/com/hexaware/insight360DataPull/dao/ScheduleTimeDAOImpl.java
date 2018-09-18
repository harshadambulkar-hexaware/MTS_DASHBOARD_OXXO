package com.hexaware.insight360DataPull.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.hexaware.insight360DataPull.model.ScheduleTime;

public class ScheduleTimeDAOImpl implements ScheduleTimeDAO {

	private SessionFactory sessionFactory;

    @SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(ScheduleTimeDAOImpl.class);
		
	public ScheduleTimeDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
	
	@Override
	@Transactional
	public List<ScheduleTime> ScheduleTimeList() {
		@SuppressWarnings("unchecked")
        List<ScheduleTime> listScheduleTime = (List<ScheduleTime>) sessionFactory.getCurrentSession()
                .createCriteria(ScheduleTime.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
 
        return listScheduleTime;
		
	}

	@Override
	@Transactional
	public boolean createScheduleTime(ScheduleTime ScheduleTime) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<ScheduleTime> listScheduleTime = (List<ScheduleTime>)session.createQuery("from ScheduleTime D where D.schedule_id = '"+ ScheduleTime.getSchedule_id() + "'").list();
		
		if(listScheduleTime.isEmpty()) {
			session.save(ScheduleTime);
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	@Transactional
	public boolean deleteScheduleTime(int ScheduleTimeId) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<ScheduleTime> listScheduleTime = (List<ScheduleTime>)session.createQuery("from ScheduleTime D where D.schedule_id = '"+ ScheduleTimeId +"'").list();
		if(listScheduleTime.isEmpty()) {
			return false;
		}
		else {
			session.delete(listScheduleTime.get(0));
			return true;
		}
	}

	@Override
	@Transactional
	public boolean updateScheduleTime(ScheduleTime scheduleTime) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<ScheduleTime> listScheduleTime = (List<ScheduleTime>)session.createQuery("from ScheduleTime D where D.schedule_id = '"+ scheduleTime.getSchedule_id() +"'").list();
		if(listScheduleTime.isEmpty()) {
			return false;
		}
		else {
			ScheduleTime ScheduleTimeToUpdate = listScheduleTime.get(0);
			ScheduleTimeToUpdate.setHh(scheduleTime.getHh());
			ScheduleTimeToUpdate.setMm(scheduleTime.getMm());
			session.update(ScheduleTimeToUpdate);
			return true;
		}
	}

}
