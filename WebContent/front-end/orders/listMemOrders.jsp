<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.orders.model.*"%>
<%@ page import="com.members.model.*"%>

<%
	String member_no = (String)session.getAttribute("member_no");
	MembersService membersSvc = new MembersService();
	MembersVO membersVO = membersSvc.getOneMembers(member_no);
	pageContext.setAttribute("membersVO",membersVO); 
%>

<%
    OrdersService orderSvc = new OrdersService();
	List<OrdersVO> list = orderSvc.getMem(member_no);
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
<title>我的訂單 - listMemOrders.jsp</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/js/vendors/bootstrap/css/bootstrap.min.css">
<link rel="icon" href="<%=request.getContextPath()%>/picture/logo.png">
<style>
  	/* 訂單編號 */
	tbody td:first-child {
		width:3em;
 	}
	/* 郵遞區號 */
	tbody td:nth-child(2),thead th:nth-child(2) {
		width:8em;
		text-align:center;
 	}
	/* 訂單地址 */
	tbody td:nth-child(3),thead th:nth-child(3){
		width:12em;
 	}
	/* ETB與ETA */
	tbody td:nth-child(4),tbody td:nth-child(5){
		width:10em;
 	}
 	/* 總價與狀態 */
 	thead th:nth-child(6),thead th:nth-child(7),
	tbody td:nth-child(6),tbody td:nth-child(7){
		width:7em;
		text-align:center;
 	}
 	 	/* 總價與狀態 */
	tbody td:nth-child(8),thead th:nth-child(8){
		width:8em;
		text-align:center;
 	}
	/* 表格內容垂直置中 */
  	.table tbody td{
	  vertical-align: middle;
	  font-size:14px;
	  height:7em;
  	}
  	.table thead th{
	  vertical-align: middle;
	  font-size:14px;
	  border-bottom: 2px solid black;	  
  	}
  	.table thead {
	  background-color: #FFF0F5;
      border: 2px solid black;
  	}
  	.table {
	  border: 2px solid black;
	  margin: 15px auto; 	
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
			<font size="+3" style="vertical-align: -webkit-baseline-middle;">我的訂單列表</font>
			<input type="button" class="btn btn-dark" onclick="location.href='<%=request.getContextPath() %>/front-end/members/membersPage.jsp'" value="會員頁面"/>						
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
	<div class="container-md" style="margin:20px auto;">
		<div class="row justify-content-center">
				<%@ include file="page1.file" %> 
			<table class="table table-hover col-md-10">
				<thead>
				<tr>
					<th scope="col">訂單編號</th>
					<th scope="col">郵遞區號</th>
					<th scope="col">訂單地址</th>
					<th scope="col">訂單成立時間</th>
					<th scope="col">預計送達時間</th>
					<th scope="col">訂單總價</th>
					<th scope="col">訂單狀態</th>
					<th scope="col">訂單詳情</th>
				</tr>
				</thead>
				<tbody>
				<c:forEach var="ordersVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
					
					<tr>
						<td scope="col">${ordersVO.order_no}</td>
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
						  <FORM METHOD="post" ACTION="<%=request.getContextPath() %>/order_list.do" style="margin-bottom: 0px;">
						     <input class="btn btn-dark" type="submit" value="訂單明細" style="font-size:12px;">
						     <input type="hidden" name="order_no"  value="${ordersVO.order_no}">
						     <input type="hidden" name="action"	value="getOne_For_Check"></FORM>
						</td>
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