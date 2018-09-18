package com.hexaware.insight360DataPull.model;

import java.util.Date;

public class RequirementProgress {
	
	private int req_progress_entity_id;
	private int req_release_id;
	private int req_id;
	private String test_phase;
	private Date planned_planning_start;
	private Date actual_planning_start;
	private Date planned_planning_end;
	private Date actual_planning_end;
	private int num_of_test_cases;
	private int tcs_with_iterations;
	private String package_number;
	private String direct_coverage;
	private Date planned_dev_certification;
	private Date actual_dev_certification;
	private Date planned_execution_start;
	private Date actual_execution_start;
	private Date planned_execution_end;
	private Date actual_execution_end;
	private int tcs_passed_first_run;
	private int tcs_failed_first_run;
	private int dev_failures;
	private int tsc_pending_fun_request;
	private int tcs_pending;
	private Date current_certification;
	private Date actual_certification;
	private int tcs_passed_retest;
	private Date timestamp;
	private String in_scope;
	private int tcs_pending_fun_request;
	private Date orig_plnd_exec_start_dt;
	private Date orig_plnd_exec_end_dt;
	private int orig_plnd_tcs_count;
	private int execution_estimate;
	private int planning_estimate;
	private String planning_status;
	private String platform;
	private int requirement_defn_percentage;
	private int ux_progress_percentage;
	private Date orig_dev_cert_date;
	private Date orig_cert_date;
	private Date cr_rel_link_date;
	private String data_src_tool;
	private String req_progress_module;
	
