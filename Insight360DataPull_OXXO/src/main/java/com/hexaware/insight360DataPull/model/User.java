package com.hexaware.insight360DataPull.model;

public class User {
    private int id;
    private String username;
    private String password;
    private String email;
    private String projectname;
	private String userrole;
    private String status;
    private String alm_host;
    private String alm_username;
    private String alm_password;
    private String tfs_host;
    private String tfs_username;
    private String tfs_password;
    
    public String getTfs_host() {
		return tfs_host;
	}
	public String getAlm_host() {
		return alm_host;
	}
	public void setAlm_host(String alm_host) {
		this.alm_host = alm_host;
	}
	public String getAlm_username() {
		return alm_username;
	}
	public void setAlm_username(String alm_username) {
		this.alm_username = alm_username;
	}
	public String getAlm_password() {
		return alm_password;
	}
	public void setAlm_password(String alm_password) {
		this.alm_password = alm_password;
	}
	public void setTfs_host(String tfs_host) {
		this.tfs_host = tfs_host;
	}
	public String getTfs_username() {
		return tfs_username;
	}
	public void setTfs_username(String tfs_username) {
		this.tfs_username = tfs_username;
	}
	public String getTfs_password() {
		return tfs_password;
	}
	public void setTfs_password(String tfs_password) {
		this.tfs_password = tfs_password;
	}
	
    public int getid () {
    	return id;
    }
    public void setid (int ID) {
    	this.id = ID;
    }
    
    public String getusername () {
    	return username;
    }
    public void setusername(String UserName) {
    	this.username = UserName;
    }
    
    public String getpassword () {
    	return password;
    }
    public void setpassword(String Pass) {
    	this.password = Pass;
    }
    
    public String getemail () {
    	return email;
    }
    public void setemail(String Email) {
    	this.email = Email;
    }
    
    public String getProjectname() {
		return projectname;
	}
	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}
	
	public String getUserrole() {
		return userrole;
	}
	public void setUserrole(String userrole) {
		this.userrole = userrole;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}