package com.mygroup.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.groupgo.model.GroupgoService;
import com.groupgo.model.GroupgoVO;
import com.mygroup.model.MyGroupVO;
import com.mygroup.model.MygroupService;

public class MyGroupServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);

	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("getMemberAttend".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			HttpSession session = req.getSession();
			String member_no = (String)session.getAttribute("member_no");
			
			MygroupService mygroupsvc = new MygroupService();
			List<MyGroupVO> mygroupList = mygroupsvc.getMemberAttend(member_no);

			req.setAttribute("mygroupList", mygroupList);
			
			
			String master_id = (String)session.getAttribute("member_no");

			GroupgoService groupgoSvc = new GroupgoService();
			List<GroupgoVO> groupgoList = groupgoSvc.getMasterGroup(master_id);
			req.setAttribute("groupgoList", groupgoList);
			
			try {
				RequestDispatcher successView = req.getRequestDispatcher("/front-end/groupgo/listMyGroup.jsp");
				successView.forward(req, res);
			} catch (Exception e) {
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/mygroup/select_page.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getGroupAttend".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				String groupgo_id = req.getParameter("groupgo_id");

				MygroupService mygroupsvc = new MygroupService();
				List<MyGroupVO> groupmember = mygroupsvc.getGroupAttend(groupgo_id);
				if (groupmember == null) {
					errorMsgs.add("查無資料");
				}
				req.setAttribute("groupmember", groupmember);

				RequestDispatcher successView = req.getRequestDispatcher("/back-end/mygroup/getGroupAttend.jsp");
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/mygroup/select_page.jsp");
				failureView.forward(req, res);
			}
		}

		if ("updateScore".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				JSONObject output = new JSONObject();
				String groupgo_id = req.getParameter("groupgo_id");
				String member_no = req.getParameter("member_no");
				
				Integer score = null;
				try {
					score = new Integer(req.getParameter("score").trim());
					
				} catch (NumberFormatException e) {
					output.put("success", "N");
				}
			if(0 <= score && score <=10) {
				MyGroupVO mygroupVO = new MyGroupVO();
				mygroupVO.setGroupgo_id(groupgo_id);
				mygroupVO.setMember_no(member_no);
				mygroupVO.setScore(score);

				MygroupService mygroupsvc = new MygroupService();
				mygroupsvc.updateScore(mygroupVO);
				
				GroupgoService groupgosvc = new GroupgoService();
				Integer score_sum = groupgosvc.getScoreSum(groupgo_id);
				groupgosvc.updateScoreSum(score_sum,groupgo_id);
				
				
				output.put("success", "Y");
			}else {
				output.put("success", "N");
			}
				res.setContentType("text/plain");
				res.setCharacterEncoding("UTF-8");
				PrintWriter out = res.getWriter();
				out.write(output.toString());
				out.flush();
				out.close();

//				String url = "/mygroup/mygroup.do?action=getMemberAttend&member_no="+member_no;
//				List<MyGroupVO> mygroup = mygroupsvc.getMemberAttend(member_no);
//				req.setAttribute("mygroup", mygroup);
//				RequestDispatcher successView = req.getRequestDispatcher(url);
//				successView.forward(req, res);

			} catch (Exception e) {

				errorMsgs.add("錯誤:" + e.getMessage() + e.getStackTrace());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/mygroup/select_page.jsp");
				failureView.forward(req, res);
			}
		}

		if ("updateStatus".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				String groupgo_id = req.getParameter("groupgo_id");
				String member_no = req.getParameter("member_no");

				Integer mygroup_status = new Integer(req.getParameter("mygroup_status"));

				MyGroupVO mygroupVO = new MyGroupVO();
				mygroupVO.setGroupgo_id(groupgo_id);
				mygroupVO.setMember_no(member_no);
				mygroupVO.setMygroup_status(mygroup_status);

				MygroupService mygroupsvc = new MygroupService();
				mygroupsvc.updateStatus(mygroupVO);

				String url = "/back-end/mygroup/getGroupAttend.jsp";
				List<MyGroupVO> groupmember = mygroupsvc.getGroupAttend(groupgo_id);
				req.setAttribute("groupmember", groupmember);
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {

				errorMsgs.add("錯誤:" + e.getMessage() + e.getStackTrace());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/mygroup/select_page.jsp");
				failureView.forward(req, res);
			}
		}

		if ("insert".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				String member_no = req.getParameter("member_no");
				
				String groupgo_id = req.getParameter("groupgo_id");			
				

				GroupgoService groupgosvc = new GroupgoService();
				if(groupgosvc.getOneGroup(groupgo_id).getMaster_id().equals(member_no)) {
					errorMsgs.add("你已經是團主了");
				}else if(groupgosvc.getOneGroup(groupgo_id).getCurrent_peo() >= groupgosvc.getOneGroup(groupgo_id).getPeople_num()){
					errorMsgs.add("人數滿囉!");
				}else {
					
				MygroupService mygroupsvc = new MygroupService();
				MyGroupVO mygroup = mygroupsvc.addGroup(groupgo_id, member_no);
				
				GroupgoService groupgoSvc = new GroupgoService();
				Integer current_peo  = new Integer(groupgoSvc.getOneGroup(groupgo_id).getCurrent_peo()) +1;
				groupgoSvc.updateCurrentPeo(current_peo, groupgo_id);
				}
		
				
			} catch (Exception e) {		
//				errorMsgs.add(e.getMessage());
				errorMsgs.add("已經加入過了吧~");
				
//				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/mygroup/addGroup.jsp");
//				failureView.forward(req, res);
			}finally{
				JSONObject obj = new JSONObject();
				try {
					obj.put("err",errorMsgs);
				} catch (JSONException e1) {
				
					e1.printStackTrace();
				}
				
//				JSONArray array = new JSONArray();
//				array.put(obj);
		
				
				res.setContentType("text/html;charset=UTF-8");
				PrintWriter out = res.getWriter();
				out.write(obj.toString());
				out.flush();
				out.close();
			}
		}

		if ("delete".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String groupgo_id = req.getParameter("groupgo_id");
				String member_no = req.getParameter("member_no");
				MygroupService mygroupsvc = new MygroupService();
				mygroupsvc.delete(member_no, groupgo_id);

				String url = "/front-end/mygroup/select_page.jsp";
//				List<MyGroupVO> mygroup = mygroupsvc.getMemberAttend(member_no);
//				req.setAttribute("mygroup", mygroup);
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/mygroup/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("statusCancel".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			JSONObject output = new JSONObject();
			try {				
				String groupgo_id = req.getParameter("groupgo_id");
				String member_no = req.getParameter("member_no");

				Integer mygroup_status = new Integer(req.getParameter("mygroup_status"));

				MyGroupVO mygroupVO = new MyGroupVO();
				mygroupVO.setGroupgo_id(groupgo_id);
				mygroupVO.setMember_no(member_no);
				mygroupVO.setMygroup_status(mygroup_status);

				MygroupService mygroupsvc = new MygroupService();
				mygroupsvc.updateStatus(mygroupVO);
				GroupgoService groupgoSvc = new GroupgoService();
				Integer current_peo  = new Integer(groupgoSvc.getOneGroup(groupgo_id).getCurrent_peo()) -1;
				groupgoSvc.updateCurrentPeo(current_peo, groupgo_id);
				
				output.put("success", "Y");
				
				res.setContentType("text/plain");
				res.setCharacterEncoding("UTF-8");
				PrintWriter out = res.getWriter();
				out.write(output.toString());
				out.flush();
				out.close();

//				String url = "/mygroup/mygroup.do?action=getMemberAttend&member_no="+member_no;
//				List<MyGroupVO> groupmember = mygroupsvc.getGroupAttend(groupgo_id);
//				req.setAttribute("groupmember", groupmember);
//				RequestDispatcher successView = req.getRequestDispatcher(url);
//				successView.forward(req, res);

			} catch (Exception e) {

				errorMsgs.add("錯誤:" + e.getMessage() + e.getStackTrace());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/mygroup/select_page.jsp");
				failureView.forward(req, res);
			}
		}
	}

}
