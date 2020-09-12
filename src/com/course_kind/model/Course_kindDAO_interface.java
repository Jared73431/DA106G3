package com.course_kind.model;

import java.util.*;

public interface Course_kindDAO_interface {
	public void insert(Course_kindVO course_kindVO);
	public void update(Course_kindVO course_kindVO);
	public void delete(String cour_type_no);
	public Course_kindVO findByPrimaryKey(String cour_type_no);
	public List<Course_kindVO> getAll();
}
