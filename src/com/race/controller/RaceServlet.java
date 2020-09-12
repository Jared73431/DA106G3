package com.race.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.race.model.*;
// 注意可能會用爬蟲 故大部分用字串，如果改靜態呈現就換回正常型別
public class RaceServlet extends HttpServlet{
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		// 確認0403 展示暫時關閉
//		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求
//			List<String> errorMsgs = new LinkedList<String>();
//			// Store this set in the request scope, in case we need to
//			// send the ErrorPage view.
//			req.setAttribute("errorMsgs", errorMsgs);
//
//			try {
//				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
//				String race_no = req.getParameter("race_no");
//				if (race_no == null || (race_no.trim()).length() == 0) {
//					errorMsgs.add("請輸入比賽年度");
//				}
//				// Send the use back to the form, if there were errors
//				if (!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req
//							.getRequestDispatcher("/back-end/race/select_page.jsp");
//					failureView.forward(req, res);
//					return;//程式中斷
//				}
//				
//				/***************************2.開始查詢資料*****************************************/
//				RaceService raceSvc = new RaceService();
//				List<RaceVO> raceVO = raceSvc.getRace(race_no);
//				if (raceVO == null) {
//					errorMsgs.add("查無資料");
//				}
//				// Send the use back to the form, if there were errors
//				if (!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req
//							.getRequestDispatcher("/back-end/race/select_page.jsp");
//					failureView.forward(req, res);
//					return;//程式中斷
//				}
//				
//				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
//				req.setAttribute("raceVO", raceVO); // 資料庫取出的 raceVO 物件,存入req
//				String url = "/back-end/race/listAllRace.jsp";				
//				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneRace.jsp
//				successView.forward(req, res);
//
//				/***************************其他可能的錯誤處理*************************************/
//			} catch (Exception e) {
//				errorMsgs.add("無法取得資料:" + e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/back-end/race/select_page.jsp");
//				failureView.forward(req, res);
//			}
//		}
		
