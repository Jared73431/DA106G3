package com.categorys.model;

import java.util.List;



public class CategorysService {
	private CategorysDAO_interface dao;

	public CategorysService() {
		dao = new CategorysDAO();
	}

	public CategorysVO addCategorys(String category_name) {

		CategorysVO categorysVO = new CategorysVO();

		categorysVO.setCategory_name(category_name);
		dao.insert(categorysVO);

		return categorysVO;
	}

	public CategorysVO updateCategorys(String category_no, String category_name) {

		CategorysVO categorysVO = new CategorysVO();

		categorysVO.setCategory_no(category_no);
		categorysVO.setCategory_name(category_name);
		dao.update(categorysVO);

		return categorysVO;
	}

	public void deleteCategorys(String category_no) {
		dao.delete(category_no);
	}

	public CategorysVO getOneCategorys(String category_no) {
		return dao.findByPrimaryKey(category_no);
	}

	public List<CategorysVO> getAll() {
		return dao.getAll();
	}
}
