<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.dietdetail.model.*"%>

<%
	DietDetailVO dietDetailVO = (DietDetailVO) request.getAttribute("dietDetailVO");
%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>資料修改 - update_diet_input.jsp</title>
<link href="<%=request.getContextPath()%>/js/select2/dist/css/select2.min.css" rel="stylesheet" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/select2/dist/js/select2.min.js"></script>
<link rel="icon" href="<%=request.getContextPath()%>/picture/logo.png">

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

	<table id="table-1">
		<tr>
			<td>
				<h3>食物資料修改 - update_diet_input.jsp</h3>
				<h4>
					<a href="<%=request.getContextPath()%>/front-end/dietdetail/select_page.jsp">回首頁</a>
				</h4>
			</td>
		</tr>
	</table>

	<h3>資料修改:</h3>

	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/dietdetail/dietdetail.do" name="form1">
		<table>
			<table>
				<tr>
					<td>紀錄編號:</td>
					<td>${dietDetailVO.diet_no}</td>
				</tr>
				<jsp:useBean id="foodnutritionSvc" scope="page"
					class="com.foodnutrition.model.FoodNutritionService" />

				<tr>
					<td>食物名稱:</td>
					<td>${foodnutritionSvc.getOneFood(dietDetailVO.food_no).food_name}
					
				</tr>

				<tr>
					<td>食用分量:</td>

					<td><input type="text" name="amount"
						value="<%=(dietDetailVO == null) ? "" : dietDetailVO.getAmount()%>" /></td>
				</tr>
				
			</table>
			<br>
			<input type="hidden" name="action" value="update">
			<input type="hidden" name="diet_no" value="<%=dietDetailVO.getDiet_no()%>">
			<input type="hidden" name="food_no" value="<%=dietDetailVO.getFood_no()%>">
			<input type="submit" value="送出修改">
	</FORM>
</body>


</html>