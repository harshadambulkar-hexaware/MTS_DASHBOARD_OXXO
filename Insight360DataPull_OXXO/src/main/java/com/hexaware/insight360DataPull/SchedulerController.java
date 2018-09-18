package com.hexaware.insight360DataPull;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import com.hexaware.insight360DataPull.HpAlmQc.AlmClient;
import com.hexaware.insight360DataPull.HpAlmQc.AlmDataPullEntities;
import com.hexaware.insight360DataPull.dao.DatapullLogDAO;
import com.hexaware.insight360DataPull.dao.DefectDAO;
import com.hexaware.insight360DataPull.dao.DefectsHistoryDAO;
import com.hexaware.insight360DataPull.dao.DsrCrHistoryDAO;
import com.hexaware.insight360DataPull.dao.ProjectDAO;
import com.hexaware.insight360DataPull.dao.ReleaseDAO;
import com.hexaware.insight360DataPull.dao.RequirementDAO;
import com.hexaware.insight360DataPull.dao.RequirementProgressDAO;
import com.hexaware.insight360DataPull.dao.ScheduleTimeDAO;
import com.hexaware.insight360DataPull.dao.TestDAO;
import com.hexaware.insight360DataPull.dao.UserDAO;
import com.hexaware.insight360DataPull.model.ScheduleTime;

@SuppressWarnings("unused")
@RestController
@CrossOrigin
public class SchedulerController {
	
	private static final Logger logger = Logger.getLogger(SchedulerController.class);
	
	//Autowiring the DAO's for passing it to DataPull Entities 
		@Autowired
	    private ProjectDAO projectDAO;
		
		@Autowired
	    private ReleaseDAO releaseDAO;
		
		@Autowired
	    private RequirementDAO requirementDAO;
		
		@Autowired
		private DefectDAO defectDAO;
		
		@Autowired
		private DefectsHistoryDAO defectshistoryDAO;
		
		@Autowired
		private DsrCrHistoryDAO dsrCrhistoryDAO;
		
		@Autowired
		private TestDAO testDAO;
		
		@Autowired
		private UserDAO userDAO;
		
		@Autowired
		ScheduleTimeDAO scheduleTimeDAO;
		
		@Autowired
		private RequirementProgressDAO requirementprogressDAO;
		
		@Autowired
		private DatapullLogDAO datapulllogDAO;
		
		@Value("${alm.host}")
		String almUserName;
	
	//@Scheduled(cron="0 * * * * *")
	private void demoSceduledMethod() throws JsonParseException, JsonMappingException, RestClientException, IOException, ParseException, URISyntaxException, KeyManagementException, KeyStoreException, NoSuchAlgorithmException {
		
		Date currentTime = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentTime);
		int hh = cal.get(Calendar.HOUR_OF_DAY);
		int mm = cal.get(Calendar.MINUTE);
		
		for(ScheduleTime timeRecord : scheduleTimeDAO.ScheduleTimeList()){
			logger.info(hh +":"+ mm +" and "+ timeRecord.getHh() +":"+ timeRecord.getMm() +" "+ almUserName);
			if(hh == timeRecord.getHh() && mm == timeRecord.getMm()) {
				
				logger.info("Data Pull Service Started");
				
				this.startDataPullService();
				
			}
			
		}
		
	}
	
	private void startDataPullService() throws JsonParseException, JsonMappingException, RestClientException, IOException, ParseException, URISyntaxException, KeyManagementException, KeyStoreException, NoSuchAlgorithmException {
		//Login into the ALM using loginAlm() static method defined in the AlmClient class
		//AlmClient almClient = new AlmClient(userDAO);
		AlmClient.LoginAlm(userDAO);
		//Creating and Initilizing the Object for the DataPullEntities class 
		AlmDataPullEntities almDataPullEntities = new AlmDataPullEntities(projectDAO, releaseDAO, requirementDAO, defectDAO, defectshistoryDAO, dsrCrhistoryDAO, testDAO,requirementprogressDAO, datapulllogDAO);
		//Calling updateAlmData method of DataPullEntites class
		almDataPullEntities.updateAlmData();
		//Logout from the ALM using loginAlm() static method defined in the AlmClient class
		AlmClient.LogoutAlm();
		
		
	}
	
	
}
