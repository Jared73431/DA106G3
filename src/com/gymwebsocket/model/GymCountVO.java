package com.gymwebsocket.model;

import org.json.JSONArray;

public class GymCountVO implements java.io.Serializable {
	private String gym_no;
	private String gym_url;
	private String gym_data;

	public GymCountVO() {
		super();
	}
		
	public GymCountVO(String gym_no, String gym_url) {
		super();
		this.gym_no = gym_no;
		this.gym_url = gym_url;
	}

	public String getGym_no() {
		return gym_no;
	}

	public void setGym_no(String gym_no) {
		this.gym_no = gym_no;
	}

	public String getGym_url() {
		return gym_url;
	}

	public void setGym_url(String gym_url) {
		this.gym_url = gym_url;
	}

	public String getGym_data() {
		return gym_data;
	}

	public void setGym_data(String gym_data) {
		this.gym_data = gym_data;
	}

	
}
