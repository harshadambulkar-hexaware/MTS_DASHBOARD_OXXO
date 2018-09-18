package com.hexaware.insight360DataPull.dao;

import java.util.List;

import com.hexaware.insight360DataPull.model.Test;

public interface TestDAO {
	public List<Test> testList();
	public List<Test> getTestListForRelease(int ReleaseID);
	public List<Test> getTestListForRequirement(int ReleaseID, int RequirementID);
	public Test getTest(int TestID);
	public boolean createTest(Test test,String Phase);
    public boolean deleteTest(int testID);
    public boolean updateTest(Test test, String Phase);
}