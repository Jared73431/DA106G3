package com.newsknowledge.controller;

import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;

import com.foodinform.model.FoodInformService;
import com.newsknowledge.model.*;
import com.util.tool.*;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class NewsKnowledgeServlet extends HttpServlet{
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		// 確認
		if ("getOne_For_Display".equals(action)) { // 來自listAllNewsKnowledge.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("news_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入運動新知編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/news_knowledge/listAllNewsKnowledge.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				String news_no = null;
				try {
					news_no = new String(str);
				} catch (Exception e) {
					errorMsgs.add("編號格式不正確");
				}
				// Send the use back to the sform, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/news_knowledge/listAllNewsKnowledge.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				NewsKnowledgeService newsknowledgeSvc = new NewsKnowledgeService();
				NewsKnowledgeVO newsknowledgeVO = newsknowledgeSvc.getOneNewsKnowledge(news_no);
				if (newsknowledgeVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/news_knowledge/listAllNewsKnowledge.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("newsknowledgeVO", newsknowledgeVO); // 資料庫取出的 newsknowledgeVO 物件,存入req
				String url = "/back-end/news_knowledge/listOneNewsKnowledge.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneNewKnowledge.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/news_knowledge/listAllNewsKnowledge.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("getOne_Display".equals(action)) { // 來自前台listAllNewsKnowledge.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String whichPage = req.getParameter("whichPage");
				String str = req.getParameter("news_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入運動新知編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/news_knowledge/listAllNewsKnowledge.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				String news_no = null;
				try {
					news_no = new String(str);
				} catch (Exception e) {
					errorMsgs.add("編號格式不正確");
				}
				// Send the use back to the sform, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/news_knowledge/listAllNewsKnowledge.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				NewsKnowledgeService newsknowledgeSvc = new NewsKnowledgeService();
				NewsKnowledgeVO newsknowledgeVO = newsknowledgeSvc.getOneNewsKnowledge(news_no);
				if (newsknowledgeVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/news_knowledge/listAllNewsKnowledge.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("newsknowledgeVO", newsknowledgeVO); // 資料庫取出的 newsknowledgeVO 物件,存入req
				req.setAttribute("whichPage", whichPage); // 轉交頁數讓返回使用
				String url = "/front-end/news_knowledge/listOneNewsKnowledge.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneNewKnowledge.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/news_knowledge/listAllNewsKnowledge.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("getModal_Display".equals(action)) { // 來自前台listAllNewsKnowledge.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String whichPage = req.getParameter("whichPage");
				String str = req.getParameter("news_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入運動新知編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/news_knowledge/listAllNewsKnowledge.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				String news_no = null;
				try {
					news_no = new String(str);
				} catch (Exception e) {
					errorMsgs.add("編號格式不正確");
				}
				// Send the use back to the sform, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/news_knowledge/listAllNewsKnowledge.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				NewsKnowledgeService newsknowledgeSvc = new NewsKnowledgeService();
				NewsKnowledgeVO newsknowledgeVO = newsknowledgeSvc.getOneNewsKnowledge(news_no);
				if (newsknowledgeVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/news_knowledge/listAllNewsKnowledge.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("newsknowledgeVO", newsknowledgeVO); // 資料庫取出的 newsknowledgeVO 物件,存入req
				req.setAttribute("whichPage", whichPage); // 轉交頁數讓返回使用
				//Bootstrap_modal
				boolean openModal2=true;
				req.setAttribute("openModal2",openModal2 );
				String url = "/front-end/inform_cate/inform_index.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneNewKnowledge.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/news_knowledge/listAllNewsKnowledge.jsp");
				failureView.forward(req, res);
			}
		}
		
		// 確認
		if ("getOne_For_Update".equals(action)) { // 來自 listAllNewsKnowledge.jsp 的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				String news_no = new String(req.getParameter("news_no"));
				String whichPage = req.getParameter("whichPage");
				/***************************2.開始查詢資料****************************************/
				NewsKnowledgeService newsknowledgeSvc = new NewsKnowledgeService();
				NewsKnowledgeVO newsknowledgeVO = newsknowledgeSvc.getOneNewsKnowledge(news_no);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("newsknowledgeVO", newsknowledgeVO);         // 資料庫取出的 newsknowledgeVO 物件,存入req
				req.setAttribute("whichPage", whichPage); // 轉交頁數讓返回使用
				String url = "/back-end/news_knowledge/update_newsknowledge_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_newsknowledge_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/news_knowledge/listAllNewsKnowledge.jsp");
				failureView.forward(req, res);
			}
		}
		
		// 確認 0509 改成維持日期
		if ("update".equals(action)) { // 來自 update_newsknowledge_input.jsp 的請求
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String news_no = (req.getParameter("news_no").trim());

				String news_title = req.getParameter("news_title");
				if (news_title == null || news_title.trim().length() == 0) {
					errorMsgs.add("文章標題: 請勿空白");
				} 
				
				String news_author = req.getParameter("news_author");
				if (news_author == null || news_author.trim().length() == 0) {
					errorMsgs.add("文章作者: 請勿空白");
				} 
				// 上傳的異常判斷小心				
				byte[] news_cover = null;
				if (req.getPart("news_cover").getSize() != 0) {
				news_cover = Upload.getPictureByteArray(req.getPart("news_cover"));
				}else{
					NewsKnowledgeService newsknowledgeSvc = new NewsKnowledgeService();
					news_cover = (newsknowledgeSvc.readPic(news_no));
				}				
				
				// 上傳的異常判斷小心，待研究做法				
				String news_content = null;
				if (req.getParameter("news_content").trim()!=null||req.getParameter("news_content").trim().length()!=0) {
					news_content = req.getParameter("news_content");
				}else {
					errorMsgs.add("文章內容: 請勿空白");					
				}
								
				// 更新後刷新修改日期
				Timestamp news_date = null;
				if (req.getParameter("news_date").trim()!=null||req.getParameter("news_date").trim().length()!=0) {
					news_date = Timestamp.valueOf(req.getParameter("news_date").trim());
				}else {
					errorMsgs.add("文章日期: 請勿空白");					
				}

				// 下拉式選單
				Integer news_status = Integer.parseInt(req.getParameter("news_status").trim());

				
				NewsKnowledgeVO newsknowledgeVO = new NewsKnowledgeVO();
				newsknowledgeVO.setNews_no(news_no);
				newsknowledgeVO.setNews_title(news_title);
				newsknowledgeVO.setNews_author(news_author);
				newsknowledgeVO.setNews_cover(news_cover);				
				newsknowledgeVO.setNews_content(news_content);
				newsknowledgeVO.setNews_date(news_date);
				newsknowledgeVO.setNews_status(news_status);				

				String whichPage = req.getParameter("whichPage");
				req.setAttribute("whichPage", whichPage); // 轉交頁數讓返回使用

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("newsknowledgeVO", newsknowledgeVO); // 含有輸入格式錯誤的 newsknowledgeVO 物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/news_knowledge/update_newsknowledge_input.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				NewsKnowledgeService newsknowledgeSvc = new NewsKnowledgeService();
				newsknowledgeVO = newsknowledgeSvc.updateNewsKnowledge(news_no, news_title, news_author, news_cover, news_content, news_date, news_status);
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("newsknowledgeVO", newsknowledgeVO); // 資料庫update成功後,正確的的 newsknowledgeVO 物件,存入req
				String url = "/back-end/news_knowledge/listAllNewsKnowledge.jsp?whichPage="+whichPage;
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交 listAllNewKnowledge.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/news_knowledge/update_newsknowledge_input.jsp");
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
				String news_title = req.getParameter("news_title");
				if (news_title == null || news_title.trim().length() == 0) {
					errorMsgs.add("文章標題: 請勿空白");
				} 
				
				String news_author = req.getParameter("news_author");
				if (news_author == null || news_author.trim().length() == 0) {
					errorMsgs.add("文章作者: 請勿空白");
				} 
				// 上傳的異常判斷小心
				byte[] news_cover = null;
				if (req.getPart("news_cover").getSize() == 0) {
					errorMsgs.add("文章封面: 請勿空白");
				}	
				news_cover = Upload.getPictureByteArray(req.getPart("news_cover"));
				
				// 上傳的異常判斷小心			
				String news_content = null;
				news_content = req.getParameter("news_content");
				if ( news_content == null|| news_content.trim().length() == 0) {
					errorMsgs.add("文章內容: 請勿空白");					
				}
				
				NewsKnowledgeVO newsknowledgeVO = new NewsKnowledgeVO();
				newsknowledgeVO.setNews_title(news_title);
				newsknowledgeVO.setNews_author(news_author);
				newsknowledgeVO.setNews_cover(news_cover);				
				newsknowledgeVO.setNews_content(news_content);				

				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("newsknowledgeVO", newsknowledgeVO); // 含有輸入格式錯誤的 newsknowledgeVO 物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/news_knowledge/addNewsKnowledge.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				NewsKnowledgeService newsknowledgeSvc = new NewsKnowledgeService();
				newsknowledgeVO = newsknowledgeSvc.addNewsKnowledge(news_title, news_author, news_cover, news_content);
				req.setAttribute("newsknowledgeVO", newsknowledgeVO);
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/back-end/news_knowledge/listAllNewsKnowledge.jsp?whichPage=99999";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交 listAllNewsKnowledge.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				e.printStackTrace();
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/news_knowledge/addNewsKnowledge.jsp");
				failureView.forward(req, res);
			}
		}
		
		// 確認從delete改成下架
		if ("AJAX_For_Status".equals(action)) { // 來自 listAllNewsKnowledge.jsp
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.接收請求參數***************************************/
				String news_no = new String(req.getParameter("news_no"));
				Integer news_status = Integer.parseInt(req.getParameter("news_status"));
				/***************************2.開始刪除資料***************************************/
				NewsKnowledgeService newsknowledgeSvc = new NewsKnowledgeService();
				newsknowledgeSvc.changeStatus(news_no, news_status);
				
				/***************************3.AJAX不跳頁,印出字串提示(Send the Success view)***********/								
				res.setContentType("text/html; charset=UTF-8");
				PrintWriter out = res.getWriter();
				out.print("AJAX傳送成功");
				out.close();
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				System.out.println("更新狀態失敗:"+e.getMessage());
			}
		}
	}
}
