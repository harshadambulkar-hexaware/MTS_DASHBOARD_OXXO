package com.hexaware.insight360DataPull.dao;

import java.util.List;

import com.hexaware.insight360DataPull.model.SubBusinessLines;

public interface SubBusinessLinesDAO {

	public List<SubBusinessLines> SubBusinessLinesList();
	public Integer createSubBusinessLines(SubBusinessLines subBusinessLines);
	public boolean updateSubBusinessLines(SubBusinessLines subBusinessLines);
	public boolean deleteSubBusinessLines(int subBusinessLinesId);
}
