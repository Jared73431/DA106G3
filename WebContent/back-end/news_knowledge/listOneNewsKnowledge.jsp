<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.newsknowledge.model.*"%>
<%@ page import="java.util.*"%>

<%
String feature = "F0009";
%>
<%@ include file="/back-end/backstage/authList.file" %>
<%@ include file="/back-end/backstage/navbar.file" %>

<%
	NewsKnowledgeVO newsknowledgeVO = (NewsKnowledgeVO) request.getAttribute("newsknowledgeVO"); 
  	String whichPage = (String) request.getAttribute("whichPage");
	//NewsKnowledgeServlet.java(Concroller), 存入req的 newsknowledgeVO 物件
%>
<%
	Map<String, String> news_status = new LinkedHashMap<String, String>();
	news_status.put("1", "上架");
	news_status.put("2", "下架");
	pageContext.setAttribute("map", news_status);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>運動新知資料 - listOneNewsKnowledge.jsp</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/js/vendors/bootstrap/css/bootstrap.min.css">
<style>
  .table th,.table td {
    border: 0px solid #CCCCFF;
  }
  .table td{
    vertical-align:middle;
  }
  .table {
  	margin:0;
  }
</style>
</head>
<body style="background-color:#ECF5FF">

<div class="container-md">
	<div class="row justify-content-center">
		<div class="col-md-4" style="text-align: center;">	
			<h4>新知詳情<a href="<%=request.getContextPath() %>/back-end/news_knowledge/listAllNewsKnowledge.jsp?whichPage=<%= whichPage%>"><img src="<%=request.getContextPath()%>/js/back-end/images/p5.png" style="height:70px;"></a>
			<input type="button" class="btn btn-dark" onclick="location.href='<%=request.getContextPath() %>/back-end/news_knowledge/listAllNewsKnowledge.jsp?whichPage=<%= whichPage%>'" value="返回"/>			
			</h4>
		</div>
	</div>
</div>
<div class="container-md">
	<div class="row justify-content-center">	
		<table class="table col-md-10">
			<tr style="background-color:#FFDAC8;">
				<th>新知編號</th>		
				<th>新知作者</th>		
				<th>新知標題</th>
				<th>新知日期</th>		
				<th>新知狀態</th>		
			</tr>
			<tr style="font-size:14px;">
				<td><%=newsknowledgeVO.getNews_no()%></td>
				<td><%=newsknowledgeVO.getNews_author()%></td>
				<td><%=newsknowledgeVO.getNews_title()%></td>
				<td><fmt:formatDate value="<%=newsknowledgeVO.getNews_date()%>" pattern="yyyy-MM-dd HH:mm"/></td>
				<td>
					<c:forEach var="status" items="${news_status}">
						<c:if test="${(status.key == newsknowledgeVO.news_status)}">
						${status.value}
						</c:if>
					</c:forEach>
				</td>
			</tr>		
		</table>
		<table class="table col-md-10">	
			<tr style="background-color:#FFDAC8;">
				<th>新知封面</th><td></td>	
			</tr>
		</table>
		
		<div class="col-md-6" style="margin:30px auto;">
			<img src="<%=request.getContextPath() %>/PhotoReader?action=news_knowledge&pk_no=${newsknowledgeVO.news_no}" class="img-fluid">
		</div>
		
		<table class="table col-md-10">	
			<tr style="background-color:#FFDAC8;">
				<th>新知內容</th>	<td></td>
			</tr>
		</table>
		<div class="col-md-8" style="margin:30px auto;"><%=newsknowledgeVO.getNews_content()%></div>
	</div>
</div>
</body>
</html>