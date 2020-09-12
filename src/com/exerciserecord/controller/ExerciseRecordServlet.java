package com.exerciserecord.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.dietdetail.model.DietDetailVO;
import com.exerciseitem.model.ExerciseItemService;
import com.exerciseitem.model.ExerciseItemVO;
import com.exerciserecord.model.ExerciseRecordService;
import com.exerciserecord.model.ExerciseRecordVO;
import com.foodnutrition.model.FoodNutritionService;
import com.foodnutrition.model.FoodNutritionVO;
import com.members.model.MembersService;
import com.members.model.MembersVO;
import com.posture.model.PostureService;
import com.posture.model.PostureVO;

public class ExerciseRecordServlet extends HttpServlet {
int count = 0;
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req,res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		

		if ("getOne_For_Display".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String str = req.getParameter("exerec_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入紀錄編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/exerciserecord/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				String exerec_no = "";
				try {
					exerec_no = str;
				} catch (Exception e) {
					errorMsgs.add("揪團編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/exerciserecord/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/
				ExerciseRecordService exerciseRecordSvc = new ExerciseRecordService();
				ExerciseRecordVO exerciseRecordVO = exerciseRecordSvc.getOneExerciseRecord(exerec_no);
				if (exerciseRecordVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/exerciserecord/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("exerciseRecordVO", exerciseRecordVO); // 資料庫取出的empVO物件,存入req
				String url = "/front-end/exerciserecord/listOneExe.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/exerciserecord/select_page.jsp");
				failureView.forward(req, res);
			}
		}
			if ("getOne_For_Update".equals(action)) { // 來自listAllEmp.jsp的請求

				List<String> errorMsgs = new LinkedList<String>();
				req.setAttribute("errorMsgs", errorMsgs);

				try {
					/*************************** 1.接收請求參數 ****************************************/
					String exerec_no = req.getParameter("exerec_no");

					/*************************** 2.開始查詢資料 ****************************************/
					ExerciseRecordService exerciseRecordSvc = new ExerciseRecordService();
					ExerciseRecordVO exerciseRecordVO = exerciseRecordSvc.getOneExerciseRecord(exerec_no);

					/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
					req.setAttribute("exerciseRecordVO", exerciseRecordVO); // 資料庫取出的empVO物件,存入req
					String url = "/front-end/exerciserecord/update_exe_input.jsp";
					RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
					successView.forward(req, res);

					/*************************** 其他可能的錯誤處理 **********************************/
				} catch (Exception e) {
					errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/exerciserecord/listAllExe.jsp");
					failureView.forward(req, res);
				}
			}
			if ("update".equals(action)) {

				List<String> errorMsgs = new LinkedList<String>();
				req.setAttribute("errorMsgs", errorMsgs);
				try {
					/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
					String exerec_no = req.getParameter("exerec_no");
					String member_no = req.getParameter("member_no");
					String exe_no = req.getParameter("exe_no");
					
					Double exe_time = null;
							try {		
					 exe_time = new Double(req.getParameter("exe_time"));
							} catch (NumberFormatException e) {
								exe_time = 0.0;
								errorMsgs.add("請填數字");
							}
					
					java.sql.Date exe_date = null;
					try {
						exe_date = java.sql.Date.valueOf(req.getParameter("exe_date").trim());
					} catch (IllegalArgumentException e) {
						exe_date = new java.sql.Date(System.currentTimeMillis());
						errorMsgs.add("請輸入日期!");
					}

					Double exe_tcal = null;
					try {		
						exe_tcal = new Double(req.getParameter("exe_tcal"));
					} catch (NumberFormatException e) {
						exe_tcal = 0.0;
						errorMsgs.add("請填數字");
					}

					ExerciseRecordVO exerciseRecordVO = new ExerciseRecordVO();
					exerciseRecordVO.setExerec_no(exerec_no);
					exerciseRecordVO.setMember_no(member_no);
					exerciseRecordVO.setExe_no(exe_no);
					exerciseRecordVO.setExe_date(exe_date);
					exerciseRecordVO.setExe_time(exe_time);
					exerciseRecordVO.setExe_tcal(exe_tcal);
					// Send the use back to the form, if there were errors
					if (!errorMsgs.isEmpty()) {
						req.setAttribute("exerciseRecordVO", exerciseRecordVO); // 含有輸入格式錯誤的empVO物件,也存入req
						RequestDispatcher failureView = req.getRequestDispatcher("/front-end/exerciserecord/update_exe_input.jsp");
						failureView.forward(req, res);
						return; // 程式中斷
					}

					/*************************** 2.開始修改資料 *****************************************/
					ExerciseRecordService exerciseRecordSvc = new ExerciseRecordService();
					exerciseRecordVO = exerciseRecordSvc.updateExerciseRecord(exerec_no,member_no,exe_no,exe_date,exe_time,exe_tcal);

					/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
					req.setAttribute("exerciseRecordVO", exerciseRecordVO); // 資料庫update成功後,正確的的empVO物件,存入req
					String url = "/front-end/posture/posture.jsp";
					RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
					successView.forward(req, res);

					/*************************** 其他可能的錯誤處理 *************************************/
				} catch (Exception e) {
					errorMsgs.add("修改資料失敗:" + e.getMessage());
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/exerciserecord/update_exe_input.jsp");
					failureView.forward(req, res);
				}
			}
			if ("insert".equals(action)) {
				Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
				req.setAttribute("errorMsgs", errorMsgs);
				try {
					String member_no = req.getParameter("member_no");
					String exe_no = req.getParameter("exe_no");
					Double exe_time = null;
							try {		
					 exe_time = new Double(req.getParameter("exe_time"));
							} catch (NumberFormatException e) {
								exe_time = 0.0;
								errorMsgs.put("exe_time","時間請填數字");
							}
					
					java.sql.Date exe_date = null;
					try {
						exe_date = java.sql.Date.valueOf(req.getParameter("exe_date").trim());
					} catch (IllegalArgumentException e) {
						exe_date = new java.sql.Date(System.currentTimeMillis());
						errorMsgs.put("exe_date","請輸入日期!");
					}

					Double exe_tcal = null;
					try {		
						exe_tcal = new Double(req.getParameter("exe_tcal"));
					} catch (NumberFormatException e) {
						exe_tcal = 0.0;
						errorMsgs.put("exe_tcal","消耗熱量請填數字");
					}
					ExerciseRecordVO exerciseRecordVO = new ExerciseRecordVO();
					exerciseRecordVO.setMember_no(member_no);
					exerciseRecordVO.setExe_no(exe_no);
					exerciseRecordVO.setExe_date(exe_date);
					exerciseRecordVO.setExe_time(exe_time);
					exerciseRecordVO.setExe_tcal(exe_tcal);
//					if (!errorMsgs.isEmpty()) {
////						req.setAttribute("exerciseRecordVO", exerciseRecordVO);
//						RequestDispatcher failureView = req.getRequestDispatcher("/front-end/exerciserecord/addExerciseRecord.jsp");
//						failureView.forward(req, res);
//						return; // 程式中斷
//					}

					ExerciseRecordService exerciseRecordSvc = new ExerciseRecordService();
					exerciseRecordVO = exerciseRecordSvc.addExerciseRecord(member_no,exe_no,exe_date,exe_time,exe_tcal);
					PostureService postureSvc = new PostureService();
					List<PostureVO> posture = postureSvc.getMember(member_no);
			
					int i = posture.size();
					int num = -1;
					for(int k = 0; k < i ; k++) {
						if(posture.get(k).getRecord_date() == exe_date) {
							num = k;
							
						}
					}
					if (i >= 1 && num == -1) {
						PostureVO posture_last = posture.get(i - 1);

						Integer bmr = posture_last.getBmr();
						Double remain_cal = 0.0 + exe_tcal + bmr;
						Double weight = posture_last.getWeight();
						Double bodyfat = posture_last.getBodyfat();
						Double bmi = posture_last.getBmi();

						PostureVO postureVO = new PostureVO();
						postureVO.setMember_no(member_no);
						postureVO.setRecord_date(exe_date);
						postureVO.setWeight(weight);
						postureVO.setBodyfat(bodyfat);
						postureVO.setBmr(bmr);
						postureVO.setBmi(bmi);
						postureVO.setRemain_cal(remain_cal);
						postureVO = postureSvc.addPosture(member_no, exe_date, weight, bodyfat, bmr, bmi, remain_cal);
					} else if(num != -1) {
						PostureVO posture_this = posture.get(num);
						Integer bmr = posture_this.getBmr();
						Double remain_cal = 0.0 + exe_tcal + bmr;
						PostureVO postureVO = postureSvc.findOneByDate(member_no,exe_date);
						postureSvc.updateRemaincal(remain_cal,postureVO.getPosture_no());
						
					}
					JSONObject output = new JSONObject();
					output.put("success", "Y");
					
					res.setContentType("text/html;charset=UTF-8");
					PrintWriter out = res.getWriter();
					out.write(output.toString());
					out.flush();
					out.close();
					
//					String url = "/front-end/posture/posture.jsp";
//					RequestDispatcher successView = req.getRequestDispatcher(url);
//					successView.forward(req, res);

				} catch (Exception e) {
					errorMsgs.put("sorry","修改資料失敗:" + e.getMessage());
//					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/exerciserecord/addExerciseRecord.jsp");
//					failureView.forward(req, res);
					
					JSONObject output = new JSONObject();
					try {
						output.put("errorMsgs", errorMsgs);
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					res.setContentType("text/html;charset=UTF-8");
					PrintWriter out = res.getWriter();
					out.write(output.toString());
					out.flush();
					out.close();
					
				}
			}
			if ("delete".equals(action)) { 

				List<String> errorMsgs = new LinkedList<String>();
				req.setAttribute("errorMsgs", errorMsgs);

				try {
					String exerec_no = req.getParameter("exerec_no");
					ExerciseRecordService exerciseRecordSvc = new ExerciseRecordService();
					exerciseRecordSvc.deleteExerciseRecord(exerec_no);
					
				
				} catch (Exception e) {
					errorMsgs.add("刪除資料失敗:" + e.getMessage());
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/posture/posture.jsp");
					failureView.forward(req, res);
				}
			}

			
			if ("getCal".equals(action)) { // 來自select_page.jsp的請求
				JSONObject obj = new JSONObject();
				try {
				String member_no = req.getParameter("member_no");
				
				PostureService postureSvc = new PostureService();
				List<PostureVO> posture = postureSvc.getMember(member_no);
				Double weight = 0.0;
				if(posture.size() >= 1) {
				int i = posture.size();
				weight = posture.get(i-1).getWeight();
				}
				Double exe_time = new Double(req.getParameter("exe_time"));
				
				String exe_no = req.getParameter("exe_no");
				ExerciseItemService exerciseItemSvc = new ExerciseItemService();
				ExerciseItemVO exerciseItemVO = exerciseItemSvc.getOneExe(exe_no);
				Double ref_cal = exerciseItemVO.getExe_cal();
				
				Double cal = weight * (exe_time/30)*ref_cal;
				DecimalFormat df = new DecimalFormat("##.00");
				cal = Double.parseDouble(df.format(cal));
		
				try {
					obj.put("cal", cal);
				} catch (JSONException e) {

				}
				}catch(Exception e) {
					
				}
				
				res.setContentType("text/html;charset=UTF-8");
				PrintWriter out = res.getWriter();
				out.write(obj.toString());
				out.flush();
				out.close();

			}
					
		}
	}


