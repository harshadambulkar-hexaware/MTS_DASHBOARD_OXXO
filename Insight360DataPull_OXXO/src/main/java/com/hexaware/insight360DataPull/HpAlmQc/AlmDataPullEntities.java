package com.hexaware.insight360DataPull.HpAlmQc;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.web.client.RestClientException;

import com.hexaware.insight360DataPull.dao.DatapullLogDAO;
import com.hexaware.insight360DataPull.dao.DefectDAO;
import com.hexaware.insight360DataPull.dao.DefectsHistoryDAO;
import com.hexaware.insight360DataPull.dao.DsrCrHistoryDAO;
import com.hexaware.insight360DataPull.dao.ProjectDAO;
import com.hexaware.insight360DataPull.dao.ReleaseDAO;
import com.hexaware.insight360DataPull.dao.RequirementDAO;
import com.hexaware.insight360DataPull.dao.RequirementProgressDAO;
import com.hexaware.insight360DataPull.dao.TestDAO;
import com.hexaware.insight360DataPull.dao.UserDAO;
import com.hexaware.insight360DataPull.model.DatapullLog;
import com.hexaware.insight360DataPull.model.Defect;
import com.hexaware.insight360DataPull.model.DefectsHistory;
import com.hexaware.insight360DataPull.model.DsrCrHistory;
import com.hexaware.insight360DataPull.model.Project;
import com.hexaware.insight360DataPull.model.Release;
import com.hexaware.insight360DataPull.model.Requirement;
import com.hexaware.insight360DataPull.model.RequirementProgress;
import com.hexaware.insight360DataPull.model.Test;
import com.hexaware.insight360DataPull.services.commonMethods;

/*
 * @author Hexaware Technologies
 * Connects to ALM using AlmClient class
 * Gets parsed reaponces from AlmClients class and pass it to the Hibernate DAOs
 * Gets DAOs as constructor parameters 
 * */
public class AlmDataPullEntities {
	
	private static final Logger logger = Logger.getLogger(AlmDataPullEntities.class);
	
	//Creating instances for models
	private ProjectDAO projectDao;
	private ReleaseDAO releaseDao;
	private RequirementDAO requirementDao;
	private RequirementProgressDAO requirementprogressDao;
	private DefectDAO defectDao;
	private DefectsHistoryDAO defectshistoryDao;
	private DsrCrHistoryDAO dsrcrhistoryDao;
	private TestDAO testDao;
	private DatapullLogDAO datapulllogDao;
	@SuppressWarnings("unused")
	private UserDAO userDao;
	private String rel_ind;
	private String req_ind;
	private String phases;
	public HashMap<String, String> field_list = new HashMap<String, String>();
	
	//Initialize all data access objects(DAOs required for the data pull)
	public AlmDataPullEntities(ProjectDAO projectDAO, ReleaseDAO releaseDAO, RequirementDAO requirementDAO, DefectDAO defectDAO, DefectsHistoryDAO defectshistoryDAO, DsrCrHistoryDAO dsrcrhistoryDAO, TestDAO testDAO,RequirementProgressDAO requirementprogressDAO, DatapullLogDAO datapulllogDAO) {
		projectDao = projectDAO;
		releaseDao = releaseDAO;
		requirementDao = requirementDAO;
		requirementprogressDao = requirementprogressDAO;
		defectDao = defectDAO;
		defectshistoryDao = defectshistoryDAO;
		dsrcrhistoryDao = dsrcrhistoryDAO;
		testDao = testDAO;
		datapulllogDao = datapulllogDAO;
		logger.info("AlmDataPullEntities Constructor Called");
	}
	
	/*
	 * Automates the data pull process using Project and Release active status
	 */
	public boolean updateAlmData() throws JsonParseException, JsonMappingException, IOException, ParseException, RestClientException, URISyntaxException, KeyManagementException, KeyStoreException, NoSuchAlgorithmException {
		
		//Requests and iterates through all the domains
		for(String domainName : AlmClient.GetDomains()) {
			//Requests and iterates through all the projects 
			for(String projectName : AlmClient.GetProjects(domainName)) {
				
				//Initializing model object for inserting or updating
				Project projectToAdd = new Project();
				projectToAdd.setprojectName(projectName);
				projectToAdd.setisActive(0);
				projectToAdd.setisArchived(0);
				projectToAdd.setisPlatform(0);
				projectToAdd.setdisplayName(projectName);
				projectToAdd.setdomainName(domainName);
				projectToAdd.setHealth("Amber");
				projectToAdd.setSub_business_line_id(1);
				projectToAdd.setData_src_tool(AlmConstants.dataSrcAlmQc);
				projectDao.createProject(projectToAdd);
				
				//Getting and checking whether this project is active or not
				Project project = projectDao.getProject(projectName, domainName);
				
				//If project is active then calls updateRelease Method which updates release data
				if(project.getisActive() == 1){
					
					rel_ind = project.getSit_uat_rel_ind();
					req_ind = project.getSit_uat_req_ind();
					phases = project.getTest_scope();
					
				// Get Custom Fields for each project in a domain	
					field_list = AlmClient.Get_CustomFields(domainName, projectName);
					logger.info("Custom Fields - " +projectName+ " " + field_list);
					logger.info("Check_Key" + field_list.get("release_Go Live Date"));
					//updateReleaseData(project.getprojectId(), projectName, domainName, rel_ind,req_ind,phases);
					updateReleaseData(project.getprojectId(), projectName, domainName, rel_ind,req_ind,phases);
					
				}
			}
		}
		return true;
	}
	
