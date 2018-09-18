package com.hexaware.insight360DataPull.dao;

import java.util.List;

import com.hexaware.insight360DataPull.model.BusinessLines;

public interface BusinessLinesDAO {

	public List<BusinessLines> BusinessLinesList();
	public Integer createBusinessLines(BusinessLines businessLines);
	public boolean updateBusinessLines(BusinessLines businessLines);
	public boolean deleteBusinessLines(int businessLinesId);
}
