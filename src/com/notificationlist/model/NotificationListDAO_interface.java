package com.notificationlist.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface NotificationListDAO_interface {
	public void insert(NotificationListVO notificationListVO);

	public void update(NotificationListVO notificationListVO);

	public void delete(String note_no);

	public NotificationListVO findByPrimaryKey(String note_no);

	public List<NotificationListVO> getAll();
	
	public void read (String note_no);

	public List<NotificationListVO> getAll(Map<String, String[]> map);
	
}
