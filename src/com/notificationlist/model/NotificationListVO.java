package com.notificationlist.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class NotificationListVO implements Serializable {
	private String note_no;
	private String member_no;
	private String category_no;
	private String note_content;
	private Integer note_status;
	private Date note_date;

	public NotificationListVO() {
		super();
	}

	public NotificationListVO(String note_no, String member_no, String category_no, String note_content,
			Integer note_status, Date note_date) {
		super();
		this.note_no = note_no;
		this.member_no = member_no;
		this.category_no = category_no;
		this.note_content = note_content;
		this.note_status = note_status;
		this.note_date = note_date;
	}

	public String getNote_no() {
		return note_no;
	}

	public void setNote_no(String note_no) {
		this.note_no = note_no;
	}

	public String getMember_no() {
		return member_no;
	}

	public void setMember_no(String member_no) {
		this.member_no = member_no;
	}

	public String getCategory_no() {
		return category_no;
	}

	public void setCategory_no(String category_no) {
		this.category_no = category_no;
	}

	public String getNote_content() {
		return note_content;
	}

	public void setNote_content(String note_content) {
		this.note_content = note_content;
	}

	public Integer getNote_status() {
		return note_status;
	}

	public void setNote_status(Integer note_status) {
		this.note_status = note_status;
	}

	public Date getNote_date() {
		return note_date;
	}

	public void setNote_date(Date note_date) {
		this.note_date = note_date;
	}

}
