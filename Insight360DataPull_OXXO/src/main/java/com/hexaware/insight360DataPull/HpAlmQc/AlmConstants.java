package com.hexaware.insight360DataPull.HpAlmQc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

public class AlmConstants {
	
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(AlmConstants.class);

	/*@Autowired
	private UserDAO userDao;
	
	User user = userDao.getUserByUsername("naveen", "nava");
	
	String alm_host = user.getAlm_host();
	String alm_username = user.getAlm_username();
	String alm_password = user.getAlm_password();*/

	//	Alm 11 - Multiple Domain and Project
	public static String host = "172.25.157.7";// 11-172.25.121.28 and 12-172.25.157.124 //qc.nam.nsroot.net
	public static String port = "8080";//8080 // http://172.25.157.124:8080 //650
	public static String domainName = "hexaview" ;//11-DEFAULT/ACCELERATOR and 12-SAMPLE //DEFAULT
	public static String projectName = "Insight360";//11-QualityCenter_Demo and 12-Training //QualityCenter_Demo
	public static String userName = "harsh";//11-kiran_g/sridhar and 12-31585 //padmin
	public static String userPassword = "harsh";//admin
	public static String dataSrcAlmQc = "ALM";
	
	//String Constants for Open Defect Severity         //Severity

	public static String SEVERITY_1 = "1-Low";
	public static String SEVERITY_2 = "2-Medium";
	public static String SEVERITY_3 = "3-High";
	public static String SEVERITY_4 = "4-Very High";
	public static String SEVERITY_5 = "5-Urgent";
	
	//String Constants for Open Defect State
	public static String STATE_NEW = "New";
	public static String STATE_OPEN = "Open";
	public static String STATE_FIXED = "Fixed";
	public static String STATE_CLOSED = "Closed";
	public static String STATE_REJECTED = "Rejected";
	public static String STATE_REOPEN = "Reopen";
	public static String STATE_PENDING = "Pending";
	
	//String Constants for Requirement Execution Status
	public static String STATE_NOT_STARTED = "No Run";
	public static String STATE_FAILED = "Failed";
	public static String STATE_PASSED = "Passed";
	public static String STATE_NOT_COVERED = "Not Covered";
	public static String STATE_NOT_COMPLETED = "Not Completed";
	public static String STATE_BLOCKED = "Blocked";
	
	//String Constants for Test Design Status
	public static List<String> TC_DESIGN_STATUS_LIST = new ArrayList<String>(
			Arrays.asList("Design", "Imported", "Ready", "Repair"));	
}
