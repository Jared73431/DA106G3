package com.posture.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.posture.model.PostureService;
import com.posture.model.PostureVO;

public class PostureServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String member_no = req.getParameter("member_no");
				if (member_no == null || (member_no.trim()).length() == 0) {
					errorMsgs.add("請輸入會員編號");
				}				

				PostureService postureSvc = new PostureService();
				List<PostureVO> list= postureSvc.getMember(member_no);
				
				if (list == null) {
					errorMsgs.add("查無資料");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/posture/posture.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				req.setAttribute("list", list);
				String url = "/front-end/posture/listOnePosture.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/posture/posture.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("getline".equals(action)) { // 來自select_page.jsp的請求

			
				String member_no = req.getParameter("member_no");

				PostureService postureSvc = new PostureService();
				List<PostureVO> list = postureSvc.getMember(member_no);

				JSONArray output1 = new JSONArray();
				JSONArray output2 = new JSONArray();
				JSONArray output3 = new JSONArray();

				try {
					if (list.size() >= 7) {
						java.sql.Date record_date = java.sql.Date.valueOf(req.getParameter("record_date"));

						for (int i = 0; i < list.size(); i++) {
							if (record_date.compareTo(list.get(i).getRecord_date()) == 0 && i >= 6 ) {
								
								for (int k =i-6; k <= i; k++) {
									output1.put(list.get(k).getRemain_cal());
									output2.put(list.get(k).getRecord_date());
								}
								output3.put(output1);
								output3.put(output2);
								throw new Exception("故意的");
							}
						}

						for (int i = list.size()-7; i <= list.size()-1; i++) {
			
							output1.put(list.get(i).getRemain_cal());
							output2.put(list.get(i).getRecord_date());
						}
						output3.put(output1);
						output3.put(output2);
					} else {
						output3.put(false);
					}
				} catch (Exception e) {
				}

				res.setContentType("text/html;charset=UTF-8");
				PrintWriter out = res.getWriter();
				out.write(output3.toString());
				out.flush();
				out.close();

			

//			RequestDispatcher failureView = req.getRequestDispatcher("/front-end/posture/posture.jsp");
//			failureView.forward(req, res);
		}

	if("update".equals(action)){

	List<String> errorMsgs = new LinkedList<String>();req.setAttribute("errorMsgs",errorMsgs);

	try
	{
		String posture_no = req.getParameter("posture_no");
		String member_no = req.getParameter("member_no");
		java.sql.Date record_date = null;

		try {
			record_date = java.sql.Date.valueOf(req.getParameter("record_date").trim());
		} catch (IllegalArgumentException e) {
			record_date = new java.sql.Date(System.currentTimeMillis());
			errorMsgs.add("請輸入日期!");
		}
		Double weight = null;
		try {
			weight = new Double(req.getParameter("weight").trim());
		} catch (NumberFormatException e) {
			weight = 0.0;
			errorMsgs.add("請填數字.");
		}
		Double bodyfat = null;
		try {
			bodyfat = new Double(req.getParameter("bodyfat").trim());
		} catch (NumberFormatException e) {
			bodyfat = 0.0;
			errorMsgs.add("請填數字.");
		}
		Integer bmr = null;
		try {
			bmr = new Integer(req.getParameter("bmr").trim());
		} catch (NumberFormatException e) {
			bmr = 0;
			errorMsgs.add("請填數字.");
		}
		Double bmi = null;
		try {
			bmi = new Double(req.getParameter("bmi").trim());
		} catch (NumberFormatException e) {
			bmi = 0.0;
			errorMsgs.add("請填數字.");
		}
		Double remain_cal = null;
		try {
			remain_cal = new Double(req.getParameter("remain_cal").trim());
		} catch (NumberFormatException e) {
			remain_cal = 0.0;
			errorMsgs.add("請填數字.");
		}
		PostureVO postureVO = new PostureVO();
		postureVO.setPosture_no(posture_no);
		postureVO.setMember_no(member_no);
		postureVO.setRecord_date(record_date);
		postureVO.setWeight(weight);
		postureVO.setBodyfat(bodyfat);
		postureVO.setBmr(bmr);
		postureVO.setBmi(bmi);
		postureVO.setRemain_cal(remain_cal);

		if (!errorMsgs.isEmpty()) {
			req.setAttribute("postureVO", postureVO);
			RequestDispatcher failureView = req.getRequestDispatcher("/front-end/posture/posture.jsp");
			failureView.forward(req, res);
			return; // 程式中斷
		}
		PostureService postureSvc = new PostureService();
		postureVO = postureSvc.update(posture_no, member_no, record_date, weight, bodyfat, bmr, bmi, remain_cal);

		JSONObject output = new JSONObject();
		output.put("success", "Y");

		res.setContentType("text/plain");
		res.setCharacterEncoding("UTF-8");
		PrintWriter out = res.getWriter();
		out.write(output.toString());
		out.flush();
		out.close();

	}catch(
	Exception e)
	{
		errorMsgs.add("修改資料失敗:" + e.getMessage());
		RequestDispatcher failureView = req.getRequestDispatcher("/front-end/posture/posture.jsp");
		failureView.forward(req, res);
	}}if("insert".equals(action)){

	List<String> errorMsgs = new LinkedList<String>();req.setAttribute("errorMsgs",errorMsgs);

	try
	{
		String member_no = req.getParameter("member_no");

		java.sql.Date record_date = null;

		try {
			record_date = java.sql.Date.valueOf(req.getParameter("record_date").trim());
		} catch (IllegalArgumentException e) {
			record_date = new java.sql.Date(System.currentTimeMillis());
			errorMsgs.add("請輸入日期!");
		}
		Double weight = null;
		try {
			weight = new Double(req.getParameter("weight").trim());
		} catch (NumberFormatException e) {
			weight = 0.0;
			errorMsgs.add("請填數字.");
		}

		Double bodyfat = 0.0;
		try {
			bodyfat = new Double(req.getParameter("bodyfat").trim());
		} catch (NumberFormatException e) {
			bodyfat = 0.0;
			errorMsgs.add("請填數字.");
		}

		Integer bmr = null;
		try {
			bmr = new Integer(req.getParameter("bmr").trim());
		} catch (NumberFormatException e) {
			bmr = 0;
			errorMsgs.add("請填數字.");
		}

		Double bmi = 0.0;
		try {
			bmi = new Double(req.getParameter("bmi").trim());
		} catch (NumberFormatException e) {
			bmi = 0.0;
			errorMsgs.add("請填數字.");
		}

		Double remain_cal = 0.0 + bmr;

		PostureVO postureVO = new PostureVO();
		postureVO.setMember_no(member_no);
		postureVO.setRecord_date(record_date);
		postureVO.setWeight(weight);
		postureVO.setBodyfat(bodyfat);
		postureVO.setBmr(bmr);
		postureVO.setBmi(bmi);
		postureVO.setRemain_cal(remain_cal);

//				if (!errorMsgs.isEmpty()) {
//					req.setAttribute("postureVO", postureVO);
//					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/posture/addPosture.jsp");
//					failureView.forward(req, res);
//					return; // 程式中斷
//				}

		PostureService postureSvc = new PostureService();
		postureVO = postureSvc.addPosture(member_no, record_date, weight, bodyfat, bmr, bmi, remain_cal);
		JSONObject output = new JSONObject();
		output.put("success", "Y");

		res.setContentType("text/plain");
		res.setCharacterEncoding("UTF-8");
		PrintWriter out = res.getWriter();
		out.write(output.toString());
		out.flush();
		out.close();

	}catch(
	Exception e)
	{
		errorMsgs.add("修改資料失敗:" + e.getMessage());
		RequestDispatcher failureView = req.getRequestDispatcher("/front-end/posture/posture.jsp");
		failureView.forward(req, res);
	}}

	if("delete".equals(action)){ // 來自listAllEmp.jsp

	List<String> errorMsgs = new LinkedList<String>();req.setAttribute("errorMsgs",errorMsgs);

	try
	{
		String posture_no = req.getParameter("posture_no");

		PostureService postureSvc = new PostureService();
		postureSvc.delete(posture_no);

		/*************************** 其他可能的錯誤處理 **********************************/
	}catch(
	Exception e)
	{
		errorMsgs.add("刪除資料失敗:" + e.getMessage());
		RequestDispatcher failureView = req.getRequestDispatcher("/front-end/posture/posture.jsp");
		failureView.forward(req, res);
	}
}

}

}
