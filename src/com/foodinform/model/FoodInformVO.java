package com.foodinform.model;

import java.sql.*;

public class FoodInformVO implements java.io.Serializable {
	private String fi_no;
	private String cate_no;
	private String fi_title;
	private String fi_author;
	private byte[] fi_cover;
	private String fi_content;
	private Timestamp fi_date;
	private Integer fi_status;

	public FoodInformVO() {
		super();
	}

	public String getFi_no() {
		return fi_no;
	}

	public void setFi_no(String fi_no) {
		this.fi_no = fi_no;
	}

	public String getCate_no() {
		return cate_no;
	}

	public void setCate_no(String cate_no) {
		this.cate_no = cate_no;
	}

	public String getFi_title() {
		return fi_title;
	}

	public void setFi_title(String fi_title) {
		this.fi_title = fi_title;
	}

	public String getFi_author() {
		return fi_author;
	}

	public void setFi_author(String fi_author) {
		this.fi_author = fi_author;
	}

	public byte[] getFi_cover() {
		return fi_cover;
	}

	public void setFi_cover(byte[] fi_cover) {
		this.fi_cover = fi_cover;
	}

	public String getFi_content() {
		return fi_content;
	}

	public void setFi_content(String fi_content) {
		this.fi_content = fi_content;
	}

	public Timestamp getFi_date() {
		return fi_date;
	}

	public void setFi_date(Timestamp fi_date) {
		this.fi_date = fi_date;
	}

	public Integer getFi_status() {
		return fi_status;
	}

	public void setFi_status(Integer fi_status) {
		this.fi_status = fi_status;
	}

}