		// 0424新增前端使用 0509新增錯誤防止
		if ("getOne_Race_Display".equals(action)) { // 來自listAllRace.jsp的請求
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String race_no = req.getParameter("race_no");
				if (race_no == null || (race_no.trim()).length() == 0) {
					errorMsgs.add("請輸入比賽編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/race/listAllRace.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				RaceService raceSvc = new RaceService();
				List<RaceVO> raceVO = raceSvc.getRace(race_no);
				if (raceVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/race/listAllRace.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("raceVO", raceVO); // 資料庫取出的 raceVO 物件,存入req
				req.setAttribute("race_no", race_no); // 提供頁面Select使用
				String url = "/front-end/race/listHisRace.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listHisRace.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/race/listAllRace.jsp");
				failureView.forward(req, res);
			}
		}
		
		// 0403確認 展示暫時關閉
//		if ("getOne_For_Update".equals(action)) { // 來自 listAllGym.jsp 的請求
//
//			List<String> errorMsgs = new LinkedList<String>();
//			// Store this set in the request scope, in case we need to
//			// send the ErrorPage view.
//			req.setAttribute("errorMsgs", errorMsgs);
//			
//			try {
//				/***************************1.接收請求參數****************************************/
//				String race_no = new String(req.getParameter("race_no"));
//				/***************************2.開始查詢資料****************************************/
//				RaceService raceSvc = new RaceService();
//				RaceinformVO raceinformVO = raceSvc.getOneRace(race_no);
//								
//				/***************************3.查詢完成,準備轉交(Send the Success view)************/
//				req.setAttribute("raceinformVO", raceinformVO);         // 資料庫取出的 raceVO 物件,存入req
//				String url = "/back-end/race/update_race_input.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_race_input.jsp
//				successView.forward(req, res);
//
//				/***************************其他可能的錯誤處理**********************************/
//			} catch (Exception e) {
//				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/back-end/race/select_page.jsp");
//				failureView.forward(req, res);
//			}
//		}
		
		// 0403確認 (展示暫時關閉)
//		if ("update".equals(action)) { // 來自 update_race_input.jsp 的請求
//			List<String> errorMsgs = new LinkedList<String>();
//			// Store this set in the request scope, in case we need to
//			// send the ErrorPage view.
//			req.setAttribute("errorMsgs", errorMsgs);
//		
//			try {
//				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
//				String race_no = new String(req.getParameter("race_no").trim());
//				
//				String cate_no = new String(req.getParameter("cate_no").trim());
//				
//				// 使用Select
//				String race_year = req.getParameter("race_year");
//				if (race_year == null || race_year.trim().length() == 0) {
//					errorMsgs.add("比賽年度: 請勿空白");
//				}
//				
//				String race_inform = JsoupRace.raceinform(race_year);
//				if (race_inform == null || race_inform.trim().length() == 0) {
//					errorMsgs.add("請確認程式");
//				}
//				
//				RaceinformVO raceinformVO = new RaceinformVO();
//				raceinformVO.setRace_no(race_no);
//				raceinformVO.setCate_no(cate_no);				
//				raceinformVO.setRace_year(race_year);
//				raceinformVO.setRace_inform(race_inform);
//
//				// Send the use back to the form, if there were errors
//				if (!errorMsgs.isEmpty()) {
//					req.setAttribute("raceinformVO", raceinformVO); // 含有輸入格式錯誤的 raceVO 物件,也存入req
//					RequestDispatcher failureView = req
//							.getRequestDispatcher("/back-end/race/update_race_input.jsp");
//					failureView.forward(req, res);
//					return; //程式中斷
//				}
//				
//				/***************************2.開始修改資料*****************************************/
//				RaceService raceSvc = new RaceService();
//				raceinformVO = raceSvc.updateRace(race_no,cate_no,race_year,race_inform);
//				
//				/***************************3.修改完成,準備轉交(Send the Success view)*************/
//				req.setAttribute("raceinformVO", raceinformVO); // 資料庫update成功後,正確的的 raceVO 物件,存入req
//				String url = "/back-end/race/select_page.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交 listOneRace.jsp
//				successView.forward(req, res);
//
//				/***************************其他可能的錯誤處理*************************************/
//			} catch (Exception e) {
//				errorMsgs.add("修改資料失敗:"+e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/back-end/race/update_race_input.jsp");
//				failureView.forward(req, res);
//			}
//		}
		// 0403確認 展示暫時關閉
//        if ("insert".equals(action)) { // 來自 addRace.jsp的請求  
//			
//			List<String> errorMsgs = new LinkedList<String>();
//			// Store this set in the request scope, in case we need to
//			// send the ErrorPage view.
//			req.setAttribute("errorMsgs", errorMsgs);
//
//			try {
//				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
//				String race_year = req.getParameter("year");
//				if (race_year == null || race_year.trim().length() == 0) {
//					errorMsgs.add("比賽名稱: 請勿空白");
//				}
//								
//				String race_inform = JsoupRace.raceinform(race_year);
//				if (race_inform == null || race_inform.trim().length() == 0) {
//					errorMsgs.add("請確認程式運作.");
//				}
//				
//				RaceinformVO raceinformVO = new RaceinformVO();
//				raceinformVO.setRace_year(race_year);
//				raceinformVO.setRace_inform(race_inform);
//				
//				// Send the use back to the form, if there were errors
//				if (!errorMsgs.isEmpty()) {
//					req.setAttribute("raceinformVO", raceinformVO); // 含有輸入格式錯誤的 raceVO 物件,也存入req
//					RequestDispatcher failureView = req
//							.getRequestDispatcher("/back-end/race/addRace.jsp");
//					failureView.forward(req, res);
//					return;
//				}
//				
//				/***************************2.開始新增資料***************************************/
//				RaceService raceSvc = new RaceService();
//				raceinformVO = raceSvc.addRace(race_year, race_inform);
//				
//				/***************************3.新增完成,準備轉交(Send the Success view)***********/
//				String url = "/back-end/race/select_page.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交 listAllRace.jsp
//				successView.forward(req, res);				
//				
//				/***************************其他可能的錯誤處理**********************************/
//			} catch (Exception e) {
//				errorMsgs.add(e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/back-end/race/addRace.jsp");
//				failureView.forward(req, res);
//			}
//		}
//		
	}

}
