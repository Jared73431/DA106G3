package com.foodnutrition.model;

import java.util.*;

public interface FoodNutritionDAO_interface {

    public int insert(FoodNutritionVO foodNutritionVO);

    public int update(FoodNutritionVO foodNutritionVO);

    public int delete(String food_no);

    public FoodNutritionVO findByPrimaryKey(String food_no);

    public List<FoodNutritionVO> getAll();
}
