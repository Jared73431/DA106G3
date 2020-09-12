<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.groupgo.model.*"%>
<%-- �����m�߱ĥ� EL ���g�k���� --%>

<%
List<GroupgoVO> list = (List<GroupgoVO>) request.getAttribute("list");
Map<Integer,String> status = new HashMap<>();
status.put(1, "���Τ�");
status.put(2, "�w����");
status.put(3, "�w����");
status.put(4, "�y��");
pageContext.setAttribute("status", status);
%>


<html>
<head>
<title>�Ҧ����θ�� - listAllgroup.jsp</title>

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

	<h4>�����m�߱ĥ� EL ���g�k����:</h4>
	<table id="table-1">
		<tr>
			<td>
				<h3>�Ҧ����u��� - listAllEmp.jsp</h3>
				<h4>
					<a href="<%=request.getContextPath()%>/front-end/groupgo/select_page.jsp">�^����</a>
				</h4>
			</td>
		</tr>
	</table>

	<%-- ���~��C --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">�Эץ��H�U���~:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<table>
		<tr>
			<th>���νs��</th>
			<th>���ΦW��</th>
			<th>�ΥD�s��</th>
			<th>���ʤ��</th>
			<th>���ʦa�I</th>
			<th>�n��</th>
			<th>�g��</th>
			<th>���ʪ��A</th>
			<th>���ʹw�p�H��</th>
			<th>���ʳ��W�H��</th>
			<th>�w��</th>
			<th>���W�I���</th>
			<th>���e</th>
			<th>�Ӥ�</th>
			<th>����</th>
			<th>�ק�</th>
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
						<input type="submit" value="�ק�"> <input type="hidden"
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