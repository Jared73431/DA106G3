<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.posture.model.*"%>
<%@ page import="java.util.*"%>
<%
	List<PostureVO> list = (List) request.getAttribute("list");
	pageContext.setAttribute("list", list);
%>
<html>
<head>
<title>飲食資料 - listOPosture.jsp</title>

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
				<h3>飲食資料 - ListPosture.jsp</h3>
				<h4>
					<a href="<%=request.getContextPath()%>/front-end/posture/select_page.jsp">回首頁</a>
				</h4>
			</td>
		</tr>
	</table>

	<table>
		<tr>
		
			<th>紀錄編號</th>
			<th>會員編號</th>
			<th>日期</th>
			<th>體重</th>
			<th>體脂肪</th>
			<th>基礎代謝率</th>
			<th>BMI</th>
			<th>剩餘熱量</th>
		</tr>
		<c:forEach var="postureVO" items="${list}">

			<tr>
				<td>${postureVO.posture_no}</td>
				<td>${postureVO.member_no}</td>
				<td>${postureVO.record_date}</td>
				<td>${postureVO.weight}</td>
				<td>${postureVO.bodyfat}</td>
				<td>${postureVO.bmr}</td>
				<td>${postureVO.bmi}</td>
				<td>${postureVO.remain_cal}</td>
				
			
			</tr>
		</c:forEach>
	</table>


</body>
</html>