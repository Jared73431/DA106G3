<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.transactions.model.*"%>
<%@ page import="java.util.*"%>
<%
String feature = "F0006";
%>
<%@ include file="/back-end/backstage/authList.file" %>
<%@ include file="/back-end/backstage/navbar.file" %>
<%
  TransactionsVO transactionsVO = (TransactionsVO) request.getAttribute("transactionsVO"); //EmpServlet.java (Concroller) �s�Jreq��empVO���� (�]�A�������X��empVO, �]�]�A��J��ƿ��~�ɪ�empVO����)
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>����ק� - update_transactions_input.jsp</title>

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
	<tr><td>
		 <h3>����ק� - update_transactions_input.jsp</h3>
		 <h4><a href="select_page_transactions.jsp"><img src="images/back1.gif" width="100" height="32" border="0">�^����</a></h4>
	</td></tr>
</table>

<h3>��ƭק�:</h3>

<%-- ���~��C --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">�Эץ��H�U���~:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<FORM METHOD="post" ACTION="transactions.do" name="form1">
<table>
	<tr>
		<td>����s��:<font color=red><b>*</b></font></td>
		<td><%=transactionsVO.getTrans_no()%></td>
	</tr>
	<tr>
		<td>�|���s��:<font color=red><b>*</b></font></td>
		<td><%=transactionsVO.getMember_no()%></td>
	</tr>
	<tr>
		<td>�s�J���B:</td>
		<td><input type="TEXT" name="deposit" size="45"	value="<%=transactionsVO.getDeposit()%>" /></td>
	</tr>
	<tr>
		<td>���ڪ��B:</td>
		<td><input type="TEXT" name="withdraw" size="45"	value="<%=transactionsVO.getWithdraw()%>" /></td>
	</tr>
	<tr>
		<td>������:<font color=red><b>*</b></font></td>
		<td><%=transactionsVO.getTran_date()%></td>
	</tr>
	<tr>
		<td>����Ƶ�:</td>
		<td><input type="TEXT" name="remark" size="45" value="<%=transactionsVO.getRemark()%>" /></td>
	</tr>
	
	

	

</table>
<br>
<input type="hidden" name="action" value="update">
<input type="hidden" name="trans_no" value="<%=transactionsVO.getTrans_no()%>">
<input type="hidden" name="member_no" value="<%=transactionsVO.getMember_no()%>">
<input type="hidden" name="tran_date" value="<%=transactionsVO.getTran_date()%>">
<input type="submit" value="�e�X�ק�"></FORM>
</body>



</html>