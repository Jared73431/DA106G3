<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.categorys.model.*"%>
<%-- �����m�߱ĥ� EL ���g�k���� --%>

<%
	CategorysService cateSvc = new CategorysService();
	List<CategorysVO> list = cateSvc.getAll();
	pageContext.setAttribute("list", list);
%>
<%
String feature = "F0007";
%>
<%@ include file="/back-end/backstage/authList.file" %>
<%@ include file="/back-end/backstage/navbar.file" %>

<html>
<head>
<title>�Ҧ����O</title>

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


	<%-- ���~��C --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">�Эץ��H�U���~:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<table>
		<tr>
			<th>���O�s��</th>
			<th>�q�����O�W��</th>
			<th>�ק�</th>
			<th>�R��</th>
		</tr>
		<%@ include file="page1.file"%>
		<c:forEach var="cateVO" items="${list}" begin="<%=pageIndex%>"
			end="<%=pageIndex+rowsPerPage-1%>">

			<tr>
				<td>${cateVO.category_no}</td>
				<td>${cateVO.category_name}</td>
				<td>
					<FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/categorys.do"
						style="margin-bottom: 0px;">
						<input type="submit" value="�ק�"> 
						<input type="hidden"name="category_no" value="${cateVO.category_no}"> 
						<input type="hidden" name="action" value="getOne_For_Update">
					</FORM>
				</td>
				<td>
					<FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/categorys.do"
						style="margin-bottom: 0px;">
						<input type="submit" value="�R��"> <input type="hidden"
							name="category_no" value="${cateVO.category_no}"> <input
							type="hidden" name="action" value="delete">
					</FORM>
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="page2.file"%>

</body>
</html>