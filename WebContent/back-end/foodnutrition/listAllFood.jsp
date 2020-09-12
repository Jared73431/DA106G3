<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.foodnutrition.model.*"%>

<%
String feature = "F0003";
%>
<%@ include file="/back-end/backstage/authList.file" %>
<%@ include file="/back-end/backstage/navbar.file" %>
<%
    FoodNutritionService foodnutritionSvc = new FoodNutritionService();
    List<FoodNutritionVO> list = foodnutritionSvc.getAll();
    pageContext.setAttribute("list",list);
%>

<html>
<head>
<title>食物資料</title>

<style>


</style>

</head>
       

<div class="container">
	<div class="row" id="top">
		<div class="col-6 offset-3">
			<%@ include file="page1.file" %>
		</div>
		<div class="col-1 offset-2">
			<a href='<%=request.getContextPath()%>/front-end/foodnutrition/addFood.jsp'>新增</a>		
		</div>
	</div>
 	<table class="table">
    	<thead class="thead-light">
			<tr>
				<th>食物編號</th>
				<th>食物名稱</th>
				<th>食物品牌</th>
				<th>分量單位</th>
				<th>每單位熱量</th>
				<th>每單位蛋白質</th>
				<th>每單位脂肪</th>
				<th>每單位碳水化合物</th>
				<th>修改</th>
				<th>刪除</th>
			</tr>
		 </thead>
    	<tbody> 
	<c:forEach var="foodNutritionVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
		
		<tr  ${(foodNutritionVO.food_no==param.food_no)? 'bgcolor=#CCCFF':''}>
		<td>${foodNutritionVO.food_no}</td>
		<td>${foodNutritionVO.food_name}</td>
		<td>${foodNutritionVO.ref_brand}</td>
		<td>${foodNutritionVO.ref_amount}</td>
		<td>${foodNutritionVO.ref_cal}</td>
		<td>${foodNutritionVO.ref_protein}</td>
		<td>${foodNutritionVO.ref_fat}</td>
		<td>${foodNutritionVO.ref_cho}</td>
			
			
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/foodnutrition/foodnutrition.do" style="margin-bottom: 0px;">
			     <input type="submit" value="修改">
			     <input type="hidden" name="food_no"  value="${foodNutritionVO.food_no}">
			     <input type="hidden" name="action"	value="getOne_For_Update">
			     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>">
			     <input type="hidden" name="whichPage"	value="<%=whichPage%>"> 
			  </FORM>
			</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/foodnutrition/foodnutrition.do" style="margin-bottom: 0px;">
			     <input type="submit" value="刪除">
			     <input type="hidden" name="food_no"  value="${foodNutritionVO.food_no}">
			     <input type="hidden" name="action" value="delete">
			     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>">
			     <input type="hidden" name="whichPage"	value="<%=whichPage%>">
			   </FORM>
			</td>
		</tr>
	</c:forEach>
</table>
<%@ include file="page2.file" %>
	</div>
 </div>

 
</body>
</html>