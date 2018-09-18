package com.hexaware.insight360DataPull.dao;

import java.util.List;

import com.hexaware.insight360DataPull.model.ScopeAnalysis;

public interface ScopeAnalysisDAO {
	
	public List<ScopeAnalysis> ScopeAnalysisList();
	public List<ScopeAnalysis> ScopeAnalysisListForRelease(int ReleaseID);

}
