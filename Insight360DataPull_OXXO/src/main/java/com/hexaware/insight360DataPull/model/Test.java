package com.hexaware.insight360DataPull.model;

import java.util.Date;

public class Test {
	
	private int test_entity_id;
	private int test_req_id;
	private int test_release_id;
	private int test_id;
	private String title;
	private Date creation_time;
	private Date last_modified;
	private String status;
	private String exec_status;
	private String test_phase;
	private String data_src_tool;
	
	public String getData_src_tool() {
		return data_src_tool;
	}
	public void setData_src_tool(String data_src_tool) {
		this.data_src_tool = data_src_tool;
	}
	
	public int getTest_entity_id() {
		return test_entity_id;
	}
	public void setTest_entity_id(int test_entity_id) {
		this.test_entity_id = test_entity_id;
	}
	public int getTest_req_id() {
		return test_req_id;
	}
	public void setTest_req_id(int test_req_id) {
		this.test_req_id = test_req_id;
	}
	public int getTest_release_id() {
		return test_release_id;
	}
	public void setTest_release_id(int test_release_id) {
		this.test_release_id = test_release_id;
	}
	public int getTest_id() {
		return test_id;
	}
	public void setTest_id(int test_id) {
		this.test_id = test_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getCreation_time() {
		return creation_time;
	}
	public void setCreation_time(Date creation_time) {
		this.creation_time = creation_time;
	}
	public Date getLast_modified() {
		return last_modified;
	}
	public void setLast_modified(Date last_modified) {
		this.last_modified = last_modified;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getExec_status() {
		return exec_status;
	}
	public void setExec_status(String exec_status) {
		this.exec_status = exec_status;
	}
	public String getTest_phase() {
		return test_phase;
	}
	public void setTest_phase(String test_phase) {
		this.test_phase = test_phase;
	}
	
}
