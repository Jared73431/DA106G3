<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.exerciseitem.model.*"%>
<%@ page import="java.util.*"%>
<%
String feature = "F0003";
%>
<%@ include file="/back-end/backstage/authList.file" %>
<%@ include file="/back-end/backstage/navbar.file" %>
<%
	ExerciseItemVO exerciseItemVO = (ExerciseItemVO) request.getAttribute("exerciseItemVO"); 
%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>運動項目修改 </title>

<style>
div#editExe{
	margin: 0 auto;
}

</style>

</head>

<div class="container" id="editExe">

<table id="table-1">
	<tr><td>
		 <h3>運動資料修改 - update_exe_input.jsp</h3>
		 <h4><a href="<%=request.getContextPath()%>/back-end/exerciseitem/listAllExe.jsp">回上一頁</a></h4>
	</td></tr>
</table>

<h3>資料修改:</h3>

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/exerciseitem/exerciseitem.do" name="form1">
<table>
	<tr>
		<td>運動編號:<font color=red><b>*</b></font></td>
		<td><%=exerciseItemVO.getExe_no()%></td>
	</tr>
	<tr>
		<td>運動名稱:</td>
		<td><input type="TEXT" name="exe_item" size="45" value="<%=exerciseItemVO.getExe_item()%>" /></td>
	</tr>
	<tr>
		<td>消耗熱量(每公斤每小時):</td>
		<td><input type="TEXT" name="exe_cal" size="45"	value="<%=exerciseItemVO.getExe_cal()%>" /></td>
	</tr>
	<tr>
	
</table>
<br>
<input type="hidden" name="action" value="update">
<input type="hidden" name="exe_no" value="<%=exerciseItemVO.getExe_no()%>">
<input type="hidden" name="requestURL" value="<%=request.getParameter("requestURL")%>"> 
<input type="hidden" name="whichPage"  value="<%=request.getParameter("whichPage")%>"> 
<input type="submit" value="送出修改"></FORM>
</div>
</div>

</body>
</html>