	/*
	 * Updates the Release data and automates the updation of the child entity update 
	 * using active ststus of the release
	 * */
	public boolean updateReleaseData(int projectID, String projectName, String domainName,String rel_indicator,String req_indicator,String phase) throws JsonParseException, JsonMappingException, IOException, ParseException, RestClientException, URISyntaxException, KeyManagementException, KeyStoreException, NoSuchAlgorithmException {
		
		//Initializing the entityResourceName
		String entityResource = "releases";
		
		//Initializing the query string parameters
		StringBuilder queryString = new StringBuilder();
		//adding query string parameter for getting response in JSON format
		queryString.append("?alt=application/json");
		//queryString.append("&page-size=1");
		
		//Calling the requestResource method of the AlmClient class
		List<Map<String, Object>> responseMapList = AlmClient.requestResource(domainName, projectName,entityResource, queryString.toString());
		
		//Iterating through all the entities we have got in the response
		for(Map<String, Object> singleEntityMap : responseMapList) {
		
						
			//Creating model object for the release model
			Release entityToAdd = new Release();
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			
			//Setting model properties from the response map 
			entityToAdd.setRelease_id(Integer.parseInt(singleEntityMap.get("id").toString()));
			entityToAdd.setRelease_project_id(projectID);
			entityToAdd.setRelease_name(singleEntityMap.get("name").toString());
			entityToAdd.setIs_active(0);
			entityToAdd.setIs_archived(0);
			entityToAdd.setData_src_tool(AlmConstants.dataSrcAlmQc);
			entityToAdd.setPhase(phases);
			
			// Updated for Citi TTS dashboard implementation
			/*
			if(singleEntityMap.get("user-01") != null)
				entityToAdd.setGo_live_date(dateFormat.parse(singleEntityMap.get("user-01").toString()));
			
			if(singleEntityMap.get("user-02") != null)
				entityToAdd.setIst_planned_start(dateFormat.parse(singleEntityMap.get("user-02").toString()));
			if(singleEntityMap.get("user-05") != null)
				entityToAdd.setIst_planned_end(dateFormat.parse(singleEntityMap.get("user-05").toString()));
			if(singleEntityMap.get("user-04") != null)
				entityToAdd.setUat_planned_start(dateFormat.parse(singleEntityMap.get("user-04").toString()));
			if(singleEntityMap.get("user-06") != null)
				entityToAdd.setUat_planned_end(dateFormat.parse(singleEntityMap.get("user-06").toString())); */
		//Commented based on GBS project difference	
			
			 if(singleEntityMap.get(field_list.get("release_Release_Go Live Date").toString()) != null)
				entityToAdd.setGo_live_date(dateFormat.parse(singleEntityMap.get(field_list.get("release_Release_Go Live Date").toString()).toString()));
			if(singleEntityMap.get(field_list.get("release_Release_Initial Planned IST Start").toString()) != null)
				entityToAdd.setIst_planned_start(dateFormat.parse(singleEntityMap.get(field_list.get("release_Release_Initial Planned IST Start").toString()).toString()));
			if(singleEntityMap.get(field_list.get("release_Release_Initial Planned IST End").toString()) != null)
				entityToAdd.setIst_planned_end(dateFormat.parse(singleEntityMap.get(field_list.get("release_Release_Initial Planned IST End").toString()).toString()));
			if(singleEntityMap.get(field_list.get("release_Release_Initial Planned UAT Start").toString()) != null)
				entityToAdd.setUat_planned_start(dateFormat.parse(singleEntityMap.get(field_list.get("release_Release_Initial Planned UAT Start").toString()).toString()));
			if(singleEntityMap.get(field_list.get("release_Release_Initial Planned UAT End").toString()) != null)
				entityToAdd.setUat_planned_end(dateFormat.parse(singleEntityMap.get(field_list.get("release_Release_Initial Planned UAT End").toString()).toString())); 
		
			
			//Calling the hibernate method createRelese 
			boolean isCreated = releaseDao.createRelease(entityToAdd);
			
			if(!isCreated) {
				releaseDao.updatePullRelease(entityToAdd);
			
			}
			
			//Getting the release from hibernate for chechking its activation status
			//  Need to uncheck after successful updation fieldname by label
			Release release = releaseDao.getRelease(entityToAdd.getRelease_name(), projectID);
			
			//If release is active then call the update methods for the following entities
			//Requirements + Tests
			//Defects
			//Defect History
			
			
			logger.info("Release_entity_id:"+release.getRelease_entity_id());
	
			if(release.getIs_active() == 1){
				DatapullLog datapull_log_entity = new DatapullLog();
				datapull_log_entity.setProject_id(projectID);
				datapull_log_entity.setProject_name(projectName);
				datapull_log_entity.setRelease_id(release.getRelease_entity_id());
				//datapull_log_entity.setRelease_id(Integer.parseInt(singleEntityMap.get("id").toString()));
				datapull_log_entity.setRelease_name(singleEntityMap.get("name").toString());
				
				LocalDateTime now = LocalDateTime.now();
				DateTimeFormatter date_format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
				DateTimeFormatter time_format = DateTimeFormatter.ofPattern("HH:mm:ss");
				datapull_log_entity.setStart_date(date_format.format(now));
				datapull_log_entity.setStart_time(time_format.format(now));
				datapull_log_entity.setSrc_tool(AlmConstants.dataSrcAlmQc);
				int flag=0;
				 //if(rel_indicator.equals("Y") && req_indicator.equals("Y"))
				
				 if(rel_indicator == "Y" && req_indicator == "Y")
				 {
				  try {
					 updateRequirementsData(release.getRelease_entity_id() ,release.getRelease_id(), projectName, domainName);
					 LocalDateTime now2 = LocalDateTime.now();					
						datapull_log_entity.setEnd_date(date_format.format(now2));
						datapull_log_entity.setEnd_time(time_format.format(now2));
						datapull_log_entity.setReq_success(1);
						flag=1;
						//datapull_log_entity.setReq_exception("NULL");
				  }
				  catch (Exception e) {
					  logger.info("ExceptionAayaReq_Y:"+e);
					  LocalDateTime now2 = LocalDateTime.now();					
						datapull_log_entity.setEnd_date("NA");
						datapull_log_entity.setEnd_time("NA");
						datapull_log_entity.setReq_success(0);
						datapull_log_entity.setReq_exception(e.toString());
						flag=0;
				}
				  
				  
				 }
				 
				 else if(rel_indicator.equals("N")&& req_indicator.equals("N"))
				 {
					 try {
						 updateRequirementsData_Same_Rel_Same_Req(release.getRelease_entity_id() ,release.getRelease_id(), projectName, domainName,phase);
						 LocalDateTime now2 = LocalDateTime.now();					
							datapull_log_entity.setEnd_date(date_format.format(now2));
							datapull_log_entity.setEnd_time(time_format.format(now2));
							datapull_log_entity.setReq_success(1);
							flag=1;
					  }
					  catch (Exception e) {
						  logger.info("ExceptionAayaReq_N:"+e.getCause());
						  LocalDateTime now2 = LocalDateTime.now();					
							datapull_log_entity.setEnd_date("NA");
							datapull_log_entity.setEnd_time("NA");
							datapull_log_entity.setReq_success(0);
							datapull_log_entity.setReq_exception(e.getCause().toString());
							flag=0;
					}
					 //updateRequirementsData_Same_Rel_Same_Req(release.getRelease_entity_id() ,release.getRelease_id(), projectName, domainName,phase);
					
				 }
				 
				 if(flag==1)
				 {
					 try{
					 updateDefectsData(release.getRelease_entity_id() ,release.getRelease_id(), projectName, domainName);
					 LocalDateTime now2 = LocalDateTime.now();					
						datapull_log_entity.setEnd_date(date_format.format(now2));
						datapull_log_entity.setEnd_time(time_format.format(now2));
						datapull_log_entity.setDef_success(1);
					 }
					 catch (Exception e) {								
							datapull_log_entity.setEnd_date("NA");
							datapull_log_entity.setEnd_time("NA");
							datapull_log_entity.setDef_success(0);
							datapull_log_entity.setDef_exception(e.getCause().toString());
					 }				 	
				 }
				 else
				 {
					 datapull_log_entity.setEnd_date("NA");
					 datapull_log_entity.setEnd_time("NA");
					 datapull_log_entity.setDef_success(0);
					 datapull_log_entity.setDef_exception("Error in requirements datapull");
				 }
					
					boolean isDatapullLogCreated = datapulllogDao.createDatapullLog(datapull_log_entity);
					
				 // Check the phases and update the records accordingly in requirements progress table
		           String phaseArray[] = phases.split(",");
		           logger.info("Phases Length" + phaseArray.length);
		           
		           for (int i =0;i<= phaseArray.length-1;i++){
		        	   
		        	   if(phaseArray[i].equals("SIT"))
		        		   
		        	   {
		        		   try {
		        			   updateDefectsHistoryData(release.getRelease_entity_id(),release.getRelease_id(), projectName, domainName,phaseArray[i]);
		        			   datapull_log_entity.setDef_hist_success(1);
							
						} catch (Exception e) {
							datapull_log_entity.setDef_hist_success(0);
							datapull_log_entity.setDef_hist_exception(e.getCause().toString());
						}				        
		        	   }
		        	   
                        if(phaseArray[i].equals("UAT"))
		        		   
		        	   {
                        	try {
                        		updateDefectsHistoryData(release.getRelease_entity_id(),release.getRelease_id(), projectName, domainName,phaseArray[i]);
                        		datapull_log_entity.setDef_hist_success(1);

							} catch (Exception e) {
								datapull_log_entity.setDef_hist_success(0);
								datapull_log_entity.setDef_hist_exception(e.getCause().toString());								
							}				        
		        	   }
		           }
		       
			} 
			
		}
		
		return true;
	}
	
