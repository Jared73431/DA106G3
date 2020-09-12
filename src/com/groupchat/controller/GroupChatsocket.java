package com.groupchat.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.groupchat.model.GroupChatredisDAO;

@ServerEndpoint("/GroupChatsocket/{memberno}/{groupgoid}")
public class GroupChatsocket {

//private static final Set<Session> allSessions = Collections.synchronizedSet(new HashSet<Session>());
	private static final Map<String, Set<Session>> map = new Hashtable<String, Set<Session>>();

	Gson gson = new Gson();

	@OnOpen
	public void onOpen(@PathParam("memberno") String memberno, @PathParam("groupgoid") String groupgoid,
			Session userSession) throws IOException {

		System.out.println("揪團編號=" + groupgoid);
		System.out.println("會員上線:" + memberno);
		Set<Session> groupallSessions = null;
		if (map.containsKey(groupgoid)) {
			groupallSessions = map.get(groupgoid);
			groupallSessions.add(userSession);
			map.put(groupgoid, groupallSessions);
		} else {
			groupallSessions = Collections.synchronizedSet(new HashSet<Session>());
			groupallSessions.add(userSession);
			map.put(groupgoid, groupallSessions);
		}
		List<String> historyData = GroupChatredisDAO.getHistoryMsg( groupgoid);
		System.out.println(historyData);
		JSONArray jsonArray = new JSONArray(historyData);
		JsonObject jobj = new JsonObject();
		jobj.addProperty("action", "history");
		String historyMsg = gson.toJson(historyData);
		jobj.addProperty("message", historyMsg);
		userSession.getAsyncRemote().sendText(jobj.toString());
		System.out.println(jobj.toString());
	}

	@OnMessage
	public void onMessage(@PathParam("groupgoid") String groupgoid, Session userSession, String message) throws JSONException {
		Set<Session> groupallSessions = map.get(groupgoid);
		JSONObject jsonobj = new JSONObject(message);
		String memberno = jsonobj.getString("memberno");
		for (Session session : groupallSessions) {
			if (session.isOpen())
				session.getAsyncRemote().sendText(message);
		}
		GroupChatredisDAO.saveChatMessage(memberno, groupgoid, message);
		System.out.println("Message received: " + message);
	}

	@OnError
	public void onError(Session userSession, Throwable e) {

	}

	@OnClose
	public void onClose(@PathParam("groupgoid") String groupgoid, Session userSession, CloseReason reason) {
		Set<Session> hostID_allSessions = map.get(groupgoid);
		hostID_allSessions.remove(userSession);
		System.out.println(userSession.getId() + ": Disconnected: " + Integer.toString(reason.getCloseCode().getCode()));
		System.out.println("map.get(hostID).size()="+map.get(groupgoid).size());
	}
}
