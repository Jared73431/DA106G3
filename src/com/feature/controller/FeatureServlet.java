package com.feature.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.feature.model.FeatureService;
import com.feature.model.FeatureVO;

public class FeatureServlet extends HttpServlet {
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
				String str = req.getParameter("feature_no");
				if (str == null || (str.trim().length() == 0)) {
					errorMsgs.add("請輸入功能編號");
				}
//				if (!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/feature/select_page.jsp");
//					failureView.forward(req, res);
//					return;
//				}
				String feature_no = null;
				try {
					feature_no = new String(str);
				} catch (Exception e) {
					errorMsgs.add("功能編號格式不正確");
				}
//				if (!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/feature/select_page.jsp");
//					failureView.forward(req, res);
//					return;
//				}
				/****************************
				 * 2.開始查詢資料
				 ******************************************/
				FeatureService featureSvc = new FeatureService();
				FeatureVO featureVO = featureSvc.getFeature(feature_no);

				if (featureVO == null) {
					errorMsgs.add("查無資料");
				}
//				if (!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/feature/select_page.jsp");
//					failureView.forward(req, res);
//					return;
//				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("featureVO", featureVO);
				String url = "/back-end/feature/listOneFeature.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				/*************************** 其他可能的錯誤處理 *************************************/

			} catch (Exception e) {
				errorMsgs.add("無法拿到資料" + e.getMessage());
//				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/feature/select_page.jsp");
//				failureView.forward(req, res);
			}
		}

		if ("getOne_For_Update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();

			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");
			try {
				/*************************** 1.接收請求參數 ****************************************/
				String feature_no = new String(req.getParameter("feature_no"));
				/*************************** 2.開始查詢資料 ****************************************/
				FeatureService featureSvc = new FeatureService();
				FeatureVO featureVO = featureSvc.getFeature(feature_no);
				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("featureVO", featureVO);
				String url = "/back-end/feature/update_feature_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher successView = req.getRequestDispatcher(requestURL);
				successView.forward(req, res);
			}
		}
		if ("update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();

			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");
			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String feature_no = new String(req.getParameter("feature_no"));

				String feature_name = req.getParameter("feature_name");
				String feature_nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,15}$";

				if (feature_name == null || feature_name.trim().length() == 0) {
					errorMsgs.add("功能請勿空白");
				} else if (!feature_name.trim().matches(feature_nameReg)) {
					errorMsgs.add("功能只能是中、英文字母和數字,不可包含特殊字元包含空白,且長度需2-15之間");
				}

				String feature_spec = req.getParameter("feature_spec");
				String feature_specReg = "^[(\u4e00-\u9fa5)(a-zA-Z_)]{0,30}$";
				if (!feature_spec.trim().matches(feature_specReg)) {
					errorMsgs.add("介紹只能是中、英文字母和數字,不可包含特殊字元包含空白,且長度請保持在30字之內");
				}

				FeatureVO featureVO = new FeatureVO();

				featureVO.setFeature_no(feature_no);
				featureVO.setFeature_name(feature_name);
				featureVO.setFeature_spec(feature_spec);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("featureVO", featureVO);
					RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
					failureView.forward(req, res);
					return;
				}
				/*************************** 2.開始修改資料 *****************************************/
				FeatureService featureSvc = new FeatureService();

				featureVO = featureSvc.updateFeature(feature_no, feature_name, feature_spec);

				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/

				req.setAttribute("featureVO", featureVO);
				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				/*************************** 其他可能的錯誤處理 *************************************/

			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/feature/update_feature_input.jsp");
				failureView.forward(req, res);
			}
		}

		if ("insert".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				String feature_name = req.getParameter("feature_name");
				String feature_nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,15}$";

				if (feature_name == null || feature_name.trim().length() == 0) {
					errorMsgs.add("功能請勿空白");
				} else if (!feature_name.trim().matches(feature_nameReg)) {
					errorMsgs.add("功能只能是中、英文字母和數字,不可包含特殊字元包含空白,且長度需2-15之間");
				}

				String feature_spec = req.getParameter("feature_spec");
				String feature_specReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{0,30}$";
				if (!feature_spec.trim().matches(feature_specReg)) {
					errorMsgs.add("介紹只能是中、英文字母和數字,不可包含特殊字元包含空白,且長度請保持在30字之內");
				}
				FeatureVO featureVO = new FeatureVO();

		
				featureVO.setFeature_name(feature_name);
				featureVO.setFeature_spec(feature_spec);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("featureVO", featureVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/feature/addFeature.jsp");
					failureView.forward(req, res);
					return;
				}
				/*************************** 2.開始修改資料 *****************************************/
				FeatureService featureSvc = new FeatureService();

				featureVO = featureSvc.addFeature(feature_name, feature_spec);

				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/

				req.setAttribute("featureVO", featureVO);
				String url = "/back-end/feature/listAllFeature.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				/*************************** 其他可能的錯誤處理 *************************************/

			
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/feature/addFeature.jsp");
				failureView.forward(req, res);
			}
		}
		if ("delete".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");
			try {
				/*************************** 1.接收請求參數 ***************************************/
				String feature_no = req.getParameter("feature_no");

				/*************************** 2.開始刪除資料 ***************************************/
				FeatureService featureSvc = new FeatureService();
				featureSvc.delete(feature_no);
				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("刪除失敗" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}
	}
}
