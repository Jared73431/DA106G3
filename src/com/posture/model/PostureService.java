package com.posture.model;

import java.sql.Date;
import java.util.List;

import com.mygroup.model.MyGroupVO;

public class PostureService {
	private PostureDAO_interface dao;

	public PostureService() {
		dao = new PostureDAO();
	}

	public List<PostureVO> getAll() {
		return dao.getAll();
	}

	public PostureVO addPosture(String member_no, Date record_date, Double weight, Double bodyfat, Integer bmr,
			Double bmi, Double remain_cal) {
		PostureVO one =  dao.findOneByDate(member_no, record_date);
		
		PostureVO postureVO = new PostureVO();
		postureVO.setMember_no(member_no);
		postureVO.setRecord_date(record_date);
		postureVO.setWeight(weight);
		postureVO.setBodyfat(bodyfat);
		postureVO.setBmr(bmr);
		postureVO.setBmi(bmi);
		postureVO.setRemain_cal(remain_cal);
		if(one == null) {
		dao.insert(postureVO);
		}
		return postureVO;
	}

	public void delete(String posture_no) {
		dao.delete(posture_no);
	}

	public PostureVO update(String posture_no, String member_no, Date record_date, Double weight, Double bodyfat,
			Integer bmr, Double bmi, Double remain_cal) {
		PostureVO postureVO = new PostureVO();
		postureVO.setPosture_no(posture_no);
		postureVO.setMember_no(member_no);
		postureVO.setRecord_date(record_date);
		postureVO.setWeight(weight);
		postureVO.setBodyfat(bodyfat);
		postureVO.setBmr(bmr);
		postureVO.setBmi(bmi);
		postureVO.setRemain_cal(remain_cal);
		dao.update(postureVO);
		return postureVO;
	}
	
	public void updateRemaincal(Double remain_cal,String posture_no){
		dao.updateRemaincal(remain_cal,posture_no);
	}

	public List<PostureVO> getMember(String member_no) {
		return dao.findByPrimaryKey(member_no);
	}
	
	public PostureVO getOne(String posture_no) {
		return dao.findOneByPrimaryKey(posture_no);
	}
	public PostureVO findOneByDate(String member_no,java.sql.Date record_date) {
		return dao.findOneByDate(member_no,record_date);
	}

}
