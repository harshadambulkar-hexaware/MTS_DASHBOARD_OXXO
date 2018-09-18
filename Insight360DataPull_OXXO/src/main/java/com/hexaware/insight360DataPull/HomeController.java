package com.hexaware.insight360DataPull;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Date;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
import com.hexaware.insight360DataPull.dao.TestDAO;
import com.hexaware.insight360DataPull.dao.TrendsHistoryStorageDAO;
import com.hexaware.insight360DataPull.dao.UserDAO;
import com.hexaware.insight360DataPull.model.User;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	//Initilizing logger
	private static final Logger logger = Logger.getLogger(HomeController.class);
	
	
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
	private UserDAO userDao;
	
	@Autowired
	private TrendsHistoryStorageDAO trendsHistoryStorageDao;
	
	@Autowired
	private RequirementProgressDAO requirementprogressDAO;
	
	@Autowired
	private DatapullLogDAO datapulllogDAO;

	/**
	 * Simply selects the home view to render by returning its name.
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 * @throws ParseException 
	 * @throws URISyntaxException 
	 * @throws RestClientException 
	 */
	
	public String alm_host;
	public String alm_username;
	public String alm_password;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) throws JsonParseException, JsonMappingException, IOException, ParseException, RestClientException, URISyntaxException, KeyManagementException, KeyStoreException, NoSuchAlgorithmException {
		
		logger.info("Data Pull Service Started");
		
		
		 User user = userDao.getUserByUsername("naveen", "nava");
		 alm_host = user.getAlm_host();
		 alm_username = user.getAlm_username();
		 alm_password = user.getAlm_password();
		 System.out.println("alm_host : "+user.getAlm_host());
		 System.out.println("alm_username : "+user.getAlm_username());
		 System.out.println("alm_password : "+user.getAlm_password());
		
			//Login into the ALM using loginAlm() static method defined in the AlmClient class
		 //AlmClient almClientDefault = new AlmClient();
		 //AlmClient almClient = new AlmClient(userDao);
		AlmClient.LoginAlm(userDao);
		//Creating and Initilizing the Object for the DataPullEntities class 
		// Get Cutom Fields for all the ALM entities
		
		AlmDataPullEntities almDataPullEntities = new AlmDataPullEntities(projectDAO, releaseDAO, requirementDAO, defectDAO, defectshistoryDAO, dsrCrhistoryDAO, testDAO, requirementprogressDAO, datapulllogDAO);
		almDataPullEntities.updateAlmData();
		////qTestDataPullEntities.updateQTestData();
		//Logout from the ALM using loginAlm() static method defined in the AlmClient class
		AlmClient.LogoutAlm();
		
		//Creating and Initilizing the Object for the JiraPullEntities class 
	//JiraWithZephyrDataPull jiraWithZephyrDataPull = new JiraWithZephyrDataPull(projectDAO, releaseDAO, defectDAO, defectshistoryDAO);
		//Calling updateAlmData method of DataPullEntites class
	//jiraWithZephyrDataPull.updateJiraWithZephyrData();
		
		//TrendsHistoryStorageService trendsHistoryStorageService = new TrendsHistoryStorageService(releaseDAO, defectDAO, trendsHistoryStorageDao, requirementprogressDAO);
		//trendsHistoryStorageService.saveTrendsHistory();
		model.addAttribute("serverTime", new Date());
		
		return "home";
	}
	
}
