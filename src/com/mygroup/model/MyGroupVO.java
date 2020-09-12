package com.mygroup.model;

public class MyGroupVO implements java.io.Serializable{
	private String groupgo_id;
	private String member_no;
	private Integer mygroup_status;
	private Integer score;
	
	public MyGroupVO() {
		super();
	}
	
	public MyGroupVO(String groupgo_id, String member_no, Integer mygroup_status, Integer score) {
		super();
		this.groupgo_id = groupgo_id;
		this.member_no = member_no;
		this.mygroup_status = mygroup_status;
		this.score = score;
	}

	public MyGroupVO(String groupgo_id, String member_no, Integer mygroup_status) {
		super();
		this.groupgo_id = groupgo_id;
		this.member_no = member_no;
		this.mygroup_status = mygroup_status;
		
	}

	public String getGroupgo_id() {
		return groupgo_id;
	}

	public void setGroupgo_id(String groupgo_id) {
		this.groupgo_id = groupgo_id;
	}

	public String getMember_no() {
		return member_no;
	}

	public void setMember_no(String member_no) {
		this.member_no = member_no;
	}

	public Integer getMygroup_status() {
		return mygroup_status;
	}

	public void setMygroup_status(Integer mygroup_status) {
		this.mygroup_status = mygroup_status;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

}
