package com.mygroup.model;

import java.util.List;


public class MygroupService {
	private MyGroupDAO_interface dao;

	public MygroupService() {
		dao = new MyGroupDAO();
	}
	public List<MyGroupVO> getAll() {
		return dao.getAll();
	}
	
	public MyGroupVO addGroup(String groupgo_id,String member_no) {
		MyGroupVO mygroupVO = new MyGroupVO();
		mygroupVO.setGroupgo_id(groupgo_id);
		mygroupVO.setMember_no(member_no);
		
		dao.insert(mygroupVO);
		return mygroupVO;
	}
	
	public void delete(String member_no,String groupgo_id) {
		dao.delete(member_no, groupgo_id);
	}
	

     public void updateScore (MyGroupVO mygroupVO) {
    	 
    	 dao.updateScore(mygroupVO);
    	 
    	
     }
     public void updateStatus(MyGroupVO mygroupVO) {
    	 dao.updateStatus(mygroupVO);
 
     }
     
     public List<MyGroupVO> getMemberAttend(String member_no){
    	 return dao.getMemberAttend(member_no);
     }
     public List<MyGroupVO> getGroupAttend(String groupgo_id){
    	 return dao.getGroupAttend(groupgo_id);
     }
	

}
