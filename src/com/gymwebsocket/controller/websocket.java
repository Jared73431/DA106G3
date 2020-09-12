package com.gymwebsocket.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONException;
import com.gymwebsocket.model.*;

@WebListener()
@ServerEndpoint("/echo2")
public class websocket implements ServletContextAttributeListener,ServletContextListener {
	private static final Set<Session> connectedSessions = Collections.synchronizedSet(new HashSet<>());
	// 共同變數存取需要注意同步的問題
	static String gym_data;
	/*
	 * 如果想取得HttpSession與ServletContext必須實作
	 * ServerEndpointConfig.Configurator.modifyHandshake()，
	 * 參考https://stackoverflow.com/questions/21888425/accessing-servletcontext-and-httpsession-in-onmessage-of-a-jsr-356-serverendpoint
	 */	
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		ServletContextListener.super.contextInitialized(sce);
		try {
			// 藉由起始監聽器觸發方法，避免Servlet啟動太慢拿不到值的問題
			gym_data = GymCount.count();
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("來自ServletContext監聽器啟動後執行方法:"+gym_data);
	}

	
	@OnOpen
	public void onOpen(Session userSession){
		connectedSessions.add(userSession);
		userSession.getAsyncRemote().sendText(gym_data);
		String text = String.format("Session ID = %s, connected;", userSession.getId());
		System.out.println(text);
	}

	@Override
	public void attributeReplaced(ServletContextAttributeEvent event) {
		// TODO Auto-generated method stub
		ServletContextAttributeListener.super.attributeReplaced(event);
		String attr = event.getName();
		if(attr == "gym_data") {
			// 鎖定避免與進入使用者衝突
			synchronized (gym_data) {
			gym_data = event.getValue().toString();
			}
		}
		onMessage(gym_data);
	}



	@OnMessage
	public void onMessage(String message) {
		for (Session session : connectedSessions) {
			if (session.isOpen())
				session.getAsyncRemote().sendText(message);
		}
		System.out.println("Message received: " + message );
	}

	@OnClose
	public void onClose(Session userSession, CloseReason reason) {
		connectedSessions.remove(userSession);
		String text = String.format("session ID = %s, disconnected; close code = %d; reason phrase = %s",
				userSession.getId(), reason.getCloseCode().getCode(), reason.getReasonPhrase());
		System.out.println(text);
	}

	@OnError
	public void onError(Session userSession, Throwable e) {
		System.out.println("Error: " + e.toString());
	}
}
