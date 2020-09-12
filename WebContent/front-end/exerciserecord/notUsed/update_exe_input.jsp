<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.exerciserecord.model.*"%>

<%
ExerciseRecordVO exerciseRecordVO = (ExerciseRecordVO) request.getAttribute("exerciseRecordVO");
%>
<html>
<head>

<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>��ƭק� - update_diet_input.jsp</title>

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
	width: 900px;
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
				<h3>��ƭק� - update_diet_input.jsp</h3>
				<h4>
					<a href="<%=request.getContextPath()%>/front-end/exerciserecord/select_page.jsp">�^����</a>
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


			
			<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/exerciserecord/exerciserecord.do" name="form1">
		<table>
			<tr>
				<td>�B�ʬ����s��:<font color=red><b>*</b></font></td>
				<td><%=exerciseRecordVO.getExerec_no()%></td>
			</tr>
			<tr>
				<td>�|���s��:</td>
				<td><%=exerciseRecordVO.getMember_no()%></td>
			</tr>
		<jsp:useBean id="execiseItemSvc" scope="page" class="com.exerciseitem.model.ExerciseItemService" />	
			<tr>
				<td>�B�ʶ��ؽs��:</td>
				<td><select size="1" name="exe_no">
						<c:forEach var="exerciseItemVO" items="${execiseItemSvc.all}">
							<option value="${exerciseItemVO.exe_no}"
								${(exerciseItemVO.exe_no == exerciseRecordVO.exe_no)?'selected':'' }>
								${exerciseItemVO.exe_item},${exerciseItemVO.exe_cal}
							</option>
						</c:forEach>
				</select></td>
			</tr>

			<tr>
				<td>�B�ʤ��:</td>
				<td><input type="date" name="exe_date"
					value="<%=(exerciseRecordVO == null) ? "" : exerciseRecordVO.getExe_date()%>" /></td>

			</tr>
			<tr>
				<td>�B�ʮɶ�</td>
				<td><input type="text" name="exe_time"
					value="<%=(exerciseRecordVO == null) ? "" : exerciseRecordVO.getExe_time()%>" /></td>
			</tr>
			
			<tr>
				<td>�`���Ӽ��q</td>
				<td><input type="text" name="exe_tcal"
					value="<%=(exerciseRecordVO == null) ? "" : exerciseRecordVO.getExe_tcal()%>" /></td>
			</tr>

		</table>
		<br> <input type="hidden" name="action" value="update"> <input
			type="hidden" name="exerec_no" value="<%=exerciseRecordVO.getExerec_no()%>">
		<input type="hidden" name="member_no"
			value="<%=exerciseRecordVO.getMember_no()%>"> <input
			type="submit" value="�e�X�ק�">
	</FORM>

	

</body>

</html>