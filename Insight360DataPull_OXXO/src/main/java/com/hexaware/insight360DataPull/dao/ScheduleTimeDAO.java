package com.hexaware.insight360DataPull.dao;

import java.util.List;

import com.hexaware.insight360DataPull.model.ScheduleTime;

public interface ScheduleTimeDAO {

	public List<ScheduleTime> ScheduleTimeList();
	public boolean createScheduleTime(ScheduleTime scheduleTime);
	public boolean updateScheduleTime(ScheduleTime scheduleTime);
	public boolean deleteScheduleTime(int scheduleTimeId);
}
