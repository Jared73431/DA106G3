package com.informcate.model;

public class InformCateVO implements java.io.Serializable {
	private String cate_no;
	private String cate_name;

	public InformCateVO() {
		super();
	}

	public String getCate_no() {
		return cate_no;
	}

	public void setCate_no(String cate_no) {
		this.cate_no = cate_no;
	}

	public String getCate_name() {
		return cate_name;
	}

	public void setCate_name(String cate_name) {
		this.cate_name = cate_name;
	}

}
