package com.foodinform.model;

import java.util.List;
import java.sql.*;

public class FoodInformService {
	
	private FoodInformDAO_interface dao;

	public FoodInformService() {
		dao = new FoodInformDAO();
	}

	public FoodInformVO addFoodInform(String fi_title, String fi_author, byte[] fi_cover, String fi_content) {

		FoodInformVO foodinformVO = new FoodInformVO();

		foodinformVO.setFi_title(fi_title);
		foodinformVO.setFi_author(fi_author);
		foodinformVO.setFi_cover(fi_cover);
		foodinformVO.setFi_content(fi_content);
		dao.insert(foodinformVO);

		return foodinformVO;
	}

	public FoodInformVO updateFoodInform(String fi_no, String fi_title, String fi_author, byte[] fi_cover, String fi_content, Timestamp fi_date, Integer fi_status) {

		FoodInformVO foodinformVO = new FoodInformVO();
		
		foodinformVO.setFi_no(fi_no);
		foodinformVO.setFi_title(fi_title);
		foodinformVO.setFi_author(fi_author);
		foodinformVO.setFi_cover(fi_cover);
		foodinformVO.setFi_content(fi_content);
		foodinformVO.setFi_date(fi_date);
		foodinformVO.setFi_status(fi_status);
		dao.update(foodinformVO);

		return foodinformVO;
	}

	public void changeStatus(String fi_no,Integer fi_status) {
		dao.changeStatus(fi_no,fi_status);
	}

	public FoodInformVO getOneFoodInform(String fi_no) {
		return dao.findByPrimaryKey(fi_no);
	}
	
	// 測試方法
	public byte[] readPic(String fi_no) {
		return dao.readPic(fi_no);
	}

	public List<FoodInformVO> getAll() {
		return dao.getAll();
	}
	
	// 取得上架文章
	public List<FoodInformVO> getAuth() {
		return dao.getAuth();
	}
	
	// 隨機取得3筆文章
	public List<FoodInformVO> getRandom() {
		return dao.getRandom();
	}
}
