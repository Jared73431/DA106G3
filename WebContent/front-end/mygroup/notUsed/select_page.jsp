<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>IBM Mygroup: Home</title>

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
   <tr><td><h3>IBM Mygroup: Home</h3><h4>( MVC )</h4></td></tr>
</table>

<p>This is the Home page for IBM Mygroup: Home</p>

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
  <li><a href='<%=request.getContextPath()%>/front-end/mygroup/listAllGroup.jsp'>List</a> all Group.  <br><br></li>
  
   <jsp:useBean id="groupgoSvc" scope="page" class="com.groupgo.model.GroupgoService" />
   
  <li>
    <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/mygroup/mygroup.do" >
        <b>選擇揪團編號:</b>
        <select size="1" name="groupgo_id">
         <c:forEach var="groupgoVO" items="${groupgoSvc.all}" > 
          <option value="${groupgoVO.groupgo_id}">${groupgoVO.groupgo_id}:${groupgoVO.group_name}
         </c:forEach>   
       </select>
        <input type="hidden" name="action" value="getGroupAttend">
        <input type="submit" value="送出">
    </FORM>
  </li>

<li>
    <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/mygroup/mygroup.do" >
        <b>輸入會員編號:</b>
        <input type="text" name="member_no">
        <input type="hidden" name="action" value="getMemberAttend">
        <input type="submit" value="送出">
    </FORM>
  </li>
   
  
  
  


<h3>員工管理</h3>

<ul>
  <li><a href='addGroup.jsp'>Add</a> a new Group.</li>
</ul>

</body>
</html>