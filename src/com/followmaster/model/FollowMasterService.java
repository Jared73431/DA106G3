package com.followmaster.model;

import java.util.List;

public class FollowMasterService {
 private FollowMasterDAO_interface dao;

public FollowMasterService() {
	dao = new FollowMasterDAO();
}
 public List<FollowMasterVO> getAll(){
	 return dao.getAll();
 }
 
 public FollowMasterVO addFollow(String member_no,String master_no) {
	 FollowMasterVO followMasterVO = new FollowMasterVO();
	 followMasterVO.setMember_no(member_no);
	 followMasterVO.setMaster_no(master_no);
	 dao.insert(followMasterVO);
	 return followMasterVO;
 }
 
 public void delete(String member_no,String master_no) {
	dao.delete(member_no, master_no); 
 }
 
 public List<FollowMasterVO> getMaster(String member_no){
	 return dao.getMaster(member_no);
 }
}
