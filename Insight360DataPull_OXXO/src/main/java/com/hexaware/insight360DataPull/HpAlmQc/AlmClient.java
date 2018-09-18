package com.hexaware.insight360DataPull.HpAlmQc;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.XML;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.hexaware.insight360DataPull.dao.UserDAO;
import com.hexaware.insight360DataPull.model.User;

/*
 * @Author Hexaware Technologies
 * Provides the following generic method to achieve the common tasks required for HP ALM  
 * Login
 * Logout
 * GetProjects
 * Get
 * RequestEntityResource
 * GetEntityCount
 * */



public class AlmClient {
	
	
	
	//Initializing logger
	private static final Logger logger = Logger.getLogger(AlmClient.class);
	
	
	//Creating and Initializing objects for the RestTemplate which will act as HTTPRestClient for HP QC/ALM
	
	//Initializing the RestTemplate object with HttpComponentsClientHttpRequestFactory() class which helps in maintaining the cookies
	
			//new RestTemplate(new HttpComponentsClientHttpRequestFactory());
	
	//Creating header for passing customized headers in the request
	
	 public static HttpComponentsClientHttpRequestFactory requestFactory =
             new HttpComponentsClientHttpRequestFactory();
	public static HttpHeaders headers = new HttpHeaders();
	
	//Creating HttpEntity object which can be used as container for request parameters
	public static HttpEntity<String> request = null;
	
	//Creating the ResponseEntity object for storing the obtained response from RestService
	public static ResponseEntity<String> responseString;
	public ResponseEntity<Object> responseObject;
	
	//creating object of ObjectMapper class provided by Jackson package it helps converting JSON string to Java Object and vice versa
	public static ObjectMapper mapper = new ObjectMapper();
	
	
//	static String alm_host = user.getAlm_host();
	
