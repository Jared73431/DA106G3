package com.chatroom.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class ChatroomVO implements Serializable {
	private String type;
	private String member_no1;
	private String member_no2;
	private String chat_content;
	public ChatroomVO(String type, String member_no1, String member_no2, String chat_content) {
		super();
		this.type = type;
		this.member_no1 = member_no1;
		this.member_no2 = member_no2;
		this.chat_content = chat_content;
	}
	public ChatroomVO() {
		super();
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMember_no1() {
		return member_no1;
	}
	public void setMember_no1(String member_no1) {
		this.member_no1 = member_no1;
	}
	public String getMember_no2() {
		return member_no2;
	}
	public void setMember_no2(String member_no2) {
		this.member_no2 = member_no2;
	}
	public String getChat_content() {
		return chat_content;
	}
	public void setChat_content(String chat_content) {
		this.chat_content = chat_content;
	}
	@Override
	public String toString() {
		return "ChatroomVO [type=" + type + ", member_no1=" + member_no1 + ", member_no2=" + member_no2
				+ ", chat_content=" + chat_content + "]";
	}

}
