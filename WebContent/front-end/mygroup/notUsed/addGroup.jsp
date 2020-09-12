<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.mygroup.model.*"%>
<%@ page import="com.groupgo.model.*"%>

<% MyGroupVO mygroupVO = (MyGroupVO)request.getAttribute("mygroupVO"); %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="BIG5">
<title>加入揪團</title>
</head>
<body>
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
		 <h3>資料新增 - addGroup.jsp</h3></td><td>
		 <h4><a href="<%=request.getContextPath()%>/front-end/mygroup/select_page.jsp">回首頁</a></h4>
	</td></tr>
</table>

<h3>資料新增:</h3>

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/mygroup/mygroup.do" name="form1">
<table>
	<tr>
		<td>會員編號:</td>
		<td><input type="TEXT" name="member_no"
			 value="<%= (mygroupVO==null)? "M000001" : mygroupVO.getMember_no()%>" /></td>
	</tr>
	
	

	<jsp:useBean id="groupgoSvc" scope="page" class="com.groupgo.model.GroupgoService" />
	<tr>
		<td>揪團名稱:</td>
		<td><select size="1" name="groupgo_id">
			<c:forEach var="groupgoVO" items="${groupgoSvc.all}">
				<option value="${groupgoVO.groupgo_id}" ${(mygroupVO.groupgo_id==groupgoVO.groupgo_id)? 'selected':'' } >${groupgoVO.group_name} 
			</c:forEach>
		</select></td>
	</tr>
	
	

</table>
<br>
<input type="hidden" name="action" value="insert">
<input type="submit" value="送出新增"></FORM>
</body>

</body>
</html>