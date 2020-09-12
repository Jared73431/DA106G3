<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.newsknowledge.model.*"%>
<%-- 此頁暫練習採用 Script 的寫法取值 --%>
<%
  NewsKnowledgeVO newsknowledgeVO = (NewsKnowledgeVO) request.getAttribute("newsknowledgeVO"); 
  //NewsKnowledgeServlet.java(Concroller), 存入req的 newsknowledgeVO 物件
  String whichPage = (String) request.getAttribute("whichPage");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>運動新知資料 - listOneNewsKnowledge.jsp</title>
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
<body style='background-color:#FDFFFF'>
<!-- 套用首頁標頭 -->
<%@ include file="/front-end/homepage/nav.file"%>
<!-- 套用首頁標頭 -->
<div id="listall">
	<div class="container-md">
		<div class="row justify-content-center article">
			<div class="col-md-8" style="text-align:center">
				 <h3>新知詳情
			    	<input type="button" class="btn btn-dark" 
			    	onclick="location.href='<%=request.getContextPath() %>/front-end/news_knowledge/listAllNewsKnowledge.jsp?whichPage=<%=whichPage %>'" value="返回"/>
				</h3>
				<hr>
			</div>
		</div>
		<div class="row justify-content-center article" style="height:350px; margin:20px auto 30px;">
			<!-- 大圖 -->
			<div class="col-md-10 img">
				<img src="<%=request.getContextPath() %>/PhotoReader?action=news_knowledge&pk_no=${newsknowledgeVO.news_no}">			
			</div>					
		</div>
			<!-- 時間 -->
		<div class="row article" style="height:30px; margin:10px auto;">
			<div class="col-md-3 offset-2">
				<fmt:formatDate value="<%=newsknowledgeVO.getNews_date()%>" pattern="yyyy-MM-dd HH:mm"/>			
			</div>					
		</div>		
			<!-- 作者 -->
		<div class="row article" style="height:30px; margin:10px auto;">
			<div class="col-md-4 offset-2">
				<i>Author: <%=newsknowledgeVO.getNews_author()%></i>
			</div>					
		</div>
			<!-- 標題 -->		
		<div class="row article" style="height:85px; margin:10px auto;">
			<div class="col-md-8 offset-2">
				<h3><%=newsknowledgeVO.getNews_title()%></h3>
			</div>					
		</div>
			<!-- 內容 -->		
		<div class="row inner" style="margin:10px auto 40px;">
			<div class="col-md-8 offset-2">
				<%=newsknowledgeVO.getNews_content()%>			
			</div>					
		</div>
	</div>
</div>
<!-- 首頁註腳 -->
<%@ include file="/front-end/homepage/footer.file"%>	
<!-- 首頁註腳 -->	
</body>
</html>