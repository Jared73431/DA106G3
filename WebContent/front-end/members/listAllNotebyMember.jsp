<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.notificationlist.model.*"%>
<%@ page import="java.util.*"%>

<%@ page import="com.members.model.*"%>
    
    
<%
 MembersService memberSvc = new MembersService();
 String member_no = (String)session.getAttribute("member_no");
 MembersVO membersVO = memberSvc.getOneMembers(member_no);
 pageContext.setAttribute("membersVO", membersVO);
%>
<jsp:useBean id="listAllNotesByMember" scope="request" type="java.util.Set<NotificationListVO>" />
<!-- 於EL此行可省略 -->
<%-- <jsp:useBean id="memberSvc" scope="page" class="com.members.model.MembersService" /> --%>


<html>
<head>
<title>會員通知 </title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/js/vendors/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/js/lobibox/dist/css/lobibox.css" />
<style>
table#table-2 {
	background-color: #CCCCFF;
	border: 2px solid black;
	text-align: center;
}

table#table-2 h4 {
	color: red;
	display: block;
	margin-bottom: 1px;
}

h4 {
	color: blue;
	display: inline;
}
</style>

<style>
table {
	width: 800px;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 5px;
}

table, th, td {
	border: 1px solid #CCCCFF;
}

th, td {
	padding: 5px;
	text-align: center;
}
</style>

</head>
<body bgcolor='white' onload='connect()' onunload="disconnect()">
<%@ include file="/front-end/homepage/nav.file"%>
<%@ include file="/front-end/notificationlist/notesocket.jsp"%>
	<script
		src="<%=request.getContextPath()%>/js/vendors/jquery/jquery-3.1.0.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/js/lobibox/dist/js/lobibox.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/js/lobibox/dist/js/notifications.min.js"></script>
	<%-- 錯誤表列 --%>
	
	<div id="listall">
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>
<div style="visibility:hidden" id="memberno"><c:out value="${listAllNotesByMember.toArray()[0].member_no}"/></div>
<%-- 	<c:forEach var="noteVO" items="${listAllNotesByMember}"> --%>
<!-- 	<h1 id="memberno"> -->
<%-- 	<c:out value="${listAllNotesByMember.toArray()[0].member_no}"/> --%>
<!-- 	</h1> -->
<%-- 	</c:forEach> --%>
	<div class="container">
  <div class="row justify-content-md-center">
    <div class="col col-lg-12">
	<table align="center" width="80%" class="table table-striped"  >
	 <thead>
		<tr>
			<th scope="col">狀態</th>
			<th scope="col">通知類別</th>
			<th scope="col">通知日期</th>
			<th scope="col">點我讀取</th>
			<th scope="col">刪除</th>
		</tr>
	</thead>
	 <tbody>
		<c:forEach var="noteVO" items="${listAllNotesByMember}">
			<tr ${(noteVO.note_no==param.note_no) ? 'bgcolor=#CCCCFF':''}>
				<!--將修改的那一筆加入對比色-->
				<jsp:useBean id="cateSvc" scope="page" class="com.categorys.model.CategorysService" />
				<td scope="row"><c:forEach var="status" items="${note_status}">
					<c:if test="${(status.key == noteVO.note_status)}">
					 	${status.value}
					</c:if>
					</c:forEach>
				</td>
				<td><a href="<%=request.getContextPath() %>/notificationlist/notificationlist.do?action=getOne_For_Display&note_no=${noteVO.note_no}">
				${cateSvc.getOneCategorys(noteVO.category_no).category_name }
				</td>
				<td>${noteVO.note_date}</td>
				<td>
					<FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/notificationlist/notificationlist.do"
						style="margin-bottom: 0px;">
						<input type="submit" value="讀取"> 
						<input type="hidden" name="note_no" value="${noteVO.note_no}"> 
						<input type="hidden" name="requestURL" value="<%=request.getServletPath()%>">
						<!--送出本網頁的路徑給Controller-->
						<input type="hidden" name="action" value="readed">
					</FORM>
				</td>
				<td>
					<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/notificationlist/notificationlist.do"
	                   	style="margin-bottom: 0px;">
						<input type="submit" value="刪除"> 
						<input type="hidden" name="note_no" value="${noteVO.note_no}"> 
						<input type="hidden" name="requestURL" value="<%=request.getServletPath()%>">
						<!--送出本網頁的路徑給Controller-->
						<input type="hidden" name="action" value="delete">
					</FORM>
				</td>
			</tr>
		</c:forEach>
		 <tbody>
	</table>
 </div>
 </div>
</div>
<%@ include file="/front-end/homepage/footer.file"%>
</div>
</body>
</html>