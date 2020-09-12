<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<html>
<head>
<title>IBM Members: Home</title>

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
   <tr><td><h3>IBM Members: Home1</h3><h4>( MVC )</h4></td></tr>
</table>

<p>This is the Home page for IBM Members: Home</p>

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
  <li><a href='listAllMembers.jsp'>
  		<input type="button" value="查看所有會員">
  		</a><br><br></li>
  
  
  <li>
    <FORM METHOD="post" ACTION="members.do" >
        <b>輸入會員編號 (如M000001):</b>
        <input type="text" name="member_no">
        <input type="hidden" name="action" value="getOne_For_Display">
        <input type="submit" value="送出">
    </FORM>
  </li>

  <jsp:useBean id="membersSvc" scope="page" class="com.members.model.MembersService" />
   
  <li>
     <FORM METHOD="post" ACTION="members.do" >
       <b>選擇會員編號:</b>
       <select size="1" name="member_no">
         <c:forEach var="membersVO" items="${membersSvc.all}" > 
          <option value="${membersVO.member_no}">${membersVO.member_no}
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getOne_For_Display">
       <input type="submit" value="送出">
    </FORM>
  </li>
  
  <li>
     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/members.do" >
       <b>選擇會員姓名:</b>
       <select size="1" name="member_no">
         <c:forEach var="membersVO" items="${membersSvc.all}" > 
          <option value="${membersVO.member_no}">${membersVO.mem_name}
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getOne_For_Display">
       <input type="submit" value="送出">
     </FORM>
  </li>
  
  <li>
  	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/members/members.do" >
  		<b>選擇會員狀態去查詢</b>
  		<select name="mem_status">
  			<option value="0">沒資格</option>
  			<option value="1">有資格</option>
  			<option value="2">審核中</option>
  		</select>		
  		<input type="hidden" name="action" value="getAll_For_Nostatus">
  		<input type="submit" value="查詢">
  	</FORM>
  	</li>
  	
  	<li>
  	<FORM METHOD="post" ACTION="members.do" >
  		 		
  		<a href='membersLogin1.jsp'>
  		<input type="button" value="登入">
  		</a>
  	</FORM>
  	</li>
  	
  	<li>
  	<FORM METHOD="post" ACTION="members.do" >
  		 		
  		<a href='membersPage1.jsp'>
  		<input type="button" value="個人頁面">
  		</a>
  	</FORM>
  	</li>
  	
  	<li>
  	<FORM METHOD="post" ACTION="members.do" >
  		 		
  		<a href='<%=request.getContextPath()%>/transactions/select_page_transactions.jsp'>
  		<input type="button" value="交易紀錄">
  		
  		</a>
  	</FORM>
  	</li>
</ul>


<h3>會員管理</h3>

<ul>
  <li><a href='addMembers.jsp'>
  	<input type="button" value="新增會員">
  		</a></li>
</ul>

</body>
</html>