	/*
	 * Updates Reuirement details
	 * Calls requestResource method of AlmClient  
	 * Initializes Requirement model object
	 * Calls create requirement or updateRequirement method of RequirementDAO interface 
	 * */
	public boolean updateRequirementsData(int releaseEntityID, int releaseID, String projectName, String domainName) throws JsonParseException, JsonMappingException, RestClientException, IOException, URISyntaxException, ParseException, KeyManagementException, KeyStoreException, NoSuchAlgorithmException {
        
		//Initializing request parameters 
        String entityResource = "requirements";
        StringBuilder queryString = new StringBuilder();
        queryString.append("?alt=application/json");
        //Adding filter query to get tests details for selected requirement only 
        //Encoding {} using URL encoding characters '%7B' for '{' and '%7D' for '}' 
        queryString.append("&query=%7Btarget-rel[" + releaseID + "]%7D");
        //Calling requestResource
        List<Map<String, Object>> responseMapList = AlmClient.requestResource(domainName, projectName,entityResource, queryString.toString());
        
        //Iterates through all the requirements
        for(Map<String, Object> singleEntityMap : responseMapList) {
               
        	//Creating and Initializing the Requirement model object
           Requirement entityToAdd = new Requirement();
           RequirementProgress entityToAdd_1 = new RequirementProgress();
           
           //Setting property values to model
           
          
           entityToAdd.setReq_id(Integer.parseInt(singleEntityMap.get("id").toString()));
           entityToAdd.setReq_release_id(releaseEntityID);
           entityToAdd.setTitle(singleEntityMap.get("name").toString());
           entityToAdd.setDirect_coverage("status");
           entityToAdd.setData_src_tool(AlmConstants.dataSrcAlmQc);
           
           //For verifying date is available or not 
          
           Date todaysDate = new Date();
           
           // Original Certification Date
           if(singleEntityMap.get("user-10").toString().compareToIgnoreCase("Passed") == 0)
                 entityToAdd_1.setOrig_cert_date(todaysDate);
          
           
           if(singleEntityMap.get("last-modified") != null) {
                 DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
                 entityToAdd_1.setTimestamp(dateTimeFormat.parse(singleEntityMap.get("last-modified").toString()));
           }
           
           //Initializing test entity request
           String entityResourceTest = "tests";
           String rquirementID = singleEntityMap.get("id").toString();
           
           StringBuilder queryStringConsolTotal = new StringBuilder();
           queryStringConsolTotal.append("?alt=application/json");
           //Adding query parameter for getting required fields only for test request
           queryStringConsolTotal.append("&fields=id,exec-status,status,creation-time,last-modified");
           //Adding filter query to get tests details for selected requirement only 
           //Encoding {} using URL encoding characters '%7B' for '{' and '%7D' for '}' 
           queryStringConsolTotal.append("&query=%7Brequirement.id[" + rquirementID + "]%7D");
           List<Map<String, Object>> tcListForCR = AlmClient.requestResource(domainName, projectName, entityResourceTest, queryStringConsolTotal.toString());
           
           StringBuilder queryStringConsolPassedFirstRun = new StringBuilder();
           queryStringConsolPassedFirstRun.append("?alt=application/json");
           //Adding query parameter for getting required fields only for test request
           queryStringConsolPassedFirstRun.append("&fields=id,exec-status,status,creation-time,last-modified");
           //Adding filter query to get tests details for selected requirement only 
           //Encoding {} using URL encoding characters '%7B' for '{' and '%7D' for '}' 
           queryStringConsolPassedFirstRun.append("&query=%7Brequirement.id[" + rquirementID + "]%3Btest-instance.user-02[Y]%7D");
           List<Map<String, Object>> passedFirstRunListForCR = AlmClient.requestResource(domainName, projectName, entityResourceTest, queryStringConsolPassedFirstRun.toString());
           
           StringBuilder queryStringConsolPassedRetest = new StringBuilder();
           queryStringConsolPassedRetest.append("?alt=application/json");
           //Adding query parameter for getting required fields only for test request
           queryStringConsolPassedRetest.append("&fields=id,exec-status,status,creation-time,last-modified");
           //Adding filter query to get tests details for selected requirement only 
           //Encoding {} using URL encoding characters '%7B' for '{' and '%7D' for '}' 
           queryStringConsolPassedRetest.append("&query=%7Brequirement.id[" + rquirementID + "]%3Btest-instance.user-03[Y]%7D");
           List<Map<String, Object>> passedRetestListForCR = AlmClient.requestResource(domainName, projectName, entityResourceTest, queryStringConsolPassedRetest.toString());
          
           StringBuilder queryStringConsolFailed = new StringBuilder();
           queryStringConsolFailed.append("?alt=application/json");
           //Adding query parameter for getting required fields only for test request
           queryStringConsolFailed.append("&fields=id,exec-status,status,creation-time,last-modified");
           //Adding filter query to get tests details for selected requirement only 
           //Encoding {} using URL encoding characters '%7B' for '{' and '%7D' for '}' 
           queryStringConsolFailed.append("&query=%7Brequirement.id[" + rquirementID + "]%3Btest-instance.status[%27Failed%27]%7D");
           List<Map<String, Object>> failedListForCR = AlmClient.requestResource(domainName, projectName, entityResourceTest, queryStringConsolFailed.toString());
      
           
           
           entityToAdd_1.setNum_of_test_cases(tcListForCR.size());
           entityToAdd_1.setTcs_with_iterations(0);
           
           entityToAdd_1.setTcs_passed_first_run(passedFirstRunListForCR.size());
           entityToAdd_1.setTcs_failed_first_run(failedListForCR.size());
           entityToAdd_1.setDev_failures(0);
           entityToAdd_1.setTsc_pending_fun_request(0);
           entityToAdd_1.setTcs_pending(0);
           entityToAdd_1.setTcs_passed_retest(passedRetestListForCR.size());
           
           entityToAdd_1.setPlanning_estimate(0);
           entityToAdd_1.setExecution_estimate(0);      
           
           entityToAdd_1.setOrig_plnd_tcs_count(0);
           entityToAdd_1.setExecution_estimate(0);
           entityToAdd_1.setRequirement_defn_percentage(0);
           entityToAdd_1.setUx_progress_percentage(0);
           
           //Custom Fields
           
           //Requirement Module
           if(singleEntityMap.get("user-06") != null){
        	   entityToAdd.setModule(singleEntityMap.get("user-06").toString());
        	   entityToAdd_1.setReq_progress_module(singleEntityMap.get("user-06").toString());
        	   logger.info("setReq_progress_module if : "+ singleEntityMap.get("user-06").toString());
           }
           else{
        	   entityToAdd.setModule("Not Updated");
        	   entityToAdd_1.setReq_progress_module("Not Updated");
        	   logger.info("setReq_progress_module else : "+ "Not Updated");
           }
           
           //In Scope
           if(singleEntityMap.get("user-41") != null)
        	   entityToAdd_1.setIn_scope(singleEntityMap.get("user-41").toString());
           else
        	   entityToAdd_1.setIn_scope("N");
           
           // Execution estimate
           if(singleEntityMap.get("user-44") != null)
        	   entityToAdd_1.setExecution_estimate(Integer.parseInt(singleEntityMap.get("user-44").toString()));
           else
        	   entityToAdd_1.setExecution_estimate(0);
           
          /* //IST Planning Status
           if(singleEntityMap.get("user-45") != null)
        	   entityToAdd.setIst_(Integer.parseInt(singleEntityMap.get("user-45").toString()));
           else
        	   entityToAdd.setIst_execution_estimate(0);*/
           
           
         // Planned Planning Start date 
           if(singleEntityMap.get("user-47") != null) {
               DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd");
               entityToAdd_1.setPlanned_planning_start(dateTimeFormat.parse(singleEntityMap.get("user-47").toString()));
           }
           
           // Planned Planning End date 
           if(singleEntityMap.get("user-49") != null) {
               DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd");
               entityToAdd_1.setPlanned_planning_end(dateTimeFormat.parse(singleEntityMap.get("user-49").toString()));
           }
           
           // Actual Planning Start date 
           if(singleEntityMap.get("user-48") != null) {
               DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd");
               entityToAdd_1.setActual_planning_start(dateTimeFormat.parse(singleEntityMap.get("user-48").toString()));
           }
           
           // Actual Planning End date 
           if(singleEntityMap.get("user-50") != null) {
               DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd");
               entityToAdd_1.setActual_planning_end(dateTimeFormat.parse(singleEntityMap.get("user-50").toString()));
           }
           
                      
           // Planned Execution Start date 
           if(singleEntityMap.get("user-51") != null) {
               DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd");
               entityToAdd_1.setPlanned_execution_start(dateTimeFormat.parse(singleEntityMap.get("user-51").toString()));
           }
           
           // Planned Execution End date 
           if(singleEntityMap.get("user-53") != null) {
               DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd");
               entityToAdd_1.setPlanned_execution_end(dateTimeFormat.parse(singleEntityMap.get("user-53").toString()));
           }
           
           // Actual Execution Start date 
           if(singleEntityMap.get("user-52") != null) {
               DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd");
               entityToAdd_1.setActual_execution_start(dateTimeFormat.parse(singleEntityMap.get("user-52").toString()));
           }
           
           //Actual Execution End date 
           if(singleEntityMap.get("user-54") != null) {
               DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd");
               entityToAdd_1.setActual_execution_end(dateTimeFormat.parse(singleEntityMap.get("user-54").toString()));
           }
           
           
           
         //Planned Dev certification date 
         if(singleEntityMap.get("user-30") != null) {
             DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd");
             entityToAdd_1.setPlanned_dev_certification(dateTimeFormat.parse(singleEntityMap.get("user-09").toString()));
         }
         
         //Actual Dev certification date 
         if(singleEntityMap.get("user-31") != null) {
             DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd");
             entityToAdd_1.setActual_dev_certification(dateTimeFormat.parse(singleEntityMap.get("user-10").toString()));
         }       
           
           
         

         
         //Create new requirement using inialized model  
         boolean isCreated = requirementDao.createRequirement(entityToAdd);
         
         logger.info("Requirement is being created : " + isCreated);
         
         //If requirement is already available then update the same
         if(!isCreated) {
        	 
        	 isCreated = requirementDao.updateRequirement(entityToAdd);
        	 logger.info("Requirement is being updated : " + isCreated);
         }
     
         // 
         
         boolean isCreated_1 = requirementprogressDao.createRequirement(entityToAdd_1,"Test");
         
       
         
       //If requirement is already available in requirements progress then update the same for req progress
         if(!isCreated_1) {
        	 
        	 isCreated_1 = requirementprogressDao.updateRequirement(entityToAdd_1);
        	 logger.info("Requirement is being updated : " + isCreated);
         }
         
         
        //Select the newly added requirement  
      
		RequirementProgress requirementAdded = requirementprogressDao.getRequirement(entityToAdd_1.getReq_id(), entityToAdd_1.getReq_release_id(),entityToAdd_1.getTest_phase());
        
        if(requirementAdded != null)
        {
        //Update the Daily History Records for the selected release
         //updateDsrHistoryData(requirementAdded);
         //updateTestData(requirementAdded, tcListForCR);       
        }
		   
         
       } 
        return false;
 }

	

public boolean updateRequirementsData_Same_Rel_Same_Req(int releaseEntityID, int releaseID, String projectName, String domainName, String test_phases) throws JsonParseException, JsonMappingException, RestClientException, IOException, URISyntaxException, ParseException, KeyManagementException, KeyStoreException, NoSuchAlgorithmException {
        
		//Initializing request parameters 
        String entityResource = "requirements";
        StringBuilder queryString = new StringBuilder();
        queryString.append("?alt=application/json");
        //Adding filter query to get tests details for selected requirement only 
        //Encoding {} using URL encoding characters '%7B' for '{' and '%7D' for '}' 
       // queryString.append("&query=%7Btarget-rel[" + releaseID + "]%7D");                        
        queryString.append("&query=%7Btarget-rel[" + releaseID + "]%3Btype-id[NOT%201]%7D");    //%3B for ;
        //Calling requestResource
        List<Map<String, Object>> responseMapList = AlmClient.requestResource(domainName, projectName,entityResource, queryString.toString());
        
        //logger.info("responseMapList:" + responseMapList);
        
        //Iterates through all the requirements
        for(Map<String, Object> singleEntityMap : responseMapList) {
               
        	//Creating and Initializing the Requirement model object
           Requirement entityToAdd = new Requirement();
           RequirementProgress entityToAdd_1 = new RequirementProgress();
           
           //Setting property values to model
           
          
           entityToAdd.setReq_id(Integer.parseInt(singleEntityMap.get("id").toString()));
           entityToAdd.setReq_release_id(releaseEntityID);
           
           //requirement_Requirement_Functional Area
           //if(singleEntityMap.get(field_list.get("release_Go Live Date").toString()) != null)
           //entityToAdd.setGo_live_date(dateFormat.parse(singleEntityMap.get(field_list.get("release_Go Live Date").toString()).toString()));
           
           entityToAdd.setTitle(singleEntityMap.get("name").toString());
           //if(singleEntityMap.get(field_list.get("requirement_Requirement_Functional Area").toString()) != null)
           if(singleEntityMap.get(field_list.get("requirement_Requirement_Functional Area").toString()) != null){
        	   entityToAdd.setModule(singleEntityMap.get(field_list.get("requirement_Requirement_Functional Area").toString()).toString());
        	   entityToAdd_1.setReq_progress_module(singleEntityMap.get(field_list.get("requirement_Requirement_Functional Area").toString()).toString());
        	   logger.info("requirement module IF : " +singleEntityMap.get(field_list.get("requirement_Requirement_Functional Area").toString()));
           }
           else{
        	   entityToAdd.setModule("Not Updated");
        	   entityToAdd_1.setReq_progress_module("Not Updated");
        	   
        	   logger.info("requirement module ELSE : " +"Not Updated");
           }
           if(singleEntityMap.get(field_list.get("requirement_Requirement_Receiving Regions").toString()) != null)
                      entityToAdd.setRegion(singleEntityMap.get(field_list.get("requirement_Requirement_Receiving Regions").toString()).toString());
            else
            	   entityToAdd.setRegion("Not Updated");
           //requirement_requirement_Direct Coverage Status
           entityToAdd.setDirect_coverage(singleEntityMap.get("status").toString());
           entityToAdd.setData_src_tool(AlmConstants.dataSrcAlmQc);
           
           
         //Create new requirement in requirements base table using inialized model  
           boolean isCreated = requirementDao.createRequirement(entityToAdd);
           
           logger.info("Requirement is being created : " + isCreated);
           
           //If requirement is already available in requirements base table then update the same
           if(!isCreated) {
          	 
          	 isCreated = requirementDao.updateRequirement(entityToAdd);
          	 logger.info("Requirement is being updated : " + isCreated);
           }
           
           // Check the phases and update the records accordingly in requirements progress table
           String phaseArray[] = test_phases.split(",");
           logger.info("Phases Length" + phaseArray.length);
           
           for (int i =0;i<= phaseArray.length-1;i++){
        	   
        	   if(phaseArray[i].equals("SIT"))
        		   
        	   {
        		  
        		   // Set Requirement Release ID - SIT
        		   
        		     entityToAdd_1.setReq_release_id(releaseEntityID);
        		   
        		   // Set Requirement ID - SIT
        		     entityToAdd_1.setReq_id(Integer.parseInt(singleEntityMap.get("id").toString()));
        		       
        		   // Set Phase - SIT
        		   
        		      entityToAdd_1.setTest_phase(phaseArray[i]);
        		        		   
        		      entityToAdd_1.setDirect_coverage(singleEntityMap.get("status").toString());
        		      if(singleEntityMap.get(field_list.get("requirement_Requirement_Functional Area").toString()) != null)
        	        	   entityToAdd.setModule(singleEntityMap.get(field_list.get("requirement_Requirement_Functional Area").toString()).toString());
        	           else
        	        	   entityToAdd.setModule("Not Updated");
        		   
        		   //Initializing test entity request
                   String entityResourceTest = "tests";
                   String rquirementID = singleEntityMap.get("id").toString();
                   
                   StringBuilder queryStringConsolTotal = new StringBuilder();
                   queryStringConsolTotal.append("?alt=application/json");
                   //Adding query parameter for getting required fields only for test request
                   queryStringConsolTotal.append("&fields=id,exec-status,status,creation-time,last-modified");
                   //Adding filter query to get tests details for selected requirement only 
                   //Encoding {} using URL encoding characters '%7B' for '{' and '%7D' for '}'
                   
                   //queryStringConsolTotal.append("&query=%7Brequirement.id[" + rquirementID + "]%3Bname[" +phaseArray[i] + '*' +"]%7D");
                   
                   // Added API link for Oxxo implementation to get testcases based on field value
                   queryStringConsolTotal.append("&query=%7Brequirement.id[" + rquirementID + "]%3Buser-01[" +phaseArray[i] + '*' +"]%7D");
                   List<Map<String, Object>> tcListForCR = AlmClient.requestResource(domainName, projectName, entityResourceTest, queryStringConsolTotal.toString());
                   
                   StringBuilder queryStringConsolPassedFirstRun = new StringBuilder();
                   queryStringConsolPassedFirstRun.append("?alt=application/json");
                   //Adding query parameter for getting required fields only for test request
                   queryStringConsolPassedFirstRun.append("&fields=id,exec-status,status,creation-time,last-modified");
                   //Adding filter query to get tests details for selected requirement only 
                   //Encoding {} using URL encoding characters '%7B' for '{' and '%7D' for '}' 
                   queryStringConsolPassedFirstRun.append("&query=%7Brequirement.id[" + rquirementID + "]%3Bname[" +phaseArray[i] + '*' +"]%3Btest-instance.user-06[Y]%7D");
                   List<Map<String, Object>> passedFirstRunListForCR = AlmClient.requestResource(domainName, projectName, entityResourceTest, queryStringConsolPassedFirstRun.toString());
                   
                   StringBuilder queryStringConsolPassedRetest = new StringBuilder();
                   queryStringConsolPassedRetest.append("?alt=application/json");
                   //Adding query parameter for getting required fields only for test request
                   queryStringConsolPassedRetest.append("&fields=id,exec-status,status,creation-time,last-modified");
                   //Adding filter query to get tests details for selected requirement only 
                   //Encoding {} using URL encoding characters '%7B' for '{' and '%7D' for '}' 
                   queryStringConsolPassedRetest.append("&query=%7Brequirement.id[" + rquirementID + "]%3Bname[" +phaseArray[i] + '*' +"]%3Btest-instance.user-07[Y]%7D");
                   List<Map<String, Object>> passedRetestListForCR = AlmClient.requestResource(domainName, projectName, entityResourceTest, queryStringConsolPassedRetest.toString());
                  
                   StringBuilder queryStringConsolFailed = new StringBuilder();
                   queryStringConsolFailed.append("?alt=application/json");
                   //Adding query parameter for getting required fields only for test request
                   queryStringConsolFailed.append("&fields=id,exec-status,status,creation-time,last-modified");
                   //Adding filter query to get tests details for selected requirement only 
                   //Encoding {} using URL encoding characters '%7B' for '{' and '%7D' for '}' 
                   queryStringConsolFailed.append("&query=%7Brequirement.id[" + rquirementID + "]%3Bname[" +phaseArray[i] + '*' +"]%3Btest-instance.status[%27Failed%27]%7D");
                   List<Map<String, Object>> failedListForCR = AlmClient.requestResource(domainName, projectName, entityResourceTest, queryStringConsolFailed.toString());
              
                   //  SIT Fields - Date and TC's Progress
                   
                   entityToAdd_1.setNum_of_test_cases(tcListForCR.size());
                   entityToAdd_1.setTcs_with_iterations(0);
                                     		   
                   entityToAdd_1.setTcs_passed_first_run(passedFirstRunListForCR.size());
                   entityToAdd_1.setTcs_failed_first_run(failedListForCR.size());
                   entityToAdd_1.setDev_failures(0);
                   entityToAdd_1.setTsc_pending_fun_request(0);
                   entityToAdd_1.setTcs_pending(0);
                   entityToAdd_1.setTcs_passed_retest(passedRetestListForCR.size());
                   
                   entityToAdd_1.setPlanning_estimate(0);
                   entityToAdd_1.setExecution_estimate(0);      
                   
                   entityToAdd_1.setOrig_plnd_tcs_count(0);
                   entityToAdd_1.setExecution_estimate(0);
                   entityToAdd_1.setRequirement_defn_percentage(0);
                   entityToAdd_1.setUx_progress_percentage(0);
                   
                   
                   
                   //Requirement Module - SIT Fields
                   
                  
                   
                   //In Scope	requirement_In IST Scope?
                   //entityToAdd.setDirect_coverage(singleEntityMap.get(field_list.get("requirement_requirement_Direct Coverage Status").toString()).toString());
                   if(singleEntityMap.get(field_list.get("requirement_Requirement_In IST Scope").toString()) != null)
                	   entityToAdd_1.setIn_scope(singleEntityMap.get(field_list.get("requirement_Requirement_In IST Scope").toString()).toString());
                   else
                	   entityToAdd_1.setIn_scope("N");
                   
                   // Execution estimate
                   if(singleEntityMap.get(field_list.get("requirement_Requirement_IST Execution Estimate (PDs)").toString()) != null)
                	   entityToAdd_1.setExecution_estimate(Integer.parseInt(singleEntityMap.get(field_list.get("requirement_Requirement_IST Execution Estimate (PDs)").toString()).toString()));
                   else
                	   entityToAdd_1.setExecution_estimate(0);
                   
                 // Planning Status		requirement_SIT Test Planing Status
                   if(singleEntityMap.get(field_list.get("requirement_Requirement_IST Test Planing Status").toString()) != null)
                	   entityToAdd_1.setPlanning_status(singleEntityMap.get(field_list.get("requirement_Requirement_IST Test Planing Status").toString()).toString());
                   else
                	   entityToAdd_1.setPlanning_status("N/A");
                   
                   
                 // Planned Planning Start date requirement_Planned IST Planning Start Date
                   if(singleEntityMap.get(field_list.get("requirement_Requirement_Planned IST Planning Start Date").toString()) != null) {
                       DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd");
                       entityToAdd_1.setPlanned_planning_start(dateTimeFormat.parse(singleEntityMap.get(field_list.get("requirement_Requirement_Planned IST Planning Start Date").toString()).toString()));
                   }
                   
                   // Planned Planning End date 			requirement_Planned IST Planning End Date
                   if(singleEntityMap.get(field_list.get("requirement_Requirement_Planned IST Planning End Date").toString()) != null) {
                       DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd");
                       entityToAdd_1.setPlanned_planning_end(dateTimeFormat.parse(singleEntityMap.get(field_list.get("requirement_Requirement_Planned IST Planning End Date").toString()).toString()));
                   }
                   
                   // Actual Planning Start date 			requirement_Actual IST Planning Start Date
                   if(singleEntityMap.get(field_list.get("requirement_Requirement_Actual IST Planning Start Date").toString()) != null) {
                       DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd");
                       entityToAdd_1.setActual_planning_start(dateTimeFormat.parse(singleEntityMap.get(field_list.get("requirement_Requirement_Actual IST Planning Start Date").toString()).toString()));
                   }
                   
                   // Actual Planning End date 				requirement_Actual IST Planning End Date
                   if(singleEntityMap.get(field_list.get("requirement_Requirement_Actual IST Planning End Date").toString()) != null) {
                       DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd");
                       entityToAdd_1.setActual_planning_end(dateTimeFormat.parse(singleEntityMap.get(field_list.get("requirement_Requirement_Actual IST Planning End Date").toString()).toString()));
                   }
                   
                              
                   // Planned Execution Start date 			requirement_Planned IST Execution Start Date
                   if(singleEntityMap.get(field_list.get("requirement_Requirement_Planned IST Execution Start Date").toString()) != null) {
                       DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd");
                       entityToAdd_1.setPlanned_execution_start(dateTimeFormat.parse(singleEntityMap.get(field_list.get("requirement_Requirement_Planned IST Execution Start Date").toString()).toString()));
                   }
                   
                   // Planned Execution End date 			requirement_Planned IST Execution End Date
                   if(singleEntityMap.get(field_list.get("requirement_Requirement_Planned IST Execution End Date").toString()) != null) {
                       DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd");
                       entityToAdd_1.setPlanned_execution_end(dateTimeFormat.parse(singleEntityMap.get(field_list.get("requirement_Requirement_Planned IST Execution End Date").toString()).toString()));
                   }
                   
                   // Actual Execution Start date 			requirement_Actual IST Execution Start Date
                   if(singleEntityMap.get(field_list.get("requirement_Requirement_Actual IST Execution Start Date").toString()) != null) {
                       DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd");
                       entityToAdd_1.setActual_execution_start(dateTimeFormat.parse(singleEntityMap.get(field_list.get("requirement_Requirement_Actual IST Execution Start Date").toString()).toString()));
                   }
                   
                   //Actual Execution End date 				requirement_Actual IST Execution End Date
                   if(singleEntityMap.get(field_list.get("requirement_Requirement_Actual IST Execution End Date").toString()) != null) {
                       DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd");
                       entityToAdd_1.setActual_execution_end(dateTimeFormat.parse(singleEntityMap.get(field_list.get("requirement_Requirement_Actual IST Execution End Date").toString()).toString()));
                   }
                   
                   
                   
                 //Planned Dev certification date 			requirement_Requirement_Planned Dev Certification Date
                 if(singleEntityMap.get(field_list.get("requirement_Requirement_Planned Dev Certification Date").toString()) != null) {
                     DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd");
                     entityToAdd_1.setPlanned_dev_certification(dateTimeFormat.parse(singleEntityMap.get(field_list.get("requirement_Requirement_Planned Dev Certification Date").toString()).toString()));
                 }
                 
                 //Actual Dev certification date 			requirement_Requirement_Actual Dev Certification Date
                 if(singleEntityMap.get(field_list.get("requirement_Requirement_Actual Dev Certification Date").toString()) != null) {
                     DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd");
                     entityToAdd_1.setActual_dev_certification(dateTimeFormat.parse(singleEntityMap.get(field_list.get("requirement_Requirement_Actual Dev Certification Date").toString()).toString()));
                 }       
                   
                 
                 //For verifying date is available or not 
                 
                 //Commented for GBS
                 
               //  Date todaysDate = new Date();
                 
                 //	requirement_requirement_Direct Coverage Status
                 /*if(singleEntityMap.get(field_list.get("requirement_requirement_Direct Coverage Status").toString()).toString().compareToIgnoreCase("Passed") == 0)
                       entityToAdd_1.setOrig_cert_date(todaysDate);*/
                
                 
                 if(singleEntityMap.get("last-modified") != null) {
                       DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
                       entityToAdd_1.setTimestamp(dateTimeFormat.parse(singleEntityMap.get("last-modified").toString()));
                 }
                 
                 
                 // 
              
                 boolean isCreated_1 = requirementprogressDao.createRequirement(entityToAdd_1,phaseArray[i]);
                 
               
                 
               //If requirement is already available in requirements progress then update the same for req progress
                 if(!isCreated_1) {
                	 RequirementProgress requirementUpdated = requirementprogressDao.getRequirement(entityToAdd_1.getReq_id(), entityToAdd_1.getReq_release_id(),entityToAdd_1.getTest_phase());
                	 isCreated_1 = requirementprogressDao.updateRequirement(entityToAdd_1);
                	 updateDsrHistoryData(requirementUpdated,entityToAdd_1.getTest_phase());
                	 logger.info("Requirement is being updated : " + isCreated);
                 }
                 
                 
                //Select the newly added requirement  
              
        		RequirementProgress requirementAdded = requirementprogressDao.getRequirement(entityToAdd_1.getReq_id(), entityToAdd_1.getReq_release_id(),entityToAdd_1.getTest_phase());
                
                if(requirementAdded != null)
                {
                //Update the Daily History Records for the selected release
                 updateDsrHistoryData(requirementAdded,entityToAdd_1.getTest_phase());
                 updateTestData(requirementAdded, tcListForCR, entityToAdd_1.getTest_phase());
                        
                }
        		   
        	   }   
        	
        	   
        	   if(phaseArray[i].equals("UAT")) 
        	   {
        		  
        		   logger.info("Phase_Details:" + phaseArray[i] );
        		   
      		     entityToAdd_1.setReq_release_id(releaseEntityID);
      		   
      		   // Set Requirement ID - UAT
      		     entityToAdd_1.setReq_id(Integer.parseInt(singleEntityMap.get("id").toString()));
      		       
      		   // Set Phase - UAT
      		   
      		      entityToAdd_1.setTest_phase(phaseArray[i]);
      		        		   
      		    entityToAdd_1.setDirect_coverage(singleEntityMap.get("status").toString());
        		  
      		  if(singleEntityMap.get(field_list.get("requirement_Requirement_Functional Area").toString()) != null)
           	   entityToAdd.setModule(singleEntityMap.get(field_list.get("requirement_Requirement_Functional Area").toString()).toString());
              else
           	   entityToAdd.setModule("Not Updated");
        		   
        		   
        		 //Initializing test entity request
                   String entityResourceTest = "tests";
                   String rquirementID = singleEntityMap.get("id").toString();
                   
                   StringBuilder queryStringConsolTotal = new StringBuilder();
                   queryStringConsolTotal.append("?alt=application/json");
                   //Adding query parameter for getting required fields only for test request
                   queryStringConsolTotal.append("&fields=id,exec-status,status,creation-time,last-modified");
                   //Adding filter query to get tests details for selected requirement only 
                   //Encoding {} using URL encoding characters '%7B' for '{' and '%7D' for '}' 
                   //queryStringConsolTotal.append("&query=%7Brequirement.id[" + rquirementID + "]%3Bname[" +phaseArray[i] + '*'+"]%7D");
                   //OXXO implementation
                   queryStringConsolTotal.append("&query=%7Brequirement.id[" + rquirementID + "]%3Buser-01[" +phaseArray[i] + '*'+"]%7D");
                   List<Map<String, Object>> tcListForCR = AlmClient.requestResource(domainName, projectName, entityResourceTest, queryStringConsolTotal.toString());
                   
                   StringBuilder queryStringConsolPassedFirstRun = new StringBuilder();
                   queryStringConsolPassedFirstRun.append("?alt=application/json");
                   //Adding query parameter for getting required fields only for test request
                   queryStringConsolPassedFirstRun.append("&fields=id,exec-status,status,creation-time,last-modified");
                   //Adding filter query to get tests details for selected requirement only 
                   //Encoding {} using URL encoding characters '%7B' for '{' and '%7D' for '}' 
                   queryStringConsolPassedFirstRun.append("&query=%7Brequirement.id[" + rquirementID + "]%3Bname[" +phaseArray[i] +'*'+"]%3Btest-instance.user-06[Y]%7D");
                   List<Map<String, Object>> passedFirstRunListForCR = AlmClient.requestResource(domainName, projectName, entityResourceTest, queryStringConsolPassedFirstRun.toString());
                   
                   StringBuilder queryStringConsolPassedRetest = new StringBuilder();
                   queryStringConsolPassedRetest.append("?alt=application/json");
                   //Adding query parameter for getting required fields only for test request
                   queryStringConsolPassedRetest.append("&fields=id,exec-status,status,creation-time,last-modified");
                   //Adding filter query to get tests details for selected requirement only 
                   //Encoding {} using URL encoding characters '%7B' for '{' and '%7D' for '}' 
                   queryStringConsolPassedRetest.append("&query=%7Brequirement.id[" + rquirementID + "]%3Bname[" +phaseArray[i] +'*'+"]%3Btest-instance.user-07[Y]%7D");
                   List<Map<String, Object>> passedRetestListForCR = AlmClient.requestResource(domainName, projectName, entityResourceTest, queryStringConsolPassedRetest.toString());
                  
                   StringBuilder queryStringConsolFailed = new StringBuilder();
                   queryStringConsolFailed.append("?alt=application/json");
                   //Adding query parameter for getting required fields only for test request
                   queryStringConsolFailed.append("&fields=id,exec-status,status,creation-time,last-modified");
                   //Adding filter query to get tests details for selected requirement only 
                   //Encoding {} using URL encoding characters '%7B' for '{' and '%7D' for '}' 
                   queryStringConsolFailed.append("&query=%7Brequirement.id[" + rquirementID + "]%3Bname[" +phaseArray[i] +'*'+"]%3Btest-instance.status[%27Failed%27]%7D");
                   List<Map<String, Object>> failedListForCR = AlmClient.requestResource(domainName, projectName, entityResourceTest, queryStringConsolFailed.toString());
              
                   
                   entityToAdd_1.setNum_of_test_cases(tcListForCR.size());
                   entityToAdd_1.setTcs_with_iterations(0);
                                     		   
                   entityToAdd_1.setTcs_passed_first_run(passedFirstRunListForCR.size());
                   entityToAdd_1.setTcs_failed_first_run(failedListForCR.size());
                   entityToAdd_1.setDev_failures(0);
                   entityToAdd_1.setTsc_pending_fun_request(0);
                   entityToAdd_1.setTcs_pending(0);
                   entityToAdd_1.setTcs_passed_retest(passedRetestListForCR.size());
                   
                   entityToAdd_1.setPlanning_estimate(0);
                   entityToAdd_1.setExecution_estimate(0);      
                   
                   entityToAdd_1.setOrig_plnd_tcs_count(0);
                   entityToAdd_1.setExecution_estimate(0);
                   entityToAdd_1.setRequirement_defn_percentage(0);
                   entityToAdd_1.setUx_progress_percentage(0);
                   
                   
                   
                   //Requirement Module - UAT Fields - Update with UAT Dates and TC execution metrics 
                   
                   //In Scope					requirement_requirement_In UAT CoE Scope?
                   if(singleEntityMap.get(field_list.get("requirement_Requirement_In UAT CoE Scope?").toString()) != null)
                	   entityToAdd_1.setIn_scope(singleEntityMap.get(field_list.get("requirement_Requirement_In UAT CoE Scope?").toString()).toString());
                   else
                	   entityToAdd_1.setIn_scope("N");
                   
                   // Execution estimate		requirement_requirement_UAT Execution Estimate (PDs)
                   if(singleEntityMap.get(field_list.get("requirement_Requirement_UAT Execution Estimate (PDs)").toString()) != null)
                	   entityToAdd_1.setExecution_estimate(Integer.parseInt(singleEntityMap.get(field_list.get("requirement_Requirement_UAT Execution Estimate (PDs)").toString()).toString()));
                   else
                	   entityToAdd_1.setExecution_estimate(0);
                   
                   // Planning Status			requirement_requirement_UAT Planning Status
                   if(singleEntityMap.get(field_list.get("requirement_Requirement_UAT Planning Status").toString()) != null)
                	   entityToAdd_1.setPlanning_status(singleEntityMap.get(field_list.get("requirement_Requirement_UAT Planning Status").toString()).toString());
                   else
                	   entityToAdd_1.setPlanning_status("N");
                   
                   
                 // Planned Planning Start date requirement_requirement_Planned UAT Planning Start Date
                   if(singleEntityMap.get(field_list.get("requirement_Requirement_Planned UAT Planning Start Date").toString()) != null) {
                       DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd");
                       entityToAdd_1.setPlanned_planning_start(dateTimeFormat.parse(singleEntityMap.get(field_list.get("requirement_Requirement_Planned UAT Planning Start Date").toString()).toString()));
                   }
                   
                   // Planned Planning End date 			requirement_requirement_Planned UAT Planning End Date
                   if(singleEntityMap.get(field_list.get("requirement_Requirement_Planned UAT Planning End Date").toString()) != null) {
                       DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd");
                       entityToAdd_1.setPlanned_planning_end(dateTimeFormat.parse(singleEntityMap.get(field_list.get("requirement_Requirement_Planned UAT Planning End Date").toString()).toString()));
                   }
                   
                   // Actual Planning Start date 			requirement_requirement_Actual UAT Planning Start Date
                   if(singleEntityMap.get(field_list.get("requirement_Requirement_Actual UAT Planning Start Date").toString()) != null) {
                       DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd");
                       entityToAdd_1.setActual_planning_start(dateTimeFormat.parse(singleEntityMap.get(field_list.get("requirement_Requirement_Actual UAT Planning Start Date").toString()).toString()));
                   }
                   
                   // Actual Planning End date 				requirement_requirement_Actual UAT Planning End Date
                   if(singleEntityMap.get(field_list.get("requirement_Requirement_Actual UAT Planning End Date").toString()) != null) {
                       DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd");
                       entityToAdd_1.setActual_planning_end(dateTimeFormat.parse(singleEntityMap.get(field_list.get("requirement_Requirement_Actual UAT Planning End Date").toString()).toString()));
                   }
                   
                              
                   // Planned Execution Start date 			requirement_requirement_Planned UAT Execution Start Date
                   if(singleEntityMap.get(field_list.get("requirement_Requirement_Planned UAT Execution Start Date").toString()) != null) {
                       DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd");
                       entityToAdd_1.setPlanned_execution_start(dateTimeFormat.parse(singleEntityMap.get(field_list.get("requirement_Requirement_Planned UAT Execution Start Date").toString()).toString()));
                   }
                   
                   // Planned Execution End date			requirement_requirement_Planned UAT Execution End Date
                   if(singleEntityMap.get(field_list.get("requirement_Requirement_Planned UAT Execution End Date").toString()) != null) {
                       DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd");
                       entityToAdd_1.setPlanned_execution_end(dateTimeFormat.parse(singleEntityMap.get(field_list.get("requirement_Requirement_Planned UAT Execution End Date").toString()).toString()));
                   }
                   
                   // Actual Execution Start date 			requirement_requirement_Actual UAT Execution Start Date
                   if(singleEntityMap.get(field_list.get("requirement_Requirement_Actual UAT Execution Start Date").toString()) != null) {
                       DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd");
                       entityToAdd_1.setActual_execution_start(dateTimeFormat.parse(singleEntityMap.get(field_list.get("requirement_Requirement_Actual UAT Execution Start Date").toString()).toString()));
                   }
                   
                   //Actual Execution End date 				requirement_requirement_Actual UAT Execution End Date
                   if(singleEntityMap.get(field_list.get("requirement_Requirement_Actual UAT Execution End Date").toString()) != null) {
                       DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd");
                       entityToAdd_1.setActual_execution_end(dateTimeFormat.parse(singleEntityMap.get(field_list.get("requirement_Requirement_Actual UAT Execution End Date").toString()).toString()));
                   }
                   
                   
                   
                 //Planned Dev certification date 			requirement_requirement_Planned UAT Certification Date
                 if(singleEntityMap.get(field_list.get("requirement_Requirement_Planned UAT Certification Date").toString()) != null) {
                     DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd");
                     entityToAdd_1.setPlanned_dev_certification(dateTimeFormat.parse(singleEntityMap.get(field_list.get("requirement_Requirement_Planned UAT Certification Date").toString()).toString()));
                 }
                 
                 //Actual Dev certification date 			requirement_Actual UAT Certification Date
                 if(singleEntityMap.get(field_list.get("requirement_Requirement_Actual UAT Certification Date").toString()) != null) {
                     DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd");
                     entityToAdd_1.setActual_dev_certification(dateTimeFormat.parse(singleEntityMap.get(field_list.get("requirement_Requirement_Actual UAT Certification Date").toString()).toString()));
                 } 
                 
              /*   //For verifying date is available or not 
                 
                 Date todaysDate = new Date();
                 
                 // requirement_requirement_Direct Coverage Status = Passed/Failed
                 if(singleEntityMap.get(field_list.get("requirement_requirement_Direct Coverage Status").toString()).toString().compareToIgnoreCase("Passed") == 0)
                       entityToAdd_1.setOrig_cert_date(todaysDate); */
                
                 
                 if(singleEntityMap.get("last-modified") != null) {
                       DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
                       entityToAdd_1.setTimestamp(dateTimeFormat.parse(singleEntityMap.get("last-modified").toString()));
                 }
                 
                 
                 // 
              
                 boolean isCreated_1 = requirementprogressDao.createRequirement(entityToAdd_1,phaseArray[i]);
                 
               
                 
               //If requirement is already available in requirements progress then update the same for req progress
                 if(!isCreated_1) {
                	 
                	 isCreated_1 = requirementprogressDao.updateRequirement(entityToAdd_1);
                	 logger.info("Requirement is being updated : " + isCreated);
                 }
                 
                 
                //Select the newly added requirement  
              
        		RequirementProgress requirementAdded = requirementprogressDao.getRequirement(entityToAdd_1.getReq_id(), entityToAdd_1.getReq_release_id(),entityToAdd_1.getTest_phase());
                
                if(requirementAdded != null)
                {
                //Update the Daily History Records for the selected release
                 updateDsrHistoryData(requirementAdded,entityToAdd_1.getTest_phase());
                 updateTestData(requirementAdded, tcListForCR, entityToAdd_1.getTest_phase());      
                }
        		   
        		   
        	   }
        	   
           }
           
         

        }
        
          
        return false;
 }

	
	
	
	
	
	/*
	 * Updates Defects details
	 * Calls requestResource method of AlmClient  
	 * Initializes Defect model object
	 * Calls createDefect or updateDefect method of DefectDAO interface 
	 * */
	public boolean updateDefectsData(int releaseEntityID, int releaseID, String projectName, String domainName) throws JsonParseException, JsonMappingException, IOException, ParseException, RestClientException, URISyntaxException, KeyManagementException, KeyStoreException, NoSuchAlgorithmException {
			
		//Initializing request parameters
		String entityResource = "defects";
		StringBuilder queryString = new StringBuilder();
		queryString.append("?alt=application/json");
		//Adding filter query to get tests details for selected requirement only 
        //Encoding {} using URL encoding characters '%7B' for '{' and '%7D' for '}' 
		queryString.append("&query=%7Bdetected-in-rel[" + releaseID + "]%7D");
		
		List<Map<String, Object>> responseMapList = AlmClient.requestResource(domainName, projectName,entityResource, queryString.toString());
		
		//Iterating through all the defects tagged to selected release
		for(Map<String, Object> singleEntityMap : responseMapList) {
		
			//Creating and Initializing Defect model
			Defect entityToAdd = new Defect();
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			
			//Setting properties to the defect model
			entityToAdd.setDefect_id(Integer.parseInt(singleEntityMap.get("id").toString()));
			entityToAdd.setDefect_release_id(releaseEntityID);
			entityToAdd.setTitle(singleEntityMap.get("name").toString());
			entityToAdd.setDetected_in_release(releaseID);
			entityToAdd.setState(singleEntityMap.get("status").toString());	//defect_defect_State
			entityToAdd.setData_src_tool(AlmConstants.dataSrcAlmQc);
			
			//entityToAdd.setPriority(singleEntityMap.get("priority").toString());
			entityToAdd.setSubmit_date(dateFormat.parse(singleEntityMap.get("creation-time").toString()));
			
			if(singleEntityMap.get("severity") != null)
				entityToAdd.setSeverity(singleEntityMap.get("severity").toString());
			else
				entityToAdd.setSeverity("N/A");
			
			if(singleEntityMap.get("closing-date") != null)
				entityToAdd.setClosed_date(dateFormat.parse(singleEntityMap.get("closing-date").toString()));
			if(singleEntityMap.get("last-modified") != null) {
				DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:MM");
				entityToAdd.setTimestamp(dateTimeFormat.parse(singleEntityMap.get("last-modified").toString()));
			}
			//entityToAdd.setPhase_raised(singleEntityMap.get(field_list.get("defect_Test Phase").toString()).toString());
			
			
			// Not Working
			/*if(singleEntityMap.get(field_list.get("defect_Defect_Phase Raised").toString()) != null){			
			entityToAdd.setPhase_raised(singleEntityMap.get(field_list.get("defect_Defect_Phase Raised").toString()).toString());					//defect_defect_Phase Raised
			//entityToAdd.setRaised_during("NA");
			}
			else
				entityToAdd.setPhase_raised("Not Updated");
			
			if(singleEntityMap.get(field_list.get("defect_Defect_Raised During").toString()) != null){			
				entityToAdd.setRaised_during(singleEntityMap.get(field_list.get("defect_Defect_Raised During").toString()).toString());					//defect_defect_Phase Raised
				}
				else
					entityToAdd.setRaised_during("Not Updated");		*/		
			
			
		  if(singleEntityMap.get(field_list.get("defect_Defect_Phase Raised").toString()) != null){			
			entityToAdd.setPhase_raised(singleEntityMap.get(field_list.get("defect_Defect_Phase Raised").toString()).toString());					//defect_defect_Phase Raised
			entityToAdd.setRaised_during("NA");
		  }
			
			else
				entityToAdd.setPhase_raised("Not Updated");
			
			/*if(singleEntityMap.get(field_list.get("defect_Defect_Raised During").toString()) != null){			
				entityToAdd.setRaised_during(singleEntityMap.get(field_list.get("defect_Defect_Raised During").toString()).toString());					//defect_defect_Phase Raised
				}
				else
					entityToAdd.setRaised_during("Not Updated");*/
			
			
			if(singleEntityMap.get("estimated-fix-time") != null)
				entityToAdd.setEstimated_fix_time(Integer.parseInt(singleEntityMap.get("estimated-fix-time").toString()));
			
			//Custom Fields
			//Modules
			//if(singleEntityMap.get(field_list.get("defect_Module").toString()) != null)	
			//	entityToAdd.setFunctional_area(singleEntityMap.get(field_list.get("defect_Module").toString()).toString());
			
			if(singleEntityMap.get(field_list.get("defect_Defect_Functional Area").toString()) != null)											//defect_defect_Functional Area
				entityToAdd.setFunctional_area(singleEntityMap.get(field_list.get("defect_Defect_Functional Area").toString()).toString());
			else
				entityToAdd.setFunctional_area("Not Updated");
			
			//Root Causes
			/*if(singleEntityMap.get(field_list.get("defect_Issue Type").toString()) != null)
				entityToAdd.setRoot_cause_analysis(singleEntityMap.get(field_list.get("defect_Issue Type").toString()).toString());*/
			
			if(singleEntityMap.get(field_list.get("defect_Defec Root Cause").toString()) != null)										//defect_defect_Root Cause Analysis
				entityToAdd.setRoot_cause_analysis(singleEntityMap.get(field_list.get("defect_Defec Root Cause").toString()).toString());
			else
				entityToAdd.setRoot_cause_analysis("Not Updated");
			
			
			
		/*	//Impelemtation
			if(field_list.containsKey("defect_Defect_Passed 1st UAT Retest?")== true){
				entityToAdd.setPassed_first_ist_retest(singleEntityMap.get(field_list.get("defect_Defect_Passed 1st UAT Retest?").toString()).toString());
			}
			else
				entityToAdd.setPassed_first_ist_retest("Not Updated");*/
			
			
			if(singleEntityMap.get(field_list.get("defect_Defect_Passed 1st UAT Retest?").toString()) != null)										//defect_defect_Passed 1st UAT Retest?
				entityToAdd.setPassed_first_ist_retest(singleEntityMap.get(field_list.get("defect_Defect_Passed 1st UAT Retest?").toString()).toString());
			
			else
				entityToAdd.setPassed_first_ist_retest("Not Updated");
			//Implementation for IST Classification
			if(singleEntityMap.get(field_list.get("defect_SIT Classification").toString()) != null)										//defect_defect_Passed 1st UAT Retest?
				entityToAdd.setIst_classification(singleEntityMap.get(field_list.get("defect_SIT Classification").toString()).toString());
			
			else
				entityToAdd.setIst_classification("Not Updated");
			
			
			
			boolean isCreated = defectDao.createDefect(entityToAdd);
			
			if(!isCreated) {
				isCreated = defectDao.updateDefect(entityToAdd);
			}
		}
		
		return true;
	}
	
