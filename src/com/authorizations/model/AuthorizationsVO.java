package com.authorizations.model;

public class AuthorizationsVO implements java.io.Serializable{
	private String admin_no;
	private String feature_no;
	
	public AuthorizationsVO() {
		super();
	}

	public String getAdmin_no() {
		return admin_no;
	}

	public void setAdmin_no(String admin_no) {
		this.admin_no = admin_no;
	}

	public String getFeature_no() {
		return feature_no;
	}

	public void setFeature_no(String feature_no) {
		this.feature_no = feature_no;
	}
	
	
	
}
