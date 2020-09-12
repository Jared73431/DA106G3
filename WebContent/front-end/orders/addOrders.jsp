<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.orders.model.*"%>
<%@ page import="com.members.model.*"%>

<%
	String member_no = (String)session.getAttribute("member_no");
	MembersService membersSvc = new MembersService();
	MembersVO membersVO = membersSvc.getOneMembers(member_no);
	pageContext.setAttribute("membersVO",membersVO); 
%>

<%
	OrdersVO ordersVO = (OrdersVO) request.getAttribute("ordersVO");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>購物資料新增 - addOrders.jsp</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/js/vendors/bootstrap/css/bootstrap.min.css">
<script src="<%=request.getContextPath()%>/js/vendors/jquery/jquery-3.5.0.min.js"></script>
<script src="<%=request.getContextPath()%>/js/jQuery-TWzipcode-master/jquery.twzipcode.min.js"></script>
<link rel="icon" href="<%=request.getContextPath()%>/picture/logo.png">
<style>
  .table th, .table td {
    vertical-align:middle;
  }
  .table th, .table td{
    border-bottom:1px solid #dee2e6;
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
		<span>			
				 <font size="+3" style="vertical-align: -webkit-baseline-middle;">訂單資料新增</font>
				 <img src="<%=request.getContextPath()%>/front-end/orders/images/Add2.png" style="height:70px;">
		</span>
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
			<FORM METHOD="post" ACTION="<%=request.getContextPath() %>/orders.do" name="form1" style="margin:10px auto;">
			<table class="table table-hover" style="background-color:#ECF0F1;">
				<tr>
					<td>會員名稱:</td>
					<td><%= membersVO.getMem_name()%></td>
				</tr>
				<tr>
					<td>郵遞區號:</td>
					<td id="twzipcode"></td>
				</tr>
				<tr>
					<td>訂單地址:</td>
					<td>
						<input type="text" name="order_address" id="address" class="form-control" placeholder="請填寫寄送地址" aria-label="Deliver Address" aria-describedby="basic-addon1"
							value="<%= (ordersVO.getOrder_address()==null)? "" : ordersVO.getOrder_address()%>" />
					</td>
				</tr>
				<tr>
					<td>訂單總價:</td>
					<td><%= ordersVO.getTotal_price()%></td>
				</tr>
			</table>
			<input type="hidden" name="action" value="insertA">
			<input type="hidden" name="member_no" value="<%= ordersVO.getMember_no()%>">
			<input type="hidden" name="total_price" value="<%= ordersVO.getTotal_price()%>">
			<input class="btn btn-light col-md-3 offset-5" type="submit" value="送出新增" style="text-align:center;"></FORM>
		</div>
	</div>
</div>
<!-- 首頁註腳 -->
<%@ include file="/front-end/homepage/footer.file"%>	
<!-- 首頁註腳 -->

</body>
<script type="text/javascript">
$("#twzipcode").twzipcode({
	zipcodeName: "order_zip", // 自訂郵遞區號input標籤的name值,方便送後端
	zipcodeSel: "<%= (ordersVO.getOrder_zip()==null)? "" : ordersVO.getOrder_zip()%>"
});

// 寫一個jQuery把選擇的文字塞到地址欄位,使用者友善介面
$("#twzipcode").on('change', function () {
	var county = $("#twzipcode").twzipcode('get','county');
	var district = $("#twzipcode").twzipcode('get','district');
	var result = county+district;
	$("#address").val(result);
	});
</script>
</html>