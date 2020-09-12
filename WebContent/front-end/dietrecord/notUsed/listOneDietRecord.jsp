<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.dietrecord.model.*"%>
<%@ page import="java.util.*"%>
<%
	DietRecordVO dietRecordVO = (DietRecordVO) request.getAttribute("dietRecordVO");
%>

<html>
<head>
<title>��� - listDietRecord.jsp</title>

<style>
img {
	max-width: 500px;
}

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
	width: 1400px;
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
<body bgcolor='white'>

	<table id="table-1">
		<tr>
			<td>
				<h3>��� - listDietRecord.jsp</h3>
				<h4>
					<a href="<%=request.getContextPath()%>/front-end/dietrecord/select_page.jsp">�^����</a>
				</h4>
			</td>
		</tr>
	</table>

	<table>
		<tr>
			<th>���������s��</th>
			<th>�|���s��</th>
			<th>�������</th>
			<th>�����ɬq</th>
			<th>�Ӥ�</th>
		</tr>

		<tr>
			<td><%=dietRecordVO.getDiet_no()%></td>
			<td><%=dietRecordVO.getMember_no()%></td>
			<td><%=dietRecordVO.getRec_date()%></td>
			<td>${period[dietRecordVO.eat_period]}</td>
			<td><img
				src="<%=request.getContextPath() %>/showdiet.do?diet_no=${dietRecordVO.diet_no}"></td>
		</tr>
	</table>
</body>
</html>