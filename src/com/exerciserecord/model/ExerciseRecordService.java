package com.exerciserecord.model;

import java.sql.Date;
import java.util.List;

public class ExerciseRecordService {
	private ExerciseRecordDAO_interface dao;
	
	public ExerciseRecordService(){
		dao = new ExerciseRecordDAO();
	}
	
	public List<ExerciseRecordVO> getAll(){
		return dao.getAll();
	}
	
	public ExerciseRecordVO addExerciseRecord(String member_no, String exe_no, Date exe_date, double exe_time,
			Double exe_tcal) {
		ExerciseRecordVO exerciseRecordVO = new ExerciseRecordVO();
		exerciseRecordVO.setMember_no(member_no);
		exerciseRecordVO.setExe_no(exe_no);
		exerciseRecordVO.setExe_date(exe_date);
		exerciseRecordVO.setExe_time(exe_time);
		exerciseRecordVO.setExe_tcal(exe_tcal);
		dao.insert(exerciseRecordVO);
		return exerciseRecordVO;
	}
	
	public ExerciseRecordVO updateExerciseRecord(String exerec_no, String member_no, String exe_no, Date exe_date, double exe_time,
			Double exe_tcal) {
		ExerciseRecordVO exerciseRecordVO = new ExerciseRecordVO();
		exerciseRecordVO.setExerec_no(exerec_no);
		exerciseRecordVO.setMember_no(member_no);
		exerciseRecordVO.setExe_no(exe_no);
		exerciseRecordVO.setExe_date(exe_date);
		exerciseRecordVO.setExe_time(exe_time);
		exerciseRecordVO.setExe_tcal(exe_tcal);
		dao.update(exerciseRecordVO);
		return exerciseRecordVO;
	}
	
	public void deleteExerciseRecord(String exerec_no) {
		dao.delete(exerec_no);
	}
	public ExerciseRecordVO getOneExerciseRecord(String exerec_no) {
		return dao.findByPrimaryKey(exerec_no);
	}
	
	public List findByDate(String member_no, java.sql.Date exe_date) {
		return dao.findByDate(member_no, exe_date);
	}
}
