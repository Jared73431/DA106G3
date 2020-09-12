<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.experience.model.*"%>
<%-- 此頁暫練習採用 Script 的寫法取值 --%>

<%
  ExperienceVO expVO = (ExperienceVO) request.getAttribute("expVO"); //EmpServlet.java(Concroller), 存入req的empVO物件
  String whichPage = (String) request.getAttribute("whichPage");
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
<title>員工資料 - listOneExperience.jsp</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/js/vendors/bootstrap/css/bootstrap.min.css">
<link rel="icon" href="<%=request.getContextPath()%>/picture/logo.png">
<style>
.article .img{
	height: 350px;
	box-sizing: border-box;
	text-align:center;
}

.article .img img{
	max-height: 350px;
	max-width: 100%;
}

.inner img{
	max-width:100%;
}
</style>

</head>
<body bgcolor='white'>
<%@ include file="/front-end/homepage/nav.file"%>
<div id="listall">
	<div class="container-md">
		<div class="row justify-content-center article">
			<div class="col-md-8" style="text-align:center">
				 <h3>心得詳情
			    	<input type="button" class="btn btn-dark" 
			    	onclick="location.href='<%=request.getContextPath() %>/front-end/experience/listAllExperience.jsp?whichPage=<%=whichPage %>'" value="返回"/>
			    	<input type="button" class="btn btn-dark" 
			    	onclick="location.href='<%=request.getContextPath() %>/experience.do?action=getOne_For_Update&exper_no=${expVO.exper_no}'" value="修改"/>
				</h3>
				<hr>
			</div>
		</div>	
		<div class="row justify-content-center article" style="height:350px; margin:20px auto 30px;">
			<!-- 大圖 -->
			<div class="col-md-10 img">
				<td><img src="<%=request.getContextPath()%>/experReader?exper_no=${expVO.exper_no}"></td>			
			</div>					
		</div>
			<!-- 時間 -->
		<div class="row article" style="height:30px; margin:10px auto;">
			<div class="col-md-3 offset-2">
				<fmt:formatDate value="<%=expVO.getExper_date()%>" pattern="yyyy-MM-dd HH:mm"/>			
			</div>					
		</div>		
			<!-- 作者 -->
		<div class="row article" style="height:30px; margin:10px auto;">
			<div class="col-md-3 offset-2">
				<i>Author: <%=expVO.getMember_no()%></i>
			</div>					
		</div>
			<!-- 標題 -->		
		<div class="row article" style="height:85px; margin:10px auto;">
			<div class="col-md-8 offset-2">
				<h3><%=expVO.getExper_title()%></h3>
			</div>					
		</div>
		<!-- 分類 -->
		<div class="row article" style="height:30px; margin:10px auto;">
			<div class="col-md-3 offset-2">
				<i>分類: 
				<c:forEach var="status" items="${cate_no}">
					<c:if test="${status.key == expVO.cate_no}">
						${status.value}
					</c:if>
				</c:forEach></i>
			</div>
			</div>					
			<!-- 內容 -->		
		<div class="row inner" style="margin:10px auto;">
			<div class="col-md-8 offset-2">
				<%=expVO.getExper_context()%>			
			</div>					
		</div>
	</div>
	</div>
<%@ include file="/front-end/homepage/footer.file"%>
</body>
</html>