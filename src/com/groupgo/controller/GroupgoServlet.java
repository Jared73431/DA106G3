package com.groupgo.controller;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.groupgo.model.*;
import com.groupreport.model.*;
import com.main.*;
import com.mygroup.model.*;
import com.notificationlist.model.*;

@MultipartConfig

public class GroupgoServlet extends HttpServlet {

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
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String groupgo_id = req.getParameter("groupgo_id");				

				/*************************** 2.開始查詢資料 *****************************************/
				GroupgoService groupgoSvc = new GroupgoService();
				GroupgoVO groupgoVO = groupgoSvc.getOneGroup(groupgo_id);
				if (groupgoVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/groupgo/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}
				

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("groupgoVO", groupgoVO); // 資料庫取出的empVO物件,存入req
				String url = "/front-end/groupgo/listOneGroup.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/groupgo/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		

		
		if ("getNear_For_Display".equals(action)) { // 來自select_page.jsp的請求
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				Double lon = 0.0;
				try{
					lon = new Double(req.getParameter("lon"));
						}catch(Exception e) {
							lon = 0.0;
						}
				
				Double lat = 0.0;
				try{
					lat = new Double(req.getParameter("lat"));
				}catch(Exception e) {
					lat = 0.0;
				}
				GroupgoService groupgoSvc = new GroupgoService();
				List<GroupgoVO> list = groupgoSvc.findNear(lon,lat);
				
				if (list.size() <= 0) {
					List<GroupgoVO> listAll = groupgoSvc.getAll();
					for(int i =0; i < listAll.size();i++) {
						if(listAll.get(i).getGroup_status() == 1) {
							list.add(listAll.get(i));
						}
					}
				}

				int random = (int)(Math.random() * list.size());
				GroupgoVO groupgoVO = (GroupgoVO)list.get(random);
				
				JSONObject obj = new JSONObject();
				obj.put("groupgo_id",groupgoVO.getGroupgo_id());
				obj.put("master_id",groupgoVO.getMaster_id());
				obj.put("group_name",groupgoVO.getGroup_name());
				obj.put("group_date",groupgoVO.getGroup_date());
				obj.put("place",groupgoVO.getPlace());
				obj.put("people_num",groupgoVO.getPeople_num());
				obj.put("current_peo",groupgoVO.getCurrent_peo());
				obj.put("budget",groupgoVO.getBudget());
				obj.put("group_content",groupgoVO.getGroup_content());
				obj.put("deadline",groupgoVO.getDeadline());			
				JSONArray output = new JSONArray();
				output.put(obj);
				
				res.setContentType("text/html;charset=UTF-8");
				PrintWriter out = res.getWriter();
				out.write(output.toString());
				out.flush();
				out.close();
			

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/groupgo/listAllGroupgo_v2.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOne_For_Update".equals(action)) { // 來自listAllEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String groupgo_id = req.getParameter("groupgo_id");

				/*************************** 2.開始查詢資料 ****************************************/
				GroupgoService groupSvc = new GroupgoService();
				GroupgoVO groupgoVO = groupSvc.getOneGroup(groupgo_id);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("groupgoVO", groupgoVO); // 資料庫取出的empVO物件,存入req
				String url = "/front-end/groupgo/update_group_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/groupgo/listAllGroup.jsp");
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			GroupgoService groupgoSvc = new GroupgoService();
			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String groupgo_id = req.getParameter("groupgo_id");
				String master_id = req.getParameter("master_id");
				String group_name = req.getParameter("group_name");
				
				java.sql.Timestamp group_date = new java.sql.Timestamp(System.currentTimeMillis());
				try {
					group_date = java.sql.Timestamp.valueOf(req.getParameter("group_date"));
				} catch (NullPointerException e) {
					errorMsgs.add("請輸入活動日期!");
				} catch (IllegalArgumentException e) {
					errorMsgs.add("活動日期請輸入正確格式!");
				}

				String place = req.getParameter("place").trim();
				if (place == null || place.trim().length() == 0) {
					errorMsgs.add("地點請勿空白");
				}
		
				Double lat = null;
				Double lon = null;

