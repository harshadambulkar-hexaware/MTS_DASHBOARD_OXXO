package com.hexaware.insight360DataPull.dao;

import java.util.List;

import com.hexaware.insight360DataPull.model.DefectsHistory;

public interface DefectsHistoryDAO {
	
	public List<DefectsHistory> DefectsHistoryList();
	public List<DefectsHistory> DefectsHistoryListForRelease(int ReleaseID);
	public boolean createDefectsHistory(DefectsHistory defectsHistory,String test_phase);
	public boolean updateDefectsHistory(DefectsHistory defectsHistory,String test_phase);

}
