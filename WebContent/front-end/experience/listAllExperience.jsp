<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.experience.model.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
    ExperienceService expSvc = new ExperienceService();
    List<ExperienceVO> list = expSvc.getAll();
    pageContext.setAttribute("list",list);
%>
<%@ page import="com.members.model.*"%>
    
    
<%
 MembersService memberSvc = new MembersService();
 String member_no = (String)session.getAttribute("member_no");
 MembersVO membersVO = memberSvc.getOneMembers(member_no);
 pageContext.setAttribute("membersVO", membersVO);
%>

<html>
<head>

<title>所有心得資料 - listAllExperience.jsp</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="<%=request.getContextPath() %>/js/vendors/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath() %>/js/vendors/bootstrap/js/bootstrap.min.js">
<script src="https://cdn.ckeditor.com/4.14.0/standard/ckeditor.js"></script>
<link rel="icon" href="<%=request.getContextPath()%>/picture/logo.png">

<style>
  .article .img{
	height: 180px;
	line-height: 180px;
}

.article .img img{
	max-height: 180px;
	max-width: 100%;
}
</style>
</head>
<body bgcolor='white'>
<%@ include file="/front-end/homepage/nav.file"%>
 <script
		src="<%=request.getContextPath()%>/js/vendors/jquery/jquery-3.1.0.min.js"></script>
<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<div class="container-md">
	<div class="row justify-content-center article"><%@ include file="page1.file" %></div>	
	<div class="row article" style="margin:40px auto 0px;">
		<c:forEach var="expVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
		<div class="col-md-5" style="margin-bottom:20px;">
					<div class="row" style="height:18px; font-size:12px;">
						<div class="col-md offset-4">
							<fmt:formatDate value="${expVO.exper_date}" pattern="yyyy-MM-dd HH:mm"/>
						</div>
					</div>					
		<jsp:useBean id="memSvc" scope="page" class="com.members.model.MembersService" />											
					<div class="row" style="height:24px;">							
						<div class="col-md offset-4">
		  
          <c:forEach var="memVO" items="${memSvc.all}">
          <c:if test="${expVO.member_no==memVO.member_no}">
							<b>Author: </b>${memVO.mem_name}
							</c:if>
          </c:forEach>
						</div>							
					</div>
					<div class="row article" style="height:180px;">
						<div class="col-md img offset-4">
							<td><img src="<%=request.getContextPath()%>/experReader?exper_no=${expVO.exper_no}"></td>
						</div>
					</div>
					<div class="row" style="height:80px; margin:10px auto">
						<div class="col-md-9 offset-4">
							<a href="<%=request.getContextPath() %>/experience.do?action=getOne_For_Display&exper_no=${expVO.exper_no}&whichPage=<%=whichPage%>">
								${expVO.exper_title}
							</a>
						</div>
						<div class="col-md-3"></div>
					</div>
				</div>
	</c:forEach>
	</div>
		<div class="row">
			<div class="col-md-12" style="text-align:center;">
				<%@ include file="page2.file" %>
			</div>
		</div>		
	</div>		
</table>



 
</body>
</html>