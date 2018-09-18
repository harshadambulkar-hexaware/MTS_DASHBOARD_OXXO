package com.hexaware.insight360DataPull.JiraWithZephyr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class JiraWithZephyrClient {
	
		//Initializing logger
		private static final Logger logger = Logger.getLogger(JiraWithZephyrClient.class);
			
		//Creating and Initializing objects for the RestTemplate which will act as HTTPRestClient for JIRA
			
		//Initializing the RestTemplate object with HttpComponentsClientHttpRequestFactory() class which helps in maintaining the cookies
		public static RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
			
		//Creating header for passing customized headers in the request
		public static HttpHeaders headers = new HttpHeaders();
			
		//Creating HttpEntity object which can be used as container for request parameters
		public static HttpEntity<String> request = null;
			
		//Creating the ResponseEntity object for storing the obtained response from RestService
		public static ResponseEntity<String> responseString;
		public static ResponseEntity<Object> responseObject;
			
		//creating object of ObjectMapper class provided by Jackson package it helps converting JSON string to Java Object and vice varsa
		public static ObjectMapper mapper = new ObjectMapper();
			
		public static String serverUrl = "http://" + JiraWithZephyrConstants.host + ":" + JiraWithZephyrConstants.port + "/rest/api/2/";
		public static String entityUrl = "";
		public static String requestUrl = "";
		
		/*
		 * Generic method to declare the HTTP Header information 
		 * Encoding and Authenticating headers using Base64 Encoder
		 * @return Http Entity request object
		 * */
		public static HttpEntity<String> declareHeaderInfo()	{
			
			//Obtaining the byte code for the credentials
			byte[] credBytes = (JiraWithZephyrConstants.userName + ":" + JiraWithZephyrConstants.userPassword).getBytes();
			//Encoding the credentials to add in the Authentication header using Base64Encoder class
			headers.add("Authorization", "Basic " + new String(Base64.getEncoder().encode(credBytes)));
			//Adding header to the HttpEntity request
			request = new HttpEntity<String>(headers);
				
			return request;
		}
		
//		Method to get the list of Jira Projects 
		public static List<Map<String, Object>> getProjects() throws JsonParseException, JsonMappingException, IOException	{			
			
			entityUrl = "project";
			requestUrl = serverUrl + entityUrl;
			
			//Calling exchange method of the RestTemplate class
			responseString = restTemplate.exchange(requestUrl, HttpMethod.GET, declareHeaderInfo(), String.class);
			
			//Allowing unquoted field names because after converting JSON string to object quotes will be removed
			mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
				
			List<Map<String, Object>> responseObjectList = new ArrayList<Map<String, Object>>();			
			//Converted object map for all the entities
			responseObjectList = mapper.readValue(responseString.getBody().toString(), new TypeReference<List<Map<String, Object>>>(){});
				
			return responseObjectList;
		}
		
		//	Method to get the list of Releases for the Projects
		public static List<Map<String, Object>> getReleases(String projectID) throws JsonParseException, JsonMappingException, IOException	{
				
			entityUrl = "project/" + projectID + "/versions";
			requestUrl = serverUrl + entityUrl;
			
			
			//Calling exchange method of the RestTemplate class
			responseString = restTemplate.exchange(requestUrl, HttpMethod.GET, declareHeaderInfo(), String.class);
			
			//Allowing unquoted field names because after converting JSON string to object quotes will be removed
			mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
				
			List<Map<String, Object>> responseObjectList = new ArrayList<Map<String, Object>>();			
			//Converted object map for all the entities
			responseObjectList = mapper.readValue(responseString.getBody().toString(), new TypeReference<List<Map<String, Object>>>(){});
			
			logger.info("responseObjectList :"+responseObjectList);
			return responseObjectList;
		}
	
}
