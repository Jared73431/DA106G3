package com.shopping.controller;
import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.product.model.*;
import com.shopping.model.*;

public class ShoppingServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();

		
		@SuppressWarnings("unchecked")
		List<ShoppingVO> buylist = (Vector<ShoppingVO>) session.getAttribute("shoppingcart");
		String action = req.getParameter("action");

		// 刪除購物車中的商品
		if ("DELETE".equals(action)) {
			String del = req.getParameter("del");
			int d = Integer.parseInt(del);
			buylist.remove(d);

			// 計算目前總價
			Integer total = 0;
			if (buylist!=null) {
			for (int i = 0; i < buylist.size(); i++) {
				ShoppingVO order = buylist.get(i);
				Integer price = order.getPro_price();
				Integer quantity = order.getQuantity();
				total += (price * quantity);
				}
			}
			String total_price = String.valueOf(total);

			/*************************** 資料轉交 ************/
			session.setAttribute("shop_price", total_price);
			session.setAttribute("shoppingcart", buylist);
			String url = "/front-end/shopping/listAllShopping.jsp";
			RequestDispatcher rd = req.getRequestDispatcher(url);
			rd.forward(req, res);
		}

		// 新增商品至購物車中
		if ("ADD".equals(action)) {
			try {
				// 取得後來新增的商品
				ShoppingService shopSvc = new ShoppingService();
				ShoppingVO abuy = shopSvc.getShopping(req);
				if (buylist == null) {
					buylist = new Vector<ShoppingVO>();
					buylist.add(abuy);
				} else {
					if (buylist.contains(abuy)) {
						ShoppingVO innerBuy = buylist.get(buylist.indexOf(abuy));
						innerBuy.setQuantity(innerBuy.getQuantity() + abuy.getQuantity());
					} else {
						buylist.add(abuy);
					}
				}
				/*************************** 資料轉交 ************/

				session.setAttribute("shoppingcart", buylist);
				String url = "/front-end/shopping/listAllShopping.jsp";
				RequestDispatcher rd = req.getRequestDispatcher(url);
				rd.forward(req, res);
			} catch (Exception e) {
				System.out.println("無法取得資料:" + e.getMessage());
			}
		}
		// AJAX刪除購物車中的商品
		if ("DELETE_AJAX".equals(action)) {
			String del = req.getParameter("del");
			for(int i =0; i< buylist.size();i++) {
				ShoppingVO innerBuy = buylist.get(i);
				if (innerBuy.getPro_no().contentEquals(del)) {
					buylist.remove(i);
				}
			}
			
			// 計算目前總價
			Integer total = 0;
			if (buylist!=null) {
			for (int i = 0; i < buylist.size(); i++) {
				ShoppingVO order = buylist.get(i);
				Integer price = order.getPro_price();
				Integer quantity = order.getQuantity();
				total += (price * quantity);
				}
			}
			String total_price = String.valueOf(total);

			/*************************** 資料轉交 ************/

			session.setAttribute("shoppingcart", buylist);
			session.setAttribute("shop_price", total_price);			
			PrintWriter out = res.getWriter();
			out.print(total_price);
			out.close();

		}

		// AJAX新增商品至購物車中
		if ("ADD_AJAX".equals(action)) {
			try {
				// 取得後來新增的商品
				ShoppingService shopSvc = new ShoppingService();
				ShoppingVO abuy = shopSvc.getShopping(req);
				if (buylist == null) {
					buylist = new Vector<ShoppingVO>();
					buylist.add(abuy);
				} else {
					if (buylist.contains(abuy)) {
						ShoppingVO innerBuy = buylist.get(buylist.indexOf(abuy));
						innerBuy.setQuantity(innerBuy.getQuantity() + abuy.getQuantity());
					} else {
						buylist.add(abuy);
					}
				}
				/***************************2.資料轉交 ************/
				session.setAttribute("shoppingcart", buylist);
				/***************************3.AJAX不跳頁,印出字串提示(Send the Success view)***********/								
				Integer total = 0;
				for (int i = 0; i < buylist.size(); i++) {
					ShoppingVO order = buylist.get(i);
					Integer price = order.getPro_price();
					Integer quantity = order.getQuantity();
					total += (price * quantity);
				}			
				String total_price = String.valueOf(total);
				session.setAttribute("shop_price", total_price);
				// 回傳目前購物總價
				res.setContentType("text/html; charset=UTF-8");
				PrintWriter out = res.getWriter();
				out.print(total_price);
				out.close();
				/***************************其他可能的錯誤處理**********************************/
	
			} catch (Exception e) {
				System.out.println("無法取得資料:" + e.getMessage());
			}
		}
		
		// 瀏覽商品
		if ("getCate_Shop".equals(action)) { // front-end/shopping的jsp請求
			
			try {
				/***************************1.接收請求參數*****************************************/
				String prc_no = req.getParameter("prc_no");
				if (prc_no == null || (prc_no.trim()).length() == 0) {
					System.out.println("確認產品編號");
				}
			
				/***************************2.開始查詢資料*****************************************/
				// 直接塞關鍵字進session讓網頁能持續運作
				session.setAttribute("check_prc", prc_no);
				
				/***************************3.轉交(Send the Success view)*************************/
				String url = "/front-end/shopping/listCateShopping.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listCateShopping.jsp
				successView.forward(req, res);
				/***************************其他可能的錯誤處理*************************************/
				} catch (Exception e) {
				System.out.println("無法取得資料:" + e.getMessage());
			}
		}
		
		// 瀏覽購物車
		if ("getCart_Display".equals(action)) { // front-end/shopping的jsp請求
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數**********************/
				// Send the use back to the sform, if there were errors
				String whichPage = req.getParameter("whichPage");
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/shopping/listAllShopping.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				String prc_no = req.getParameter("prc_no");
				/***************************2.開始查詢資料*****************************************/
				String url = null;
				if (prc_no != null) {
					ProductService productSvc = new ProductService();
					List<ProductVO> productList = productSvc.getShop(prc_no);
					if(productList == null) {
						errorMsgs.add("查無資料");
					}
					// Send the use back to the form, if there were errors
					if (!errorMsgs.isEmpty()) {
						RequestDispatcher failureView = req
								.getRequestDispatcher("/front-end/shopping/listAllShopping.jsp");
						failureView.forward(req, res);
						return;//程式中斷
					}
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("productList", productList); // 資料庫取出的 productVO 物件,存入req
				req.setAttribute("whichPage", whichPage);
					url = "/front-end/shopping/listCateShopping.jsp?whichPage="+whichPage;
				}else {
					url = "/front-end/shopping/listAllShopping.jsp?whichPage="+whichPage;
				}
				//Bootstrap_modal
				boolean openModal2=true;
				req.setAttribute("openModal2",openModal2 );				
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listAllShopping.jsp or listCateShopping.jsp
				successView.forward(req, res);
				/***************************其他可能的錯誤處理*************************************/
				} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/shopping/listAllShopping.jsp");
				failureView.forward(req, res);
			}
		}
		// 瀏覽商品詳情 (購物車)
		if ("getOne_Cart_Display".equals(action)) { // front-end/shopping的jsp請求
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數**********************/
				String pro_no = req.getParameter("pro_no");
				if (pro_no == null || (pro_no.trim()).length() == 0) {
					errorMsgs.add("確認產品編號");
				}
				// Send the use back to the sform, if there were errors
				String whichPage = req.getParameter("whichPage");
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/shopping/listAllShopping.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}

				/***************************2.開始查詢資料*****************************************/
				ProductService productSvc = new ProductService();
				ProductVO productVO = productSvc.getOneProduct(pro_no);
				if(productVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/shopping/listAllShopping.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}

				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("productVO", productVO); // 資料庫取出的 productVO 物件,存入req
				req.setAttribute("whichPage", whichPage);
				//Bootstrap_modal
				boolean openModal=true;
				req.setAttribute("openModal",openModal );				

				String url = "/front-end/shopping/listAllShopping.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneShopping.jsp
				successView.forward(req, res);
				/***************************其他可能的錯誤處理*************************************/
				} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/shopping/listAllShopping.jsp");
				failureView.forward(req, res);
			}
		}

			
		// 瀏覽商品詳情 List使用
		if ("getOne_For_Display".equals(action)) { // front-end/shopping的jsp請求
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數**********************/
				String pro_no = req.getParameter("pro_no");
				if (pro_no == null || (pro_no.trim()).length() == 0) {
					errorMsgs.add("確認產品編號");
				}
				// Send the use back to the sform, if there were errors
				String whichPage = req.getParameter("whichPage");
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/shopping/listAllShopping.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				String prc_no = req.getParameter("prc_no");
				/***************************2.開始查詢資料*****************************************/
				String url = null;
				if (prc_no != null) {
					ProductService productSvc = new ProductService();
					ProductVO productVO = productSvc.getOneProduct(pro_no);
					if(productVO == null) {
						errorMsgs.add("查無資料");
					}
					// Send the use back to the form, if there were errors
					if (!errorMsgs.isEmpty()) {
						RequestDispatcher failureView = req
								.getRequestDispatcher("/front-end/shopping/listAllShopping.jsp");
						failureView.forward(req, res);
						return;//程式中斷
					}
				/***************************3.查詢完成,準備轉交(Send the Success view)************(分類版本)*/
		
					req.setAttribute("productVO", productVO);
					req.setAttribute("whichPage", whichPage);
					url = "/front-end/shopping/listCateShopping.jsp?whichPage="+whichPage;
					}else {
					ProductService productSvc = new ProductService();
					ProductVO productVO = productSvc.getOneProduct(pro_no);
					if(productVO == null) {
						errorMsgs.add("查無資料");
					}
					// Send the use back to the form, if there were errors
					if (!errorMsgs.isEmpty()) {
						RequestDispatcher failureView = req
								.getRequestDispatcher("/front-end/shopping/listAllShopping.jsp");
						failureView.forward(req, res);
						return;//程式中斷
					}
					// 計算價格
					Integer total = 0;
					if(buylist!=null) {
						for (int i = 0; i < buylist.size(); i++) {
							ShoppingVO order = buylist.get(i);
							Integer price = order.getPro_price();
							Integer quantity = order.getQuantity();
							total += (price * quantity);
						}
					}
					String total_price = String.valueOf(total);
					session.setAttribute("shop_price", total_price);

					req.setAttribute("productVO", productVO); // 資料庫取出的 productVO 物件,存入req
					req.setAttribute("whichPage", whichPage);
					url = "/front-end/shopping/listAllShopping.jsp?whichPage="+whichPage;
				}
				/***************************3.查詢完成,準備轉交(Send the Success view)************(正常版本)*/
				//Bootstrap_modal
				boolean openModal=true;
				req.setAttribute("openModal",openModal );				
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneShopping.jsp
				successView.forward(req, res);
				/***************************其他可能的錯誤處理*************************************/
				} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/shopping/listAllShopping.jsp");
				failureView.forward(req, res);
			}
		}

		// 瀏覽商品詳情 Index使用
		if ("getOne_Index_Display".equals(action)) { // front-end/homepage的jsp請求
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數**********************/
				String pro_no = req.getParameter("pro_no");
				if (pro_no == null || (pro_no.trim()).length() == 0) {
					errorMsgs.add("確認產品編號");
				}

				/***************************2.開始查詢資料*****************************************/
				ProductService productSvc = new ProductService();
				ProductVO productVO = productSvc.getOneProduct(pro_no);
				if(productVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/shopping/listAllShopping.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}

				/***************************3.查詢完成,準備轉交(Send the Success view)************(分類版本)*/
			
					// 計算價格
					Integer total = 0;
					if(buylist!=null) {
						for (int i = 0; i < buylist.size(); i++) {
							ShoppingVO order = buylist.get(i);
							Integer price = order.getPro_price();
							Integer quantity = order.getQuantity();
							total += (price * quantity);
						}
					}
					String total_price = String.valueOf(total);
					session.setAttribute("shop_price", total_price);

					req.setAttribute("productVO", productVO); // 資料庫取出的 productVO 物件,存入req
					String url = "/front-end/shopping/listAllShopping.jsp";
			
				/***************************3.查詢完成,準備轉交(Send the Success view)************(正常版本)*/
				//Bootstrap_modal
				boolean openModal=true;
				req.setAttribute("openModal",openModal );				
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneShopping.jsp
				successView.forward(req, res);
				/***************************其他可能的錯誤處理*************************************/
				} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/shopping/listAllShopping.jsp");
				failureView.forward(req, res);
			}
		}

		// 結帳，計算購物車商品價錢總數
		if ("CHECKOUT".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
			/****確認參數是否正確****/
			if (buylist.size()==0) {
				errorMsgs.add("請確認購物車是否加入商品");
			}
			
			if(!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/shopping/listAllShopping.jsp");
				failureView.forward(req, res);
				return;//程式中斷				
			}
			
			/****開始查詢購物車資料	****/
			Integer total = 0;
			if(buylist.size()>0) {
				for (int i = 0; i < buylist.size(); i++) {
					ShoppingVO order = buylist.get(i);
					Integer price = order.getPro_price();
					Integer quantity = order.getQuantity();
					total += (price * quantity);
				}
			}	
			String total_price = String.valueOf(total);
			session.setAttribute("shop_price", total_price);
			String url = "/front-end/shopping/Checkout.jsp";
			RequestDispatcher rd = req.getRequestDispatcher(url);
			rd.forward(req, res);
				/****其他可能的錯誤處理****/
			} catch (Exception e) {
			errorMsgs.add("無法取得資料:" + e.getMessage());
			RequestDispatcher failureView = req
					.getRequestDispatcher("/front-end/shopping/listAllShopping.jsp");
			failureView.forward(req, res);
			}			
		}
	}
}	

