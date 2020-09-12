package com.exerciseitem.model;

public class ExerciseItemVO implements java.io.Serializable{
	private String exe_no;
	private String exe_item;
	private Double exe_cal;
	
	public ExerciseItemVO() {
		super();
	}

	public ExerciseItemVO(String exe_no, String exe_item, Double exe_cal) {
		super();
		this.exe_no = exe_no;
		this.exe_item = exe_item;
		this.exe_cal = exe_cal;
	}

	public String getExe_no() {
		return exe_no;
	}

	public void setExe_no(String exe_no) {
		this.exe_no = exe_no;
	}

	public String getExe_item() {
		return exe_item;
	}

	public void setExe_item(String exe_item) {
		this.exe_item = exe_item;
	}

	public Double getExe_cal() {
		return exe_cal;
	}

	public void setExe_cal(Double exe_cal) {
		this.exe_cal = exe_cal;
	}
	
	
	
}
