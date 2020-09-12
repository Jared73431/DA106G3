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
  <li><a href='listAllMembers.jsp'>
  		<input type="button" value="�d�ݩҦ��|��">
  		</a><br><br></li>
  
  
  <li>
    <FORM METHOD="post" ACTION="members.do" >
        <b>��J�|���s�� (�pM000001):</b>
        <input type="text" name="member_no">
        <input type="hidden" name="action" value="getOne_For_Display">
        <input type="submit" value="�e�X">
    </FORM>
  </li>

  <jsp:useBean id="membersSvc" scope="page" class="com.members.model.MembersService" />
   
  <li>
     <FORM METHOD="post" ACTION="members.do" >
       <b>��ܷ|���s��:</b>
       <select size="1" name="member_no">
         <c:forEach var="membersVO" items="${membersSvc.all}" > 
          <option value="${membersVO.member_no}">${membersVO.member_no}
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getOne_For_Display">
       <input type="submit" value="�e�X">
    </FORM>
  </li>
  
  <li>
     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/members.do" >
       <b>��ܷ|���m�W:</b>
       <select size="1" name="member_no">
         <c:forEach var="membersVO" items="${membersSvc.all}" > 
          <option value="${membersVO.member_no}">${membersVO.mem_name}
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getOne_For_Display">
       <input type="submit" value="�e�X">
     </FORM>
  </li>
  
  <li>
  	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/members/members.do" >
  		<b>��ܷ|�����A�h�d��</b>
  		<select name="mem_status">
  			<option value="0">�S���</option>
  			<option value="1">�����</option>
  			<option value="2">�f�֤�</option>
  		</select>		
  		<input type="hidden" name="action" value="getAll_For_Nostatus">
  		<input type="submit" value="�d��">
  	</FORM>
  	</li>
  	
  	<li>
  	<FORM METHOD="post" ACTION="members.do" >
  		 		
  		<a href='membersLogin1.jsp'>
  		<input type="button" value="�n�J">
  		</a>
  	</FORM>
  	</li>
  	
  	<li>
  	<FORM METHOD="post" ACTION="members.do" >
  		 		
  		<a href='membersPage1.jsp'>
  		<input type="button" value="�ӤH����">
  		</a>
  	</FORM>
  	</li>
  	
  	<li>
  	<FORM METHOD="post" ACTION="members.do" >
  		 		
  		<a href='<%=request.getContextPath()%>/transactions/select_page_transactions.jsp'>
  		<input type="button" value="�������">
  		
  		</a>
  	</FORM>
  	</li>
</ul>


<h3>�|���޲z</h3>

<ul>
  <li><a href='addMembers.jsp'>
  	<input type="button" value="�s�W�|��">
  		</a></li>
</ul>

</body>
</html>