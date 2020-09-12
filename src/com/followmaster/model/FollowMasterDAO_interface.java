package com.followmaster.model;

import java.util.List;


public interface FollowMasterDAO_interface {
	public void insert(FollowMasterVO followMasterVO);
//    public void update(FollowMasterVO groupgoVO);
    public void delete(String member_no, String master_no);
    public List<FollowMasterVO> getMaster(String member_no);
    public List<FollowMasterVO> getAll();

//  public List<GroupgoVO> getAll(Map<String, String[]> map); 
}
