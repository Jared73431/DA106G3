<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.orderlist.model.*"%>

<%
String feature = "F0005";
%>
<%@ include file="/back-end/backstage/authList.file" %>
<%@ include file="/back-end/backstage/navbar.file" %>

<%
    OrderListService orderlistSvc = new OrderListService();
    List<OrderListVO> list = orderlistSvc.getAll();
    pageContext.setAttribute("list",list);
%>

<jsp:useBean id="productSvc" scope="page" class="com.product.model.ProductService" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>所有訂單明細資料 - listAllOrderList.jsp</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/js/vendors/bootstrap/css/bootstrap.min.css">
<style>
	/* 表格內容垂直置中 */
	.table td{
	  vertical-align: middle;
	  height:4em;
	}
		/* 訂單編號 */
	tbody td:first-child {
		width:6em;
 	}
	/* 會員編號 */
	tbody td:nth-child(2) {
		width:6em;
 	}
	/* 郵遞區號 */
	tbody td:nth-child(3) ,thead th:nth-child(3){
		width:18em;
 	}
	/* 訂單地址 */
	tbody td:nth-child(4),tbody td:nth-child(5),
	tbody th:nth-child(4),tbody th:nth-child(5){
		text-align:center;
 	}		
</style>

</head>
<body style='background-color:#ECF5FF;'>

<div class="container-md">
	<div class="row justify-content-center">
		<div class="col-md-4" style="text-align: center;">	
			<h4>訂單詳情<a href="<%=request.getContextPath() %>/back-end/backstage/index.jsp"><img src="<%=request.getContextPath()%>/js/back-end/images/p3.png" style="height:70px;"></a>
			<input type="button" class="btn btn-dark" onclick="location.href='<%=request.getContextPath() %>/back-end/backstage/index.jsp'" value="返回"/>			
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
	<div class="row justify-content-center" style="margin:10px auto;">
		<%@ include file="page1.file" %> 
	</div>
	<div class="row justify-content-center">		
		<div class="col-md-8">					
		<table class="table table-hover" style="font-size:14px;">
			<thead class="table-warning">
			<tr>
				<th scope="col">訂單編號</th>
				<th scope="col">產品編號</th>
				<th scope="col">產品名稱</th>				
				<th scope="col">產品數量</th>
				<th scope="col">訂單單價</th>
			</tr>
			</thead>
			<tbody>
			<c:forEach var="orderlistVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">				
				<tr>
					<td scope="col">${orderlistVO.order_no}</td>
					<td scope="col">${orderlistVO.pro_no}</td>
					<td scope="col">${productSvc.getOneProduct(orderlistVO.pro_no).pro_name}</td>
					<td scope="col">${orderlistVO.pro_qty}</td>
					<td scope="col">${orderlistVO.order_price}</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		</div>
	</div>
	<div class="row justify-content-center">
		<div class="col" style="margin:10px auto; text-align:center;">	
			<%@ include file="page2.file" %>
		</div>	
	</div>	
</div>
</body>
</html>