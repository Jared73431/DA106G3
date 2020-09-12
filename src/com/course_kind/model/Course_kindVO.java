package com.course_kind.model;

public class Course_kindVO implements java.io.Serializable{
	private String cour_type_no;
	private String cour_type;
	public Course_kindVO() {
		super();
	}
	public String getCour_type_no() {
		return cour_type_no;
	}
	public void setCour_type_no(String cour_type_no) {
		this.cour_type_no = cour_type_no;
	}
	public String getCour_type() {
		return cour_type;
	}
	public void setCour_type(String cour_type) {
		this.cour_type = cour_type;
	}
	
}
