package com.attendrace.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.members.model.MembersVO;

public class AttendRaceService {

	private AttendRaceDAO_interface dao;

	public AttendRaceService() {
		dao = new AttendRaceJNDIDAO();
	}

	public AttendRaceVO addAttendRace(String member_no, Integer race_type, String race_name, Date fin_date, 
			String fin_time, Integer fin_rank, byte[] fin_record, String fin_remark) {
		AttendRaceVO attendRaceVO = new AttendRaceVO();

		attendRaceVO.setMember_no(member_no);
		attendRaceVO.setRace_type(race_type);
		attendRaceVO.setRace_name(race_name);
		attendRaceVO.setFin_date(fin_date);
		attendRaceVO.setFin_time(fin_time);
		attendRaceVO.setFin_rank(fin_rank);
		attendRaceVO.setFin_record(fin_record);
		attendRaceVO.setFin_remark(fin_remark);
		dao.insert(attendRaceVO);

		return attendRaceVO;
	}

	public AttendRaceVO updateAttendRace(String attend_no, String member_no, Integer race_type, String race_name,
			String fin_time, Integer fin_rank, String fin_remark) {

		AttendRaceVO attendRaceVO = new AttendRaceVO();

		attendRaceVO.setAttend_no(attend_no);
		attendRaceVO.setMember_no(member_no);
		attendRaceVO.setRace_type(race_type);
		attendRaceVO.setRace_name(race_name);
//		attendRaceVO.setFin_date(fin_date);
		attendRaceVO.setFin_time(fin_time);
		attendRaceVO.setFin_rank(fin_rank);
//		attendRaceVO.setFin_record(fin_record);
		attendRaceVO.setFin_remark(fin_remark);
		dao.updateBookingData(attendRaceVO);

		return attendRaceVO;
	}

	public boolean deleteAttendRace(String attend_no) {
		return dao.delete(attend_no);
	}

	public AttendRaceVO getOneAttendRace(String attend_no) {
		return dao.findByPrimaryKey(attend_no);
	}

	public List<AttendRaceVO> getAll() {
		return dao.getAll();
	}
	
	public List<AttendRaceVO> getByMem(String member_no){
		return dao.getByMem(member_no);
	}
}
