package com.experience.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.experience.model.ExperienceDAO;
import com.experience.model.ExperienceService;
import com.experience.model.ExperienceVO;
import com.foodinform.model.FoodInformService;
import com.util.tool.Upload;


@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class ExperienceServlet extends HttpServlet {
	
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
        
        
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String whichPage = req.getParameter("whichPage");
				String str = req.getParameter("exper_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入exper_no");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/experience/listAllExperience.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				String exper_no = null;
				try {
					exper_no = new String(str);
				} catch (Exception e) {
					errorMsgs.add("員工編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/experience/listAllExperience.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/
				ExperienceService expSvc = new ExperienceService();
				ExperienceVO expVO = expSvc.getOneExperience(exper_no);
				if (expVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/experience/listAllExperience.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("expVO", expVO); // 資料庫取出的empVO物件,存入req
				req.setAttribute("whichPage", whichPage);
				String url = "/front-end/experience/listOneExperience.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/experience/listAllExperience.jsp");
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
				String exper_no = new String(req.getParameter("exper_no"));

				/*************************** 2.開始查詢資料 ****************************************/
				ExperienceService expSvc = new ExperienceService();
				ExperienceVO expVO = expSvc.getOneExperience(exper_no);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("expVO", expVO); // 資料庫取出的empVO物件,存入req
				boolean openModal=true;
				req.setAttribute("openModal",openModal );
				String url = "/front-end/experience/update_experience_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/experience/listAllExperience.jsp");
				failureView.forward(req, res);
			}
		}
		if ("update".equals(action)) { // 來自update_emp_input.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.,,
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				
				String exper_no = new String(req.getParameter("exper_no").trim());

				String member_no = req.getParameter("member_no");
				if (member_no == null || member_no.trim().length() == 0) {
					errorMsgs.add("member_no 請勿空白");
				}

				String cate_no = req.getParameter("cate_no").trim();
				if (cate_no == null || cate_no.trim().length() == 0) {
					errorMsgs.add("cate_no請勿空白");
				}
				String exper_context = req.getParameter("exper_context").trim();
				if (exper_context == null || exper_context.trim().length() == 0) {
					errorMsgs.add("exper_context請勿空白");
				}
				String exper_title = req.getParameter("exper_title").trim();
				if (exper_title == null || exper_title.trim().length() == 0) {
					errorMsgs.add("exper_title請勿空白");
				}

				byte[] picturese = null;
				if (req.getPart("exper_picture").getSize() > 0) {
					picturese = Upload.getPictureByteArray(req.getPart("exper_picture"));
				}else{
					ExperienceService expermSvc = new ExperienceService();
					picturese = (expermSvc.getOneExperience(exper_no).getPicture());
				}
				
				ExperienceVO expVO = new ExperienceVO();
				expVO.setExper_no(exper_no);
				expVO.setMember_no(member_no);
				expVO.setCate_no(cate_no);
				expVO.setExper_context(exper_context);
				expVO.setPicture(picturese);
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("expVO", expVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("front-end/experience/update_experience_input.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				/*************************** 2.開始修改資料 *****************************************/
				ExperienceService expSvc = new ExperienceService();
				expVO = expSvc.updateExperience(exper_no, member_no, cate_no, exper_context, exper_title, picturese);

				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
				req.setAttribute("expVO", expVO); // 資料庫update成功後,正確的的empVO物件,存入req
				String url = "/front-end/experience/listOneExperience.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/experience/update_experience_input.jsp");
				failureView.forward(req, res);
			}
		}
		 if ("insert".equals(action)) { // 來自addEmp.jsp的請求  
				
				List<String> errorMsgs = new LinkedList<String>();
				// Store this set in the request scope, in case we need to
				// send the ErrorPage view.
				req.setAttribute("errorMsgs", errorMsgs);

				try {
					/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
					
					String member_no = req.getParameter("member_no");
					if (member_no == null || member_no.trim().length() == 0) {
						errorMsgs.add("member_no姓名: 請勿空白");
					} 
					String exper_title = req.getParameter("exper_title");
					if (exper_title == null || exper_title.trim().length() == 0) {
						errorMsgs.add("exper_title姓名: 請勿空白");
					} 
				
					String cate_no = req.getParameter("cate_no").trim();
					if (cate_no == null || cate_no.trim().length() == 0) {
						errorMsgs.add("cate_no請勿空白");
					}
					
					String exper_context = req.getParameter("exper_context").trim();
					if (exper_context == null || exper_context.trim().length() == 0) {
						errorMsgs.add("exper_context請勿空白");
					}
					
					Part part = req.getPart("exper_picture");
					InputStream in = part.getInputStream();
					byte[] picturese = new byte[in.available()];
					in.read(picturese);
					in.close();

					ExperienceVO expVO = new ExperienceVO();
					expVO.setCate_no(cate_no);
					expVO.setMember_no(member_no);
					expVO.setExper_title(exper_title);
					expVO.setExper_context(exper_context);
					expVO.setPicture(picturese);
					

					// Send the use back to the form, if there were errors
					if (!errorMsgs.isEmpty()) {
						req.setAttribute("expVO", expVO); // 含有輸入格式錯誤的empVO物件,也存入req
						RequestDispatcher failureView = req
								.getRequestDispatcher("/front-end/experience/addExperience.jsp");
						failureView.forward(req, res);
						return;
					}
					
					/***************************2.開始新增資料***************************************/
					ExperienceService expSvc = new ExperienceService();
					expVO = expSvc.addexperience(member_no, cate_no, exper_context, exper_title, picturese);
					
					/***************************3.新增完成,準備轉交(Send the Success view)***********/
					String url = "/front-end/experience/listAllExperience.jsp";
					RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
					successView.forward(req, res);				
					
					/***************************其他可能的錯誤處理**********************************/
				} catch (Exception e) {
					errorMsgs.add(e.getMessage());
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/experience/addExperience.jsp");
					failureView.forward(req, res);
				}
			}
		 if ("delete".equals(action)) { // 來自listAllEmp.jsp

				List<String> errorMsgs = new LinkedList<String>();
				// Store this set in the request scope, in case we need to
				// send the ErrorPage view.
				req.setAttribute("errorMsgs", errorMsgs);
		
				try {
					/***************************1.接收請求參數***************************************/
					String exper_no = new String(req.getParameter("exper_no"));
					
					/***************************2.開始刪除資料***************************************/
					ExperienceService expSvc = new ExperienceService();
					expSvc.deleteExperience(exper_no);
					
					/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
					String url = "/front-end/experience/listAllExperience.jsp";
					RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
					successView.forward(req, res);
					
					/***************************其他可能的錯誤處理**********************************/
				} catch (Exception e) {
					errorMsgs.add("刪除資料失敗:"+e.getMessage());
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/experience/listAllExperience.jsp");
					failureView.forward(req, res);
				}
			}
		 if ("getOne_Display".equals(action)) { // 來自listAllFoodInform.jsp的請求

				List<String> errorMsgs = new LinkedList<String>();
				// Store this set in the request scope, in case we need to
				// send the ErrorPage view.
				req.setAttribute("errorMsgs", errorMsgs);

				try {
					/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
					String whichPage = req.getParameter("whichPage");
					String str = req.getParameter("exper_no");
					if (str == null || (str.trim()).length() == 0) {
						errorMsgs.add("請輸入心得編號");
					}
					
					String exper_no = null;
					try {
						exper_no = new String(str);
					} catch (Exception e) {
						errorMsgs.add("編號格式不正確");
					}
					
					// Send the use back to the sform, if there were errors
					if (!errorMsgs.isEmpty()) {
						RequestDispatcher failureView = req
								.getRequestDispatcher("/front-end/experience/listAllExperience.jsp");
						failureView.forward(req, res);
						return;//程式中斷
					}
					
					/***************************2.開始查詢資料*****************************************/
					ExperienceService expermSvc = new ExperienceService();
					ExperienceVO expVO = expermSvc.getOneExperience(exper_no);
					if (expVO == null) {
						errorMsgs.add("查無資料");
					}
					// Send the use back to the form, if there were errors
					if (!errorMsgs.isEmpty()) {
						RequestDispatcher failureView = req
								.getRequestDispatcher("/front-end/experience/listAllExperience.jsp");
						failureView.forward(req, res);
						return;//程式中斷
					}
					
					/***************************3.查詢完成,準備轉交(Send the Success view)*************/
					req.setAttribute("expVO", expVO); // 資料庫取出的 foodinformVO 物件,存入req
					req.setAttribute("whichPage", whichPage); // 轉交頁數讓返回使用
					String url = "/front-end/experience/listOneExperiencebyMember.jsp";
					RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneFoodInform.jsp
					successView.forward(req, res);

					/***************************其他可能的錯誤處理*************************************/
				} catch (Exception e) {
					errorMsgs.add("無法取得資料:" + e.getMessage());
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/experience/listAllExperience.jsp");
					failureView.forward(req, res);
				}
			}
	}

// 取出上傳的檔案名稱 (因為API未提供method,所以必須自行撰寫)
	public String getFileNameFromPart(Part part) {
		String header = part.getHeader("content-disposition");
		System.out.println("header=" + header); // 測試用
		String filename = new File(header.substring(header.lastIndexOf("=") + 2, header.length() - 1)).getName();
		System.out.println("filename=" + filename); // 測試用
		if (filename.length() == 0) {
			return null;
		}
		return filename;
	}

}
