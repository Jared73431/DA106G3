package com.coach.controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coach.model.CoachService;
import com.coach.model.CoachVO;
import com.cour_booking.model.Cour_BookingService;
import com.cour_booking.model.Cour_BookingVO;
import com.course.model.CourseService;
import com.course.model.CourseVO;
import com.members.model.*;
import com.transactions.model.TransactionsService;
@MultipartConfig
public class CoachServlet extends HttpServlet {
	private static final String MembersVO  = null;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		if ("getOne_For_Display".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			String requestURL = req.getParameter("requestURL");
			req.setAttribute("errorMsgs", errorMsgs);
			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/

			try {
				String str = req.getParameter("coa_no");
				if (str == null || (str.trim().length() == 0)) {
					errorMsgs.add("請輸入教練編號");
				}

				String coa_no = null;
				try {
					coa_no = new String(str);
				} catch (Exception e) {
					errorMsgs.add("教練編號格式不正確");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
					failureView.forward(req, res);
					return;
				}
				/****************************
				 * 2.開始查詢資料
				 ******************************************/
				CoachService coachSvc = new CoachService();
				CoachVO coachVO = coachSvc.getOneCoach(coa_no);
				if (coachVO == null) {
					errorMsgs.add("查無資料");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
					failureView.forward(req, res);
					return;
				}
				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("coachVO", coachVO);
				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				/*************************** 其他可能的錯誤處理 *************************************/

			} catch (Exception e) {

				errorMsgs.add("無法拿到資料" + e.getMessage());
//				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/coach/select_page.jsp");
//				failureView.forward(req, res);
			}
		}
		if ("getOne_For_Update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();

			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String coa_no = new String(req.getParameter("coa_no").trim());
				/*************************** 2.開始查詢資料 ****************************************/
				CoachService coachSvc = new CoachService();
				CoachVO coachVO = coachSvc.getOneCoach(coa_no);
				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("coachVO", coachVO);
				String url = "/front-end/coach/update_coach_input.jsp";
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
				String coa_no = new String(req.getParameter("coa_no"));
				String member_no = new String(req.getParameter("member_no"));
				String coa_info = req.getParameter("coa_info").trim();

				if (coa_info == null || coa_info.length() == 0) {
					errorMsgs.add("自我介紹請勿空白");
				} else if (coa_info.length() > 200) {
					errorMsgs.add("簡介請在200字以內");
				}

				String service_area = req.getParameter("service_area").trim();

				if (service_area == null || service_area.length() == 0) {
					errorMsgs.add("服務地區請勿空白");
				} else if (service_area.length() > 200) {
					errorMsgs.add("簡介請在100字以內");
				}
				String coa_skill1 = new String(req.getParameter("coa_skill1"));
				if (coa_skill1.length() > 30) {
					errorMsgs.add("教練專長1欄位請在30字內");
				}

				String coa_skill2 = new String(req.getParameter("coa_skill2"));
				if (coa_skill2.length() > 30) {
					errorMsgs.add("教練專長2欄位請在30字內");
				}

				String coa_skill3 = new String(req.getParameter("coa_skill3"));
				if (coa_skill3.length() > 30) {
					errorMsgs.add("教練專長3欄位請在30字內");
				}
				CoachService coachSvc = new CoachService();
				CoachVO coachVO = coachSvc.getOneCoach(coa_no); // 取出這個教練編號的資料庫物件

				byte[] coa_licence1 = null;
				byte[] coa_licence2 = null;
				byte[] coa_licence3 = null;

				long size1 = req.getPart("coa_licence1").getSize();
				long size2 = req.getPart("coa_licence2").getSize();
				long size3 = req.getPart("coa_licence3").getSize();

				if (size1 != 0) {
					try (InputStream in = req.getPart("coa_licence1").getInputStream();
							BufferedInputStream bis = new BufferedInputStream(in);
							ByteArrayOutputStream bos = new ByteArrayOutputStream();) {
						byte[] b = new byte[4 * 1024];
						int len = 0;
						while ((len = bis.read(b)) != -1) {
							bos.write(b, 0, len);
						}
						coa_licence1 = bos.toByteArray();
					} catch (Exception e) {
						System.out.println("GG1");
					}
				} else {
					coa_licence1 = coachVO.getCoa_licence1(); // 這邊會拿到資料庫的byte[]
				}

				if (size2 != 0) {
					try (InputStream in = req.getPart("coa_licence2").getInputStream();
							BufferedInputStream bis = new BufferedInputStream(in);
							ByteArrayOutputStream bos = new ByteArrayOutputStream();) {
						byte[] b = new byte[4 * 1024];
						int len = 0;
						while ((len = bis.read(b)) != -1) {
							bos.write(b, 0, len);
						}
						coa_licence2 = bos.toByteArray();
					} catch (Exception e) {
						System.out.println("GG2");
					}
				} else {
					coa_licence2 = coachVO.getCoa_licence2();
				}

				if (size3 != 0) {
					try (InputStream in = req.getPart("coa_licence3").getInputStream();
							BufferedInputStream bis = new BufferedInputStream(in);
							ByteArrayOutputStream bos = new ByteArrayOutputStream();) {
						byte[] b = new byte[4 * 1024];
						int len = 0;
						while ((len = bis.read(b)) != -1) {
							bos.write(b, 0, len);
						}
						coa_licence3 = bos.toByteArray();
					} catch (Exception e) {
						System.out.println("GG3");
					}
				} else {
					coa_licence3 = coachVO.getCoa_licence3();

				}

				Integer coa_status = coachVO.getCoa_status();

				coachVO.setCoa_no(coa_no);
				coachVO.setMember_no(member_no);
				coachVO.setCoa_info(coa_info);
				coachVO.setService_area(service_area);
				coachVO.setCoa_skill1(coa_skill1);
				coachVO.setCoa_skill2(coa_skill2);
				coachVO.setCoa_skill2(coa_skill3);
				coachVO.setCoa_licence1(coa_licence1);
				coachVO.setCoa_licence1(coa_licence2);
				coachVO.setCoa_licence1(coa_licence3);
				coachVO.setCoa_status(coa_status);
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("coachVO", coachVO);
					RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
					failureView.forward(req, res);
					return;
				}
				/*************************** 2.開始修改資料 *****************************************/

