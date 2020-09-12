<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.mygroup.model.*"%>

<% List<MyGroupVO> list = (List<MyGroupVO>) request.getAttribute("mygroup");
    pageContext.setAttribute("list",list); 
    Map status = new HashMap();
	status.put(1, "審核中");
	status.put(2, "即將開始");
	status.put(3, "待評分");
	status.put(4, "取消");
	pageContext.setAttribute("status", status);
%>   

<!DOCTYPE html>

<html>
<head>
<title>參與的揪團</title>

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
		 <h3>揪團資料 - getMemberAttend.jsp</h3>
		 <h4><a href="<%=request.getContextPath()%>/front-end/mygroup/select_page.jsp">回首頁</a></h4>
	</td></tr>
</table>

<table>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>
		<tr>
		<th>會員編號</th>
		<th>揪團編號</th>
		<th>揪團狀態</th>
		<th>評分(1-10)</th>		
		</tr>
				
		<tr>
		

<c:forEach var="mygroupVO" items="${list}">
<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/mygroup/mygroup.do" style="margin-bottom: 0px;">
			<td>${mygroupVO.member_no}</td>
			<td>${mygroupVO.groupgo_id}</td>
			<td>${status[mygroupVO.mygroup_status]}</td>
			<td><input type="TEXT" name="score" value="${mygroupVO.score}"></td>
		
			<td>
			  
			     <input type="submit" value="修改評分">	
			     <input type="hidden" name="member_no"  value="${mygroupVO.member_no}">
			     <input type="hidden" name="groupgo_id"  value="${mygroupVO.groupgo_id}">
			     <input type="hidden" name="mygroup_status"  value="${mygroupVO.mygroup_status}">
			     <input type="hidden" name="action"	value="updateScore"></FORM>
			</td>
			<td>
	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/mygroup/mygroup.do" style="margin-bottom: 0px;">		 
			     <input type="submit" value="刪除">
			     <input type="hidden" name="groupgo_id"  value="${mygroupVO.groupgo_id}">
			     <input type="hidden" name="member_no"  value="${mygroupVO.member_no}">
			     <input type="hidden" name="action" value="delete">
		</FORM>	</td>
		</tr>
	
	</c:forEach>

</table>

</body>
</html>