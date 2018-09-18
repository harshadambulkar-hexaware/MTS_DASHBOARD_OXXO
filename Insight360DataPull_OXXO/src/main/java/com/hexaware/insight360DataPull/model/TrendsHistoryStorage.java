package com.hexaware.insight360DataPull.model;

public class TrendsHistoryStorage {
	
	private int trendsHistoryStorageEntityId;
	private int subBusinesslineId;
	private int projectId;
	private String projectName;
	private int releaseId;
	private String releaseName;
	private String phase;
	private String module;
	private int defectCountByModule;
	private String rootCause;
	private int defectCountByRootCause;
	private String dataInsertedDate;
	private int passedTestCases;
	private int failedTestCases;
	private int passedReTestCases;
	private float ipr;
	private float defectScore;
	private float failureScore;
	private float codeDeliverySlipScore;
	private float testDesignProductivity;
	private float testExecutionProductivity;
	private float testDefectProductivity;
	
	public int getPassedTestCases() {
		return passedTestCases;
	}
	public void setPassedTestCases(int passedTestCases) {
		this.passedTestCases = passedTestCases;
	}
	public int getFailedTestCases() {
		return failedTestCases;
	}
	public void setFailedTestCases(int failedTestCases) {
		this.failedTestCases = failedTestCases;
	}
	public int getPassedReTestCases() {
		return passedReTestCases;
	}
	public void setPassedReTestCases(int passedReTestCases) {
		this.passedReTestCases = passedReTestCases;
	}
	
	public String getDataInsertedDate() {
		return dataInsertedDate;
	}
	public void setDataInsertedDate(String currentDate) {
		this.dataInsertedDate = currentDate;
	}
	public int getTrendsHistoryStorageEntityId() {
		return trendsHistoryStorageEntityId;
	}
	public void setTrendsHistoryStorageEntityId(int trendsHistoryStorageEntityId) {
		this.trendsHistoryStorageEntityId = trendsHistoryStorageEntityId;
	}
	public int getSubBusinesslineId() {
		return subBusinesslineId;
	}
	public void setSubBusinesslineId(int subBusinesslineId) {
		this.subBusinesslineId = subBusinesslineId;
	}
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public int getReleaseId() {
		return releaseId;
	}
	public void setReleaseId(int releaseId) {
		this.releaseId = releaseId;
	}
	public String getReleaseName() {
		return releaseName;
	}
	public void setReleaseName(String releaseName) {
		this.releaseName = releaseName;
	}
	public String getPhase() {
		return phase;
	}
	public void setPhase(String phase) {
		this.phase = phase;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public int getDefectCountByModule() {
		return defectCountByModule;
	}
	public void setDefectCountByModule(int defectCountByModule) {
		this.defectCountByModule = defectCountByModule;
	}
	public String getRootCause() {
		return rootCause;
	}
	public void setRootCause(String rootCause) {
		this.rootCause = rootCause;
	}
	public int getDefectCountByRootCause() {
		return defectCountByRootCause;
	}
	public void setDefectCountByRootCause(int defectCountByRootCause) {
		this.defectCountByRootCause = defectCountByRootCause;
	}
	public float getIpr() {
		return ipr;
	}
	public void setIpr(float ipr) {
		this.ipr = ipr;
	}
	public float getDefectScore() {
		return defectScore;
	}
	public void setDefectScore(float defectScore) {
		this.defectScore = defectScore;
	}
	public float getFailureScore() {
		return failureScore;
	}
	public void setFailureScore(float failureScore) {
		this.failureScore = failureScore;
	}
	public float getCodeDeliverySlipScore() {
		return codeDeliverySlipScore;
	}
	public void setCodeDeliverySlipScore(float codeDeliverySlipScore) {
		this.codeDeliverySlipScore = codeDeliverySlipScore;
	}
	public float getTestDesignProductivity() {
		return testDesignProductivity;
	}
	public void setTestDesignProductivity(float testDesignProductivity) {
		this.testDesignProductivity = testDesignProductivity;
	}
	public float getTestExecutionProductivity() {
		return testExecutionProductivity;
	}
	public void setTestExecutionProductivity(float testExecutionProductivity) {
		this.testExecutionProductivity = testExecutionProductivity;
	}
	public float getTestDefectProductivity() {
		return testDefectProductivity;
	}
	public void setTestDefectProductivity(float testDefectProductivity) {
		this.testDefectProductivity = testDefectProductivity;
	}
	
	

}
