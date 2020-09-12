package com.course.model;

import java.sql.*;

public class CourseVO implements java.io.Serializable{
	private String cour_no;
	private String cour_type_no;
	private String coa_no;
	private String cour_name;
	private String cour_addr;
	private Integer cour_mode;
	private Timestamp cour_date;
	private Integer cour_status;
	private Timestamp cour_update;
	private Timestamp cour_deadline;
	public CourseVO() {
		super();
	}
	public String getCour_no() {
		return cour_no;
	}
	public void setCour_no(String cour_no) {
		this.cour_no = cour_no;
	}
	public String getCour_type_no() {
		return cour_type_no;
	}
	public void setCour_type_no(String cour_type_no) {
		this.cour_type_no = cour_type_no;
	}
	public String getCoa_no() {
		return coa_no;
	}
	public void setCoa_no(String coa_no) {
		this.coa_no = coa_no;
	}
	public String getCour_name() {
		return cour_name;
	}
	public void setCour_name(String cour_name) {
		this.cour_name = cour_name;
	}
	public String getCour_addr() {
		return cour_addr;
	}
	public void setCour_addr(String cour_addr) {
		this.cour_addr = cour_addr;
	}
	public Integer getCour_mode() {
		return cour_mode;
	}
	public void setCour_mode(Integer cour_mode) {
		this.cour_mode = cour_mode;
	}
	public Timestamp getCour_date() {
		return cour_date;
	}
	public void setCour_date(Timestamp cour_date) {
		this.cour_date = cour_date;
	}
	public Integer getCour_status() {
		return cour_status;
	}
	public void setCour_status(Integer cour_status) {
		this.cour_status = cour_status;
	}
	public Timestamp getCour_update() {
		return cour_update;
	}
	public void setCour_update(Timestamp cour_update) {
		this.cour_update = cour_update;
	}
	public Timestamp getCour_deadline() {
		return cour_deadline;
	}
	public void setCour_deadline(Timestamp cour_deadline) {
		this.cour_deadline = cour_deadline;
	}
	
}
