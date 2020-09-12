package com.filters;

import java.io.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

import com.admins.model.*;
import com.authorizations.model.*;

public class BackLoginHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// 【檢查使用者輸入的帳號(account) 密碼(password)是否有效】
	// 【實際上應至資料庫搜尋比對】
	protected boolean allowUser(String account, String password) {
		AdminsService adminsSvc = new AdminsService();

		List<AdminsVO> allAdmins = adminsSvc.getAll();
		Optional<AdminsVO> result = allAdmins.stream().filter(e -> e.getAdmin_account().equals(account))
				.filter(e -> e.getAdmin_password().equals(password)).filter(e ->e.getAdmin_status()==1).findFirst(); // 先檢查帳號在檢查密碼

		return result.isPresent(); // 有就回傳TRUE 沒有就 FALSE

	}

	protected AdminsVO getVO(String account, String password) {
		AdminsService adminsSvc = new AdminsService();

		List<AdminsVO> allAdmins = adminsSvc.getAll();

		Optional<AdminsVO> result = allAdmins.stream().filter(e -> e.getAdmin_account().equals(account))
				.filter(e -> e.getAdmin_password().equals(password)).filter(e ->e.getAdmin_status()==1).findFirst(); // 先檢查帳號在檢查密碼
		return result.get();
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String action = req.getParameter("action");
		if("signin".equals(action)) {
		// 【取得使用者 帳號(account) 密碼(password)】
		String account = req.getParameter("account");
		String password = req.getParameter("password");
		// 【檢查該帳號 , 密碼是否有效】
		if (!allowUser(account, password)) { // 【帳號 , 密碼無效時】
			req.setCharacterEncoding("UTF-8");
		    res.setContentType("text/html; charset=UTF-8");
			PrintWriter out = res.getWriter();
			out.println("<HTML><HEAD><TITLE>Access Denied</TITLE></HEAD>");
			out.println("<BODY>你的帳號 , 密碼無效!<BR>");
			out.println("請按此重新登入 <A HREF=" + req.getContextPath() + "/backlogin.jsp>重新登入</A>");
			out.println("</BODY></HTML>");
		} else { 
			HttpSession session = req.getSession();

			// 【帳號 , 密碼有效時, 才做以下工作】
			
			session.setAttribute("account", account); // *工作1: 才在session內做已經登入過的標識
			
			AdminsVO adminsVO = getVO(account,password);
			session.setAttribute("adminsVO", adminsVO);  //登入員工的物件
			
			AuthorizationsService authorizationsSvc = new AuthorizationsService();
			List<AuthorizationsVO> listForAdmin = authorizationsSvc.getOneAdmin(adminsVO.getAdmin_no());
			
			List<String> authList = listForAdmin.stream()
												.map(e -> e.getFeature_no())
												.collect(Collectors.toList());
			session.setAttribute("authList", authList);	  //權限清單								
					
			try {
				String location = (String) session.getAttribute("location");
				if (location != null) {
					session.removeAttribute("location"); // *工作2: 看看有無來源網頁 (-->如有來源網頁:則重導至來源網頁)
					res.sendRedirect(location);
					return;
				}
			} catch (Exception ignored) {
			}
			res.sendRedirect(req.getContextPath() + "/back-end/backstage/index.jsp"); // *工作3: (-->如無來源網頁:則重導至login_success.jsp)
			}
		}
		if("logout".equals(action)) {
			HttpSession session = req.getSession();
			session.invalidate();
			res.sendRedirect(req.getContextPath() + "/backlogin.jsp");

		}
	}
}