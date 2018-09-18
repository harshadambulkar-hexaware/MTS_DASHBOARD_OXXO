package com.hexaware.insight360DataPull.dao;

import java.util.List;

import com.hexaware.insight360DataPull.model.DefectDevAnalysis;

public interface DefectDevAnalysisDAO {
	
	public List<DefectDevAnalysis> DefectDevAnalysisList();
	public List<DefectDevAnalysis> DefectDevAnalysisListForRelease(int ReleaseID);

}