				try {
				GetGooglePlace getgoogleplace = new GetGooglePlace();
				double[] latlng = getgoogleplace.GetGooglePlace(place);
				lat = latlng[0];
				lon = latlng[1];
	
				}catch(Exception e){
					errorMsgs.add("地址有誤");	
				}
				
				Integer group_status = new Integer(req.getParameter("group_status"));

				Integer people_num = null;
				try {
					people_num = new Integer(req.getParameter("people_num").trim());
					if(people_num < groupgoSvc.getOneGroup(groupgo_id).getCurrent_peo()) {
						errorMsgs.add("不能小於現在的人數");
					}
				} catch (NumberFormatException e) {
					people_num = 0;
					errorMsgs.add("請填數字");
				}

				String budget = req.getParameter("budget").trim();
				if (budget == null || budget.trim().length() == 0) {
					errorMsgs.add("預算請勿空白");
				}

				String group_content = req.getParameter("group_content");
				if (group_content == null || group_content.trim().length() == 0) {
					errorMsgs.add("內容請勿空白");
				}
				byte[] photo = null;

				
				if(req.getPart("photo") != null) {
					try(InputStream in = req.getPart("photo").getInputStream();
					BufferedInputStream bis = new BufferedInputStream(in);
					ByteArrayOutputStream bos = new ByteArrayOutputStream();) {
						byte[] b = new byte[4 * 1024];
						int len = 0;
						while ((len = bis.read(b)) != -1) {
							bos.write(b, 0, len);
						}
						photo = bos.toByteArray();

					}catch(IOException ioe) {
						errorMsgs.add("圖片上傳失敗");
					}
				}
				java.sql.Timestamp deadline = new java.sql.Timestamp(System.currentTimeMillis());
				try {
					deadline = java.sql.Timestamp.valueOf(req.getParameter("deadline"));
				} catch (NullPointerException e) {
					errorMsgs.add("請輸入截止日期!");
				} catch (IllegalArgumentException e) {
					errorMsgs.add("截止日期請輸入正確格式!");
				}
				GroupgoVO groupgoVO = new GroupgoVO();
				groupgoVO.setGroupgo_id(groupgo_id);
				groupgoVO.setMaster_id(master_id);
				groupgoVO.setGroup_name(group_name);
				groupgoVO.setGroup_date(group_date);
				groupgoVO.setPlace(place);
				groupgoVO.setLon(lon);
				groupgoVO.setLat(lat);
				groupgoVO.setGroup_status(group_status);				
				groupgoVO.setPeople_num(people_num);
				groupgoVO.setBudget(budget);
				groupgoVO.setGroup_content(group_content);
				groupgoVO.setPhoto(photo);
				groupgoVO.setDeadline(deadline);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("groupgoVO", groupgoVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/groupgo/update_group_input.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				/*************************** 2.開始修改資料 *****************************************/
				
				groupgoVO = groupgoSvc.updateGroupgo(groupgo_id, group_name, master_id, group_date, place, lon, lat,
						group_status, people_num, budget, group_content, photo, deadline);

				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
				req.setAttribute("groupgoVO", groupgoVO);
				
				MygroupService mygroupsvc = new MygroupService();
				NotificationListService noteSvc = new NotificationListService();
				List<MyGroupVO> groupmember = mygroupsvc.getGroupAttend(groupgo_id);
				
				if(group_status == 2) {
					for(MyGroupVO groupMember:groupmember) {
						MyGroupVO mygroupVO = new MyGroupVO();
						mygroupVO.setGroupgo_id(groupgo_id);
						mygroupVO.setMember_no(groupMember.getMember_no());
						mygroupVO.setMygroup_status(2);
						mygroupsvc.updateStatus(mygroupVO);
						noteSvc.addNotification(groupMember.getMember_no(), "CA0002", group_name+"揪團成立了!");
					}
				}else {
						for(MyGroupVO groupMember:groupmember) {
							noteSvc.addNotification(groupMember.getMember_no(), "CA0002", group_name+"團主做了更改喔!");
						}
				}
					
					
					
					
//				}else if(group_status == 3) {
//					MygroupService mygroupsvc = new MygroupService();
//					List<MyGroupVO> groupmember = mygroupsvc.getGroupAttend(groupgo_id);
//					for(MyGroupVO groupMember:groupmember) {
//						MyGroupVO mygroupVO = new MyGroupVO();
//						mygroupVO.setGroupgo_id(groupgo_id);
//						mygroupVO.setMember_no(groupMember.getMember_no());
//						mygroupVO.setMygroup_status(3);
//						mygroupsvc.updateStatus(mygroupVO);
//						mygroupVO.setScore(10);
//						mygroupsvc.updateScore(mygroupVO);
//						
//						GroupgoService groupgosvc = new GroupgoService();
//						Integer score_no = groupgosvc.getScoreNo(groupgo_id);
//						Integer score_sum = groupgosvc.getScoreSum(groupgo_id);
//						groupgosvc.updateScoreSum(score_sum,groupgo_id);
//					}
//				}else if(group_status == 4) {
//					MygroupService mygroupsvc = new MygroupService();
//					List<MyGroupVO> groupmember = mygroupsvc.getGroupAttend(groupgo_id);
//					for(MyGroupVO groupMember:groupmember) {
//						MyGroupVO mygroupVO = new MyGroupVO();
//						mygroupVO.setGroupgo_id(groupgo_id);
//						mygroupVO.setMember_no(groupMember.getMember_no());
//						mygroupVO.setMygroup_status(4);
//						mygroupsvc.updateStatus(mygroupVO);
//					}
					
				
				
				String url = "/front-end/groupgo/listOneGroup.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/groupgo/update_group_input.jsp");
				failureView.forward(req, res);
			}
		}

		if ("update_status_report".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				String groupgo_id = req.getParameter("groupgo_id");				
				Integer group_status = new Integer(req.getParameter("group_status"));
				Integer report_status = new Integer(req.getParameter("report_status"));

				GroupgoService groupgoSvc = new GroupgoService();
				groupgoSvc.updateStatus(group_status, groupgo_id);
				
				GroupReportService groupreportsvc = new GroupReportService();
				groupreportsvc.updateStatus(groupgo_id, report_status);
				

				MygroupService mygroupsvc = new MygroupService();
				NotificationListService noteSvc = new NotificationListService();
				List<MyGroupVO> groupmember = mygroupsvc.getGroupAttend(groupgo_id);
				
				NotificationListVO noteVO = noteSvc.addNotification(groupgoSvc.getOneGroup(groupgo_id).getMaster_id(), "CA0002", groupgoSvc.getOneGroup(groupgo_id).getGroup_name()+"被檢舉下架囉QQ");
					
				for(MyGroupVO groupMember:groupmember) {
						MyGroupVO mygroupVO = new MyGroupVO();
						mygroupVO.setGroupgo_id(groupgo_id);
						mygroupVO.setMember_no(groupMember.getMember_no());
						mygroupVO.setMygroup_status(5);
						mygroupsvc.updateStatus(mygroupVO);
						NotificationListVO noteVO2 =  noteSvc.addNotification(groupMember.getMember_no(), "CA0002", groupgoSvc.getOneGroup(groupgo_id).getGroup_name()+"被檢舉下架囉QQ");
					}
				
				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/

				String url = "/back-end/groupreport/listAllGroupReport.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("更改失敗!");
				RequestDispatcher failureView = req.getRequestDispatcher("//back-end/groupreport/listAllGroupReport.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("insert".equals(action)) { // 來自addEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				String master_id = req.getParameter("master_id");

				String group_name = req.getParameter("group_name");
				if (group_name == null || group_name.trim().length() == 0) {
					errorMsgs.add("揪團名稱: 請勿空白");
				}
				java.sql.Timestamp group_date = new java.sql.Timestamp(System.currentTimeMillis());
				try {
					group_date = java.sql.Timestamp.valueOf(req.getParameter("group_date"));
				} catch (NullPointerException e) {
					errorMsgs.add("請輸入活動日期!");
				} catch (IllegalArgumentException e) {
					errorMsgs.add("活動日期請輸入正確格式!");
				}

				String place = req.getParameter("place").trim();
				if (place == null || place.trim().length() == 0) {
					errorMsgs.add("地點請勿空白");
				}
			
				GetGooglePlace getGooglePlace = new GetGooglePlace();
				double[] latlng = getGooglePlace.GetGooglePlace(place);

				double lat = latlng[0];
				double lon = latlng[1];
		
				

				// Integer group_status = new Integer(req.getParameter("group_status"));

				Integer people_num;
				people_num = new Integer(req.getParameter("people_num"));
				if (group_name == null || group_name.trim().length() == 0) {
					errorMsgs.add("請填人數");
				}
				String budget = null;
				try {
					budget = req.getParameter("budget").trim();
				} catch (NumberFormatException e) {
					budget = "";
					errorMsgs.add("請填預算");
				}
				String group_content = req.getParameter("group_content");
				if (group_content == null || group_content.trim().length() == 0) {
					errorMsgs.add("內容請勿空白");
				}

				
				byte[] photo = null;
				if(req.getPart("photo")!= null) {
					try(InputStream in = req.getPart("photo").getInputStream();
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
				

				java.sql.Timestamp deadline = new java.sql.Timestamp(System.currentTimeMillis());
				try {
					deadline = java.sql.Timestamp.valueOf(req.getParameter("deadline"));
				} catch (NullPointerException e) {
					errorMsgs.add("請輸入截止日期!");
				} catch (IllegalArgumentException e) {
					errorMsgs.add("截止日期請輸入正確格式!");
				}
				GroupgoVO groupgoVO = new GroupgoVO();
				groupgoVO.setMaster_id(master_id);
				groupgoVO.setGroup_name(group_name);
				groupgoVO.setGroup_date(group_date);
				groupgoVO.setPlace(place);
				groupgoVO.setLon(lon);
				groupgoVO.setLat(lat);
				groupgoVO.setPeople_num(people_num);
				groupgoVO.setBudget(budget);
				groupgoVO.setGroup_content(group_content);
				groupgoVO.setPhoto(photo);
				groupgoVO.setDeadline(deadline);
				req.setAttribute("groupgoVO", groupgoVO);
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("groupgoVO", groupgoVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/groupgo/addGroupgo.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				GroupgoService groupgoSvc = new GroupgoService();
				groupgoVO = groupgoSvc.addGroupgo(master_id, group_name, group_date, place, lon, lat, people_num,
						budget, group_content, photo, deadline);
				
				
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/front-end/groupgo/listAllGroupgo.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/groupgo/addGroupgo.jsp");
				failureView.forward(req, res);
			}
		}

		if ("delete".equals(action)) { // 來自listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ***************************************/
				String groupgo_id = req.getParameter("groupgo_id");

				/*************************** 2.開始刪除資料 ***************************************/
				GroupgoService groupgoSvc = new GroupgoService();
				groupgoSvc.deleteGroupgo(groupgo_id);

				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				String url = "/front-end/groupgo/listAllGroupgo.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/groupgo/listAllGroupgo.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("update_status".equals(action)) {
			
			JSONObject output = new JSONObject();
			try {
				String groupgo_id = req.getParameter("groupgo_id");				
				Integer group_status = new Integer(req.getParameter("group_status"));

				
				GroupgoService groupgoSvc = new GroupgoService();
				groupgoSvc.updateStatus(group_status, groupgo_id);
				GroupgoVO groupgoVO = groupgoSvc.getOneGroup(groupgo_id);
				
				MygroupService mygroupsvc = new MygroupService();
				List<MyGroupVO> groupmember = mygroupsvc.getGroupAttend(groupgo_id);
				for(MyGroupVO groupMember:groupmember) {
					MyGroupVO mygroupVO = new MyGroupVO();
					mygroupVO.setGroupgo_id(groupgo_id);
					mygroupVO.setMember_no(groupMember.getMember_no());
					mygroupVO.setMygroup_status(5);
					mygroupsvc.updateStatus(mygroupVO);
					NotificationListService noteSvc = new NotificationListService();
					NotificationListVO noteVO = noteSvc.addNotification(groupMember.getMember_no(), "CA0002", groupgoVO.getGroup_name()+"已取消囉QQ");
				}
				
				

				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
				output.put("success", "Y");
				res.setContentType("text/html;charset=UTF-8");
				PrintWriter out = res.getWriter();
				out.write(output.toString());
				out.flush();
				out.close();
				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/groupreport/select_page.jsp");
				failureView.forward(req, res);
			}
		}

	}

}