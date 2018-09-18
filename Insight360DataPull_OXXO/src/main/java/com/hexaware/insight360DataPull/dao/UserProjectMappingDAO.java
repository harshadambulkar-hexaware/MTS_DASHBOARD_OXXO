package com.hexaware.insight360DataPull.dao;

import java.util.List;

import com.hexaware.insight360DataPull.model.Project;
import com.hexaware.insight360DataPull.model.User;

public interface UserProjectMappingDAO {
	public List<User> userListForProject(int projectID);
    public List<Project> projectListForUser(int userID);
    public boolean subscribeProject(int userID, int projectID);
    public boolean unsubscribeProject(int userID, int projectID);
}
