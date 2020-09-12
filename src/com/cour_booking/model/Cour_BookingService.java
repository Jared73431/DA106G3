package com.cour_booking.model;

import java.util.List;

public class Cour_BookingService {
	private Cour_BookingDAO dao;

	public Cour_BookingService() {
		dao = new Cour_BookingDAO();
	}

	public Cour_BookingVO addBooking(String cour_no, String coa_no, String member_no, Integer choose_mode) {
		Cour_BookingVO cour_bookingVO = new Cour_BookingVO();

		cour_bookingVO.setCour_no(cour_no);
		cour_bookingVO.setCoa_no(coa_no);
		cour_bookingVO.setMember_no(member_no);
		cour_bookingVO.setChoose_mode(choose_mode);

		dao.insert(cour_bookingVO);

		return cour_bookingVO;
	}

	public Cour_BookingVO updateBooking(String cour_no, String coa_no, String member_no, Integer coa_comf,
			Integer trainee_comf, Integer coa_ci, Integer trainee_ci, Integer cour_score, String cour_option,
			Integer choose_mode, String booking_no) {
		Cour_BookingVO cour_bookingVO = new Cour_BookingVO();

		cour_bookingVO.setCour_no(cour_no);
		cour_bookingVO.setCoa_no(coa_no);
		cour_bookingVO.setMember_no(member_no);
		cour_bookingVO.setCoa_comf(coa_comf);
		cour_bookingVO.setTrainee_comf(trainee_comf);
		cour_bookingVO.setCoa_ci(coa_ci);
		cour_bookingVO.setTrainee_ci(trainee_ci);
		cour_bookingVO.setCour_score(cour_score);
		cour_bookingVO.setCour_option(cour_option);
		cour_bookingVO.setChoose_mode(choose_mode);
		cour_bookingVO.setBooking_no(booking_no);

		dao.update(cour_bookingVO);

		return cour_bookingVO;
	}

	public Cour_BookingVO getOneBooking(String booking_no) {
		return dao.findByPrimaryKey(booking_no);
	}

	public List<Cour_BookingVO> getListBooking(String booking_no, String cour_no, String coa_no, String member_no) {
		return dao.findByPrimaryKey(booking_no, cour_no, coa_no, member_no);
	}

	public List<Cour_BookingVO> getAll() {
		return dao.getAll();
	}

	public void coachCheckin(String booking_no) {
		dao.coachCheckin(booking_no);
	}

	public void traineeCheckin(String booking_no) {
		dao.traineeCheckin(booking_no);
	}
	
	public void comfOnchange(String booking_no,Integer coa_comf,Integer trainee_comf,String cour_no) {
		dao.comfOnchange(booking_no, coa_comf, trainee_comf,cour_no);
	}
	
	public void passForCourseStatus(String cour_no) {
		dao.passForCourseStatus(cour_no);
	}
	
	public boolean refund (String member_no,Integer real_blance,Integer deposit,Integer withdraw,String remark) {
		return dao.refund(member_no, real_blance, deposit, withdraw, remark);
	}

}
