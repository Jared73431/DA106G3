<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.foodnutrition.model.*"%>
<%@ page import="java.util.*"%>
<%
String feature = "F0003";
%>
<%@ include file="/back-end/backstage/authList.file" %>
<%@ include file="/back-end/backstage/navbar.file" %>
<%
	FoodNutritionVO foodNutritionVO = (FoodNutritionVO) request.getAttribute("foodNutritionVO");
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>食物資料修改</title>

<style>
div#editFood{
	margin: 0 auto;
}


</style>

</head>
     

<div class="container" id="editFood">

	<table id="table-1">
		<tr>
			<td>
				<h3>食物資料修改 - update_food_input.jsp</h3>
				<h4>
					<a href="<%=request.getContextPath()%>/back-end/foodnutrition/listAllFood.jsp">回首頁</a>
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

	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/foodnutrition/foodnutrition.do" name="form1">
		<table>
			<tr>
				<td>食物編號:<font color=red><b>*</b></font></td>
				<td><%=foodNutritionVO.getFood_no()%></td>
			</tr>
			<tr>
				<td>食物名稱:</td>
				<td><input type="text" name="food_name"
					value="<%=(foodNutritionVO == null) ? "" : foodNutritionVO.getFood_name()%>" /></td>
			<tr>
				<td>食物品牌:</td>
				<td><input type="text" name="ref_brand"
					value="<%=(foodNutritionVO == null) ? "" : foodNutritionVO.getRef_brand()%>" /></td>
			<tr>
				<td>分量單位:</td>
				<td><input type="text" name="ref_amount"
					value="<%=(foodNutritionVO == null) ? "" : foodNutritionVO.getRef_amount()%>" /></td>
			<tr>
				<td>每單位熱量:</td>
				<td><input type="text" name="ref_cal"
					value="<%=(foodNutritionVO == null) ? "" : foodNutritionVO.getRef_cal()%>" /></td>
			<tr>
				<td>每單位蛋白質:</td>
				<td><input type="text" name="ref_protein"
					value="<%=(foodNutritionVO == null) ? "" : foodNutritionVO.getRef_protein()%>" /></td>
			<tr>
				<td>每單位脂肪:</td>
				<td><input type="text" name="ref_fat"
					value="<%=(foodNutritionVO == null) ? "" : foodNutritionVO.getRef_fat()%>" /></td>
			<tr>
				<td>每單位碳水化合物:</td>
				<td><input type="text" name="ref_cho"
					value="<%=(foodNutritionVO == null) ? "" : foodNutritionVO.getRef_cho()%>" /></td>
		</table>
		<br> <input type="hidden" name="action" value="update"> 
		<input type="hidden" name="food_no" value="<%=foodNutritionVO.getFood_no()%>"> 
		<input type="hidden" name="requestURL" value="<%=request.getParameter("requestURL")%>"> 
		<input type="hidden" name="whichPage"  value="<%=request.getParameter("whichPage")%>">  
		<input type="submit" value="送出修改">
	</FORM>
	</div>
</div>


</body>
</html>