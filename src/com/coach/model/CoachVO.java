package com.coach.model;

public class CoachVO implements java.io.Serializable{
	private String coa_no;
	private String member_no;
	private String coa_info;
	private String service_area;
	private String coa_skill1;
	private byte[] coa_licence1;
	private String coa_skill2;
	private byte[] coa_licence2;
	private String coa_skill3;
	private byte[] coa_licence3;
	private Integer coa_status;
	public CoachVO() {
		super();
	}
	public String getCoa_no() {
		return coa_no;
	}
	public void setCoa_no(String coa_no) {
		this.coa_no = coa_no;
	}
	public String getMember_no() {
		return member_no;
	}
	public void setMember_no(String member_no) {
		this.member_no = member_no;
	}
	public String getCoa_info() {
		return coa_info;
	}
	public void setCoa_info(String coa_info) {
		this.coa_info = coa_info;
	}
	public String getService_area() {
		return service_area;
	}
	public void setService_area(String service_area) {
		this.service_area = service_area;
	}
	public String getCoa_skill1() {
		return coa_skill1;
	}
	public void setCoa_skill1(String coa_skill1) {
		this.coa_skill1 = coa_skill1;
	}
	public byte[] getCoa_licence1() {
		return coa_licence1;
	}
	public void setCoa_licence1(byte[] coa_licence1) {
		this.coa_licence1 = coa_licence1;
	}
	public String getCoa_skill2() {
		return coa_skill2;
	}
	public void setCoa_skill2(String coa_skill2) {
		this.coa_skill2 = coa_skill2;
	}
	public byte[] getCoa_licence2() {
		return coa_licence2;
	}
	public void setCoa_licence2(byte[] coa_licence2) {
		this.coa_licence2 = coa_licence2;
	}
	public String getCoa_skill3() {
		return coa_skill3;
	}
	public void setCoa_skill3(String coa_skill3) {
		this.coa_skill3 = coa_skill3;
	}
	public byte[] getCoa_licence3() {
		return coa_licence3;
	}
	public void setCoa_licence3(byte[] coa_licence3) {
		this.coa_licence3 = coa_licence3;
	}
	public Integer getCoa_status() {
		return coa_status;
	}
	public void setCoa_status(Integer coa_status) {
		this.coa_status = coa_status;
	}
	
}
