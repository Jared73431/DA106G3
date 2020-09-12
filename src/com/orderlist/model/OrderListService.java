package com.orderlist.model;

import java.sql.Connection;
import java.util.List;

public class OrderListService {
	
	private OrderListDAO_interface dao;

	public OrderListService() {
		dao = new OrderListDAO();
	}

	public OrderListVO addOrderList(String order_no, String pro_no, Integer pro_qty, Integer order_price) {

		OrderListVO orderlistVO = new OrderListVO();

		orderlistVO.setOrder_no(order_no);
		orderlistVO.setPro_no(pro_no);
		orderlistVO.setPro_qty(pro_qty);
		orderlistVO.setOrder_price(order_price);
		dao.insert(orderlistVO);

		return orderlistVO;
	}
	// 自增主鍵使用
	public void addOrderListA(OrderListVO orderlistVO,Connection con) {
		dao.insertA(orderlistVO, con);
	}
	public void deleteOrderList(String order_no, String pro_no) {
		dao.delete(order_no, pro_no);
	}

	public OrderListVO getOneOrderList(String order_no) {
		return dao.findByPrimaryKey(order_no);
	}

	public List<OrderListVO> getAll() {
		return dao.getAll();
	}
	
	public List<OrderListVO> getOrderList(String order_no) {
		return dao.getOrderList(order_no);
	}
}
