package com.groupgo.model;
import java.sql.*;
import java.util.Arrays;

public class GroupgoVO implements java.io.Serializable{
	private String groupgo_id;
	private String master_id;
	private String group_name;
	private Timestamp group_date;
	private String place;
	private Double lon;
	private Double lat;
	private Integer group_status;
	private Integer people_num;
	private Integer current_peo;
	private String budget;
	private String group_content;
	private byte[] photo;
	private Timestamp deadline;
	private Integer score_sum;
	
	
	public GroupgoVO() {
		super();
		}


	public GroupgoVO(String groupgo_id, String master_id, String group_name, Timestamp group_date, String place,
			Double lon, Double lat, Integer group_status, Integer people_num, Integer current_peo, String budget,
			String group_content, byte[] photo, Timestamp deadline, Integer score_sum) {
		super();
		this.groupgo_id = groupgo_id;
		this.master_id = master_id;
		this.group_name = group_name;
		this.group_date = group_date;
		this.place = place;
		this.lon = lon;
		this.lat = lat;
		this.group_status = group_status;
		this.people_num = people_num;
		this.current_peo = current_peo;
		this.budget = budget;
		this.group_content = group_content;
		this.photo = photo;
		this.deadline = deadline;
		this.score_sum = score_sum;
	}

//	public GroupgoVO(String master_id, String group_name, Timestamp group_date, String place,
//			Double lon, Double lat, Integer people_num, Integer current_peo, String budget,
//			String group_content, Timestamp deadline) {
//		super();
//		this.master_id = master_id;
//		this.group_name = group_name;
//		this.group_date = group_date;
//		this.place = place;
//		this.lon = lon;
//		this.lat = lat;
//		this.people_num = people_num;
//		this.current_peo = current_peo;
//		this.budget = budget;
//		this.group_content = group_content;
//		
//		this.deadline = deadline;
//	}

	public String getGroupgo_id() {
		return groupgo_id;
	}


	public void setGroupgo_id(String groupgo_id) {
		this.groupgo_id = groupgo_id;
	}


	public String getMaster_id() {
		return master_id;
	}


	public void setMaster_id(String master_id) {
		this.master_id = master_id;
	}


	public String getGroup_name() {
		return group_name;
	}


	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}


	public Timestamp getGroup_date() {
		return group_date;
	}


	public void setGroup_date(Timestamp group_date) {
		this.group_date = group_date;
	}


	public String getPlace() {
		return place;
	}


	public void setPlace(String place) {
		this.place = place;
	}


	public Double getLon() {
		return lon;
	}


	public void setLon(Double lon) {
		this.lon = lon;
	}


	public Double getLat() {
		return lat;
	}


	public void setLat(Double lat) {
		this.lat = lat;
	}


	public Integer getGroup_status() {
		return group_status;
	}


	public void setGroup_status(Integer group_status) {
		this.group_status = group_status;
	}


	public Integer getPeople_num() {
		return people_num;
	}


	public void setPeople_num(Integer people_num) {
		this.people_num = people_num;
	}


	public Integer getCurrent_peo() {
		return current_peo;
	}


	public void setCurrent_peo(Integer current_peo) {
		this.current_peo = current_peo;
	}


	public String getBudget() {
		return budget;
	}


	public void setBudget(String budget) {
		this.budget = budget;
	}


	public String getGroup_content() {
		return group_content;
	}


	public void setGroup_content(String group_content) {
		this.group_content = group_content;
	}


	public byte[] getPhoto() {
		return photo;
	}


	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}


	public Timestamp getDeadline() {
		return deadline;
	}


	public void setDeadline(Timestamp deadline) {
		this.deadline = deadline;
	}


	public Integer getScore_sum() {
		return score_sum;
	}


	public void setScore_sum(Integer score_sum) {
		this.score_sum = score_sum;
	}


	@Override
	public String toString() {
		return "GroupgoVO [groupgo_id=" + groupgo_id + ", master_id=" + master_id + ", group_name=" + group_name
				+ ", group_date=" + group_date + ", place=" + place + ", lon=" + lon + ", lat=" + lat
				+ ", group_status=" + group_status + ", people_num=" + people_num + ", current_peo=" + current_peo
				+ ", budget=" + budget + ", group_content=" + group_content 
				+ ", deadline=" + deadline + ", score_sum=" + score_sum + "]";
	}
	

}
