<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.exerciserecord.model.*"%>
<%
ExerciseRecordVO exerciseRecordVO = (ExerciseRecordVO) request.getAttribute("exerciseRecord");
	// 	pageContext.setAttribute("dietRecordVO", dietRecordVO);
%>

<html>
<head>
<title>資料 - listExerciseRecord.jsp</title>

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
				<h3>資料 - listExerciseRecord.jsp</h3>
				<h4>
					<a href="<%=request.getContextPath()%>/front-end/exerciserecord/select_page.jsp">回首頁</a>
				</h4>
			</td>
		</tr>
	</table>

	<table>
		<tr>
			<th>運動紀錄編號</th>
			<th>會員編號</th>
			<th>運動項目編號</th>
			<th>運動日期</th>
			<th>運動時間</th>
			<th>總消耗熱量</th>

		</tr>


		<tr>
			<td>${exerciseRecordVO.exerec_no}</td>
			<td>${exerciseRecordVO.member_no}</td>
			<td>${exerciseRecordVO.exe_no}</td>
			<td>${exerciseRecordVO.exe_date}</td>
			<td>${exerciseRecordVO.exe_time}</td>
			<td>${exerciseRecordVO.exe_tcal}</td>
		</tr>
	</table>
</body>
</html>