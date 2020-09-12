package com.exerciseitem.model;

import java.util.List;


public interface ExerciseItemDAO_interface {
	public void insert(ExerciseItemVO exerciseItemVO);
    public void update(ExerciseItemVO exerciseItemVO);
    public void delete(String groupgo_id);
    public ExerciseItemVO findByPrimaryKey(String exe_no);
    public List<ExerciseItemVO> getAll();
}
