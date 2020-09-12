package com.dietrecord.controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.dietdetail.model.DietDetailService;
import com.dietdetail.model.DietDetailVO;
import com.dietrecord.model.DietRecordService;
import com.dietrecord.model.DietRecordVO;
import com.exerciseitem.model.ExerciseItemService;
import com.exerciseitem.model.ExerciseItemVO;
import com.exerciserecord.model.ExerciseRecordService;
import com.exerciserecord.model.ExerciseRecordVO;
import com.foodnutrition.model.FoodNutritionService;
import com.foodnutrition.model.FoodNutritionVO;
import com.posture.model.PostureService;
import com.posture.model.PostureVO;

@MultipartConfig

public class DietRecordServlet extends HttpServlet {

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
				String str = req.getParameter("diet_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入紀錄編號");
				}

				String diet_no = "";
				try {
					diet_no = str;
				} catch (Exception e) {
					errorMsgs.add("編號格式不正確");
				}
				
				/*************************** 2.開始查詢資料 *****************************************/
				DietRecordService dietRecordSvc = new DietRecordService();
				DietRecordVO dietRecordVO = dietRecordSvc.getOneDietRecord(diet_no);
				if (dietRecordVO == null) {
					errorMsgs.add("查無資料");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/posture/posture.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("dietRecordVO", dietRecordVO); // 資料庫取出的empVO物件,存入req
				String url = "/front-end/dietrecord/listOneDietRecord.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/posture/posture.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("getAll_jsp".equals(action)) { // 來自select_page.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			Double todayCal = 0.0;

			String member_no = req.getParameter("member_no");

			java.sql.Date rec_date = null;
			try {
				rec_date = java.sql.Date.valueOf(req.getParameter("rec_date"));
			} catch (IllegalArgumentException e) {
				rec_date = new java.sql.Date(System.currentTimeMillis());
			}
			DietRecordService dietRecordSvc = new DietRecordService();
			List<DietRecordVO> list = dietRecordSvc.findByDate(member_no, rec_date);

			ExerciseRecordService exerciseRecordSvc = new ExerciseRecordService();
			List<ExerciseRecordVO> exelist = exerciseRecordSvc.findByDate(member_no, rec_date);

			PostureService postureSvc = new PostureService();
			PostureVO postureVO = postureSvc.findOneByDate(member_no, rec_date);

			JSONArray output = new JSONArray();
			JSONArray food = new JSONArray();
			JSONArray exe = new JSONArray();
			JSONObject posture = null;

			if (list != null) {
				for (DietRecordVO dietRecordVO : list) {

					try {
						JSONObject obj = new JSONObject();
						obj.put("diet_no", dietRecordVO.getDiet_no());
						obj.put("member_no", dietRecordVO.getMember_no());
						obj.put("rec_date", dietRecordVO.getRec_date());
						obj.put("eat_period", dietRecordVO.getEat_period());
						if(dietRecordVO.getPhoto() == null) {
							obj.put("photo", "false");
						}else{
							obj.put("photo", "true");
						}
						DietDetailService dietDetailSvc = new DietDetailService();
						List<DietDetailVO> Detaillist = dietDetailSvc.getDietDetail(dietRecordVO.getDiet_no());
						
						String diet_no = dietRecordVO.getDiet_no();
						Double total = 0.0;
						for (DietDetailVO dietDetailVO : Detaillist) {
							total = total + dietDetailVO.getFood_cal();
							DecimalFormat df = new DecimalFormat("##.00");
							total = Double.parseDouble(df.format(total));

						}
						
						todayCal = todayCal - total;
						obj.put("total", total);
						if(total ==0 && dietRecordVO.getPhoto() == null) {
							dietRecordSvc.deleteDietRecord(diet_no);
						}else {
						food.put(obj);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}

			if (exelist != null) {
				for (ExerciseRecordVO exerciseRecordVO : exelist) {
					ExerciseItemService exerciseItemSvc = new ExerciseItemService();
					ExerciseItemVO exerciseItemVO = exerciseItemSvc.getOneExe(exerciseRecordVO.getExe_no());

					try {
						JSONObject obj = new JSONObject();
						obj.put("exerec_no", exerciseRecordVO.getExerec_no());
						obj.put("exe_no", exerciseRecordVO.getExe_no());
						obj.put("exe_item", exerciseItemVO.getExe_item());
						obj.put("exe_time", exerciseRecordVO.getExe_time());
						obj.put("exe_tcal", exerciseRecordVO.getExe_tcal());
						todayCal = todayCal + exerciseRecordVO.getExe_tcal();
						exe.put(obj);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}

			if (postureVO != null) {
				try {
					posture = new JSONObject();
					posture.put("posture_no", postureVO.getPosture_no());
					posture.put("weight", postureVO.getWeight());
					posture.put("bodyfat", postureVO.getBodyfat());
					posture.put("bmr", postureVO.getBmr());
					todayCal = todayCal + postureVO.getBmr();
					DecimalFormat df = new DecimalFormat("##.00");
					Double remain_cal = Double.parseDouble(df.format(todayCal));
					postureSvc.updateRemaincal(remain_cal,postureVO.getPosture_no());
					
					//posture.put("todayCal", todayCal);					
					posture.put("bmi", postureVO.getBmi());
					posture.put("remain_cal", remain_cal);
					output.put(posture);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else {
				output.put("查無資料");
			}

			output.put(food);
			output.put(exe);
			res.setContentType("text/html;charset=UTF-8");
			PrintWriter out = res.getWriter();
			out.write(output.toString());
			out.flush();
			out.close();

		}

		if ("getfooddetail_jsp".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			String diet_no = req.getParameter("diet_no");
			DietDetailService dietDetailSvc = new DietDetailService();
			List<DietDetailVO> list = dietDetailSvc.getDietDetail(diet_no);

			if (list == null) {
				errorMsgs.add("查無資料");
			}

			JSONArray output = new JSONArray();
			JSONObject obj = null;

			for (DietDetailVO dietDetailVO : list) {
				try {
					obj = new JSONObject();
					obj.put("diet_no", dietDetailVO.getDiet_no());
					obj.put("food_no", dietDetailVO.getFood_no());
					obj.put("amount", dietDetailVO.getAmount());
					obj.put("food_cal", dietDetailVO.getFood_cal());

					FoodNutritionService foodNutritionSvc = new FoodNutritionService();
					FoodNutritionVO foodNutritionVO = foodNutritionSvc.getOneFood(dietDetailVO.getFood_no());
					obj.put("food_name", foodNutritionVO.getFood_name());
					obj.put("ref_brand", foodNutritionVO.getRef_brand());
					obj.put("ref_amount", foodNutritionVO.getRef_amount());
					obj.put("ref_cal", foodNutritionVO.getRef_cal());
					obj.put("ref_protein", foodNutritionVO.getRef_protein());
					obj.put("ref_fat", foodNutritionVO.getRef_fat());
					obj.put("ref_cho", foodNutritionVO.getRef_cho());

					output.put(obj);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			res.setContentType("text/html;charset=UTF-8");
			PrintWriter out = res.getWriter();
			out.write(output.toString());
			out.flush();
			out.close();

		}
		if ("getOne_For_Update".equals(action)) { // 來自listAllEmp.jsp的請求

			List<String> errorMsgs = (List) req.getAttribute("errorMsgs");
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String diet_no = req.getParameter("diet_no");

				/*************************** 2.開始查詢資料 ****************************************/
				DietRecordService dietRecordSvc = new DietRecordService();
				DietRecordVO dietRecordVO = dietRecordSvc.getOneDietRecord(diet_no);
				System.out.println(diet_no);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("dietRecordVO", dietRecordVO); // 資料庫取出的empVO物件,存入req
				System.out.println(dietRecordVO);
				String url = "/front-end/dietrecord/update_DietRecord_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/dietrecord/update_DietRecord_input.jsp");
				failureView.forward(req, res);
			}
		}
		if ("update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String diet_no = req.getParameter("diet_no");
				String member_no = req.getParameter("member_no");
				Integer eat_period = new Integer(req.getParameter("eat_period"));
				java.sql.Date rec_date = null;
				try {
					rec_date = java.sql.Date.valueOf(req.getParameter("rec_date").trim());
				} catch (IllegalArgumentException e) {
					rec_date = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
				byte[] photo = null;

				if (req.getPart("photo") != null) {
					try (InputStream in = req.getPart("photo").getInputStream();
							BufferedInputStream bis = new BufferedInputStream(in);
							ByteArrayOutputStream bos = new ByteArrayOutputStream();) {
						byte[] b = new byte[4 * 1024];
						int len = 0;
						while ((len = bis.read(b)) != -1) {
							bos.write(b, 0, len);
						}
						photo = bos.toByteArray();

					} catch (IOException ioe) {
						errorMsgs.add("圖片上傳失敗");
					}
				}
				DietRecordVO dietRecordVO = new DietRecordVO();
				dietRecordVO.setDiet_no(diet_no);
				dietRecordVO.setMember_no(member_no);
				dietRecordVO.setEat_period(eat_period);
				dietRecordVO.setRec_date(rec_date);
				dietRecordVO.setPhoto(photo);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("dietRecordVO", dietRecordVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/dietrecord/update_DietRecord_input.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}
				
				/*************************** 2.開始修改資料 *****************************************/
				DietRecordService dietRecordSvc = new DietRecordService();
				dietRecordVO = dietRecordSvc.updateDietRecord(diet_no, member_no, rec_date, eat_period, photo);

				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
				req.setAttribute("dietRecordVO", dietRecordVO); // 資料庫update成功後,正確的的empVO物件,存入req
				
				String url = "/front-end/dietrecord/update_DietRecord_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/dietrecord/update_DietRecord_input.jsp");
				failureView.forward(req, res);
			}
		}
		if ("insert".equals(action)) {

			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {

				String member_no = req.getParameter("member_no");

				Integer eat_period = new Integer(req.getParameter("eat_period").trim());

				java.sql.Date rec_date = null;
				try {
					rec_date = java.sql.Date.valueOf(req.getParameter("rec_date").trim());
				} catch (IllegalArgumentException e) {
					rec_date = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.put("rec_date","請輸入日期!");
				}

				byte[] photo = null;

				if (req.getPart("photo") != null) {
					try (InputStream in = req.getPart("photo").getInputStream();
							BufferedInputStream bis = new BufferedInputStream(in);
							ByteArrayOutputStream bos = new ByteArrayOutputStream();) {
						byte[] b = new byte[4 * 1024];
						int len = 0;
						while ((len = bis.read(b)) != -1) {
							bos.write(b, 0, len);
						}
						photo = bos.toByteArray();

					} catch (IOException ioe) {
						errorMsgs.put("photo","圖片上傳失敗");
					}
				}

				DietRecordVO dietRecordVO = new DietRecordVO();
				dietRecordVO.setMember_no(member_no);
				dietRecordVO.setEat_period(eat_period);
				dietRecordVO.setRec_date(rec_date);
				dietRecordVO.setPhoto(photo);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("dietRecordVO", dietRecordVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/dietrecord/addDietRecord.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				DietRecordService dietRecordSvc = new DietRecordService();
				dietRecordVO = dietRecordSvc.addDietRecord(member_no, rec_date, eat_period, photo);
//				String url = "/front-end/posture/posture.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url);
//				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.put("error","修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/dietrecord/addDietRecord.jsp");
				failureView.forward(req, res);
			}
		}

		if ("insert2".equals(action)) {

			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			List dietdetailList = new ArrayList();
			HttpSession session = req.getSession();
			try {
				String member_no = req.getParameter("member_no");
				Integer eat_period = new Integer(req.getParameter("eat_period").trim());
				java.sql.Date rec_date = null;	
				try {
					rec_date = java.sql.Date.valueOf(req.getParameter("rec_date").trim());
				} catch (IllegalArgumentException e) {
					rec_date = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.put("rec_date","請輸入日期!");
				}

				byte[] photo = null;
				if (req.getPart("photo") != null) {
					try (InputStream in = req.getPart("photo").getInputStream();
							BufferedInputStream bis = new BufferedInputStream(in);
							ByteArrayOutputStream bos = new ByteArrayOutputStream();) {
						byte[] b = new byte[4 * 1024];
						int len = 0;
						while ((len = bis.read(b)) != -1) {
							bos.write(b, 0, len);
						}
						photo = bos.toByteArray();
					} catch (IOException ioe) {
						errorMsgs.put("photo","圖片上傳失敗");
					}
				}
				DietRecordVO dietRecordVO = new DietRecordVO();
				dietRecordVO.setMember_no(member_no);
				dietRecordVO.setEat_period(eat_period);
				dietRecordVO.setRec_date(rec_date);
				dietRecordVO.setPhoto(photo);

				dietdetailList = (List) session.getAttribute("dietdetailList");
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("dietRecordVO", dietRecordVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/posture/posture.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				DietRecordService dietRecordSvc = new DietRecordService();
				dietRecordSvc.insertWithDetail(dietRecordVO, dietdetailList);
				PostureService postureSvc = new PostureService();
				List<PostureVO> posture = postureSvc.getMember(member_no);
				int i = posture.size();
				int num = -1;
				for(int k = 0; k < i ; k++) {
					if(posture.get(k).getRecord_date() == rec_date) {
						num = k;
					}
				}

				if (i >= 1 && num == -1) {
					
					PostureVO posture_last = posture.get(i - 1);

					Integer bmr = posture_last.getBmr();
					Double myfood = 0.0;
					for (Object obj : dietdetailList) {
						DietDetailVO food = (DietDetailVO) obj;
						myfood = myfood + food.getFood_cal();
					}
					Double remain_cal = bmr - myfood;
					Double weight = posture_last.getWeight();
					Double bodyfat = posture_last.getBodyfat();
					Double bmi = posture_last.getBmi();

					PostureVO postureVO = new PostureVO();
					postureVO.setMember_no(member_no);
					postureVO.setRecord_date(rec_date);
					postureVO.setWeight(weight);
					postureVO.setBodyfat(bodyfat);
					postureVO.setBmr(bmr);
					postureVO.setBmi(bmi);
					postureVO.setRemain_cal(remain_cal);
					postureVO = postureSvc.addPosture(member_no, rec_date, weight, bodyfat, bmr, bmi, remain_cal);
				} else if(num != -1) {
					
					PostureVO posture_this = posture.get(num);
					Integer bmr = posture_this.getBmr();
					Double myfood = 0.0;
					for (Object obj : dietdetailList) {
						DietDetailVO food = (DietDetailVO) obj;
						myfood = myfood + food.getFood_cal();
					}

					Double remain_cal = posture_this.getRemain_cal() - myfood;
					PostureVO postureVO = postureSvc.findOneByDate(member_no,rec_date);
					postureSvc.updateRemaincal(remain_cal,postureVO.getPosture_no());
					
				}

				dietdetailList.clear();
				session.setAttribute("dietdetailList", dietdetailList);

				String url = "/front-end/posture/posture.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.put("error","修改資料失敗:" + e.getMessage());
				dietdetailList.clear();
				session.setAttribute("dietdetailList", dietdetailList);
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/posture/posture.jsp");
				failureView.forward(req, res);
			}
		}
		if ("delete_pic".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				String diet_no = req.getParameter("diet_no");
	
				DietRecordService dietRecordSvc = new DietRecordService();
				dietRecordSvc.delete_pic(diet_no);
				

				JSONObject output = new JSONObject();
				output.put("success", "Y");
				res.setContentType("text/plain");
				res.setCharacterEncoding("UTF-8");
				PrintWriter out = res.getWriter();
				out.write(output.toString());
				out.flush();
				out.close();
				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/dietrecord/update_DietRecord_input.jsp");
				failureView.forward(req, res);
			}
		}
	}

}
