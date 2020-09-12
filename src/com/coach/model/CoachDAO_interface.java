package com.coach.model;

import java.io.File;
import java.util.*;

public interface CoachDAO_interface {
	public void insert(CoachVO coachVO);
	public void update(CoachVO coachVO);
	public void delete(String coa_no);
	public CoachVO findByPrimaryKey(String coa_no);
	public CoachVO findByMember(String member_no);
	public String checkStatus(String member_no);
	public void punishUpdate(String coa_no);
	public List<CoachVO> getAll();
	public byte[] getPicture(String member_no,String licence);
	public void clearLicense(String member_no);
}