	public static String serverUrl = "";//alm_host + "/";
	public static String entityUrl = "";
	public static String requestUrl = "";
	
	
	// To Bypass the SSL certificate
	 @Bean
	  public static HttpComponentsClientHttpRequestFactory restTemp()
	                 {
		  TrustStrategy acceptingTrustStrategy = new TrustStrategy() {
		        @Override public boolean isTrusted(X509Certificate[] x509Certificates, String s)
		                        throws CertificateException {
		            return true;
		        }
		    };
	    SSLContext sslContext;
		try {
			sslContext = org.apache.http.ssl.SSLContexts.custom()
			                .loadTrustMaterial(null, acceptingTrustStrategy)
			                .build();
			
			   SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

			    HttpClient httpClient = HttpClients.custom()
			                    .setSSLSocketFactory(csf)
			                    .build();

			   

			    requestFactory.setHttpClient(httpClient);
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	 
	    //RestTemplate restTemplate = new RestTemplate(requestFactory);
	    return requestFactory;
	    
	}
	
	 public static RestTemplate restTemplate = new RestTemplate(restTemp());
	 
	
	//Login to the HP ALM/QC using encoded credentials with basic authentication header
	public static void LoginAlm(UserDAO userDao) throws KeyManagementException, KeyStoreException, NoSuchAlgorithmException {
		
	
		User user = userDao.getUserByUsername("naveen", "nava");
		
		String alm_username = user.getAlm_username();
		String alm_password = user.getAlm_password();
		System.out.println(alm_username + " " + alm_password);
		
		serverUrl = user.getAlm_host() + "/";		
		
		//Login to Alm using credentials defined in the constants
		entityUrl = "qcbin/authentication-point/authenticate";
		requestUrl = serverUrl + entityUrl;
		//Obtaining the byte code for the credentials
		
		byte[] credBytes =(alm_username + ":" + alm_password).getBytes();
		
		//Encoding the credentials to add in the Authentication header using Base64Encoder class
		//headers.add("Authorization", "Basic " + Base64Encoder.encode(credBytes));
		headers.add("Authorization", "Basic " + new String(Base64.getEncoder().encode(credBytes)));
		//Adding header to the HttpEntity request
		request = new HttpEntity<String>(headers);
		//Calling exchange method of the RestTemplate class
		
		responseString = restTemplate.exchange(requestUrl, HttpMethod.GET, request, String.class);
		
		//Obtain the site session for the user
		entityUrl = "qcbin/rest/site-session";
		requestUrl = serverUrl + entityUrl;
		responseString = restTemplate.exchange(requestUrl, HttpMethod.POST, request, String.class);
		//logger.info("Session Obtained : "+responseString);		
		
		//Check whether user is loged in or not
		//entityUrl = "qcbin/rest/is-authenticated";
		//requestUrl = serverUrl + entityUrl + queryString.toString();
		//responseString = restTemplate.exchange(requestUrl, HttpMethod.GET, request, String.class);
		//logger.info("Is - Authenticated : " + responseString);		
	}
	
	//Logout from the HP ALM/QC and discard the session
	public static void LogoutAlm() throws KeyManagementException, KeyStoreException, NoSuchAlgorithmException {
		
		
		//Logoff Alm and discard the session
		entityUrl = "qcbin/authentication-point/logout";
		requestUrl = serverUrl + entityUrl;
		responseString = restTemplate.exchange(requestUrl, HttpMethod.GET, request, String.class);
		//logger.info("Logged off : " + responseString);
		
		//Logout and discard the site session for the user
		//entityUrl = "rest/site-session";
		//requestUrl = serverUrl + entityUrl;
		//responseString = restTemplate.exchange(requestUrl, HttpMethod.DELETE, request, String.class);
		//logger.info("Session Obtained : "+responseString);		
	}
	
	//Get domins available in the HP ALM/QC
	public static List<String> GetDomains() throws KeyManagementException, KeyStoreException, NoSuchAlgorithmException{	
		
		List<String> domainList = new ArrayList<String>();
		
		//Creating request url for getting domains
		requestUrl = serverUrl + "qcbin/rest/domains";//?alt=application/json";
		responseString = restTemplate.getForEntity(requestUrl, String.class);
		//Map<String, List<Map<String, String>>> domainMap = new HashMap<String, List<Map<String, String>>>();
		//mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		//domainMap = mapper.readValue(responseString.getBody().toString(), new TypeReference<Map<String, List<Map<String, String>>>>(){});
		//logger.info(domainMap.toString());
		
		//JAXBContext jaxbContext = JAXBContext.newInstance(Object.class);
	    //Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		//StringBuffer xmlStr = new StringBuffer( responseString.getBody().toString() );
		//Object domainMap = (Object) jaxbUnmarshaller.unmarshal(new StreamSource( new StringReader( xmlStr.toString() ) ));
		
		//Checking whether response is in XML or JSON using response header
		if(responseString.getHeaders().get("Content-type").toString().compareToIgnoreCase("[application/xml]") == 0) {
			
			//Convert given XML to JSON
			JSONObject xmlJSONObj = XML.toJSONObject(responseString.getBody().toString());
            
			//String jsonPrettyPrintString = xmlJSONObj.toString(4);
            
			//Extract and convert Domain object from JSON string
			JSONObject dominsJsonObject = (JSONObject) xmlJSONObj.get("Domains");
            
			//Tokenize the particular key to check whether value is JSONObject or JSONArray
            Object dominsJson = new JSONTokener(dominsJsonObject.get("Domain").toString()).nextValue();
            
            //If only one value is there in domain means list manipulation is not necessary
            if(dominsJson instanceof  JSONObject) {
            	domainList.add(dominsJsonObject.getJSONObject("Domain").getString("Name"));
            }
            
            //If multiple values means iterate and parse the list
            if(dominsJson instanceof  JSONArray) {
            	JSONArray dominListObject = (JSONArray)dominsJson;
            	for(Object domainObject : dominListObject) {
            		JSONObject domainName = (JSONObject)domainObject;
            		domainList.add(domainName.get("Name").toString());
            	} 
            }
		}
		else {
			
			//if response is in JSON then simply extract the values from JSON Array
			JSONObject domainsObject = new JSONObject(responseString.getBody().toString());
			logger.info("responseString:"+responseString);
			logger.info("domainsObject:"+domainsObject);
			JSONObject domainListObjectParent = (JSONObject)domainsObject.get("Domains");
			logger.info("domainListObjectParent:"+domainListObjectParent);
			JSONArray domainListObject = (JSONArray)domainListObjectParent.get("Domain");
			logger.info("domainListObject:"+domainListObject);
			String domainNameString = null;
			
			//JSONArray domainListObject = (JSONArray)domainsObject.get("Domain Name"); //Domain Name
        	for(Object domainObject : domainListObject) {
        		JSONObject domainName = (JSONObject)domainObject;
        		domainNameString = domainName.get("Name").toString();
        		StringBuilder domainNameStringBuilder = new StringBuilder(domainNameString);
        		logger.info("Domain List: " +domainNameStringBuilder);
        		domainList.add(domainName.get("Name").toString());
        		//domainList.add(domainName.get("Domain Name").toString());
        	} 
			
			logger.info(responseString.getBody().toString());
			/*JSONObject domainsObject = new JSONObject(responseString.getBody().toString());
			//Extract and convert Domain object from JSON string
			JSONObject dominsJsonObject = (JSONObject) domainsObject.get("Domains");
            
			//Tokenize the perticuler key to check whether value is JSONObject or JSONArray
            Object dominsJson = new JSONTokener(dominsJsonObject.get("Domain").toString()).nextValue();
            
            //If only one value is there in domain means list manipulation is not nessessary
            if(dominsJson instanceof  JSONObject) {
            	domainList.add(dominsJsonObject.getJSONObject("Domain").getString("Name"));
            }
            
            //If multiple values means iterate and parse the list
            if(dominsJson instanceof  JSONArray) {
            	JSONArray dominListObject = (JSONArray)dominsJson;
            	for(Object domainObject : dominListObject) {
            		JSONObject domainName = (JSONObject)domainObject;
            		domainList.add(domainName.get("Name").toString());
            	} 
            }*/
		}
		return domainList;		
	}
	
	//Response body parsing is exactly similar as GetDomain method 
	public static List<String> GetProjects(String domainName) throws KeyManagementException, KeyStoreException, NoSuchAlgorithmException {
	
		List<String> projectList = new ArrayList<String>();
		
		requestUrl = serverUrl + "qcbin/rest/domains/" + domainName + "/projects";//?alt=application/json";
		responseString = restTemplate.getForEntity(requestUrl, String.class);
		
		if(responseString.getHeaders().get("Content-type").toString().compareToIgnoreCase("[application/xml]") == 0) {
			
			JSONObject xmlJSONObj = XML.toJSONObject(responseString.getBody().toString());
			
            //String jsonPrettyPrintString = xmlJSONObj.toString(4);
            
			JSONObject projectsJsonObject = (JSONObject) xmlJSONObj.get("Projects");
            
            Object projectsJson = new JSONTokener(projectsJsonObject.get("Project").toString()).nextValue();
            
            if(projectsJson instanceof  JSONObject) {
            	projectList.add(projectsJsonObject.getJSONObject("Project").getString("Name"));
            }
            if(projectsJson instanceof  JSONArray) {
            	JSONArray projectListObject = (JSONArray)projectsJson;
            	for(Object projectObject : projectListObject) {
            		JSONObject projectName = (JSONObject)projectObject;
            		projectList.add(projectName.get("Name").toString());
            	} 
            }
		}
		else {
			logger.info(responseString.getBody().toString());
			//JSONObject projectsObject = new JSONObject(responseString.getBody().toString());
			//JSONObject projectListObjectParent = (JSONObject)projectsObject.get("Projects");
			
			//JSONObject xmlJSONObj = XML.toJSONObject(responseString.getBody().toString());
			//JSONObject projectsJsonObject = (JSONObject) xmlJSONObj.get("Projects");
			JSONObject mainProjectsJsonObject = new JSONObject(responseString.getBody().toString());
			JSONObject projectsJsonObject = (JSONObject)mainProjectsJsonObject.get("Projects");
			
			Object projectsJson = new JSONTokener(projectsJsonObject.get("Project").toString()).nextValue();
			//projectListObjectParent.get("Project");
			
			if(projectsJson instanceof  JSONObject){
				projectList.add(projectsJsonObject.getJSONObject("Project").getString("Name"));
				
				//projectList.add(( projectListObject).get("Name"));
				//projectList.add(JSONObject)projectListObject.get("Name").toString();
			}
			if(projectsJson instanceof  JSONArray){
				JSONArray projectListObject = (JSONArray)projectsJson;
			
        	for(Object projectObject : projectListObject) {
        		JSONObject projectName = (JSONObject)projectObject;
        		projectList.add(projectName.get("Name").toString());
        	}
			}
			
			/*JSONObject projectsObject = new JSONObject(responseString.getBody().toString());
			
			JSONObject projectsJsonObject = (JSONObject) projectsObject.get("Projects");
            
            Object projectsJson = new JSONTokener(projectsJsonObject.get("Project").toString()).nextValue();
            
            if(projectsJson instanceof  JSONObject) {
            	projectList.add(projectsJsonObject.getJSONObject("Project").getString("Name"));
            }
            if(projectsJson instanceof  JSONArray) {
            	JSONArray projectListObject = (JSONArray)projectsJson;
            	for(Object projectObject : projectListObject) {
            		JSONObject projectName = (JSONObject)projectObject;
            		projectList.add(projectName.get("Name").toString());
            	} 
            }*/
		}		
		return projectList;		
	}
	
	/*
	 * Generic method for Requesting different resources using RestTemplate
	 * Parsing the resopnse we are getting using ObjectMapper
	 * @param Domain
	 * @param Project
	 * @param EntityResource
	 * @Param QueryString
	 * @return requestResource
	 * */
	public static List<Map<String, Object>> requestResource(String domainName, String projectName, String resourceEntity, String queryString) throws JsonParseException, JsonMappingException, IOException, RestClientException, URISyntaxException, KeyManagementException, KeyStoreException, NoSuchAlgorithmException {
		
		entityUrl = "qcbin/rest/domains/" + domainName + "/projects/" + projectName + "/" + resourceEntity;
		requestUrl = serverUrl + entityUrl + queryString;
		//logger.info(".., Request URL :" + requestUrl);
		responseString = restTemplate.getForEntity(new URI(requestUrl), String.class);
		logger.info("responseString:" + responseString);
		//Allowing unquoted field names because after converting JSON string to object quotes will be removed
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		Map<String, Object> responseObjectMap = new HashMap<String, Object>();
		
		@SuppressWarnings("unused")
		int totalResults = 0;
		
		//Object for all entities
		List<Map<String, Object>> entitesList = new ArrayList<Map<String, Object>>();
		
		//Converted object map for all the entities
		responseObjectMap = mapper.readValue(responseString.getBody().toString(), new TypeReference<HashMap<String, Object>>(){});
		
		//Extracting total number of entities
		totalResults = mapper.readValue(mapper.writeValueAsString(responseObjectMap.get("TotalResults")), int.class);
		
		//Extracting the entity list from response object map
		entitesList = mapper.readValue(mapper.writeValueAsString(responseObjectMap.get("entities")), new TypeReference<List<Map<String, Object>>>(){});
		
		//Return type variable after parsing
		List<Map<String, Object>> returnEntitesList = new ArrayList<Map<String, Object>>();
		
		//Iterating through all the entities
		for(Map<String, Object> entity : entitesList) {
			
			List<Map<String, Object>> fieldsList = new ArrayList<Map<String, Object>>();
			
			fieldsList = mapper.readValue(mapper.writeValueAsString(entity.get("Fields")), new TypeReference<List<Map<String, Object>>>(){});
		
			Map<String, Object> entityFieldsMap = new HashMap<String, Object>();
			
			//Iterating through all the fields in the selected entities
			for(Map<String, Object> field : fieldsList) {
				
				//Getting the field name
				String fieldName = mapper.readValue(mapper.writeValueAsString(field.get("Name")), String.class);
				String value = "";
				
				@SuppressWarnings("unused")
				String referenceValue = "";
				
				List<Map<String, Object>> valuesList = new ArrayList<Map<String, Object>>();
				
				//Extracting value list for the selected field
				valuesList = mapper.readValue(mapper.writeValueAsString(field.get("values")), new TypeReference<List<Map<String, Object>>>(){});
				
				//Iterating through value list for selected field
				for(Map<String, Object> fieldValue : valuesList) {
					
					//Extracting value and reference value for selected value field
					value = mapper.readValue(mapper.writeValueAsString(fieldValue.get("value")), String.class);
					referenceValue = mapper.readValue(mapper.writeValueAsString(fieldValue.get("ReferenceValue")), String.class);
					
					//Initilizing the field map with name and value
					entityFieldsMap.put(fieldName, value);

				}
					
			}
			
			//Adding the generated map for the selected entity
			returnEntitesList.add(entityFieldsMap);
			
		}
		
		return returnEntitesList;
	}

	/*
	 * Generic method to get count only
	 * Parsing the resopnse we are getting using ObjectMapper
	 * @param Domain
	 * @param Project
	 * @param EntityResource
	 * @Param QueryString
	 * @return entityCount
	 * */
	public static int getRecordCount(String domainName, String projectName, String resourceEntity, String queryString) throws RestClientException, URISyntaxException, JsonParseException, JsonMappingException, IOException, KeyManagementException, KeyStoreException, NoSuchAlgorithmException {
	
		entityUrl = "qcbin/rest/domains/" + domainName + "/projects/" + projectName + "/" + resourceEntity;
		requestUrl = serverUrl + entityUrl + queryString;
		responseString = restTemplate.getForEntity(new URI(requestUrl), String.class);
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		Map<String, Object> responseObjectMap = new HashMap<String, Object>();
		
		int totalResults = 0;
	
		responseObjectMap = mapper.readValue(responseString.getBody().toString(), new TypeReference<HashMap<String, Object>>(){});
		
		totalResults = mapper.readValue(mapper.writeValueAsString(responseObjectMap.get("TotalResults")), int.class);
		
		return totalResults;
	}
	
	public static HashMap<String, String> Get_CustomFields(String domainName, String projectName) throws  ClientProtocolException, IOException, RestClientException, URISyntaxException
	{
		
		String myentities[] = {"requirement","test","test-instance","defect","release"};
		//Initializing the query string parameters
		StringBuilder queryString = new StringBuilder();
		//adding query string parameter for getting response in JSON format
		queryString.append("?alt=application/json");
				//queryString.append("&page-size=1");
		HashMap<String, String> field_collection = new HashMap<String, String>();
		// Loop through all the ALM entities and add their custom fields to the list
		for (int j = 0; j < myentities.length; j++)
		{
			//String a = "http://115.69.80.152:8080/qcbin/rest/domains/default/projects/citi_demo/customization/entities/"+myentities[j]+"/fields?alt=application/json";
		entityUrl = "qcbin/rest/domains/" + domainName + "/projects/" + projectName + "/" +"customization/entities/"+ myentities[j] +"/fields";
		requestUrl = serverUrl + entityUrl + queryString;
		responseString = restTemplate.getForEntity(new URI(requestUrl), String.class);
		JSONObject result_output = new JSONObject(responseString.getBody().toString());
		
		
		JSONArray arr = result_output.getJSONObject("Fields").getJSONArray("Field");
		
		 for (int i = 0; i < arr.length(); i++) {
			 
			 if (arr.getJSONObject(i).getString("name").startsWith("user"))
				 
			 {
				 field_collection.put(myentities[j]+"_"+arr.getJSONObject(i).getString("label"), arr.getJSONObject(i).getString("name"));
				 System.out.println(arr.getJSONObject(i).getString("label") + " - " + arr.getJSONObject(i).getString("name"));
			 }
		  }
		}
		
		return field_collection;
		
	}
	
}
