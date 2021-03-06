<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.foodinform.model.*"%>
<%@ page import="com.util.controller.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>
<%
    FoodInformService foodinformSvc = new FoodInformService();
    List<FoodInformVO> list = foodinformSvc.getAuth();
    pageContext.setAttribute("list",list);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>所有食訊資料 - listAllFoodInform.jsp</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/js/vendors/bootstrap/css/bootstrap.min.css">
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
.badge{
	font-size:14px;
}
</style>

</head>
<body style='background-color:#FDFFFF'>
<!-- 套用首頁標頭 -->
<%@ include file="/front-end/homepage/nav.file"%>
<!-- 套用首頁標頭 -->
<div id="listall">

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
			<c:forEach var="foodinformVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">				
				<div class="col-md-5" style="margin-bottom:20px;">
					<div class="row" style="height:18px; font-size:12px;">
						<div class="col-md offset-4">
							<fmt:formatDate value="${foodinformVO.fi_date}" pattern="yyyy-MM-dd HH:mm"/>
						</div>
					</div>													
					<div class="row" style="height:24px;">							
						<div class="col-md offset-4">
							<b>Author: </b>${foodinformVO.fi_author}
						</div>							
					</div>
					<div class="row article" style="height:180px;">
						<div class="col-md img offset-4">
							<img src="<%=request.getContextPath() %>/PhotoReader?action=food_inform&pk_no=${foodinformVO.fi_no}">
						</div>
					</div>
					<div class="row" style="height:80px; margin:10px auto">
						<div class="col-md-9 offset-4">
							<a href="<%=request.getContextPath() %>/food_inform.do?action=getOne_Display&fi_no=${foodinformVO.fi_no}&whichPage=<%=whichPage%>">
								${foodinformVO.fi_title}
							</a>
						</div>
						<div class="col-md-3"></div>
					</div>
				</div>
			</c:forEach>
		</div>
		<div class="row">
			<div class="col-md-12" style="text-align:center; margin-bottom:30px;">
				<%@ include file="page2.file" %>
			</div>
		</div>		
	</div>
</div>
<!-- 首頁註腳 -->
<%@ include file="/front-end/homepage/footer.file"%>	
<!-- 首頁註腳 -->
			
</body>
</html>