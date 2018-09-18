package com.hexaware.insight360DataPull.dao;

import java.util.List;

import com.hexaware.insight360DataPull.model.TestingAnalysis;

public interface TestingAnalysisDAO {
	
	public List<TestingAnalysis> DefectList();
	public List<TestingAnalysis> DefectListForRelease(int ReleaseID);

}
