package com.followmaster.model;


public class FollowMasterVO implements java.io.Serializable{

	private String member_no;
	private String master_no;

	public FollowMasterVO() {
		super();
	}

	public FollowMasterVO(String member_no, String master_no) {
		super();
		this.member_no = member_no;
		this.master_no = master_no;
	}

	public String getMember_no() {
		return member_no;
	}

	public void setMember_no(String member_no) {
		this.member_no = member_no;
	}

	public String getMaster_no() {
		return master_no;
	}

	public void setMaster_no(String master_no) {
		this.master_no = master_no;
	}

}
