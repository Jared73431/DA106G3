	package com.admins.controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.admins.model.AdminsService;
import com.admins.model.AdminsVO;
import com.admins.model.DBGifReader4Admin;

@MultipartConfig
public class AdminsServlet extends HttpServlet {
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
				String str = req.getParameter("admin_no");
				if (str == null || (str.trim().length() == 0)) {
					errorMsgs.add("請輸入員工編號");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/backstage/index.jsp");
					failureView.forward(req, res);
					return;
				}
				String admin_no = null;
				try {
					admin_no = new String(str);
				} catch (Exception e) {
					errorMsgs.add("員工編號格式不正確");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/backstage/index.jsp");
					failureView.forward(req, res);
					return;
				}
				/****************************
				 * 2.開始查詢資料
				 ******************************************/
				AdminsService adminSvc = new AdminsService();
				AdminsVO adminsVO = adminSvc.getOneAdmin(admin_no);
				if (adminsVO == null) {
					errorMsgs.add("查無資料");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/backstage/index.jsp");
					failureView.forward(req, res);
					return;
				}
				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/

				req.setAttribute("adminsVO", adminsVO);
				String url = "/back-end/admins/listOneAdmin.jsp";
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
				String admin_no = new String(req.getParameter("admin_no").trim());
				/*************************** 2.開始查詢資料 ****************************************/
				AdminsService adminSvc = new AdminsService();
				AdminsVO adminsVO = adminSvc.getOneAdmin(admin_no);
				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("adminsVO", adminsVO);
				String url = "/back-end/admins/update_admin_input.jsp";
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
				String admin_no = new String(req.getParameter("admin_no").trim());

