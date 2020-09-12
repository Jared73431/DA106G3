package com.productcate.model;

public class ProductCateVO implements java.io.Serializable {
	private String prc_no;
	private String prc_name;

	public ProductCateVO() {
		super();
	}

	public String getPrc_no() {
		return prc_no;
	}

	public void setPrc_no(String prc_no) {
		this.prc_no = prc_no;
	}

	public String getPrc_name() {
		return prc_name;
	}

	public void setPrc_name(String prc_name) {
		this.prc_name = prc_name;
	}

}
