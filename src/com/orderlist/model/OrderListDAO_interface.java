package com.orderlist.model;

import java.sql.Connection;
import java.util.*;


public interface OrderListDAO_interface {
	public int insert(OrderListVO orderlistVO);
	public int delete(String order_no,String pro_no);
    public OrderListVO findByPrimaryKey(String order_no);
	public List<OrderListVO> getAll();
	public void insertA(OrderListVO orderlistVO,Connection con);
	public List<OrderListVO> getOrderList(String order_no);
}
