<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.dietdetail.model.*"%>

<%
	DietDetailVO dietDetailVO = (DietDetailVO) request.getAttribute("dietDetailVO");
	String diet_no = request.getParameter("diet_no");
	pageContext.setAttribute("diet_no", diet_no);
%>

<html>
<head>
<meta http-equiv="Content-Type" content="IE=edge,chrome=1" />
<title>食物紀錄新增 - addDietDetail.jsp</title>
<link href="<%=request.getContextPath()%>/js/select2/dist/css/select2.min.css" rel="stylesheet" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/select2/dist/js/select2.min.js"></script>
<link rel="icon" href="<%=request.getContextPath()%>/picture/logo.png">
<style>
table#adddetail{
width:40rem;
margin:3rem auto;
}
</style>

</head>
<body >


	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/dietdetail/dietdetail.do" name="form1">
		<table id="adddetail">
		<tr><td colspan="2"><h4><b>新增內容</b></h4></td></tr>
			<tr>
				<td>紀錄編號:</td>
				<td>${diet_no}</td>
			</tr>
			<jsp:useBean id="foodnutritionSvc" scope="page"
				class="com.foodnutrition.model.FoodNutritionService" />

			<tr>
				<td>食物名稱:</td>
				<td><select name="food_no" class="myselect" style="width:300px;">
						<c:forEach var="foodnutritionVO" items="${foodnutritionSvc.all}">
							<option value="${foodnutritionVO.food_no}"
								${(dietDetailVO.food_no == foodnutritionVO.food_no)? 'selected':''}>
								${foodnutritionVO.food_name},${foodnutritionVO.ref_amount},
								卡路里:${foodnutritionVO.ref_cal}</option>
						</c:forEach>
				</select></td><td><a href="<%=request.getContextPath()%>/front-end/foodnutrition/addFood.jsp" Target="_blank">找不到食物?</a></td>
			</tr>

			<tr>
				<td>食用分量:</td>

				<td><input type="number" name="amount" min="0" step="0.1" id="amount"
					value="<%=(dietDetailVO == null) ? "" : dietDetailVO.getAmount()%>" required /></td>
					
			</tr>
			
			<tr><td>
			<input type="hidden" name="diet_no" value="${diet_no}"> 
			<input type="hidden" name="action" value="insert"> 
			<input type="submit" value="新增">
			</td></tr>
			
		</table>
		
	</FORM>
</body>
<script type="text/javascript">
	$(document).ready(function() {
		  $(".myselect").select2();
	});
  
</script>
</html>