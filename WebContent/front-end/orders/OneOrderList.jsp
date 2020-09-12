<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.orderlist.model.*"%>
<%@ page import="com.product.model.*"%>
<%@ page import="com.members.model.*"%>

<%
String member_no = (String)session.getAttribute("member_no");
MembersService membersSvc = new MembersService();
MembersVO membersVO = membersSvc.getOneMembers(member_no);
pageContext.setAttribute("membersVO",membersVO); 
%>
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
<link rel="icon" href="<%=request.getContextPath()%>/picture/logo.png">
<style>
  	.table thead {
	  background-color: #FFF0F5;
      border: 2px solid black;
  	}
  	.table {
	  border: 2px solid black;
	  margin: 15px auto; 	
  	}
  	.table thead th{
	  vertical-align: middle;
	  font-size:14px;
	  border-bottom: 2px solid black;
	  text-align:center;	  
  	}
  	.table tbody td{
	  vertical-align: middle;
	  font-size:14px;
	  text-align:center;
  	}
</style>

</head>
<body style="background-color:#ECF0F1">
<!-- 套用首頁標頭 -->
<%@ include file="/front-end/homepage/nav.file"%>
<!-- 套用首頁標頭 -->
<div id="listall">
<div class="container-md">
	<div class="row justify-content-center">
		<div class="col-md-1">
			<img src="<%=request.getContextPath() %>/front-end/shopping/images/Add.png" class="img-fluid"> 
		</div>
		<div class="col-md-4" style="margin:auto 0px">
			<font size="+3" style="vertical-align: -webkit-baseline-middle;">我的訂單明細</font>
			<input type="button" class="btn btn-dark" onclick="location.href='<%=request.getContextPath() %>/front-end/orders/listMemOrders.jsp'" value="訂單列表"/>						
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
				<%@ include file="page1.file" %> 
			<table class="table table-hover col-md-8">
				<thead>
					<tr>
						<th>訂單編號</th>
						<th>產品名稱</th>
						<th>產品數量</th>
						<th>訂單單價</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach var="orderlistVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
					
					<tr>
						<td>${orderlistVO.order_no}</td>
						<td>
							<a href="<%=request.getContextPath()%>/shopping.do?whichPage=<%=whichPage %>&action=getOne_For_Display&pro_no=${orderlistVO.pro_no}">
								${productSvc.getOneProduct(orderlistVO.pro_no).pro_name}
							</a>
						</td>
						<td>${orderlistVO.pro_qty}</td>
						<td>${orderlistVO.order_price}</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
			<%@ include file="page2.file" %>
		</div>
	</div>
</div>
<!-- 首頁註腳 -->
<%@ include file="/front-end/homepage/footer.file"%>	
<!-- 首頁註腳 -->
</body>
</html>