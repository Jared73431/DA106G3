package com.foodinform.model;

import java.util.*;

public interface FoodInformDAO_interface {
	public int insert(FoodInformVO foodinformVO);
	public int update(FoodInformVO foodinformVO);
	public void changeStatus(String fi_no, Integer fi_status);
    public FoodInformVO findByPrimaryKey(String fi_no);
	public List<FoodInformVO> getAll();
	public List<FoodInformVO> getAuth();
	public byte[] readPic(String fi_no);
	public List<FoodInformVO> getRandom();
}
