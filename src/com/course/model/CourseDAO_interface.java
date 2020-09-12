package com.course.model;

import java.util.*;

public interface CourseDAO_interface {
	public void insert(CourseVO courseVO);
	public void update(CourseVO courseVO);
	public void updateForStatus(String cour_no,Integer cour_status);
	public String updateForComf(String cour_no,Integer coa_comf,Integer trainee_comf);
	public void delete(String cour_no);
	public List<CourseVO> findCoachCourse(String coa_no);
	public CourseVO findByPrimaryKey(String cour_no);
	public List<CourseVO> getAll();
	
	
}
