package com.feature.model;

public class FeatureVO implements java.io.Serializable{
	private String feature_no;
	private String feature_name;
	private String feature_spec;
	public FeatureVO() {
		super();
	}

	public String getFeature_no() {
		return feature_no;
	}

	public void setFeature_no(String feature_no) {
		this.feature_no = feature_no;
	}

	public String getFeature_name() {
		return feature_name;
	}

	public void setFeature_name(String feature_name) {
		this.feature_name = feature_name;
	}

	public String getFeature_spec() {
		return feature_spec;
	}

	public void setFeature_spec(String feature_spec) {
		this.feature_spec = feature_spec;
	}
}
