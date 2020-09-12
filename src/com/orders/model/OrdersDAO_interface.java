package com.orders.model;

import java.util.*;
import com.orderlist.model.*;

public interface OrdersDAO_interface {
	public int insert(OrdersVO ordersVO);
	public int update(OrdersVO ordersVO);
	public int delete(String order_no);
    public OrdersVO findByPrimaryKey(String order_no);
	public List<OrdersVO> getAll();
	// 提供出貨管理
	public List<OrdersVO> getEta();
	// 提供會員查詢
	public List<OrdersVO> getMem(String member_no);
	// 要測試多形呼叫
	public boolean insert(OrdersVO OrdersVO , List<OrderListVO> buylist);
	// 扣款
	public boolean paybill (String member_no,Integer real_blance,Integer deposit,Integer withdraw,String remark);
}
