package com.chatroom.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONArray;
import org.json.JSONObject;

import com.chatroom.model.ChatroomVO;
import com.chatroom.model.ChatroomredisDAO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.groupchat.model.GroupChatredisDAO;
import com.notificationlist.model.NotificationListVO;


@ServerEndpoint("/Chatroom/{memberno}")
public class ChatroomWebsocket {

	private static Map<String, Session> sessionsMap = new ConcurrentHashMap<>();//Concurrent並行,每個session都有value
	Gson gson = new Gson();

	@OnOpen
	public void onOpen(@PathParam("memberno") String memberno, Session userSession) throws IOException {
		/* save the new user in the map */
		sessionsMap.put(memberno, userSession);
		/* Sends all the connected users to the new user */
		Set<String> userNames = sessionsMap.keySet();
		Collection<Session> sessions = sessionsMap.values();
		String text = String.format("Session ID = %s, connected; userName = %s%nusers: %s", userSession.getId(),
				memberno, userNames);
		System.out.println(text);
		String rownum = String.valueOf(ChatroomredisDAO.rownum(memberno));
		JsonObject jobj = new JsonObject();
		jobj.addProperty("row", rownum);
		userSession.getAsyncRemote().sendText(jobj.toString());
		System.out.println(jobj);
		ChatroomredisDAO.readed(memberno);
		}

	@OnMessage
	public void onMessage(Session userSession, String message) {
		
		ChatroomVO chVO = gson.fromJson(message, ChatroomVO.class);
		
		String memberno1 = chVO.getMember_no1();;
		String memberno2 = chVO.getMember_no2();
		String type = chVO.getType();
		System.out.println("typr:"+type);

		if ("history".equals(type)) {
		List<String> historyData = ChatroomredisDAO.getHistoryMsg(memberno1, memberno2);
		System.out.println("historydata:"+historyData);
		String historyMsg = gson.toJson(historyData);
		ChatroomVO chvoHistory = new ChatroomVO("history", memberno1, memberno2, historyMsg);
		System.out.println(chvoHistory.toString());
		if (userSession != null && userSession.isOpen()) {
			userSession.getAsyncRemote().sendText(gson.toJson(chvoHistory));
			return;
			}
		}
		
		Session receiverSession2 = (Session) sessionsMap.get(memberno1);
		if (receiverSession2 != null && receiverSession2.isOpen()) {
			receiverSession2.getAsyncRemote().sendText(message);
			System.out.println(message);
		}
		
		
		Session receiverSession = (Session) sessionsMap.get(memberno2);
		if (receiverSession != null && receiverSession.isOpen()) {
			receiverSession.getAsyncRemote().sendText(message);
		}else {
			ChatroomredisDAO.saveChatMessage(memberno1, memberno2, message);
			ChatroomredisDAO.savewhileunonload(memberno2, message);
			return;
		}
		
		
		

		System.out.println("Message received: " + message);
		
	}
	@OnClose
	public void onClose(Session session) {
		sessionsMap.remove(session);
		System.out.println("onClose::" + session.getId());
	}

	@OnError
	public void onError(Throwable t) {
		System.out.println("onError::" + t.getMessage());
	}
}