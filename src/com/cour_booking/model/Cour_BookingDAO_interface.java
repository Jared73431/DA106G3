package com.cour_booking.model;

import java.util.*;

public interface Cour_BookingDAO_interface {
	public void insert(Cour_BookingVO cour_BookingVO);
	public void update(Cour_BookingVO cour_BookingVO);
	public void coachCheckin(String booking_no);
	public void traineeCheckin(String booking_no);
	public void comfOnchange(String booking_no,Integer coa_comf,Integer trainee_comf,String cour_no);
	public void passForCourseStatus(String cour_no);
	public List<Cour_BookingVO> findByPrimaryKey(String booking_no,String cour_no ,String coa_no ,String member_no);
	public List<Cour_BookingVO> getAll();
	public Cour_BookingVO findByPrimaryKey(String booking_no);
	public boolean refund (String member_no,Integer real_blance,Integer deposit,Integer withdraw,String remark);
}
