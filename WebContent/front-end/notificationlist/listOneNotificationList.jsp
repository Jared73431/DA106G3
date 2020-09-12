<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.notificationlist.model.*"%>
<%@ page import="com.categorys.model.*"%>
<%@ page import="java.util.*"%>
<%-- 此頁暫練習採用 Script 的寫法取值 --%>
<%@ page import="com.members.model.*"%>
    
    
<%
 MembersService memberSvc = new MembersService();
 String member_no = (String)session.getAttribute("member_no");
 MembersVO membersVO = memberSvc.getOneMembers(member_no);
 pageContext.setAttribute("membersVO", membersVO);
%>
<%
  NotificationListVO noteVO = (NotificationListVO) request.getAttribute("noteVO"); //EmpServlet.java(Concroller), 存入req的empVO物件
%>
<%
  CategorysService cateSvc = new CategorysService();
  CategorysVO cateVO = cateSvc.getOneCategorys(noteVO.getCategory_no());
%>
<html>
<head>
<title>通知內容</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/js/vendors/bootstrap/css/bootstrap.min.css">
<style>
  table#table-1 {
	background-color: #CCCCFF;
    border: 2px solid black;
    text-align: center;
  }
  table#table-1 h4 {
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
	width: 600px;
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
<body bgcolor='white' onload='connect();'>
<%@ include file="/front-end/homepage/nav.file"%>
<div id="listall">
<div class="container">
  <div class="row justify-content-md-center">
    <div class="col col-lg-12">
<table >
	<tr><td>
		 <h3>通知詳細資料</h3>
		 <h4><input type ="button" onclick="history.back()" value="返回"></input></h4>
	</td></tr>
</table >
<!-- <a href="/front-end/member/listAllNotebyMember.jsp"><img src="images/back1.gif" width="100" height="32" border="0">回首頁</a> -->
<table>
	<tr>
	<thead>
		<th scope="col">會員姓名</th>
		<th scope="col">通知類別</th>
		<th scope="col">通知內容</th>
		<th scope="col">狀態</th>
		<th scope="col">通知日期</th>
	</tr>
	</thead>
	<tbody>
	<tr>
		<jsp:useBean id="memSvc" scope="page" class="com.members.model.MembersService" />
		<td scope="row">${memSvc.getOneMembers(noteVO.member_no).mem_name}</td>
		<td><%=cateVO.getCategory_name()%></td>
		<td><%=noteVO.getNote_content()%></td>
		<td><c:forEach var="status" items="${note_status}">
					<c:if test="${(status.key == noteVO.note_status)}">
					 	${status.value}
					</c:if>
					</c:forEach></td>
		<td><%=noteVO.getNote_date()%></td>
	</tr>
	<tbody>
</table>
</div>
</div>
</div>
</div>
<%@ include file="/front-end/homepage/footer.file"%>
</body>
</html>