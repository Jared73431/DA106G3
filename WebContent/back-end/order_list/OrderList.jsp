<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.orderlist.model.*"%>
<%@ page import="com.product.model.*"%>

<%
String feature = "F0005";
%>
<%@ include file="/back-end/backstage/authList.file" %>
<%@ include file="/back-end/backstage/navbar.file" %>

<%
	String order_no = (String) session.getAttribute("check_order");
	OrderListService orderlistSvc = new OrderListService();
	List<OrderListVO> list = orderlistSvc.getOrderList(order_no);
	pageContext.setAttribute("list",list);    
%>
<jsp:useBean id="productSvc" scope="page" class="com.product.model.ProductService" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>我的訂單明細 - OneOrderList.jsp</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/js/vendors/bootstrap/css/bootstrap.min.css">
<style>
	/* 表格內容垂直置中 */
	.table td{
	  vertical-align: middle;
	  font:12px;
	  height:3em;
	}
</style>

</head>
<body style='background-color:#ECF5FF;'>

<div class="container-md">
	<div class="row justify-content-center">
		<div class="col-md-4" style="text-align: center;">	
			<h4>訂單明細<a href="<%=request.getContextPath() %>/back-end/orders/listAllOrders.jsp"><img src="<%=request.getContextPath()%>/js/back-end/images/p3.png" style="height:70px;"></a>
			<input type="button" class="btn btn-dark" onclick="location.href='<%=request.getContextPath() %>/back-end/orders/listAllOrders.jsp'" value="返回"/>			
			</h4>		
		</div>
	</div>
</div>

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>
	<div class="container-md">
		<div class="row justify-content-center">			
			<div class="col-md-8">			
				<table class="table table-hover" style="font-size:14px;">
					<thead class="table-warning">
						<tr>
							<th scope="col">訂單編號</th>
							<th scope="col">產品名稱</th>
							<th scope="col">產品數量</th>
							<th scope="col">訂單單價</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach var="orderlistVO" items="${list}">					
						<tr>
							<td scope="col">${orderlistVO.order_no}</td>
							<td scope="col">${productSvc.getOneProduct(orderlistVO.pro_no).pro_name}</td>
							<td scope="col">${orderlistVO.pro_qty}</td>
							<td scope="col">${orderlistVO.order_price}</td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>