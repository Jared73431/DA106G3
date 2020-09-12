<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.orders.model.*"%>

<%
String feature = "F0005";
%>
<%@ include file="/back-end/backstage/authList.file" %>
<%@ include file="/back-end/backstage/navbar.file" %>

<%
    OrdersService orderSvc = new OrdersService();
    List<OrdersVO> list = orderSvc.getAll();
    pageContext.setAttribute("list",list);
%>
<%
	Map<String, String> order_status = new LinkedHashMap<String, String>();
	order_status.put("1", "成立");	
	order_status.put("2", "出貨");
	pageContext.setAttribute("order_status", order_status);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>所有訂單資料 - listAllOrders.jsp</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/js/vendors/bootstrap/css/bootstrap.min.css">
<style>
	/* 訂單編號 */
	tbody td:first-child {
		width:2em;
 	}
	/* 會員編號 */
	tbody td:nth-child(2) {
		width:2em;
 	}
	/* 郵遞區號 */
	tbody td:nth-child(3) ,thead th:nth-child(3){
		width:6em;
 	}
	/* 訂單地址 */
	tbody td:nth-child(4){
		width:14em;
 	}	
	/* ETB與ETA */
	tbody td:nth-child(5),tbody td:nth-child(6){
		width:6em;
 	}
 	/* 總價與狀態 */
	tbody td:nth-child(7),tbody th:nth-child(7){
		width:0.8em;
 	}
 	 	/* 總價與狀態 */
	tbody td:nth-child(9) form{
		width:8em;
		text-align:center;
		display:inline;
 	}
	/* 表格內容垂直置中 */
	.table td{
	  vertical-align: middle;
	  font:12px;
	  height:6.2em;
	}
</style>
</head>
<body style='background-color:#ECF5FF;'>

<div class="container-md">
	<div class="row justify-content-center">
		<div class="col-md-4" style="text-align: center; margin: 20px auto;">	
			<h4>訂單管理<a href="<%=request.getContextPath() %>/back-end/backstage/index.jsp"><img src="<%=request.getContextPath()%>/js/back-end/images/p3.png" style="height:70px;"></a>
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
	<div class="row justify-content-center" style="margin:5px auto;">		
		<%@ include file="page1.file" %> 
	</div>		
	<div class="row justify-content-center">		
		<div class="col-md-11">
		<table class="table table-hover" style="font-size:14px;">
			<thead class="table-warning">
				<tr>
					<th scope="col">訂單編號</th>
					<th scope="col">會員編號</th>
					<th scope="col">郵遞區號</th>
					<th scope="col">訂單地址</th>
					<th scope="col">訂單成立時間</th>
					<th scope="col">預計送達時間</th>
					<th scope="col">訂單總價</th>
					<th scope="col">訂單狀態</th>				
					<th scope="col">訂單處理</th>
				</tr>
			</thead>
			<tbody>	
			<c:forEach var="ordersVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
				<tr>
					<td scope="col">${ordersVO.order_no}</td>
					<td scope="col">${ordersVO.member_no}</td>
					<td scope="col">${ordersVO.order_zip}</td>
					<td scope="col">${ordersVO.order_address}</td>
					<td scope="col"><fmt:formatDate value="${ordersVO.etb}" pattern="yyyy-MM-dd HH:mm"/></td>
					<td scope="col"><fmt:formatDate value="${ordersVO.eta}" pattern="yyyy-MM-dd HH:mm"/></td>
					<td scope="col">${ordersVO.total_price}</td>
					<td scope="col">
						<c:forEach var="status" items="${order_status}">
							<c:if test="${(status.key == ordersVO.order_status)}">
								${status.value}
							</c:if>
						</c:forEach>
					</td>
					<td scope="col">
					  <FORM METHOD="post" ACTION="<%=request.getContextPath() %>/orders.do">
					     <input type="submit" value="修改">
					     <input type="hidden" name="order_no"  value="${ordersVO.order_no}">
					     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
					  <FORM METHOD="post" ACTION="<%=request.getContextPath() %>/order_list.do">
					     <input type="submit" value="詳情">
					     <input type="hidden" name="order_no"  value="${ordersVO.order_no}">
					     <input type="hidden" name="whichPage"  value="<%=whichPage%>">
					     <input type="hidden" name="action" value="get_For_Display"></FORM>
					</td>
				</tr>				
			</c:forEach>
			</tbody>
		</table>
		</div>
	</div>
	<div class="row justify-content-center">
		<div class="col-md" style="text-align:center; margin:10px auto">			
			<%@ include file="page2.file" %>
		</div>
	</div>
</div>
</body>
</html>