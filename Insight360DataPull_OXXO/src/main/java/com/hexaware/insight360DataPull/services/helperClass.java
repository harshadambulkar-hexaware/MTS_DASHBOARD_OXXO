package com.hexaware.insight360DataPull.services;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class helperClass {

	
	
	public static void main(String[] args)throws IOException, ParseException {
		
		List<String> testCasesIdList = new ArrayList<String>();
		testCasesIdList.add("TC-19");
		testCasesIdList.add("TC-18");
		testCasesIdList.add("TC-17");
		
		//System.out.println(testCasesIdList.toString());
		String query = "";
		int tcInc = 1;
		for(String testName : testCasesIdList){
			query = query + "id='"+testName+"'";
			if(tcInc++ < testCasesIdList.size())
				query = query + " or ";
		}
		
		//Simple file reading
//		FileReader fr=new FileReader("C:/Users/31585/Desktop/reqSAcript.txt");  
//		int i;  
//		while((i=fr.read())!=-1)  
//		System.out.print((char)i);  
//		fr.close();  
		
		//Testing the working days calculation
//		SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
//		Date Startdate = dateformat.parse("01/06/2016");
//		Date Enddate = dateformat.parse("10/06/2016");
//		Date ReportDate = dateformat.parse("09/06/2016");
//		System.out.println("Working days :" + commonMethods.getWorkingDaysBetweenTwoDates(Startdate, Enddate)); 
//		System.out.println(commonMethods.PlannedTCcalculation(ReportDate, Startdate, Enddate, 33));
		
//		@SuppressWarnings("resource")
//		Scanner scanner = new Scanner(new FileReader("C:/Users/31585/Desktop/reqSAcript.txt"));
//        String str, var_1 = "", var_2 = "";
//        
//        @SuppressWarnings("unused")
//		String dtType = "";
//        
//        while ((str = scanner.nextLine().toLowerCase()) != null) {
//            //System.out.println(str);
//            for(int i = 0; i < str.length();i++) {
//            	String [] tokens = str.split("\\s+");
//                var_1 = tokens[1];
//                var_2 = tokens[2];
//                
//                if (var_2.substring(0, 3).equalsIgnoreCase("int") || var_2.substring(0, 3).equalsIgnoreCase("dec"))
//                	dtType = "int";
//                else if (var_2.substring(0, 3).equalsIgnoreCase("dat"))
//                	dtType = "Date";
//                else
//                	dtType = "String";
//                
//            }
//        //System.out.println("private " + dtType + " " + var_1.substring(1, var_1.length() - 1) + ";");
//        System.out.println("<property name=\"" + var_1.substring(1, var_1.length() - 1) + "\" column=\"" + var_1.substring(1, var_1.length() - 1).toUpperCase() + "\" />");
//        }
		
		
		/*ObjectMapper mapper = new ObjectMapper();
		
		Map<String, List<Map<String, Object>>> projectEntityDetails = mapper.readValue(new File("D:/kiran.json"), new TypeReference<Map<String, List<Map<String, Object>>>>(){});
		
		List<Map<String, Object>> blList = projectEntityDetails.get("BL");
		List<Map<String, Object>> sblList = projectEntityDetails.get("SBL");
		List<Map<String, Object>> projectList = projectEntityDetails.get("Project");
		List<Map<String, Object>> releaseList = projectEntityDetails.get("Release");
		
		for(Map<String, Object> selectedBL : blList) {
			String slectedBLName = selectedBL.get("name").toString();
			int selectedBLId = Integer.parseInt(selectedBL.get("BLid").toString());
			System.out.println("Selected BL :" + selectedBLId + " " + slectedBLName);
			for(Map<String, Object> selectedSBL : sblList) {
				String slectedSBLName = selectedSBL.get("name").toString();
				int selectedSBLId = Integer.parseInt(selectedSBL.get("SBLid").toString());
				int selectedSBLBLId = Integer.parseInt(selectedSBL.get("BLid").toString());
				if(selectedSBLBLId == selectedBLId) {
					System.out.println("\tSelected SBL :" + selectedSBLId + " " + slectedSBLName);
					for(Map<String, Object> selectedProject : projectList) {
						String slectedProjectName = selectedProject.get("name").toString();
						int selectedProjectId = Integer.parseInt(selectedProject.get("Projectid").toString());
						int selectedProjectSBLId = Integer.parseInt(selectedProject.get("SBLid").toString());
						if(selectedProjectSBLId == selectedSBLId) {
							System.out.println("\t\tSelected Project :" + selectedProjectId + " " + slectedProjectName);
							for(Map<String, Object> selectedRelease : releaseList) {
								String slectedReleaseName = selectedRelease.get("name").toString();
								int selectedReleaseId = Integer.parseInt(selectedRelease.get("Releaseid").toString());
								int selectedReleaseProjectId = Integer.parseInt(selectedRelease.get("Projectid").toString());
								if(selectedReleaseProjectId == selectedProjectId) {
									System.out.println("\t\t\tSelected Release :" + selectedReleaseId + " " + slectedReleaseName);
								}
							}
						}
					}
				}
			}
			
		}*/
		
	}

}
