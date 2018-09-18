package com.hexaware.insight360DataPull.dao;

import java.util.List;

import com.hexaware.insight360DataPull.model.Release;

public interface ReleaseDAO {

	public List<Release> ReleaseList();
    public List<Release> getReleaseListForProject(int projectID);
    public List<Release> getActiveReleaseListForProject(int projectID);
    public Release getRelease(int releaseID);
    Release getRelease(String releaseName, int projectID);
    public boolean createRelease(Release release);
    public boolean deleteRelease(int releaseID);
    public boolean updateRelease(Release release);
    public boolean updatePullRelease(Release release);
	
}
