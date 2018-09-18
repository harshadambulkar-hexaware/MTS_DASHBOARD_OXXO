/*package com.hexaware.insight360DataPull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.hexaware.insight360DataPull.dao.DefectDAO;
import com.hexaware.insight360DataPull.dao.ReleaseDAO;
import com.hexaware.insight360DataPull.dao.RequirementProgressDAO;
import com.hexaware.insight360DataPull.dao.TrendsHistoryStorageDAO;
import com.hexaware.insight360DataPull.model.Defect;
import com.hexaware.insight360DataPull.model.Release;
import com.hexaware.insight360DataPull.model.RequirementProgress;
import com.hexaware.insight360DataPull.model.TrendsHistoryStorage;
import com.hexaware.insight360DataPull.services.Constants;

public class TrendsHistoryStorageService {
	
	@Autowired
    private ReleaseDAO releaseDao;	
	@Autowired
	private DefectDAO defectDao;	
	@Autowired
	private TrendsHistoryStorageDAO trendsHistoryStorageDao;
	@Autowired
    private RequirementProgressDAO requirementProgressDao;
	
	//Initialize all data access objects(DAOs required for the data pull)
	public TrendsHistoryStorageService(ReleaseDAO releaseDAO, DefectDAO defectDAO, TrendsHistoryStorageDAO trendsHistoryStorageDAO, RequirementProgressDAO requirementProgressDAO) {
		releaseDao = releaseDAO;
		defectDao = defectDAO;
		trendsHistoryStorageDao = trendsHistoryStorageDAO;
		requirementProgressDao = requirementProgressDAO;
	}
	
	public boolean saveTrendsHistory(){
		
		final Logger logger = Logger.getLogger(TrendsHistoryStorageService.class);		
		
		List<Release> releasesList = releaseDao.ReleaseList();
		logger.info("Release Size :"+releasesList.size());
		
		for(Release releaseObject : releasesList){
			
			logger.info("Release Name :"+releaseObject.getRelease_name()+" : "+releaseObject.getPhase());
			if(releaseObject.getPhase()!= null){
			if(releaseObject.getPhase().contains("SIT")){
				int sitReleaseEntityId = releaseObject.getRelease_entity_id();
				
				//Get Current Date 
				DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
				Date dateToday = new Date();
				String currentDate = dateFormat.format(dateToday);
				
				List<String> rootCauses = defectDao.getRCAValuesForRelease(sitReleaseEntityId);
				logger.info("rootCauses :"+rootCauses);
				
				if(rootCauses.isEmpty()!=true){
				for(String rootCause : rootCauses){
					TrendsHistoryStorage trendsrootCausesHistoryStorageToAdd = new TrendsHistoryStorage();
					int defectsCountByRootCause = defectDao.getDefectsCountByRootCauseForTrendsHistory(sitReleaseEntityId, releaseObject.getRelease_name(), rootCause, Constants.ConfirmedDefctsStates, "SIT");
					trendsrootCausesHistoryStorageToAdd.setRootCause(rootCause);
					trendsrootCausesHistoryStorageToAdd.setDefectCountByRootCause(defectsCountByRootCause);
					trendsrootCausesHistoryStorageToAdd.setReleaseId(sitReleaseEntityId);
					trendsrootCausesHistoryStorageToAdd.setReleaseName(releaseObject.getRelease_name());
					trendsrootCausesHistoryStorageToAdd.setProjectId(releaseObject.getRelease_project_id());
					trendsrootCausesHistoryStorageToAdd.setPhase("SIT");
					trendsrootCausesHistoryStorageToAdd.setDataInsertedDate(currentDate);
					boolean isCreated = trendsHistoryStorageDao.createTrendsHistoryStorageForRootCause(trendsrootCausesHistoryStorageToAdd);
					logger.info("SIT Trends History created for Root Cause :"+isCreated);
				}
				}
				
				List<String> modulesList = defectDao.getModuleValuesForRelease(sitReleaseEntityId);
				logger.info("modulesList :"+modulesList);
				
				if(modulesList.isEmpty()!=true){
				for(String Module : modulesList){
					TrendsHistoryStorage trendsModuleHistoryStorageToAdd = new TrendsHistoryStorage();
					int defectsCountByModule = defectDao.getDefectsCountByModuleForTrendsHistory(sitReleaseEntityId, releaseObject.getRelease_name(), Module, Constants.ConfirmedDefctsStates, "SIT");
					trendsModuleHistoryStorageToAdd.setModule(Module);
					trendsModuleHistoryStorageToAdd.setDefectCountByModule(defectsCountByModule);
					trendsModuleHistoryStorageToAdd.setReleaseId(sitReleaseEntityId);
					trendsModuleHistoryStorageToAdd.setReleaseName(releaseObject.getRelease_name());
					trendsModuleHistoryStorageToAdd.setProjectId(releaseObject.getRelease_project_id());
					trendsModuleHistoryStorageToAdd.setPhase("SIT");
					trendsModuleHistoryStorageToAdd.setDataInsertedDate(currentDate);
					boolean isCreated = trendsHistoryStorageDao.createTrendsHistoryStorageForModule(trendsModuleHistoryStorageToAdd);
					logger.info("SIT Trends History created for Module :"+isCreated);
				}
				}
				
				TrendsHistoryStorage trendsHistoryStorageToAdd = new TrendsHistoryStorage();
				int passedTCs = requirementProgressDao.getPassedTCsForTrendsHistory(sitReleaseEntityId);
	    		int failedTCs = requirementProgressDao.getFailedTCsForTrendsHistory(sitReleaseEntityId);
	    		int retestCases = requirementProgressDao.getIPCaseCasesForTrendsHistory(sitReleaseEntityId);
				int executedTCs = passedTCs + failedTCs + retestCases;
				int TotalsTCs=requirementProgressDao.getTotalTCs(sitReleaseEntityId);
				List<Defect> totConfirmedDefectList=defectDao.getConfirmDefectsforReleaseForPhase(sitReleaseEntityId, "SIT");
				int testDefectProductivity = totConfirmedDefectList.size();
				float initPassPerc = executedTCs > 0 ? (((float)passedTCs/(float)executedTCs)*100) : 0;
				
				float defectScore = calculateDefectScore(sitReleaseEntityId, "SIT");
				float failureScore = calculateFailureScore(sitReleaseEntityId, "SIT");
				float codeDeliverySlipScore = codeDeliverySleepScore(sitReleaseEntityId);
				
				logger.info("SIT defectScore : "+defectScore);
				logger.info("SIT failureScore : "+failureScore);
				logger.info("SIT codeDeliverySlipScore : "+codeDeliverySlipScore);
				
				trendsHistoryStorageToAdd.setReleaseId(sitReleaseEntityId);
				trendsHistoryStorageToAdd.setReleaseName(releaseObject.getRelease_name());
				trendsHistoryStorageToAdd.setProjectId(releaseObject.getRelease_project_id());
				trendsHistoryStorageToAdd.setPhase("SIT");
				trendsHistoryStorageToAdd.setPassedTestCases(passedTCs);
				trendsHistoryStorageToAdd.setFailedTestCases(failedTCs);
				trendsHistoryStorageToAdd.setPassedReTestCases(retestCases);
				trendsHistoryStorageToAdd.setDataInsertedDate(currentDate);
				trendsHistoryStorageToAdd.setIpr(initPassPerc);
				trendsHistoryStorageToAdd.setCodeDeliverySlipScore(codeDeliverySlipScore);
				trendsHistoryStorageToAdd.setDefectScore(defectScore);
				trendsHistoryStorageToAdd.setFailureScore(failureScore);
				trendsHistoryStorageToAdd.setTestDesignProductivity(TotalsTCs);
				trendsHistoryStorageToAdd.setTestExecutionProductivity(executedTCs);
				trendsHistoryStorageToAdd.setTestDefectProductivity(testDefectProductivity);
				
				boolean isCreated = trendsHistoryStorageDao.createTrendsHistoryStorage(trendsHistoryStorageToAdd);
				logger.info("SIT Trends History stored :"+isCreated);
			}
			
			if(releaseObject.getPhase().contains("UAT")){
				int uatReleaseEntityId = releaseObject.getRelease_entity_id();
				
				//Get Current Date
				DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
				Date dateToday = new Date();
				String currentDate = dateFormat.format(dateToday);
				
				List<String> rootCauses = defectDao.getRCAValuesForRelease(uatReleaseEntityId);
				logger.info("rootCauses :"+rootCauses);
				
				if(rootCauses.isEmpty()!=true){
				for(String rootCause : rootCauses){
					TrendsHistoryStorage trendsHistoryStorageToAdd = new TrendsHistoryStorage();
					int defectsCountByRootCause = defectDao.getDefectsCountByRootCauseForTrendsHistory(uatReleaseEntityId, releaseObject.getRelease_name(), rootCause, Constants.ConfirmedDefctsStates, "UAT");
					trendsHistoryStorageToAdd.setRootCause(rootCause);
					trendsHistoryStorageToAdd.setDefectCountByRootCause(defectsCountByRootCause);
					trendsHistoryStorageToAdd.setReleaseId(uatReleaseEntityId);
					trendsHistoryStorageToAdd.setReleaseName(releaseObject.getRelease_name());
					trendsHistoryStorageToAdd.setProjectId(releaseObject.getRelease_project_id());
					trendsHistoryStorageToAdd.setPhase("UAT");
					trendsHistoryStorageToAdd.setDataInsertedDate(currentDate);
					boolean isCreated = trendsHistoryStorageDao.createTrendsHistoryStorageForRootCause(trendsHistoryStorageToAdd);
					logger.info("UAT Trends History created for Root Cause :"+isCreated);
				}
				}
				
				List<String> modulesList = defectDao.getModuleValuesForRelease(uatReleaseEntityId);
				logger.info("modulesList :"+modulesList);
				
				if(modulesList.isEmpty()!=true){
				for(String Module : modulesList){
					TrendsHistoryStorage trendsHistoryStorageToAdd = new TrendsHistoryStorage();
					int defectsCountByModule = defectDao.getDefectsCountByModuleForTrendsHistory(uatReleaseEntityId, releaseObject.getRelease_name(), Module, Constants.ConfirmedDefctsStates, "UAT");
					trendsHistoryStorageToAdd.setModule(Module);
					trendsHistoryStorageToAdd.setDefectCountByModule(defectsCountByModule);
					trendsHistoryStorageToAdd.setReleaseId(uatReleaseEntityId);
					trendsHistoryStorageToAdd.setReleaseName(releaseObject.getRelease_name());
					trendsHistoryStorageToAdd.setProjectId(releaseObject.getRelease_project_id());
					trendsHistoryStorageToAdd.setPhase("UAT");
					trendsHistoryStorageToAdd.setDataInsertedDate(currentDate);
					boolean isCreated = trendsHistoryStorageDao.createTrendsHistoryStorageForModule(trendsHistoryStorageToAdd);
					logger.info("UAT Trends History created for Module :"+isCreated);
				}
				}
				
				TrendsHistoryStorage uatTrendsHistoryStorageToAdd = new TrendsHistoryStorage();
				
				int passedTCs = requirementProgressDao.getPassedTCsForTrendsHistory(uatReleaseEntityId);
	    		int failedTCs = requirementProgressDao.getFailedTCsForTrendsHistory(uatReleaseEntityId);
	    		int retestCases = requirementProgressDao.getIPCaseCasesForTrendsHistory(uatReleaseEntityId);
				int executedTCs = passedTCs + failedTCs + retestCases;	
				
				int TotalsTCs=requirementProgressDao.getTotalTCs(uatReleaseEntityId);
				
				List<Defect> totConfirmedDefectList=defectDao.getConfirmDefectsforReleaseForPhase(uatReleaseEntityId, "UAT");
				int testDefectProductivity = totConfirmedDefectList.size();
				
				float initPassPerc = executedTCs > 0 ? (((float)passedTCs/(float)executedTCs)*100) : 0;
				
				float defectScore = calculateDefectScore(uatReleaseEntityId, "UAT");
				float failureScore = calculateFailureScore(uatReleaseEntityId, "UAT");
				float codeDeliverySlipScore = codeDeliverySleepScore(uatReleaseEntityId);
				
				logger.info("UAT defectScore : "+defectScore);
				logger.info("UAT failureScore : "+failureScore);
				logger.info("UAT codeDeliverySlipScore : "+codeDeliverySlipScore);
				
				uatTrendsHistoryStorageToAdd.setReleaseId(uatReleaseEntityId);
				uatTrendsHistoryStorageToAdd.setReleaseName(releaseObject.getRelease_name());
				uatTrendsHistoryStorageToAdd.setProjectId(releaseObject.getRelease_project_id());
				uatTrendsHistoryStorageToAdd.setPhase("UAT");
				uatTrendsHistoryStorageToAdd.setPassedTestCases(passedTCs);
				uatTrendsHistoryStorageToAdd.setFailedTestCases(failedTCs);
				uatTrendsHistoryStorageToAdd.setPassedReTestCases(retestCases);
				uatTrendsHistoryStorageToAdd.setDataInsertedDate(currentDate);
				uatTrendsHistoryStorageToAdd.setIpr(initPassPerc);
				uatTrendsHistoryStorageToAdd.setCodeDeliverySlipScore(codeDeliverySlipScore);
				uatTrendsHistoryStorageToAdd.setDefectScore(defectScore);
				uatTrendsHistoryStorageToAdd.setFailureScore(failureScore);
				uatTrendsHistoryStorageToAdd.setTestDesignProductivity(TotalsTCs);
				uatTrendsHistoryStorageToAdd.setTestExecutionProductivity(executedTCs);				
				uatTrendsHistoryStorageToAdd.setTestDefectProductivity(testDefectProductivity);
				
				TrendsHistoryStorage returnedTrendsHistoryStorage = trendsHistoryStorageDao.getTrendsHistoryData(uatReleaseEntityId, "UAT");
				
				if(returnedTrendsHistoryStorage != null && returnedTrendsHistoryStorage.getDataInsertedDate()!=currentDate){
				boolean isCreated = trendsHistoryStorageDao.createTrendsHistoryStorage(uatTrendsHistoryStorageToAdd);
				logger.info("UAT Trends History stored :"+isCreated);
				}
				else{
					logger.info("UAT Trends History Not stored : ");
				}
							
			}
			
			}
			
		}
		
		return true;
	}
	
	//Method to calculate defct score for the release 
    private float calculateDefectScore(int ReleaseID, String Phase) {
    	
    	int totDefectWeightage=0;
    	for(String selectedSeverity: Constants.DEFECT_SEVERITY_LIST) {
    		List<Defect> defectsBySeverity=defectDao.getDefectCountBySeverityForPhase(ReleaseID,selectedSeverity,Phase);
    		System.out.println(selectedSeverity+" Count===>"+defectsBySeverity.size());
    		int severityWeight = Constants.defectWeightMap.get(selectedSeverity);    		
    		totDefectWeightage=totDefectWeightage+(defectsBySeverity.size() * severityWeight);
    	}
    	List<Defect> totConfirmedDefects=defectDao.getConfirmDefectsforReleaseForPhase(ReleaseID, Phase);
    	System.out.println("Total confirmed defects ===>"+totConfirmedDefects.size());
		return ((float)totDefectWeightage/(float)totConfirmedDefects.size());
	
    	}
    
    //Method to calculate Failure score for the release 
    private float calculateFailureScore(int ReleaseID, String Phase) {
    	
    	 int devOwnedDefects = defectDao.getDefectsByRootCauseforReleaseForPhase(ReleaseID, Constants.ConfirmedDefctsStates, Constants.devOwnedRootCauseList, Phase).size();
		 int totConfirmedDefects=defectDao.getConfirmDefectsforReleaseForPhase(ReleaseID, Phase).size();
		 return ((float)devOwnedDefects/(float)totConfirmedDefects);
    }
    
    //Method to calculate Failure score for the release 
    private float codeDeliverySleepScore(int releaseId) {
    	List<RequirementProgress> totalReleasedReqByScope=requirementProgressDao.getRequirementListForReleaseByScope(releaseId); 
    	int delayedReqCount=0;
    	for (RequirementProgress requirementProgress : totalReleasedReqByScope) {
    		
    		if(requirementProgress.getActual_dev_certification() != null && requirementProgress.getPlanned_dev_certification() != null) {
    			if(requirementProgress.getActual_dev_certification().after(requirementProgress.getPlanned_dev_certification())) {
    				++delayedReqCount;
    			}
    		}
    	}
    		return ((float)delayedReqCount/(float)totalReleasedReqByScope.size());
    	}

}
*/