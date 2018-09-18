package com.hexaware.insight360DataPull.model;

import java.util.Date;

public class Release {

	private int release_entity_id;
	private int release_project_id;
	private int release_id;
	private String release_name;
	private int is_active;
	private int is_archived;
	private Date last_updated_ts;
	private Date schdl_istsignoff_date;
	private Date uat_start_date;
	private Date uat_end_date;
	private Date go_nogo_date;
	private Date go_live_date;
	private Date init_scope_cutoff_date;
	private Date first_uat_rels_orig_date;
	private Date first_uat_rels_curr_date;
	private Date exec_report_start_date;
	private String comments;
	private int cycle_weeks;
	private Date curr_end_dt;
	private Date plnd_end_dt;
	private Date req_planned_start;
	private Date req_actual_start;
	private Date req_planned_end;
	private Date req_actual_end;
	private Date ux_planned_start;
	private Date ux_actual_start;
	private Date ux_planned_end;
	private Date ux_actual_end;
	private Date dev_planned_start;
	private Date dev_actual_start;
	private Date dev_planned_end;
	private Date dev_actual_end;
	private Date ist_planned_start;
	private Date ist_actual_start;
	private Date ist_planned_end;
	private Date ist_actual_end;
	private Date uat_planned_start;
	private Date uat_actual_start;
	private Date uat_planned_end;
	private Date uat_actual_end;
	private String platforms;
	private Date ist_first_report_date;
	private Date ist_last_report_date;
	private Date scope_freeze_date;
	private Date planned_ist_start_date;
	private Date planned_uat_start_date;
	private Date uat_first_report_date;
	private Date uat_last_report_date;
	private Date rel_ist_planned_end_dt;
	private Date rel_ist_actual_end_dt;
	private Date rel_uat_planned_end_dt;
	private Date rel_uat_actual_end_dt;
	private String ist_health;
	private String uat_health;
	private String data_src_tool;
	private int uat_release_id;
	private int jira_project_id;
	private String phase;
	
	public int getUat_release_id() {
		return uat_release_id;
	}
	public void setUat_release_id(int uat_release_id) {
		this.uat_release_id = uat_release_id;
	}
	public String getPhase() {
		return phase;
	}
	public void setPhase(String phase) {
		this.phase = phase;
	}
	
	public String getData_src_tool() {
		return data_src_tool;
	}
	public void setData_src_tool(String data_src_tool) {
		this.data_src_tool = data_src_tool;
	}
	
