package com.mygroup.model;

import java.util.List;

public interface MyGroupDAO_interface {
	 public void insert(MyGroupVO myGroupVO);
     public void updateScore(MyGroupVO myGroupVO);
     public void updateStatus(MyGroupVO myGroupVO);
     public void delete(String member_no,String groupgo_id);
     public List<MyGroupVO> getAll();
     public List<MyGroupVO> getMemberAttend(String member_no);
     public List<MyGroupVO> getGroupAttend(String groupgo_id);
    
//   public List<MyGroupVO> getAll(Map<String, String[]> map); 
}
