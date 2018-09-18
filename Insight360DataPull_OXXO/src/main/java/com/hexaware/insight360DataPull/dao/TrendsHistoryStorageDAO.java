package com.hexaware.insight360DataPull.dao;

import com.hexaware.insight360DataPull.model.TrendsHistoryStorage;

public interface TrendsHistoryStorageDAO {
	
	public boolean createTrendsHistoryStorage(TrendsHistoryStorage trendsHistoryStorage);

	public boolean updateTrendsHistoryStorage(TrendsHistoryStorage trendsHistoryStorage);

	public boolean createTrendsHistoryStorageForRootCause(TrendsHistoryStorage trendsHistoryStorage);
	
	public boolean createTrendsHistoryStorageForModule(TrendsHistoryStorage trendsHistoryStorage);
	
	public TrendsHistoryStorage getTrendsHistoryData(int releaseId,String Phase);

}
