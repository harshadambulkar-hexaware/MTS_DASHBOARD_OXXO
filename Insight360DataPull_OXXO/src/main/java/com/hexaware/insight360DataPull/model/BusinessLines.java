package com.hexaware.insight360DataPull.model;

public class BusinessLines {
	
	private int business_line_id;
	private String business_line_name;
	private int is_active;
	private int is_archived;
	private String business_line_health;
	
	public int getBusiness_line_id() {
		return business_line_id;
	}
	public void setBusiness_line_id(int business_line_id) {
		this.business_line_id = business_line_id;
	}
	public String getBusiness_line_name() {
		return business_line_name;
	}
	public void setBusiness_line_name(String business_line_name) {
		this.business_line_name = business_line_name;
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
	public String getBusiness_line_health() {
		return business_line_health;
	}
	public void setBusiness_line_health(String business_line_health) {
		this.business_line_health = business_line_health;
	}
	

}
