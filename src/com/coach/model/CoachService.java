package com.coach.model;

import java.util.List;

public class CoachService {
	private CoachDAO_interface dao;

	public CoachService() {
		dao = new CoachDAO();
	}

	public CoachVO addCoach(String member_no, String coa_info, String service_area, String coa_skill1,
			byte[] coa_licence1, String coa_skill2, String coa_skill3) {
		CoachVO coachVO = new CoachVO();

		coachVO.setMember_no(member_no);
		coachVO.setCoa_info(coa_info);
		coachVO.setService_area(service_area);
		coachVO.setCoa_skill1(coa_skill1);
		coachVO.setCoa_licence1(coa_licence1);
		coachVO.setCoa_skill2(coa_skill2);
		coachVO.setCoa_skill3(coa_skill3);

		

		dao.insert(coachVO);

		return coachVO;
	}

	public CoachVO updateCoach(String member_no, String coa_info, String service_area, String coa_skill1,
			byte[] coa_licence1, String coa_skill2, byte[] coa_licence2, String coa_skill3, byte[] coa_licence3,
			Integer coa_status, String coa_no) {
		CoachVO coachVO = new CoachVO();
		coachVO.setMember_no(member_no);
		coachVO.setCoa_info(coa_info);
		coachVO.setService_area(service_area);
		coachVO.setCoa_skill1(coa_skill1);
		coachVO.setCoa_licence1(coa_licence1);
		coachVO.setCoa_skill2(coa_skill2);
		coachVO.setCoa_licence2(coa_licence2);
		coachVO.setCoa_skill3(coa_skill3);
		coachVO.setCoa_licence3(coa_licence3);
		coachVO.setCoa_status(coa_status);
		coachVO.setCoa_no(coa_no);

		dao.update(coachVO);

		return coachVO;

	}

	public void delete(String coa_no) {
		dao.delete(coa_no);
	}

	public CoachVO getOneCoach(String coa_no) {
		return dao.findByPrimaryKey(coa_no);
	}
	
	public CoachVO getOneCoachByMember(String member_no) {
		return dao.findByMember(member_no);
	}
	
	public String getCheckStatus(String member_no) {
		return dao.checkStatus(member_no);
	}

	public List<CoachVO> getAll() {
		return dao.getAll();
	}
	
	public byte[] getPicture(String coa_no,String licence) {
		return dao.getPicture(coa_no, licence);
	}
	public void punishUpdate(String coa_no) {
		dao.punishUpdate(coa_no);
	}
	
	public void clearLicense(String member_no) {
		dao.clearLicense(member_no);
	}
	
}
