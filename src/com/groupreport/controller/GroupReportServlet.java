package com.groupreport.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import com.groupgo.model.GroupgoService;
import com.groupgo.model.GroupgoVO;
import com.groupreport.model.*;
import com.notificationlist.model.NotificationListService;
import com.notificationlist.model.NotificationListVO;

/**
 * Servlet implementation class GroupReportServlet
 */

public class GroupReportServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("getStatus".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				Integer report_status = new Integer(req.getParameter("report_status"));
				
				GroupReportService groupreportsvc = new GroupReportService();
				List<GroupReportVO> groupreport = groupreportsvc.getStatus(report_status);
				if (groupreport == null) {
					errorMsgs.add("查無資料");
				}
				req.setAttribute("groupreport", groupreport);

				RequestDispatcher successView = req.getRequestDispatcher("/back-end/groupreport/getStatus.jsp");
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/groupreport/select_page.jsp");
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)){
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				String groupgo_id = req.getParameter("groupgo_id");
				String member_no = req.getParameter("member_no");
				String report_content = req.getParameter("report_content");
				java.sql.Timestamp report_date = java.sql.Timestamp.valueOf(req.getParameter("report_date"));
				Integer report_status = new Integer(req.getParameter("report_status"));
				GroupReportVO groupreportVO = new GroupReportVO(groupgo_id,member_no,report_date,report_content,report_status);
				
				GroupReportService groupreportsvc = new GroupReportService();
				groupreportsvc.update(groupreportVO);
				
				
				
				RequestDispatcher successView = req.getRequestDispatcher("/back-end/groupreport/listAllGroupReport.jsp");
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/groupreport/listAllGroupReport.jsp");
				failureView.forward(req, res);
			}

		}
		
		
		if ("insert".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			JSONObject output = new JSONObject();
			try {
				String groupgo_id = req.getParameter("groupgo_id");
				String member_no = req.getParameter("member_no");
//				java.sql.Timestamp report_date = new java.sql.Timestamp(System.currentTimeMillis());
				String report_content = req.getParameter("report_content");

				GroupReportService groupreportsvc = new GroupReportService();
				GroupReportVO groupreport = groupreportsvc.addGroupReport(groupgo_id, member_no,report_content);
				
				
				output.put("success", "Y");
				res.setContentType("text/plain");
				res.setCharacterEncoding("UTF-8");
				PrintWriter out = res.getWriter();
				out.write(output.toString());
				out.flush();
				out.close();
				
				
//				 req.setAttribute("groupreport", groupreport);
//				RequestDispatcher successView = req.getRequestDispatcher("/back-end/groupreport/listAllGroupReport.jsp");
//				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("檢舉過了吧");
				errorMsgs.add(e.getMessage());
				
//				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/groupreport/addGroupReport.jsp");
//				failureView.forward(req, res);
			}

			}
	}

}
