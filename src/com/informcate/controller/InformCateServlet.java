package com.informcate.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.informcate.model.*;

public class InformCateServlet extends HttpServlet{
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		// 確認0402
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("cate_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入資訊類別編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/inform_cate/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				String cate_no = null;
				try {
					cate_no = new String(str);
				} catch (Exception e) {
					errorMsgs.add("資訊類別編號格式不正確");
				}
				// Send the use back to the sform, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/inform_cate/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				InformCateService informcateSvc = new InformCateService();
				InformCateVO informcateVO = informcateSvc.getOneInformCate(cate_no);
				if (informcateVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/inform_cate/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("informcateVO", informcateVO); // 資料庫取出的 informcateVO 物件,存入req
				String url = "/back-end/inform_cate/listOneInformCate.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneInformCate.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/inform_cate/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
		// 0402確認
		if ("getOne_For_Update".equals(action)) { // 來自 listAllInformCate.jsp 的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				String cate_no = new String(req.getParameter("cate_no"));
				
				/***************************2.開始查詢資料****************************************/
				InformCateService informcateSvc = new InformCateService();
				InformCateVO informcateVO = informcateSvc.getOneInformCate(cate_no);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("informcateVO", informcateVO);         // 資料庫取出的 informcateVO 物件,存入req
				String url = "/back-end/inform_cate/update_informcate_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_informcate_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/inform_cate/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
		// 0402確認
		if ("update".equals(action)) { // 來自 update_informcate_input.jsp 的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String cate_no = new String(req.getParameter("cate_no").trim());
				
				String cate_name = req.getParameter("cate_name");
				if (cate_name == null || cate_name.trim().length() == 0) {
					errorMsgs.add("資訊類別名稱: 請勿空白");
				}				
				InformCateVO informcateVO = new InformCateVO();
				informcateVO.setCate_no(cate_no);
				informcateVO.setCate_name(cate_name);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("informcateVO", informcateVO); // 含有輸入格式錯誤的 informcateVO 物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/inform_cate/update_informcate_input.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				InformCateService informcateSvc = new InformCateService();
				informcateVO = informcateSvc.updateInformCate(cate_no, cate_name);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("informcateVO", informcateVO); // 資料庫update成功後,正確的的 informcateVO 物件,存入req
				String url = "/back-end/inform_cate/listOneInformCate.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交 listOneInformCate.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/inform_cate/update_informcate_input.jsp");
				failureView.forward(req, res);
			}
		}
		// 0402確認
        if ("insert".equals(action)) { // 來自 addInformCate.jsp的請求  
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String cate_name = req.getParameter("cate_name");
				if (cate_name == null || cate_name.trim().length() == 0) {
					errorMsgs.add("資訊類別名稱: 請勿空白");
				} 

				InformCateVO informcateVO = new InformCateVO();
				informcateVO.setCate_name(cate_name);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("informcateVO", informcateVO); // 含有輸入格式錯誤的 informcate物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/inform_cate/addInformCate.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				InformCateService informcateSvc = new InformCateService();
				informcateVO = informcateSvc.addInformCate(cate_name);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/back-end/inform_cate/listAllInformCate.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllInformCate.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/inform_cate/addInformCate.jsp");
				failureView.forward(req, res);
			}
		}
		
		// 0402確認
		if ("delete".equals(action)) { // 來自listAllInformCate.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.接收請求參數***************************************/
				String cate_no = new String(req.getParameter("cate_no"));
				
				/***************************2.開始刪除資料***************************************/
				InformCateService informcateSvc = new InformCateService();
				informcateSvc.deleteInformCate(cate_no);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/back-end/inform_cate/listAllInformCate.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/inform_cate/listAllInformCate.jsp");
				failureView.forward(req, res);
			}
		}
	}

}
