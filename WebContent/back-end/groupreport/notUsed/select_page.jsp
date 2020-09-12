<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>


<%
	
	Map<Integer,String> status = new HashMap<>();
	status.put(1, "���B�z");
	status.put(2, "�f�ֳq�L");
	status.put(3, "�w�U�[");
	pageContext.setAttribute("status", status);
	
%>
<!DOCTYPE html>
<html>
<head>
<title>GroupReport: Home</title>

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
   <tr><td><h3>GroupReport: Home</h3><h4>( MVC )</h4></td></tr>
</table>

<p>This is the Home page for GroupReport: Home</p>

<h3>��Ƭd��:</h3>
	
<%-- ���~��C --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">�Эץ��H�U���~:</font>
	<ul>
	    <c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<ul>
  <li><a href='<%=request.getContextPath()%>/back-end/groupreport/listAllGroupReport.jsp'>List</a> all GroupReport.  <br><br></li>
  
  
  <li>
    <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/groupreport/groupreport.do" >
        <b>��ܳB�z���A:</b>
      
        
       <select name="report_status">			
		<c:forEach var="report_status" items="${status}">
			<option value=${report_status.key}>${report_status.value}</option>
		</c:forEach>
		</select>
     <input type="hidden" name="action" value="getStatus">
        <input type="submit" value="�e�X">
    </FORM>
  </li>


<h3>�s�W���|</h3>

<ul>
  <li><a href='<%=request.getContextPath()%>/front-end/groupreport/addGroupReport.jsp'>Add</a> a new GroupReport.</li>
</ul>

</body>
</html>