	/*
	 * Updates DefectsHistory details
	 * Calls requestResource method of AlmClient  
	 * Initializes DefectHistory model object
	 * Calls createDefectHistory or updateDefectHistory method of DefectHistoryDAO interface 
	 * */
	public boolean updateDefectsHistoryData(int releaseEntityID, int releaseID, String projectName, String domainName,String phases) throws JsonParseException, JsonMappingException, IOException, ParseException, RestClientException, URISyntaxException, KeyManagementException, KeyStoreException, NoSuchAlgorithmException {
        
		//Initializing request parameters
        String entityResource = "defects";
        StringBuilder queryString = new StringBuilder();
        queryString.append("?alt=application/json");
        //Adding filter query to get tests details for selected requirement only 
        //Encoding {} using URL encoding characters '%7B' for '{' and '%7D' for '}'
        //queryString.append("&query=%7Bdetected-in-rel[" + releaseID + "];status[NOT%20CLOSED%20AND%20NOT%20REJECTED]%7D");
        
        //queryString.append("&query=%7Bdetected-in-rel[" + releaseID + "]%3Buser-03[" +phases + '*' +"]%7D");
        
        //Implementation for OXXO
        queryString.append("&query=%7Bdetected-in-rel[" + releaseID + "]%3Buser-01[" +phases + '*' +"]%7D");
        List<Map<String, Object>> responseMapList = AlmClient.requestResource(domainName, projectName,entityResource, queryString.toString());
        
        String getSeverity, getStatus;
        int nUrgent = 0, nVeryHigh = 0, nHigh = 0, nMedium = 0, nLow = 0;
        int nClosedUrgent = 0, nClosedVeryHigh = 0, nClosedHigh = 0, nClosedMedium = 0, nClosedLow = 0;
        
        //Creating and Initializing Defect model
        DefectsHistory entityToAdd = new DefectsHistory();     
        	
        //Iterating through all the defects tagged to selected release and Phase to generate the history
        for(Map<String, Object> singleEntityMap : responseMapList) {
                       //Setting properties to the model                
               entityToAdd.setDef_hist_rel_id(releaseEntityID);
              
               Date todaysDate = new Date();
               entityToAdd.setDef_hist_timestamp(todaysDate);
               entityToAdd.setData_src_tool(AlmConstants.dataSrcAlmQc);
               entityToAdd.setPhase(phases);
               
               getStatus = singleEntityMap.get("status").toString();               
               if(getStatus.compareToIgnoreCase(AlmConstants.STATE_REJECTED) != 0 && getStatus.compareToIgnoreCase(AlmConstants.STATE_CLOSED) != 0) {            	   
            	   getSeverity = singleEntityMap.get("severity").toString();                   
                   if(getSeverity.compareToIgnoreCase(AlmConstants.SEVERITY_5) == 0)                                     
                         nUrgent++;                        
                   if(getSeverity.compareToIgnoreCase(AlmConstants.SEVERITY_4) == 0)
                         nVeryHigh++;                      
                   if(getSeverity.compareToIgnoreCase(AlmConstants.SEVERITY_3) == 0)
                         nHigh++;                          
                   if(getSeverity.compareToIgnoreCase(AlmConstants.SEVERITY_2) == 0)
                         nMedium++;                        
                   if(getSeverity.compareToIgnoreCase(AlmConstants.SEVERITY_1) == 0)
                         nLow++;                   
               }
                              
               if(getStatus.compareToIgnoreCase(AlmConstants.STATE_CLOSED) == 0) {            	   
            	   getSeverity = singleEntityMap.get("severity").toString();                   
                   if(getSeverity.compareToIgnoreCase(AlmConstants.SEVERITY_5) == 0)                                     
                         nClosedUrgent++;                        
                   if(getSeverity.compareToIgnoreCase(AlmConstants.SEVERITY_4) == 0)
                         nClosedVeryHigh++;                      
                   if(getSeverity.compareToIgnoreCase(AlmConstants.SEVERITY_3) == 0)
                         nClosedHigh++;                          
                   if(getSeverity.compareToIgnoreCase(AlmConstants.SEVERITY_2) == 0)
                         nClosedMedium++;                        
                   if(getSeverity.compareToIgnoreCase(AlmConstants.SEVERITY_1) == 0)
                         nClosedLow++;                   
               }
                                   
               int totalDefectCount = nUrgent + nVeryHigh + nHigh + nMedium + nLow;
               int totalClosedDefectCount = nClosedUrgent + nClosedVeryHigh + nClosedHigh + nClosedMedium + nClosedLow;
              
               entityToAdd.setDef_hist_severity_sev5(nUrgent);
               entityToAdd.setDef_hist_severity_sev4(nVeryHigh);
               entityToAdd.setDef_hist_severity_sev3(nHigh);
               entityToAdd.setDef_hist_severity_sev2(nMedium);
               entityToAdd.setDef_hist_severity_sev1(nLow);                  
               entityToAdd.setDef_totl_defects(totalDefectCount);
               
               entityToAdd.setDef_hist_closed_severity_sev5(nClosedUrgent);
               entityToAdd.setDef_hist_closed_severity_sev4(nClosedVeryHigh);
               entityToAdd.setDef_hist_closed_severity_sev3(nClosedHigh);
               entityToAdd.setDef_hist_closed_severity_sev2(nClosedMedium);
               entityToAdd.setDef_hist_closed_severity_sev1(nClosedLow);                  
               entityToAdd.setDef_totl_closed_defects(totalClosedDefectCount);    
               
               entityToAdd.setDef_total_incremental(totalDefectCount + totalClosedDefectCount);
        }
        
        //Create or update defectHistory records only if there are defects in the response
        if (responseMapList.size() != 0) {
        	//Call createDefectHistory method
        	boolean isCreated = defectshistoryDao.createDefectsHistory(entityToAdd,phases);
        	//If for todays date record is already available then just update the record
	        if (isCreated == false) {
	               defectshistoryDao.updateDefectsHistory(entityToAdd,phases);
	        }
        }	
        return true;
	}

