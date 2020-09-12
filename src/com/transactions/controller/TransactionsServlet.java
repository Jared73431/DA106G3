package com.transactions.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.transactions.model.*;
import com.members.model.*;

public class TransactionsServlet extends HttpServlet {

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
				String str = req.getParameter("trans_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入交易編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/transactions/select_page_transactions.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				String trans_no = null;
				try {
					trans_no = new String(str);
				} catch (Exception e) {
					errorMsgs.add("交易編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/transactions/select_page_transactions.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				TransactionsService transactionsSvc = new TransactionsService();
				TransactionsVO transactionsVO = transactionsSvc.getOneTransactions(trans_no);
				if (transactionsVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/transactions/select_page_transactions.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("transactionsVO", transactionsVO); // 鞈�澈����mpVO�隞�,摮req
				String url = "/transactions/listOneTransactions.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ����漱listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/transactions/select_page_transactions.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("getOne_For_Update".equals(action)) { //  來自listAllEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				String trans_no = new String(req.getParameter("trans_no"));
				
				/***************************2.開始查詢資料****************************************/
				TransactionsService transactionsSvc = new TransactionsService();
				TransactionsVO transactionsVO = transactionsSvc.getOneTransactions(trans_no);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("transactionsVO", transactionsVO);         // 鞈�澈����mpVO�隞�,摮req
				String url = "/transactions/update_transactions_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// ����漱 update_emp_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/transactions/listAllTransactions.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("update".equals(action)) { // // 來自update_transactions_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String trans_no = new String(req.getParameter("trans_no").trim());
				
				String member_no = req.getParameter("member_no");
				if (member_no == null || member_no.trim().length() == 0) {
					errorMsgs.add("1");
				}	
				
				Integer deposit = new Integer(req.getParameter("deposit").trim());
				
				
				
				Integer withdraw = new Integer(req.getParameter("withdraw").trim());
				
				
				
				String remark = req.getParameter("remark").trim();
				if (remark == null || remark.trim().length() == 0) {
					errorMsgs.add("請加入備註");
				}	
				
				java.sql.Timestamp tran_date = null;
				try {
					tran_date = java.sql.Timestamp.valueOf(req.getParameter("tran_date").trim());
				} catch (IllegalArgumentException e) {
					tran_date=new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}



				TransactionsVO transactionsVO = new TransactionsVO();
				transactionsVO.setTrans_no(trans_no);
				transactionsVO.setMember_no(member_no);
				transactionsVO.setDeposit(deposit);
				transactionsVO.setWithdraw(withdraw);
				transactionsVO.setTran_date(tran_date);
				transactionsVO.setRemark(remark);
				
				
				

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("transactionsVO", transactionsVO); // ���撓��撘隤斤�mpVO�隞�,銋�req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/transactions/update_transactions_input.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				TransactionsService transactionsSvc = new TransactionsService();
				transactionsSvc.updateTransactions(trans_no, member_no, deposit, withdraw, remark);
				TransactionsVO transactionsVO2 = new TransactionsVO();
				transactionsVO2 = transactionsSvc.getOneTransactions(trans_no);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("transactionsVO", transactionsVO); // 資料庫update成功後,正確的的transactionsVO物件,存入req
				String url = "/transactions/listOneTransactions.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneTransactions.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/transactions/update_transactions_input.jsp");
				failureView.forward(req, res);
			}
		}

        if ("insert".equals(action)) { // 來自addTransactions.jsp的請求  
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				
				String member_no = req.getParameter("member_no");
				if (member_no == null || member_no.trim().length() == 0) {
					errorMsgs.add("請輸入會員編號");
				}	

				Integer deposit = new Integer(req.getParameter("deposit").trim());

				
				
//				Integer withdraw = new Integer(req.getParameter("withdraw").trim());

				
//				String remark = req.getParameter("remark").trim();
//				if (remark == null || remark.trim().length() == 0) {
//					errorMsgs.add("請輸入備註");
//				}	
				

				TransactionsVO transactionsVO = new TransactionsVO();
				transactionsVO.setMember_no(member_no);
				transactionsVO.setDeposit(deposit);
//				transactionsVO.setWithdraw(withdraw);
//				transactionsVO.setTran_date(tran_date);
//				transactionsVO.setRemark(remark);
				
				
				if(deposit < 100)
					errorMsgs.add("儲值金額不能少於100元");
				if(deposit > 99999)
					errorMsgs.add("儲值金額不能大於99999");
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					System.out.println(deposit);
					req.setAttribute("transactionsVO", transactionsVO); 
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/transactions/addTransactions.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				TransactionsService transactionsSvc = new TransactionsService();
				transactionsVO = transactionsSvc.addTransactions(member_no, deposit, 0, "正常交易");
				MembersService membersSvc = new MembersService();
				MembersVO membersVO = membersSvc.getOneMembers(member_no);
				int result = deposit;
				membersSvc.updateMembersRealBlance((membersVO.getReal_blance() + result), member_no);
				membersVO = membersSvc.getOneMembers(member_no);
				errorMsgs.add("11112233");
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				HttpSession session = req.getSession();
				session.setAttribute("membersVO", membersVO);
				String url = "/front-end/members/membersPage.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 嚙編嚙磕嚙踝蕭嚙穀嚙踝蕭嚙踝蕭嚙締istAllEmp.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/transactions/addTransactions.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("delete".equals(action)) { // 靘listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.��隢��***************************************/
				String trans_no = new String(req.getParameter("trans_no"));
				
				/***************************2.����鞈��***************************************/
				TransactionsService transactionsSvc = new TransactionsService();
				boolean i;
				i = transactionsSvc.deleteTransactions(trans_no);
				System.out.println(i);
				
				/***************************3.��摰��,皞��漱(Send the Success view)***********/								
				String url = "/transactions/listAllTransactions.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// �������,頧漱���������雯���
				successView.forward(req, res);
				
				/***************************�隞���隤方���**********************************/
			} catch (Exception e) {
				errorMsgs.add("��鞈�仃���:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/transactions/listAllTransactions.jsp");
				failureView.forward(req, res);
			}
		}
	}
}

