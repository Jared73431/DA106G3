package com.gym.model;

public class GymVO implements java.io.Serializable {
	private String gym_no;
	private String cate_no;
	private String gym_name;
	private String gym_address;
	private Double gym_lon;
	private Double gym_lat;
	private String gym_site;

	public GymVO() {
		super();
	}

	public String getGym_no() {
		return gym_no;
	}

	public void setGym_no(String gym_no) {
		this.gym_no = gym_no;
	}

	public String getCate_no() {
		return cate_no;
	}

	public void setCate_no(String cate_no) {
		this.cate_no = cate_no;
	}

	public String getGym_name() {
		return gym_name;
	}

	public void setGym_name(String gym_name) {
		this.gym_name = gym_name;
	}

	public String getGym_address() {
		return gym_address;
	}

	public void setGym_address(String gym_address) {
		this.gym_address = gym_address;
	}

	public Double getGym_lon() {
		return gym_lon;
	}

	public void setGym_lon(Double gym_lon) {
		this.gym_lon = gym_lon;
	}

	public Double getGym_lat() {
		return gym_lat;
	}

	public void setGym_lat(Double gym_lat) {
		this.gym_lat = gym_lat;
	}

	public String getGym_site() {
		return gym_site;
	}

	public void setGym_site(String gym_site) {
		this.gym_site = gym_site;
	}

}
