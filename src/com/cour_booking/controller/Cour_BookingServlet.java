package com.cour_booking.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.tribes.membership.McastService;
import org.json.JSONArray;
import org.json.JSONObject;

import com.coach.model.CoachService;
import com.coach.model.CoachVO;
import com.cour_booking.model.Cour_BookingService;
import com.cour_booking.model.Cour_BookingVO;
import com.course.model.CourseService;
import com.course.model.CourseVO;
import com.members.model.MembersService;
import com.members.model.MembersVO;
import com.notificationlist.model.NotificationListService;
import com.transactions.model.TransactionsService;
import com.transactions.model.TransactionsVO;

public class Cour_BookingServlet extends HttpServlet {
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
				String str = req.getParameter("booking_no");

				if (str == null || str.trim().length() == 0) {
					errorMsgs.add("請輸入課程預約編號");
				}
				String booking_no = null;
				try {
					booking_no = new String(str);
				} catch (Exception e) {
					errorMsgs.add("課程預約編號格式不正確");
				}

//				if (!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/backstage/index.jsp");
//					failureView.forward(req, res);
//					return;
//				}
				/****************************
				 * 2.開始查詢資料
				 ******************************************/
				Cour_BookingService cour_bookingSvc = new Cour_BookingService();
				Cour_BookingVO cour_bookingVO = cour_bookingSvc.getOneBooking(booking_no);

