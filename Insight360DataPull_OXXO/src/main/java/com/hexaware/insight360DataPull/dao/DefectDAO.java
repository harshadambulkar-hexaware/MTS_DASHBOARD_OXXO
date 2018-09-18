package com.hexaware.insight360DataPull.dao;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.hexaware.insight360DataPull.model.Defect;

public interface DefectDAO {
	
	public List<Defect> DefectList();
    public List<Defect> getDefectListForRelease(int ReleaseID);
    public List<Defect> getDefectsBySeverity(int ReleaseID, String defectSeverity);
    public List<Defect> getDefectsByState(int ReleaseID, String defectState);
    public List<Defect> getDefectsBySeverityState(int ReleaseID, String defectState, String defectSeverity);
    public Defect getDefect(int DefectID);
    public boolean createDefect(Defect defect);
    public boolean deleteDefect(int DefectID);
    public boolean updateDefect(Defect defect);
    public Map<String, Object> getAgeingData(int ReleaseID, int fromDays, int toDays) throws ParseException;
    //Added for New Report Open Defects - Root Cause Analysis
    public List<Defect> getDefectsByRootCause(int ReleaseID, String defectState, String rootCauseAnalysis);
	public List<String> getRCAValuesForRelease(int releaseIDs);
	public int getDefectsCountByRootCauseForTrendsHistory(int releaseId, String releaseName,String rootCause, String confirmedDefctsStates, String phase);
	public List<String> getModuleValuesForRelease(int releaseIDs);
	public int getDefectsCountByModuleForTrendsHistory(int releaseId, String releaseName, String rootCause, String confirmedDefctsStates, String phase);
	List<Defect> getDefectCountBySeverityForPhase(int releaseId, String selectedSeverity, String Phase);
	List<Defect> getDefectsByRootCauseforReleaseForPhase(int ReleaseID, String defectStates, String rootCauseList, String Phase);
	List<Defect> getConfirmDefectsforReleaseForPhase(int ReleaseID, String phase);
}
