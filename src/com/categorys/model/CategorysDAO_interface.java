package com.categorys.model;

import java.util.List;


public interface CategorysDAO_interface {
	public void insert(CategorysVO categorysVO);

	public void update(CategorysVO categorysVO);

	public void delete(String category_no);

	public CategorysVO findByPrimaryKey(String category_no);

	public List<CategorysVO> getAll();
	
}
