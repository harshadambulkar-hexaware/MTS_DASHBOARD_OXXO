package com.hexaware.insight360DataPull.dao;

import java.util.List;
import java.util.Map;

import com.hexaware.insight360DataPull.model.RequirementProgress;

@SuppressWarnings("unused")
public interface RequirementProgressDAO {
	
	public boolean createRequirement(RequirementProgress requirement_progress, String Phase);
    public boolean deleteRequirement(int RequirementID);
    public boolean updateRequirement(RequirementProgress requirement_progress);
    public int getTotalTCs(int requirementID, int releaseID,String Phase);
    public int getPassedTCs(int requirementID, int releaseID,String Phase);
    public int getFailedTCs(int requirementID, int releaseID,String Phase);
    public RequirementProgress getRequirement(int requirementEntityID);
    public RequirementProgress getRequirement(int requirementID, int releaseID,String Phase);
	int getFailedTCsForTrendsHistory(int ReleaseID);
	int getPassedTCsForTrendsHistory(int ReleaseID);
	int getIPCaseCasesForTrendsHistory(int ReleaseID);
	List<RequirementProgress> getRequirementListForReleaseByScope(int releaseId);
	int getTotalTCs(int ReleaseID);
}
