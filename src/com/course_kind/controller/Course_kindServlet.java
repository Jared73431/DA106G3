package com.course_kind.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.course_kind.model.Course_kindService;
import com.course_kind.model.Course_kindVO;

public class Course_kindServlet extends HttpServlet {
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
				String str = req.getParameter("cour_type_no");

				if (str == null || (str.trim().length() == 0)) {
					errorMsgs.add("請輸入課程編號");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/backstage/index.jsp");
					failureView.forward(req, res);
					return;
				}
				String cour_type_no = null;
				try {
					cour_type_no = new String(str);
				} catch (Exception e) {
					errorMsgs.add("課程編號格式不正確");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/backstage/index.jsp");
					failureView.forward(req, res);
					return;
				}
				/****************************
				 * 2.開始查詢資料
				 ******************************************/
				Course_kindService course_kindSvc = new Course_kindService();
				Course_kindVO course_kindVO = course_kindSvc.getCourse_kind(cour_type_no);

				if (course_kindVO == null) {
					errorMsgs.add("查無資料");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/backstage/index.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("course_kindVO", course_kindVO);
				String url = "/back-end/course_kind/listOneCourse_kind.jsp";
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

				String cour_type_no = new String(req.getParameter("cour_type_no"));

				/*************************** 2.開始查詢資料 ****************************************/
				Course_kindService course_kindSvc = new Course_kindService();
				Course_kindVO course_kindVO = course_kindSvc.getCourse_kind(cour_type_no);
				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("course_kindVO", course_kindVO);
				String url = "/back-end/course_kind/update_course_kind_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {

				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();

			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");
			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String cour_type_no = new String(req.getParameter("cour_type_no"));

				String cour_type = req.getParameter("cour_type").trim();
				

				if (cour_type == null || cour_type.trim().length() == 0) {
					errorMsgs.add("功能請勿空白");
				} else if (!(cour_type.length()<20)) {
					errorMsgs.add("且長度需20之內");
				}

				Course_kindVO course_kindVO = new Course_kindVO();

				course_kindVO.setCour_type_no(cour_type_no);
				course_kindVO.setCour_type(cour_type);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("course_kindVO", course_kindVO);
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/course_kind/update_course_kind_input.jsp");
					failureView.forward(req, res);
					return;
				}
				/*************************** 2.開始修改資料 *****************************************/
				Course_kindService course_kindSvc = new Course_kindService();

				course_kindVO = course_kindSvc.updateCourse_kind(cour_type_no, cour_type);

				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/

				req.setAttribute("course_kindVO", course_kindVO);
				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				/*************************** 其他可能的錯誤處理 *************************************/

			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/course_kind/update_course_kind_input.jsp");
				failureView.forward(req, res);
			}
		}
		if ("insert".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				String cour_type = req.getParameter("cour_type").trim();


				if (cour_type == null || cour_type.trim().length() == 0) {
					errorMsgs.add("功能請勿空白");
				} else if (!(cour_type.length()<20)) {
					errorMsgs.add("且長度需20之內");
				}

				Course_kindVO course_kindVO = new Course_kindVO();

				course_kindVO.setCour_type_no(cour_type);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("course_kindVO", course_kindVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/course_kind/addCourse_kind.jsp");
					failureView.forward(req, res);
					return;
				}
				/*************************** 2.開始修改資料 *****************************************/
				Course_kindService course_kindSvc = new Course_kindService();

				course_kindVO = course_kindSvc.addCourse_kind(cour_type);

				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/

				req.setAttribute("course_kindVO", course_kindVO);
				String url = "/back-end/course_kind/listAllCourse_kind.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				/*************************** 其他可能的錯誤處理 *************************************/

			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/course_kind/addCourse_kind.jsp");
				failureView.forward(req, res);
			}
		}
		if ("delete".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");
			try {
				/*************************** 1.接收請求參數 ***************************************/
				String cour_type_no = req.getParameter("cour_type_no");

				/*************************** 2.開始刪除資料 ***************************************/
				Course_kindService course_kindSvc = new Course_kindService();
				course_kindSvc.delete(cour_type_no);
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
