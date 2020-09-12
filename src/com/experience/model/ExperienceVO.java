package com.experience.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class ExperienceVO implements Serializable {
	private String exper_no;
	private String member_no;
	private Date exper_date;
	private String cate_no;
	private String exper_context;
	private String exper_title;
	private byte[] picture;
	public ExperienceVO(String exper_no, String member_no, Date exper_date, String cate_no, String exper_context,
			String exper_title, byte[] picture) {
		super();
		this.exper_no = exper_no;
		this.member_no = member_no;
		this.exper_date = exper_date;
		this.cate_no = cate_no;
		this.exper_context = exper_context;
		this.exper_title = exper_title;
		this.picture = picture;
	}
	public ExperienceVO() {
		super();
	}
	public String getExper_no() {
		return exper_no;
	}
	public void setExper_no(String exper_no) {
		this.exper_no = exper_no;
	}
	public String getMember_no() {
		return member_no;
	}
	public void setMember_no(String member_no) {
		this.member_no = member_no;
	}
	public Date getExper_date() {
		return exper_date;
	}
	public void setExper_date(Date exper_date) {
		this.exper_date = exper_date;
	}
	public String getCate_no() {
		return cate_no;
	}
	public void setCate_no(String cate_no) {
		this.cate_no = cate_no;
	}
	public String getExper_context() {
		return exper_context;
	}
	public void setExper_context(String exper_context) {
		this.exper_context = exper_context;
	}
	public String getExper_title() {
		return exper_title;
	}
	public void setExper_title(String exper_title) {
		this.exper_title = exper_title;
	}
	public byte[] getPicture() {
		return picture;
	}
	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

}