				coachVO = coachSvc.updateCoach(member_no, coa_info, service_area, coa_skill1, coa_licence1, coa_skill2,
						coa_licence2, coa_skill3, coa_licence3, coa_status, coa_no);
				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
				req.setAttribute("coachVO", coachVO);
				String url = "/front-end/coach/coachInfo.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				/*************************** 其他可能的錯誤處理 *************************************/

			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/coach/update_coach_input.jsp");
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

				CoachService coachSvc = new CoachService();

				if (coachSvc.getCheckStatus(member_no) != null) {
					if (coachSvc.getCheckStatus(member_no).equals("2")) {
						errorMsgs.add("此會員的教練資格已被停權");
					} else {
						errorMsgs.add("此會員已有教練資格");
					}
				}

				String coa_info = req.getParameter("coa_info");

				String service_area =req.getParameter("service_area");

				String coa_skill1 = "教練專長1";

				String coa_skill2 = "教練專長2";

				String coa_skill3 = "教練專長3";

				CoachVO coachVO = new CoachVO();

				byte[] coa_licence1 = null;
				
				MembersService membersSvc = new MembersService();
				MembersVO membersVO = membersSvc.getOneMembers(member_no);

				coa_licence1 = membersVO.getLicense();
				
				coachVO.setMember_no(member_no);
				coachVO.setCoa_info(coa_info);
				coachVO.setService_area(service_area);
				coachVO.setCoa_skill1(coa_skill1);
				coachVO.setCoa_skill2(coa_skill2);
				coachVO.setCoa_skill2(coa_skill3);
				coachVO.setCoa_licence1(coa_licence1);


				if (!errorMsgs.isEmpty()) {
					req.setAttribute("coachVO", coachVO);
					RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
					failureView.forward(req, res);
					return;
				}
				/*************************** 2.開始修改資料 *****************************************/

				coachVO = coachSvc.addCoach(member_no, coa_info, service_area, coa_skill1, coa_licence1, coa_skill2,
						 coa_skill3);
				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
				req.setAttribute("coachVO", coachVO);
				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				/*************************** 其他可能的錯誤處理 *************************************/

			} catch (Exception e) {

				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/coach/addCoach.jsp");
				failureView.forward(req, res);
			}
		}
		if("fail".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();

			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");

			try {
				String member_no = req.getParameter("member_no");
				CoachService coachSvc = new CoachService();
				/*************************** 2.開始修改資料 *****************************************/
				
				coachSvc.clearLicense(member_no);
				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		

		if ("punish".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();

			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");
			try {
				CoachService coachSvc = new CoachService();
				String coa_no = new String(req.getParameter("coa_no"));
				boolean openModal=true;
				req.setAttribute("openModal",openModal);
				/*************************** 2.開始修改資料 *****************************************/

				coachSvc.punishUpdate(coa_no);
				CoachVO coachVO = coachSvc.getOneCoach(coa_no);
				
				CourseService courseSvc = new CourseService();
				List<CourseVO> courseList = courseSvc.getAll();
				
				List <CourseVO> thisCoachList = courseList.stream()
														.filter(e -> e.getCoa_no().equals(coa_no))
														.filter(e -> e.getCour_status()!=4)
														.collect(Collectors.toList());//將所有這個教練的課程裝進list(狀態不為結案)
				
				List <String> orderList = thisCoachList.stream()
														.filter(e -> e.getCour_status()==3)
														.map(e -> e.getCour_no())
														.collect(Collectors.toList()); //將此教練的有被預約課程挑出
System.out.println(orderList);
				Cour_BookingService cour_bookingSvc = new Cour_BookingService();
				Cour_BookingVO cour_bookingVO = null;
				String booking_no = null;
				String member_no = null;
				Integer real_blance = null;
				MembersService membersSvc = new MembersService();
				boolean result;
				
				for(String orderCour_no : orderList) {	

					booking_no = courseSvc.updateComf(orderCour_no, 3, 2); //將教練確認改為取消  預約人改為未確認
					cour_bookingVO = cour_bookingSvc.getOneBooking(booking_no);
					member_no = cour_bookingVO.getMember_no();   //找出此預約人的會員編號
					
					MembersVO membersVO = membersSvc.getOneMembers(member_no);
					real_blance = membersVO.getReal_blance();  //拿出預約人的儲值金餘額
					
					real_blance += 2000;
					result = cour_bookingSvc.refund(member_no, real_blance,2000,0,"課程退款"); //呼叫退費方法  先新增交易紀錄  在修改會員餘額

					if(result != true) {
						errorMsgs.add("退費失敗");
					}
				}
				

				
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("coachVO", coachVO);
					RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
					failureView.forward(req, res);
					return;
				}
				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
				for(CourseVO courseVO :thisCoachList) {
					courseSvc.updateStatus(courseVO.getCour_no(), 2);  //將這些課程通通改成下架
				}
				
				req.setAttribute("coachVO", coachVO);
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
