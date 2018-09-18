package com.hexaware.insight360DataPull.model;

import java.util.Date;

public class Requirement {
	
	private int requirement_entity_id;
	private int req_release_id;
	private int req_id;
	private String title;
	private String module;
	private String region;
	private String type_of_change;
	private Date approved_for_dev_date;
	private String direct_coverage;
	private String data_src_tool;
	
	public int getRequirement_entity_id() {
		return requirement_entity_id;
	}
	public void setRequirement_entity_id(int requirement_entity_id) {
		this.requirement_entity_id = requirement_entity_id;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getType_of_change() {
		return type_of_change;
	}
	public void setType_of_change(String type_of_change) {
		this.type_of_change = type_of_change;
	}
	public Date getApproved_for_dev_date() {
		return approved_for_dev_date;
	}
	public void setApproved_for_dev_date(Date approved_for_dev_date) {
		this.approved_for_dev_date = approved_for_dev_date;
	}
	public String getDirect_coverage() {
		return direct_coverage;
	}
	public void setDirect_coverage(String direct_coverage) {
		this.direct_coverage = direct_coverage;
	}
	public String getData_src_tool() {
		return data_src_tool;
	}
	public void setData_src_tool(String data_src_tool) {
		this.data_src_tool = data_src_tool;
	}
	
	
}
