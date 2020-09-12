<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.followmaster.model.*"%>
<%@ page import="com.members.model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>followMaster: Home</title>

<style>
  table#table-1 {
	width: 450px;
	background-color: #CCCCFF;
	margin-top: 5px;
	margin-bottom: 10px;
    border: 3px ridge Gray;
    height: 80px;
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

</head>
<body bgcolor='white'>

<table id="table-1">
   <tr><td><h3>follow master: Home</h3><h4>( MVC )</h4></td></tr>
</table>

<p>This is the Home page for Follow: Home</p>

<h3>資料查詢:</h3>
	
<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
	    <c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<ul>
  <li><a href='<%=request.getContextPath()%>/front-end/followmaster/listAllFollow.jsp'>List</a> all follow.  <br><br></li>
 
 <jsp:useBean id="memberSvc" scope="page" class="com.members.model.MembersService" />
  <li>
    <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/followmaster/followmaster.do" >
        <b>選擇會員編號:</b>
        
        <select size="1" name="member_no">
        <c:forEach var="membersVO" items="${memberSvc.all}" >
        	<option value="${membersVO.member_no}">${membersVO.member_no}
        </c:forEach>
        </select>
 
        <input type="hidden" name="action" value="getMaster">
        <input type="submit" value="送出">
    </FORM>
  </li>

   
<h3>追蹤管理</h3>

<ul>
  <li><a href='addFollow.jsp'>Add</a> a new follower.</li>
</ul>

</body>
</html>