<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.dietdetail.model.*"%>
<%@ page import="java.util.*"%>
<%
	String diet_no = request.getParameter("diet_no");
	pageContext.setAttribute("diet_no", diet_no);
	List<DietDetailVO> list = (List) request.getAttribute("list");
	session.setAttribute("list", list);
%>
<jsp:useBean id="foodnutritionSvc" class="com.foodnutrition.model.FoodNutritionService" />
<html>
<head>
<title>飲食資料 - listOneDetail.jsp</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/js/vendors/bootstrap/css/bootstrap.min.css">
<link rel="icon" href="<%=request.getContextPath()%>/picture/logo.png">


<style>

table#detail{
width:35rem;
margin:3rem auto;
}
</style>

</head>
<body>

	

	<table id="detail">
	<tr><td colspan="2"><h3>食物紀錄</h3></td></tr>
		<tr>
			<th>紀錄編號</th>
			<th>食物名稱</th>
			<th>每份熱量</th>
			<th>食用份數</th>
			<th>總卡洛里</th>
			<th>修改</th>
			<th>刪除</th>
		</tr>
		<c:forEach var="dietDetailVO" items="${list}">
			<tr>
				<td>${dietDetailVO.diet_no}</td>
				<td>${foodnutritionSvc.getOneFood(dietDetailVO.food_no).food_name}</td>
				<td>${foodnutritionSvc.getOneFood(dietDetailVO.food_no).ref_cal}</td>
				<td><input type="number" value=${dietDetailVO.amount} disabled="true" id="amount"  min="0" max="10" step="0.1" required></td>
				<td>${dietDetailVO.food_cal}</td>
				<td>

						<input type="submit" value="修改" class="editFood" id="edit"> 
						<input type="hidden" id="diet_no" value="${dietDetailVO.diet_no}">
						<input type="hidden" id="food_no" value="${dietDetailVO.food_no}"> 

				</td>
				<td>
					<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/dietdetail/dietdetail.do"
						style="margin-bottom: 0px;">
						<input type="submit" value="刪除"> 
						<input type="hidden" name="diet_no" value="${dietDetailVO.diet_no}">
						<input type="hidden" name="food_no" value="${dietDetailVO.food_no}"> 
						<input type="hidden" name="action" value="delete">
					</FORM>
				</td>
			</tr>
		</c:forEach>
		
		 <jsp:include page="/front-end/dietdetail/addDietDetail_de.jsp">
		 	<jsp:param name="diet_no" value="${diet_no}" />
		 </jsp:include>
				
	</table>

<script src="<%=request.getContextPath()%>/js/vendors/jquery/jquery-3.4.1.min.js"></script>
<script src="<%=request.getContextPath()%>/js/vendors/popper/popper.min.js"></script>
<script src="<%=request.getContextPath()%>/js/vendors/bootstrap/js/bootstrap.min.js"></script>
<script>
$('#detail').on('click','.editFood',function(){
	$(this).parent().parent().find('#amount').attr('disabled', false);
	$(this).val("確認");
	$(this).removeClass("editFood").addClass("updated");
});

 $('#detail').on('click','.updated',function(){
		var amount =$(this).parent().parent().find('#amount').val();
		var diet_no =$(this).parent().find('#diet_no').val();
		var food_no =$(this).parent().find('#food_no').val();
		$(this).parent().parent().find('#amount').attr('disabled', true);


		
					$.ajax({
						type: "POST",
						url: "<%=request.getContextPath()%>/dietdetail/dietdetail.do",
						data: {
							"action" : "update",
							"amount" : amount,
							"diet_no" : diet_no,
							"food_no" : food_no,							
						},
						datatype : "json",
						success: function(data){
							$(this).removeClass("updated").addClass("editFood");
							$(this).val("修改");
							window.location.reload();
						}
					});	
});	
</script>
</body>
</html>