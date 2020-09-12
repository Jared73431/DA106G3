package com.gym.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.gym.model.*;

public class GymServlet extends HttpServlet{
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		// 確認0403
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("gym_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入健身房編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/gym/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				String gym_no = null;
				try {
					gym_no = new String(str);
				} catch (Exception e) {
					errorMsgs.add("健身房編號格式不正確");
				}
				// Send the use back to the sform, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/gym/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				GymService gymSvc = new GymService();
				GymVO gymVO = gymSvc.getOneGym(gym_no);
				if (gymVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/gym/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("gymVO", gymVO); // 資料庫取出的 gymVO 物件,存入req
				String url = "/back-end/gym/listOneGym.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneGym.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/gym/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
		// 0402確認
		if ("getOne_For_Update".equals(action)) { // 來自 listAllGym.jsp 的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				String gym_no = new String(req.getParameter("gym_no"));
				/***************************2.開始查詢資料****************************************/
				GymService gymSvc = new GymService();
				GymVO gymVO = gymSvc.getOneGym(gym_no);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("gymVO", gymVO);         // 資料庫取出的 gymVO 物件,存入req
				String url = "/back-end/gym/update_gym_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_gym_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/gym/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
		// 0403確認
		if ("update".equals(action)) { // 來自 update_gym_input.jsp 的請求
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String gym_no = new String(req.getParameter("gym_no").trim());
				String gym_name = req.getParameter("gym_name");
				if (gym_name == null || gym_name.trim().length() == 0) {
					errorMsgs.add("健身房名稱: 請勿空白");
				}
				
				String gym_address = req.getParameter("gym_address");
				if (gym_address == null || gym_address.trim().length() == 0) {
					errorMsgs.add("健身房地址: 請勿空白");
				}
				Double gym_lon = null;
				try {
					gym_lon = new Double(req.getParameter("gym_lon").trim());
				} catch (NumberFormatException e) {
					gym_lon = new Double(req.getParameter("gym_lonO")); //為了獲取從前一頁送來的資料，輸入錯誤就塞回去
					errorMsgs.add("緯度請填詳細數字.");
				}

				Double gym_lat = null;
				try {
					gym_lat = new Double(req.getParameter("gym_lat").trim());
				} catch (NumberFormatException e) {
					gym_lat = new Double(req.getParameter("gym_latO")); //為了獲取從前一頁送來的資料，輸入錯誤就塞回去
					errorMsgs.add("緯度請填詳細數字.");
				}

				String gym_site = req.getParameter("gym_site");
				if (gym_site == null || gym_site.trim().length() == 0) {
					errorMsgs.add("健身房網址: 請勿空白");
				}
				
				GymVO gymVO = new GymVO();
				gymVO.setGym_no(gym_no);
				gymVO.setGym_name(gym_name);				
				gymVO.setGym_address(gym_address);
				gymVO.setGym_lon(gym_lon);
				gymVO.setGym_lat(gym_lat);
				gymVO.setGym_site(gym_site);
				

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("gymVO", gymVO); // 含有輸入格式錯誤的 gymVO 物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/gym/update_gym_input.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				GymService gymSvc = new GymService();
				gymVO = gymSvc.updateGym(gym_no, gym_name, gym_address, gym_lon, gym_lat, gym_site);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("gymVO", gymVO); // 資料庫update成功後,正確的的 gymVO 物件,存入req
				String url = "/back-end/gym/listOneGym.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交 listOneGym.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/gym/update_gym_input.jsp");
				failureView.forward(req, res);
			}
		}
		// 0403確認
        if ("insert".equals(action)) { // 來自 addGym.jsp的請求  
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String gym_name = req.getParameter("gym_name");
				if (gym_name == null || gym_name.trim().length() == 0) {
					errorMsgs.add("健身房名稱: 請勿空白");
				} 

				String gym_address = req.getParameter("gym_address");
				if (gym_address == null || gym_address.trim().length() == 0) {
					errorMsgs.add("健身房地址: 請勿空白");
				}
				
				Double gym_lon = null;
				try {
					gym_lon = new Double(req.getParameter("gym_lon").trim());
				} catch (NumberFormatException e) {
					gym_lon = 0.0;
					errorMsgs.add("經度請填數字.");
				}

				Double gym_lat = null;
				try {
					gym_lat = new Double(req.getParameter("gym_lat").trim());
				} catch (NumberFormatException e) {
					gym_lat = 0.0;
					errorMsgs.add("緯度請填數字.");
				}

				String gym_site = req.getParameter("gym_site");
				if (gym_site == null || gym_site.trim().length() == 0) {
					errorMsgs.add("健身房網址: 請勿空白");
				}
				
				GymVO gymVO = new GymVO();
				gymVO.setGym_name(gym_name);
				gymVO.setGym_address(gym_address);
				gymVO.setGym_lon(gym_lon);
				gymVO.setGym_lat(gym_lat);
				gymVO.setGym_site(gym_site);
				

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("gymVO", gymVO); // 含有輸入格式錯誤的 gymVO 物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/gym/addGym.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				GymService gymSvc = new GymService();
				gymVO = gymSvc.addGym(gym_name, gym_address, gym_lon, gym_lat, gym_site);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/back-end/gym/listAllGym.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交 listAllGym.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/gym/addGym.jsp");
				failureView.forward(req, res);
			}
		}
		
		// 0403確認
		if ("delete".equals(action)) { // 來自 listAllGym.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
//			try {
				/***************************1.接收請求參數***************************************/
				String gym_no = new String(req.getParameter("gym_no"));
				/***************************2.開始刪除資料***************************************/
				GymService gymSvc = new GymService();
				gymSvc.deleteGym(gym_no);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/back-end/gym/listAllGym.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
//			} catch (Exception e) {
//				errorMsgs.add("刪除資料失敗:"+e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/back-end/gym/listAllGym.jsp");
//				failureView.forward(req, res);
//			}
		}
	}

}
