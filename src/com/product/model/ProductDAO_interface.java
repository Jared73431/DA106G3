package com.product.model;

import java.util.*;

public interface ProductDAO_interface {
	public int insert(ProductVO productVO);
	public int update(ProductVO productVO);
	public void changeStatus(String pro_no,Integer pro_status);
    public ProductVO findByPrimaryKey(String pro_no);
	public List<ProductVO> getAll();
	public byte[] readPic(String pro_no);
	public List<ProductVO> getShop();
	public List<ProductVO> getShop(String prc_no);
}
