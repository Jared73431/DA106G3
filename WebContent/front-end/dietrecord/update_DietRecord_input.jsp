<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.members.model.*"%>
<%@ page import="com.dietrecord.model.*"%>
<% String member_no = (String)session.getAttribute("member_no");
	MembersService membersSvc = new MembersService();
	MembersVO membersVO = membersSvc.getOneMembers(member_no);
	pageContext.setAttribute("membersVO",membersVO); 
	
	DietRecordVO dietRecordVO = (DietRecordVO) request.getAttribute("dietRecordVO");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>修改食物紀錄</title>
 <link rel="stylesheet" href="<%=request.getContextPath()%>/js/vendors/bootstrap/css/bootstrap.min.css">
 <link rel="stylesheet" href="<%=request.getContextPath()%>/js/sweetalert/sweetalert2.css" />

<style>

img {
	max-width: 500px;
}

div#record_container{
margin-top:3rem;
margin-bottom:3rem;
height:auto;
min-width:80%;
}


#record_row{
margin-left:-15px;
margin-right:-15px;

}
table{
margin:2rem;
}

tr{
height:2.5rem;
}

div#food_left{
padding:2rem;
border-radius: 120px;
border: dashed gray;
}

td{
min-width:6rem;
}
</style>





</head>
<body>
<%@ include file="/front-end/homepage/nav.file"%>
<div id="listall">

	<div class="container" id="record_container">
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
	    <c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>
		<div class="row" id="record_row">
		
			<div class="col-md-7" id="food_left">
				<jsp:include page="/dietdetail/dietdetail.do">
					<jsp:param name="diet_no" value="${dietRecordVO.diet_no}" />
					<jsp:param name="action" value="getOne_For_Display" />
				</jsp:include>		
			</div>	
		
		<div class="col-md-5 " id="food_right">
			<c:if test="${not empty errorMsgs}">
					<font style="color: red">請修正以下錯誤:</font>
					<ul>
						<c:forEach var="message" items="${errorMsgs}">
							<li style="color: red">${message}</li>
						</c:forEach>
					</ul>
				</c:if>
			
				<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/dietrecord/dietrecord.do" name="form1"
					enctype="multipart/form-data">
					<table>
						
						<tr>
							<th>紀錄日期:</th>
							<td><%=dietRecordVO.getRec_date()%></td>
						</tr>
			
						<tr>
							<th>紀錄時段:</th>
							<td>${period[dietRecordVO.eat_period]}</td>
						</tr>
			
						<tr>
							<th>照片</th>
							<td>
								<input type="file" name="photo" id="photo" onchange="loadFile(event)">
								<input type="hidden" name="diet_no" id="diet_no" value="<%=dietRecordVO.getDiet_no()%>">
								<input type="button" value="刪除照片" id="btnDelete">
							</td>
						</tr>
						<tr>
							<td></td><td><img src='<%=request.getContextPath() %>/showdiet.do?diet_no=${dietRecordVO.diet_no}' id="foodpic" /></td>
						</tr>
					</table>
					<br> <input type="hidden" name="action" value="update"> 
						<input type="hidden" name="diet_no" value="<%=dietRecordVO.getDiet_no()%>">
						<input type="hidden" name="member_no" value="<%=dietRecordVO.getMember_no()%>"> 	
						<input type="hidden" name="rec_date" value="<%=dietRecordVO.getRec_date()%>">
						<input type="hidden" name="eat_period" value="<%=dietRecordVO.getEat_period()%>"> 		
						<center><input type="submit" value="送出修改" id="btnUpdate"></center>
					
				
				</FORM>

		</div>
		
		
	</div>
</div>
</div>


<%@ include file="/front-end/homepage/footer.file"%>

<script src="<%=request.getContextPath()%>/js/vendors/jquery/jquery-3.4.1.min.js"></script>
<script src="<%=request.getContextPath()%>/js/vendors/popper/popper.min.js"></script>
<script src="<%=request.getContextPath()%>/js/vendors/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/js/sweetalert/sweetalert2.js" type="text/javascript"></script>

<script>

$('#btnUpdate').click(function(){
	alert("資料修改!");
})

var loadFile = function(event) {
    var reader = new FileReader();
    reader.onload = function(){
      var output = document.getElementById('foodpic');
      output.src = reader.result;
    };
    reader.readAsDataURL(event.target.files[0]);
  };
 
$('#btnDelete').click(function(){
		
		var diet_no =$(this).parent().find('#diet_no').val();

					$.ajax({
						type: "POST",
						url: "<%=request.getContextPath()%>/dietrecord/dietrecord.do",
						data: {
							"action" : "delete_pic",
							"diet_no" : diet_no,
						},
						datatype : "json",
						success: function(data){
							var data = JSON.parse(data);							
							if(data.success === 'Y')
								alert("照片刪除!");	   
								window.location.reload();
						}						
					});	
});	
  
</script>
</body>
</html>