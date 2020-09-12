package com.gymwebsocket.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.json.JSONArray;
import org.json.JSONException;

import com.gymwebsocket.model.*;

public class GymcountServlet extends HttpServlet {
	Timer timer;
	Calendar cal;
	// 使用context裝載資料
	ServletContext context;
	public void destroy() {
		timer.cancel();
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out = res.getWriter();
		out.print(context.getAttribute("gym_data"));
	}
	
	public void init() throws ServletException {
		timer = new Timer();
		TimerTask task = new TimerTask() {

			public void run() {
				context = getServletContext();
				try {
					String gym_data = GymCount.count();
					context.setAttribute("gym_data", gym_data);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 

			}
		};
		timer.schedule(task, 0, 60 * 1000);
	}
}