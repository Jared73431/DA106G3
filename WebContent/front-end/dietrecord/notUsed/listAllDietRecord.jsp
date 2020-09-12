<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.dietrecord.model.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
	DietRecordService dietrecordSvc = new DietRecordService();
	List<DietRecordVO> list = dietrecordSvc.getAll();
	pageContext.setAttribute("list", list);

%>


<html>
<head>
<title>所有資料 - listDietRecord.jsp</title>

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
				<h3>所有資料 - listAllDietRecord.jsp</h3>
				<h4>
					<a href="<%=request.getContextPath()%>/front-end/dietrecord/select_page.jsp">回首頁</a>
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
			<th>飲食紀錄編號</th>
			<th>會員編號</th>
			<th>紀錄日期</th>
			<th>紀錄時段</th>
			<th>照片</th>
			<th>查看</th>
			<th>修改</th>
			<th>刪除</th>
		</tr>
		<%@ include file="page1.file"%>
		<c:forEach var="dietrecordVO" items="${list}" begin="<%=pageIndex%>"
			end="<%=pageIndex+rowsPerPage-1%>">

			<tr>
				<td>${dietrecordVO.diet_no}</td>
				<td>${dietrecordVO.member_no}</td>
				<td>${dietrecordVO.rec_date}</td>
				<td>${period[dietrecordVO.eat_period]}</td>
			<td><img src="<%=request.getContextPath() %>/showdiet.do?diet_no=${dietrecordVO.diet_no}"></td>

				<td><FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/dietdetail/dietdetail.do"
						style="margin-bottom: 0px;">
						<input type="submit" value="查看"> <input type="hidden"
							name="diet_no" value="${dietrecordVO.diet_no}">
						<input type="hidden" name="action" value="getOne_For_Display">
					</FORM>
				</td>


				<td>
					<FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/dietrecord/dietrecord.do"
						style="margin-bottom: 0px;">
						<input type="submit" value="修改"> <input type="hidden"
							name="diet_no" value="${dietrecordVO.diet_no}"> <input
							type="hidden" name="action" value="getOne_For_Update">
					</FORM>
				</td>
				
				<td>
					<FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/dietrecord/dietrecord.do"
						style="margin-bottom: 0px;">
						<input type="submit" value="刪除"> <input type="hidden"
							name="diet_no" value="${dietrecordVO.diet_no}"> <input
							type="hidden" name="action" value="delete">
					</FORM>
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="page2.file"%>

</body>
</html>