<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.dietrecord.model.*"%>

<%
	DietRecordVO dietRecordVO = (DietRecordVO) request.getAttribute("dietRecordVO");
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>紀錄新增 - adddietRecord.jsp</title>
<link rel="icon" href="<%=request.getContextPath()%>/picture/logo.png">
<style>
body{
  font-family: "Fira Mono", monospace, microsoft jhengHei;
}
img#foodpic {
	max-width: 300px;
}


div#right{
display:inline-block;
margin:50px;
width:600px;
}

div#left{
float:left;
display:inline-block;
margin:50px;
width:600px;
}

</style>

</head>
<body bgcolor='white'>

<div id="left">	
	 <jsp:include page="/front-end/dietdetail/addDietDetail_re.jsp" />
	 
	
</div>

<div id="right">

	<%-- 錯誤表列 --%>
<%-- 	<c:if test="${not empty errorMsgs}"> --%>
<!-- 		<font style="color: red">請修正以下錯誤:</font> -->
<!-- 		<ul> -->
<%-- 			<c:forEach var="message" items="${errorMsgs}"> --%>
<%-- 				<li style="color: red">${message}</li> --%>
<%-- 			</c:forEach> --%>
<!-- 		</ul> -->
<%-- 	</c:if> --%>

	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/dietrecord/dietrecord.do" name="form1" enctype="multipart/form-data">
		<table>
			<jsp:useBean id="membersSvc" scope="page" class="com.members.model.MembersService" />

			<tr>
				<td>飲食時段:</td>
				<td><select size="1" name="eat_period">
						<option value="0" ${(0 == dietRecordVO.eat_period)?'selected':'' }>早餐</option>
						<option value="1" ${(1 == dietRecordVO.eat_period)?'selected':'' }>午餐</option>
						<option value="2" ${(2 == dietRecordVO.eat_period)?'selected':'' }>晚餐</option>
						<option value="3" ${(3 == dietRecordVO.eat_period)?'selected':'' }>其他</option>
				</select></td>
			</tr>

			<tr>
				<td>日期:</td>
				<td><input type="text" name="rec_date"  id="rec_date" readonly="readonly"/></td><td>${errorMsgs.rec_date}</td>

			</tr>
			<tr>
				<td>照片</td>
				<td><input type="file" name="photo" id="photo" onchange="loadFile(event)">

						<img src='' id="foodpic" />

			</tr>
			
			<tr> <td>
			<input type="submit" value="送出新增">
			<input type="hidden" name="member_no" value="${member_no}"> 
			<input type="hidden" name="action" value="insert2"> 
				</td>
			</tr>

		</table>		
	</FORM>
</div>
<script src="<%=request.getContextPath()%>/js/vendors/jquery/jquery-3.4.1.min.js"></script>
<script src="<%=request.getContextPath()%>/js/vendors/popper/popper.min.js"></script>
<script src="<%=request.getContextPath()%>/js/vendors/bootstrap/js/bootstrap.min.js"></script>
	<script>
	
	 var loadFile = function(event) {
		    var reader = new FileReader();
		    reader.onload = function(){
		      var output = document.getElementById('foodpic');
		      output.src = reader.result;
		    };
		    reader.readAsDataURL(event.target.files[0]);
		  };

		
	</script>
</body>