package com.orders.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

import com.members.model.MembersService;
import com.members.model.MembersVO;
import com.notificationlist.model.NotificationListService;
import com.orderlist.model.OrderListVO;
import com.orders.model.*;
import com.shopping.model.*;

public class OrdersServlet extends HttpServlet{
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		String action = req.getParameter("action");
		
		// 確認 專題展示遮蔽
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
//							.getRequestDispatcher("/back-end/orders/select_page.jsp");
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
//							.getRequestDispatcher("/back-end/orders/select_page.jsp");
//					failureView.forward(req, res);
//					return;//程式中斷
//				}
//				
//				/***************************2.開始查詢資料*****************************************/
//				OrdersService orderSvc = new OrdersService();
//				OrdersVO ordersVO = orderSvc.getOneOrders(order_no);
//				if (ordersVO == null) {
//					errorMsgs.add("查無資料");
//				}
//				// Send the use back to the form, if there were errors
//				if (!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req
//							.getRequestDispatcher("/back-end/orders/select_page.jsp");
//					failureView.forward(req, res);
//					return;//程式中斷
//				}
//				
//				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
//				req.setAttribute("ordersVO", ordersVO); // 資料庫取出的 ordersVO 物件,存入req
//				String url = "/back-end/orders/listOneOrder.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneOrder.jsp
//				successView.forward(req, res);
//
//				/***************************其他可能的錯誤處理*************************************/
//			} catch (Exception e) {
//				errorMsgs.add("無法取得資料:" + e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/back-end/orders/select_page.jsp");
//				failureView.forward(req, res);
//			}
//		}
		
		// 確認
		if ("getOne_For_Update".equals(action)) { // 來自 listAllOrders.jsp 的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				String order_no = new String(req.getParameter("order_no"));
				/***************************2.開始查詢資料****************************************/
				OrdersService orderSvc = new OrdersService();
				OrdersVO ordersVO = orderSvc.getOneOrders(order_no);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("ordersVO", ordersVO);         // 資料庫取出的 ordersVO 物件,存入req
				String url = "/back-end/orders/update_orders_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_orders_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/orders/listAllOrders.jsp");
				failureView.forward(req, res);
			}
		}
		
		// 確認
		if ("update".equals(action)) { // 來自 update_orders_input.jsp 的請求
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String order_no = new String(req.getParameter("order_no").trim());
				String member_no = req.getParameter("member_no");
				
				// 錯誤處理使用
				OrdersService Svc = new OrdersService();
				
				String order_zip = req.getParameter("order_zip");
				if (order_zip == null || order_zip.trim().length() == 0) {
					order_zip = Svc.getOneOrders(order_no).getOrder_zip();
					errorMsgs.add("郵遞區號: 請勿空白");
				}

				String order_address = req.getParameter("order_address");
				if (order_address == null || order_address.trim().length() == 0) {
					order_address = Svc.getOneOrders(order_no).getOrder_address();
					errorMsgs.add("遞送地址: 請勿空白");
				}

				Timestamp etb = null;
				try {
				etb = Timestamp.valueOf(req.getParameter("etb").trim());
				} catch (IllegalArgumentException e) {
					etb = Svc.getOneOrders(order_no).getEtb();
					errorMsgs.add(req.getParameter("請確認時間格式"));
				} 
				// 使用日期選擇器
				Timestamp eta = null;
				try {
					eta = Timestamp.valueOf(req.getParameter("eta").trim());
				} catch (IllegalArgumentException e) {
					if(Svc.getOneOrders(order_no).getEta()==null) {
						eta =new Timestamp(System.currentTimeMillis());
					}else {
						eta = Svc.getOneOrders(order_no).getEta();
					}
					errorMsgs.add("請確認時間格式");
				}
				
				Integer total_price = Integer.parseInt(req.getParameter("total_price").trim());
				// 狀態用下拉式選單
				Integer order_status = Integer.parseInt(req.getParameter("order_status").trim());
				
				
				OrdersVO ordersVO = new OrdersVO();
				ordersVO.setOrder_no(order_no);
				ordersVO.setMember_no(member_no);
				ordersVO.setOrder_zip(order_zip);
				ordersVO.setOrder_address(order_address);				
				ordersVO.setEtb(etb);				
				ordersVO.setEta(eta);				
				ordersVO.setOrder_status(order_status);
				ordersVO.setTotal_price(total_price);
				
				
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("ordersVO", ordersVO); // 含有輸入格式錯誤的 ordersVO 物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/orders/update_orders_input.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				OrdersService orderSvc = new OrdersService();
				// 先取得修改前的資料
				Integer order_status0= orderSvc.getOneOrders(order_no).getOrder_status();
				ordersVO = orderSvc.updateOrders(order_no, member_no, order_zip, order_address, etb, eta, total_price, order_status);
				if (order_status0 ==1 && order_status ==2) {
					// 發送通知提醒使用者即將出貨
					NotificationListService notificationSvc = new NotificationListService();
					notificationSvc.addNotification(member_no, "CA0005", "你的訂單已處理完成，商品即將出貨");	
				}
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("ordersVO", ordersVO); // 資料庫update成功後,正確的的 ordersVO 物件,存入req
				String url = "/back-end/orders/listAllOrders.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交 listOneOrder.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/orders/update_orders_input.jsp");
				failureView.forward(req, res);
			}
		}
		// 04/13新增checkout 引導至新增Orders
        if ("checkout".equals(action)) { // 來自 Checkout.jsp的請求  
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String member_no = req.getParameter("member_no");
				if (member_no == null || member_no.trim().length() == 0) {
					errorMsgs.add("會員編號: 請勿空白");
				} 

				Integer total_price = null;
				try {
					total_price = Integer.parseInt(req.getParameter("total_price"));
				} catch (NumberFormatException e) {
					errorMsgs.add("真的是來購物？");
				}
				
				OrdersVO ordersVO = new OrdersVO();
				ordersVO.setMember_no(member_no);
				ordersVO.setTotal_price(total_price);
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("ordersVO", ordersVO); // 含有輸入格式錯誤的 ordersVO 物件,也存入req
					req.setAttribute("total_price", total_price);
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/shopping/Checkout.jsp");
					failureView.forward(req, res);
					return;
				}
								
				/***************************2.準備轉交資料(Send the Add view)***********/
				String url = "/front-end/orders/addOrders.jsp";
				req.setAttribute("ordersVO", ordersVO);							// 務必記得傳遞物件，不然jsp就炸裂XD
				RequestDispatcher successView = req.getRequestDispatcher(url);	// 成功後轉交 addOrders.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/shopping/Checkout.jsp");
				failureView.forward(req, res);
			}
		}
		
		// 確認 專題展示遮蔽 後台不用新增
