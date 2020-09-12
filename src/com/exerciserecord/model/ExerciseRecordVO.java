package com.exerciserecord.model;
import java.sql.*;

public class ExerciseRecordVO implements java.io.Serializable{
	private String exerec_no;
	private String member_no;
	private String exe_no;
	private Date exe_date;
	private double exe_time;
	private Double exe_tcal;
	
	public ExerciseRecordVO() {
		super();
	}

	@Override
	public String toString() {
		return "ExerciseRecordVO [exerec_no=" + exerec_no + ", member_no=" + member_no + ", exe_no=" + exe_no
				+ ", exe_date=" + exe_date + ", exe_time=" + exe_time + ", exe_tcal=" + exe_tcal + "]";
	}

	public ExerciseRecordVO(String exerec_no, String member_no, String exe_no, Date exe_date, double exe_time,
			Double exe_tcal) {
		super();
		this.exerec_no = exerec_no;
		this.member_no = member_no;
		this.exe_no = exe_no;
		this.exe_date = exe_date;
		this.exe_time = exe_time;
		this.exe_tcal = exe_tcal;
	}

	public String getExerec_no() {
		return exerec_no;
	}

	public void setExerec_no(String exerec_no) {
		this.exerec_no = exerec_no;
	}

	public String getMember_no() {
		return member_no;
	}

	public void setMember_no(String member_no) {
		this.member_no = member_no;
	}

	public String getExe_no() {
		return exe_no;
	}

	public void setExe_no(String exe_no) {
		this.exe_no = exe_no;
	}

	public Date getExe_date() {
		return exe_date;
	}

	public void setExe_date(Date exe_date) {
		this.exe_date = exe_date;
	}

	public double getExe_time() {
		return exe_time;
	}

	public void setExe_time(double exe_time) {
		this.exe_time = exe_time;
	}

	public Double getExe_tcal() {
		return exe_tcal;
	}

	public void setExe_tcal(Double exe_tcal) {
		this.exe_tcal = exe_tcal;
	}
	
	

}
