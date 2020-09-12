package com.orderlist.model;

public class OrderListVO implements java.io.Serializable {
	private String order_no;
	private String pro_no;
	private Integer pro_qty;
	private Integer order_price;

	public OrderListVO() {
		super();
	}

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public String getPro_no() {
		return pro_no;
	}

	public void setPro_no(String pro_no) {
		this.pro_no = pro_no;
	}

	public Integer getPro_qty() {
		return pro_qty;
	}

	public void setPro_qty(Integer pro_qty) {
		this.pro_qty = pro_qty;
	}

	public Integer getOrder_price() {
		return order_price;
	}

	public void setOrder_price(Integer order_price) {
		this.order_price = order_price;
	}

}
