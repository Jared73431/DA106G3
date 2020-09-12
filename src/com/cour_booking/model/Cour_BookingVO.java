package com.cour_booking.model;

public class Cour_BookingVO implements java.io.Serializable{
	private String booking_no;
	private String cour_no;
	private String member_no;
	private String coa_no;
	private Integer coa_comf;
	private Integer trainee_comf;
	private Integer coa_ci;
	private Integer trainee_ci;
	private Integer cour_score;
	private String cour_option;
	private Integer choose_mode;
	public Cour_BookingVO() {
		super();
	}
	public String getBooking_no() {
		return booking_no;
	}
	public void setBooking_no(String booking_no) {
		this.booking_no = booking_no;
	}
	public String getCour_no() {
		return cour_no;
	}
	public void setCour_no(String cour_no) {
		this.cour_no = cour_no;
	}
	public String getMember_no() {
		return member_no;
	}
	public void setMember_no(String member_no) {
		this.member_no = member_no;
	}
	public String getCoa_no() {
		return coa_no;
	}
	public void setCoa_no(String coa_no) {
		this.coa_no = coa_no;
	}
	public Integer getCoa_comf() {
		return coa_comf;
	}
	public void setCoa_comf(Integer coa_comf) {
		this.coa_comf = coa_comf;
	}
	public Integer getTrainee_comf() {
		return trainee_comf;
	}
	public void setTrainee_comf(Integer trainee_comf) {
		this.trainee_comf = trainee_comf;
	}
	public Integer getCoa_ci() {
		return coa_ci;
	}
	public void setCoa_ci(Integer coa_ci) {
		this.coa_ci = coa_ci;
	}
	public Integer getTrainee_ci() {
		return trainee_ci;
	}
	public void setTrainee_ci(Integer trainee_ci) {
		this.trainee_ci = trainee_ci;
	}
	public Integer getCour_score() {
		return cour_score;
	}
	public void setCour_score(Integer cour_score) {
		this.cour_score = cour_score;
	}
	public String getCour_option() {
		return cour_option;
	}
	public void setCour_option(String cour_option) {
		this.cour_option = cour_option;
	}
	public Integer getChoose_mode() {
		return choose_mode;
	}
	public void setChoose_mode(Integer choose_mode) {
		this.choose_mode = choose_mode;
	}
	
}
