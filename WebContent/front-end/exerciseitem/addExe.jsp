<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.members.model.*"%>
<%@ page import="com.exerciseitem.model.*"%>
<% String member_no = (String)session.getAttribute("member_no");
	MembersService membersSvc = new MembersService();
	MembersVO membersVO = membersSvc.getOneMembers(member_no);
	pageContext.setAttribute("membersVO",membersVO); 
	
	ExerciseItemVO exerciseItemVO = (ExerciseItemVO) request.getAttribute("exerciseItemVO");
%>

<html>
<head>
<meta charset="UTF-8">
<title>新增運動項目</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/js/vendors/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/js/sweetalert/sweetalert2.css" />
<link rel="icon" href="<%=request.getContextPath()%>/picture/logo.png">
<style>

#addExeitem{
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


<div id ="addExeitem">
	

	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/exerciseitem/exerciseitem.do" name="form1">
		<table>
<tr></td colspan="2"><h3><b>新增運動項目</b></h3></td></tr>
			<tr>
				<th>運動項目:</th>
				
				<td><input type="TEXT" name="exe_item" 
					value="<%=(exerciseItemVO == null) ? "" : exerciseItemVO.getExe_no()%>" required/></td>
			</tr>
			<tr>
				<th>消耗熱量(每公斤每30分鐘):</th>
				<td><input type="number" name="exe_cal"
					value="<%=(exerciseItemVO == null) ? "" : exerciseItemVO.getExe_cal()%>" required></td>
			</tr>

		</table>
		<br> <center><input type="hidden" name="action" value="insert" > <input
			type="submit" value="送出新增" id="exeInsert"></center>
	</FORM>
	</div>	
</div>
<%@ include file="/front-end/homepage/footer.file"%>

<script src="<%=request.getContextPath()%>/js/vendors/jquery/jquery-3.4.1.min.js"></script>
<script src="<%=request.getContextPath()%>/js/vendors/popper/popper.min.js"></script>
<script src="<%=request.getContextPath()%>/js/vendors/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/js/sweetalert/sweetalert2.js" type="text/javascript"></script>


<script>

if(${exeSuccess} != null){
	swal("新增完成!","", "success");	
}

</script>
</body>
</html>