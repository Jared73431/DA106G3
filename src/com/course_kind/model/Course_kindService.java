package com.course_kind.model;

import java.util.List;

public class Course_kindService {
	private Course_kindDAO_interface dao;

	public Course_kindService() {
		dao = new Course_kindDAO();
	}

	public Course_kindVO addCourse_kind(String cour_type) {
		Course_kindVO course_kindVO = new Course_kindVO();

		course_kindVO.setCour_type(cour_type);

		dao.insert(course_kindVO);
		return course_kindVO;
	}

	public Course_kindVO updateCourse_kind(String cour_type_no, String cour_type) {
		Course_kindVO course_kindVO = new Course_kindVO();
		course_kindVO.setCour_type_no(cour_type_no);
		course_kindVO.setCour_type(cour_type);

		dao.update(course_kindVO);
		return course_kindVO;
	}

	public void delete(String cour_type_no) {
		dao.delete(cour_type_no);
	}

	public Course_kindVO getCourse_kind(String cour_type_no) {
		return dao.findByPrimaryKey(cour_type_no);
	}

	public List<Course_kindVO> getAll() {
		return dao.getAll();
	}
}
