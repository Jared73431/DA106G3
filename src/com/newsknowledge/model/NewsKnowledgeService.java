package com.newsknowledge.model;

import java.util.List;
import java.sql.*;

public class NewsKnowledgeService {
	
	private NewsKnowledgeDAO_interface dao;

	public NewsKnowledgeService() {
		dao = new NewsKnowledgeDAO();
	}

	public NewsKnowledgeVO addNewsKnowledge(String news_title, String news_author, byte[] news_cover, String news_content) {

		NewsKnowledgeVO foodinformVO = new NewsKnowledgeVO();

		foodinformVO.setNews_title(news_title);
		foodinformVO.setNews_author(news_author);
		foodinformVO.setNews_cover(news_cover);
		foodinformVO.setNews_content(news_content);
		dao.insert(foodinformVO);

		return foodinformVO;
	}

	public NewsKnowledgeVO updateNewsKnowledge(String news_no, String news_title, String news_author, byte[] news_cover, String news_content, Timestamp news_date, Integer news_status) {

		NewsKnowledgeVO foodinformVO = new NewsKnowledgeVO();
		
		foodinformVO.setNews_no(news_no);
		foodinformVO.setNews_title(news_title);
		foodinformVO.setNews_author(news_author);
		foodinformVO.setNews_cover(news_cover);
		foodinformVO.setNews_content(news_content);
		foodinformVO.setNews_date(news_date);
		foodinformVO.setNews_status(news_status);
		dao.update(foodinformVO);

		return foodinformVO;
	}

	public void changeStatus(String news_no, Integer news_status) {
		dao.changeStatus(news_no, news_status);
	}

	public NewsKnowledgeVO getOneNewsKnowledge(String news_no) {
		return dao.findByPrimaryKey(news_no);
	}
	
	// 測試方法
	public byte[] readPic(String news_no) {
		return dao.readPic(news_no);
	}

	public List<NewsKnowledgeVO> getAll() {
		return dao.getAll();
	}
	
	// 取得上架文章
	public List<NewsKnowledgeVO> getAuth() {
		return dao.getAuth();
	}
	
	public List<NewsKnowledgeVO> getRandom() {
		return dao.getRandom();
	}
}
