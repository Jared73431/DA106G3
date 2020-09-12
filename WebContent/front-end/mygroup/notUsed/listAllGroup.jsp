<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.mygroup.model.*"%>

<%
    MygroupService mygroupsvc = new MygroupService();
    List<MyGroupVO> list = mygroupsvc.getAll();
    pageContext.setAttribute("list",list);
%>

<!DOCTYPE html>
<html>
<head>
<title>揪團資料</title>

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

<table id="table-1">
	<tr><td>
		 <h3>所有揪團資料 - listAllGroup.jsp</h3>
		 <h4><a href="<%=request.getContextPath()%>/front-end/mygroup/select_page.jsp">回首頁</h4>
	</td></tr>
</table>

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<table>
	<tr>
		<th>揪團編號</th>
		<th>團員編號</th>
		<th>揪團狀態</th>
		<th>評分</th>
		
	</tr>
	<%@ include file="page1.file" %> 
	<c:forEach var="mygroupVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
		
		<tr>
			<td>${mygroupVO.groupgo_id}</td>
			<td>${mygroupVO.member_no}</td>
			<td>${mygroupVO.mygroup_status}</td>
			<td>${mygroupVO.score}</td>
				
			
		</tr>
	</c:forEach>
</table>
<%@ include file="page2.file" %>


</body>
</html>