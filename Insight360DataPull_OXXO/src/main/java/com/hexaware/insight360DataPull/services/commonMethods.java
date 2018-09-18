package com.hexaware.insight360DataPull.services;

import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

public class commonMethods {
	
	private static final Logger logger = Logger.getLogger(commonMethods.class);
		//
		//Calculates the working days in the given pair of date
		//
		public static int getWorkingDaysBetweenTwoDates(Date startDate, Date endDate) {
		    Calendar startCal = Calendar.getInstance();
		    startCal.setTime(startDate);        
	
		    Calendar endCal = Calendar.getInstance();
		    endCal.setTime(endDate);
	
		    int workDays = 0;
	
		    //Return 0 if start and end are the same
		    if (startCal.getTimeInMillis() == endCal.getTimeInMillis()) {
		        return 1;
		    }
	
		    if (startCal.getTimeInMillis() > endCal.getTimeInMillis()) {
		        startCal.setTime(endDate);
		        endCal.setTime(startDate);
		    }
	
		    do {
		       //excluding start date
		        startCal.add(Calendar.DAY_OF_MONTH, 1);
		        if (startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
		            ++workDays;
		        }
		    } while (startCal.getTimeInMillis() < endCal.getTimeInMillis()); //excluding end date
	
		    return workDays + 1;
		}
		
		
		//
		//Calculating the planned test cases for release
		//
		public static int PlannedTCcalculation(Date reportdate, Date plannedStartDate, Date plannedEndDate, int totalTestCases)
        {
			int plannedtcs = 0;
			int perDayTCCount = 0;
          
            if (plannedStartDate != null && plannedEndDate != null)
            {
                if (reportdate.compareTo(plannedEndDate)==0 || reportdate.compareTo(plannedEndDate)==1)
                {
                    plannedtcs = totalTestCases;
                    logger.info("plannedtcs1 : "+plannedtcs);
                }
                else if (reportdate.compareTo(plannedStartDate) == -1)
                {
                    plannedtcs = 0;
                    logger.info("plannedtcs2 : "+plannedtcs);
                }
                else if ((reportdate.compareTo(plannedStartDate)==1 || reportdate.compareTo(plannedStartDate)==0) && (reportdate.compareTo(plannedEndDate) == -1 || reportdate.compareTo(plannedEndDate) == 0))
                {
                    //To get per day test cases as a whole number
                    perDayTCCount = Math.round(totalTestCases / getWorkingDaysBetweenTwoDates(plannedStartDate, plannedEndDate));
                    logger.info("perDayTCCount : "+perDayTCCount);
                    if ((perDayTCCount * getWorkingDaysBetweenTwoDates(plannedStartDate, reportdate)) > totalTestCases)
                    {
                        plannedtcs = totalTestCases;
                        logger.info("plannedtcs3 : "+plannedtcs);
                    }
                    else
                    {
                        plannedtcs = (perDayTCCount * getWorkingDaysBetweenTwoDates(plannedStartDate, reportdate));
                        logger.info("plannedtcs4 : "+plannedtcs);
                    }
                }
            }
           
            return plannedtcs;
        }
}
