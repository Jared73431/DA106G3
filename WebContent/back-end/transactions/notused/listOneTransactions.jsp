<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ page import="com.transactions.model.*"%>
<%-- 此頁暫練習採用 Script 的寫法取值 --%>

<%
	TransactionsVO transactionsVO = (TransactionsVO) request.getAttribute("transactionsVO"); //EmpServlet.java(Concroller), 存入req的empVO物件
%>

<html>
<head>
<title>會員資料 - listOneEmp.jsp</title>

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

<h4>此頁暫練習採用 Script 的寫法取值:</h4>
<table id="table-1">
	<tr><td>
		 <h3>員工資料 - ListOneEmp.jsp</h3>
		 <h4><a href="select_page_transactions.jsp"><img src="images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
	</td></tr>
</table>

<table>
	<tr>
		<th>紀錄編號</th>
		<th>會員編號</th>
		<th>存入金額</th>
		<th>提款金額</th>
		<th>交易日期</th>
		<th>交易備註</th>
	</tr>
	<tr>
			<td>${transactionsVO.trans_no}</td>
			<td>${transactionsVO.member_no}</td>
			<td>${transactionsVO.deposit}</td>
			<td>${transactionsVO.withdraw}</td>
			<td>${transactionsVO.tran_date}</td>
			<td>${transactionsVO.remark}</td> 
	</tr>
</table>

</body>
</html>