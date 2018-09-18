package com.hexaware.insight360DataPull.model;

import java.sql.Date;

public class ScopeAnalysis {
	
	private int scope_entity_id;
	private int scope_release_id;
	private int crs_release_scope;
	private int crs_ist_scope;
	private int crs_uat_scope;
	private Date scope_freeze_date;
	private int crs_added_after_freeze;
	private Date planned_ist_start_date;
	private Date actual_ist_start_date;
	private int delta_ist_start_date;
	private Date planned_uat_start_date;
	private Date actual_uat_start_date;
	private int delta_uat_start_date;
	private String data_src_tool;
	
	public String getData_src_tool() {
		return data_src_tool;
	}
	public void setData_src_tool(String data_src_tool) {
		this.data_src_tool = data_src_tool;
	}
	
	public int getScope_entity_id() {
		return scope_entity_id;
	}
	public void setScope_entity_id(int scope_entity_id) {
		this.scope_entity_id = scope_entity_id;
	}
	public int getScope_release_id() {
		return scope_release_id;
	}
	public void setScope_release_id(int scope_release_id) {
		this.scope_release_id = scope_release_id;
	}
	public int getCrs_release_scope() {
		return crs_release_scope;
	}
	public void setCrs_release_scope(int crs_release_scope) {
		this.crs_release_scope = crs_release_scope;
	}
	public int getCrs_ist_scope() {
		return crs_ist_scope;
	}
	public void setCrs_ist_scope(int crs_ist_scope) {
		this.crs_ist_scope = crs_ist_scope;
	}
	public int getCrs_uat_scope() {
		return crs_uat_scope;
	}
	public void setCrs_uat_scope(int crs_uat_scope) {
		this.crs_uat_scope = crs_uat_scope;
	}
	public Date getScope_freeze_date() {
		return scope_freeze_date;
	}
	public void setScope_freeze_date(Date scope_freeze_date) {
		this.scope_freeze_date = scope_freeze_date;
	}
	public int getCrs_added_after_freeze() {
		return crs_added_after_freeze;
	}
	public void setCrs_added_after_freeze(int crs_added_after_freeze) {
		this.crs_added_after_freeze = crs_added_after_freeze;
	}
	public Date getPlanned_ist_start_date() {
		return planned_ist_start_date;
	}
	public void setPlanned_ist_start_date(Date planned_ist_start_date) {
		this.planned_ist_start_date = planned_ist_start_date;
	}
	public Date getActual_ist_start_date() {
		return actual_ist_start_date;
	}
	public void setActual_ist_start_date(Date actual_ist_start_date) {
		this.actual_ist_start_date = actual_ist_start_date;
	}
	public int getDelta_ist_start_date() {
		return delta_ist_start_date;
	}
	public void setDelta_ist_start_date(int delta_ist_start_date) {
		this.delta_ist_start_date = delta_ist_start_date;
	}
	public Date getPlanned_uat_start_date() {
		return planned_uat_start_date;
	}
	public void setPlanned_uat_start_date(Date planned_uat_start_date) {
		this.planned_uat_start_date = planned_uat_start_date;
	}
	public Date getActual_uat_start_date() {
		return actual_uat_start_date;
	}
	public void setActual_uat_start_date(Date actual_uat_start_date) {
		this.actual_uat_start_date = actual_uat_start_date;
	}
	public int getDelta_uat_start_date() {
		return delta_uat_start_date;
	}
	public void setDelta_uat_start_date(int delta_uat_start_date) {
		this.delta_uat_start_date = delta_uat_start_date;
	}

}
