package com.groupreport.model;
import java.sql.*;

public class GroupReportVO implements java.io.Serializable{
	private String groupgo_id;
	private String member_no;
	private Timestamp report_date;
	private String report_content;
	private Integer report_status;
	
	public GroupReportVO() {
		super();
	}
	

	public GroupReportVO(String groupgo_id, String member_no, Timestamp report_date, String report_content,
			Integer report_status) {
		super();
		this.groupgo_id = groupgo_id;
		this.member_no = member_no;
		this.report_date = report_date;
		this.report_content = report_content;
		this.report_status = report_status;
	}




	public String getGroupgo_id() {
		return groupgo_id;
	}




	public void setGroupgo_id(String groupgo_id) {
		this.groupgo_id = groupgo_id;
	}




	public String getMember_no() {
		return member_no;
	}




	public void setMember_no(String member_no) {
		this.member_no = member_no;
	}




	public Timestamp getReport_date() {
		return report_date;
	}




	public void setReport_date(Timestamp report_date) {
		this.report_date = report_date;
	}




	public String getReport_content() {
		return report_content;
	}




	public void setReport_content(String report_content) {
		this.report_content = report_content;
	}




	public Integer getReport_status() {
		return report_status;
	}




	public void setReport_status(Integer report_status) {
		this.report_status = report_status;
	}


	
}
