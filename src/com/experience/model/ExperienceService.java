package com.experience.model;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.notificationlist.model.NotificationListVO;



public class ExperienceService {
	private ExperienceDAO_interface dao;

	public ExperienceService() {
		dao = new ExperienceDAO();
	}

	public ExperienceVO addexperience(String member_no, String cate_no, String exper_context, String exper_title,
			byte[] picture) {
		ExperienceVO experienceVO = new ExperienceVO();
		experienceVO.setMember_no(member_no);
		experienceVO.setCate_no(cate_no);
		experienceVO.setExper_context(exper_context);
		experienceVO.setExper_title(exper_title);
		experienceVO.setPicture(picture);
		dao.insert(experienceVO);
		return experienceVO;
	}

	public ExperienceVO updateExperience(String exper_no, String member_no, String cate_no, String exper_context,
			String exper_title, byte[] picture) {
		ExperienceVO experienceVO = new ExperienceVO();
		experienceVO.setExper_no(exper_no);
		experienceVO.setMember_no(member_no);
		experienceVO.setCate_no(cate_no);
		experienceVO.setExper_context(exper_context);
		experienceVO.setExper_title(exper_title);
		experienceVO.setPicture(picture);
		dao.update(experienceVO);
		return experienceVO;
	}

	public void deleteExperience(String exper_no) {
		dao.delete(exper_no);
	}

	public ExperienceVO getOneExperience(String exper_no) {
		return dao.findByPrimaryKey(exper_no);
	}

	public List<ExperienceVO> getAll() {
		return dao.getAll();
	}
	// 隨機取得3筆文章
		public List<ExperienceVO> getRandom() {
			return dao.getRandom();
		}
}
