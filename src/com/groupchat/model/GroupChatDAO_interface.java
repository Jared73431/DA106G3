package com.groupchat.model;

import java.util.List;

public interface GroupChatDAO_interface {
	public void insert(GroupChatVO groupChatVO);

	public void update(GroupChatVO groupChatVO);

	public void delete(String grchat_no);

	public GroupChatVO findByPrimaryKey(String grchat_no);

	public List<GroupChatVO> getAll();
}
