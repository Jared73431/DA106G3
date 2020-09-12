package com.main;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class MyMap extends HttpServlet {
	public void init() throws ServletException{
		ServletContext context = getServletContext();
		
		//翠珍
		//揪團
		Map<Integer,String> groupgoStatus = new LinkedHashMap<Integer,String>();
		groupgoStatus.put(1, "揪團中");
		groupgoStatus.put(2, "已成團");
		groupgoStatus.put(3, "已結束");
		groupgoStatus.put(4, "流團");
		groupgoStatus.put(5, "已取消");
		context.setAttribute("groupgoStatus", groupgoStatus);
		
		//我的揪團
		Map<Integer,String> myGroupStatus = new LinkedHashMap<Integer,String>();
		myGroupStatus.put(1, "審核中");
		myGroupStatus.put(2, "即將開始");
		myGroupStatus.put(3, "待評分");
		myGroupStatus.put(4, "自行取消");
		myGroupStatus.put(5, "被取消了");
		context.setAttribute("myGroupStatus", myGroupStatus);
		
		//飲食時段
		Map<Integer,String> period = new LinkedHashMap<Integer,String>();
		period.put(0,"早餐");
		period.put(1,"午餐");
		period.put(2,"晚餐");
		period.put(3,"其他");
		context.setAttribute("period",period);
		
		Map<Integer,String> reportStatus = new LinkedHashMap<Integer,String>();
		reportStatus.put(1, "未處理");
		reportStatus.put(2, "審核通過");
		reportStatus.put(3, "已下架");
		context.setAttribute("reportStatus",reportStatus);
		
		//建興
		//食訊		
		Map<String, String> fi_status = new LinkedHashMap<String, String>();
		fi_status.put("1", "上架");
		fi_status.put("2", "下架");
		context.setAttribute("fi_status", fi_status);
		
		//新聞 
		Map<String, String> news_status = new LinkedHashMap<String, String>();
		news_status.put("1", "上架");
		news_status.put("2", "下架");
		context.setAttribute("news_status", news_status);
		
		//訂單
		Map<String, String> order_status = new LinkedHashMap<String, String>();
		order_status.put("1", "成立");
		order_status.put("2", "出貨");
		context.setAttribute("order_status", order_status);
		
		//產品
		Map<String, String> pro_status = new LinkedHashMap<String, String>();
		pro_status.put("1", "上架");
		pro_status.put("2", "下架");
		context.setAttribute("pro_status", pro_status);
		
		//勝瑜
		//通知
		Map<String, String> note_status = new LinkedHashMap<String, String>();
		note_status.put("0", "未讀");note_status.put("1", "已讀");
		context.setAttribute("note_status",note_status);
		
		//心得
		Map<String, String> cate_no = new LinkedHashMap<String, String>();
		cate_no.put("1", "慢跑");cate_no.put("2", "瑜珈"); cate_no.put("3", "健身");
		cate_no.put("4", "舉重"); cate_no.put("5", "體適能");
		context.setAttribute("cate_no", cate_no);
		
		//尹翔
		//會員
		Map<Integer, String> sexual = new LinkedHashMap<Integer, String>();
		sexual.put(0, "男性");
		sexual.put(1, "女性");
		context.setAttribute("sexual", sexual);
		
		Map<Integer, String> mem_status = new LinkedHashMap<Integer, String>();
		mem_status.put(0, "正常");
		mem_status.put(1, "停權");
		mem_status.put(2, "審核中");
		context.setAttribute("mem_status", mem_status);

		//教練
		Map<Integer, String> coa_qualifications = new LinkedHashMap<Integer, String>();
		coa_qualifications.put(0, "沒資格");
		coa_qualifications.put(1, "有資格");
		coa_qualifications.put(2, "審核中");
		context.setAttribute("coa_qualifications", coa_qualifications);
		
	}
	
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
	}
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	
	}
}
