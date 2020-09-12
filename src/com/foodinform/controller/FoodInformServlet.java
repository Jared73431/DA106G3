package com.foodinform.controller;

import java.io.*;
import java.sql.Timestamp;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;

import com.foodinform.model.*;
import com.product.model.ProductService;
import com.util.tool.*;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class FoodInformServlet extends HttpServlet{
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		// 確認
		if ("getOne_For_Display".equals(action)) { // 來自listAllFoodInform.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String whichPage = req.getParameter("whichPage");
				String str = req.getParameter("fi_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入食訊編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/food_inform/listAllFoodInform.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				String fi_no = null;
				try {
					fi_no = new String(str);
				} catch (Exception e) {
					errorMsgs.add("編號格式不正確");
				}
				// Send the use back to the sform, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/food_inform/listAllFoodInform.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				FoodInformService foodinformSvc = new FoodInformService();
				FoodInformVO foodinformVO = foodinformSvc.getOneFoodInform(fi_no);
				if (foodinformVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/food_inform/listAllFoodInform.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("foodinformVO", foodinformVO); // 資料庫取出的 foodinformVO 物件,存入req
				req.setAttribute("whichPage", whichPage);
				String url = "/back-end/food_inform/listOneFoodInform.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneFoodInform.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/food_inform/listAllFoodInform.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("getOne_Display".equals(action)) { // 來自listAllFoodInform.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String whichPage = req.getParameter("whichPage");
				String str = req.getParameter("fi_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入食訊編號");
				}
				
				String fi_no = null;
				try {
					fi_no = new String(str);
				} catch (Exception e) {
					errorMsgs.add("編號格式不正確");
				}
				
				// Send the use back to the sform, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/food_inform/listAllFoodInform.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				FoodInformService foodinformSvc = new FoodInformService();
				FoodInformVO foodinformVO = foodinformSvc.getOneFoodInform(fi_no);
				if (foodinformVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/food_inform/listAllFoodInform.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("foodinformVO", foodinformVO); // 資料庫取出的 foodinformVO 物件,存入req
				req.setAttribute("whichPage", whichPage); // 轉交頁數讓返回使用
				String url = "/front-end/food_inform/listOneFoodInform.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneFoodInform.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/food_inform/listAllFoodInform.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("getModal_Display".equals(action)) { // 來自listAllFoodInform.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("fi_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入食訊編號");
				}
				
				String fi_no = null;
				try {
					fi_no = new String(str);
				} catch (Exception e) {
					errorMsgs.add("編號格式不正確");
				}
				
				// Send the use back to the sform, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/food_inform/listAllFoodInform.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				FoodInformService foodinformSvc = new FoodInformService();
				FoodInformVO foodinformVO = foodinformSvc.getOneFoodInform(fi_no);
				if (foodinformVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/food_inform/listAllFoodInform.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("foodinformVO", foodinformVO); // 資料庫取出的 foodinformVO 物件,存入req
				String url = "/front-end/inform_cate/inform_index.jsp";
				//Bootstrap_modal
				boolean openModal1=true;
				req.setAttribute("openModal1",openModal1 );
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneFoodInform.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/food_inform/listAllFoodInform.jsp");
				failureView.forward(req, res);
			}
		}

		
		// 確認
		if ("getOne_For_Update".equals(action)) { // 來自 listAllFoodInform.jsp 的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				String fi_no = new String(req.getParameter("fi_no"));
				String whichPage = req.getParameter("whichPage");
				/***************************2.開始查詢資料****************************************/
				FoodInformService foodinformSvc = new FoodInformService();
				FoodInformVO foodinformVO = foodinformSvc.getOneFoodInform(fi_no);
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("foodinformVO", foodinformVO);         // 資料庫取出的 foodinformVO 物件,存入req
				req.setAttribute("whichPage", whichPage); // 轉交頁數讓返回使用
				String url = "/back-end/food_inform/update_foodinform_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_foodinform_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/food_inform/listAllFoodInform.jsp");
				failureView.forward(req, res);
			}
		}
		
		// 確認 0509維持發文日期不變動
		if ("update".equals(action)) { // 來自 update_foodinform_input.jsp 的請求
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String fi_no = (req.getParameter("fi_no").trim());
				
				String fi_title = req.getParameter("fi_title");
				if (fi_title == null || fi_title.trim().length() == 0) {
					errorMsgs.add("文章標題: 請勿空白");
				} 
				
				String fi_author = req.getParameter("fi_author");
				if (fi_author == null || fi_author.trim().length() == 0) {
					errorMsgs.add("文章作者: 請勿空白");
				}
				
				// 上傳的異常判斷要小心
				byte[] fi_cover = null;
				if (req.getPart("fi_cover").getSize() != 0) {
				fi_cover = Upload.getPictureByteArray(req.getPart("fi_cover"));
				}else{
					FoodInformService foodinformSvc = new FoodInformService();
					fi_cover = (foodinformSvc.readPic(fi_no));
				}
				
				// 上傳的異常判斷小心，待研究做法				
				String fi_content = null;
				fi_content = req.getParameter("fi_content");
				if (fi_content.trim()==null||fi_content.trim().length()==0) {
					errorMsgs.add("文章內容: 請勿空白");					
				}
				
				// 更新後維持日期
				Timestamp fi_date = null;
				if (req.getParameter("fi_date").trim()!=null||req.getParameter("fi_date").trim().length()!=0) {
					fi_date = Timestamp.valueOf(req.getParameter("fi_date").trim());
				}else {
					errorMsgs.add("文章日期: 請勿空白");					
				}
				// 下拉式選單
				Integer fi_status = Integer.parseInt(req.getParameter("fi_status").trim());
				
				FoodInformVO foodinformVO = new FoodInformVO();
				foodinformVO.setFi_no(fi_no);
				foodinformVO.setFi_title(fi_title);
				foodinformVO.setFi_author(fi_author);
				foodinformVO.setFi_cover(fi_cover);				
				foodinformVO.setFi_content(fi_content);
				foodinformVO.setFi_date(fi_date);
				foodinformVO.setFi_status(fi_status);				
				
				String whichPage = req.getParameter("whichPage");
				req.setAttribute("whichPage", whichPage); // 轉交頁數讓返回使用
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("foodinformVO", foodinformVO); // 含有輸入格式錯誤的 foodinformVO 物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/food_inform/update_foodinform_input.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				FoodInformService foodinformSvc = new FoodInformService();
				foodinformVO = foodinformSvc.updateFoodInform(fi_no, fi_title, fi_author, fi_cover, fi_content, fi_date, fi_status);
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("foodinformVO", foodinformVO); // 資料庫update成功後,正確的的 foodinformVO 物件,存入req
				String url = "/back-end/food_inform/listAllFoodInform.jsp?whichPage="+whichPage;
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交 listOneFoodInform.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/food_inform/update_foodinform_input.jsp");
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
				String fi_title = req.getParameter("fi_title");
				if (fi_title == null || fi_title.trim().length() == 0) {
					errorMsgs.add("文章標題: 請勿空白");
				} 
				
				String fi_author = req.getParameter("fi_author");
				if (fi_author == null || fi_author.trim().length() == 0) {
					errorMsgs.add("文章作者: 請勿空白");
				} 
				// 上傳的異常判斷小心
				byte[] fi_cover = null;
				if (req.getPart("fi_cover").getSize() == 0) {				
					errorMsgs.add("文章封面: 請勿空白");
				}				
				fi_cover = Upload.getPictureByteArray(req.getPart("fi_cover"));
				
				// 上傳的異常判斷小心			
				String fi_content = null;
				fi_content = req.getParameter("fi_content");
				if (fi_content.trim()==null||fi_content.trim().length()==0) {
					errorMsgs.add("文章內容: 請勿空白");					
				}
				
				FoodInformVO foodinformVO = new FoodInformVO();
				foodinformVO.setFi_title(fi_title);
				foodinformVO.setFi_author(fi_author);
				foodinformVO.setFi_cover(fi_cover);				
				foodinformVO.setFi_content(fi_content);				

				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("foodinformVO", foodinformVO); // 含有輸入格式錯誤的 foodinformVO 物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/food_inform/addFoodInform.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				FoodInformService foodinformSvc = new FoodInformService();
				foodinformVO = foodinformSvc.addFoodInform(fi_title, fi_author, fi_cover, fi_content);
				req.setAttribute("foodinformVO", foodinformVO);
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/back-end/food_inform/listAllFoodInform.jsp?whichPage=9999";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交 listAllFoodInform.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				e.printStackTrace();
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/food_inform/addFoodInform.jsp");
				failureView.forward(req, res);
			}
		}

        // 確認從delete改成下架
		if ("AJAX_For_Status".equals(action)) { // 來自 listAllFoodInform.jsp
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.接收請求參數***************************************/
				// 要小心參數名稱，如果跟前端相異會取到空值
				String fi_no = new String(req.getParameter("fi_no"));
				Integer fi_status = Integer.parseInt(req.getParameter("fi_status"));
				/***************************2.開始修改資料***************************************/
				FoodInformService foodinformSvc = new FoodInformService();
				foodinformSvc.changeStatus(fi_no , fi_status);
				
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
