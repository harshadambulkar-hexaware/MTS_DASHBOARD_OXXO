package com.hexaware.insight360DataPull.model;

public class SubBusinessLines {
	
	private int sub_business_line_id;
	private String sub_business_line_name;
	private int is_active;
	private int is_archived;
	private String sub_business_line_health;
	
	public String getSub_business_line_health() {
		return sub_business_line_health;
	}
	public void setSub_business_line_health(String sub_business_line_health) {
		this.sub_business_line_health = sub_business_line_health;
	}
	private int business_line_id;
	
	public int getSub_business_line_id() {
		return sub_business_line_id;
	}
	public void setSub_business_line_id(int sub_business_line_id) {
		this.sub_business_line_id = sub_business_line_id;
	}
	public String getSub_business_line_name() {
		return sub_business_line_name;
	}
	public void setSub_business_line_name(String sub_business_line_name) {
		this.sub_business_line_name = sub_business_line_name;
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
	public int getBusiness_line_id() {
		return business_line_id;
	}
	public void setBusiness_line_id(int business_line_id) {
		this.business_line_id = business_line_id;
	}
	
	
}
