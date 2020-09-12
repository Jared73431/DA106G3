package com.coa_follow.model;

import java.util.List;

public class Coa_FollowService {
	private Coa_FollowDAO_interface dao;
	
	public Coa_FollowService() {
		dao = new Coa_FollowDAO();
	}
	
	public Coa_FollowVO addFollow(String coa_no,String member_no) {
		Coa_FollowVO coa_followVO = new Coa_FollowVO();
		
		coa_followVO.setCoa_no(coa_no);
		coa_followVO.setMember_no(member_no);
		
		dao.insert(coa_followVO);
		
		return coa_followVO;
	}
	
	public List<Coa_FollowVO> getOneFollow(String member_no,String coa_no){
		return dao.findByPrimaryKey(member_no,coa_no);
	}
	
	public List<Coa_FollowVO> getAll(){
		return dao.getAll();
	}
	
	public void delete(String member_no,String coa_no) {
		 dao.delete(member_no, coa_no);
	}
	
	public int check(String member_no,String coa_no) {
		return dao.check(member_no, coa_no);
	}
}
