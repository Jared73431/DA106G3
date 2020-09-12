package com.groupchat.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class GroupChatVO implements Serializable {
	private String type;
	private String groupgo_id;
	private String chatmen_id;
	private String chat_con;
	public GroupChatVO(String type, String groupgo_id, String chatmen_id, String chat_con) {
		super();
		this.type = type;
		this.groupgo_id = groupgo_id;
		this.chatmen_id = chatmen_id;
		this.chat_con = chat_con;
		
	}
	public GroupChatVO() {
		super();
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getGroupgo_id() {
		return groupgo_id;
	}
	public void setGroupgo_id(String groupgo_id) {
		this.groupgo_id = groupgo_id;
	}
	public String getChatmen_id() {
		return chatmen_id;
	}
	public void setChatmen_id(String chatmen_id) {
		this.chatmen_id = chatmen_id;
	}
	public String getChat_con() {
		return chat_con;
	}
	public void setChat_con(String chat_con) {
		this.chat_con = chat_con;
	}
	

}
