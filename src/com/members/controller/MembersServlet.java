package com.members.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;

import org.json.JSONObject;

import com.experience.model.ExperienceVO;
import com.members.model.*;
import com.notificationlist.model.NotificationListVO;
import com.transactions.model.TransactionsService;
import com.transactions.model.TransactionsVO;


@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)

public class MembersServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
		if ("getOne_For_Display".equals(action)) { // 來自select_page_members.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接受請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("member_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("會員編號不能空白");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/members/select_page_members.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				String member_no = null;
				try {
					member_no = new String(str);
				} catch (Exception e) {
					errorMsgs.add("會員編號不能空白");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/members/select_page_members.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				MembersService membersSvc = new MembersService();
				MembersVO membersVO = membersSvc.getOneMembers(member_no);
				if (membersVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/members/select_page_members.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("membersVO", membersVO); 
				String url = "/members/listOneMembers.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("�瘜�����:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/members/select_page_members.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("getAll_For_Nostatus".equals(action)) { //來自listNoStatusMembers.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
//
			try {
				// 1.接受請求參數
				Integer mem_status = null;
				try {
					mem_status = new Integer(req.getParameter("mem_status").trim());
				} catch (Exception e) {
					errorMsgs.add("123");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/members/select_page_members.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				// 2.開始查詢資料
				MembersService membersSvc = new MembersService();
				List<MembersVO> membersVO = membersSvc.getNoStatus(mem_status);
				if (membersVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/members/select_page_members.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("List", membersVO);
				String url = "/back-end/members/listNoStatusMembers.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 來自listOneMembers.jsp的請求
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/members/select_page_members.jsp");
				failureView.forward(req, res);
			}
		}
		
		if("beforelogin".equals(action)) {

			try {
				String location = (String) req.getParameter("location");

				HttpSession session = req.getSession();// *工作: 看看有無來源網頁 (-->如有來源網頁:則重導至來源網頁)
				session.removeAttribute("location");
				session.setAttribute("location", location);

			} catch (Exception ignored) {
			}

			res.sendRedirect(req.getContextPath() + "/front-end/members/membersLogin.jsp"); // *工作:
			// (-->如無來源網頁:則重導至login_success.jsp)
		}
		
		if("login".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				//1.
				String mem_account = req.getParameter("mem_account");
				String mem_password = req.getParameter("mem_password");
				
				//2.
				MembersService membersSvc = new MembersService();
				MembersVO membersVO = membersSvc.getOneMemberByAccount(mem_account);
				Integer mem_status = membersVO.getMem_status();
				
				
				//3.確認帳號密碼是否空白，如果都有輸入就讓程式繼續執行，不然就程式終止，這樣就不會跑到下面的帳密不正確
				if(mem_account == null || mem_account.length() == 0)
					errorMsgs.add("帳號不能空白");
				if(mem_password == null || mem_password.length() == 0)
					errorMsgs.add("密碼不能空白");
				if(!errorMsgs.isEmpty()) {
					RequestDispatcher failure = req.getRequestDispatcher("/front-end/members/membersLogin.jsp");
					failure.forward(req, res);
					return;//程式終止
				}
				//確認帳號密碼是否正確
				if(membersVO == null || !(mem_password.equals(membersVO.getMem_password()))) {
					System.out.println("mem_account = "+mem_account);
					System.out.println("mem_password = "+mem_password);
					errorMsgs.add("帳密不正確");
					
				}
				if(!errorMsgs.isEmpty()) {
					RequestDispatcher failure = req.getRequestDispatcher("/front-end/members/membersLogin.jsp");
					failure.forward(req, res);
					return;//程式終止
				}
				
				if(mem_status == 1 || mem_status == 2)
					errorMsgs.add("會員狀態未驗證");
				
				if(!errorMsgs.isEmpty()) {
					RequestDispatcher failure = req.getRequestDispatcher("/front-end/members/membersLogin.jsp");
					failure.forward(req, res);
					return;//程式終止
				}
				//讓會員資料存入session，在其他頁面瀏覽的時候還是維持會員登入的狀態
				HttpSession session = req.getSession();
				String member_no = membersVO.getMember_no();
				session.setAttribute("member_no", member_no); 
				
				
				try {                                                        
			         String location = (String) session.getAttribute("location");
			         if (location != null) {
			           session.removeAttribute("location");  //*工作: 看看有無來源網頁 (-->如有來源網頁:則重導至來源網頁)
			           res.sendRedirect(location);
			           
			           return;
			         }else {
			        	
			        	String url = "/front-end/members/membersPage.jsp";
						RequestDispatcher successView = req.getRequestDispatcher(url); 
						successView.forward(req, res);
				           return;
			         }
			       }catch (Exception ignored) { }

			      res.sendRedirect(req.getContextPath()+"/members/membersLogin.jsp"); //*工作: (-->如無來源網頁:則重導至login_success.jsp)
				
			}catch(Exception e) {
				errorMsgs.add("登入失敗" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/members/membersLogin.jsp");
				failureView.forward(req, res);
			}
		}
		
		if("logout".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				//登出之後去拿掉session裡的attribute
				HttpSession session = req.getSession();
				session.removeAttribute("member_no");
				
				String url = "/front-end/homepage/Home.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ����漱membersLogin.jsp
				successView.forward(req, res);
				
				
			}catch(Exception e) {
				errorMsgs.add("登入失敗" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/homepage/Home.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("getOne_For_Update_Back".equals(action)) { // 來自listAllMembers.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接受請求參數 - 輸入格式的錯誤處理**********************/
				String members_no = req.getParameter("member_no");
				
				/***************************2.開始查詢資料****************************************/
				MembersService membersSvc = new MembersService();
				MembersVO membersVO = membersSvc.getOneMembers(members_no);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("membersVO", membersVO);         // 資料庫取出的membersVO物件,存入req
				String url = "/back-end/members/update_members_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_members_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/members/select_members_input.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("getOne_For_Update".equals(action)) { // 來自listAllMembers.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接受請求參數 - 輸入格式的錯誤處理**********************/
				String members_no = req.getParameter("member_no");
				
				/***************************2.開始查詢資料****************************************/
				MembersService membersSvc = new MembersService();
				MembersVO membersVO = membersSvc.getOneMembers(members_no);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("membersVO", membersVO);         // 鞈�澈����embersVO�隞�,摮req
				String url = "/front-end/members/update_members_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// ����漱 update_members_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/members/Home.jsp");
				failureView.forward(req, res);
			}
		}
		
		if("applyCoach".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
//			try {
				
				MembersService membersSvc = new MembersService();
				HttpSession session = req.getSession();
				
				String member_no = (String)(session.getAttribute("member_no"));
				MembersVO membersVO = membersSvc.getOneMembers(member_no);
				System.out.println(member_no);
//				System.out.println(membersVO.getMem_account());
				Integer coa_qualifications = membersVO.getCoa_qualifications();
//				
//				System.out.println(coa_qualifications);
//				
				byte[] license = null;
//				Part part2 = req.getPart("upfile2");
//				if(part2.getSize() != -1) {
//					InputStream is2 = part2.getInputStream();
//					license = new byte[is2.available()];
//					is2.read(license);
//				}else {
//					errorMsgs.add("隢�����");
//				}
				
			     try (InputStream in = req.getPart("upfile2").getInputStream();
			    	       BufferedInputStream bis = new BufferedInputStream(in);
			    	       ByteArrayOutputStream bos = new ByteArrayOutputStream();) {
			    	 
			    	      byte[] b = new byte[4 * 1024];
			    	      int len = 0;
			    	      while ((len = bis.read(b)) != -1) {
			    	       bos.write(b, 0, len);
			    	      }
			    	      license = bos.toByteArray();
			    	     }
				

//				membersVO.setMember_no(member_no);
//				membersVO.setCoa_qualifications(coa_qualifications);
//				membersVO.setLicense(license);
				
				if(coa_qualifications == 1) 
					errorMsgs.add("此帳號已有資格");
				if(coa_qualifications == 2)
					errorMsgs.add("此帳號已經在審核中");
				if(!errorMsgs.isEmpty()) {
					req.setAttribute("membersVO", membersVO); // 含有輸入格式錯誤的membersVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/members/applyCoach.jsp");
					failureView.forward(req, res);
					return;
				}
				
//				membersVO.setCoa_qualifications(2);
				
				coa_qualifications = 2;
//				membersSvc = new MembersService();
				membersSvc.updateLicense(coa_qualifications, license, member_no);
				membersVO = membersSvc.getOneMembers(member_no);

				req.setAttribute("membersVO", membersVO);
				String url = "/front-end/members/membersPage.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);
//				}catch(Exception e) {
//				errorMsgs.add("修改資料失敗:"+e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/front-end/members/Home.jsp");
//				failureView.forward(req, res);
//			}
		}
		if ("update".equals(action)) { // 來自update_members_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
			/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String member_no = new String(req.getParameter("member_no").trim());
				
				Integer coa_qualifications = new Integer(req.getParameter("coa_qualifications").trim());
				
				String known = req.getParameter("known");
				if (known == null || known.trim().length() == 0) {
					errorMsgs.add("暱稱不能空白");
				}
				
				String sexualStr = req.getParameter("sexual");
				Integer sexual = null;
				if(sexualStr == null ) {
					errorMsgs.add("性別不能空白");
				}else {
					sexual = new Integer(sexualStr);
				}
				
				String mem_name = req.getParameter("mem_name");
				String mem_nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_ )]{2,20}$";
				if (mem_name == null || mem_name.trim().length() == 0) {
					errorMsgs.add("會員姓名: 請勿空白");
				} else if(!mem_name.trim().matches(mem_nameReg)) { 
					errorMsgs.add("會員姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到20之間");
	            }
				
				String mem_account = req.getParameter("mem_account").trim();
				if (mem_account == null || mem_account.trim().length() == 0) {
					errorMsgs.add("帳號不能空白");
				}
				
				Integer mem_status = new Integer(req.getParameter("mem_status").trim());
				
				String email = req.getParameter("email").trim();
				if (email == null || email.trim().length() == 0) {
					errorMsgs.add("信箱不能空白");
				}	
				String mem_password = req.getParameter("mem_password").trim();
				if (mem_password == null || mem_password.trim().length() == 0) {
					errorMsgs.add("密碼不能空白");
				}	
				
				java.sql.Date birthday = null;
				try {
					birthday = java.sql.Date.valueOf(req.getParameter("birthday").trim());
				} catch (IllegalArgumentException e) {
					birthday=new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("生日不能空白");
				}
				
				String phone = req.getParameter("phone").trim();
				if (phone == null || phone.trim().length() == 0) {
					errorMsgs.add("電話不能空白");
				}
				
				String mem_zip = req.getParameter("mem_zip").trim();
				if (mem_zip == null || mem_zip.trim().length() == 0) {
					errorMsgs.add("郵遞區號不能空白");
				}	
				
				String address = req.getParameter("address").trim();
				if (address == null || address.trim().length() == 0) {
					errorMsgs.add("地址不能空白");
				}	
				
				Integer real_blance = new Integer(req.getParameter("real_blance").trim());
				
				Double height = null;
				try {
					height = new Double(req.getParameter("height").trim());
				} catch (NumberFormatException e) {
					height = 0.0;
					errorMsgs.add("身高不能空白");
				}
				

				byte[] picture = null;
				Part part1 = req.getPart("upfile1");
				if(part1.getSize() != 0 ) {
					InputStream is1 = part1.getInputStream();
					picture = new byte[is1.available()];
					is1.read(picture);
				}else {
					MembersService membersSvc = new MembersService();
					MembersVO membersVO = membersSvc.getOneMembers(member_no);
					picture = membersVO.getPicture();
				}
				
				
				
//				byte[] license = null;
//				Part part2 = req.getPart("upfile2");
//				if(part2.getSize() != 0) {
//					InputStream is2 = part2.getInputStream();
//					license = new byte[is2.available()];
//					is2.read(license);
//				}else {
//					MembersService membersSvc = new MembersService();
//					MembersVO membersVO = membersSvc.getOneMembers(member_no);
//					license = membersVO.getLicense();
//				}
				

				MembersVO membersVO = new MembersVO();
				membersVO.setMember_no(member_no);
				membersVO.setCoa_qualifications(coa_qualifications);
				membersVO.setKnown(known);
				membersVO.setSexual(sexual);
				membersVO.setMem_name(mem_name);
				membersVO.setMem_account(mem_account);
				membersVO.setMem_status(mem_status);
				membersVO.setEmail(email);
				membersVO.setMem_password(mem_password);
				membersVO.setBirthday(birthday);
				membersVO.setPhone(phone);
				membersVO.setMem_zip(mem_zip);
				membersVO.setAddress(address);
				membersVO.setReal_blance(real_blance);
				membersVO.setHeight(height);
//				membersVO.setReg_date(reg_date);
				membersVO.setPicture(picture);
//				membersVO.setLicense(license);
				
				
				

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("membersVO", membersVO); // ���撓��撘隤斤�embersVO�隞�,銋�req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/members/update_members_input.jsp");
					failureView.forward(req, res); 
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				MembersService membersSvc = new MembersService();
				membersSvc.updateMembers(member_no, coa_qualifications, known, sexual, mem_name,mem_account, mem_status, email, mem_password, birthday, phone, mem_zip, address, real_blance, height, picture);
				MembersVO membersVO2 =new MembersVO();
				membersVO2 = membersSvc.getOneMembers(member_no);
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("membersVO", membersVO2); // 鞈�澈update�����,甇�蝣箇��embersVO�隞�,摮req
				String url = "/front-end/members/membersPage.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);  // 靽格�����,頧漱listOneMembers.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/emp/update_emp_input.jsp");
				failureView.forward(req, res);
			}
		}
		
if ("update_back".equals(action)) { // 來自update_members_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
//			try {
			/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String member_no = new String(req.getParameter("member_no").trim());
				
				Integer coa_qualifications = new Integer(req.getParameter("coa_qualifications").trim());
				
				String known = req.getParameter("known");
				if (known == null || known.trim().length() == 0) {
					errorMsgs.add("暱稱不能空白");
				}
				
				String sexualStr = req.getParameter("sexual");
				Integer sexual = null;
				if(sexualStr == null ) {
					errorMsgs.add("性別不能空白");
				}else {
					sexual = new Integer(sexualStr);
				}
				
				String mem_name = req.getParameter("mem_name");
				String mem_nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_ )]{2,20}$";
				if (mem_name == null || mem_name.trim().length() == 0) {
					errorMsgs.add("員工姓名: 請勿空白");
				} else if(!mem_name.trim().matches(mem_nameReg)) { //隞乩�毀蝧迤���(閬�)銵函內撘�(regular-expression)
					errorMsgs.add("員工姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到20之間");
	            }
				
				String mem_account = req.getParameter("mem_account").trim();
				if (mem_account == null || mem_account.trim().length() == 0) {
					errorMsgs.add("帳號不能空白");
				}
				
				Integer mem_status = new Integer(req.getParameter("mem_status").trim());
				
				String email = req.getParameter("email").trim();
				if (email == null || email.trim().length() == 0) {
					errorMsgs.add("信箱不能空白");
				}	
				String mem_password = req.getParameter("mem_password").trim();
				if (mem_password == null || mem_password.trim().length() == 0) {
					errorMsgs.add("密碼不能空白");
				}	
				
				java.sql.Date birthday = null;
				try {
					birthday = java.sql.Date.valueOf(req.getParameter("birthday").trim());
				} catch (IllegalArgumentException e) {
					birthday=new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("生日不能空白");
				}
				
				String phone = req.getParameter("phone").trim();
				if (phone == null || phone.trim().length() == 0) {
					errorMsgs.add("電話不能空白");
				}
				
				String mem_zip = req.getParameter("mem_zip").trim();
				if (mem_zip == null || mem_zip.trim().length() == 0) {
					errorMsgs.add("郵遞區號不能空白");
				}	
				
				String address = req.getParameter("address").trim();
				if (address == null || address.trim().length() == 0) {
					errorMsgs.add("地址不能空白");
				}	
				
				Integer real_blance = new Integer(req.getParameter("real_blance").trim());
				
				Double height = null;
				try {
					height = new Double(req.getParameter("height").trim());
				} catch (NumberFormatException e) {
					height = 0.0;
					errorMsgs.add("身高不能空白");
				}
				

				byte[] picture = null;
				Part part1 = req.getPart("upfile1");
				if(part1.getSize() != 0 ) {
					InputStream is1 = part1.getInputStream();
					picture = new byte[is1.available()];
					is1.read(picture);
				}else {
					MembersService membersSvc = new MembersService();
					MembersVO membersVO = membersSvc.getOneMembers(member_no);
					picture = membersVO.getPicture();
				}
				
				
				
