<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.notificationlist.model.*"%>

<%-- �����m�߱ĥ� EL ���g�k���� --%>
<%NotificationListVO notificationlistVO =(NotificationListVO)request.getAttribute("noteVO");%>

<%
    NotificationListService noteSvc = new NotificationListService();
    List<NotificationListVO> list = noteSvc.getAll();
    pageContext.setAttribute("list",list);
%>
<%
String feature = "F0007";
%>
<%@ include file="/back-end/backstage/authList.file" %>
<%@ include file="/back-end/backstage/navbar.file" %>

<html>
<head>
<title>�Ҧ��q��</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/js/vendors/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/js/lobibox/dist/css/lobibox.css" />
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
<body bgcolor='white' >
<script
		src="<%=request.getContextPath()%>/js/vendors/jquery/jquery-3.1.0.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/js/lobibox/dist/js/lobibox.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/js/lobibox/dist/js/notifications.min.js"></script>
		 <h3>�Ҧ��q��</h3>
		 <h4> 
			    	<input type="button" class="btn btn-dark" 
			    	onclick="location.href='<%=request.getContextPath() %>/back-end/backstage/index.jsp'" value="��^"/>
		 </h3></h4>

<%-- ���~��C --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">�Эץ��H�U���~:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<table align="center" width="80%" class="table table-striped">
<thead>
	<tr>
		<th scope="col">�q���s��</th>
		<th scope="col">�|���s��</th>
		<th scope="col">�q�����O</th>
		<th scope="col">�q�����e</th>
		<th scope="col">���A</th>
		<th scope="col">�q�����</th>
		<th scope="col">�ק�</th>
		<th scope="col">�R��</th>
	</tr>
</thead>
 <tbody>	
	<%@ include file="page1.file" %> 
	<c:forEach var="notificationlistVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
	
		<tr>
			<td scope="row">${notificationlistVO.note_no}</td>
			<jsp:useBean id="memSvc" scope="page" class="com.members.model.MembersService" />
			<td scope="row">${memSvc.getOneMembers(notificationlistVO.member_no).mem_name}</td>
			<jsp:useBean id="cateSvc" scope="page" class="com.categorys.model.CategorysService" />
			<td scope="row">${cateSvc.getOneCategorys(notificationlistVO.category_no).category_name }</td>
			<td scope="row">${notificationlistVO.note_content}</td>
			<td scope="row"> 
				<c:forEach var="status" items="${note_status}">
					<c:if test="${(status.key == notificationlistVO.note_status)}">
					${status.value}
					</c:if>
				</c:forEach>
			</td>
			<td scope="row">${notificationlistVO.note_date}</td> 
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/notificationlist/notificationlist.do" style="margin-bottom: 0px;">
			     <input type="submit" value="�ק�">
			     <input type="hidden" name="note_no"  value="${notificationlistVO.note_no}">
			      <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>">
			     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
			</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/notificationlist/notificationlist.do" style="margin-bottom: 0px;">
			     <input type="submit" value="�R��">
			     <input type="hidden" name="note_no"  value="${notificationlistVO.note_no}">
			      <input type="hidden" name="requestURL"	value="/back-end/notificationlist/listAllNotificationList.jsp">
			     <input type="hidden" name="action" value="deleteback"></FORM>
			</td>
		</tr>
	</c:forEach>
	 <tbody>
</table>
<%@ include file="page2.file" %>





</body>

</html>