package com.posture.model;
import java.sql.*;

public class PostureVO implements java.io.Serializable{
	private String posture_no;
	private String member_no;
	private Date record_date;
	private Double weight;
	private Double bodyfat;
	private Integer bmr;
	private Double bmi;
	private Double remain_cal;

	public PostureVO() {
		super();
	}

	public PostureVO(String posture_no, String member_no, Date record_date, Double weight, Double bodyfat,
			Integer bmr, Double bmi, Double remain_cal) {
		super();
		this.posture_no = posture_no;
		this.member_no = member_no;
		this.record_date = record_date;
		this.weight = weight;
		this.bodyfat = bodyfat;
		this.bmr = bmr;
		this.bmi = bmi;
		this.remain_cal = remain_cal;
	}

	@Override
	public String toString() {
		return "PostureVO [posture_no=" + posture_no + ", member_no=" + member_no + ", record_date=" + record_date
				+ ", weight=" + weight + ", bodyfat=" + bodyfat + ", bmr=" + bmr + ", bmi=" + bmi + ", remain_cal="
				+ remain_cal + "]";
	}

	public String getPosture_no() {
		return posture_no;
	}

	public void setPosture_no(String posture_no) {
		this.posture_no = posture_no;
	}

	public String getMember_no() {
		return member_no;
	}

	public void setMember_no(String member_no) {
		this.member_no = member_no;
	}

	public Date getRecord_date() {
		return record_date;
	}

	public void setRecord_date(Date record_date) {
		this.record_date = record_date;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Double getBodyfat() {
		return bodyfat;
	}

	public void setBodyfat(Double bodyfat) {
		this.bodyfat = bodyfat;
	}

	public Integer getBmr() {
		return bmr;
	}

	public void setBmr(Integer bmr) {
		this.bmr = bmr;
	}

	public Double getBmi() {
		return bmi;
	}

	public void setBmi(Double bmi) {
		this.bmi = bmi;
	}

	public Double getRemain_cal() {
		return remain_cal;
	}

	public void setRemain_cal(Double remain_cal) {
		this.remain_cal = remain_cal;
	}
	

}