	/*
	 * Updates DsrCrHistory details
	 * @param Requirement  
	 * Initializes DsrCrHistory model object
	 * Calls createDsrCrHistory or updateDsrCrHistory method of DsrCrHistoryDAO interface 
	 * */
	public boolean updateDsrHistoryData(RequirementProgress requirement, String test_phase) throws ParseException {
        
		//Initializing request parameters
		DsrCrHistory entityToAdd = new DsrCrHistory();
		
		//Setting properties to the model 
		entityToAdd.setHistory_requirement_id(requirement.getReq_id());  
		entityToAdd.setHistory_release_id(requirement.getReq_release_id());
		entityToAdd.setFk_req_progress_entity_id(requirement.getReq_progress_entity_id());
		entityToAdd.setCr_execution_status(requirement.getDirect_coverage());
		entityToAdd.setCr_passed_cases(requirement.getTcs_passed_first_run());
		entityToAdd.setCr_failed_cases(requirement.getTcs_failed_first_run());
		entityToAdd.setHistory_phase(test_phase);
		entityToAdd.setData_src_tool(AlmConstants.dataSrcAlmQc);
		
		entityToAdd.setHistory_date(new Date());
		
		//for planned calculation in datapull dsr_cr_history
				
		
		if(requirement.getPlanned_execution_start() != null && requirement.getPlanned_execution_end() != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date currentDateforRelease = dateFormat.parse(dateFormat.format(new Date()));
			logger.info("currentDateforRelease : "+currentDateforRelease);
			int plannedTCTillDate = commonMethods.PlannedTCcalculation(currentDateforRelease, requirement.getPlanned_execution_start() ,requirement.getPlanned_execution_end() , requirement.getNum_of_test_cases());
			logger.info("plannedTCTillDate :"+plannedTCTillDate);
			entityToAdd.setCr_planned_cases(plannedTCTillDate);
		}
		else {
			entityToAdd.setCr_planned_cases(0);
		}
		
		//Call createDefectHistory method
		boolean isCreated = dsrcrhistoryDao.createDsrHistory(entityToAdd,test_phase);
		
		
		//If for todays date record is already available then just update the record
        if (isCreated == false) {
               dsrcrhistoryDao.updateDsrHistory(entityToAdd,test_phase);
               logger.info("DsrHistory Updated : "+isCreated);
        }
		
        return true;
	}
	
