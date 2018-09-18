package com.hexaware.insight360DataPull.model;

import java.util.Date;

public class DsrCrHistory {

	private int history_entity_id;
	private int history_requirement_id;
	private int history_release_id;
	private Date history_date;
	private String history_phase;
	private int cr_planned_cases;
	private int cr_passed_cases;
	private int cr_failed_cases;
	private String cr_execution_status;
	private Date cr_planned_ist_end;
	private String data_src_tool;
	private int fk_req_progress_entity_id;    
	
	public int getFk_req_progress_entity_id() {
		return fk_req_progress_entity_id;
	}
	public void setFk_req_progress_entity_id(int fk_req_progress_entity_id) {
		this.fk_req_progress_entity_id = fk_req_progress_entity_id;
	}
	public String getData_src_tool() {
		return data_src_tool;
	}
	public void setData_src_tool(String data_src_tool) {
		this.data_src_tool = data_src_tool;
	}
	
	public int getHistory_entity_id() {
		return history_entity_id;
	}
	public void setHistory_entity_id(int history_entity_id) {
		this.history_entity_id = history_entity_id;
	}
	public int getHistory_requirement_id() {
		return history_requirement_id;
	}
	public void setHistory_requirement_id(int history_requirement_id) {
		this.history_requirement_id = history_requirement_id;
	}
	public int getHistory_release_id() {
		return history_release_id;
	}
	public void setHistory_release_id(int history_release_id) {
		this.history_release_id = history_release_id;
	}
	public Date getHistory_date() {
		return history_date;
	}
	public void setHistory_date(Date history_date) {
		this.history_date = history_date;
	}
	public String getHistory_phase() {
		return history_phase;
	}
	public void setHistory_phase(String history_phase) {
		this.history_phase = history_phase;
	}
	public int getCr_planned_cases() {
		return cr_planned_cases;
	}
	public void setCr_planned_cases(int cr_planned_cases) {
		this.cr_planned_cases = cr_planned_cases;
	}
	public int getCr_passed_cases() {
		return cr_passed_cases;
	}
	public void setCr_passed_cases(int cr_passed_cases) {
		this.cr_passed_cases = cr_passed_cases;
	}
	public int getCr_failed_cases() {
		return cr_failed_cases;
	}
	public void setCr_failed_cases(int cr_failed_cases) {
		this.cr_failed_cases = cr_failed_cases;
	}
	public String getCr_execution_status() {
		return cr_execution_status;
	}
	public void setCr_execution_status(String cr_execution_status) {
		this.cr_execution_status = cr_execution_status;
	}
	public Date getCr_planned_ist_end() {
		return cr_planned_ist_end;
	}
	public void setCr_planned_ist_end(Date cr_planned_ist_end) {
		this.cr_planned_ist_end = cr_planned_ist_end;
	}
	
}
