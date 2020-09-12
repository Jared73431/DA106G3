<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.categorys.model.*"%>
<%@ page import="java.util.*"%>
<%
  CategorysVO cateVO = (CategorysVO) request.getAttribute("categorysVO"); //EmpServlet.java (Concroller) 存入req的empVO物件 (包括幫忙取出的empVO, 也包括輸入資料錯誤時的empVO物件)
%>
<%
String feature = "F0007";
%>
<%@ include file="/back-end/backstage/authList.file" %>
<%@ include file="/back-end/backstage/navbar.file" %>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>員工資料修改 - update_categorys_input.jsp</title>

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
		 <input type="button" class="btn btn-dark" 
			    	onclick="location.href='<%=request.getContextPath() %>/back-end/categorys/listAllCategorys.jsp?whichPage'" value="返回"/>
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

<FORM METHOD="post" ACTION="categorys.do" name="form1">
<table>
	<tr>
		<td>類別編號:<font color=red><b>*</b></font></td>
		<td><%=cateVO.getCategory_no()%></td>
	</tr>
	<tr>
		<td>類別內容:</td>
		<td><input type="TEXT" name="category_name" size="45" value="<%=cateVO.getCategory_name()%>" /></td>
	</tr>
	

</table>
<br>
<input type="hidden" name="action" value="update">
<input type="hidden" name="category_no" value="<%=cateVO.getCategory_no()%>">
<input type="submit" value="送出修改"></FORM>
</body>




</html>