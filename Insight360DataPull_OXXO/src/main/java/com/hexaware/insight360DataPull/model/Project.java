package com.hexaware.insight360DataPull.model;

public class Project {

	private int projectId;
    private String projectName;
    private int isActive;
    private int isArchived;
    private int isPlatform;
    private String displayName;
    private String domainName;
    private String data_src_tool;
    private int sub_business_line_id;
    private String health;
    private String sit_uat_insight;
    private int business_critical;
    private String sit_uat_rel_ind;
    private String sit_uat_req_ind;
    private String test_scope;
    
    
    public String getSit_uat_insight() {
		return sit_uat_insight;
	}
	public void setSit_uat_insight(String sit_uat_insight) {
		this.sit_uat_insight = sit_uat_insight;
	}
	public int getBusiness_critical() {
		return business_critical;
	}
	public void setBusiness_critical(int business_critical) {
		this.business_critical = business_critical;
	}
	public int getSub_business_line_id() {
		return sub_business_line_id;
	}
	public void setSub_business_line_id(int sub_business_line_id) {
		this.sub_business_line_id = sub_business_line_id;
	}
	public String getHealth() {
		return health;
	}
	public void setHealth(String health) {
		this.health = health;
	}   
	
	public String getData_src_tool() {
		return data_src_tool;
	}
	public void setData_src_tool(String data_src_tool) {
		this.data_src_tool = data_src_tool;
	}
 
    public int getprojectId () {
    	return projectId;
    }
    public void setprojectId (int ID) {
    	this.projectId = ID;
    }
    
    public String getprojectName () {
    	return projectName;
    }
    public void setprojectName(String pName) {
    	this.projectName = pName;
    }
    
    public int getisArchived () {
    	return isArchived;
    }
    public void setisArchived(int isarchived) {
    	this.isArchived = isarchived;
    }
    
    public int getisActive () {
    	return isActive;
    }
    public void setisActive(int isactive) {
    	this.isActive = isactive;
    }
    
    public int getisPlatform () {
    	return isPlatform;
    }
    public void setisPlatform(int isplatform) {
    	this.isPlatform = isplatform;
    }
    
    public String getdisplayName () {
    	return displayName;
    }
    public void setdisplayName(String displayName) {
    	this.displayName = displayName;
    }
    
    public String getdomainName () {
    	return domainName;
    }
    public void setdomainName(String domainName) {
    	this.domainName = domainName;
    }
	public String getSit_uat_rel_ind() {
		return sit_uat_rel_ind;
	}
	public void setSit_uat_rel_ind(String sit_uat_rel_ind) {
		this.sit_uat_rel_ind = sit_uat_rel_ind;
	}
	public String getSit_uat_req_ind() {
		return sit_uat_req_ind;
	}
	public void setSit_uat_req_ind(String sit_uat_req_ind) {
		this.sit_uat_req_ind = sit_uat_req_ind;
	}
	public String getTest_scope() {
		return test_scope;
	}
	public void setTest_scope(String test_scope) {
		this.test_scope = test_scope;
	}
}
