package com.dietdetail.model;

import java.util.List;


public class DietDetailService {
	private DietDetailDAO_interface dao;
	
	public DietDetailService() {
		dao = new DietDetailDAO();
	}

	public List<DietDetailVO> getAll(){
		return dao.getAll();
	}
	public DietDetailVO addDietDetail(String diet_no,String food_no, Double amount, Double food_cal) {
		DietDetailVO dietDetailVO = new DietDetailVO();
		dietDetailVO.setDiet_no(diet_no);
		dietDetailVO.setFood_no(food_no);
		dietDetailVO.setAmount(amount);
		dietDetailVO.setFood_cal(food_cal);
		dao.insert(dietDetailVO);
		
		return dietDetailVO;
	}
	
	public DietDetailVO updateDietDetail (String diet_no,String food_no, Double amount, Double food_cal) {
		DietDetailVO dietDetailVO = new DietDetailVO();
		dietDetailVO.setDiet_no(diet_no);
		dietDetailVO.setFood_no(food_no);
		dietDetailVO.setAmount(amount);
		dietDetailVO.setFood_cal(food_cal);
		dao.update(dietDetailVO);

		return dietDetailVO;
	}

	public void deleteDietDetail(String diet_no,String food_no) {
		dao.delete(diet_no,food_no);
	}

	public List<DietDetailVO> getDietDetail(String diet_no) {
		return dao.findByPrimaryKey(diet_no);
	}
	
	public DietDetailVO getOneDietDetail(String diet_no,String food_no) {
		return dao.findOneByPrimaryKey(diet_no,food_no);
	}
	
}
