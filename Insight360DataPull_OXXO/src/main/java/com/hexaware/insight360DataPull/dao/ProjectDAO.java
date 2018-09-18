package com.hexaware.insight360DataPull.dao;

import java.util.List;

import com.hexaware.insight360DataPull.model.Project;

public interface ProjectDAO {
    public List<Project> ProjectList();
    public List<Project> getProjectList(String username);
    public List<Project> getActiveProjectListForUser();
    public Project getProject(int projectID);
    public Project getProject(String projectName, String domainName);
    public boolean createProject(Project project);
    public boolean deleteProject(int projectID);
    public boolean updateProject(Project project);
    public Project getProject(String projectName);
}
