package com.foodnutrition.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.exerciseitem.model.ExerciseItemService;
import com.exerciseitem.model.ExerciseItemVO;
import com.foodnutrition.model.FoodNutritionService;
import com.foodnutrition.model.FoodNutritionVO;

public class FoodNutritionServlet extends HttpServlet {

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
				String food_no = req.getParameter("food_no");
				if (food_no == null || (food_no.trim()).length() == 0) {
					errorMsgs.add("請輸入食物編號");
				}
			
				FoodNutritionService foodNutritionSvc = new FoodNutritionService();
				FoodNutritionVO foodNutritionVO = foodNutritionSvc.getOneFood(food_no);
				if (foodNutritionVO == null) {
					errorMsgs.add("查無資料");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/foodnutrition/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				req.setAttribute("foodNutritionVO", foodNutritionVO);
				String url = "/back-end/foodnutrition/listOneFood.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/foodnutrition/select_page.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOne_For_Update".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");
			try {
				String food_no = req.getParameter("food_no");

				FoodNutritionService foodNutritionSvc = new FoodNutritionService();
				FoodNutritionVO foodNutritionVO = foodNutritionSvc.getOneFood(food_no);

				req.setAttribute("foodNutritionVO", foodNutritionVO);
				String url = "/back-end/foodnutrition/update_food_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/foodnutrition/listAllFood.jsp");
				failureView.forward(req, res);
			}
		}
		if ("update".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");

			try {
				String food_no = req.getParameter("food_no");

				String food_name = req.getParameter("food_name").trim();
				if (food_name == null || food_name.trim().length() == 0) {
					errorMsgs.add("項目請勿空白");
				}

				String ref_brand = req.getParameter("ref_brand").trim();

				String ref_amount = req.getParameter("ref_amount").trim();
				if (ref_amount == null || ref_amount.trim().length() == 0) {
					errorMsgs.add("項目請勿空白");
				}

				Double ref_cal = null;
				try {
					ref_cal = new Double(req.getParameter("ref_cal").trim());
				} catch (NumberFormatException e) {
					ref_cal = 0.0;
					errorMsgs.add("請填數字.");
				}

				Double ref_protein = null;
				try {
					ref_protein = new Double(req.getParameter("ref_protein").trim());
				} catch (NumberFormatException e) {
					ref_protein = 0.0;
					errorMsgs.add("請填數字.");
				}

				Double ref_fat = null;
				try {
					ref_fat = new Double(req.getParameter("ref_fat").trim());
				} catch (NumberFormatException e) {
					ref_fat = 0.0;
					errorMsgs.add("請填數字.");
				}

				Double ref_cho = null;
				try {
					ref_cho = new Double(req.getParameter("ref_cho").trim());
				} catch (NumberFormatException e) {
					ref_cho = 0.0;
					errorMsgs.add("請填數字.");
				}
				FoodNutritionVO foodNutritionVO = new FoodNutritionVO();
				foodNutritionVO.setFood_name(food_name);
				foodNutritionVO.setRef_brand(ref_brand);
				foodNutritionVO.setRef_amount(ref_amount);
				foodNutritionVO.setRef_cal(ref_cal);
				foodNutritionVO.setRef_protein(ref_protein);
				foodNutritionVO.setRef_fat(ref_fat);
				foodNutritionVO.setRef_cho(ref_cho);
				foodNutritionVO.setFood_no(food_no);
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("foodNutritionVO", foodNutritionVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/foodnutrition/update_food_input.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}
				
				FoodNutritionService foodNutritionSvc = new FoodNutritionService();
				foodNutritionVO = foodNutritionSvc.updateFood(food_name, ref_brand, ref_amount, ref_cal, ref_protein,
						ref_fat, ref_cho, food_no);
				req.setAttribute("foodNutritionVO", foodNutritionVO);
				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/foodnutrition/update_food_input.jsp");
				failureView.forward(req, res);
			}
		}
		if ("insert".equals(action)) { // 來自addEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				String food_name = req.getParameter("food_name").trim();
				if (food_name == null || food_name.trim().length() == 0) {
					errorMsgs.add("項目請勿空白");
				}

				String ref_brand = req.getParameter("ref_brand").trim();

				String ref_amount = req.getParameter("ref_amount").trim();
				if (ref_amount == null || ref_amount.trim().length() == 0) {
					errorMsgs.add("項目請勿空白");
				}

				Double ref_cal = null;
				try {
					ref_cal = new Double(req.getParameter("ref_cal").trim());
				} catch (NumberFormatException e) {
					ref_cal = 0.0;
					errorMsgs.add("請填數字.");
				}

				Double ref_protein = null;
				try {
					ref_protein = new Double(req.getParameter("ref_protein").trim());
				} catch (NumberFormatException e) {
					ref_protein = 0.0;
					errorMsgs.add("請填數字.");
				}

				Double ref_fat = null;
				try {
					ref_fat = new Double(req.getParameter("ref_fat").trim());
				} catch (NumberFormatException e) {
					ref_fat = 0.0;
					errorMsgs.add("請填數字.");
				}

				Double ref_cho = null;
				try {
					ref_cho = new Double(req.getParameter("ref_cho").trim());
				} catch (NumberFormatException e) {
					ref_cho = 0.0;
					errorMsgs.add("請填數字.");
				}

				FoodNutritionVO foodNutritionVO = new FoodNutritionVO();
				foodNutritionVO.setFood_name(food_name);
				foodNutritionVO.setRef_brand(ref_brand);
				foodNutritionVO.setRef_amount(ref_amount);
				foodNutritionVO.setRef_cal(ref_cal);
				foodNutritionVO.setRef_protein(ref_protein);
				foodNutritionVO.setRef_fat(ref_fat);
				foodNutritionVO.setRef_cho(ref_cho);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("foodNutritionVO", foodNutritionVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/foodnutrition/addFood.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				FoodNutritionService foodNutritionSvc = new FoodNutritionService();
				foodNutritionVO = foodNutritionSvc.addFood(food_name, ref_brand, ref_amount, ref_cal, ref_protein,
						ref_fat, ref_cho);

				req.setAttribute("foodSuccess", true);
				String url = "/front-end/foodnutrition/addFood.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/foodnutrition/addFood.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("delete".equals(action)) { // 來自listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");
			
			try {
				String food_no = req.getParameter("food_no");

				FoodNutritionService foodNutritionSvc = new FoodNutritionService();
				foodNutritionSvc.deleteFood(food_no);

				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/foodnutrition/listAllFood.jsp");
				failureView.forward(req, res);
			}
		}
	}

}
