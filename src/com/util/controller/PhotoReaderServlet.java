package com.util.controller;

import java.io.*;


import javax.servlet.*;
import javax.servlet.http.*;

import com.foodinform.model.*;
import com.newsknowledge.model.*;
import com.product.model.*;

public class PhotoReaderServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		res.setContentType("image/jpeg");
		ServletOutputStream out = res.getOutputStream();

		try {
			String pk_no = req.getParameter("pk_no"); // 主鍵號碼
			String action = req.getParameter("action"); // 表格名稱
			byte[] pic = null;
			
			if ("product".equals(action)) { // 來自product 的請求
				ProductService productSvc = new ProductService();
				pic = (productSvc.readPic(pk_no));
			}
			if ("food_inform".equals(action)) { // 來自food_inform 的請求
				FoodInformService foodinformSvc = new FoodInformService();
				pic = (foodinformSvc.readPic(pk_no));
			}
			if ("news_knowledge".equals(action)) { // 來自news_knowledge 的請求
				NewsKnowledgeService newsknowledgeSvc = new NewsKnowledgeService();
				pic = (newsknowledgeSvc.readPic(pk_no));
			}
			/*************************** 開始讀照片 **********************************/
			if (pic !=null) {
				out.write(pic);
			} else {
				// res.sendError(HttpServletResponse.SC_NOT_FOUND);
				InputStream in = getServletContext().getResourceAsStream("/picture/null2.jpg");
				byte[] buf = new byte[in.available()];
				in.read(buf);
				out.write(buf);
				in.close();
			}
		} catch (Exception e) {
			// System.out.println(e);
			InputStream in = getServletContext().getResourceAsStream("/picture/none.jpg");
			byte[] buf = new byte[in.available()];
			in.read(buf);
			out.write(buf);
			in.close();
		}
	}
	
}