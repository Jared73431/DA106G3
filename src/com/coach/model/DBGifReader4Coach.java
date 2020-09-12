package com.coach.model;

import java.io.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.DataSource;

public class DBGifReader4Coach extends HttpServlet {

	Connection con;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("image/gif");
		ServletOutputStream out = res.getOutputStream();
		byte[] pic = null;
		try {

			Statement stmt = con.createStatement();

			String member_no = req.getParameter("member_no").trim();
			String licence = req.getParameter("licence").trim();
			ResultSet rs = stmt.executeQuery("SELECT " + licence + " FROM coach WHERE member_no='" + member_no + "'");

			if (rs.next()){
				pic = rs.getBytes(1);
				out.write(pic);
				
			}else{
				System.out.println("跑進Else");
				InputStream in = getServletContext().getResourceAsStream("/picture/noPic.jpg");
				byte[] b = new byte[in.available()];
				in.read(b);
				out.write(b);
				in.close();
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			System.out.println("跑進E");

			InputStream in = getServletContext().getResourceAsStream("/picture/noPic.jpg");
			byte[] b = new byte[in.available()];
			in.read(b);
			out.write(b);
			in.close();
		}
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}

	public void init() throws ServletException {
		try {
			Context ctx = new javax.naming.InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDBG3");
			con = ds.getConnection();

		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void destroy() {
		try {
			if (con != null)
				con.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

}