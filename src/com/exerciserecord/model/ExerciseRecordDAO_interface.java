package com.exerciserecord.model;

import java.util.*;

public interface ExerciseRecordDAO_interface {
		public void insert(ExerciseRecordVO exerciseRecordVO);

		public void update(ExerciseRecordVO exerciseRecordVO);

		public void delete(String exerec_no);

		public ExerciseRecordVO findByPrimaryKey(String exerec_no);

		// public List<ExerciseRecordVO> getAll(Map<String, String[]> map);
		public List<ExerciseRecordVO> getAll();
		
		public List findByDate(String member_no, java.sql.Date exe_date);
	
}
