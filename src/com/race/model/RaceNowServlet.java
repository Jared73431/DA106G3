package com.race.model;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.json.JSONArray;
import org.json.JSONException;

import com.gymwebsocket.model.*;

public class RaceNowServlet extends HttpServlet {
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
	}
	
	public void init() throws ServletException {
		timer = new Timer();
		TimerTask task = new TimerTask() {

			public void run() {
		    					
				String raceinform = null;
					try {
						raceinform = JsoupRace.raceinform("");
						// 放在try裡面避免傳入空值
						RaceService raceSvc = new RaceService();
						raceSvc.updateRace("R001", "IC02", "NOW", raceinform);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}				
			}
		};
		timer.schedule(task, 0, 24 * 60 * 60 * 1000);
	}
}