//				byte[] license = null;
//				Part part2 = req.getPart("upfile2");
//				if(part2.getSize() != 0) {
//					InputStream is2 = part2.getInputStream();
//					license = new byte[is2.available()];
//					is2.read(license);
//				}else {
//					MembersService membersSvc = new MembersService();
//					MembersVO membersVO = membersSvc.getOneMembers(member_no);
//					license = membersVO.getLicense();
//				}
				

				MembersVO membersVO = new MembersVO();
				membersVO.setMember_no(member_no);
				membersVO.setCoa_qualifications(coa_qualifications);
				membersVO.setKnown(known);
				membersVO.setSexual(sexual);
				membersVO.setMem_name(mem_name);
				membersVO.setMem_account(mem_account);
				membersVO.setMem_status(mem_status);
				membersVO.setEmail(email);
				membersVO.setMem_password(mem_password);
				membersVO.setBirthday(birthday);
				membersVO.setPhone(phone);
				membersVO.setMem_zip(mem_zip);
				membersVO.setAddress(address);
				membersVO.setReal_blance(real_blance);
				membersVO.setHeight(height);
//				membersVO.setReg_date(reg_date);
				membersVO.setPicture(picture);
//				membersVO.setLicense(license);
				
				
				

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					HttpSession session = req.getSession();
					req.setAttribute("membersVO", membersVO); // 含有輸入格式錯誤的membersVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/members/update_members_input.jsp");
					failureView.forward(req, res); 
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				MembersService membersSvc = new MembersService();
				membersSvc.updateMembers(member_no, coa_qualifications, known, sexual, mem_name, mem_account, mem_status, email, mem_password, birthday, phone, mem_zip, address, real_blance, height, picture);
				MembersVO membersVO2 =new MembersVO();
				membersVO2 = membersSvc.getOneMembers(member_no);
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("membersVO", membersVO2); // 資料庫update成功後,正確的的membersVO物件,存入req
				String url = "/back-end/members/listAllMembers.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);  // 修改成功後,轉交listOneMembers.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