	/*
	 * Updates Test Case details
	 * @param Requirement
	 * @param TcCaseList   
	 * Initializes DsrCrHistory model object
	 * Calls createDsrCrHistory or updateDsrCrHistory method of DsrCrHistoryDAO interface 
	 * */
	public void updateTestData(RequirementProgress requirementAdded, List<Map<String, Object>> tcListForCR,String Phase) throws ParseException {
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		logger.info("tcListForCR : "+tcListForCR.size());
		//Iterating through all the tests tagged to selected requirement
        for(Map<String, Object> testEntity : tcListForCR) {
            
              //Updating test data for selected CR
              Test testEntityToAdd = new Test();
              testEntityToAdd.setData_src_tool(AlmConstants.dataSrcAlmQc);
              testEntityToAdd.setTest_id(Integer.parseInt(testEntity.get("id").toString()));
              testEntityToAdd.setTest_req_id(requirementAdded.getReq_id());
              testEntityToAdd.setTest_release_id(requirementAdded.getReq_release_id());
              testEntityToAdd.setStatus(testEntity.get("status").toString());
              testEntityToAdd.setExec_status(testEntity.get("exec-status").toString());
              testEntityToAdd.setCreation_time(dateFormat.parse(testEntity.get("creation-time").toString()));
              testEntityToAdd.setLast_modified(dateFormat.parse(testEntity.get("last-modified").toString()));
              testEntityToAdd.setTest_phase(Phase);
              testEntityToAdd.setTitle(testEntity.get("name") == null ? "N/A" : testEntity.get("name").toString());
              
            //Call createDefectHistory method
      		boolean isCreated = testDao.createTest(testEntityToAdd,Phase);
      		
      		//If for todays date record is already available then just update the record
              if (isCreated == false) {
            	  testDao.updateTest(testEntityToAdd,Phase);
              }
        }
		
	}
}
