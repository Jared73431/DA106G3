package com.followmaster.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.followmaster.model.*;
import com.mygroup.model.MyGroupVO;
import com.mygroup.model.MygroupService;

public class FollowMasterServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("getMaster".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			String member_no = req.getParameter("member_no");
			if (member_no == null || (member_no.trim()).length() == 0) {
				errorMsgs.add("請輸入編號");
			}

			FollowMasterService followsvc = new FollowMasterService();
			List<FollowMasterVO> master = followsvc.getMaster(member_no);
			if (master == null) {
				errorMsgs.add("查無資料");
			}
			req.setAttribute("master", master);
			try {
				RequestDispatcher successView = req.getRequestDispatcher("/front-end/followmaster/getMaster.jsp");
				successView.forward(req, res);
			} catch (Exception e) {
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/followmaster/getMaster.jsp");
				failureView.forward(req, res);
			}
		}
		if (("insert").equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				String member_no = req.getParameter("member_no");
				if (member_no == null)
					errorMsgs.add("會員編號不得為空");

				
				String master_no = req.getParameter("master_no");
				if (master_no == null)
					errorMsgs.add("團主編號不得為空");

				
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/followmaster/getMaster.jsp");
					failureView.forward(req, res);
					return;
				}
				FollowMasterService followsvc = new FollowMasterService();
				FollowMasterVO followMasterVO = followsvc.addFollow(member_no, master_no);
//				RequestDispatcher successView = req.getRequestDispatcher("/front-end/followmaster/listAllFollow.jsp");
//				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/followmaster/getMaster.jsp");
				failureView.forward(req, res);
			}
		}
		if ("delete".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String member_no = req.getParameter("member_no");
				String master_no = req.getParameter("master_no");
				FollowMasterService followsvc = new FollowMasterService();
				followsvc.delete(member_no, master_no);

				RequestDispatcher successView = req.getRequestDispatcher("/front-end/followmaster/getMaster.jsp");
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/followmaster/getMaster.jsp");
				failureView.forward(req, res);
			}
		}
	}

}
