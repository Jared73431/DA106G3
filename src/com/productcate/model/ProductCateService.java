package com.productcate.model;

import java.util.List;

public class ProductCateService {
	
	private ProductCateDAO_interface dao;

	public ProductCateService() {
		dao = new ProductCateDAO();
	}

	public ProductCateVO addProductCate(String prc_name) {

		ProductCateVO productcateVO = new ProductCateVO();

		productcateVO.setPrc_name(prc_name);
		dao.insert(productcateVO);

		return productcateVO;
	}

	public ProductCateVO updateProductCate(String prc_no, String prc_name) {

		ProductCateVO productcateVO = new ProductCateVO();

		productcateVO.setPrc_no(prc_no);
		productcateVO.setPrc_name(prc_name);
		dao.update(productcateVO);

		return productcateVO;
	}

	public void deleteProductCate(String prc_no) {
		dao.delete(prc_no);
	}

	public ProductCateVO getOneProductCate(String prc_no) {
		return dao.findByPrimaryKey(prc_no);
	}

	public List<ProductCateVO> getAll() {
		return dao.getAll();
	}
}
