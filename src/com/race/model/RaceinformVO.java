package com.race.model;


public class RaceinformVO implements java.io.Serializable {

	private String race_no;
	private String cate_no;
	private String race_year;
	private String race_inform;

	public RaceinformVO() {
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

	public String getRace_year() {
		return race_year;
	}

	public void setRace_year(String race_year) {
		this.race_year = race_year;
	}

	public String getRace_inform() {
		return race_inform;
	}

	public void setRace_inform(String race_inform) {
		this.race_inform = race_inform;
	}

}
