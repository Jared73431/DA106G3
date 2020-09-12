package com.newsknowledge.model;

import java.util.*;

import com.foodinform.model.FoodInformVO;

public interface NewsKnowledgeDAO_interface {
	public int insert(NewsKnowledgeVO newsknowledgeVO);
	public int update(NewsKnowledgeVO newsknowledgeVO);
	public void changeStatus(String news_no , Integer news_status);
	public NewsKnowledgeVO findByPrimaryKey(String news_no);
	public List<NewsKnowledgeVO> getAll();
	public List<NewsKnowledgeVO> getAuth();
	public byte[] readPic(String news_no);
	public List<NewsKnowledgeVO> getRandom();
}
