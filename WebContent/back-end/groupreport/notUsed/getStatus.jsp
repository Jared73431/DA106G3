<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.groupreport.model.*"%>

<%
	List<GroupReportVO> list = (List<GroupReportVO>) request.getAttribute("groupreport");
	pageContext.setAttribute("list", list);	
	
%>

<!DOCTYPE html>

<html>
<head>
<title>處理檢舉</title>

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
<body bgcolor='white'>

	<table id="table-1">
		<tr>
			<td>
				<h3>揪團檢舉 - getStatus.jsp</h3>
				<h4>
					<a href="<%=request.getContextPath()%>/back-end/groupreport/select_page.jsp">回首頁</a>
				</h4>
			</td>
		</tr>
	</table>

	<table>
		<c:if test="${not empty errorMsgs}">
			<font style="color: red">請修正以下錯誤:</font>
			<ul>
				<c:forEach var="message" items="${errorMsgs}">
					<li style="color: red">${message}</li>
				</c:forEach>
			</ul>
		</c:if>
		<tr>
			<th>揪團編號</th>
			<th>揪團狀態</th>
			<th>檢舉編號</th>
			<th>檢舉時間</th>
			<th>檢舉內容</th>
			<th>處理狀態</th>
			<th>取消揪團</th>
		</tr>
		<tr>
		<jsp:useBean id="groupgoSvc" scope="page" class="com.groupgo.model.GroupgoService" />

			<%@include file="page1.file" %>  
			<c:forEach var="groupreportVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">

				<FORM METHOD="post"
					ACTION="<%=request.getContextPath()%>/groupreport/groupreport.do"
					style="margin-bottom: 0px;">

					<td><a href="<%=request.getContextPath()%>/groupgo/groupgo.do?action=getOne_For_Display&groupgo_id=${groupreportVO.groupgo_id}">${groupreportVO.groupgo_id}</a></td>
					<td>${groupStatus[groupgoSvc.getOneGroup(groupreportVO.groupgo_id).group_status]}</td>
					<td>${groupreportVO.member_no}</td>
					<td>${groupreportVO.report_date}</td>
					<td>${groupreportVO.report_content}</td>
					<td><select name="report_status" value="${groupreportVO.report_status}">
						<c:forEach var="report_status" items="${reportStatus}">
							<option value=${report_status.key} ${(groupreportVO.report_status==report_status.key)?'selected':''}>${report_status.value}</option>
						</c:forEach>
					</select><br><br>

					<input type="submit" value="更新處理狀態"> 
					<input type="hidden" name="groupgo_id" value="${groupreportVO.groupgo_id}"> 
					<input type="hidden" name="member_no" value="${groupreportVO.member_no}"> 
					<input type="hidden" name="report_date" value="${groupreportVO.report_date}"> 
					<input type="hidden" name="report_content" value="${groupreportVO.report_content}"> 
					<input type="hidden" name="old_status" value="${groupreportVO.report_status}"> 
					<input type="hidden" name="action" value="update">
				</FORM></td>
				<td><FORM METHOD="post" ACTION="<%=request.getContextPath()%>/groupgo/groupgo.do">
					<input type="submit" value="下架揪團"> 
					<input type="hidden" name="group_status" value="5"> 
					<input type="hidden" name="groupgo_id" value="${groupreportVO.groupgo_id}"> 
					<input type="hidden" name="report_status" value="${groupreportVO.report_status}">
					<input type="hidden" name="action" value="update_status_report">
				</FORM>
				</td>
			</tr>

		</c:forEach>

	</table>
<%@ include file="page2.file" %>
</body>
</html>