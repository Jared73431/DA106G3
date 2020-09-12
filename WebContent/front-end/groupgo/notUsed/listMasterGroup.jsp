<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.groupgo.model.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
List<GroupgoVO> list = (List<GroupgoVO>) request.getAttribute("list");
Map<Integer,String> status = new HashMap<>();
status.put(1, "揪團中");
status.put(2, "已成團");
status.put(3, "已結束");
status.put(4, "流團");
pageContext.setAttribute("status", status);
%>


<html>
<head>
<title>所有揪團資料 - listAllgroup.jsp</title>

<style>
#content {
	width: 300px;
}

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
				<h3>所有員工資料 - listAllEmp.jsp</h3>
				<h4>
					<a href="<%=request.getContextPath()%>/front-end/groupgo/select_page.jsp">回首頁</a>
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
			<th>揪團編號</th>
			<th>揪團名稱</th>
			<th>團主編號</th>
			<th>活動日期</th>
			<th>活動地點</th>
			<th>緯度</th>
			<th>經度</th>
			<th>活動狀態</th>
			<th>活動預計人數</th>
			<th>活動報名人數</th>
			<th>預算</th>
			<th>報名截止日</th>
			<th>內容</th>
			<th>照片</th>
			<th>評價</th>
			<th>修改</th>
		</tr>
		<%@ include file="page1.file"%>
		<c:forEach var="groupgoVO" items="${list}" begin="<%=pageIndex%>"
			end="<%=pageIndex+rowsPerPage-1%>">

			<tr>
				<td>${groupgoVO.groupgo_id}</td>
				<td>${groupgoVO.group_name}</td>
				<td>${groupgoVO.master_id}</td>
				<td>${groupgoVO.group_date}</td>
				<td>${groupgoVO.place}</td>
				<td>${groupgoVO.lon}</td>
				<td>${groupgoVO.lat}</td>
				<td>${status[groupgoVO.group_status]}</td>
				<td>${groupgoVO.people_num}</td>
				<td>${groupgoVO.current_peo}</td>
				<td>${groupgoVO.budget}</td>
				<td>${groupgoVO.deadline}</td>
				<td><div id="content">${groupgoVO.group_content}</div></td>
				<td><img src="<%=request.getContextPath() %>/showgroupgo.do?groupgo_id=${groupgoVO.groupgo_id}"></td>
				
				<td>${groupgoVO.score_sum}</td>
				<td>
					<FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/groupgo/groupgo.do"
						style="margin-bottom: 0px;">
						<input type="submit" value="修改"> <input type="hidden"
							name="groupgo_id" value="${groupgoVO.groupgo_id}"> <input
							type="hidden" name="action" value="getOne_For_Update">
					</FORM>
				</td>
				
			</tr>
		</c:forEach>
	</table>
	<%@ include file="page2.file"%>

</body>
</html>