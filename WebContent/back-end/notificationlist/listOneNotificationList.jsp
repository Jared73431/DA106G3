<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ page import="com.notificationlist.model.*"%>
<%@ page import="com.categorys.model.*"%>
<%@ page import="java.util.*"%>
<%-- �����Ƚm�߱ĥ� Script ���g�k���� --%>

<%
  NotificationListVO noteVO = (NotificationListVO) request.getAttribute("noteVO"); //EmpServlet.java(Concroller), �s�Jreq��empVO����
%>
<%
  CategorysService cateSvc = new CategorysService();
  CategorysVO cateVO = cateSvc.getOneCategorys(noteVO.getCategory_no());
%>
<%
String feature = "F0007";
%>
<%@ include file="/back-end/backstage/authList.file" %>
<%@ include file="/back-end/backstage/navbar.file" %>
<html>
<head>
<title>���u��� - listOneEmp.jsp</title>

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
<body bgcolor='white' >


<table>
	<tr>
		<th>�|���m�W</th>
		<th>�q�����O</th>
		<th>�q�����e</th>
		<th>���A</th>
		<th>�q�����</th>
	</tr>
	<tr>
	<jsp:useBean id="memSvc" scope="page" class="com.members.model.MembersService" />	
	<c:forEach var="memVO" items="${memSvc.all}">
          <c:if test="${noteVO.member_no==memVO.member_no}">
				<td>${memVO.mem_name}</td>
		  </c:if>
     </c:forEach>
		
		<td><%=cateVO.getCategory_name()%></td>
		<td><%=noteVO.getNote_content()%></td>
		<td><c:forEach var="status" items="${note_status}">
					<c:if test="${(status.key == noteVO.note_status)}">
					 	${status.value}
					</c:if>
					</c:forEach></td>
		<td><%=noteVO.getNote_date()%></td>
	</tr>
</table>

</body>
</html>