				if (cour_bookingVO == null) {
					errorMsgs.add("查無資料");
				}

//				if (!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/backstage/index.jsp");
//					failureView.forward(req, res);
//					return;
//				}
				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("cour_bookingVO", cour_bookingVO);
				String url = "/front-end/cour_booking/listOneCour_Booking.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("無法拿到資料" + e.getMessage());
//				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/backstage/index.jsp");
//				failureView.forward(req, res);
			}
		}
		if ("Coach_For_Update".equals(action) || "Trainee_For_Update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();

			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String booking_no = new String(req.getParameter("booking_no"));
				/*************************** 2.開始查詢資料 ****************************************/
				Cour_BookingService cour_bookingSvc = new Cour_BookingService();
				Cour_BookingVO cour_bookingVO = cour_bookingSvc.getOneBooking(booking_no);
				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("cour_bookingVO", cour_bookingVO);

				String url = null;
				if ("Coach_For_Update".equals(action)) {
					url = "/front-end/cour_booking/coach_update_view.jsp";
				}
				if ("Trainee_For_Update".equals(action)) {
					url = "/front-end/cour_booking/trainee_update_view.jsp";
				}

				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("無法拿到資料" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/cour_booking/courseManage.jsp");
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();

			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");
			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String booking_no = new String(req.getParameter("booking_no"));

				Cour_BookingService cour_bookingSvc = new Cour_BookingService();
				Cour_BookingVO cour_bookingVO = new Cour_BookingVO();
				cour_bookingVO = cour_bookingSvc.getOneBooking(booking_no);
				
				String coa_no = cour_bookingVO.getCoa_no();
				String cour_no = cour_bookingVO.getCour_no();
				String member_no =cour_bookingVO.getMember_no();
				CourseService courseSvc = new CourseService();
				CourseVO courseVO = courseSvc.getOneCourse(cour_no);// 拿出課程物件 為了檢查日期是否可以評分留評價

				Integer coa_comf, trainee_comf, coa_ci, trainee_ci, cour_score, choose_mode;

				if (req.getParameter("coa_comf") == null) {
					coa_comf = cour_bookingVO.getCoa_comf();

				} else {
					coa_comf = new Integer(req.getParameter("coa_comf"));
				}

				if (req.getParameter("trainee_comf") == null) {
					trainee_comf = cour_bookingVO.getTrainee_comf();

				} else {
					trainee_comf = new Integer(req.getParameter("trainee_comf"));
				}

				coa_ci = cour_bookingVO.getCoa_ci();

				trainee_ci = cour_bookingVO.getTrainee_ci();

				if (req.getParameter("cour_score") == null) {
					cour_score = cour_bookingVO.getCour_score();

				} else {

					if (System.currentTimeMillis() > courseVO.getCour_date().getTime()
							&& cour_bookingVO.getTrainee_ci() == 1) {
						cour_score = new Integer(req.getParameter("cour_score"));
					} else if (!(System.currentTimeMillis() > courseVO.getCour_date().getTime())) {
						cour_score = new Integer(req.getParameter("cour_score"));
						if (cour_score != 0) {
							
							errorMsgs.add("課程尚未開始,不得評分");
						}
					} else {
						
						cour_score = new Integer(req.getParameter("cour_score"));
						errorMsgs.add("尚未報到,不得評分");
					}
				}

				if (req.getParameter("choose_mode") == null) {
					choose_mode = cour_bookingVO.getChoose_mode();

				} else {
					choose_mode = new Integer(req.getParameter("choose_mode"));
				}

				String cour_option = null;

				if (req.getParameter("cour_option") == null) {
					cour_option = cour_bookingVO.getCour_option();
				} else {

					if (System.currentTimeMillis() > courseVO.getCour_date().getTime()
							&& cour_bookingVO.getTrainee_ci() == 1) {
						cour_option = req.getParameter("cour_option").trim();
					} else if (!(System.currentTimeMillis() > courseVO.getCour_date().getTime())) {
						cour_option = req.getParameter("cour_option").trim();
						if (cour_option.length() != 0) {
							errorMsgs.add("課程尚未開始,不得評價");
						}
					} else {
						cour_option = req.getParameter("cour_option").trim();
						errorMsgs.add("尚未報到,不得評價");
					}
				}

				cour_bookingVO.setBooking_no(booking_no);
				cour_bookingVO.setCoa_no(coa_no);
				cour_bookingVO.setMember_no(member_no);
				cour_bookingVO.setCour_no(cour_no);
				cour_bookingVO.setCoa_comf(coa_comf);
				cour_bookingVO.setTrainee_comf(trainee_comf);
				cour_bookingVO.setCoa_ci(coa_ci);
				cour_bookingVO.setTrainee_ci(trainee_ci);
				cour_bookingVO.setCour_score(cour_score);
				cour_bookingVO.setChoose_mode(choose_mode);
				cour_bookingVO.setCour_option(cour_option);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("cour_bookingVO", cour_bookingVO);
					RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
					failureView.forward(req, res);
					return;
				}

				cour_bookingVO = cour_bookingSvc.updateBooking(cour_no, coa_no, member_no, coa_comf, trainee_comf,
						coa_ci, trainee_ci, cour_score, cour_option, choose_mode, booking_no);
				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
				req.setAttribute("cour_bookingVO", cour_bookingVO);
				String url = "/front-end/cour_booking/courseManage.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}
		if ("getSelect".equals(action)) { // for AJAX
			try {

				String cour_no = req.getParameter("cour_no");

				Integer cour_mode;
				JSONArray array = new JSONArray();

				if (!"".equals(cour_no)) {

					CourseService courseSvc = new CourseService();
					CourseVO courseVO = courseSvc.getOneCourse(cour_no);

					cour_mode = courseVO.getCour_mode();
					JSONObject obj = null;
					switch (cour_mode) {
					case 1:
						obj = new JSONObject();
						obj.put("choose_mode", 1);
						obj.put("choose_info", "網路");
						array.put(obj);
						break;
					case 2:
						obj = new JSONObject();
						obj.put("choose_mode", 2);
						obj.put("choose_info", "面授");
						array.put(obj);
						break;
					case 3:
						for (int i = 1; i < 3; i++) {
							String str = null;
							obj = new JSONObject();
							obj.put("choose_mode", i);
							if (i == 1) {
								str = "網路";
							} else {
								str = "面授";
							}
							obj.put("choose_info", str);
							array.put(obj);
						}
						break;
					}
					res.setContentType("text/plain");
					res.setCharacterEncoding("UTF-8");
					PrintWriter out = res.getWriter();
					out.write(array.toString());
					out.flush();
					out.close();
				}

			} catch (Exception e) {

			}
		}
		if ("getJson".equals(action)) { // for fullcalendar
			try {

				String member_no = req.getParameter("member_no");

				JSONArray array = new JSONArray();

				if (!"".equals(member_no)) {
					Cour_BookingService cour_bookingSvc = new Cour_BookingService();
					List<Cour_BookingVO> list = cour_bookingSvc.getListBooking(null, null, null, member_no);

					CourseService courseSvc = new CourseService();
					CoachService coachSvc = new CoachService();
					JSONObject obj = null;

					for (Cour_BookingVO cour_bookingVO : list) { // 學員的預約課程
						String booking_no = cour_bookingVO.getBooking_no();
						String cour_name = courseSvc.getOneCourse((cour_bookingVO.getCour_no())).getCour_name();
						java.sql.Timestamp cour_date = courseSvc.getOneCourse((cour_bookingVO.getCour_no()))
								.getCour_date();
						String url =req.getContextPath()+"/cour_booking/cour_booking.do?action=Trainee_For_Update&booking_no="
								+ booking_no;
						String color ;
						if(cour_bookingVO.getCoa_comf() == 3 || cour_bookingVO.getTrainee_comf() == 3) {
							color = "#FFC107";
						}else {
							color = "lightblue";
						}
						obj = new JSONObject();
						obj.put("id", booking_no);
						obj.put("title", cour_name);
						obj.put("start", cour_date);
						obj.put("url", url);
						obj.put("color", color);
						array.put(obj);
					}
					MembersService memberSvc = new MembersService();
					Integer coa_qualifications = memberSvc.getOneMembers(member_no).getCoa_qualifications();
					Integer coa_status;
					if(coa_qualifications==1) {
						coa_status = coachSvc.getOneCoachByMember(member_no).getCoa_status();
					}else {
						coa_status=0;
					}
					
					
					if (coa_qualifications == 1&& coa_status==1) {
						String coa_no = coachSvc.getOneCoachByMember(member_no).getCoa_no();
						List<Cour_BookingVO> coachOrderList = cour_bookingSvc.getListBooking(null, null, coa_no, null)
								.stream().filter(e -> e.getCoa_comf() != 3).filter(e -> e.getTrainee_comf() != 3)
								.collect(Collectors.toList());
						for (Cour_BookingVO cour_bookingVO : coachOrderList) { // 教練的被預約課程
							String booking_no = cour_bookingVO.getBooking_no();
							String cour_name = courseSvc.getOneCourse((cour_bookingVO.getCour_no())).getCour_name();
							java.sql.Timestamp cour_date = courseSvc.getOneCourse((cour_bookingVO.getCour_no()))
									.getCour_date();
							String url = req.getContextPath()+"/cour_booking/cour_booking.do?action=Coach_For_Update&booking_no="
									+ booking_no;

							String color = "red";

							obj = new JSONObject();
							obj.put("id", booking_no);
							obj.put("title", cour_name);
							obj.put("start", cour_date);
							obj.put("url", url);
							obj.put("color", color);
							array.put(obj);
						}
						List<CourseVO> coachCourseList = courseSvc.getCoachList(coa_no).stream()
								.filter(e -> e.getCour_status() != 3).filter(e -> e.getCour_status() != 4).collect(Collectors.toList());
						for (CourseVO courseVO : coachCourseList) {
							String cour_no = courseVO.getCour_no();
							String cour_name = courseVO.getCour_name();
							java.sql.Timestamp cour_date = courseVO.getCour_date();
							String url = req.getContextPath()+"/course/course.do?action=getOne_For_Display&cour_no=" + cour_no;
							String color = "green";
							obj = new JSONObject();
							obj.put("id", cour_no);
							obj.put("title", cour_name);
							obj.put("start", cour_date);
							obj.put("url", url);
							obj.put("color", color);
							array.put(obj);
						}
					}

					res.setContentType("text/plain");
					res.setCharacterEncoding("UTF-8");
					PrintWriter out = res.getWriter();
					out.write(array.toString());
					out.flush();
					out.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if ("insert".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();

			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");
			try {
				String member_no = new String(req.getParameter("member_no"));
				String cour_no = new String(req.getParameter("cour_no"));
				Integer choose_mode = new Integer(req.getParameter("choose_mode"));

				synchronized (this) {

					if (choose_mode == 0) {
						errorMsgs.add("請選擇上課方式");
					}

					Cour_BookingService cour_bookingSvc = new Cour_BookingService();
					CourseService courseSvc = new CourseService();
					CourseVO courseVO = courseSvc.getOneCourse(cour_no);

					long courBooking = courseVO.getCour_date().getTime(); // 這個課程的開課時間
					long deadline = courseVO.getCour_deadline().getTime(); // 這個課程的截止時間

					if (System.currentTimeMillis() > deadline) {
						errorMsgs.add("已超過報名截止時間");
					}

					CoachService coachSvc = new CoachService();

					String coa_no = courseVO.getCoa_no();
					Integer cour_status = courseVO.getCour_status();
					List<Cour_BookingVO> list = cour_bookingSvc.getAll();
					List<Cour_BookingVO> listForMem = list.stream() // 拿出預約人目前預約課程列表
							.filter(e -> e.getMember_no().equals(member_no)).filter(e -> e.getCoa_comf() != 3)
							.filter(e -> e.getTrainee_comf() != 3).collect(Collectors.toList());

					List<Long> listOfcourDate = new ArrayList<Long>(); // 將預約人目前預約課程列表的開課時間拿出
					for (Cour_BookingVO cour_bookingVO : listForMem) {
						listOfcourDate
								.add(courseSvc.getOneCourse(cour_bookingVO.getCour_no()).getCour_date().getTime());
					}
					if(coachSvc.getOneCoachByMember(member_no)!=null) {
						List<CourseVO> homeMade = courseSvc.getCoachList(coachSvc.getOneCoachByMember(member_no).getCoa_no())
														.stream()
														.filter(e->e.getCour_status()!=2)
														.filter(e->e.getCour_status()!=4)//如果是預約中或是上架的 就取近來
														.collect(Collectors.toList());
						for(CourseVO madeVO:homeMade) {
							listOfcourDate.add(madeVO.getCour_date().getTime()); //如果此預約人有教練資格 便將此人有創立的課程的開課時間取出比對
						}
					}
					
				
					Optional<Long> result = listOfcourDate.stream().filter(e -> e + (3 * 60 * 60 * 1000) > courBooking)
							.filter(e -> e - (3 * 60 * 60 * 1000) < courBooking).findFirst(); // 將列表的開課時間一一比對
																								// 課程與課程的間隔需大於三小時
					if (result.isPresent()) {
						errorMsgs.add("請檢查你的課程管理,課程需間距三小時");

					}

					if ((cour_status == 1 && member_no.equals(coachSvc.getOneCoach(coa_no).getMember_no()))) {
						errorMsgs.add("你不能預約自己的課程");
					} else if (cour_status != 1) {
						errorMsgs.add("此課程不能預約");
					}

					MembersService membersSvc = new MembersService();
					MembersVO membersVO = membersSvc.getOneMembers(member_no);
					Integer real_blance = membersVO.getReal_blance();
					String remark;
					String whichCourse = courseVO.getCour_name();
					DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm"); 
					String whichDate = sdf.format(courseVO.getCour_date());
					if (real_blance < 2000) {
						errorMsgs.add("餘額不足2000元");
					}

					if (!errorMsgs.isEmpty()) {

						RequestDispatcher failureView = req
								.getRequestDispatcher(requestURL);
						failureView.forward(req, res);
						return;
					}

					cour_bookingSvc.addBooking(cour_no, coa_no, member_no, choose_mode); // 新增預約
					courseSvc.updateStatus(cour_no, 3); // 修改被預約課程狀態

					real_blance -= 2000;
					remark ="["+ whichDate+" 的 "+whichCourse+"] 課程預約扣款 ";
					cour_bookingSvc.refund(member_no, real_blance, 0, 2000, remark);

				}

				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}
		if ("Coach_For_Checkin".equals(action) || "Trainee_For_Checkin".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();

			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");

			try {
				String coa_no = null;
				String member_no = null;

				if ("Coach_For_Checkin".equals(action)) {
					coa_no = req.getParameter("coa_no");
				}
				if ("Trainee_For_Checkin".equals(action)) {
					member_no = req.getParameter("member_no");
				}
				String booking_no = req.getParameter("booking_no");

				Cour_BookingService cour_bookingSvc = new Cour_BookingService();
				Cour_BookingVO cour_bookingVO = cour_bookingSvc.getOneBooking(booking_no);

				CourseService courseSvc = new CourseService();
				CourseVO courseVO = courseSvc.getOneCourse(cour_bookingVO.getCour_no());// 拿出課程物件 為了檢查日期是否可以報到

				long date = courseVO.getCour_date().getTime();

				if (cour_bookingVO.getCoa_no().equals(coa_no) || cour_bookingVO.getMember_no().equals(member_no)) {
					if (date + (10 * 60 * 1000) >= System.currentTimeMillis() && System.currentTimeMillis() > date) {
						if (coa_no != null) {
							cour_bookingSvc.coachCheckin(booking_no); // 教練
						} else {
							cour_bookingSvc.traineeCheckin(booking_no); // 學員
						}
					} else {
						errorMsgs.add("只能在課程開始後十分鐘內進行報到");
					}
				} else {
					errorMsgs.add("只有本人才能進行報到");
				}

				if (!errorMsgs.isEmpty()) {

					RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
					failureView.forward(req, res);
					return;
				}

				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}
		if ("Coach_Change".equals(action) || "Trainee_Change".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();

			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");

			try {
				String booking_no = req.getParameter("booking_no");
				Integer coa_comf = null;
				Integer trainee_comf = null;
				Cour_BookingService cour_bookingSvc = new Cour_BookingService();
				Cour_BookingVO cour_bookingVO = cour_bookingSvc.getOneBooking(booking_no);
				CoachService coachSvc = new CoachService();
				CourseService courseSvc = new CourseService();
				CourseVO courseVO = courseSvc.getOneCourse(cour_bookingVO.getCour_no());
				int count = 0; //區分確認和取消
				
				String cour_no = cour_bookingVO.getCour_no();
				long date = courseVO.getCour_deadline().getTime();
				
				NotificationListService noteSvc = new NotificationListService();
				
				if ("Coach_Change".equals(action)) {
					coa_comf = new Integer(req.getParameter("coa_comf"));
					switch ((int) coa_comf) {
					case 1:
						trainee_comf = cour_bookingVO.getTrainee_comf();
						break;
					case 3:
						trainee_comf = new Integer(2);
						count++;
						noteSvc.addNotification(cour_bookingVO.getMember_no(), "CA0012","課程已被教練取消");
						break;
					}
					if (System.currentTimeMillis() > date && coa_comf == 3) {
						errorMsgs.add("超過截止日期不得取消");
					}

				}

				if ("Trainee_Change".equals(action)) {
					trainee_comf = new Integer(req.getParameter("trainee_comf"));
					switch ((int) trainee_comf) {
					case 1:
						coa_comf = cour_bookingVO.getCoa_comf();
						break;
					case 3:
						coa_comf = new Integer(2);
						count++;
						noteSvc.addNotification(coachSvc.getOneCoach((cour_bookingVO.getCoa_no())).getMember_no(), "CA0012", "課程已被學員取消");
						break;
					}
					if (System.currentTimeMillis() > date && trainee_comf == 3) {
						errorMsgs.add("超過截止日期不得取消");
					}
				}

				if ((coa_comf == 3 && coa_comf == (int) cour_bookingVO.getCoa_comf()&&count==1)//count=1 代表這次改變是取消
						|| (trainee_comf == 3 && trainee_comf == (int) cour_bookingVO.getTrainee_comf()&&count==1)) {
					errorMsgs.add("此課程已取消");

				}

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("cour_bookingVO", cour_bookingVO);
					RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
					failureView.forward(req, res);
					return;
				}
				MembersService membersSVc = new MembersService();
				String member_no = cour_bookingVO.getMember_no(); // 這筆預約的會員

				MembersVO membersVO = membersSVc.getOneMembers(member_no);
				Integer real_blance = membersVO.getReal_blance();
				Integer deposit, withdraw;
				String remark;
				String whichCourse = courseVO.getCour_name();
				DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm"); 
				String whichDate = sdf.format(courseVO.getCour_date());
				if ("Coach_Change".equals(action)) {
					switch ((int) coa_comf) {
					case 1:
						cour_bookingSvc.comfOnchange(booking_no, coa_comf, trainee_comf, null);// 確認情況 不去修改課程狀態
						break;
					case 3:
						cour_bookingSvc.comfOnchange(booking_no, coa_comf, trainee_comf, cour_no);// 取消 就要去修改課程狀況

						real_blance += 2000;
						deposit = 2000;
						withdraw = 0;
						remark ="["+ whichDate+" 的 "+whichCourse+"] 課程退還款項 ";
						cour_bookingSvc.refund(member_no, real_blance, deposit, withdraw, remark);
						break;
					}
				} else if ("Trainee_Change".equals(action)) {
					switch ((int) trainee_comf) {
					case 1:
						cour_bookingSvc.comfOnchange(booking_no, coa_comf, trainee_comf, null);
						break;
					case 3:
						cour_bookingSvc.comfOnchange(booking_no, coa_comf, trainee_comf, cour_no);
						real_blance += 2000;
						deposit = 2000;
						withdraw = 0;
						remark ="["+ whichDate+" 的 "+whichCourse+"] 課程退還款項 ";
						cour_bookingSvc.refund(member_no, real_blance, deposit, withdraw, remark);
						break;
					}
				}
				cour_bookingVO=cour_bookingSvc.getOneBooking(booking_no);
				req.setAttribute("cour_bookingVO", cour_bookingVO);
				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}

		if ("pass".equals(action)) {

			String requestURL = req.getParameter("requestURL");

			try {
				String cour_no = req.getParameter("cour_no");

				CourseService courseSvc = new CourseService();
				CourseVO courseVO = courseSvc.getOneCourse(cour_no);

				CoachService coachSvc = new CoachService();
				CoachVO coachVO = coachSvc.getOneCoach(courseVO.getCoa_no());

				String member_no = coachVO.getMember_no(); // 教練的會員編號

				MembersService membersSvc = new MembersService();
				MembersVO membersVO = membersSvc.getOneMembers(member_no);

				Integer real_blance = membersVO.getReal_blance();
				Integer deposit, withdraw;
				String remark;
				String whichCourse = courseVO.getCour_name();
				DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm"); 
				String whichDate = sdf.format(courseVO.getCour_date());
				Cour_BookingService cour_bookingSvc = new Cour_BookingService();

				cour_bookingSvc.passForCourseStatus(cour_no);

				real_blance += 1000; // 平台收取5成費用
				deposit = 1000;
				withdraw = 0;
				remark = "["+whichDate+" 的 "+whichCourse+"] 課程完成,教練應收款項";
				cour_bookingSvc.refund(member_no, real_blance, deposit, withdraw, remark);

				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}
	}
}
