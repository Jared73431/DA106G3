package com.notificationlist.controller;

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

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.notificationlist.model.NotificationListVO;


@ServerEndpoint("/NotificationWebsocket/{memberno}")
public class NotificationWebsocket {

	private static Map<String, Session> sessionsMap = new ConcurrentHashMap<>();// Concurrent並行,每個session都有value
	Gson gson = new Gson();

	@OnOpen
	public void onOpen(@PathParam("memberno") String memberno, Session userSession) throws IOException {
		/* save the new user in the map */
		sessionsMap.put(memberno, userSession);
		/* Sends all the connected users to the new user */
		Set<String> member_nos = sessionsMap.keySet();//取得會員ID
		Collection<Session> sessions = sessionsMap.values();//取的session的集合
		String text = String.format("Session ID = %s, connected; userName = %s%n", userSession.getId(), member_nos);
		System.out.println(text);
	}

	@OnMessage
	public void onMessage(@PathParam("memberno") String memberno, String message) throws JSONException {
		//前端船的jsonso內容 Message received: {"member_no":"M000001","category_no":"CA0001","note_content":"通知內容"}
		JSONObject jsonobj = new JSONObject(message);
		//把json的會員id取出來
		String members = jsonobj.getString("member_no");
		//透過這個id找到session
		Session receiverSession = (Session) sessionsMap.get(members);
		//送message資料
		if (receiverSession != null && receiverSession.isOpen()) {
			receiverSession.getAsyncRemote().sendText(message);
		}

		System.out.println("Message received: " + message);
	}

	@OnClose
	public void onClose(Session session) {
		Set<String> userNames = sessionsMap.keySet();
		for (String userName : userNames) {
			if (sessionsMap.get(userName).equals(session)) {
				sessionsMap.remove(userName);
				break;
			}
		}
	}

	@OnError
	public void onError(Throwable t) {
		System.out.println("onError::" + t.getMessage());
	}
}