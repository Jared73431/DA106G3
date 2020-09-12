package com.informcate.model;

import java.util.List;

public class InformCateService {
	
	private InformCateDAO_interface dao;

	public InformCateService() {
		dao = new InformCateDAO();
	}

	public InformCateVO addInformCate(String cate_name) {

		InformCateVO informcateVO = new InformCateVO();

		informcateVO.setCate_name(cate_name);
		dao.insert(informcateVO);

		return informcateVO;
	}

	public InformCateVO updateInformCate(String cate_no, String cate_name) {

		InformCateVO informcateVO = new InformCateVO();

		informcateVO.setCate_no(cate_no);
		informcateVO.setCate_name(cate_name);
		dao.update(informcateVO);

		return informcateVO;
	}

	public void deleteInformCate(String cate_no) {
		dao.delete(cate_no);
	}

	public InformCateVO getOneInformCate(String cate_no) {
		return dao.findByPrimaryKey(cate_no);
	}

	public List<InformCateVO> getAll() {
		return dao.getAll();
	}
}
