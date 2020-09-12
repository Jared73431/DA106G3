<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.coach.model.*"%>
<%@ page import="com.members.model.*"%>
<%@ page import="java.util.*"%>
<%
	String feature = "F0002";
%>

<%@ include file="/back-end/backstage/authList.file" %>
<%@ include file="/back-end/backstage/navbar.file" %>
<%
	CoachVO coachVO = (CoachVO) request.getAttribute("coachVO");
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>申請審核 - addCoach.jsp</title>



</head>
<body bgcolor='white'>
<div id="listall">

	<h3>教練資格審核:</h3>

	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<jsp:useBean id="memberSvc" scope="page"
		class="com.members.model.MembersService" />
<div class="container">
<div class="table-responsive">
	<table class="table table-hover">
	<thead>
		<tr>
			<th scope="col">申請人姓名</th>
			<th scope="col">申請人證照</th>
			<th scope="col"></th>
			<th scope="col"></th>
			
			
			
		</tr>
		</thead>

		<c:forEach var="membersVO" items="${memberSvc.all}">
			<c:if test="${(membersVO.coa_qualifications == 2)}">
			<tbody>
					<th>${membersVO.mem_name}</th>
			

			
					<td><img
						src="<%=request.getContextPath()%>/MembersShowLicense?member_no=${membersVO.member_no}"
						width=100px height=100px></td>
				
			
					<td>
						<FORM METHOD="post"
							ACTION="<%=request.getContextPath()%>/coach/coach.do">
							<input type="hidden" name="action" value="insert"><input
								type="hidden" name="member_no" value="${membersVO.member_no}">
							<input type="hidden" name="requestURL"
								value="<%=request.getServletPath()%>">
							<!--送出本網頁的路徑給Controller-->
								<button type="submit" class="btn btn-outline-success">通過審核</button>
						</FORM>
					</td>

					<td>
						<FORM METHOD="post"
							ACTION="<%=request.getContextPath()%>/coach/coach.do">
							<input type="hidden" name="action" value="fail"><input
								type="hidden" name="member_no" value="${membersVO.member_no}">
							<input type="hidden" name="requestURL"
								value="<%=request.getServletPath()%>">
							<!--送出本網頁的路徑給Controller-->

							<!--送出當前是第幾頁給Controller-->
							<button type="submit" class="btn btn-outline-danger" >資格不符</button>
						</FORM>
					</td>
				</tbody>
			</c:if>
		</c:forEach>

	</table>
	</div>
</div>
	</div>
</body>





</html>