<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.posture.model.*"%>

<%
	PostureVO postureVO = (PostureVO) request.getAttribute("postureVO");
%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>��ƭק� - update_posture_input.jsp</title>

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
				<h3>��ƭק� - update_posture_input.jsp</h3>
				<h4>
					<a href="<%=request.getContextPath()%>/front-end/posture/select_page.jsp">�^����</a>
				</h4>
			</td>
		</tr>
	</table>

	<h3>��ƭק�:</h3>

	<%-- ���~��C --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">�Эץ��H�U���~:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/posture/posture.do" name="form1">
		<table>
				<tr>
					<td>�����s��:</td>
					<td>${postureVO.posture_no}</td>
				</tr>
				
<tr>
				<td>�|���s��:</td>
				<td>${postureVO.member_no}</td>
			</tr>

			<tr>
				<td>���:</td>
				<td><input type="date" name="record_date" 
				value="<%=(postureVO == null) ? "" : postureVO.getRecord_date()%>"/></td>
					
			</tr>

			<tr>
				<td>�魫:</td>
				<td><input type="text" name="weight"
					value="<%=(postureVO == null) ? "" : postureVO.getWeight()%>" /></td>
			</tr>
			<tr>
				<td>��ת�:</td>
				<td><input type="text" name="bodyfat"
					value="<%=(postureVO == null) ? "" : postureVO.getBodyfat()%>" />
				</td>

			</tr>
			<tr>
				<td>��¦�N�²v:</td>
				<td><input type="text" name="bmr"
					value="<%=(postureVO == null) ? "" : postureVO.getBmr()%>" /></td>

			</tr>
			<tr>
				<td>BMI:</td>
				<td><input type="text" name="bmi"
					value="<%=(postureVO == null) ? "" : postureVO.getBmi()%>" /></td>

			</tr>
			<tr>
				<td>�Ѿl���q</td>
				<td><input type="text" name="remain_cal"
					value="<%=(postureVO == null) ? "" : postureVO.getRemain_cal()%>" />
				</td>

			</tr>
			</table>
			<br>
			<input type="hidden" name="action" value="update">
			<input type="hidden" name="posture_no" value="<%=postureVO.getPosture_no()%>">
			<input type="hidden" name="member_no" value="<%=postureVO.getMember_no()%>">
			<input type="submit" value="�e�X�ק�">
	</FORM>
</body>

</html>