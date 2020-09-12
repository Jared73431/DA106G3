package com.groupgo.model;

import java.util.*;


public interface GroupgoDAO_interface {
	 public void insert(GroupgoVO groupgoVO);
     public void update(GroupgoVO groupgoVO);
     public void delete(String groupgo_id);
     public GroupgoVO findByPrimaryKey(String groupgo_id);
     public List<GroupgoVO> findByMaster(String master_id);
     public List<GroupgoVO> getAll();
     public Integer getScoreSum(String groupgo_id);
     public void updateScoreSum(Integer score_sum,String groupgo_id);
     public void updateCurrentPeo(Integer current_peo,String groupgo_id);
     public void updateStatus(Integer group_status,String groupgo_id);
     public List<GroupgoVO> findNear(Double lon, Double lat);

  
}
