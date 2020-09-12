<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.exerciserecord.model.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
	ExerciseRecordService exerciseRecordSvc = new ExerciseRecordService();
	List<ExerciseRecordVO> list = exerciseRecordSvc.getAll();
	pageContext.setAttribute("list", list);
%>


<html>
<head>
<title>所有資料 - listExerciseRecord.jsp</title>

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
<body bgcolor='white'>

	<h4>此頁練習採用 EL 的寫法取值:</h4>
	<table id="table-1">
		<tr>
			<td>
				<h3>所有資料 - listAllExerciseRecord.jsp</h3>
				<h4>
					<a href="<%=request.getContextPath()%>/front-end/exerciserecord/select_page.jsp">回首頁</a>
				</h4>
			</td>
		</tr>
	</table>

	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<table>
		<tr>
			<th>運動紀錄編號</th>
			<th>會員編號</th>
			<th>運動項目編號</th>
			<th>運動日期</th>
			<th>運動時間</th>
			<th>總消耗熱量</th>
			<th>修改</th>
			<th>刪除</th>
		</tr>
		<%@ include file="page1.file"%>
		<c:forEach var="exerciseRecordVO" items="${list}" begin="<%=pageIndex%>"
			end="<%=pageIndex+rowsPerPage-1%>">

			<tr>
				<td>${exerciseRecordVO.exerec_no}</td>
				<td>${exerciseRecordVO.member_no}</td>
				<td>${exerciseRecordVO.exe_no}</td>
				<td>${exerciseRecordVO.exe_date}</td>
				<td>${exerciseRecordVO.exe_time}</td>
				<td>${exerciseRecordVO.exe_tcal}</td>

				<td>
					<FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/exerciserecord/exerciserecord.do"
						style="margin-bottom: 0px;">
						<input type="submit" value="修改"> <input type="hidden"
							name="exerec_no" value="${exerciseRecordVO.exerec_no}"> <input
							type="hidden" name="action" value="getOne_For_Update">
					</FORM>
				</td>
				<td>
					<FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/exerciserecord/exerciserecord.do"
						style="margin-bottom: 0px;">
						<input type="submit" value="刪除"> <input type="hidden"
							name="exerec_no" value="${exerciseRecordVO.exerec_no}"> <input
							type="hidden" name="action" value="delete">
					</FORM>
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="page2.file"%>

</body>
</html>