<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.exerciserecord.model.*"%>

<%
//ExerciseRecordVO exerciseRecordVO = (ExerciseRecordVO) request.getAttribute("exerciseRecordVO");

%>

<html>
<head>

<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
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




	<FORM METHOD="post" name="form1">
		<table id='myExeRecord'>

		<jsp:useBean id="execiseItemSvc" scope="page" class="com.exerciseitem.model.ExerciseItemService" />	
			<tr>
				<td>運動項目編號:</td>
				<td><select size="1" name="exe_no" class="js-example-basic-single" id="exe_no">
						<c:forEach var="exerciseItemVO" items="${execiseItemSvc.all}">
							<option value="${exerciseItemVO.exe_no}"
								${(exerciseItemVO.exe_no == param.exe_no)?'selected':'' }>
								${exerciseItemVO.exe_item},${exerciseItemVO.exe_cal}
							</option>
						</c:forEach>
				</select></td>
			</tr>

			<tr>
				<td>運動日期:</td>
				<td><input type="text" name="exe_date"  id="exe_date" disabled/></td>
			<td>${errorMsgs.exe_date}</td>
			</tr>
			<tr>
				<td>運動時間</td>
				<td><input type="number" min="0" name="exe_time" id="exe_time"
					value="${param.exe_time}" required/></td>
			<td>${errorMsgs.exe_time}</td>
			</tr>
			
			<tr>
				<td>總消耗熱量</td>
				<td>
				<input type="number" name="exe_tcal" id='exe_tcal'
					value="${param.exe_tcal}" required/></td>
			</td><td>${errorMsgs.exe_tcal}</td></tr>

		</table>
		<br> 
		<input type="hidden" name="member_no"  id="member_no" value="${member_no}" />
		<input type="hidden" name="action" value="insert"> 
		<input type="submit" value="送出新增" id="submitExe">
	</FORM>

	<a href="<%=request.getContextPath()%>/front-end/exerciseitem/addExe.jsp" target="_blank">找不到運動?</a>
</body>


<script src="<%=request.getContextPath()%>/js/vendors/jquery/jquery-3.4.1.min.js"></script>
<script src="<%=request.getContextPath()%>/js/vendors/popper/popper.min.js"></script>
<script src="<%=request.getContextPath()%>/js/vendors/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/select2/dist/js/select2.min.js"></script>
<script>
	$(document).ready(function() {
		  $(".js-example-basic-single").select2();
	});
     
  	$('#exe_time').change(function(){
		let member_no = $('#member_no').val();
		let exe_time = $('#exe_time').val();
		let exe_no = $('#exe_no').val();
		let exe_date = $('#exe_date').val();
			
		$.ajax({
			type: "POST",
			url: "<%=request.getContextPath()%>/exerciserecord/exerciserecord.do",
			data: "action=getCal&exe_time="+exe_time+"&member_no="+member_no+"&exe_no="+exe_no,
			datatype : "json",
			success: function(data){
				var data = JSON.parse(data);
 					$('#exe_tcal').empty();
					$("#exe_tcal").val(data.cal);
			}		
		});
	});
  	
  	$('#exe_no').change(function(){
		let member_no = $('#member_no').val();
		let exe_time = $('#exe_time').val();
		let exe_no = $('#exe_no').val();
		let exe_date = $('#exe_date').val();
			
		$.ajax({
			type: "POST",
			url: "<%=request.getContextPath()%>/exerciserecord/exerciserecord.do",
			data: "action=getCal&exe_time="+exe_time+"&member_no="+member_no+"&exe_no="+exe_no,
			datatype : "json",
			success: function(data){
				var data = JSON.parse(data);
 					$('#exe_tcal').empty();
					$("#exe_tcal").val(data.cal);
			}
		});
	});

  	
  	$('#submitExe').click(function(){
  		
  		
		let member_no = $('#member_no').val();
		let exe_time = $('#exe_time').val();
		let exe_no = $('#exe_no').val();
		let exe_date = $('#exe_date').val();
		let exe_tcal = $('#exe_tcal').val();
		if(exe_date !='' && exe_tcal !='' && exe_time !=''){	
		$.ajax({
			type: "POST",
			url: "<%=request.getContextPath()%>/exerciserecord/exerciserecord.do",
			data: {
				"action" : "insert",
				"member_no" : member_no,
				"exe_time" : exe_time,
				"exe_no" : exe_no,
				"exe_date" : exe_date,
				"exe_tcal" : exe_tcal
			},
			datatype : "json",
			success: function(data){
				var data = JSON.parse(data);
			if(data.success === 'Y')
			
 				alert("新增成功");	   
			window.location.href = "<%=request.getContextPath()%>/front-end/posture/posture.jsp";
			
			}
		});
		}
  	});
</script>
</html>