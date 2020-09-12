package com.notificationlist.model;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;



public class NotificationListService {
	private NotificationListDAO_interface dao;

	public NotificationListService() {
		dao = new NotificationListDAO();
	}

	public NotificationListVO addNotification(String member_no, String category_no, String note_content) {
		NotificationListVO notificationListVO = new NotificationListVO();
		notificationListVO.setMember_no(member_no);
		notificationListVO.setCategory_no(category_no);
		notificationListVO.setNote_content(note_content);
		dao.insert(notificationListVO);
		return notificationListVO;

	}

	public NotificationListVO updateNotification(String note_no, String member_no, String category_no,
			String note_content) {
		NotificationListVO notificationListVO = new NotificationListVO();
		notificationListVO.setNote_no(note_no);
		notificationListVO.setMember_no(member_no);
		notificationListVO.setCategory_no(category_no);
		notificationListVO.setNote_content(note_content);
		dao.update(notificationListVO);
		return notificationListVO;
	}

	public void deleteNotification(String note_no) {
		dao.delete(note_no);
	}

	public NotificationListVO getOneNotification(String note_no) {
		return dao.findByPrimaryKey(note_no);
	}
	
	public List<NotificationListVO> getAll() {
		return dao.getAll();
	}
	public List<NotificationListVO> getAll(Map<String, String[]> map) {
		return dao.getAll(map);
	}
	public void readed(String note_no) {
		dao.read(note_no);
	}
	public NotificationListVO getnote(HttpServletRequest req) {
		String member_no= req.getParameter("member_no");
		String category_no = req.getParameter("category_no");
		String note_content = req.getParameter("note_content");
		
		
		NotificationListVO noteVO = new NotificationListVO();
		noteVO.setMember_no(member_no);
		noteVO.setCategory_no(category_no);
		noteVO.setNote_content(note_content);
		
		return noteVO;
	}
}
