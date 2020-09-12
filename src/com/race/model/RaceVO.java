package com.race.model;

public class RaceVO implements java.io.Serializable {

	private String race_no;
	private String cate_no;
	private String race_name;
	private String race_time;
	private String race_content;
	private String race_organizer;
	private String race_location;
	private String race_deadline;
	private Integer race_status;

	public RaceVO() {
		super();
	}

	public String getRace_no() {
		return race_no;
	}

	public void setRace_no(String race_no) {
		this.race_no = race_no;
	}

	public String getCate_no() {
		return cate_no;
	}

	public void setCate_no(String cate_no) {
		this.cate_no = cate_no;
	}

	public String getRace_name() {
		return race_name;
	}

	public void setRace_name(String race_name) {
		this.race_name = race_name;
	}

	public String getRace_time() {
		return race_time;
	}

	public void setRace_time(String race_time) {
		this.race_time = race_time;
	}

	public String getRace_content() {
		return race_content;
	}

	public void setRace_content(String race_content) {
		this.race_content = race_content;
	}

	public String getRace_organizer() {
		return race_organizer;
	}

	public void setRace_organizer(String race_organizer) {
		this.race_organizer = race_organizer;
	}

	public String getRace_location() {
		return race_location;
	}

	public void setRace_location(String race_location) {
		this.race_location = race_location;
	}

	public String getRace_deadline() {
		return race_deadline;
	}

	public void setRace_deadline(String race_deadline) {
		this.race_deadline = race_deadline;
	}

	public Integer getRace_status() {
		return race_status;
	}

	public void setRace_status(Integer race_status) {
		this.race_status = race_status;
	}

}