	public String getReq_progress_module() {
		return req_progress_module;
	}
	public void setReq_progress_module(String req_progress_module) {
		this.req_progress_module = req_progress_module;
	}
	public int getTsc_pending_fun_request() {
		return tsc_pending_fun_request;
	}
	public void setTsc_pending_fun_request(int tsc_pending_fun_request) {
		this.tsc_pending_fun_request = tsc_pending_fun_request;
	}
	public int getTcs_pending() {
		return tcs_pending;
	}
	public void setTcs_pending(int tcs_pending) {
		this.tcs_pending = tcs_pending;
	}
	public Date getCurrent_certification() {
		return current_certification;
	}
	public void setCurrent_certification(Date current_certification) {
		this.current_certification = current_certification;
	}
	public Date getActual_certification() {
		return actual_certification;
	}
	public void setActual_certification(Date actual_certification) {
		this.actual_certification = actual_certification;
	}
	public int getTcs_passed_retest() {
		return tcs_passed_retest;
	}
	public void setTcs_passed_retest(int tcs_passed_retest) {
		this.tcs_passed_retest = tcs_passed_retest;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public String getIn_scope() {
		return in_scope;
	}
	public void setIn_scope(String in_scope) {
		this.in_scope = in_scope;
	}
	public int getTcs_pending_fun_request() {
		return tcs_pending_fun_request;
	}
	public void setTcs_pending_fun_request(int tcs_pending_fun_request) {
		this.tcs_pending_fun_request = tcs_pending_fun_request;
	}
	public Date getOrig_plnd_exec_start_dt() {
		return orig_plnd_exec_start_dt;
	}
	public void setOrig_plnd_exec_start_dt(Date orig_plnd_exec_start_dt) {
		this.orig_plnd_exec_start_dt = orig_plnd_exec_start_dt;
	}
	public Date getOrig_plnd_exec_end_dt() {
		return orig_plnd_exec_end_dt;
	}
	public void setOrig_plnd_exec_end_dt(Date orig_plnd_exec_end_dt) {
		this.orig_plnd_exec_end_dt = orig_plnd_exec_end_dt;
	}
	public int getOrig_plnd_tcs_count() {
		return orig_plnd_tcs_count;
	}
	public void setOrig_plnd_tcs_count(int orig_plnd_tcs_count) {
		this.orig_plnd_tcs_count = orig_plnd_tcs_count;
	}
	public int getExecution_estimate() {
		return execution_estimate;
	}
	public void setExecution_estimate(int execution_estimate) {
		this.execution_estimate = execution_estimate;
	}
	public int getPlanning_estimate() {
		return planning_estimate;
	}
	public void setPlanning_estimate(int planning_estimate) {
		this.planning_estimate = planning_estimate;
	}
	public String getPlanning_status() {
		return planning_status;
	}
	public void setPlanning_status(String planning_status) {
		this.planning_status = planning_status;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public int getRequirement_defn_percentage() {
		return requirement_defn_percentage;
	}
	public void setRequirement_defn_percentage(int requirement_defn_percentage) {
		this.requirement_defn_percentage = requirement_defn_percentage;
	}
	public int getUx_progress_percentage() {
		return ux_progress_percentage;
	}
	public void setUx_progress_percentage(int ux_progress_percentage) {
		this.ux_progress_percentage = ux_progress_percentage;
	}
	public Date getOrig_dev_cert_date() {
		return orig_dev_cert_date;
	}
	public void setOrig_dev_cert_date(Date orig_dev_cert_date) {
		this.orig_dev_cert_date = orig_dev_cert_date;
	}
	public Date getOrig_cert_date() {
		return orig_cert_date;
	}
	public void setOrig_cert_date(Date orig_cert_date) {
		this.orig_cert_date = orig_cert_date;
	}
	public Date getCr_rel_link_date() {
		return cr_rel_link_date;
	}
	public void setCr_rel_link_date(Date cr_rel_link_date) {
		this.cr_rel_link_date = cr_rel_link_date;
	}
	public String getData_src_tool() {
		return data_src_tool;
	}
	public void setData_src_tool(String data_src_tool) {
		this.data_src_tool = data_src_tool;
	}
	
	public int getReq_release_id() {
		return req_release_id;
	}
	public void setReq_release_id(int req_release_id) {
		this.req_release_id = req_release_id;
	}
	public int getReq_id() {
		return req_id;
	}
	public void setReq_id(int req_id) {
		this.req_id = req_id;
	}
	public String getTest_phase() {
		return test_phase;
	}
	public void setTest_phase(String test_phase) {
		this.test_phase = test_phase;
	}
	public Date getPlanned_planning_start() {
		return planned_planning_start;
	}
	public void setPlanned_planning_start(Date planned_planning_start) {
		this.planned_planning_start = planned_planning_start;
	}
	public Date getActual_planning_start() {
		return actual_planning_start;
	}
	public void setActual_planning_start(Date actual_planning_start) {
		this.actual_planning_start = actual_planning_start;
	}
	public Date getPlanned_planning_end() {
		return planned_planning_end;
	}
	public void setPlanned_planning_end(Date planned_planning_end) {
		this.planned_planning_end = planned_planning_end;
	}
	public Date getActual_planning_end() {
		return actual_planning_end;
	}
	public void setActual_planning_end(Date actual_planning_end) {
		this.actual_planning_end = actual_planning_end;
	}
	public int getNum_of_test_cases() {
		return num_of_test_cases;
	}
	public void setNum_of_test_cases(int num_of_test_cases) {
		this.num_of_test_cases = num_of_test_cases;
	}
	public int getTcs_with_iterations() {
		return tcs_with_iterations;
	}
	public void setTcs_with_iterations(int tcs_with_iterations) {
		this.tcs_with_iterations = tcs_with_iterations;
	}
	public String getPackage_number() {
		return package_number;
	}
	public void setPackage_number(String package_number) {
		this.package_number = package_number;
	}
	public String getDirect_coverage() {
		return direct_coverage;
	}
	public void setDirect_coverage(String direct_coverage) {
		this.direct_coverage = direct_coverage;
	}
	public Date getPlanned_dev_certification() {
		return planned_dev_certification;
	}
	public void setPlanned_dev_certification(Date planned_dev_certification) {
		this.planned_dev_certification = planned_dev_certification;
	}
	public Date getActual_dev_certification() {
		return actual_dev_certification;
	}
	public void setActual_dev_certification(Date actual_dev_certification) {
		this.actual_dev_certification = actual_dev_certification;
	}
	public Date getPlanned_execution_start() {
		return planned_execution_start;
	}
	public void setPlanned_execution_start(Date planned_execution_start) {
		this.planned_execution_start = planned_execution_start;
	}
	public Date getActual_execution_start() {
		return actual_execution_start;
	}
	public void setActual_execution_start(Date actual_execution_start) {
		this.actual_execution_start = actual_execution_start;
	}
	public Date getPlanned_execution_end() {
		return planned_execution_end;
	}
	public void setPlanned_execution_end(Date planned_execution_end) {
		this.planned_execution_end = planned_execution_end;
	}
	public Date getActual_execution_end() {
		return actual_execution_end;
	}
	public void setActual_execution_end(Date actual_execution_end) {
		this.actual_execution_end = actual_execution_end;
	}
	public int getTcs_passed_first_run() {
		return tcs_passed_first_run;
	}
	public void setTcs_passed_first_run(int tcs_passed_first_run) {
		this.tcs_passed_first_run = tcs_passed_first_run;
	}
	public int getTcs_failed_first_run() {
		return tcs_failed_first_run;
	}
	public void setTcs_failed_first_run(int tcs_failed_first_run) {
		this.tcs_failed_first_run = tcs_failed_first_run;
	}
	public int getDev_failures() {
		return dev_failures;
	}
	public void setDev_failures(int dev_failures) {
		this.dev_failures = dev_failures;
	}
	public int getReq_progress_entity_id() {
		return req_progress_entity_id;
	}
	public void setReq_progress_entity_id(int req_progress_entity_id) {
		this.req_progress_entity_id = req_progress_entity_id;
	}
	
	


}
