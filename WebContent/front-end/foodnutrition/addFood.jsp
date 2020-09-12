<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.members.model.*"%>
<%@ page import="com.foodnutrition.model.*"%>
<% String member_no = (String)session.getAttribute("member_no");
	MembersService membersSvc = new MembersService();
	MembersVO membersVO = membersSvc.getOneMembers(member_no);
	pageContext.setAttribute("membersVO",membersVO); 

	FoodNutritionVO foodNutritionVO = (FoodNutritionVO) request.getAttribute("foodNutritionVO");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新增食物</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/js/vendors/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/js/sweetalert/sweetalert2.css" />
<link rel="icon" href="<%=request.getContextPath()%>/picture/logo.png">


<style>

table {
	width: 450px;
	
	margin:0rem 1rem;
}

#addFooditem{
	width:35rem;
	margin: 5rem auto;
	background-color:rgba(139,149,139,0.5);
	padding:5rem;
	border-radius: 100px;
	border: dotted gray;
}
tr{
height:2rem;
}

</style>

</head>
<body>
<%@ include file="/front-end/homepage/nav.file"%>
<div id="listall">

<div id ="addFooditem">

	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/foodnutrition/foodnutrition.do" name="form1">
		
		<table>
		<tr></td colspan="2"><h3><b>新增食物</b></h3></td></tr>
	<tr>
				<th>食物名稱:</th>
				<td><input type="text" name="food_name" required
					value="<%=(foodNutritionVO == null) ? "" : foodNutritionVO.getFood_name()%>" /></td>
			<tr>
				<th>食物品牌:</th>
				<td><input type="text" name="ref_brand" required
					value="<%=(foodNutritionVO == null) ? "" : foodNutritionVO.getRef_brand()%>" /></td>
			<tr>
				<th>分量單位:</th>
				<td><input type="text" name="ref_amount"  required
					value="<%=(foodNutritionVO == null) ? "" : foodNutritionVO.getRef_amount()%>" /></td>
			<tr>
				<th>每單位熱量:</th>
				<td><input type="number" name="ref_cal" step="0.01" required
					value="<%=(foodNutritionVO == null) ? "" : foodNutritionVO.getRef_cal()%>" /></td>
			<tr>
				<th>每單位蛋白質:</th>
				<td><input type="number" name="ref_protein" step="0.01"
					value="<%=(foodNutritionVO == null) ? "" : foodNutritionVO.getRef_protein()%>" /></td>
			<tr>
				<th>每單位脂肪:</th>
				<td><input type="number" name="ref_fat" step="0.01"
					value="<%=(foodNutritionVO == null) ? "" : foodNutritionVO.getRef_fat()%>" /></td>
			<tr>
				<th>每單位碳水化合物:</th>
				<td><input type="number" name="ref_cho" step="0.01"
					value="<%=(foodNutritionVO == null) ? "" : foodNutritionVO.getRef_cho()%>" /></td>
		</table>
		<br><center> <input type="hidden" name="action" value="insert" > 
		<input	type="submit" value="送出新增" id="foodInsert"></center>
	</FORM>
	</div>	
</div>
<%@ include file="/front-end/homepage/footer.file"%>

<script src="<%=request.getContextPath()%>/js/vendors/jquery/jquery-3.4.1.min.js"></script>
<script src="<%=request.getContextPath()%>/js/vendors/popper/popper.min.js"></script>
<script src="<%=request.getContextPath()%>/js/vendors/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/js/sweetalert/sweetalert2.js" type="text/javascript"></script>
<script>

if(${foodSuccess} != null){
	swal("新增完成!","", "success");	
}

</script>
</body>
</html>