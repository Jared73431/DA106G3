<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.followmaster.model.*"%>

<%
	FollowMasterVO followMasterVO = (FollowMasterVO) request.getAttribute("followMasterVO");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新增揪團檢舉</title>
</head>
<body>
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
		<tr>
			<td>
				<h3>資料新增 - addFollow.jsp</h3>
			</td>
			<td>
				<h4>
					<a href="<%=request.getContextPath()%>/front-end/followmaster/select_page.jsp">回首頁</a>
				</h4>
			</td>
		</tr>
	</table>

	<h3>資料新增:</h3>

	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/followmaster/followmaster.do" name="form1">
		<table>


			<tr>
				<td>會員編號:</td>
				<jsp:useBean id="memberSvc" scope="page"
					class="com.members.model.MembersService" />
				<li>
					<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/followmaster/followmaster.do">
						<b>選擇會員編號:</b>

						<td><select size="1" name="member_no">
								<c:forEach var="membersVO" items="${memberSvc.all}">
									<option value="${membersVO.member_no}">${membersVO.member_no}
								</c:forEach>
						</select></td>
			</tr>

			<tr>
				<td>團主編號:</td>
				<td><input type="TEXT" name="master_no"
					value="<%=(followMasterVO == null) ? "M000001" : followMasterVO.getMaster_no()%>" /></td>
			</tr>



		</table>
		<br> <input type="hidden" name="action" value="insert"> <input
			type="submit" value="送出新增">
	</FORM>
</body>

</body>
</html>