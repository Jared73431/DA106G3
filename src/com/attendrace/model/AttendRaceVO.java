package com.attendrace.model;
import java.sql.*;
import java.util.Arrays;

public class AttendRaceVO implements java.io.Serializable{

	private String attend_no;
	private String member_no;
	private Integer race_type;
	private String race_name;
	private Date fin_date;
	private String fin_time;
	private Integer fin_rank;
	private byte[] fin_record;
	private String fin_remark;
	public AttendRaceVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AttendRaceVO(String attend_no, String member_no, Integer race_type, String race_name, Date fin_date,
			String fin_time, Integer fin_rank, byte[] fin_record, String fin_remark) {
		super();
		this.attend_no = attend_no;
		this.member_no = member_no;
		this.race_type = race_type;
		this.race_name = race_name;
		this.fin_date = fin_date;
		this.fin_time = fin_time;
		this.fin_rank = fin_rank;
		this.fin_record = fin_record;
		this.fin_remark = fin_remark;
	}
	@Override
	public String toString() {
		return "AttendRaceVO [attend_no=" + attend_no + ", member_no=" + member_no + ", race_type=" + race_type
				+ ", race_name=" + race_name + ", fin_date=" + fin_date + ", fin_time=" + fin_time + ", fin_rank="
				+ fin_rank + ", fin_record=" + Arrays.toString(fin_record) + ", fin_remark=" + fin_remark + "]";
	}
	public String getAttend_no() {
		return attend_no;
	}
	public void setAttend_no(String attend_no) {
		this.attend_no = attend_no;
	}
	public String getMember_no() {
		return member_no;
	}
	public void setMember_no(String member_no) {
		this.member_no = member_no;
	}
	public Integer getRace_type() {
		return race_type;
	}
	public void setRace_type(Integer race_type) {
		this.race_type = race_type;
	}
	public String getRace_name() {
		return race_name;
	}
	public void setRace_name(String race_name) {
		this.race_name = race_name;
	}
	public Date getFin_date() {
		return fin_date;
	}
	public void setFin_date(Date fin_date) {
		this.fin_date = fin_date;
	}
	public String getFin_time() {
		return fin_time;
	}
	public void setFin_time(String fin_time) {
		this.fin_time = fin_time;
	}
	public Integer getFin_rank() {
		return fin_rank;
	}
	public void setFin_rank(Integer fin_rank) {
		this.fin_rank = fin_rank;
	}
	public byte[] getFin_record() {
		return fin_record;
	}
	public void setFin_record(byte[] fin_record) {
		this.fin_record = fin_record;
	}
	public String getFin_remark() {
		return fin_remark;
	}
	public void setFin_remark(String fin_remark) {
		this.fin_remark = fin_remark;
	}
	
}