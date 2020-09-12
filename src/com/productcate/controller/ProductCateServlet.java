package com.productcate.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.productcate.model.*;

public class ProductCateServlet extends HttpServlet{
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		// 0402確認，注意連結
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("prc_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入產品類別編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/product_cate/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				String prc_no = null;
				try {
					prc_no = new String(str);
				} catch (Exception e) {
					errorMsgs.add("員工編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/product_cate/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				ProductCateService productcateSvc = new ProductCateService();
				ProductCateVO productcateVO = productcateSvc.getOneProductCate(prc_no);
				if (productcateVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/product_cate/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("productcateVO", productcateVO); // 資料庫取出的productcateVO物件,存入req
				String url = "/back-end/product_cate/listOneProductCate.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneProductCate.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/product_cate/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
		// 0402小心連結名稱與正則表示式
		if ("getOne_For_Update".equals(action)) { // 來自listAllProductCate.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				String prc_no = new String(req.getParameter("prc_no"));
				
				/***************************2.開始查詢資料****************************************/
				ProductCateService productcateSvc = new ProductCateService();
				ProductCateVO productcateVO = productcateSvc.getOneProductCate(prc_no);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("productcateVO", productcateVO);         // 資料庫取出的productcateVO物件,存入req
				String url = "/back-end/product_cate/update_productcate_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_productcate_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/product_cate/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("update".equals(action)) { // 來自update_productcate_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String prc_no = new String(req.getParameter("prc_no").trim());
				
				String prc_name = req.getParameter("prc_name");
				if (prc_name == null || prc_name.trim().length() == 0) {
					errorMsgs.add("產品類別名稱: 請勿空白");
				}				
				ProductCateVO productcateVO = new ProductCateVO();
				productcateVO.setPrc_no(prc_no);
				productcateVO.setPrc_name(prc_name);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("productcateVO", productcateVO); // 含有輸入格式錯誤的productcateVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/product_cate/update_productcate_input.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				ProductCateService productcateSvc = new ProductCateService();
				productcateVO = productcateSvc.updateProductCate(prc_no, prc_name);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("productcateVO", productcateVO); // 資料庫update成功後,正確的的productcateVO物件,存入req
				String url = "/back-end/product_cate/listOneProductCate.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneProductCate.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/product_cate/update_productcate_input.jsp");
				failureView.forward(req, res);
			}
		}
		// 0402確認
        if ("insert".equals(action)) { // 來自addProudctCate.jsp的請求  
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String prc_name = req.getParameter("prc_name");
				if (prc_name == null || prc_name.trim().length() == 0) {
					errorMsgs.add("產品類別名稱: 請勿空白");
				} 

				ProductCateVO productcateVO = new ProductCateVO();
				productcateVO.setPrc_name(prc_name);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("productcateVO", productcateVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/product_cate/addProductCate.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				ProductCateService productcateSvc = new ProductCateService();
				productcateVO = productcateSvc.addProductCate(prc_name);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/back-end/product_cate/listAllProductCate.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllProductCate.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/product_cate/addProductCate.jsp");
				failureView.forward(req, res);
			}
		}
		
		// 
		if ("delete".equals(action)) { // 來自listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.接收請求參數***************************************/
				String prc_no = new String(req.getParameter("prc_no"));
				
				/***************************2.開始刪除資料***************************************/
				ProductCateService productcateSvc = new ProductCateService();
				productcateSvc.deleteProductCate(prc_no);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/back-end/product_cate/listAllProductCate.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/product_cate/listAllProductCate.jsp");
				failureView.forward(req, res);
			}
		}
	}

}