	public int getRelease_entity_id() {
		return release_entity_id;
	}
	public void setRelease_entity_id(int release_entity_id) {
		this.release_entity_id = release_entity_id;
	}
	public int getRelease_project_id() {
		return release_project_id;
	}
	public void setRelease_project_id(int release_project_id) {
		this.release_project_id = release_project_id;
	}
	public int getRelease_id() {
		return release_id;
	}
	public void setRelease_id(int release_id) {
		this.release_id = release_id;
	}
	public String getRelease_name() {
		return release_name;
	}
	public void setRelease_name(String release_name) {
		this.release_name = release_name;
	}
	public int getIs_active() {
		return is_active;
	}
	public void setIs_active(int is_active) {
		this.is_active = is_active;
	}
	public int getIs_archived() {
		return is_archived;
	}
	public void setIs_archived(int is_archived) {
		this.is_archived = is_archived;
	}
	public Date getLast_updated_ts() {
		return last_updated_ts;
	}
	public void setLast_updated_ts(Date last_updated_ts) {
		this.last_updated_ts = last_updated_ts;
	}
	public Date getSchdl_istsignoff_date() {
		return schdl_istsignoff_date;
	}
	public void setSchdl_istsignoff_date(Date schdl_istsignoff_date) {
		this.schdl_istsignoff_date = schdl_istsignoff_date;
	}
	public Date getUat_start_date() {
		return uat_start_date;
	}
	public void setUat_start_date(Date uat_start_date) {
		this.uat_start_date = uat_start_date;
	}
	public Date getUat_end_date() {
		return uat_end_date;
	}
	public void setUat_end_date(Date uat_end_date) {
		this.uat_end_date = uat_end_date;
	}
	public Date getGo_nogo_date() {
		return go_nogo_date;
	}
	public void setGo_nogo_date(Date go_nogo_date) {
		this.go_nogo_date = go_nogo_date;
	}
	public Date getGo_live_date() {
		return go_live_date;
	}
	public void setGo_live_date(Date go_live_date) {
		this.go_live_date = go_live_date;
	}
	public Date getInit_scope_cutoff_date() {
		return init_scope_cutoff_date;
	}
	public void setInit_scope_cutoff_date(Date init_scope_cutoff_date) {
		this.init_scope_cutoff_date = init_scope_cutoff_date;
	}
	public Date getFirst_uat_rels_orig_date() {
		return first_uat_rels_orig_date;
	}
	public void setFirst_uat_rels_orig_date(Date first_uat_rels_orig_date) {
		this.first_uat_rels_orig_date = first_uat_rels_orig_date;
	}
	public Date getFirst_uat_rels_curr_date() {
		return first_uat_rels_curr_date;
	}
	public void setFirst_uat_rels_curr_date(Date first_uat_rels_curr_date) {
		this.first_uat_rels_curr_date = first_uat_rels_curr_date;
	}
	public Date getExec_report_start_date() {
		return exec_report_start_date;
	}
	public void setExec_report_start_date(Date exec_report_start_date) {
		this.exec_report_start_date = exec_report_start_date;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public int getCycle_weeks() {
		return cycle_weeks;
	}
	public void setCycle_weeks(int cycle_weeks) {
		this.cycle_weeks = cycle_weeks;
	}
	public Date getCurr_end_dt() {
		return curr_end_dt;
	}
	public void setCurr_end_dt(Date curr_end_dt) {
		this.curr_end_dt = curr_end_dt;
	}
	public Date getPlnd_end_dt() {
		return plnd_end_dt;
	}
	public void setPlnd_end_dt(Date plnd_end_dt) {
		this.plnd_end_dt = plnd_end_dt;
	}
	public Date getReq_planned_start() {
		return req_planned_start;
	}
	public void setReq_planned_start(Date req_planned_start) {
		this.req_planned_start = req_planned_start;
	}
	public Date getReq_actual_start() {
		return req_actual_start;
	}
	public void setReq_actual_start(Date req_actual_start) {
		this.req_actual_start = req_actual_start;
	}
	public Date getReq_planned_end() {
		return req_planned_end;
	}
	public void setReq_planned_end(Date req_planned_end) {
		this.req_planned_end = req_planned_end;
	}
	public Date getReq_actual_end() {
		return req_actual_end;
	}
	public void setReq_actual_end(Date req_actual_end) {
		this.req_actual_end = req_actual_end;
	}
	public Date getUx_planned_start() {
		return ux_planned_start;
	}
	public void setUx_planned_start(Date ux_planned_start) {
		this.ux_planned_start = ux_planned_start;
	}
	public Date getUx_actual_start() {
		return ux_actual_start;
	}
	public void setUx_actual_start(Date ux_actual_start) {
		this.ux_actual_start = ux_actual_start;
	}
	public Date getUx_planned_end() {
		return ux_planned_end;
	}
	public void setUx_planned_end(Date ux_planned_end) {
		this.ux_planned_end = ux_planned_end;
	}
	public Date getUx_actual_end() {
		return ux_actual_end;
	}
	public void setUx_actual_end(Date ux_actual_end) {
		this.ux_actual_end = ux_actual_end;
	}
	public Date getDev_planned_start() {
		return dev_planned_start;
	}
	public void setDev_planned_start(Date dev_planned_start) {
		this.dev_planned_start = dev_planned_start;
	}
	public Date getDev_actual_start() {
		return dev_actual_start;
	}
	public void setDev_actual_start(Date dev_actual_start) {
		this.dev_actual_start = dev_actual_start;
	}
	public Date getDev_planned_end() {
		return dev_planned_end;
	}
	public void setDev_planned_end(Date dev_planned_end) {
		this.dev_planned_end = dev_planned_end;
	}
	public Date getDev_actual_end() {
		return dev_actual_end;
	}
	public void setDev_actual_end(Date dev_actual_end) {
		this.dev_actual_end = dev_actual_end;
	}
	public Date getIst_planned_start() {
		return ist_planned_start;
	}
	public void setIst_planned_start(Date ist_planned_start) {
		this.ist_planned_start = ist_planned_start;
	}
	public Date getIst_actual_start() {
		return ist_actual_start;
	}
	public void setIst_actual_start(Date ist_actual_start) {
		this.ist_actual_start = ist_actual_start;
	}
	public Date getIst_planned_end() {
		return ist_planned_end;
	}
	public void setIst_planned_end(Date ist_planned_end) {
		this.ist_planned_end = ist_planned_end;
	}
	public Date getIst_actual_end() {
		return ist_actual_end;
	}
	public void setIst_actual_end(Date ist_actual_end) {
		this.ist_actual_end = ist_actual_end;
	}
	public Date getUat_planned_start() {
		return uat_planned_start;
	}
	public void setUat_planned_start(Date uat_planned_start) {
		this.uat_planned_start = uat_planned_start;
	}
	public Date getUat_actual_start() {
		return uat_actual_start;
	}
	public void setUat_actual_start(Date uat_actual_start) {
		this.uat_actual_start = uat_actual_start;
	}
	public Date getUat_planned_end() {
		return uat_planned_end;
	}
	public void setUat_planned_end(Date uat_planned_end) {
		this.uat_planned_end = uat_planned_end;
	}
	public Date getUat_actual_end() {
		return uat_actual_end;
	}
	public void setUat_actual_end(Date uat_actual_end) {
		this.uat_actual_end = uat_actual_end;
	}
	public String getPlatforms() {
		return platforms;
	}
	public void setPlatforms(String platforms) {
		this.platforms = platforms;
	}
	public Date getIst_first_report_date() {
		return ist_first_report_date;
	}
	public void setIst_first_report_date(Date ist_first_report_date) {
		this.ist_first_report_date = ist_first_report_date;
	}
	public Date getIst_last_report_date() {
		return ist_last_report_date;
	}
	public void setIst_last_report_date(Date ist_last_report_date) {
		this.ist_last_report_date = ist_last_report_date;
	}
	public Date getScope_freeze_date() {
		return scope_freeze_date;
	}
	public void setScope_freeze_date(Date scope_freeze_date) {
		this.scope_freeze_date = scope_freeze_date;
	}
	public Date getPlanned_ist_start_date() {
		return planned_ist_start_date;
	}
	public void setPlanned_ist_start_date(Date planned_ist_start_date) {
		this.planned_ist_start_date = planned_ist_start_date;
	}
	public Date getPlanned_uat_start_date() {
		return planned_uat_start_date;
	}
	public void setPlanned_uat_start_date(Date planned_uat_start_date) {
		this.planned_uat_start_date = planned_uat_start_date;
	}
	public Date getUat_first_report_date() {
		return uat_first_report_date;
	}
	public void setUat_first_report_date(Date uat_first_report_date) {
		this.uat_first_report_date = uat_first_report_date;
	}
	public Date getUat_last_report_date() {
		return uat_last_report_date;
	}
	public void setUat_last_report_date(Date uat_last_report_date) {
		this.uat_last_report_date = uat_last_report_date;
	}
	public Date getRel_ist_planned_end_dt() {
		return rel_ist_planned_end_dt;
	}
	public void setRel_ist_planned_end_dt(Date rel_ist_planned_end_dt) {
		this.rel_ist_planned_end_dt = rel_ist_planned_end_dt;
	}
	public Date getRel_ist_actual_end_dt() {
		return rel_ist_actual_end_dt;
	}
	public void setRel_ist_actual_end_dt(Date rel_ist_actual_end_dt) {
		this.rel_ist_actual_end_dt = rel_ist_actual_end_dt;
	}
	public Date getRel_uat_planned_end_dt() {
		return rel_uat_planned_end_dt;
	}
	public void setRel_uat_planned_end_dt(Date rel_uat_planned_end_dt) {
		this.rel_uat_planned_end_dt = rel_uat_planned_end_dt;
	}
	public Date getRel_uat_actual_end_dt() {
		return rel_uat_actual_end_dt;
	}
	public void setRel_uat_actual_end_dt(Date rel_uat_actual_end_dt) {
		this.rel_uat_actual_end_dt = rel_uat_actual_end_dt;
	}
	public String getIst_health() {
		return ist_health;
	}
	public void setIst_health(String ist_health) {
		this.ist_health = ist_health;
	}
	public String getUat_health() {
		return uat_health;
	}
	public void setUat_health(String uat_health) {
		this.uat_health = uat_health;
	}
	public int getJira_project_id() {
		return jira_project_id;
	}
	public void setJira_project_id(int jira_project_id) {
		this.jira_project_id = jira_project_id;
	}
	
	
}
