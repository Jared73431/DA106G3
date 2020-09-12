<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.notificationlist.model.*"%>

<%
NotificationListVO notificationlistVO = (NotificationListVO) request.getAttribute("noteVO"); //EmpServlet.java (Concroller) �s�Jreq��empVO���� (�]�A�������X��empVO, �]�]�A��J��ƿ��~�ɪ�empVO����)
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>���O�ק�</title>

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
	width: 450px;
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
</style>

</head>
<body bgcolor='white'>

<table id="table-1">
	<tr><td>
		 <h4><a href="<%=request.getContextPath() %>/back-end/backstage/index.jsp">�^����</a></h4>
	</td></tr>
</table>

<h3>��ƭק�:</h3>

<%-- ���~��C --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">�Эץ��H�U���~:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<FORM METHOD="post" ACTION="notificationlist.do" name="form1">
<table>
	<tr>
		<td>�q���s��:<font color=red><b>*</b></font></td>
		<td><%=notificationlistVO.getNote_no()%></td>
	</tr>
	<tr>
		<td>�|���s��:</td>
		<td><input type="TEXT" name="member_no" size="45" value="<%=notificationlistVO.getMember_no()%>" /></td>
	</tr>
	<jsp:useBean id="cateSvc" scope="page" class="com.categorys.model.CategorysService" />
	<tr>
		<td>�q�����O:<font color=red><b>*</b></font></td>
		<td><select size="1" name="category_no">
			<c:forEach var="categorysVO" items="${cateSvc.all}">
				<option value="${categorysVO.category_no}" ${(notificationlistVO.category_no==categorysVO.category_no)? 'selected':'' } >${categorysVO.category_name}
			</c:forEach>
		</select></td>
	</tr>
	<tr>
		<td>�q�����e:</td>
		<td><input type="TEXT" name="note_content" size="45"	value="<%=notificationlistVO.getNote_content()%>" /></td>
	</tr>

</table>
<br>
<input type="hidden" name="action" value="update">
<input type="hidden" name="note_no" value="<%=notificationlistVO.getNote_no()%>">
<input type="submit" value="�e�X�ק�"></FORM>
</body>


</html>