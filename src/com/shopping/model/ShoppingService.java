package com.shopping.model;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class ShoppingService {
	

	public ShoppingService() {
	
	}

	public ShoppingVO getShopping(HttpServletRequest req) {

		Integer quantity = Integer.parseInt(req.getParameter("quantity"));
		String pro_no = req.getParameter("pro_no");
		String prc_no = req.getParameter("prc_no");
		String pro_name = req.getParameter("pro_name");
		Integer pro_price = Integer.parseInt(req.getParameter("pro_price"));
		
		ShoppingVO shop = new ShoppingVO();
		shop.setPro_no(pro_no);
		shop.setPrc_no(prc_no);
		shop.setPro_name(pro_name);
		shop.setPro_price(pro_price);
		shop.setQuantity(quantity);
		return shop;
	}

}
