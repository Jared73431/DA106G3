<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.members.model.*"%>
<%-- �����Ƚm�߱ĥ� Script ���g�k���� --%>

<%
	MembersVO membersVO = (MembersVO) request.getAttribute("membersVO"); //EmpServlet.java(Concroller), �s�Jreq��empVO����
%>

<%
	MembersService memberSvc = new MembersService();
	List<MembersVO> list = memberSvc.getAll();
	pageContext.setAttribute("list", list);
%>

<%
	Map<Integer, String> sexual = new LinkedHashMap<Integer, String>();
	sexual.put(0, "�k��");
	sexual.put(1, "�k��");
	pageContext.setAttribute("sexual", sexual);
%>

<%
	Map<Integer, String> coa_qualifications = new LinkedHashMap<Integer, String>();
	coa_qualifications.put(0, "�S���");
	coa_qualifications.put(1, "�����");
	coa_qualifications.put(2, "�f�֤�");
	pageContext.setAttribute("coa_qualifications", coa_qualifications);

	Map<Integer, String> mem_status = new LinkedHashMap<Integer, String>();
	mem_status.put(0, "�S���");
	mem_status.put(1, "�����");
	mem_status.put(2, "�f�֤�");
	pageContext.setAttribute("mem_status", mem_status);
%>

<html>
<head>
<title>�|����� - listOneEmp3.jsp</title>

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

	<h4>�����Ƚm�߱ĥ� Script ���g�k����:</h4>
	<table id="table-1">
		<tr>
			<td>
				<h3>���u��� - ListOneEmp.jsp</h3>
				<h4>
					<a href="select_page_members.jsp"><img src="images/back1.gif"
						width="100" height="32" border="0">�^����</a>
				</h4>
			</td>
		</tr>
	</table>

	<table>
		<tr>
			<th>�|���s��</th>
			<th>�нm���</th>
			<th>�ʺ�</th>
			<th>�ʧO</th>
			<th>�m�W</th>
			<th>�b��</th>
			<th>�|�����A</th>
			<th>�H�c</th>
			<th>�K�X</th>
			<th>�ͤ�</th>
			<th>�q��</th>
			<th>�l���ϸ�</th>
			<th>�a�}</th>
			<th>����</th>
			<th>���U�ɶ�</th>
			<th>�x�Ȫ��l�B</th>
			<th>�j�Y�K</th>
			<th>�ҷ�</th>
			<th>�ק�</th>
		</tr>
		<tr>
			<td>${membersVO.member_no}</td>
			<td><c:forEach var="coa_qualifications" items="${coa_qualifications}">
			${membersVO.coa_qualifications == coa_qualifications.key ? coa_qualifications.value : ''}
			</c:forEach></td>
			<td>${membersVO.known}</td>
			<td><c:forEach var="sexual" items="${sexual}">
			${membersVO.sexual == sexual.key ? sexual.value : ''}
			</c:forEach></td>
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
					<input type="submit" value="�ק�"> <input type="hidden"
						name="member_no" value="${membersVO.member_no}"> <input
						type="hidden" name="action" value="getOne_For_Update">
				</FORM>
			</td>

		</tr>
	</table>

</body>
</html>