package com.feature.model;

import java.util.List;

public class FeatureService {
	private FeatureDAO_interface dao;
	
	public FeatureService() {
		dao = new FeatureDAO();
	}
	
	public FeatureVO addFeature(String feature_name,String feature_spec) {
		FeatureVO featureVO = new FeatureVO();
		
		featureVO.setFeature_name(feature_name);
		featureVO.setFeature_spec(feature_spec);
		
		dao.insert(featureVO);
		return featureVO;
	}
	
	public FeatureVO updateFeature(String feature_no,String feature_name,String feature_spec) {
		FeatureVO featureVO = new FeatureVO();
		
		featureVO.setFeature_no(feature_no);
		featureVO.setFeature_name(feature_name);
		featureVO.setFeature_spec(feature_spec);
		
		dao.update(featureVO);
		return featureVO;
	}
	
	public void delete(String feature_no) {
		dao.delete(feature_no);
	}
	
	public FeatureVO getFeature(String feature_no) {
		return dao.findByPrimaryKey(feature_no);
	}
	
	public List<FeatureVO> getAll(){
		return dao.getAll();
	}
}
