package com.attendrace.controller;

import java.io.*;
import java.sql.Timestamp;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;

import com.attendrace.model.*;
import com.members.model.*;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class AttendRaceServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接受請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("attend_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入比賽編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/attendrace/select_page_attend.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				String attend_no = null;
				try {
					attend_no = new String(str);
				} catch (Exception e) {
					errorMsgs.add("比賽編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/attendrace/select_page_attend.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				AttendRaceService attendraceSvc = new AttendRaceService();
				AttendRaceVO attendRaceVO = attendraceSvc.getOneAttendRace(attend_no);
				if (attendRaceVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/attendrace/select_page_attendrace.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("attendRaceVO", attendRaceVO); // 資料庫取出的empVO物件,存入req
				String url = "/attendrace/listOneAttend.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/attendrace/select_page_attend.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("getOne_For_Update".equals(action)) { // 來自listAllEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
//			try {
				/***************************1.接收請求參數****************************************/
				String attend_no = new String(req.getParameter("attend_no"));
				
				/***************************2.開始查詢資料****************************************/
				AttendRaceService attendraceSvc = new AttendRaceService();
				AttendRaceVO attendRaceVO = attendraceSvc.getOneAttendRace(attend_no);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("attendRaceVO", attendRaceVO);         // 資料庫取出的empVO物件,存入req
				String url = "/front-end/attendrace/update_attend_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_attend_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
//			} catch (Exception e) {
//				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/attendrace/listAllAttend.jsp");
//				failureView.forward(req, res);
//			}
		}
		
		
		if ("update".equals(action)) { // 來自update_emp_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String attend_no = new String(req.getParameter("attend_no").trim());
				
				String member_no = req.getParameter("member_no");
				if (member_no == null || member_no.trim().length() == 0) {
					errorMsgs.add("1");
				}	
				
				Integer race_type = new Integer(req.getParameter("race_type").trim());
//				try {
//					sexual = new Integer(req.getParameter("sexual").trim());
//				} catch (NumberFormatException e) {
//					sexual = 0;
//					errorMsgs.add("獎金請填數字.");
//				}
				
				
				String race_name = req.getParameter("race_name").trim();
				if (race_name == null || race_name.trim().length() == 0) {
					errorMsgs.add("職位勿空白");
				}
				String fin_time = req.getParameter("fin_time").trim();
				if (fin_time == null || fin_time.trim().length() == 0) {
					errorMsgs.add("職位勿空白");
				}
				
				Integer fin_rank = new Integer(req.getParameter("fin_rank").trim());
//				try {
//					mem_status = new Integer(req.getParameter("mem_status").trim());
//				} catch (NumberFormatException e) {
//					mem_status = 0;
//					errorMsgs.add("獎金請填數字.");
//				}
				
				String fin_remark = req.getParameter("fin_remark").trim();
				if (fin_remark == null || fin_remark.trim().length() == 0) {
					errorMsgs.add("職位請勿空");
				}	
				


				AttendRaceVO attendRaceVO = new AttendRaceVO();
				attendRaceVO.setAttend_no(attend_no);
				attendRaceVO.setMember_no(member_no);
				attendRaceVO.setRace_type(race_type);
				attendRaceVO.setRace_name(race_name);
				attendRaceVO.setFin_time(fin_time);
				attendRaceVO.setFin_rank(fin_rank);
				attendRaceVO.setFin_remark(fin_remark);
				
				

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("attendRaceVO", attendRaceVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/attendrace/update_attend_input.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				AttendRaceService attendraceSvc = new AttendRaceService();
				attendraceSvc.updateAttendRace(attend_no, member_no, race_type, race_name, fin_time, fin_rank, fin_remark);
				AttendRaceVO attendRaceVO2 = new AttendRaceVO();
				attendRaceVO2 = attendraceSvc.getOneAttendRace(attend_no);
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("attendRaceVO", attendRaceVO2); // 資料庫update成功後,正確的的empVO物件,存入req
				String url = "/front-end/members/membersPage.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/members/Home.jsp");
				failureView.forward(req, res);
			}
		}

        if ("insert".equals(action)) { // 來自addEmp.jsp的請求  
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String member_no = req.getParameter("member_no");
				
				
				Integer race_type = new Integer(req.getParameter("race_type").trim());
//				try {
//					sexual = new Integer(req.getParameter("sexual").trim());
//				} catch (NumberFormatException e) {
//					sexual = 0;
//					errorMsgs.add("獎金請填數字.");
//				}
				
				String race_name = req.getParameter("race_name").trim();
				if (race_name == null || race_name.trim().length() == 0) {
					errorMsgs.add("比賽名稱不能空白");
				}
				
				java.sql.Date fin_date = null;
				try {
					fin_date = java.sql.Date.valueOf(req.getParameter("fin_date").trim());
				} catch (IllegalArgumentException e) {
					fin_date=new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("完賽日期不能空白");
				}	
				
				String fin_time = req.getParameter("fin_time").trim();
				if (fin_time == null || fin_time.trim().length() == 0) {
					errorMsgs.add("完賽成績請勿空白");
				}	
				
				Integer fin_rank = null;
				try {
					fin_rank = new Integer(req.getParameter("fin_rank").trim());
				} catch (NumberFormatException e) {
					fin_rank = 0;
					errorMsgs.add("名次不能空白");
				}
				
				Part part1 = req.getPart("upfile1");
				InputStream is1 = part1.getInputStream();
				byte[] fin_record = new byte[is1.available()];
				is1.read(fin_record);
				is1.close();
				
				String fin_remark = req.getParameter("fin_remark").trim();
				if (fin_remark == null || fin_remark.trim().length() == 0) {
					errorMsgs.add("比賽備註請勿空白");
				}	
				
				

				AttendRaceVO attendRaceVO = new AttendRaceVO();
				attendRaceVO.setMember_no(member_no);
				attendRaceVO.setRace_type(race_type);
				attendRaceVO.setRace_name(race_name);
				attendRaceVO.setFin_date(fin_date);
				attendRaceVO.setFin_time(fin_time);
				attendRaceVO.setFin_rank(fin_rank);
				attendRaceVO.setFin_record(fin_record);
				attendRaceVO.setFin_remark(fin_remark);
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					System.out.println(race_type);
					req.setAttribute("attendRaceVO", attendRaceVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/attendrace/addAttend.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				AttendRaceService attendraceSvc = new AttendRaceService();
				attendRaceVO = attendraceSvc.addAttendRace(member_no, race_type, race_name, fin_date, 
						fin_time, fin_rank, fin_record, fin_remark);
				MembersService membersSvc = new MembersService();
				MembersVO membersVO = membersSvc.getOneMembers(member_no);
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				HttpSession session = req.getSession();
				session.setAttribute("membersVO", membersVO);
				String url = "/front-end/attendrace/attendRacePage.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �s�W���\�����listAllEmp.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/attendrace/addAttend.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("delete".equals(action)) { // 來自listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/***************************1.接收請求參數***************************************/
				String attend_no = new String(req.getParameter("attend_no"));
						
				/***************************2.開始刪除資料***************************************/
				AttendRaceService attendraceSvc = new AttendRaceService();
				boolean i;
				i = attendraceSvc.deleteAttendRace(attend_no);
				System.out.println(i);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/front-end/attendrace/attendRacePage.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/attendrace/attendRacePage.jsp");
				failureView.forward(req, res);
				
			}
		}
	}
}

