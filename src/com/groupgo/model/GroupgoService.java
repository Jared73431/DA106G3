package com.groupgo.model;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;


public class GroupgoService {

	private GroupgoDAO_interface dao;
	private Map<Integer, List<GroupgoVO>> groupMap;

	public GroupgoService() {
		dao = new GroupgoDAO();
		
		List<GroupgoVO> groupgoList = dao.getAll();
		groupMap = new HashMap<>();
		groupMap = groupgoList.stream().collect(Collectors.groupingBy(GroupgoVO::getGroup_status));
	}

	public GroupgoVO addGroupgo(String master_id, String group_name, Timestamp group_date, String place, Double lon,
			Double lat, Integer people_num, String budget, String group_content,byte[]photo, Timestamp deadline) {
		GroupgoVO groupgoVO = new GroupgoVO();

		groupgoVO.setMaster_id(master_id);
		groupgoVO.setGroup_name(group_name);
		groupgoVO.setGroup_date(group_date);
		groupgoVO.setPlace(place);
		groupgoVO.setLon(lon);
		groupgoVO.setLat(lat);
		groupgoVO.setPeople_num(people_num);
		groupgoVO.setBudget(budget);
		groupgoVO.setGroup_content(group_content);
		groupgoVO.setPhoto(photo);
		groupgoVO.setDeadline(deadline);
		dao.insert(groupgoVO);
		
		return groupgoVO;
	}
	
	public GroupgoVO updateGroupgo(String groupgo_id,String group_name,String master_id, Timestamp group_date, String place, Double lon,
			Double lat, Integer group_status, Integer people_num, String budget, String group_content, byte[] photo, Timestamp deadline) {
		GroupgoVO groupgoVO = new GroupgoVO();

		groupgoVO.setGroupgo_id(groupgo_id);
		groupgoVO.setGroup_name(group_name);
		groupgoVO.setMaster_id(master_id);
		groupgoVO.setGroup_date(group_date);
		groupgoVO.setPlace(place);
		groupgoVO.setLon(lon);
		groupgoVO.setLat(lat);
		groupgoVO.setGroup_status(group_status);
		groupgoVO.setPeople_num(people_num);
		groupgoVO.setBudget(budget);
		groupgoVO.setGroup_content(group_content);
		groupgoVO.setPhoto(photo);
		groupgoVO.setDeadline(deadline);
		dao.update(groupgoVO);
		
		return groupgoVO;	
	}
	
	public void deleteGroupgo(String groupgo_id) {
		dao.delete(groupgo_id);
	}

	public GroupgoVO getOneGroup(String groupgo_id) {
		return dao.findByPrimaryKey(groupgo_id);
	}

	public List<GroupgoVO> getMasterGroup(String master_id) {
		return dao.findByMaster(master_id);
	}
	public List<GroupgoVO> getAll() {
		return dao.getAll();
	}
	
	public Integer getScoreSum(String groupgo_id) {
		return dao.getScoreSum(groupgo_id);
	}

	public void updateScoreSum(Integer score_sum,String groupgo_id) {
		dao.updateScoreSum(score_sum,groupgo_id);
	}
	 public void updateCurrentPeo(Integer current_peo,String groupgo_id) {
		 dao.updateCurrentPeo(current_peo,groupgo_id);
	 }
	 public void updateStatus(Integer group_status,String groupgo_id) {
		 dao.updateStatus(group_status,groupgo_id);
	 }
	 public List<GroupgoVO> findNear(Double lon, Double lat) {
		 return dao.findNear(lon, lat);
	 }
	 
	 public List<GroupgoVO> getIng() {		
		return groupMap.get(1);
	}
	 public List<GroupgoVO> getSoon() {		
		return groupMap.get(2);
	}
	 public List<GroupgoVO> getEd() {		
		return groupMap.get(3);
	}

}