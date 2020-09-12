package com.coa_follow.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coa_follow.model.Coa_FollowService;
import com.coa_follow.model.Coa_FollowVO;

public class Coa_FollowServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
	
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
//		if("getOneMember_For_Follow".equals(action)||"getOneCoach_For_Follow".equals(action)) {
//			List<String> errorMsgs = new LinkedList<String>();
//
//			req.setAttribute("errorMsgs", errorMsgs);
//			try {
//				String coa_no = req.getParameter("coa_no");
//				String member_no = req.getParameter("member_no");
//				/*************************** 2.開始查詢資料 ****************************************/
//				Coa_FollowService coa_followSvc = new Coa_FollowService();
//				List<Coa_FollowVO> list = coa_followSvc.getOneFollow(member_no, coa_no);
//
//				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
//				req.setAttribute("listFollow", list);
//				req.setAttribute("member_follow", member_no);
//				req.setAttribute("coa_no", coa_no);
//				String url = null;
//				if("getOneMember_For_Follow".equals(action)) {
//					url = "/front-end/coa_follow/listAllCoachForFollow.jsp";
//				}else if("getOneCoach_For_Follow".equals(action)){
//					url = "/front-end/coa_follow/listCoachAllCoa_Follow.jsp";
//				}
//				RequestDispatcher successView = req.getRequestDispatcher(url);
//				successView.forward(req, res);
//			}catch(Exception e) {
//				System.out.println("GG");
//			}
//		}
		if("insert".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();

			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");
			try {

				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String coa_no = req.getParameter("coa_no");
				String member_no = req.getParameter("member_no");

				Coa_FollowVO coa_followVO = new Coa_FollowVO();
				coa_followVO.setMember_no(member_no);
				coa_followVO.setCoa_no(coa_no);
				/*************************** 2.開始修改資料 *****************************************/			
			
				Coa_FollowService coa_followSvc = new Coa_FollowService();
				coa_followVO = coa_followSvc.addFollow(coa_no, member_no);
				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/	
				
				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			}catch(Exception e) {

				if("A database error occured. ORA-00001: unique constraint (DA106G3.PK_COA_FOLLOW) violated".equals(e.getMessage().trim())) {

					/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
					String coa_no = req.getParameter("coa_no");
					String member_no = req.getParameter("member_no");
					/*************************** 2.開始修改資料 *****************************************/		
					Coa_FollowService coa_followSvc = new Coa_FollowService();
					coa_followSvc.delete(member_no, coa_no);
					/*************************** 3.修改完成,準備轉交(Send the Success view) *************/

					String url = requestURL;
					RequestDispatcher successView = req.getRequestDispatcher(url);
					successView.forward(req, res);
				}
				

			}
		}
	}
}
