package com.newsknowledge.model;

import java.sql.*;

public class NewsKnowledgeVO implements java.io.Serializable {
	private String news_no;
	private String cate_no;
	private String news_title;
	private String news_author;
	private byte[] news_cover;
	private String news_content;
	private Timestamp news_date;
	private Integer news_status;

	public NewsKnowledgeVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getNews_no() {
		return news_no;
	}

	public void setNews_no(String news_no) {
		this.news_no = news_no;
	}

	public String getCate_no() {
		return cate_no;
	}

	public void setCate_no(String cate_no) {
		this.cate_no = cate_no;
	}

	public String getNews_title() {
		return news_title;
	}

	public void setNews_title(String news_title) {
		this.news_title = news_title;
	}

	public String getNews_author() {
		return news_author;
	}

	public void setNews_author(String news_author) {
		this.news_author = news_author;
	}

	public byte[] getNews_cover() {
		return news_cover;
	}

	public void setNews_cover(byte[] news_cover) {
		this.news_cover = news_cover;
	}

	public String getNews_content() {
		return news_content;
	}

	public void setNews_content(String news_content) {
		this.news_content = news_content;
	}

	public Timestamp getNews_date() {
		return news_date;
	}

	public void setNews_date(Timestamp news_date) {
		this.news_date = news_date;
	}

	public Integer getNews_status() {
		return news_status;
	}

	public void setNews_status(Integer news_status) {
		this.news_status = news_status;
	}

}
