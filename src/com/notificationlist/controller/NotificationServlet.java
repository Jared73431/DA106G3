package com.notificationlist.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import com.groupgo.model.GroupgoService;
import com.members.model.MembersService;
import com.mygroup.model.MyGroupVO;
import com.mygroup.model.MygroupService;
import com.notificationlist.model.NotificationListDAO;
import com.notificationlist.model.NotificationListService;
import com.notificationlist.model.NotificationListVO;

public class NotificationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("UTF-8");
		
		String action = req.getParameter("action");
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String str = req.getParameter("note_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入note_no");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/notificationlist/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				String note_no = null;
				try {
					note_no = new String(str);
				} catch (Exception e) {
					errorMsgs.add("note_no號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/notificationlist/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/
				NotificationListService noteSvc = new NotificationListService();
				NotificationListVO noteVO = noteSvc.getOneNotification(note_no);
				if (noteVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/notificationlist/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("noteVO", noteVO); // 資料庫取出的empVO物件,存入req
				String url = "/front-end/notificationlist/listOneNotificationList.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/notificationlist/select_page.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOne_For_Update".equals(action)) { // 來自listAllEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String note_no = new String(req.getParameter("note_no"));

				/*************************** 2.開始查詢資料 ****************************************/
				NotificationListService noteSvc = new NotificationListService();
				NotificationListVO noteVO = noteSvc.getOneNotification(note_no);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("noteVO", noteVO); // 資料庫取出的empVO物件,存入req
				String url = "/back-end/notificationlist/update_notificationlist_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/notificationlist/listAllNotificationList.jsp");
				failureView.forward(req, res);
			}
		}
		if ("update".equals(action)) { // 來自update_emp_input.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String note_no = new String(req.getParameter("note_no").trim());

				String member_no = req.getParameter("member_no");
				if (member_no == null || member_no.trim().length() == 0) {
					errorMsgs.add("member_no: 請勿空白");
				}
				String category_no = req.getParameter("category_no");
				if (category_no == null || category_no.trim().length() == 0) {
					errorMsgs.add("category_no: 請勿空白");
				}
				String note_content = req.getParameter("note_content");
				if (note_content == null || note_content.trim().length() == 0) {
					errorMsgs.add("note_content: 請勿空白");
				}

				NotificationListVO noteVO = new NotificationListVO();
				noteVO.setCategory_no(category_no);
				noteVO.setMember_no(member_no);
				noteVO.setNote_content(note_content);
				noteVO.setNote_no(note_no);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("noteVO", noteVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/notificationlist/update_notificationlist_input.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				/*************************** 2.開始修改資料 *****************************************/
				NotificationListService noteSvc = new NotificationListService();
				noteVO = noteSvc.updateNotification(note_no, member_no, category_no, note_content);

				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
				NotificationListService noteSvc1 = new NotificationListService();
				NotificationListVO noteVO1 = noteSvc1.getOneNotification(note_no);

				req.setAttribute("noteVO", noteVO1); // 資料庫取出的empVO物件,存入req
				String url = "/back-end/notificationlist/listOneNotificationList.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/notificationlist/update_notificationlist_input.jsp");
				failureView.forward(req, res);
			}
		}
		if ("insert".equals(action)) { // 來自addEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				String member_no = req.getParameter("member_no");
				if (member_no == null || member_no.trim().length() == 0) {
					errorMsgs.add("member_no: 請勿空白");
				}
				String category_no = req.getParameter("category_no");
				if (category_no == null || category_no.trim().length() == 0) {
					errorMsgs.add("category_no: 請勿空白");
				}
				String note_content = req.getParameter("note_content");
				if (note_content == null || note_content.trim().length() == 0) {
					errorMsgs.add("note_content: 請勿空白");
				}

				NotificationListVO noteVO = new NotificationListVO();
				noteVO.setCategory_no(category_no);
				noteVO.setMember_no(member_no);
				noteVO.setNote_content(note_content);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("noteVO", noteVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/notificationlist/addNotificationList.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				NotificationListService noteSvc = new NotificationListService();
				noteVO = noteSvc.addNotification(member_no, category_no, note_content);

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				req.setAttribute("noteVO", noteVO);
				String url = "/back-end/notificationlist/addNotificationList.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/notificationlist/addNotificationList.jsp");
				failureView.forward(req, res);
			}
		}
		if ("delete".equals(action)) { // 來自listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");
			try {
				/*************************** 1.接收請求參數 ***************************************/
				String whichPage = req.getParameter("whichPage");
				String note_no = new String(req.getParameter("note_no"));

				/*************************** 2.開始刪除資料 ***************************************/
				NotificationListService noteSvc = new NotificationListService();
				NotificationListVO noteVO = noteSvc.getOneNotification(note_no);
				noteSvc.deleteNotification(note_no);

				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				MembersService memSvc = new MembersService();
				req.setAttribute("listAllNotesByMember", memSvc.getNotesByMemberno(noteVO.getMember_no()));

				String url = "/front-end/members/listAllNotebyMember.jsp";
				req.setAttribute("whichPage", whichPage);
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/notificationlist/listAllNotificationList.jsp");
				failureView.forward(req, res);
			}
		}
		if ("readed".equals(action)) { // listAllnotes_Bymemberno.jsp 的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			String requestURL = req.getParameter("requestURL"); // 送出修改的來源網頁路徑: 可能為【/emp/listAllEmp.jsp】 或
																// 【/dept/listEmps_ByDeptno.jsp】 或 【
																// /dept/listAllDept.jsp】

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String noteno = new String(req.getParameter("note_no"));

				/*************************** 2.開始查詢資料 ****************************************/
				NotificationListService noteSvc = new NotificationListService();
				NotificationListVO noteVO = noteSvc.getOneNotification(noteno);
				noteSvc.readed(noteno);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				MembersService memSvc = new MembersService();
				req.setAttribute("listAllNotesByMember", memSvc.getNotesByMemberno(noteVO.getMember_no()));

				String url = "/front-end/members/listAllNotebyMember.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 ************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料取出時失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}
		if ("addnoteajax".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				// 取得新增的note
				NotificationListService noteSvc = new NotificationListService();
				NotificationListVO noteVO = noteSvc.getnote(req);
				
				String member_no = req.getParameter("member_no");
				if (member_no == null || member_no.trim().length() == 0) {
					errorMsgs.add("member_no: 請勿空白");
				}
				String category_no = req.getParameter("category_no");
				if (category_no == null || category_no.trim().length() == 0) {
					errorMsgs.add("category_no: 請勿空白");
				}
				String note_content = req.getParameter("note_content");
				if (note_content == null || note_content.trim().length() == 0) {
					errorMsgs.add("note_content: 請勿空白");
				}

				NotificationListVO noteVO2 = new NotificationListVO();
				noteVO.setCategory_no(category_no);
				noteVO.setMember_no(member_no);
				noteVO.setNote_content(note_content);
				noteVO = noteSvc.addNotification(member_no, category_no, note_content);
				
				
				/*************************** 資料轉交 ************/
				
				res.setContentType("text/plain");
				res.setCharacterEncoding("UTF-8");
				PrintWriter out = res.getWriter();
				out.write("success");
				out.flush();
				out.close();
			} catch (Exception e) {
				System.out.println("無法取得資料:" + e.getMessage());
			}
		}
		
		if ("addgroupnoteajax".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				// 取得新增的note
				NotificationListService noteSvc = new NotificationListService();
				NotificationListVO noteVO = noteSvc.getnote(req);
				
				String groupgo_id = req.getParameter("groupgo_id");
				JSONArray array = new JSONArray();	

				NotificationListVO noteVO2 = new NotificationListVO();
				MygroupService mygroupsvc = new MygroupService();
				GroupgoService groupgoSvc = new GroupgoService();

				List<MyGroupVO> groupmember = mygroupsvc.getGroupAttend(groupgo_id);
				NotificationListVO groupnoteVO = noteSvc.addNotification(groupgoSvc.getOneGroup(groupgo_id).getMaster_id(), "CA0002", groupgoSvc.getOneGroup(groupgo_id).getGroup_name()+":被檢舉下架囉QQ");
				array.put(groupgoSvc.getOneGroup(groupgo_id).getMaster_id());
				for(MyGroupVO groupMember:groupmember) {
						NotificationListVO groupnoteVO2 =  noteSvc.addNotification(groupMember.getMember_no(), "CA0002", groupgoSvc.getOneGroup(groupgo_id).getGroup_name()+":被檢舉下架囉QQ");
						array.put(groupMember.getMember_no());
				}
				JSONObject obj = new JSONObject();
				obj.put("content", groupgoSvc.getOneGroup(groupgo_id).getGroup_name()+":被檢舉下架囉QQ");

				
				JSONArray output = new JSONArray();
				output.put(array);
				output.put(obj);

				
				res.setContentType("text/plain");
				res.setCharacterEncoding("UTF-8");
				PrintWriter out = res.getWriter();
				out.write(output.toString());
				out.flush();
				out.close();
			} catch (Exception e) {
				System.out.println("無法取得資料:" + e.getMessage());
			}
		}
		if ("getOnebymem".equals(action)) {

			try {
				// Retrieve form parameters.
				String noteno = new String(req.getParameter("noteno"));

				NotificationListDAO dao = new NotificationListDAO();
				NotificationListVO noteVO = dao.findByPrimaryKey(noteno);

				req.setAttribute("noteVO", noteVO); // 資料庫取出的empVO物件,存入req
				
				//Bootstrap_modal
				boolean openModal=true;
				req.setAttribute("openModal",openModal );
				
				// 取出的empVO送給listOneEmp.jsp
				RequestDispatcher successView = req
						.getRequestDispatcher("/back-end/members/listAllNotebyMember.jsp");
				successView.forward(req, res);
				return;

				// Handle any unusual exceptions
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}
		if ("deleteback".equals(action)) { // 來自listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");
			try {
				/*************************** 1.接收請求參數 ***************************************/
				String whichPage = req.getParameter("whichPage");
				String note_no = new String(req.getParameter("note_no"));

				/*************************** 2.開始刪除資料 ***************************************/
				NotificationListService noteSvc = new NotificationListService();
				NotificationListVO noteVO = noteSvc.getOneNotification(note_no);
				noteSvc.deleteNotification(note_no);

				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				MembersService memSvc = new MembersService();
				req.setAttribute("listAllNotesByMember", memSvc.getNotesByMemberno(noteVO.getMember_no()));

				String url = "/back-end/notificationlist/listAllNotificationList.jsp";
				req.setAttribute("whichPage", whichPage);
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/notificationlist/listAllNotificationList.jsp");
				failureView.forward(req, res);
			}
		}
	}

	}


