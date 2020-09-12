<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ page import="java.util.*"%>
<%@ page import="com.categorys.model.*"%>
<%-- 此頁暫練習採用 Script 的寫法取值 --%>

<%
  CategorysVO cateVO = (CategorysVO) request.getAttribute("categorysVO"); //EmpServlet.java(Concroller), 存入req的empVO物件
%>
<%
String feature = "F0007";
%>
<%@ include file="/back-end/backstage/authList.file" %>
<%@ include file="/back-end/backstage/navbar.file" %>

<html>
<head>
<title>員工資料 - listOneCategorys.jsp</title>

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



<table>
	<tr>
		<th>員工編號</th>
		<th>員工姓名</th>
	</tr>
	<tr>
		<td><%=cateVO.getCategory_no()%></td>
		<td><%=cateVO.getCategory_name()%></td>
	</tr>
</table>

</body>
</html>