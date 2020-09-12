package com.foodnutrition.model;

import java.util.List;


public class FoodNutritionService {
	private FoodNutritionDAO_interface dao;
	
	public FoodNutritionService() {
		dao = new FoodNutritionDAO();
	}
	

	public List<FoodNutritionVO> getAll(){
		return dao.getAll();
	}
	
	public FoodNutritionVO addFood(String food_name, String ref_brand, String ref_amount, Double ref_cal,
			Double ref_protein, Double ref_fat, Double ref_cho) {
		FoodNutritionVO foodNutritionVO = new FoodNutritionVO();
		foodNutritionVO.setFood_name(food_name);
		foodNutritionVO.setRef_brand(ref_brand);
		foodNutritionVO.setRef_amount(ref_amount);
		foodNutritionVO.setRef_cal(ref_cal);
		foodNutritionVO.setRef_protein(ref_protein);
		foodNutritionVO.setRef_fat(ref_fat);
		foodNutritionVO.setRef_cho(ref_cho);
		dao.insert(foodNutritionVO);
		
		return foodNutritionVO;
	}
	
	public FoodNutritionVO updateFood(String food_name, String ref_brand, String ref_amount, Double ref_cal,
			Double ref_protein, Double ref_fat, Double ref_cho, String food_no) {
		FoodNutritionVO foodNutritionVO = new FoodNutritionVO();
		foodNutritionVO.setFood_name(food_name);
		foodNutritionVO.setRef_brand(ref_brand);
		foodNutritionVO.setRef_amount(ref_amount);
		foodNutritionVO.setRef_cal(ref_cal);
		foodNutritionVO.setRef_protein(ref_protein);
		foodNutritionVO.setRef_fat(ref_fat);
		foodNutritionVO.setRef_cho(ref_cho);
		foodNutritionVO.setFood_no(food_no);
		dao.update(foodNutritionVO);
		
		return foodNutritionVO;
	}
	public void deleteFood(String food_no) {
		dao.delete(food_no);
	}

	public FoodNutritionVO getOneFood(String food_no) {
		return dao.findByPrimaryKey(food_no);
	}
}
