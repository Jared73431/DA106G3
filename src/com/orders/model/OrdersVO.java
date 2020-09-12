package com.orders.model;

import java.sql.*;

public class OrdersVO implements java.io.Serializable {
	private String order_no;
	private String member_no;
	private String order_zip;
	private String order_address;
	private Timestamp etb;
	private Timestamp eta;
	private Integer total_price;
	private Integer order_status;

	public OrdersVO() {
		super();
	}

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public String getMember_no() {
		return member_no;
	}

	public void setMember_no(String member_no) {
		this.member_no = member_no;
	}

	public String getOrder_zip() {
		return order_zip;
	}

	public void setOrder_zip(String order_zip) {
		this.order_zip = order_zip;
	}

	public String getOrder_address() {
		return order_address;
	}

	public void setOrder_address(String order_address) {
		this.order_address = order_address;
	}

	public Timestamp getEtb() {
		return etb;
	}

	public void setEtb(Timestamp etb) {
		this.etb = etb;
	}

	public Timestamp getEta() {
		return eta;
	}

	public void setEta(Timestamp eta) {
		this.eta = eta;
	}

	public Integer getTotal_price() {
		return total_price;
	}

	public void setTotal_price(Integer total_price) {
		this.total_price = total_price;
	}

	public Integer getOrder_status() {
		return order_status;
	}

	public void setOrder_status(Integer order_status) {
		this.order_status = order_status;
	}

}
