package com.experience.model;

import java.util.List;



public interface ExperienceDAO_interface {
	public void insert(ExperienceVO experienceVO);

	public void update(ExperienceVO experienceVO);

	public void delete(String exper_no);

	public ExperienceVO findByPrimaryKey(String exper_no);

	public List<ExperienceVO> getAll();
	
	public List<ExperienceVO> getRandom();
}
