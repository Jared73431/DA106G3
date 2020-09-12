<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"  %>
<%@ page import="com.transactions.model.*" %>
<%@ page import="com.members.model.*" %>
    
    <%
	MembersService memberSvc = new MembersService();
	String member_no = (String)session.getAttribute("member_no");
// 	MembersVO membersVO = memberSvc.getOneMembers(member_no);
// 	pageContext.setAttribute("membersVO", membersVO);
	
	TransactionsService transactionsSvc = new TransactionsService();
// 	String member_no = (String)request.getAttribute("attend_no");
	List<TransactionsVO> list = transactionsSvc.getOneByMem(member_no);
	pageContext.setAttribute("list", list);
	list.forEach(trans -> trans.toString());
%>
<!DOCTYPE html>
<html>
<head>
<link rel="icon" href="<%=request.getContextPath()%>/picture/logo.png">
<link rel="stylesheet" href="<%=request.getContextPath()%>/js/vendors/bootstrap/css/bootstrap.min.css">
<meta charset="BIG5">
<title>Insert title here</title>

<style type="text/css">
.table{
width:85%;
margin:0 auto;
}

.btn{
position:"fixed";
right:2rem;
bottom:0;
}
</style>
</head>
<body>
<%@ include file="/front-end/homepage/nav.file"%>
<div id="listall">


<table class="table">
  <thead class="thead-light">
    <tr>
      <th scope="col">交易編號</th>
      <th scope="col">會員編號</th>
      <th scope="col">存入金額</th>
      <th scope="col">提款金額</th>
      <th scope="col">交易日期</th>
      <th scope="col">交易備註</th>
    </tr>
  </thead>
  <c:forEach var="transactionsVO" items="${list}">
  <tbody>
    <tr>
      <th scope="row">${transactionsVO.trans_no}</th>
      <td>${transactionsVO.member_no}</td>
      <td>${transactionsVO.deposit}</td>
      <td>${transactionsVO.withdraw}</td>
      <td>${transactionsVO.tran_date}</td>
      <td>${transactionsVO.remark}</td>
    </tr>
    
  </tbody>
  </c:forEach>
  
  <a href="<%=request.getContextPath()%>/front-end/members/membersPage.jsp"><button class="btn btn-info" id="btn">回個人首頁</button></a>
</table>




		</div>
		
		<script src="<%=request.getContextPath()%>/js/vendors/jquery/jquery-3.4.1.min.js"></script>
<script src="<%=request.getContextPath()%>/js/vendors/popper/popper.min.js"></script>
<script src="<%=request.getContextPath()%>/js/vendors/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>