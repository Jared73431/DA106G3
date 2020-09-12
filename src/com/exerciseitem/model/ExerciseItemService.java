package com.exerciseitem.model;

import java.util.List;

public class ExerciseItemService {
	
	private ExerciseItemDAO_interface dao;
	
	public ExerciseItemService() {
		dao = new ExerciseItemDAO();
	}
	
	public List<ExerciseItemVO> getAll(){
		return dao.getAll();
	}
	
	public ExerciseItemVO addExerciseItem(String exe_item, Double exe_cal) {
		ExerciseItemVO exerciseItemVO = new ExerciseItemVO();
		exerciseItemVO.setExe_item(exe_item);
		exerciseItemVO.setExe_cal(exe_cal);
		dao.insert(exerciseItemVO);
		
		return exerciseItemVO;
	}
	
	public ExerciseItemVO updateExerciseItem(String exe_item, Double exe_cal, String exe_no) {
		ExerciseItemVO exerciseItemVO = new ExerciseItemVO();
		exerciseItemVO.setExe_item(exe_item);
		exerciseItemVO.setExe_cal(exe_cal);
		exerciseItemVO.setExe_no(exe_no);
		dao.update(exerciseItemVO);
		
		return exerciseItemVO;
	}
	public void deleteExercise(String exe_no) {
		dao.delete(exe_no);
	}

	public ExerciseItemVO getOneExe(String exe_no) {
		return dao.findByPrimaryKey(exe_no);
	}


	
}
