<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.members.model.*"%>
<%@ page import="com.groupgo.model.*"%>

<% String member_no = (String)session.getAttribute("member_no");
	MembersService membersSvc = new MembersService();
	MembersVO membersVO = membersSvc.getOneMembers(member_no);
	pageContext.setAttribute("membersVO",membersVO);
	GroupgoVO groupgoVO = (GroupgoVO) request.getAttribute("groupgoVO");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新增揪團</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/js/vendors/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/datetimepicker/jquery.datetimepicker.css" />
<link rel="icon" href="<%=request.getContextPath()%>/picture/logo.png">
<style>

img#groupPhoto {
	max-width: 300px;
	
}



table { 
 	width: 800px;
 	background-color: white;
 	margin-top: 1px; 
 	margin-bottom: 1px;
 } 

 table, th, td { 
 	border: 0px solid #CCCCFF; 
 } 
 th, td { 
 	padding: 1px; 
 } 
 th{ 
  width:110px; 
  text-align:left; 
 } 
tr{
height:2rem;
}

div#listallgroup{
	margin-left: 30rem;
	margin-top: 5rem;
	margin-bottom:5rem;
}

div.row{
margin-top:5px;
}

div#myGroup,div#groupgoIndex{

margin-top:1.1rem;
max-width:7rem;
padding-left: 0.6rem;
padding-top:0.3rem;
height: 2.8rem;
background-color:#E0DBDB;
display:block;

}


p#myGroupText,p#groupText{
font-size:1.2rem;
color:black;
margin-left:0.4rem;
margin-top:0.4rem;
font-weight:bold;
}


div#left{
width:8rem;
display:inline-block;
float:left;
position:relative;
top:10%;
left:3%;
}


input#submitbtn{
position:relative;
left:12rem;
margin:1rem;
}
</style>

</head>
<body>

<%@ include file="/front-end/homepage/nav.file"%>
<div id="listall">


<div id="left">
<div id="groupgoIndex">	
       		<a href="<%=request.getContextPath()%>/front-end/groupgo/listAllGroupgo.jsp"><p id="groupText">揪團首頁</p></a>
</div>


<div id="myGroup">	
       		<a href="<%=request.getContextPath()%>/mygroup/mygroup.do?action=getMemberAttend&member_no=${member_no}"><p id="myGroupText">管理揪團</p></a>
</div>

</div>

<div id="listallgroup">
	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/groupgo/groupgo.do" name="form1"
		enctype="multipart/form-data" >
		<table>

			<tr>
				<th>標題：</th>
				<td><input type="text" name="group_name" id="group_name"
					value="<%=(groupgoVO == null) ? "" : groupgoVO.getGroup_name()%>" /></td>
			</tr>
			<tr>
				<th>活動日期：</th>
				<td><input type="text" name="group_date" id="group_date" /></td>
			</tr>
			<tr>
				<th>報名截止日：</th>
				<td><input type="text" name="deadline" id="deadline" /></td>
			</tr>

			<tr>
				<th>活動地點：</th>
				<td><div id="twzipcode"></div></td>
			</tr>
			<td></td>
			<td><input type="text" size="40" name="place" id="place"
				required
				value="<%=(groupgoVO == null) ? "" : groupgoVO.getPlace()%>" /></td>
			</tr>

			<tr>
				<th>活動人數：</th>
				<td><input type="number" name="people_num" id="people_num"
					value="<%=(groupgoVO == null) ? "" : groupgoVO.getPeople_num()%>" /></td>
			</tr>
			<tr>
				<th>活動預算：</th>
				<td><input type="text" name="budget" id="budget"
					value="<%=(groupgoVO == null) ? "" : groupgoVO.getBudget()%>" /></td>
			</tr>
			<tr>
				<th>照片：</th>
				<td>
					<input type="file" name="photo" id="photo" onchange="loadFile(event)">
						<div id="groupPic">
							<img src='' id="groupPhoto" />
						</div>
				</td>
			</tr>

			<tr>
				<th>內容：</th>
				<td><textarea name="group_content"> <%=(groupgoVO == null) ? "" : groupgoVO.getGroup_content()%></textarea></td>
			</tr>
		</table>
		<br> 
		<input type="hidden" name="action" value="insert"> 
		<input type="hidden" name="master_id" value="${member_no}">
		<input type="submit" id="submitbtn" value="送出新增" class="btn-outline-secondary">
		
		<input type="button" id="magic" value="神奇小按鈕" class="btn-outline-secondary">
	</FORM>
	</div>


</div>
<%@ include file="/front-end/homepage/footer.file"%>


<script src="<%=request.getContextPath()%>/js/vendors/jquery/jquery-3.4.1.min.js"></script>
<script src="<%=request.getContextPath()%>/js/vendors/popper/popper.min.js"></script>
<script src="<%=request.getContextPath()%>/js/vendors/bootstrap/js/bootstrap.min.js"></script>

<script src="<%=request.getContextPath()%>/js/glDatePicker/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/js/jQuery-TWzipcode-master/jquery.twzipcode.min.js"></script>
<script src="<%=request.getContextPath()%>/js/ckeditor/ckeditor.js"></script>

<script src="<%=request.getContextPath()%>/js/datetimepicker/jquery.datetimepicker.full.js"></script>
<script>

CKEDITOR.replace( 'group_content', {
    uiColor: '#BDB2B2',
    width:['650px'],
	resize_enabled:false
});



var loadFile = function(event) {
    var reader = new FileReader();
    reader.onload = function(){
      var output = document.getElementById('groupPhoto');
      output.src = reader.result;
    };
    reader.readAsDataURL(event.target.files[0]);
 };
  
  
$('#twzipcode').twzipcode({
	zipcodeIntoDistrict : true
});
$("#twzipcode").click(
		function() {
			$("#place").val($('#twzipcode').twzipcode('get', 'county')+ $('#twzipcode').twzipcode('get','district'));
		});

$.datetimepicker.setLocale('zh');
$(function(){
	$('#group_date').datetimepicker({
		theme : '',
		step : 30, 
		format : 'Y-m-d H:i:00', 
		value: new Date(),
		
		onShow:function(){
			   this.setOptions({
		minDate : $('#deadline').val()? $('#deadline').val():new Date()
			   })
		  },
		  timepicker : true 
	});

	$('#deadline').datetimepicker({
		theme : '', 
		step : 60, 
		format : 'Y-m-d H:00:00', 
		value: new Date(),
		minDate : new Date(),
		onShow:function(){
			   this.setOptions({
		maxDate : $('#group_date').val()?$('#group_date').val():false
			   })
		  },
		 timepicker : true
	});
	});

$('#magic').click(function(){
	$('#group_name').val("一起去抓寶");
	$('#group_date').val("2020-05-20 18:00:00");
	$('#deadline').val("2020-05-15 11:00:00");
	$('#place').val("新北市三重區疏洪五路二段2791號");
	$('#people_num').val("3");
	$('#budget').val("行動電源很重要");

});

</script>
</body>
</html>