//			} catch (Exception e) {
//				errorMsgs.add("修改資料失敗:"+e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/emp/update_emp_input.jsp");
//				failureView.forward(req, res);
//			}
		}

		if ("check_Account".equals(action)) {
			
			
			try {
				String mem_account = req.getParameter("mem_account");//使用者輸入的帳號
				
				MembersService membersSvc = new MembersService();
				MembersVO membersVO = membersSvc.getOneMemberByAccount(mem_account);
				
				
				res.setContentType("text/plain");
				res.setCharacterEncoding("UTF-8");
				PrintWriter out = res.getWriter();

				JSONObject obj = new JSONObject();
				String value = new String();
				if(membersVO == null){
					value = "此帳號可以使用";
				} else {
					value = "此帳號已被使用";
				}
				obj.put("canPass", value);
				out.write(obj.toString());//把資料傳回去前端
				out.flush();
				out.close();
			}catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}
		
		
        if ("insert".equals(action)) { // 來自addMembers.jsp的請求  
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				//資料的保密性:
//				1.亂數產生密碼
//				
//				2.寄信email
//				
//				3.亂數產生的密碼轉碼進資料庫(先不做，公司會給轉碼的程式)
//				
				String known = req.getParameter("known");
				if (known == null || known.trim().length() == 0) {
					errorMsgs.add("暱稱不能空白");
				}
				
				String sexualStr = req.getParameter("sexual");
				Integer sexual = null;
				if(sexualStr == null ) {
					errorMsgs.add("性別不能空白");
				}else {
					sexual = new Integer(sexualStr);
				} 
				
				String mem_name = req.getParameter("mem_name");
				String mem_nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_ )]{2,20}$";
				if (mem_name == null || mem_name.trim().length() == 0) {
					errorMsgs.add("員工姓名: 請勿空白");
				} else if(!mem_name.trim().matches(mem_nameReg)) { //隞乩�毀蝧迤���(閬�)銵函內撘�(regular-expression)
					errorMsgs.add("會員姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到20之間");
	            }
				String mem_account = req.getParameter("mem_account").trim();
				if (mem_account == null || mem_account.trim().length() == 0) {
					errorMsgs.add("帳號不能空白");
				}
				
				String email = req.getParameter("email").trim();
				if (email == null || email.trim().length() == 0) {
					errorMsgs.add("信箱不能空白");
				}	
				String mem_password = req.getParameter("mem_password").trim();
				if (mem_password == null || mem_password.trim().length() == 0) {
					errorMsgs.add("密碼不能空白");
				}	
				java.sql.Date birthday = null;
				try {
					birthday = java.sql.Date.valueOf(req.getParameter("birthday").trim());
				} catch (IllegalArgumentException e) {
					birthday=new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("生日不能空白");
				}	
				String phone = req.getParameter("phone").trim();
				if (phone == null || phone.trim().length() == 0) {
					errorMsgs.add("電話不能空白");
				}
				String mem_zip = req.getParameter("mem_zip").trim();
				if (mem_zip == null || mem_zip.trim().length() == 0) {
					errorMsgs.add("郵遞區號不能空白");
				}	
				String address = req.getParameter("address").trim();
				if (address == null || address.trim().length() == 0) {
					errorMsgs.add("地址不能空白");
				}	
				
				Double height = null;
				try {
					height = new Double(req.getParameter("height").trim());
				} catch (NumberFormatException e) {
					height = 0.0;
					errorMsgs.add("身高不能空白");
				}

				Part part1 = req.getPart("upfile1");
				InputStream is1 = part1.getInputStream();
				byte[] picture = new byte[is1.available()];
				is1.read(picture);
				is1.close();
				
