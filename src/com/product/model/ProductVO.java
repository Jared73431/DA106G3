package com.product.model;

public class ProductVO implements java.io.Serializable {
	private String pro_no;
	private String prc_no;
	private String pro_name;
	private Integer pro_price;
	private Integer pro_status;
	private String pro_dis;
	private byte[] pro_pic;

	public ProductVO() {
		super();
	}

	public String getPro_no() {
		return pro_no;
	}

	public void setPro_no(String pro_no) {
		this.pro_no = pro_no;
	}

	public String getPrc_no() {
		return prc_no;
	}

	public void setPrc_no(String prc_no) {
		this.prc_no = prc_no;
	}

	public String getPro_name() {
		return pro_name;
	}

	public void setPro_name(String pro_name) {
		this.pro_name = pro_name;
	}

	public Integer getPro_price() {
		return pro_price;
	}

	public void setPro_price(Integer pro_price) {
		this.pro_price = pro_price;
	}

	public Integer getPro_status() {
		return pro_status;
	}

	public void setPro_status(Integer pro_status) {
		this.pro_status = pro_status;
	}

	public String getPro_dis() {
		return pro_dis;
	}

	public void setPro_dis(String pro_dis) {
		this.pro_dis = pro_dis;
	}

	public byte[] getPro_pic() {
		return pro_pic;
	}

	public void setPro_pic(byte[] pro_pic) {
		this.pro_pic = pro_pic;
	}

}
