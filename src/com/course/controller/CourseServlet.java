package com.course.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coach.model.*;
import com.cour_booking.model.Cour_BookingService;
import com.cour_booking.model.Cour_BookingVO;
import com.course.model.CourseService;
import com.course.model.CourseVO;
import com.notificationlist.model.NotificationListService;

import oracle.net.aso.e;

public class CourseServlet extends HttpServlet {
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
				String str = req.getParameter("cour_no");
				if (str == null || (str.trim().length() == 0)) {
					errorMsgs.add("請輸入課程編號");
				}
//				if (!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/course/select_page.jsp");
//					failureView.forward(req, res);
//					return;
//				}
				String cour_no = null;
				try {
					cour_no = new String(str);
				} catch (Exception e) {
					errorMsgs.add("教練編號格式不正確");
				}
//				if (!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/course/select_page.jsp");
//					failureView.forward(req, res);
//					return;
//				}
				/****************************
				 * 2.開始查詢資料
				 ******************************************/
				CourseService courseSvc = new CourseService();
				CourseVO courseVO = courseSvc.getOneCourse(cour_no);
				if (courseVO == null) {
					errorMsgs.add("查無資料");
				}
//				if (!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/course/select_page.jsp");
//					failureView.forward(req, res);
//					return;
//				}
				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("courseVO", courseVO);
				String url = "/front-end/course/listOneCourse.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				/*************************** 其他可能的錯誤處理 *************************************/

			} catch (Exception e) {
				errorMsgs.add("無法拿到資料" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/course/select_page.jsp");
				failureView.forward(req, res);
			}

		}
		if ("getOne_For_Update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			String requestURL = req.getParameter("requestURL");
			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			try {
				String cour_no = new String(req.getParameter("cour_no"));

				/****************************
				 * 2.開始查詢資料
				 ******************************************/
				CourseService courseSvc = new CourseService();
				CourseVO courseVO = courseSvc.getOneCourse(cour_no);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("courseVO", courseVO);
				String url = "/front-end/course/update_course_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				/*************************** 其他可能的錯誤處理 *************************************/

			} catch (Exception e) {
				errorMsgs.add("無法拿到資料" + e.getMessage());
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
				String cour_no = new String(req.getParameter("cour_no"));
				String coa_no = new String(req.getParameter("coa_no"));
				String cour_type_no = new String(req.getParameter("cour_type_no"));

				String cour_name = new String(req.getParameter("cour_name").trim());
				if (cour_name == null || cour_name.length() == 0) {
					errorMsgs.add("課程名稱不得為空");
				} else if (cour_name.length() > 100) {
					errorMsgs.add("課程名稱長度請勿超過100字");
				}

				String cour_addr = new String(req.getParameter("cour_addr").trim());
				Integer cour_mode = new Integer(req.getParameter("cour_mode"));

				CourseService courseSvc = new CourseService();
				List<CourseVO> coachlist = courseSvc.getCoachList(coa_no);// 檢查日期衝突

				java.sql.Timestamp cour_date = null;
				try {

					cour_date = java.sql.Timestamp.valueOf(req.getParameter("cour_date").trim());

				} catch (IllegalArgumentException e) {

					errorMsgs.add("請輸入日期");
				}

				for (CourseVO courseVO : coachlist) {
					if (!courseVO.getCour_no().equals(cour_no)) {

						if (cour_date.getTime() > (courseVO.getCour_date().getTime() + (2*60 * 60*1000))
								|| cour_date.getTime() < (courseVO.getCour_date().getTime() - (2*60 * 60*1000))) {
						} else {
							errorMsgs.add("開課時間間隔不得小於三小時");
							break;
						}
					}
				}

				
				java.sql.Timestamp cour_update = new Timestamp(System.currentTimeMillis());

				java.sql.Timestamp cour_deadline = null;
				try {
					cour_deadline = java.sql.Timestamp.valueOf(req.getParameter("cour_deadline").trim());
				} catch (IllegalArgumentException e) {
					errorMsgs.add("請輸入日期");
				}
				
				Integer cour_status = new Integer(req.getParameter("cour_status"));


				if(cour_status == 3) {
					CourseVO courseVO = courseSvc.getOneCourse(cour_no);
					cour_date = courseVO.getCour_date();
					cour_deadline = courseVO.getCour_deadline();
				}
				
				
				
				
				CourseVO courseVO = new CourseVO();

				courseVO.setCour_no(cour_no);
				courseVO.setCoa_no(coa_no);
				courseVO.setCour_type_no(cour_type_no);
				courseVO.setCour_name(cour_name);
				courseVO.setCour_addr(cour_addr);
				courseVO.setCour_mode(cour_mode);
				courseVO.setCour_date(cour_date);
				courseVO.setCour_update(cour_update);
				courseVO.setCour_status(cour_status);
				courseVO.setCour_deadline(cour_deadline);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("courseVO", courseVO);
					RequestDispatcher failureView = req
							.getRequestDispatcher(requestURL);
					failureView.forward(req, res);
					return;
				}
				/*************************** 2.開始修改資料 *****************************************/

				courseVO = courseSvc.updateCourse(cour_type_no, coa_no, cour_name, cour_addr, cour_mode, cour_date,
						cour_status, cour_update, cour_deadline, cour_no);
				NotificationListService noteSvc = new NotificationListService();
				Cour_BookingService cour_bookingSvc = new Cour_BookingService();
				if(cour_status==3) {  //正在被預約  會將教練狀態改為確認   學員狀態改為未確認
					String booking_no =courseSvc.updateComf(cour_no,1,2);
					noteSvc.addNotification(cour_bookingSvc.getOneBooking(booking_no).getMember_no(),"CA0012", "課程內容變更");
				}
				
				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
				req.setAttribute("courseVO", courseVO);
				String url = "/front-end/coach/coachManageCourse.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				/*************************** 其他可能的錯誤處理 *************************************/

			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}
		if ("insert".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();

			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");
			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String member_no = new String(req.getParameter("member_no"));
				String coa_no = null;

				CoachService coachSvc = new CoachService();

				coa_no = coachSvc.getOneCoachByMember(member_no).getCoa_no();
				String cour_type_no = new String(req.getParameter("cour_type_no"));

				String cour_name = new String(req.getParameter("cour_name").trim());
				if (cour_name == null || cour_name.length() == 0) {
					errorMsgs.add("課程名稱不得為空");
				} else if (cour_name.length() > 100) {
					errorMsgs.add("課程名稱長度請勿超過100字");
				}

				String cour_addr = new String(req.getParameter("cour_addr").trim());
				Integer cour_mode = new Integer(req.getParameter("cour_mode"));

				CourseService courseSvc = new CourseService();
				List<CourseVO> coachlist = courseSvc.getCoachList(coa_no);// 檢查日期衝突
				
				Cour_BookingService cour_bookingSvc = new Cour_BookingService();				
				List<String> bookingList = cour_bookingSvc.getListBooking(null, null, null, member_no)
													.stream()
													.map(e -> e.getCour_no())
													.collect(Collectors.toList());
				
				java.sql.Timestamp cour_date = null;
				try {

					cour_date = java.sql.Timestamp.valueOf(req.getParameter("cour_date").trim());
					for (CourseVO courseVO : coachlist) {
						if (cour_date.getTime() > (courseVO.getCour_date().getTime() + (2*60 * 60*1000))
								|| cour_date.getTime() < (courseVO.getCour_date().getTime() - (2*60 * 60*1000))) {
						} else {
							errorMsgs.add("開課時間間隔不得小於三小時");
							break;
						}
					}
					for(String cour_no : bookingList) {
						long bookingDate = courseSvc.getOneCourse(cour_no).getCour_date().getTime();
						if(cour_date.getTime() > (bookingDate + (2*60*60*1000))||cour_date.getTime() < (bookingDate - (2*60*60*1000))) {
							
						}else {
							errorMsgs.add("與您預約的課程間隔不得小於三小時");
							break;
						}
					}

				} catch (IllegalArgumentException e) {

					errorMsgs.add("請輸入開課日期");
				}

				java.sql.Timestamp cour_update = new Timestamp(System.currentTimeMillis());

				java.sql.Timestamp cour_deadline = null;
				try {
					cour_deadline = java.sql.Timestamp.valueOf(req.getParameter("cour_deadline").trim());
					if(cour_deadline.getTime() > cour_date.getTime()) {
						errorMsgs.add("截止日期須在開課日期前");
					}else if(cour_deadline.getTime() < (cour_date.getTime()-(3 *24* 60*60*1000))) {
						errorMsgs.add("截止日期範圍請設在開課前三天內");
					}
				} catch (IllegalArgumentException e) {
					errorMsgs.add("請輸入截止日期");
				}

				CourseVO courseVO = new CourseVO();

				courseVO.setCoa_no(coa_no);
				courseVO.setCour_type_no(cour_type_no);
				courseVO.setCour_name(cour_name);
				courseVO.setCour_addr(cour_addr);
				courseVO.setCour_mode(cour_mode);
				courseVO.setCour_date(cour_date);
				courseVO.setCour_update(cour_update);
				courseVO.setCour_deadline(cour_deadline);
				
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("courseVO", courseVO);
					RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
					failureView.forward(req, res);
					return;
				}
				/*************************** 2.開始修改資料 *****************************************/

				courseVO = courseSvc.addCourse(cour_type_no, coa_no, cour_name, cour_addr, cour_mode, cour_date,
						 cour_update, cour_deadline);
				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
				req.setAttribute("courseVO", courseVO);
				String url = "/front-end/coach/coachManageCourse.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				/*************************** 其他可能的錯誤處理 *************************************/

			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}
		if ("updown".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();

			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");
			try {
				String cour_no = new String(req.getParameter("cour_no"));

				CourseService courseSvc = new CourseService();
				CourseVO courseVO = courseSvc.getOneCourse(cour_no); // 先找出這個課程編號的課程物件出來

				Integer cour_status = courseVO.getCour_status();

				switch (cour_status) {
				case 1:
					cour_status = 2;
					break;
				case 2:
					cour_status = 1;
					break;
				case 3:
					errorMsgs.add("此課程已被預約");
					break;
				case 4:
					errorMsgs.add("此課程已結案");
					break;
				}

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("courseVO", courseVO);
					RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
					failureView.forward(req, res);
					return;
				}
				/*************************** 2.開始修改資料 *****************************************/

				courseSvc.updateStatus(cour_no, cour_status);
				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
				req.setAttribute("courseVO", courseVO);
				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				/*************************** 其他可能的錯誤處理 *************************************/

			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}

	}
}
