package com.members.model;

import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.experience.model.ExperienceVO;
import com.notificationlist.model.NotificationListVO;

import java.sql.Date;

public class MembersService {

	private MembersDAO_interface dao;

	public MembersService() {
		dao = new MembersDAO();
	}

	public MembersVO addMembers(String known,
			Integer sexual, String mem_name, String mem_account, String email, String mem_password, Date birthday, String phone, String mem_zip, String address, Double height, byte[] picture) {

		MembersVO membersVO = new MembersVO();

//		membersVO.setCoa_qualifications(coa_qualifications);
		membersVO.setKnown(known);
		membersVO.setSexual(sexual);
		membersVO.setMem_name(mem_name);
		membersVO.setMem_account(mem_account);
//		membersVO.setMem_status(mem_status);
		membersVO.setEmail(email);
		membersVO.setMem_password(mem_password);
		membersVO.setBirthday(birthday);
		membersVO.setPhone(phone);
		membersVO.setMem_zip(mem_zip);
		membersVO.setAddress(address);
//		membersVO.setReal_blance(real_blance);
		membersVO.setHeight(height);
		//membersVO.setReg_date(reg_date);
		membersVO.setPicture(picture);
//		membersVO.setLicense(license);
		dao.insert(membersVO);

		return membersVO;
	}

	public MembersVO updateMembers(String member_no, Integer coa_qualifications, String known,
			Integer sexual, String mem_name, String mem_account, Integer mem_status, String email, String mem_password, Date birthday, String phone, String mem_zip, String address, Integer real_blance, Double height, byte[] picture) {

		MembersVO membersVO = new MembersVO();

		membersVO.setMember_no(member_no);
		membersVO.setCoa_qualifications(coa_qualifications);
		membersVO.setKnown(known);
		membersVO.setSexual(sexual);
		membersVO.setMem_name(mem_name);
		membersVO.setMem_account(mem_account);
		membersVO.setMem_status(mem_status);
		membersVO.setEmail(email);
		membersVO.setMem_password(mem_password);
		membersVO.setBirthday(birthday);
		membersVO.setPhone(phone);
		membersVO.setMem_zip(mem_zip);
		membersVO.setAddress(address);
		membersVO.setReal_blance(real_blance);
		membersVO.setHeight(height);
	//	membersVO.setReg_date(reg_date);
		membersVO.setPicture(picture);
//		membersVO.setLicense(license);
		dao.update(membersVO);
		return membersVO;
	}

	public boolean deleteMembers(String member_no) {
		return dao.delete(member_no);
	}

	public MembersVO getOneMembers(String member_no) {
		return dao.findByPrimaryKey(member_no);
	}

	public List<MembersVO> getAll() {
		return dao.getAll();
	}
	
	public List<MembersVO> getNoStatus(Integer mem_status){
		return dao.getNoStatus(mem_status);
	}
	
	public MembersVO getOneMemberByAccount(String mem_account) {
		return dao.getOneMemberByAccount(mem_account);
	}
	
	public void updateMembersRealBlance(Integer real_blance, String member_no) {
		dao.updateMembersRealBlance(real_blance, member_no);
	}
	
	public void updateLicense(Integer coa_qualifications, byte[] license, String member_no) {		
		dao.updateCoa(coa_qualifications, license, member_no);
		
	}
	
	public void updateStatus(Integer mem_status, String member_no) {		
		dao.updateStatus(mem_status, member_no);
		
	}
	
	// 勝瑜加入抓通知
	public Set<NotificationListVO> getNotesByMemberno(String member_no) {
		return dao.getNotesByMember(member_no);
	}
	// 勝瑜加入抓心得
	public Set<ExperienceVO> getExperByMemberno(String member_no) {
		return dao.getExoerByMember(member_no);
	}
	
}
