package com.exerciseitem.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.exerciseitem.model.*;

public class ExerciseItemServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String exe_no = req.getParameter("exe_no");
				if (exe_no == null || (exe_no.trim()).length() == 0) {
					errorMsgs.add("請輸入運動編號");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/execiseitem/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/execiseitem/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				ExerciseItemService exerciseItemSvc = new ExerciseItemService();
				ExerciseItemVO exerciseItemVO = exerciseItemSvc.getOneExe(exe_no);
				if (exerciseItemVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/exerciseitem/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				req.setAttribute("exerciseItemVO", exerciseItemVO); // 資料庫取出的empVO物件,存入req
				String url = "/back-end/exerciseitem/listOneExe.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/exerciseitem/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		if ("getOne_For_Update".equals(action)) { // 來自listAllEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");
			try {
				String exe_no = req.getParameter("exe_no");

				ExerciseItemService exerciseItemSvc = new ExerciseItemService();
				ExerciseItemVO exerciseItemVO = exerciseItemSvc.getOneExe(exe_no);

				req.setAttribute("exerciseItemVO", exerciseItemVO); // 資料庫取出的empVO物件,存入req
				String url = "/back-end/exerciseitem/update_exe_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/exerciseitem/listAllExe.jsp");
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");
			
			try {
				String exe_no = req.getParameter("exe_no");

				String exe_item = req.getParameter("exe_item").trim();
				if (exe_item == null || exe_item.trim().length() == 0) {
					errorMsgs.add("項目請勿空白");
				}

				Double exe_cal = null;
				try {
					exe_cal = new Double(req.getParameter("exe_cal").trim());
				} catch (NumberFormatException e) {
					exe_cal = 0.0;
					errorMsgs.add("請填數字.");
				}

				ExerciseItemVO exerciseItemVO = new ExerciseItemVO();
				exerciseItemVO.setExe_no(exe_no);
				exerciseItemVO.setExe_item(exe_item);
				exerciseItemVO.setExe_cal(exe_cal);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("exerciseItemVO", exerciseItemVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/exerciseitem/update_exe_input.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				ExerciseItemService exerciseItemSvc = new ExerciseItemService();
				exerciseItemVO = exerciseItemSvc.updateExerciseItem(exe_item, exe_cal, exe_no);

				req.setAttribute("exerciseItemVO", exerciseItemVO); // 資料庫update成功後,正確的的empVO物件,存入req
				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/exerciseitem/update_exe_input.jsp");
				failureView.forward(req, res);
			}
		}
		if ("insert".equals(action)) { // 來自addEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String exe_item = req.getParameter("exe_item").trim();
				if (exe_item == null || exe_item.length() == 0){
					errorMsgs.add("項目請勿空白");
				}

				Double exe_cal = null;
				try {
					exe_cal = new Double(req.getParameter("exe_cal").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("請填數字.");
				}

				ExerciseItemVO exerciseItemVO = new ExerciseItemVO();
				exerciseItemVO.setExe_item(exe_item);
				exerciseItemVO.setExe_cal(exe_cal);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("exerciseItemVO", exerciseItemVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/exerciseitem/addExe.jsp");
					failureView.forward(req, res);
					return;
				}

				ExerciseItemService exerciseItemSvc = new ExerciseItemService();
				exerciseItemVO = exerciseItemSvc.addExerciseItem(exe_item, exe_cal);
				
				req.setAttribute("exeSuccess", true);
				String url = "/front-end/exerciseitem/addExe.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/exerciseitem/addExe.jsp");
				failureView.forward(req, res);
			}
		}
		if ("delete".equals(action)) { // 來自listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			String requestURL = req.getParameter("requestURL"); 
			try {
				String exe_no = req.getParameter("exe_no");

				ExerciseItemService exerciseItemSvc = new ExerciseItemService();
				exerciseItemSvc.deleteExercise(exe_no);

				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/exerciseitem/listAllExe.jsp");
				failureView.forward(req, res);
			}
		}

	}

}
