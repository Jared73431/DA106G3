<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.posture.model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>posture: Home</title>

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
   <tr><td><h3>posture: Home</h3><h4>( MVC )</h4></td></tr>
</table>

<p>This is the Home page for posture: Home</p>

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
  <li><a href='<%=request.getContextPath()%>/front-end/posture/listAllPosture.jsp'>List</a> all posture.  <br><br></li>
  <li>
    <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/posture/posture.do" >
        <b>輸入會員編號 (如M000001):</b>
        <input type="text" name="member_no">
        <input type="hidden" name="action" value="getOne_For_Display">
        <input type="submit" value="送出">
    </FORM>
  </li>


<ul>
  <li><a href='<%=request.getContextPath()%>/front-end/posture/listAllPosture.jsp'>List</a> all posture.  <br><br></li>
  <li>
    <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/posture/posture.do" >
        <b>要新增的會員編號 (如M000001):</b>
        <input type="text" name="member_no">
        <input type="hidden" name="action" value="ready_insert">
        <input type="submit" value="送出">
    </FORM>
  </li>
<h3>管理</h3>

<ul>
  <li><a href='<%=request.getContextPath()%>/front-end/posture/addPosture.jsp'>Add</a> a new posture.</li>
</ul>

</body>
</html>
  