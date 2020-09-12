<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.members.model.*"%>
<%
String feature = "F0006";
%>
<%@ include file="/back-end/backstage/authList.file" %>
<%@ include file="/back-end/backstage/navbar.file" %>
<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
	MembersService memberSvc = new MembersService();
	List<MembersVO> list = memberSvc.getAll();
	pageContext.setAttribute("list", list);
%>

<%
	Map<Integer, String> sexual = new LinkedHashMap<Integer, String>();
	sexual.put(0, "男性");
	sexual.put(1, "女性");
	pageContext.setAttribute("sexual", sexual);

	Map<Integer, String> coa_qualifications = new LinkedHashMap<Integer, String>();
	coa_qualifications.put(0, "沒資格");
	coa_qualifications.put(1, "有資格");
	coa_qualifications.put(2, "審核中");
	pageContext.setAttribute("coa_qualifications", coa_qualifications);

	Map<Integer, String> mem_status = new LinkedHashMap<Integer, String>();
	mem_status.put(0, "正常");
	mem_status.put(1, "停權");
	mem_status.put(2, "審核中");
	pageContext.setAttribute("mem_status", mem_status);
%>

<%
	
%>




<html>
<head>
<title>所有會員資料 - listAllEmp1.jsp</title>

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
				<h3>所有員工資料 - listAllMembers1.jsp</h3>
				<h4>
					<a href="<%=request.getContextPath()%>/back-end/backstage/index.jsp"><img src="<%=request.getContextPath()%>/js/back-end/images/p5.png"
						width="100" height="32" border="0">回首頁</a>
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
			<th>會員編號</th>
			<th>教練資格</th>
			<th>暱稱</th>
			<th>性別</th>
			<th>姓名</th>
			<th>帳號</th>
			<th>會員狀態</th>
			<th>信箱</th>
			<th>密碼</th>
			<th>生日</th>
			<th>電話</th>
			<th>郵遞區號</th>
			<th>地址</th>
			<th>身高</th>
			<th>註冊時間</th>
			<th>儲值金餘額</th>
			<th>大頭貼</th>
			<th>證照</th>
			<th>修改</th>
			<th>刪除</th>
		</tr>
		<%@ include file="page1.file"%>
		<c:forEach var="membersVO" items="${list}" begin="<%=pageIndex%>"
			end="<%=pageIndex+rowsPerPage-1%>">

			<tr>
				<td>${membersVO.member_no}</td>
				<td>${coa_qualifications.get(membersVO.coa_qualifications)}</td>
				<td>${membersVO.known}</td>
				<td>${sexual.get(membersVO.sexual)}</td>
				<td>${membersVO.mem_name}</td>
				<td>${membersVO.mem_account}</td>
				<td>${mem_status.get(membersVO.mem_status)}</td>
				<td>${membersVO.email}</td>
				<td>${membersVO.mem_password}</td>
				<td>${membersVO.birthday}</td>
				<td>${membersVO.phone}</td>
				<td>${membersVO.mem_zip}</td>
				<td>${membersVO.address}</td>
				<td>${membersVO.height}</td>
				<td>${membersVO.reg_date}</td>
				<td>${membersVO.real_blance}</td>
				<td><img
					src="<%=request.getContextPath() %>/MembersShowPicture?member_no=${membersVO.member_no}"
					width="200" height="100"></td>

				<td><img
					src="<%=request.getContextPath() %>/MembersShowLicense?member_no=${membersVO.member_no}"
					width="200" height="100"></td>
				<td>
					<FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/members/members.do"
						style="margin-bottom: 0px;">
						<input type="submit" value="修改"> <input type="hidden"
							name="member_no" value="${membersVO.member_no}"> <input
							type="hidden" name="action" value="getOne_For_Update_Back">
					</FORM>
				</td>
				<td>
					<FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/members/members.do"
						style="margin-bottom: 0px;">
						<input type="submit" value="刪除"> <input type="hidden"
							name="member_no" value="${membersVO.member_no}"> <input
							type="hidden" name="action" value="delete">
					</FORM>
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="page2.file"%>

</body>
</html>