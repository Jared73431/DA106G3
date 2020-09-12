package com.orders.model;

import java.util.List;

import com.orderlist.model.*;

import java.sql.*;

public class OrdersService {
	
	private OrdersDAO_interface dao;

	public OrdersService() {
		dao = new OrdersDAO();
	}

	public OrdersVO addOrders(String member_no,String order_zip,String order_address, Integer total_price) {

		OrdersVO ordersVO = new OrdersVO();

		ordersVO.setMember_no(member_no);
		ordersVO.setOrder_zip(order_zip);
		ordersVO.setOrder_address(order_address);
		ordersVO.setTotal_price(total_price);
		dao.insert(ordersVO);

		return ordersVO;
	}

	public OrdersVO updateOrders(String order_no,String member_no,String order_zip,String order_address,Timestamp etb, Timestamp eta, Integer total_price, Integer order_status) {

		OrdersVO ordersVO = new OrdersVO();
		
		ordersVO.setOrder_no(order_no);
		ordersVO.setMember_no(member_no);
		ordersVO.setOrder_zip(order_zip);
		ordersVO.setOrder_address(order_address);
		ordersVO.setEtb(etb);
		ordersVO.setEta(eta);
		ordersVO.setTotal_price(total_price);
		ordersVO.setOrder_status(order_status);
		dao.update(ordersVO);

		return ordersVO;
	}
    
	public boolean insert(OrdersVO OrdersVO , List<OrderListVO> buylist) {
		return dao.insert(OrdersVO, buylist);
	}

	public void deleteOrders(String order_no) {
		dao.delete(order_no);
	}

	public OrdersVO getOneOrders(String order_no) {
		return dao.findByPrimaryKey(order_no);
	}

	public List<OrdersVO> getAll() {
		return dao.getAll();
	}
	// 出貨管理用
	public List<OrdersVO> getEta() {
		return dao.getEta();
	}
	
	public List<OrdersVO> getMem(String member_no) {
		return dao.getMem(member_no);
	}
	
	// 新增扣款用
	public boolean paybill (String member_no,Integer real_blance,Integer deposit,Integer withdraw,String remark) {
		return dao.paybill(member_no, real_blance, deposit, withdraw, remark);
	}
}
