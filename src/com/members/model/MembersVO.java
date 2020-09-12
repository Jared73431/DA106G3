package com.members.model;

import java.sql.*;
import java.util.Arrays;

public class MembersVO implements java.io.Serializable {
	private String member_no;
	private Integer coa_qualifications;
	private String known;
	private Integer sexual;
	private String mem_name;
	private String mem_account; 
	private Integer mem_status;
	private String email;
	private String mem_password;
	private Date birthday;
	private String phone;
	private String mem_zip;
	private String address;
	private Integer real_blance;
	private Double height;
	private Timestamp reg_date;
	private byte[] picture;
	private byte[] license;
	public MembersVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MembersVO(String member_no, Integer coa_qualifications, String known, Integer sexual, String mem_name,
			String mem_account, Integer mem_status, String email, String mem_password, Date birthday, String phone,
			String mem_zip, String address, Integer real_blance, Double height, Timestamp reg_date, byte[] picture,
			byte[] license) {
		super();
		this.member_no = member_no;
		this.coa_qualifications = coa_qualifications;
		this.known = known;
		this.sexual = sexual;
		this.mem_name = mem_name;
		this.mem_account = mem_account;
		this.mem_status = mem_status;
		this.email = email;
		this.mem_password = mem_password;
		this.birthday = birthday;
		this.phone = phone;
		this.mem_zip = mem_zip;
		this.address = address;
		this.real_blance = real_blance;
		this.height = height;
		this.reg_date = reg_date;
		this.picture = picture;
		this.license = license;
	}
	@Override
	public String toString() {
		return "MembersVO [member_no=" + member_no + ", coa_qualifications=" + coa_qualifications + ", known=" + known
				+ ", sexual=" + sexual + ", mem_name=" + mem_name + ", mem_account=" + mem_account + ", mem_status="
				+ mem_status + ", email=" + email + ", mem_password=" + mem_password + ", birthday=" + birthday
				+ ", phone=" + phone + ", mem_zip=" + mem_zip + ", address=" + address + ", real_blance=" + real_blance
				+ ", height=" + height + ", reg_date=" + reg_date + ", picture=" + Arrays.toString(picture)
				+ ", license=" + Arrays.toString(license) + "]";
	}
	public String getMember_no() {
		return member_no;
	}
	public void setMember_no(String member_no) {
		this.member_no = member_no;
	}
	public Integer getCoa_qualifications() {
		return coa_qualifications;
	}
	public void setCoa_qualifications(Integer coa_qualifications) {
		this.coa_qualifications = coa_qualifications;
	}
	public String getKnown() {
		return known;
	}
	public void setKnown(String known) {
		this.known = known;
	}
	public Integer getSexual() {
		return sexual;
	}
	public void setSexual(Integer sexual) {
		this.sexual = sexual;
	}
	public String getMem_name() {
		return mem_name;
	}
	public void setMem_name(String mem_name) {
		this.mem_name = mem_name;
	}
	public String getMem_account() {
		return mem_account;
	}
	public void setMem_account(String mem_account) {
		this.mem_account = mem_account;
	}
	public Integer getMem_status() {
		return mem_status;
	}
	public void setMem_status(Integer mem_status) {
		this.mem_status = mem_status;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMem_password() {
		return mem_password;
	}
	public void setMem_password(String mem_password) {
		this.mem_password = mem_password;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMem_zip() {
		return mem_zip;
	}
	public void setMem_zip(String mem_zip) {
		this.mem_zip = mem_zip;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getReal_blance() {
		return real_blance;
	}
	public void setReal_blance(Integer real_blance) {
		this.real_blance = real_blance;
	}
	public Double getHeight() {
		return height;
	}
	public void setHeight(Double height) {
		this.height = height;
	}
	public Timestamp getReg_date() {
		return reg_date;
	}
	public void setReg_date(Timestamp reg_date) {
		this.reg_date = reg_date;
	}
	public byte[] getPicture() {
		return picture;
	}
	public void setPicture(byte[] picture) {
		this.picture = picture;
	}
	public byte[] getLicense() {
		return license;
	}
	public void setLicense(byte[] license) {
		this.license = license;
	}
}
