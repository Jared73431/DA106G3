package com.dietdetail.model;

public class DietDetailVO implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String diet_no;
	private String food_no;
	private Double amount;
	private Double food_cal;
	
	public DietDetailVO() {
		super();
	}
	
	public DietDetailVO(String diet_no, String food_no, Double amount, Double food_cal) {
		super();
		this.diet_no = diet_no;
		this.food_no = food_no;
		this.amount = amount;
		this.food_cal = food_cal;
	}
	
	public DietDetailVO(String food_no, Double amount, Double food_cal) {
		super();
		this.food_no = food_no;
		this.amount = amount;
		this.food_cal = food_cal;
	}

	public String getDiet_no() {
		return diet_no;
	}

	public void setDiet_no(String diet_no) {
		this.diet_no = diet_no;
	}

	public String getFood_no() {
		return food_no;
	}

	public void setFood_no(String food_no) {
		this.food_no = food_no;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getFood_cal() {
		return food_cal;
	}

	public void setFood_cal(Double food_cal) {
		this.food_cal = food_cal;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

	@Override
	public String toString() {
		return "DietDetailVO [diet_no=" + diet_no + ", food_no=" + food_no + ", amount=" + amount + ", food_cal="
				+ food_cal + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((food_no == null) ? 0 : food_no.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DietDetailVO other = (DietDetailVO) obj;
		if (food_no == null) {
			if (other.food_no != null)
				return false;
		} else if (!food_no.equals(other.food_no))
			return false;
		return true;
	}

	
}
