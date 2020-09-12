package com.productcate.model;

import java.util.*;

public interface ProductCateDAO_interface {
	public int insert(ProductCateVO productcateVO);
	public int update(ProductCateVO productcateVO);
	public int delete(String prc_no);
    public ProductCateVO findByPrimaryKey(String prc_no);
	public List<ProductCateVO> getAll();

}
