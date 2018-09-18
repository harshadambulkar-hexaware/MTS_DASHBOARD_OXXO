package com.hexaware.insight360DataPull.dao;

import java.util.List;
import java.util.Map;

import com.hexaware.insight360DataPull.model.DsrCrHistory;

public interface DsrCrHistoryDAO {
	
	public List<DsrCrHistory> DsrCrHistoryList();	
	public List<Map<String, Object>> DsrTCHistoryGraph(int ReleaseID);
	public List<Map<String, Object>> DsrCRHistoryGraph(int ReleaseID);
	boolean createDsrHistory(DsrCrHistory dsrCrHistory, String test_phase);
	boolean updateDsrHistory(DsrCrHistory dsrCrHistory,String test_phase);
	
}
