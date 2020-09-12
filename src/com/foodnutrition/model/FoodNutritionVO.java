package com.foodnutrition.model;

public class FoodNutritionVO implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String food_no;
	private String food_name;
	private String ref_brand;
	private String ref_amount;
	private Double ref_cal;
	private Double ref_protein;
	private Double ref_fat;
	private Double ref_cho;
	
	public FoodNutritionVO() {
		super();
	}

	public FoodNutritionVO(String food_no, String food_name, String ref_brand, String ref_amount, Double ref_cal,
			Double ref_protein, Double ref_fat, Double ref_cho) {
		super();
		this.food_no = food_no;
		this.food_name = food_name;
		this.ref_brand = ref_brand;
		this.ref_amount = ref_amount;
		this.ref_cal = ref_cal;
		this.ref_protein = ref_protein;
		this.ref_fat = ref_fat;
		this.ref_cho = ref_cho;
	}

	public String getFood_no() {
		return food_no;
	}

	public void setFood_no(String food_no) {
		this.food_no = food_no;
	}

	public String getFood_name() {
		return food_name;
	}

	public void setFood_name(String food_name) {
		this.food_name = food_name;
	}

	public String getRef_brand() {
		return ref_brand;
	}

	public void setRef_brand(String ref_brand) {
		this.ref_brand = ref_brand;
	}

	public String getRef_amount() {
		return ref_amount;
	}

	public void setRef_amount(String ref_amount) {
		this.ref_amount = ref_amount;
	}

	public Double getRef_cal() {
		return ref_cal;
	}

	public void setRef_cal(Double ref_cal) {
		this.ref_cal = ref_cal;
	}

	public Double getRef_protein() {
		return ref_protein;
	}

	public void setRef_protein(Double ref_protein) {
		this.ref_protein = ref_protein;
	}

	public Double getRef_fat() {
		return ref_fat;
	}

	public void setRef_fat(Double ref_fat) {
		this.ref_fat = ref_fat;
	}

	public Double getRef_cho() {
		return ref_cho;
	}

	public void setRef_cho(Double ref_cho) {
		this.ref_cho = ref_cho;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}	
	
}
