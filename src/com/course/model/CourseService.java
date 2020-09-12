package com.course.model;

import java.sql.Timestamp;
import java.util.List;

public class CourseService {
	private CourseDAO_interface dao;
	public CourseService() {
		dao = new CourseDAO();
	}
	
	public CourseVO addCourse(String cour_type_no,String coa_no,String cour_name,String cour_addr,Integer cour_mode,Timestamp cour_date,Timestamp cour_update,Timestamp cour_deadline) {
		
		CourseVO courseVO = new CourseVO();
		
		courseVO.setCour_type_no(cour_type_no);
		courseVO.setCoa_no(coa_no);
		courseVO.setCour_name(cour_name);
		courseVO.setCour_addr(cour_addr);
		courseVO.setCour_mode(cour_mode);
		courseVO.setCour_date(cour_date);
		courseVO.setCour_update(cour_update);
		courseVO.setCour_deadline(cour_deadline);
		
		dao.insert(courseVO);
		
		return courseVO;
	}
	
	public CourseVO updateCourse(String cour_type_no,String coa_no,String cour_name,String cour_addr,Integer cour_mode,Timestamp cour_date,Integer cour_status,Timestamp cour_update,Timestamp cour_deadline,String cour_no) {
		CourseVO courseVO = new CourseVO();
		
		courseVO.setCour_type_no(cour_type_no);
		courseVO.setCoa_no(coa_no);
		courseVO.setCour_name(cour_name);
		courseVO.setCour_addr(cour_addr);
		courseVO.setCour_mode(cour_mode);
		courseVO.setCour_date(cour_date);
		courseVO.setCour_status(cour_status);
		courseVO.setCour_update(cour_update);
		courseVO.setCour_deadline(cour_deadline);
		courseVO.setCour_no(cour_no);
		
		dao.update(courseVO);
		
		return courseVO;
	}
	
	public void updateStatus(String cour_no,Integer cour_status) {
		dao.updateForStatus(cour_no, cour_status);
	}
	
	public void delete(String cour_no) {
		dao.delete(cour_no);
	}
	
	public CourseVO getOneCourse(String cour_no) {
		return dao.findByPrimaryKey(cour_no);
	}
	public List<CourseVO> getCoachList(String coa_no){
		return dao.findCoachCourse(coa_no);
	}
	public List<CourseVO> getAll(){
		return dao.getAll();
	}
	public String updateComf(String cour_no,Integer coa_comf,Integer trainee_comf) {
		return dao.updateForComf(cour_no,coa_comf,trainee_comf);
	}
}
