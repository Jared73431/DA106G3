<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.dietdetail.model.*"%>
<%@ page import="com.dietrecord.model.*"%>

<%@ page import="java.util.*"%>


<%
	DietDetailVO dietDetailVO = (DietDetailVO) request.getAttribute("dietDetailVO");
	DietRecordVO dietRecordVO = (DietRecordVO) request.getAttribute("dietRecordVO");
	List dietdetailList = new ArrayList();
	dietdetailList = (List)session.getAttribute("dietdetailList");

%>
<html>
<head>
<meta http-equiv="Content-Type" content="IE=edge,chrome=1" />
<link href="<%=request.getContextPath()%>/js/select2/dist/css/select2.min.css" rel="stylesheet" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/js/vendors/bootstrap/css/bootstrap.min.css">
<link rel="icon" href="<%=request.getContextPath()%>/picture/logo.png">

  
<style>
body{
  font-family: "Fira Mono", monospace, microsoft jhengHei;
}
</style>

</head>
<body bgcolor='white'>


	<h3>先新增飲食內容</h3>
	
	

	<table id="foodDetail">
			
			<jsp:useBean id="foodnutritionSvc" scope="page"
				class="com.foodnutrition.model.FoodNutritionService" />

			
			
				
				<td><select name="food_no" class="myselect" style="width:300px;" id="food_no">
						<c:forEach var="foodnutritionVO" items="${foodnutritionSvc.all}">
							<option value="${foodnutritionVO.food_no}"
								${(dietDetailVO.food_no == foodnutritionVO.food_no)? 'selected':''}>
								${foodnutritionVO.food_name},${foodnutritionVO.ref_amount},
								卡路里:${foodnutritionVO.ref_cal}</option>
						</c:forEach>
				</select></td>

				<td><input type="number" name="amount" min="0" step="0.1" id="amount" required="required"/></td>
			
				
				
				<td>
					<input type="submit" value="新增"  id="addDetail"> 
				</td>
			</tr>
			<tr><td><a href="<%=request.getContextPath()%>/front-end/foodnutrition/addFood.jsp" Target="_blank">找不到食物?</a></td></tr>
			
			<tr id="myfood"><th>食物名稱</th><th>食用分量</th><th>熱量</th><th></th></tr>
		

	</table>

		
	
</body>
<script src="<%=request.getContextPath()%>/js/vendors/jquery/jquery-3.4.1.min.js"></script>
<script src="<%=request.getContextPath()%>/js/vendors/popper/popper.min.js"></script>
<script src="<%=request.getContextPath()%>/js/vendors/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/select2/dist/js/select2.min.js"></script>

<script type="text/javascript">
  	$(document).ready(function() {
		  $(".myselect").select2();
	});
    
      
      $('#addDetail').click(function(){
  		let food_no = $('#food_no').val();
  		let amount = $('#amount').val();
  		
  		if(amount !=''){
  			
  		$.ajax({
  			type: "POST",
  			url: "<%=request.getContextPath()%>/dietdetail/dietdetail.do",
  			data: {
  				"action" : "add",
  				"food_no" : food_no,
  				"amount" : amount,			
  			},
  			datatype : "json",
  			success: function(data){
  				$('.myFoodtr').remove();
  				var data = JSON.parse(data);
  				console.log(data);
  				$.each(data, function(index, element){
  					$("#myfood").after("<tr class='myFoodtr'><td>"+element.food_name+"</td><td>"+element.amount+"</td><td>"+element.food_cal+"</td><td><input type='hidden' value='"+element.food_no+"' class='deleteFood_no'>"				
  						+"<input type='hidden' value='"+element.amount+"' class='deleteAmount'>"
  						+"<input type='hidden' value='"+element.food_cal+"' class='deleteFood_cal'>"
  						+"<input type='submit' value='刪除' class='deleteDietDetail'></td></tr>");
  			});
  			}
  		});  
  		}
    });
    
      $('#foodDetail').on('click','.deleteDietDetail',function(){
    	  let food_no =$(this).parent().find('.deleteFood_no').val();	
    	  let amount =$(this).parent().find('.deleteAmount').val();	
    	  let food_cal =$(this).parent().find('.deleteFood_cal').val();	
    	  let click =$(this);

    	  $.ajax({
    			type: "POST",
    			url: "<%=request.getContextPath()%>/dietdetail/dietdetail.do",
    			data: {
    				"action" : "delete_de",
    				"food_no" : food_no,
    				"amount" : amount,
    				"food_cal" : food_cal,
    			},
    			datatype : "json",
    			success: function(data){
    				var data = JSON.parse(data);
    	  			console.log(data);
    	  			$('.myFoodtr').remove();
    	  			$.each(data, function(index, element){
    	  				$("#myfood").after("<tr class='myFoodtr'><td>"+element.food_name+"</td><td>"+element.amount+"</td><td>"+element.food_cal+"</td><td><input type='hidden' value='"+element.food_no+"' class='deleteFood_no'>"				
    	  						+"<input type='hidden' value='"+element.amount+"' class='deleteAmount'>"
    	  						+"<input type='hidden' value='"+element.food_cal+"' class='deleteFood_cal'>"
    	  						+"<input type='submit' value='刪除' class='deleteDietDetail'></td></tr>");
    	  			});
    	  			}
    		});  	
      });
      
</script>
</html>