package com.hexaware.insight360DataPull.JiraWithZephyr;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.hexaware.insight360DataPull.dao.DefectDAO;
import com.hexaware.insight360DataPull.dao.DefectsHistoryDAO;
import com.hexaware.insight360DataPull.dao.ProjectDAO;
import com.hexaware.insight360DataPull.dao.ReleaseDAO;
import com.hexaware.insight360DataPull.model.Project;
import com.hexaware.insight360DataPull.model.Release;

public class JiraWithZephyrDataPull {
	
	//Initializing logger
		@SuppressWarnings("unused")
		private static final Logger logger = Logger.getLogger(JiraWithZephyrDataPull.class);
		
		//Creating and Initilizing objects for the RestTemplate which will act as HTTPRestClient for HP QC/ALM
		
		//Initilizing the RestTemplate object with HttpComponentsClientHttpRequestFactory() class which helps in mantaining the cookies
		public static RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
		
		//Creating header for passing customized headers in the request
		public static HttpHeaders headers = new HttpHeaders();
		
		//Creating HttpEntity object which can be used as container for request parameters
		public static HttpEntity<String> request = null;
		
		//Creating the ResponseEntity object for storing the obtained resopnse from RestService
		public static ResponseEntity<String> responseString;
		public static ResponseEntity<Object> responseObject;
		
		//creating object of ObjectMapper class provided by Jackson package it helps converting JSON string to Java Object and vice varsa
		public static ObjectMapper mapper = new ObjectMapper();
		
		public static String serverUrl = "http://" + JiraWithZephyrConstants.host + ":" + JiraWithZephyrConstants.port + "/";
		public static String entityUrl = "";
		public static String requestUrl = "";
		
		//Creating instances for models
		private ProjectDAO projectDao;
		private ReleaseDAO releaseDao;
		private DefectDAO defectDao;
		private DefectsHistoryDAO defectshistoryDao;
		
		//Initialize all data access objects(DAOs required for the data pull)
		public JiraWithZephyrDataPull(ProjectDAO projectDAO, ReleaseDAO releaseDAO, DefectDAO defectDAO, DefectsHistoryDAO defectshistoryDAO) {
			projectDao = projectDAO;
			releaseDao = releaseDAO;
			defectDao = defectDAO;
			defectshistoryDao = defectshistoryDAO;
		}
		
		//	Method to Update the Jira Data based on the Projects and Releases
		public boolean updateJiraWithZephyrData() throws IOException, ParseException, RestClientException, URISyntaxException  {
		
			for (Map<String, Object> projectMap : JiraWithZephyrClient.getProjects())	{
				//Initializing model object for inserting or updating
				Project projectToAdd = new Project();
				projectToAdd.setprojectName(projectMap.get("name").toString());
				projectToAdd.setisActive(0);
				projectToAdd.setisArchived(0);
				projectToAdd.setisPlatform(0);
				projectToAdd.setSub_business_line_id(1);
				projectToAdd.setdisplayName(projectMap.get("name").toString());
				projectToAdd.setdomainName(projectMap.get("id").toString());
				projectToAdd.setData_src_tool(JiraWithZephyrConstants.dataSrc);
				projectDao.createProject(projectToAdd);
				
				//Getting and checking whether this project is active or not
				Project project = projectDao.getProject(projectMap.get("name").toString(), projectMap.get("id").toString());
				
				//If project is active then calls updateRelease Method which updates release data
				if(project.getisActive() == 1){				
					for(Map<String, Object> singleEntityMap : JiraWithZephyrClient.getReleases(projectMap.get("id").toString()))	
					{
						//Creating model object for the release model
						Release entityToAdd = new Release();					
						DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
						
						//Setting model properties from the response map 
						entityToAdd.setRelease_id(Integer.parseInt(singleEntityMap.get("id").toString()));
						entityToAdd.setRelease_project_id(project.getprojectId());
						entityToAdd.setRelease_name(singleEntityMap.get("name").toString());
						entityToAdd.setIs_active(0);
						entityToAdd.setIs_archived(0);
						entityToAdd.setData_src_tool(JiraWithZephyrConstants.dataSrc);
											
						if(singleEntityMap.get("startDate") != null)
							entityToAdd.setIst_planned_start(dateFormat.parse(singleEntityMap.get("startDate").toString()));
						if(singleEntityMap.get("releaseDate") != null)
							entityToAdd.setIst_planned_end(dateFormat.parse(singleEntityMap.get("releaseDate").toString()));
						
						//Calling the Hibernate method createRelese 
						boolean isCreated = releaseDao.createRelease(entityToAdd);
						
						if(!isCreated) {
							releaseDao.updateRelease(entityToAdd);
						}
																
					}
				}
				
			}
			return true;
		}
	
}
