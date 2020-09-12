package com.product.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;

import com.foodinform.model.FoodInformService;
import com.google.gson.Gson;
import com.product.model.*;
import com.util.tool.*;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class ProductServlet extends HttpServlet{
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		// 確認
		if ("getOne_For_Display".equals(action)) { // 來自listAllProduct.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("pro_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入產品編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/product/listAllProduct.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				String pro_no = null;
				try {
					pro_no = new String(str);
				} catch (Exception e) {
					errorMsgs.add("訂單編號格式不正確");
				}
				// Send the use back to the sform, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/product/listAllProduct.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				ProductService productSvc = new ProductService();
				ProductVO productVO = productSvc.getOneProduct(pro_no);
				if (productVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/product/listAllProduct.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("productVO", productVO); // 資料庫取出的 productVO 物件,存入req
				String url = "/back-end/product/listOneProduct.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneProduct.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/product/listAllProduct.jsp");
				failureView.forward(req, res);
			}
		}
		
		// 確認
		if ("getOne_For_Update".equals(action)) { // 來自 listAllProduct.jsp 的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				String whichPage = req.getParameter("whichPage");
				String pro_no = new String(req.getParameter("pro_no"));
				/***************************2.開始查詢資料****************************************/
				ProductService productSvc = new ProductService();
				ProductVO productVO = productSvc.getOneProduct(pro_no);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("productVO", productVO);         // 資料庫取出的 productVO 物件,存入req
				req.setAttribute("whichPage", whichPage); // 轉交頁數讓返回使用				
				String url = "/back-end/product/update_product_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_product_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/product/listAllProduct.jsp");
				failureView.forward(req, res);
			}
		}
		
		// 確認
		if ("update".equals(action)) { // 來自 update_product_input.jsp 的請求
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				// 異常處理使用
				ProductService proSvc = new ProductService();
				String pro_no = req.getParameter("pro_no");
				if (pro_no == null || pro_no.trim().length() == 0) {
					errorMsgs.add("商品編號: 請勿空白");
				} 
				
				String prc_no = req.getParameter("prc_no");
				if (prc_no == null || prc_no.trim().length() == 0) {
					errorMsgs.add("商品類別: 請勿空白");
				} 
				
				String pro_name = req.getParameter("pro_name");
				if (pro_name == null || (pro_name.trim()).length() == 0) {
					pro_name = proSvc.getOneProduct(pro_no).getPro_name();
					errorMsgs.add("請輸入產品名稱");
				}
				
				Integer pro_price = null;
				try {
					pro_price = Integer.parseInt(req.getParameter("pro_price").trim());
				} catch (NumberFormatException e) {
					pro_price = new Integer(req.getParameter("pro_price0")); //為了獲取從前一頁送來的資料，輸入錯誤就塞回去
					errorMsgs.add("價格請填數字.");
				}
				// 下拉式選單
				Integer pro_status = null;
				try {
					pro_status = Integer.parseInt(req.getParameter("pro_status").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("請填狀態.");
				}
				
				// 上傳的異常判斷小心			
				String pro_dis = null;
				pro_dis = req.getParameter("pro_dis");
				if (pro_dis.trim()==null||pro_dis.trim().length()==0) {
					pro_dis = proSvc.getOneProduct(pro_no).getPro_dis();
					errorMsgs.add("商品內容: 請勿空白");					
				}
				
				byte[] pro_pic = null;
				System.out.println(req.getPart("pro_pic").getSize()); // 有值
				if (req.getPart("pro_pic").getSize()>0){
					pro_pic = Upload.getPictureByteArray(req.getPart("pro_pic"));
				}else{
					pro_pic = (proSvc.readPic(pro_no));
				}
				
				ProductVO productVO = new ProductVO();
				productVO.setPro_no(pro_no);
				productVO.setPrc_no(prc_no);
				productVO.setPro_name(pro_name);
				productVO.setPro_price(pro_price);				
				productVO.setPro_status(pro_status);				
				productVO.setPro_dis(pro_dis);				
				productVO.setPro_pic(pro_pic);
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("productVO", productVO); // 含有輸入格式錯誤的 productVO 物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/product/update_product_input.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				ProductService productSvc = new ProductService();
				productVO = productSvc.updateProduct(pro_no, prc_no, pro_name, pro_price, pro_status, pro_dis, pro_pic);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("productVO", productVO); // 資料庫update成功後,正確的的 productVO 物件,存入req
				String url = "/back-end/product/listOneProduct.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交 listOneProduct.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/product/update_product_input.jsp");
				failureView.forward(req, res);
			}
		}
		
		// 確認
        if ("insert".equals(action)) { // 來自 addOrders.jsp的請求  
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String prc_no = req.getParameter("prc_no");
				if (prc_no == null || prc_no.trim().length() == 0) {
					errorMsgs.add("商品類別: 請勿空白");
				} 

				String pro_name = req.getParameter("pro_name");
				if (pro_name == null || pro_name.trim().length() == 0) {
					errorMsgs.add("商品名稱: 請勿空白");
				} 

				Integer pro_price = null;
				try {
					pro_price = Integer.parseInt(req.getParameter("pro_price").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("價格請填整數.");
				}
				if (pro_price == null) {
					errorMsgs.add("商品價格: 請勿空白");
				} 

				// 上傳的異常判斷小心			
				String pro_dis = null;
				pro_dis = req.getParameter("pro_dis");
				if (pro_dis.trim()==null||pro_dis.trim().length()==0) {
					errorMsgs.add("文章內容: 請勿空白");					
				}

				
				byte[] pro_pic = null;
				if (req.getPart("pro_pic").getSize()==0){
					errorMsgs.add("商品封面: 請勿空白");					
				}
				pro_pic = Upload.getPictureByteArray(req.getPart("pro_pic"));				
				
				Integer pro_status = null;
				
				ProductVO productVO = new ProductVO();
				productVO.setPrc_no(prc_no);
				productVO.setPro_name(pro_name);
				productVO.setPro_price(pro_price);				
				productVO.setPro_dis(pro_dis);				
				productVO.setPro_pic(pro_pic);
				productVO.setPro_status(pro_status);
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("productVO", productVO); // 含有輸入格式錯誤的 productVO 物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/product/addProduct.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				ProductService productSvc = new ProductService();
				productVO = productSvc.addProduct(prc_no, pro_name, pro_price, pro_dis, pro_pic);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/back-end/product/listAllProduct.jsp?whichPage=99999";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交 listAllProduct.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				e.printStackTrace();
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/product/addProduct.jsp");
				failureView.forward(req, res);
			}
		}
		
        // 確認從delete改成下架
		if ("AJAX_For_Status".equals(action)) { // 來自 listAllProduct.jsp
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.接收請求參數***************************************/
				// 要小心參數名稱，如果跟前端相異會取到空值
				String pro_no = new String(req.getParameter("pro_no"));
				Integer pro_status = Integer.parseInt(req.getParameter("pro_status"));
				/***************************2.開始修改資料***************************************/
				ProductService productSvc = new ProductService();				
				productSvc.changeStatus(pro_no , pro_status);
				// 測試Ajax搭配websocket
				if(pro_status.equals(1)) {
					ProductWebsocket websocket = new ProductWebsocket();
					ProductVO productVO = productSvc.getOneProduct(pro_no);
			        // 把VO轉成JSON格式
					Gson gson = new Gson();
			        String data = gson.toJson(productVO);
					// 推送訊息至前台
					websocket.onMessage(data);
				}				
				/***************************3.AJAX不跳頁,印出字串提示(Send the Success view)***********/								
				res.setContentType("text/html; charset=UTF-8");
				PrintWriter out = res.getWriter();
				out.print("AJAX傳送成功");
				out.close();
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				System.out.println("更新狀態失敗:" + e.getMessage());
			}
		}
	}
}
