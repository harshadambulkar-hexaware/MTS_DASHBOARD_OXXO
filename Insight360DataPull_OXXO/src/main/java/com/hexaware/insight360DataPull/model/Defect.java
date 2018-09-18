package com.hexaware.insight360DataPull.model;

import java.util.Date;

public class Defect {
	
	private int defect_entity_id;
	private int defect_release_id;
	private String severity;
	private String priority;
	private String functional_area;
	private String title;
	private Date submit_date;
	private Date closed_date;
	private String state;
	private Date ist_release_eta;
	private String root_cause_analysis;
	private String ist_retest_status;
	private int defect_id;
	private String phase_raised;
	private String raised_during;
	private int detected_in_release;
	private Date timestamp;
	private String unit_test_candidate_flag;
	private String impacted_regions;
	private String rejected_issue_analysis;
	private String ist_classification;
	private String passed_first_ist_retest;
	private String passed_first_uat_retest;
	private Date first_defect_investcode_date;
	private Date last_defect_investcode_date;
	private int estimated_fix_time;
	private String data_src_tool;
	
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getData_src_tool() {
		return data_src_tool;
	}
	public void setData_src_tool(String data_src_tool) {
		this.data_src_tool = data_src_tool;
	}
	
	public int getDefect_entity_id() {
		return defect_entity_id;
	}
	public void setDefect_entity_id(int defect_entity_id) {
		this.defect_entity_id = defect_entity_id;
	}
	public int getDefect_release_id() {
		return defect_release_id;
	}
	public void setDefect_release_id(int defect_release_id) {
		this.defect_release_id = defect_release_id;
	}
	public String getSeverity() {
		return severity;
	}
	public void setSeverity(String severity) {
		this.severity = severity;
	}
//	public String getPriority() {
//		return priority;
//	}
//	public void setPriority(String priority) {
//		this.priority = priority;
//	}
	public String getFunctional_area() {
		return functional_area;
	}
	public void setFunctional_area(String functional_area) {
		this.functional_area = functional_area;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getSubmit_date() {
		return submit_date;
	}
	public void setSubmit_date(Date submit_date) {
		this.submit_date = submit_date;
	}
	public Date getClosed_date() {
		return closed_date;
	}
	public void setClosed_date(Date closed_date) {
		this.closed_date = closed_date;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Date getIst_release_eta() {
		return ist_release_eta;
	}
	public void setIst_release_eta(Date ist_release_eta) {
		this.ist_release_eta = ist_release_eta;
	}
	public String getRoot_cause_analysis() {
		return root_cause_analysis;
	}
	public void setRoot_cause_analysis(String root_cause_analysis) {
		this.root_cause_analysis = root_cause_analysis;
	}
	public String getIst_retest_status() {
		return ist_retest_status;
	}
	public void setIst_retest_status(String ist_retest_status) {
		this.ist_retest_status = ist_retest_status;
	}
	public int getDefect_id() {
		return defect_id;
	}
	public void setDefect_id(int defect_id) {
		this.defect_id = defect_id;
	}
	public String getPhase_raised() {
		return phase_raised;
	}
	public void setPhase_raised(String phase_raised) {
		this.phase_raised = phase_raised;
	}
	public String getRaised_during() {
		return raised_during;
	}
	public void setRaised_during(String raised_during) {
		this.raised_during = raised_during;
	}
	public int getDetected_in_release() {
		return detected_in_release;
	}
	public void setDetected_in_release(int detected_in_release) {
		this.detected_in_release = detected_in_release;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public String getUnit_test_candidate_flag() {
		return unit_test_candidate_flag;
	}
	public void setUnit_test_candidate_flag(String unit_test_candidate_flag) {
		this.unit_test_candidate_flag = unit_test_candidate_flag;
	}
	public String getImpacted_regions() {
		return impacted_regions;
	}
	public void setImpacted_regions(String impacted_regions) {
		this.impacted_regions = impacted_regions;
	}
	public String getRejected_issue_analysis() {
		return rejected_issue_analysis;
	}
	public void setRejected_issue_analysis(String rejected_issue_analysis) {
		this.rejected_issue_analysis = rejected_issue_analysis;
	}
	public String getIst_classification() {
		return ist_classification;
	}
	public void setIst_classification(String ist_classification) {
		this.ist_classification = ist_classification;
	}
	public String getPassed_first_ist_retest() {
		return passed_first_ist_retest;
	}
	public void setPassed_first_ist_retest(String passed_first_ist_retest) {
		this.passed_first_ist_retest = passed_first_ist_retest;
	}
	public String getPassed_first_uat_retest() {
		return passed_first_uat_retest;
	}
	public void setPassed_first_uat_retest(String passed_first_uat_retest) {
		this.passed_first_uat_retest = passed_first_uat_retest;
	}
	public Date getFirst_defect_investcode_date() {
		return first_defect_investcode_date;
	}
	public void setFirst_defect_investcode_date(Date first_defect_investcode_date) {
		this.first_defect_investcode_date = first_defect_investcode_date;
	}
	public Date getLast_defect_investcode_date() {
		return last_defect_investcode_date;
	}
	public void setLast_defect_investcode_date(Date last_defect_investcode_date) {
		this.last_defect_investcode_date = last_defect_investcode_date;
	}
	public int getEstimated_fix_time() {
		return estimated_fix_time;
	}
	public void setEstimated_fix_time(int estimated_fix_time) {
		this.estimated_fix_time = estimated_fix_time;
	}

}
