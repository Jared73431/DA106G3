package com.dietrecord.model;

import java.sql.Date;
import java.util.List;

import com.dietdetail.model.DietDetailVO;

public class DietRecordService {
	private DietRecordDAO_interface dao;

	public DietRecordService() {
		dao = new DietRecordDAO();
	}

	public List<DietRecordVO> getAll() {
		return dao.getAll();
	}

	public DietRecordVO addDietRecord(String member_no, Date rec_date, Integer eat_period,
			byte[] photo) {
		DietRecordVO dietRecordVO = new DietRecordVO();
		dietRecordVO.setMember_no(member_no);
		dietRecordVO.setRec_date(rec_date);
		dietRecordVO.setEat_period(eat_period);
		dietRecordVO.setPhoto(photo);
		dao.insert(dietRecordVO);
		return dietRecordVO;
	}

	public DietRecordVO updateDietRecord(String diet_no, String member_no, Date rec_date, Integer eat_period,
			byte[] photo) {
		DietRecordVO dietRecordVO = new DietRecordVO();
		dietRecordVO.setDiet_no(diet_no);
		dietRecordVO.setMember_no(member_no);
		dietRecordVO.setRec_date(rec_date);
		dietRecordVO.setEat_period(eat_period);
		dietRecordVO.setPhoto(photo);
		dao.update(dietRecordVO);
		return dietRecordVO;
	}

	public void deleteDietRecord(String diet_no) {
		dao.delete(diet_no);
	}

	public DietRecordVO getOneDietRecord(String diet_no) {
		return dao.findByPrimaryKey(diet_no);
	}
	
	public void insertWithDetail(DietRecordVO dietRecordVO , List<DietDetailVO> list) {
		dao.insertWithDetail(dietRecordVO , list);
	}
	
	public List findByDate(String member_no, java.sql.Date rec_date) {
		return dao.findByDate(member_no, rec_date);
	}
	
	public void delete_pic (String diet_no) {
		dao.delete_pic(diet_no);
	}
}
