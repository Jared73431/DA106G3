package com.shopping.model;

public class ShoppingVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String pro_no;
	private String prc_no;
	private String pro_name;
	private Integer pro_price;
	private Integer quantity;

	public ShoppingVO() {
		super();
	}

	public String getPro_no() {
		return pro_no;
	}

	public void setPro_no(String pro_no) {
		this.pro_no = pro_no;
	}

	public String getPrc_no() {
		return prc_no;
	}

	public void setPrc_no(String prc_no) {
		this.prc_no = prc_no;
	}

	public String getPro_name() {
		return pro_name;
	}

	public void setPro_name(String pro_name) {
		this.pro_name = pro_name;
	}

	public Integer getPro_price() {
		return pro_price;
	}

	public void setPro_price(Integer pro_price) {
		this.pro_price = pro_price;
	}

	public Integer getQuantity() {
		return quantity;
	}
	
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pro_no == null) ? 0 : pro_no.hashCode());
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
		ShoppingVO other = (ShoppingVO) obj;
		if (pro_no == null) {
			if (other.pro_no != null)
				return false;
		} else if (!pro_no.equals(other.pro_no))
			return false;
		return true;
	}


}