				String admin_name = req.getParameter("admin_name");
				String admin_nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z_)]{2,15}$";
				if (admin_name == null || admin_name.trim().length() == 0) {
					errorMsgs.add("姓名請勿空白");
				} else if (!admin_name.trim().matches(admin_nameReg)) {
					errorMsgs.add("姓名只能是中、英文字母且長度需2-15之間");
				}

				String admin_account = req.getParameter("admin_account");

				String admin_password = req.getParameter("admin_password");
				String admin_passwordReg = "^[(a-zA-Z0-9)]{6,18}$";

				if (admin_password == null || admin_password.trim().length() == 0) {
					errorMsgs.add("密碼請勿空白");
				} else if (!admin_password.trim().matches(admin_passwordReg)) {
					errorMsgs.add("密碼只能是英文字母和數字,不可包含特殊字元包含空白,且長度為6到18之間");
				}

				Integer admin_status = new Integer(req.getParameter("admin_status").trim());

				byte[] admin_photo = null;

				AdminsVO adminsVO = new AdminsVO();// 需上傳圖片的判斷 先在這邊新增物件

				long size = req.getPart("admin_photo").getSize();
				if (size != 0) { // 判定有上傳圖片
					try (InputStream in = req.getPart("admin_photo").getInputStream();
							BufferedInputStream bis = new BufferedInputStream(in);
							ByteArrayOutputStream bos = new ByteArrayOutputStream();) {
						byte[] b = new byte[4 * 1024];
						int len = 0;
						while ((len = bis.read(b)) != -1) {
							bos.write(b, 0, len);
						}
						admin_photo = bos.toByteArray();
						

					} catch (IOException ioe) {
						System.out.println("GG");
					}
				} else { // 沒上傳圖片 從資料庫讀出再放進去
					AdminsService adminSvc = new AdminsService();

					if ((adminSvc.getPicture(admin_no) != null)) {//確認資料庫有圖片再讀圖

						admin_photo = adminSvc.getPicture(admin_no);
						
					}
				}
				adminsVO.setAdmin_photo(admin_photo);
				adminsVO.setAdmin_no(admin_no);
				adminsVO.setAdmin_name(admin_name);
				adminsVO.setAdmin_account(admin_account);
				adminsVO.setAdmin_password(admin_password);
				adminsVO.setAdmin_status(admin_status);

				if (!errorMsgs.isEmpty()) {

					req.setAttribute("adminsVO", adminsVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/admins/update_admin_input.jsp");
					failureView.forward(req, res);
					return;
				}
				/*************************** 2.開始修改資料 *****************************************/
				AdminsService adminSvc = new AdminsService();
				adminsVO = adminSvc.updateAdmin(admin_no, admin_account, admin_password, admin_name, admin_photo,
						admin_status);

				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
	
				req.setAttribute("adminsVO", adminsVO);
				String url = requestURL;
	
				RequestDispatcher successView = req.getRequestDispatcher(url);
	
				successView.forward(req, res);
	
				/*************************** 其他可能的錯誤處理 *************************************/

			} catch (Exception e) {
	
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/admins/update_admin_input.jsp");
				failureView.forward(req, res);
			}
		}

		if ("insert".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				String admin_name = req.getParameter("admin_name").trim();

				if (admin_name == null || admin_name.trim().length() == 0) {
					errorMsgs.add("姓名請勿空白");
				}

				String admin_account = req.getParameter("admin_account");
				AdminsService check = new AdminsService();
				int count = check.checkAccount(admin_account);// 確認帳號有無重複 不等於0為重複

				if (admin_account == null || admin_account.trim().length() == 0) {
					errorMsgs.add("帳號請勿空白");
				} else if (count != 0) {
					errorMsgs.add("此帳號已重複");
				}
				
				
				Random random = new Random();
				String admin_password ="";
				
				for(int i = 0 ; i < 8 ; i++) {
					switch(random.nextInt(3)) {
						case 0:
							admin_password += random.nextInt(10);
							break;
						case 1:
							admin_password +=	(char) (random.nextInt(26)+65);
							break;
						case 2:
							admin_password +=	(char) (random.nextInt(26)+97);
							break;
					}
				}
				
				
				Integer admin_status = new Integer(req.getParameter("admin_status").trim());
				byte[] admin_photo = null;
				AdminsVO adminsVO = new AdminsVO();
				try (InputStream in = req.getPart("admin_photo").getInputStream();
						BufferedInputStream bis = new BufferedInputStream(in);
						ByteArrayOutputStream bos = new ByteArrayOutputStream();) {
					byte[] b = new byte[4 * 1024];
					int len = 0;

					while ((len = bis.read(b)) != -1) {
						bos.write(b, 0, len);
					}
					admin_photo = bos.toByteArray();
					adminsVO.setAdmin_photo(admin_photo);

				} catch (IOException ioe) {
					System.out.println("GG");
				}

				adminsVO.setAdmin_name(admin_name);
				adminsVO.setAdmin_account(admin_account);
				adminsVO.setAdmin_password(admin_password);
				adminsVO.setAdmin_status(admin_status);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("adminsVO", adminsVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/admins/addAdmin.jsp");
					failureView.forward(req, res);
					return;
				}
				/*************************** 2.開始新增資料 ***************************************/
				AdminsService adminSvc = new AdminsService();

				adminsVO = adminSvc.addAdmin(admin_account, admin_password, admin_name, admin_photo, admin_status);
				
				String to = admin_account;
				String subject = "動吃動吃員工的密碼通知";
				String messageText = "Hi!很高興 "+admin_name+" 先生/小姐您的加入  以下為您的密碼  請妥善保管: \n"+admin_password+"\n" +" (已經啟用)";
				
				adminSvc.sendMail(to, subject, messageText);
				
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/back-end/admins/listAllAdmin.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {

				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/admins/addAdmin.jsp");
				failureView.forward(req, res);
			}
		}

		if ("delete".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL"); 
			try {
				/*************************** 1.接收請求參數 ***************************************/
				String admin_no = req.getParameter("admin_no");

				/*************************** 2.開始刪除資料 ***************************************/
				AdminsService adminSvc = new AdminsService();
				adminSvc.delete(admin_no);
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
