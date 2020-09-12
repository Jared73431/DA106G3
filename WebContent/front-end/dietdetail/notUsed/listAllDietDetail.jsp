<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.dietdetail.model.*"%>
<%
	DietDetailService dietDetailSvc = new DietDetailService();
	List<DietDetailVO> list = dietDetailSvc.getAll();
	pageContext.setAttribute("list", list);
%>

<jsp:useBean id="foodnutritionSvc" class="com.foodnutrition.model.FoodNutritionService" />

<html>
<head>
<title>所有紀錄資料 - listAllDiet.jsp</title>

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
	width: 800px;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 5px;
}

table, th, td {
	border: 1px solid #CCCCFF;
}

th, td {
	padding: 5px;
	text-align: center;
}
</style>

</head>
<body bgcolor='white'>

	<h4>此頁練習採用 EL 的寫法取值:</h4>
	<table id="table-1">
		<tr>
			<td>
				<h3>所有紀錄資料 - listAllDiet.jsp</h3>
				<h4>
					<a href="<%=request.getContextPath()%>/front-end/dietdetail/select_page.jsp">回首頁</a>
				</h4>
			</td>
		</tr>
	</table>

	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<table>
		<tr>
			<th>紀錄編號</th>
			<th>食物名稱</th>
			<th>每份熱量</th>
			<th>食用份量</th>
			<th>熱量</th>
			<th>修改</th>
			<th>刪除</th>
		</tr>
		<%@ include file="page1.file"%>
		<c:forEach var="dietDetailVO" items="${list}" begin="<%=pageIndex%>"
			end="<%=pageIndex+rowsPerPage-1%>">

			<tr>
				<td>${dietDetailVO.diet_no}</td>
				<td>${foodnutritionSvc.getOneFood(dietDetailVO.food_no).food_name}</td>
				<td>${foodnutritionSvc.getOneFood(dietDetailVO.food_no).ref_cal}</td>
				<td>${dietDetailVO.amount}</td>
				<td>${dietDetailVO.food_cal}</td>
				
				
				<td>
					<FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/dietdetail/dietdetail.do"
						style="margin-bottom: 0px;">
						<input type="submit" value="修改"> 
						<input type="hidden" name="diet_no" value="${dietDetailVO.diet_no}">
						<input type="hidden" name="food_no" value="${dietDetailVO.food_no}"> 
						<input type="hidden" name="action" value="getOne_For_Update">
					</FORM>
				</td>
				<td>
					<FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/dietdetail/dietdetail.do"
						style="margin-bottom: 0px;">
						<input type="submit" value="刪除"> 
						<input type="hidden" name="diet_no" value="${dietDetailVO.diet_no}">
						<input type="hidden" name="food_no" value="${dietDetailVO.food_no}"> 
						<input type="hidden" name="action" value="delete">
					</FORM>
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="page2.file"%>

</body>
</html>