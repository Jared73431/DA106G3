<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.members.model.*"%>
<%@ page import="com.groupgo.model.*"%>
<%
String member_no = (String)session.getAttribute("member_no");
MembersService membersSvc = new MembersService();
MembersVO membersVO = membersSvc.getOneMembers(member_no);
pageContext.setAttribute("membersVO",membersVO); 
pageContext.setAttribute("membersSvc",membersSvc); 

GroupgoVO groupgoVO = (GroupgoVO) request.getAttribute("groupgoVO");

%>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/js/vendors/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/datetimepicker/jquery.datetimepicker.css" />
<link rel="icon" href="<%=request.getContextPath()%>/picture/logo.png">
<title>揪團資料修改</title>

<style>
img {
	max-width: 400px;
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


div#listallgroup{
	margin-left: 30rem;
	margin-top: 5rem;
	margin-bottom:5rem;
}
tr{
height:2rem;
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
       		<a href="<%=request.getContextPath()%>/mygroup/mygroup.do?action=getMemberAttend"><p id="myGroupText">管理揪團</p></a>
</div>

</div>
<div id="listallgroup">

	
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

	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/groupgo/groupgo.do" name="form1"
		enctype="multipart/form-data">
		<table>
			<tr>
				<th>揪團編號:</th>
				<td><%=groupgoVO.getGroupgo_id()%></td>
			</tr>
			<tr>
				<th>團主:</th>
				<td><%=membersSvc.getOneMembers(groupgoVO.getMaster_id()).getKnown()%></td>
			</tr>
			<tr>
				<th>揪團名稱:</th>
				<td><%=groupgoVO.getGroup_name()%></td>
			</tr>

			<tr>
				<th>活動日期:</th>
				<td><input type="text" name="group_date" id="group_date"
					value="<%=(groupgoVO == null) ? "" : groupgoVO.getGroup_date()%>" /></td>
			</tr>
			<tr>
				<th>報名截止日：</th>
				<td><input type="text" name="deadline" id="deadline"
					value="<%=(groupgoVO == null) ? "" : groupgoVO.getDeadline()%>" /></td>
			</tr>

			<tr>
				<th>活動地址：</th>
				<td><div id="twzipcode"></div></td>
			</tr>
			<td></td>
			<td><input type="text" size="40" name="place" id="place"
				required
				value="<%=(groupgoVO == null) ? "揪團地點" : groupgoVO.getPlace()%>" /></td>
			</tr>

			<tr>
				<th>活動人數：</th>
				<td><input type="number" name="people_num" id="people_num"
					value="<%=(groupgoVO == null) ? "10" : groupgoVO.getPeople_num()%>"/></td>
			</tr>
			<jsp:useBean id="mygroupSvc" class="com.mygroup.model.MygroupService" />
			<tr>
				<th>目前團員：</th>
			<td><%=membersSvc.getOneMembers(groupgoVO.getMaster_id()).getKnown()%>
			<c:forEach var="myGroupVO" items="${mygroupSvc.getGroupAttend(groupgoVO.groupgo_id)}">
			, ${membersSvc.getOneMembers(myGroupVO.member_no).known}
			</c:forEach>
			</td>
			</tr>
			
			<tr>
				<th>活動預算：</th>
				<td><input type="text" name="budget" id="budget"
					value="<%=(groupgoVO == null) ? "200" : groupgoVO.getBudget()%>" /></td>
			</tr>
			<tr>
				<th>狀態：</th>
				<td>
				<select size="1" name="group_status">
						<option value="1" ${(groupgoVO.group_status == '1') ? 'selected' :''}>揪團中</option>
						<option value="2" ${(groupgoVO.group_status == '2') ? 'selected' :''}>提早成團</option>
				</select>
				</td>
			</tr>
			<tr>
				<th>照片</th>
				<td>
					<input type="file" name="photo" id="photo" onchange="loadFile(event)">
						<div id="groupPic">
							<img src='<%=request.getContextPath() %>/showgroupgo.do?groupgo_id=${groupgoVO.groupgo_id}' id="groupPhoto" />
						</div>
				</td>
			</tr>

			<tr>
				<th>內容：</th>
				<td><textarea name="group_content"><%=(groupgoVO == null) ? "" : groupgoVO.getGroup_content()%></textarea></td>
			</tr>
		</table>
		<br> <input type="hidden" name="action" value="update"> 
			<input type="hidden" name="groupgo_id" value="<%=groupgoVO.getGroupgo_id()%>"> 
			<input type="hidden" name="master_id" value="<%=groupgoVO.getMaster_id()%>"> 
			<input type="hidden" name="group_name" value="<%=groupgoVO.getGroup_name()%>">
			<input type="submit" value="送出修改"  class="btn-outline-secondary"  id="submitbtn">
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
	
	        width:['650px']
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
					$("#place").val(
							$('#twzipcode').twzipcode('get', 'county')
									+ $('#twzipcode').twzipcode('get',
											'district'));
				});

		$.datetimepicker.setLocale('zh');
		$(function(){
			$('#group_date').datetimepicker({
				theme : '', //theme: 'dark',
				step : 30, //step: 60 (這是timepicker的預設間隔60分鐘)
				format : 'Y-m-d H:i:0', //format:'Y-m-d H:i:s',
				onShow:function(){
					   this.setOptions({
				minDate : $('#deadline').val()? $('#deadline').val():false
					   })
				  },
				  timepicker : true //timepicker:true
			});

			
			$('#deadline').datetimepicker({
				theme : '', //theme: 'dark',
				step : 60, //step: 60 (這是timepicker的預設間隔60分鐘)
				format : 'Y-m-d H:0:0', //format:'Y-m-d H:i:s',
				minDate : new Date(),
				onShow:function(){
					   this.setOptions({
				maxDate : $('#group_date').val()? $('#group_date').val():false
					   })
				  },
				 timepicker : true
			});
			});
	</script>

</body>

</html>