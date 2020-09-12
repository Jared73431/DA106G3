package com.attendrace.model;

import java.util.List;
import java.util.Map;

import com.attendrace.*;
import com.members.model.MembersVO;

public interface AttendRaceDAO_interface {
	
	public int insert(AttendRaceVO  attendRaceVO);
	public int updateBookingData(AttendRaceVO  attendRaceVO);
	public boolean delete(String attendRaceVO);
	public AttendRaceVO findByPrimaryKey(String attendRaceVO);
	public List<AttendRaceVO> getAll();
	public List<AttendRaceVO> getByMem(String member_no);
//	public List<AttendRaceVO> getAll(Map<String, String[]> map);
}