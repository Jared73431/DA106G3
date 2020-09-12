
package com.dietdetail.controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import com.dietdetail.model.DietDetailService;
import com.dietdetail.model.DietDetailVO;
import com.dietrecord.model.DietRecordVO;
import com.foodnutrition.model.FoodNutritionService;
import com.foodnutrition.model.FoodNutritionVO;

public class DietDetailServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req,res);
	}
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		HttpSession session = req.getSession();
//		List<DietDetailVO>dietdetailList = (List<DietDetailVO>)session.getAttribute("dietdetailList");
		

		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String diet_no = req.getParameter("diet_no");
				if (diet_no == null || (diet_no.trim()).length() == 0) {
					errorMsgs.add("請輸入飲食編號");
				}
				
				DietDetailService dietDetailSvc = new DietDetailService();
				List<DietDetailVO> list= dietDetailSvc.getDietDetail(diet_no);
				
				if (list == null) {
					errorMsgs.add("查無資料");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/posture/posture.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				req.setAttribute("list", list);
				String url = "/front-end/dietdetail/listOneDietDetail.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.include(req, res);

			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/posture/posture.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOne_For_Update".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String diet_no = req.getParameter("diet_no");
				String food_no = req.getParameter("food_no");

				DietDetailService dietDetailSvc = new DietDetailService();
				DietDetailVO dietDetailVO= dietDetailSvc.getOneDietDetail(diet_no,food_no);
				
				req.setAttribute("dietDetailVO", dietDetailVO);
				String url = "/front-end/dietdetail/update_DietDetail_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/posture/posture.jsp");
				failureView.forward(req, res);
			}
		}
		if ("update".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String diet_no = req.getParameter("diet_no");
				String food_no = req.getParameter("food_no");

				Double amount = null;
				try {
					amount = new Double(req.getParameter("amount").trim());
					if(amount <= 0) {
						errorMsgs.add("請填正整數");
					}
				} catch (NumberFormatException e) {
					amount = 0.0;
					errorMsgs.add("請填數字.");
				}

				FoodNutritionService foodNutritionSvc = new FoodNutritionService();
				FoodNutritionVO foodNutritionVO = foodNutritionSvc.getOneFood(food_no);
				DecimalFormat df = new DecimalFormat("##.00");
				Double food_cal = Double.parseDouble(df.format(foodNutritionVO.getRef_cal()*amount));

				DietDetailVO dietDetailVO = new DietDetailVO();
				dietDetailVO.setDiet_no(diet_no);
				dietDetailVO.setFood_no(food_no);
				dietDetailVO.setAmount(amount);
				dietDetailVO.setFood_cal(food_cal);

				if (!errorMsgs.isEmpty()) {
					String url = "/dietrecord/dietrecord.do?action=getOne_For_Update&diet_no="+diet_no;
					RequestDispatcher failureView = req.getRequestDispatcher(url);
					failureView.forward(req, res);
					return; // 程式中斷
				}

				DietDetailService dietDetailSvc = new DietDetailService();
				dietDetailVO = dietDetailSvc.updateDietDetail(diet_no,food_no,amount,food_cal);

				req.setAttribute("dietDetailVO", dietDetailVO);
				String url = "/dietrecord/dietrecord.do?action=getOne_For_Update&diet_no="+diet_no;
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/dietrecord/update_DietRecord_input.jsp");
				failureView.forward(req, res);
			}
		}
		if ("insert".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String diet_no = req.getParameter("diet_no");
				String food_no = req.getParameter("food_no");

				Double amount = null;
				try {
					amount = new Double(req.getParameter("amount").trim());
					if(amount <= 0) {
						errorMsgs.add("請填正整數");
					}
				} catch (NumberFormatException e) {
					amount = 0.0;
					errorMsgs.add("請填數字.");
				}
				
				FoodNutritionService foodNutritionSvc = new FoodNutritionService();
				FoodNutritionVO foodNutritionVO = foodNutritionSvc.getOneFood(food_no);
				DecimalFormat df = new DecimalFormat("##.00");
				Double food_cal = Double.parseDouble(df.format(foodNutritionVO.getRef_cal()*amount));

				DietDetailVO dietDetailVO = new DietDetailVO();
				dietDetailVO.setDiet_no(diet_no);
				dietDetailVO.setFood_no(food_no);
				dietDetailVO.setAmount(amount);
				dietDetailVO.setFood_cal(food_cal);
				
				List list = (List)session.getAttribute("list");
				if(list.contains(dietDetailVO)) {
					errorMsgs.add("已經有相同食物囉");
				}else {
					DietDetailService dietDetailSvc = new DietDetailService();
					dietDetailVO = dietDetailSvc.addDietDetail(diet_no,food_no,amount,food_cal);
				}

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("dietDetailVO", dietDetailVO);
					String url = "/dietrecord/dietrecord.do?action=getOne_For_Update&diet_no="+diet_no;
					RequestDispatcher failureView = req.getRequestDispatcher(url);
					failureView.forward(req, res);
					return; // 程式中斷
				}

				String url = "/dietrecord/dietrecord.do?action=getOne_For_Update&diet_no="+diet_no;
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/posture/posture.jsp");
				failureView.forward(req, res);
			}
		}
		if ("delete".equals(action)){ 

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String diet_no = req.getParameter("diet_no");
				String food_no = req.getParameter("food_no");

				DietDetailService dietDetailSvc = new DietDetailService();
				dietDetailSvc.deleteDietDetail(diet_no,food_no);
				
				
				String url = "/dietrecord/dietrecord.do?action=getOne_For_Update&food_no="+food_no;
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage() );
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/posture/posture.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("add".equals(action)) {

			List<DietDetailVO>dietdetailList = (List<DietDetailVO>)session.getAttribute("dietdetailList");
			try {
				if(dietdetailList == null) {
					dietdetailList = new LinkedList<DietDetailVO>();
				}
				
				String food_no = req.getParameter("food_no");
				
				DecimalFormat df = new DecimalFormat("##.0");
				Double amount = new Double(req.getParameter("amount"));
				amount = Double.parseDouble(df.format(amount));
						
				FoodNutritionService foodNutritionSvc = new FoodNutritionService();
				FoodNutritionVO foodNutritionVO = foodNutritionSvc.getOneFood(food_no);
				Double food_cal = Double.parseDouble(df.format(foodNutritionVO.getRef_cal()*amount));
				
				DietDetailVO dietDetailVO = new DietDetailVO();
				dietDetailVO.setFood_no(food_no);				
				dietDetailVO.setAmount(amount);
				dietDetailVO.setFood_cal(food_cal);
					
				if(dietdetailList.contains(dietDetailVO)) {
					DietDetailVO mydietDetailVO = dietdetailList.get(dietdetailList.indexOf(dietDetailVO));
					mydietDetailVO.setAmount(Double.parseDouble(df.format(dietDetailVO.getAmount()+mydietDetailVO.getAmount())));
					mydietDetailVO.setFood_cal(Double.parseDouble(df.format(dietDetailVO.getFood_cal()+mydietDetailVO.getFood_cal())));
				}else {
					dietdetailList.add(dietDetailVO);
				}
				session.setAttribute("dietdetailList", dietdetailList);
				JSONArray outArray = new JSONArray();
				JSONObject output = null;
				for(int i=0; i<dietdetailList.size(); i++) {
					DietDetailVO mydietDetailVO = dietdetailList.get(i);
					
					output = new JSONObject();					
					output.put("food_no", mydietDetailVO.getFood_no());
					String food_name = foodNutritionSvc.getOneFood(mydietDetailVO.getFood_no()).getFood_name();
					output.put("food_name", food_name);
					output.put("amount", mydietDetailVO.getAmount());
					output.put("food_cal", mydietDetailVO.getFood_cal());
					outArray.put(output);
				}
				res.setContentType("text/html;charset=UTF-8");
				PrintWriter out = res.getWriter();
				out.write(outArray.toString());
				out.flush();
				out.close();


			} catch (Exception e) {
				System.out.println("修改資料失敗:" + e.getMessage());

			}
		}
		if ("delete_de".equals(action)){ 
			List<DietDetailVO>dietdetailList = (List<DietDetailVO>)session.getAttribute("dietdetailList");
			try {
			
			String food_no = req.getParameter("food_no");
			
			Double amount = new Double(req.getParameter("amount"));
		
			Double food_cal = new Double(req.getParameter("food_cal"));
		
			
			DietDetailVO dietDetailVO = new DietDetailVO();
			dietDetailVO.setFood_no(food_no);				
			dietDetailVO.setAmount(amount);
			dietDetailVO.setFood_cal(food_cal);
			dietdetailList.remove(dietDetailVO);
				
			session.setAttribute("dietdetailList", dietdetailList);
			
			FoodNutritionService foodNutritionSvc = new FoodNutritionService();
			JSONArray outArray = new JSONArray();
			JSONObject output = null;
			for(int i=0; i<dietdetailList.size(); i++) {
				DietDetailVO mydietDetailVO = dietdetailList.get(i);				
				output = new JSONObject();					
				output.put("food_no", mydietDetailVO.getFood_no());
				String food_name = foodNutritionSvc.getOneFood(mydietDetailVO.getFood_no()).getFood_name();
				output.put("food_name", food_name);
				output.put("amount", mydietDetailVO.getAmount());
				output.put("food_cal", mydietDetailVO.getFood_cal());
				outArray.put(output);
			}
			res.setContentType("text/html;charset=UTF-8");
			PrintWriter out = res.getWriter();
			out.write(outArray.toString());
			out.flush();
			out.close();


				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				

			}
		}
		

	}

}
