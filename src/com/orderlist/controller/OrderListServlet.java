package com.orderlist.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.orderlist.model.*;

public class OrderListServlet extends HttpServlet{
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		HttpSession session = req.getSession();

		// 確認 0509展示關閉
//		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求
//
//			List<String> errorMsgs = new LinkedList<String>();
//			// Store this set in the request scope, in case we need to
//			// send the ErrorPage view.
//			req.setAttribute("errorMsgs", errorMsgs);
//
//			try {
//				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
//				String str = req.getParameter("order_no");
//				if (str == null || (str.trim()).length() == 0) {
//					errorMsgs.add("請輸入訂單編號");
//				}
//				// Send the use back to the form, if there were errors
//				if (!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req
//							.getRequestDispatcher("/back-end/order_list/select_page.jsp");
//					failureView.forward(req, res);
//					return;//程式中斷
//				}
//				
//				String order_no = null;
//				try {
//					order_no = new String(str);
//				} catch (Exception e) {
//					errorMsgs.add("訂單編號格式不正確");
//				}
//				// Send the use back to the sform, if there were errors
//				if (!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req
//							.getRequestDispatcher("/back-end/order_list/select_page.jsp");
//					failureView.forward(req, res);
//					return;//程式中斷
//				}
//				
//				/***************************2.開始查詢資料*****************************************/
//				OrderListService orderlistSvc = new OrderListService();
//				OrderListVO orderlistVO = orderlistSvc.getOneOrderList(order_no);
//				if (orderlistVO == null) {
//					errorMsgs.add("查無資料");
//				}
//				// Send the use back to the form, if there were errors
//				if (!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req
//							.getRequestDispatcher("/back-end/order_list/select_page.jsp");
//					failureView.forward(req, res);
//					return;//程式中斷
//				}
//				
//				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
//				req.setAttribute("orderlistVO", orderlistVO); // 資料庫取出的 orderlistVO 物件,存入req
//				String url = "/back-end/order_list/listOneOrderList.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneOrderList.jsp
//				successView.forward(req, res);
//
//				/***************************其他可能的錯誤處理*************************************/
//			} catch (Exception e) {
//				errorMsgs.add("無法取得資料:" + e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/back-end/order_list/select_page.jsp");
//				failureView.forward(req, res);
//			}
//		}
		
		// 提供客戶查詢用
		if ("getOne_For_Check".equals(action)) { // 來自listMemOrders.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("order_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入訂單編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/order_list/listMemOrders.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				String order_no = null;
				try {
					order_no = new String(str);
				} catch (Exception e) {
					errorMsgs.add("訂單編號格式不正確");
				}
				// Send the use back to the sform, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/order_list/listMemOrders.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				// 直接塞關鍵字進session讓網頁能持續運作
				session.setAttribute("check_order", order_no);
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				String url = "/front-end/orders/OneOrderList.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneOrderList.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/orders/listMemOrders.jsp");
				failureView.forward(req, res);
			}
		}
		
		// 0509 提供管理者查詢用
		if ("get_For_Display".equals(action)) { // 來自listAllOrders.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("order_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入訂單編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/order_list/listAllOrders.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				String order_no = null;
				try {
					order_no = new String(str);
				} catch (Exception e) {
					errorMsgs.add("訂單編號格式不正確");
				}
				// Send the use back to the sform, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/order_list/listAllOrders.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				// 直接塞關鍵字進session讓網頁能持續運作
				session.setAttribute("check_order", order_no);
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				String url = "/back-end/order_list/OrderList.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneOrderList.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/orders/listAllOrders.jsp");
				failureView.forward(req, res);
			}
		}
				
		// 確認 0509 展示無用關閉
//        if ("insert".equals(action)) { // 來自 addOrderList.jsp的請求  
//			
//			List<String> errorMsgs = new LinkedList<String>();
//			// Store this set in the request scope, in case we need to
//			// send the ErrorPage view.
//			req.setAttribute("errorMsgs", errorMsgs);
//
//			try {
//				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
//				String order_no = req.getParameter("order_no");
//				if (order_no == null || order_no.trim().length() == 0) {
//					errorMsgs.add("訂單編號: 請勿空白");
//				} 
//				
//				String pro_no = req.getParameter("pro_no");
//				if (pro_no == null || pro_no.trim().length() == 0) {
//					errorMsgs.add("產品編號: 請勿空白");
//				}
//								
//				Integer pro_qty = null;
//				try {
//					pro_qty = new Integer (req.getParameter("pro_qty").trim());
//				} catch (NumberFormatException e) {
//					errorMsgs.add("數量請填數字.");
//				}
//				
//				Integer order_price = null;
//				try {
//					order_price = new Integer (req.getParameter("order_price").trim());
//				} catch (NumberFormatException e) {
//					errorMsgs.add("單價請填數字.");
//				}
//
//				
//				OrderListVO orderlistVO = new OrderListVO();
//				orderlistVO.setOrder_no(order_no);
//				orderlistVO.setPro_no(pro_no);
//				orderlistVO.setPro_qty(pro_qty);
//				orderlistVO.setOrder_price(order_price);				
//
//				
//				// Send the use back to the form, if there were errors
//				if (!errorMsgs.isEmpty()) {
//					req.setAttribute("orderlistVO", orderlistVO); // 含有輸入格式錯誤的 orderlistVO 物件,也存入req
//					RequestDispatcher failureView = req
//							.getRequestDispatcher("/back-end/order_list/addOrderList.jsp");
//					failureView.forward(req, res);
//					return;
//				}
//				
//				/***************************2.開始新增資料***************************************/
//				OrderListService orderlistSvc = new OrderListService();
//				orderlistVO = orderlistSvc.addOrderList(order_no, pro_no, pro_qty, order_price);
//				
//				/***************************3.新增完成,準備轉交(Send the Success view)***********/
//				String url = "/back-end/order_list/listAllOrderList.jsp?whichPage=99999";
//				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交 listAllOrderList.jsp
//				successView.forward(req, res);				
//				
//				/***************************其他可能的錯誤處理**********************************/
//			} catch (Exception e) {
//				errorMsgs.add(e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/back-end/order_list/addOrderList.jsp");
//				failureView.forward(req, res);
//			}
//		}
		
		// 確認 非提供展示使用 關閉
//		if ("delete".equals(action)) { // 來自 listAllOrderList.jsp
//
//			List<String> errorMsgs = new LinkedList<String>();
//			// Store this set in the request scope, in case we need to
//			// send the ErrorPage view.
//			req.setAttribute("errorMsgs", errorMsgs);
//	
//			try {
//				/***************************1.接收請求參數***************************************/
//				String order_no = new String(req.getParameter("order_no"));
//				String pro_no = new String(req.getParameter("pro_no"));
//				String whichPage = new String(req.getParameter("whichPage"));
//				/***************************2.開始刪除資料***************************************/
//				OrderListService orderlistSvc = new OrderListService();
//				orderlistSvc.deleteOrderList(order_no, pro_no);
//				
//				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
//				String url = "/back-end/order_list/listAllOrderList.jsp?whichPage="+whichPage;
//				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
//				successView.forward(req, res);
//				
//				/***************************其他可能的錯誤處理**********************************/
//			} catch (Exception e) {
//				errorMsgs.add("刪除資料失敗:"+e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/back-end/order_list/listAllOrderList.jsp");
//				failureView.forward(req, res);
//			}
//		}
	}

}
