package com.hexaware.insight360DataPull.dao;

import java.util.List;

import com.hexaware.insight360DataPull.model.Requirement;

public interface RequirementDAO {
	
	public List<Requirement> RequirementList();
    public List<Requirement> getRequirementListForRelease(int ReleaseID);
    public List<Requirement> getCertifiedCR(int ReleaseID);
    public Requirement getRequirement(int requirementEntityID);
    public Requirement getRequirement(int requirementID, int releaseID);
    public boolean createRequirement(Requirement requirement);
    public boolean deleteRequirement(int RequirementID);
    public boolean updateRequirement(Requirement requirement);    
    
}
