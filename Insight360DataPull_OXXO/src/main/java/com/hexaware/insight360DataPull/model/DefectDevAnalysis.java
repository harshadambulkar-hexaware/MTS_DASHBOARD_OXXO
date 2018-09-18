package com.hexaware.insight360DataPull.model;

public class DefectDevAnalysis {
	
	private int defect_dev_entity_id;
	private int defect_dev_release_id;
	private int ist_confirmed_defects;
	private int ist_dev_defects;
	private int ist_unit_testing_candidates;
	private int uat_confirmed_defects;
	private int uat_dev_defects;
	private int uat_unit_testing_candidates;
	private String ist_initial_pass_rate;
	private String uat_initial_pass_rate;
	private String retest_initial_pass_rate;
	private String defect_score;
	private String defect_aging_score;
	private String code_slip_score;
	private String data_src_tool;
	
	public String getData_src_tool() {
		return data_src_tool;
	}
	public void setData_src_tool(String data_src_tool) {
		this.data_src_tool = data_src_tool;
	}
	
	public int getDefect_dev_entity_id() {
		return defect_dev_entity_id;
	}
	public void setDefect_dev_entity_id(int defect_dev_entity_id) {
		this.defect_dev_entity_id = defect_dev_entity_id;
	}
	public int getDefect_dev_release_id() {
		return defect_dev_release_id;
	}
	public void setDefect_dev_release_id(int defect_dev_release_id) {
		this.defect_dev_release_id = defect_dev_release_id;
	}
	public int getIst_confirmed_defects() {
		return ist_confirmed_defects;
	}
	public void setIst_confirmed_defects(int ist_confirmed_defects) {
		this.ist_confirmed_defects = ist_confirmed_defects;
	}
	public int getIst_dev_defects() {
		return ist_dev_defects;
	}
	public void setIst_dev_defects(int ist_dev_defects) {
		this.ist_dev_defects = ist_dev_defects;
	}
	public int getIst_unit_testing_candidates() {
		return ist_unit_testing_candidates;
	}
	public void setIst_unit_testing_candidates(int ist_unit_testing_candidates) {
		this.ist_unit_testing_candidates = ist_unit_testing_candidates;
	}
	public int getUat_confirmed_defects() {
		return uat_confirmed_defects;
	}
	public void setUat_confirmed_defects(int uat_confirmed_defects) {
		this.uat_confirmed_defects = uat_confirmed_defects;
	}
	public int getUat_dev_defects() {
		return uat_dev_defects;
	}
	public void setUat_dev_defects(int uat_dev_defects) {
		this.uat_dev_defects = uat_dev_defects;
	}
	public int getUat_unit_testing_candidates() {
		return uat_unit_testing_candidates;
	}
	public void setUat_unit_testing_candidates(int uat_unit_testing_candidates) {
		this.uat_unit_testing_candidates = uat_unit_testing_candidates;
	}
	public String getIst_initial_pass_rate() {
		return ist_initial_pass_rate;
	}
	public void setIst_initial_pass_rate(String ist_initial_pass_rate) {
		this.ist_initial_pass_rate = ist_initial_pass_rate;
	}
	public String getUat_initial_pass_rate() {
		return uat_initial_pass_rate;
	}
	public void setUat_initial_pass_rate(String uat_initial_pass_rate) {
		this.uat_initial_pass_rate = uat_initial_pass_rate;
	}
	public String getRetest_initial_pass_rate() {
		return retest_initial_pass_rate;
	}
	public void setRetest_initial_pass_rate(String retest_initial_pass_rate) {
		this.retest_initial_pass_rate = retest_initial_pass_rate;
	}
	public String getDefect_score() {
		return defect_score;
	}
	public void setDefect_score(String defect_score) {
		this.defect_score = defect_score;
	}
	public String getDefect_aging_score() {
		return defect_aging_score;
	}
	public void setDefect_aging_score(String defect_aging_score) {
		this.defect_aging_score = defect_aging_score;
	}
	public String getCode_slip_score() {
		return code_slip_score;
	}
	public void setCode_slip_score(String code_slip_score) {
		this.code_slip_score = code_slip_score;
	}

}