//        if ("insert".equals(action)) { // 來自 addOrders.jsp的請求  
//			
//			List<String> errorMsgs = new LinkedList<String>();
//			// Store this set in the request scope, in case we need to
//			// send the ErrorPage view.
//			req.setAttribute("errorMsgs", errorMsgs);
//
//			try {
//				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
//				String member_no = req.getParameter("member_no");
//				if (member_no == null || member_no.trim().length() == 0) {
//					errorMsgs.add("會員編號: 請勿空白");
//				} 
//				
//				// 使用郵局的下拉式選單
//				String order_zip = req.getParameter("order_zip");
//				
//				String order_address = req.getParameter("order_address");
//				if (order_address == null || order_address.trim().length() == 0) {
//					errorMsgs.add("寄件地址: 請勿空白");
//				}
//				
//				Integer total_price = null;
//				try {
//					total_price = new Integer (req.getParameter("total_price").trim());
//				} catch (NumberFormatException e) {
//					total_price = 0;
//					errorMsgs.add("總價請填數字.");
//				}
//				
//				OrdersVO ordersVO = new OrdersVO();
//				ordersVO.setMember_no(member_no);
//				ordersVO.setOrder_zip(order_zip);
//				ordersVO.setOrder_address(order_address);
//				ordersVO.setTotal_price(total_price);				
//
//				
//				// Send the use back to the form, if there were errors
//				if (!errorMsgs.isEmpty()) {
//					req.setAttribute("ordersVO", ordersVO); // 含有輸入格式錯誤的 ordersVO 物件,也存入req
//					RequestDispatcher failureView = req
//							.getRequestDispatcher("/back-end/orders/addOrders.jsp");
//					failureView.forward(req, res);
//					return;
//				}
//				
//				/***************************2.開始新增資料***************************************/
//				OrdersService orderSvc = new OrdersService();
//				ordersVO = orderSvc.addOrders(member_no, order_zip, order_address, total_price);
//				
//				/***************************3.新增完成,準備轉交(Send the Success view)***********/
//				String url = "/back-end/orders/listAllOrders.jsp?whichPage=99999";
//				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交 listAllOrders.jsp
//				successView.forward(req, res);				
//				
//				/***************************其他可能的錯誤處理**********************************/
//			} catch (Exception e) {
//				errorMsgs.add(e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/back-end/orders/addOrders.jsp");
//				failureView.forward(req, res);
//			}
//		}
		
        // 同時設定訂單與明細
        if ("insertA".equals(action)) { // 來自 addOrders.jsp的請求  
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String member_no = req.getParameter("member_no");
				if (member_no == null || member_no.trim().length() == 0) {
					errorMsgs.add("會員編號: 請勿空白");
				}
				System.out.println(member_no);
				
				// 使用下拉式選單
				String order_zip = req.getParameter("order_zip");
				if (order_zip == null || order_zip.trim().length() == 0) {
					errorMsgs.add("郵遞區號: 請勿空白");
				}
				
				// 下拉選單會幫忙輸入部分內容
				String order_address = req.getParameter("order_address");
				if (order_address == null || order_address.trim().length() == 0) {
					errorMsgs.add("寄件地址: 請勿空白");
				}
				
				Integer total_price = null;
				try {
					total_price = new Integer (req.getParameter("total_price").trim());
				} catch (NumberFormatException e) {
					total_price = 0;
					errorMsgs.add("總價請填數字.");
				}
				
				OrdersVO ordersVO = new OrdersVO();
				ordersVO.setMember_no(member_no);
				ordersVO.setOrder_zip(order_zip);
				ordersVO.setOrder_address(order_address);
				ordersVO.setTotal_price(total_price);				
				
				@SuppressWarnings("unchecked")
				// 把購物車內容轉成訂單明細的資料
				List<ShoppingVO> buylist = (List<ShoppingVO>) session.getAttribute("shoppingcart");
				// 魂體轉換：準備相關載具 (原因購物是存產品VO與明細資料有差異要提取相關資料轉成訂單明細)
				List<OrderListVO> list = new ArrayList<OrderListVO>();
				for (ShoppingVO svo : buylist) {
					// 千萬不能寫在迴圈外面
					OrderListVO orderlistVO = new OrderListVO();
					orderlistVO.setPro_no(svo.getPro_no());
					orderlistVO.setOrder_price(svo.getPro_price());
					orderlistVO.setPro_qty(svo.getQuantity());
					list.add(orderlistVO);
				}				
				
				// 攔檢點2 確認會員實際金額是否足夠
				// 呼叫會員領班取得該會員剩下點數
				MembersService membersSvc = new MembersService();
				MembersVO membersVO = membersSvc.getOneMembers(member_no);
				Integer real_blance = membersVO.getReal_blance();
				// 判斷交易金額是否大於存款
				if (total_price > real_blance) {
					errorMsgs.add("金額不足請儲值");
					boolean flag = true;
					req.setAttribute("flag", flag);
				}
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("ordersVO", ordersVO); // 含有輸入格式錯誤的 ordersVO 物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/orders/addOrders.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				OrdersService orderSvc = new OrdersService();
				boolean flag = orderSvc.insert(ordersVO,list);
				if(flag) {
					// 確認新增完成進行扣款操作
					real_blance -= total_price;
					orderSvc.paybill(member_no, real_blance, 0, total_price, "商品交易扣款");
				}
				// 呼叫方法生成通知存入會員資料庫
				NotificationListService notificationSvc = new NotificationListService();
				notificationSvc.addNotification(member_no, "CA0005", "你的商品已完成交易");
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				session.removeAttribute("shoppingcart");
				session.removeAttribute("shop_price");
				// 轉交位置
				String url = "/front-end/shopping/listAllShopping.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉回 listAllShopping.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/orders/addOrders.jsp");
				failureView.forward(req, res);
			}
		}
        
		// 確認 專題展示遮蔽不用刪除
//		if ("delete".equals(action)) { // 來自 listAllOrders.jsp
//
//			List<String> errorMsgs = new LinkedList<String>();
//			// Store this set in the request scope, in case we need to
//			// send the ErrorPage view.
//			req.setAttribute("errorMsgs", errorMsgs);
//	
//			try {
//				/***************************1.接收請求參數***************************************/
//				String order_no = new String(req.getParameter("order_no"));
//				String whichPage = new String(req.getParameter("whichPage"));
//				/***************************2.開始刪除資料***************************************/
//				OrdersService orderSvc = new OrdersService();
//				orderSvc.deleteOrders(order_no);
//				
//				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
//				String url = "/back-end/orders/listAllOrders.jsp?whichPage="+whichPage;
//				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
//				successView.forward(req, res);
//				
//				/***************************其他可能的錯誤處理**********************************/
//			} catch (Exception e) {
//				errorMsgs.add("刪除資料失敗:"+e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/back-end/orders/listAllOrders.jsp");
//				failureView.forward(req, res);
//			}
//		}

	}

}
