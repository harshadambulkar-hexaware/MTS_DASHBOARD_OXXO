package com.hexaware.insight360DataPull.model;

import java.util.Date;

public class DatapullLog {

	private int datapull_id;
	private int project_id;
	private String project_name;
	private int release_id;
	private String release_name;
	private String start_date;
	private String start_time;
	private String src_tool;
	private String end_date;
	private String end_time;
	
	private int req_success;
	private int def_success;
	private int def_hist_success;
	private String req_exception;
	private String def_exception;
	private String def_hist_exception;
	
	
	public int getReq_success() {
		return req_success;
	}
	public void setReq_success(int req_success) {
		this.req_success = req_success;
	}
	public int getDef_success() {
		return def_success;
	}
	public void setDef_success(int def_success) {
		this.def_success = def_success;
	}
	public int getDef_hist_success() {
		return def_hist_success;
	}
	public void setDef_hist_success(int def_hist_success) {
		this.def_hist_success = def_hist_success;
	}
	public String getReq_exception() {
		return req_exception;
	}
	public void setReq_exception(String req_exception) {
		this.req_exception = req_exception;
	}
	public String getDef_exception() {
		return def_exception;
	}
	public void setDef_exception(String def_exception) {
		this.def_exception = def_exception;
	}
	public String getDef_hist_exception() {
		return def_hist_exception;
	}
	public void setDef_hist_exception(String def_hist_exception) {
		this.def_hist_exception = def_hist_exception;
	}
	public int getDatapull_id() {
		return datapull_id;
	}
	public void setDatapull_id(int datapull_id) {
		this.datapull_id = datapull_id;
	}
	public int getProject_id() {
		return project_id;
	}
	public void setProject_id(int project_id) {
		this.project_id = project_id;
	}
	public String getProject_name() {
		return project_name;
	}
	public void setProject_name(String project_name) {
		this.project_name = project_name;
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
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getSrc_tool() {
		return src_tool;
	}
	public void setSrc_tool(String src_tool) {
		this.src_tool = src_tool;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	
}
