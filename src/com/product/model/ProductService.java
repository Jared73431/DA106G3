package com.product.model;

import java.util.List;
import java.sql.*;

public class ProductService {
	
	private ProductDAO_interface dao;

	public ProductService() {
		dao = new ProductDAO();
	}

	public ProductVO addProduct(String prc_no, String pro_name, Integer pro_price, String pro_dis, byte[] pro_pic) {

		ProductVO productVO = new ProductVO();

		productVO.setPrc_no(prc_no);
		productVO.setPro_name(pro_name);
		productVO.setPro_price(pro_price);
		productVO.setPro_dis(pro_dis);
		productVO.setPro_pic(pro_pic);
		dao.insert(productVO);

		return productVO;
	}

	public ProductVO updateProduct(String pro_no, String prc_no, String pro_name, Integer pro_price, Integer pro_status,
			String pro_dis, byte[] pro_pic) {

		ProductVO productVO = new ProductVO();
		
		productVO.setPro_no(pro_no);
		productVO.setPrc_no(prc_no);
		productVO.setPro_name(pro_name);
		productVO.setPro_price(pro_price);
		productVO.setPro_status(pro_status);
		productVO.setPro_dis(pro_dis);
		productVO.setPro_pic(pro_pic);
		dao.update(productVO);

		return productVO;
	}

	public void changeStatus(String pro_no,Integer pro_status) {
		dao.changeStatus(pro_no,pro_status);
	}

	public ProductVO getOneProduct(String pro_no) {
		return dao.findByPrimaryKey(pro_no);
	}
	// 測試方法
	public byte[] readPic(String pro_no) {
		return dao.readPic(pro_no);
	}
	
	public List<ProductVO> getAll() {
		return dao.getAll();
	}
	// 新增給前台商品使用
	public List<ProductVO> getShop() {
		return dao.getShop();
	}
	// 新增給前台商品使用，依分類查詢
	public List<ProductVO> getShop(String prc_no) {
		return dao.getShop(prc_no);
	}
}
