<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="com.members.model.*"%>
  <%
String member_no = (String)session.getAttribute("member_no");
MembersService membersSvc = new MembersService();
MembersVO membersVO = membersSvc.getOneMembers(member_no);
pageContext.setAttribute("membersVO",membersVO); 
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="BIG5">
<title>Insert title here</title>
</head>
<body>
<%@ include file="/front-end/homepage/nav.file"%>


----------------------------------------------



<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/members/members.do" name="form1" enctype="multipart/form-data">
<table>
	

	<tr>
	<td>上傳證照</td>
	<td>
		<input type="file" name="upfile2" onchange="loadFile2(event)">
		<div id="output2"></div>
	</td>
	</tr>
	<tr>
	<td>
				<input class="btn btn-danger" type="submit" value="申請教練" id="update"> 
				<input type="hidden" name="member_no" value="${member_no}">
				<input type="hidden" name="action" value="applyCoach">
		</td>
		</tr>
	
</table>

	<script type="text/javascript">
	var loadFile2 = function(event){
		var reader = new FileReader();
		reader.onload = function(){
			var output2 = document.getElementById('output2');
			output2.innerHTML = "<img width='100' id ='preview2'>";
			document.getElementById("preview2").src = reader.result;
		};
		reader.readAsDataURL(event.target.files[0]);
	}
	</script>
</FORM>
</body>
</html>