<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.orders.model.*"%>

<%
String feature = "F0005";
%>
<%@ include file="/back-end/backstage/authList.file" %>
<%@ include file="/back-end/backstage/navbar.file" %>

<!DOCTYPE html>

<%
  OrdersVO ordersVO = (OrdersVO) request.getAttribute("ordersVO");
  // Gym.java (Concroller) 存入req的 ordersVO 物件 (包括幫忙取出的 ordersVO , 也包括輸入資料錯誤時的 ordersVO 物件)
%>
<%
	Map<String, String> order_status = new LinkedHashMap<String, String>();
	order_status.put("1", "成立");
	order_status.put("2", "出貨"); 
	pageContext.setAttribute("order_status", order_status);
%>
<html>
<head>
<meta charset="UTF-8">

<title>訂單資料修改 - update_orders_input.jsp</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/js/vendors/bootstrap/css/bootstrap.min.css">
<style>
  table#table-1 {
	background-color: #CCCCFF;
    border: 2px solid black;
    text-align: center;
  }
  table#table-1 h4 {
    color: red;
    display: block;
    margin-bottom: 1px;
  }
  h4 {
    color: blue;
    display: inline;
  }
</style>

<style>
  table {
	width: 450px;
	background-color: white;
	margin-top: 1px;
	margin-bottom: 1px;
  }
  table, th, td {
    border: 0px solid #CCCCFF;
  }
  th, td {
    padding: 1px;
  }
</style>

</head>
<body bgcolor='white'>

<div class="container-md">
	<div class="row justify-content-center">
		<div class="col-md-4" style="text-align: center;">	
			<h4>訂單資料修改<a href="<%=request.getContextPath() %>/back-end/orders/listAllOrders.jsp"><img src="<%=request.getContextPath()%>/js/back-end/images/p5.png" style="height:70px;"></a>
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

		<FORM METHOD="post" ACTION="<%=request.getContextPath() %>/orders.do" name="form1">
		<table>
			<tr>
				<td>訂單編號:<font color=red><b>*</b></font></td>
				<td><%=ordersVO.getOrder_no()%></td>
			</tr>
			<tr>
				<td>會員編號:</td>
				<td><%=ordersVO.getMember_no()%></td>
				</tr>
				<tr>
				<td>郵遞區號:</td>
				<td id="twzipcode"></td>
				</tr>
				<tr>
				<td>訂單地址:</td>
				<td><input type="TEXT" name="order_address" size="45" id="address"
					 value="<%=ordersVO.getOrder_address()%>" /></td>
				</tr>
				<tr>
				<td>訂單成立時間:</td>
				<td><fmt:formatDate value="<%=ordersVO.getEtb()%>" pattern="yyyy-MM-dd HH:mm"/></td>
				</tr>
				<tr>
				<td>預計送達時間:</td>
				<td><input type="TEXT" name="eta" size="45" id="f_date1"/></td>
				</tr>
				<tr>
				<td>訂單總價:</td>
				<td><%=ordersVO.getTotal_price()%></td>
				</tr>
				<tr>
				<td>訂單狀態:</td>
				<td>
					<select size="1" name="order_status">
						<c:forEach var="status" items="${order_status}">
							<option value="${status.key}"  ${(status.key == ordersVO.order_status) ? 'selected':'' } >${status.value}
						</c:forEach>
					</select>
				</td>		 				
			</tr>
		</table>
		<br>
		<input type="hidden" name="action" value="update">
		<input type="hidden" name="order_no" value="<%=ordersVO.getOrder_no()%>">
		<input type="hidden" name="member_no" value="<%=ordersVO.getMember_no()%>">
		<input type="hidden" name="order_zip" value="<%=ordersVO.getOrder_zip()%>">
		<input type="hidden" name="order_address" value="<%=ordersVO.getOrder_address()%>">
		<input type="hidden" name="etb" value="<fmt:formatDate value="<%=ordersVO.getEtb()%>" pattern="yyyy-MM-dd HH:mm:ss"/>">
		<input type="hidden" name="total_price" value="<%=ordersVO.getTotal_price()%>">
		<input type="submit" value="送出修改"></FORM>
	</div>
</div>
</body>
<script src="<%=request.getContextPath()%>/js/vendors/jquery/jquery-3.5.0.min.js"></script>
<script src="<%=request.getContextPath()%>/js/jQuery-TWzipcode-master/jquery.twzipcode.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/datetimepicker/jquery.datetimepicker.css" />
<script src="<%=request.getContextPath()%>/js/datetimepicker/jquery.datetimepicker.full.js"></script>

<!-- 地址套件設定 -->
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

<!-- =========================================以下為 datetimepicker 之相關設定========================================== -->

<style>
  .xdsoft_datetimepicker .xdsoft_datepicker {
           width:  300px;   /* width:  300px; */
  }
  .xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
           height: 151px;   /* height:  151px; */
  }
</style>

<script>
        $.datetimepicker.setLocale('zh');
        $('#f_date1').datetimepicker({
           theme: '',              //theme: 'dark',
 	       timepicker:true,       //timepicker:true,
 	       step: 10,                //step: 60 (這是timepicker的預設間隔60分鐘)
 	       format:'Y-m-d H:i:00',         //format:'Y-m-d H:i:s',
 		   value: '<%= (ordersVO.getEta()==null)? ordersVO.getEtb() : ordersVO.getEta()%>', // value:   new Date(),
           //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
           //startDate:	            '2017/07/10',  // 起始日
           minDate:               '-1970-01-01', // 去除今日(不含)之前
           //maxDate:               '+1970-01-01'  // 去除今日(不含)之後
        }); 
</script>
</html>