//				Part part2 = req.getPart("upfile2");
//				InputStream is2 = part2.getInputStream();
//				byte[] license = new byte[is2.available()];
//				is2.read(license);
//				is2.close();
				MembersVO membersVO = new MembersVO();
//				membersVO.setCoa_qualifications(coa_qualifications);
				membersVO.setKnown(known);
				membersVO.setSexual(sexual);
				membersVO.setMem_name(mem_name);
				membersVO.setMem_account(mem_account);
//				membersVO.setMem_status(mem_status);
				membersVO.setEmail(email);
				membersVO.setMem_password(mem_password);
				membersVO.setBirthday(birthday);
				membersVO.setPhone(phone);
				membersVO.setMem_zip(mem_zip);
				membersVO.setAddress(address);
//				membersVO.setReal_blance(real_blance);
				membersVO.setHeight(height);
//				membersVO.setReg_date(reg_date);
				membersVO.setPicture(picture);
//				membersVO.setLicense(license);
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					System.out.println(mem_name);
					req.setAttribute("membersVO", membersVO); 
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/members/addMembers.jsp");
					failureView.forward(req, res);
					return;
				}
				errorMsgs.add("123");

				/***************************2.開始新增資料***************************************/
				MembersService membersSvc = new MembersService();
				membersVO = membersSvc.addMembers(known, sexual, mem_name,mem_account, email, mem_password, birthday, phone, mem_zip, address, height, picture);
				MailService mailservice = new MailService();
				boolean mail_alert=true;
				req.setAttribute("mail_alert",mail_alert);
				System.out.println("886");
				mailservice.sendMail(membersVO.getEmail(), "歡迎加入動吃動吃", "http://yourhostname"+req.getContextPath()+"/members/members.do?action=send&mem_account="+membersVO.getMem_account());
				
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/front-end/homepage/Home.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/members/update_members_input.jsp");
				failureView.forward(req, res);
			}
		}
		
        if("send".equals(action)) {
        	MembersService membersSvc = new MembersService();
        	HttpSession session = req.getSession();
        	String mem_account = (String)(req.getParameter("mem_account"));
        	
        	MembersVO membersVO = membersSvc.getOneMemberByAccount(mem_account);
			String member_no = membersVO.getMember_no();

			session.setAttribute("member_no", member_no);

        	try {
        		Integer mem_status = 0;
        		
        		membersSvc.updateStatus(mem_status, member_no);
				
				String url = "/front-end/members/membersPage.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);
        		
        	}catch(Exception e) {
        		
        	}
        }
		
		if ("delete".equals(action)) { // 來自listAllMembers.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.接受請求參數 - 輸入格式的錯誤處理**********************/
				String member_no = new String(req.getParameter("member_no"));
				
				/***************************2.開始刪除資料***************************************/
				MembersService membersSvc = new MembersService();
				boolean i;
				i = membersSvc.deleteMembers(member_no);
				System.out.println(i);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/							
				String url = "/members/listAllMembers.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);//
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/members/listAllMembers.jsp");
				failureView.forward(req, res);
			}
		}
//		勝瑜加入
		if ("listNotes_ByMemberno".equals(action) ) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String memberno = new String(req.getParameter("member_no"));

				/*************************** 2.開始查詢資料 ****************************************/
				MembersService memberSvc = new MembersService();
				Set<NotificationListVO> set = memberSvc.getNotesByMemberno(memberno);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("listAllNotesByMember", set);    // 資料庫取出的list物件,存入request
				String url = "/front-end/members/listAllNotebyMember.jsp";              // 成功轉交 dept/listAllDept.jsp

				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 ***********************************/
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}
		if ("listExper_ByMemberno".equals(action) ) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String memberno = new String(req.getParameter("member_no"));

				/*************************** 2.開始查詢資料 ****************************************/
				MembersService memberSvc = new MembersService();
				Set<ExperienceVO> set = memberSvc.getExperByMemberno(memberno);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("listAllExperByMember", set);    // 資料庫取出的list物件,存入request
				String url = "/front-end/members/listAllExperbyMember.jsp";              // 成功轉交 dept/listAllDept.jsp

				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 ***********************************/
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}
	}
}

