package com.admins.model;



public class AdminsVO implements java.io.Serializable {
	private String admin_no;
	private String admin_account;
	private String admin_password;
	private String admin_name;
	private byte[] admin_photo;
	private Integer admin_status;

	public AdminsVO() {
		super();
	}

	public String getAdmin_no() {
		return admin_no;
	}

	public void setAdmin_no(String admin_no) {
		this.admin_no = admin_no;
	}

	public String getAdmin_account() {
		return admin_account;
	}

	public void setAdmin_account(String admin_account) {
		this.admin_account = admin_account;
	}

	public String getAdmin_password() {
		return admin_password;
	}

	public void setAdmin_password(String admin_password) {
		this.admin_password = admin_password;
	}

	public String getAdmin_name() {
		return admin_name;
	}

	public void setAdmin_name(String admin_name) {
		this.admin_name = admin_name;
	}

	public byte[] getAdmin_photo() {
		return admin_photo;
	}

	public void setAdmin_photo(byte[] admin_photo) {
		this.admin_photo = admin_photo;
	}

	public Integer getAdmin_status() {
		return admin_status;
	}

	public void setAdmin_status(Integer admin_status) {
		this.admin_status = admin_status;
	}

	
	
}
