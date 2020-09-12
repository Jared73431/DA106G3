package com.authorizations.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.admins.model.AdminsService;
import com.authorizations.model.AuthorizationsService;
import com.authorizations.model.AuthorizationsVO;

public class AuthorizationsServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		if ("getOne_For_Display".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			try {
				String str = req.getParameter("admin_no");
				if (str == null || (str.trim().length() == 0)) {
					errorMsgs.add("請輸入員工編號");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/backstage/index.jsp");
					failureView.forward(req, res);
					return;
				}
				String admin_no = null;
				try {
					admin_no = new String(str);
				} catch (Exception e) {
					errorMsgs.add("功能編號格式不正確");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/backstage/index.jsp");
					failureView.forward(req, res);
					return;
				}

				/****************************
				 * 2.開始查詢資料
				 ******************************************/
				AuthorizationsService authorizationsSvc = new AuthorizationsService();
				List<AuthorizationsVO> list = authorizationsSvc.getOneAdmin(admin_no);

				if (admin_no == null) {

					errorMsgs.add("查無資料");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/backstage/index.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("list", list);
				req.setAttribute("admin_no", admin_no);
				String url = "/back-end/authorizations/listOneAdminOfAuthorizations.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				/*************************** 其他可能的錯誤處理 *************************************/

			} catch (Exception e) {
				errorMsgs.add("無法拿到資料" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/backstage/index.jsp");
				failureView.forward(req, res);
			}
		}
		if ("getOne_For_Update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();

			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL"); 
			try {
				/*************************** 1.接收請求參數 ****************************************/
				String admin_no = new String(req.getParameter("admin_no"));
				/*************************** 2.開始查詢資料 ****************************************/
				AuthorizationsService authorizationsSvc = new AuthorizationsService();
				List<AuthorizationsVO> list = authorizationsSvc.getOneAdmin(admin_no);
				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				boolean openModal=true;
				req.setAttribute("openModal",openModal );
				req.setAttribute("list", list);
				req.setAttribute("admin_no", admin_no);
				String url = "/back-end/authorizations/listAllAuthorizations.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher successView = req
						.getRequestDispatcher(requestURL);
				successView.forward(req, res);
			}
		}
		if ("update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();

			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL"); 
			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String admin_no = new String(req.getParameter("admin_no"));
				String feature_no = null;

				String[] featureList = req.getParameterValues("feature");

				List<AuthorizationsVO> list = new ArrayList<AuthorizationsVO>();

				if (featureList != null) {

					for (String feature : featureList) {
						AuthorizationsVO authorizationsVO = new AuthorizationsVO();

						authorizationsVO.setAdmin_no(admin_no);
						authorizationsVO.setFeature_no(feature);
						list.add(authorizationsVO);
					}

				}

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("list", list);
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/authorizations/update_authorizations_input.jsp");
					failureView.forward(req, res);
					return;
				}
				/*************************** 2.開始修改資料 *****************************************/
				AuthorizationsService authorizationsSvc = new AuthorizationsService();
				authorizationsSvc.delete(admin_no);

				for (AuthorizationsVO authorizations : list) {
					feature_no = authorizations.getFeature_no();

					authorizations = authorizationsSvc.addAuthorization(admin_no, feature_no);

				}

				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/

				req.setAttribute("list", list);
				req.setAttribute("admin_no", admin_no);
				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				/*************************** 其他可能的錯誤處理 *************************************/

			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/authorizations/update_authorizations_input.jsp");
				failureView.forward(req, res);
			}
		}
		if ("delete".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL"); 
			try {
				/*************************** 1.接收請求參數 ***************************************/
				String admin_no = req.getParameter("admin_no");

				/*************************** 2.開始刪除資料 ***************************************/
				AuthorizationsService authorizationsSvc = new AuthorizationsService();
				authorizationsSvc.delete(admin_no);
				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				String url =requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("刪除失敗" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}

	}

}
