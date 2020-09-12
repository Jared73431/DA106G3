<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.dietrecord.model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>dietrecord: Home</title>

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
   <tr><td><h3>dietrecord: Home</h3><h4>( MVC )</h4></td></tr>
</table>

<p>This is the Home page for dietrecord: Home</p>

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
  <li><a href='<%=request.getContextPath()%>/front-end/dietrecord/listAllDietRecord.jsp'>List</a> all dietrecord.  <br><br></li>
 
  <li>
    <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/dietrecord/dietrecord.do" >
        <b>輸入紀錄編號 (如DR000001):</b>
        <input type="text" name="diet_no">
        <input type="hidden" name="action" value="getOne_For_Display">
        <input type="submit" value="送出">
    </FORM>
  </li>

 
    </FORM>
  </li>
  <jsp:useBean id="dietrecordSvc" scope="page" class="com.dietrecord.model.DietRecordService" />
  <li>
     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/dietrecord/dietrecord.do" >
       <b>選擇紀錄編號:</b>
       <select size="1" name="diet_no">
         <c:forEach var="dietrecordVO" items="${dietrecordSvc.all}" > 
          <option value="${dietrecordVO.diet_no}">${dietrecordVO.diet_no}
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getOne_For_Display">
       <input type="submit" value="送出">
     </FORM>
  </li>
</ul>
 

<h3>飲食紀錄管理</h3>

<ul>
  <li><a href='<%=request.getContextPath()%>/front-end/dietrecord/addDietRecord.jsp'>Add</a> a new diet.</li>
</ul>

</body>
</html>