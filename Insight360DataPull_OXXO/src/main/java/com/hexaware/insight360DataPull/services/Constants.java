package com.hexaware.insight360DataPull.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Constants {
	
	//String Constants for Open Defect State
		public static String STATE_NEW = "New";
		public static String STATE_OPEN = "Open";
		public static String STATE_FIXED = "Fixed";
		public static String STATE_CLOSED = "Closed";
		public static String STATE_REJECTED = "Rejected";
		public static String STATE_REOPEN = "Reopen";
		public static String STATE_RETEST = "Retest";
		public static String STATE_PENDING = "Pending";
	
	public static String ConfirmedDefctsStates = "'"+STATE_NEW+"','"+STATE_OPEN+"','"+STATE_FIXED+"','"+STATE_REOPEN+"','"+STATE_CLOSED+"'";
		
	//String Constants for Open Defect Severity
		public static String SEVERITY_1 = "1-Low";
		public static String SEVERITY_2 = "2-Medium";
		public static String SEVERITY_3 = "3-High";
		public static String SEVERITY_4 = "4-Very High";
		public static String SEVERITY_5 = "5-Urgent";	
	
	//String Constants for Severities
		public static List<String> DEFECT_SEVERITY_LIST = new ArrayList<String>(Arrays.asList(SEVERITY_5, SEVERITY_4, SEVERITY_3, SEVERITY_2, SEVERITY_1));
		
		public static String devOwnedRootCauseList = "'Code Fix','Packaging','Code Issue'";
		
		public static Map<String, Integer> defectWeightMap = new HashMap<String, Integer>();
		public static Map<String, Integer> weightageFactorMap = new HashMap<String, Integer>();
		
		public static int SEVERITY_1_WEIGHT = 1;
		public static int SEVERITY_2_WEIGHT = 2;
		public static int SEVERITY_3_WEIGHT = 3;
		public static int SEVERITY_4_WEIGHT = 4;
		public static int SEVERITY_5_WEIGHT = 5;
		
		static {
		defectWeightMap.put(SEVERITY_1, SEVERITY_1_WEIGHT);
		defectWeightMap.put(SEVERITY_2, SEVERITY_2_WEIGHT);
		defectWeightMap.put(SEVERITY_3, SEVERITY_3_WEIGHT);
		defectWeightMap.put(SEVERITY_4, SEVERITY_4_WEIGHT);
		defectWeightMap.put(SEVERITY_5, SEVERITY_5_WEIGHT);
		weightageFactorMap.put("defectScore",60);
		weightageFactorMap.put("failureScore",30);
		weightageFactorMap.put("cdSlipScore",10);
		weightageFactorMap.put("rejectedDefects",30);
		weightageFactorMap.put("defectDensity",10);
		weightageFactorMap.put("testEffectiveness",60);
		}
}
