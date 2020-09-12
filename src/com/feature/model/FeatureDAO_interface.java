package com.feature.model;

import java.util.*;

public interface FeatureDAO_interface {
	public void insert(FeatureVO featureVO);
	public void update(FeatureVO featureVO);
	public void delete(String feature_no);
	public FeatureVO findByPrimaryKey(String feature_no);
	public List<FeatureVO> getAll();
}
