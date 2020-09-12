<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.newsknowledge.model.*"%>
<%@ page import="com.foodinform.model.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>DA106G3資訊頁</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/js/vendors/bootstrap/css/bootstrap.min.css">
<link rel="icon" href="<%=request.getContextPath()%>/picture/logo.png">
<%
  	// 呼叫新知領班給予隨機資料
  	NewsKnowledgeService nkSvc = new NewsKnowledgeService();
  	List<NewsKnowledgeVO> news_list = nkSvc.getRandom();
	pageContext.setAttribute("news_list",news_list);
  	// 呼叫食訊領班給予隨機資料
  	FoodInformService fiSvc = new FoodInformService();
  	List<FoodInformVO> fi_list = fiSvc.getRandom();
  	pageContext.setAttribute("fi_list", fi_list);
%>


<style>
#infrom_title{
	text-align:center;
}
#inform{
	margin:40px auto;
	box-sizing: border-box;
}


.article{
	height: 250px;
	overflow: hidden;
}
.article .img{
	height: 200px;
	line-height: 180px;
	text-align: center;
}

.article .img img{
	max-height: 150px;
	max-width: 100%;
}

#carouselExampleIndicators{
	max-height: 220px;
	overflow: hidden;
}

</style>

</head>

<body style='background-color:#FDFFFF'>
<!-- 套用首頁標頭 -->
<%@ include file="/front-end/homepage/nav.file"%>
<!-- 套用首頁標頭 -->
<div id="listall">
	<!-- 輪播牆 -->
	<div class="container-md" style="margin:30px auto">
		<hr>
			<div class="row justify-content-center col-md-12">
				<div  id="carouselExampleIndicators" class="carousel slide col-md-4" data-ride="carousel">
				<!-- 輪播點選 -->  
				  <ol class="carousel-indicators">
				    <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
				    <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
				    <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
				  </ol>
				<!-- 輪播內容 -->
				  <div class="carousel-inner">
					<div class="carousel-item active">
						<img class="d-block w-100" src="<%=request.getContextPath() %>/front-end/inform_cate/images/run.jpg" alt="Second slide">
					</div>
					<div class="carousel-item">
						<img class="d-block w-100" src="<%=request.getContextPath() %>/front-end/inform_cate/images/food1.jpg" alt="Third slide">
					</div>
					<div class="carousel-item">
						<img class="d-block w-100" src="<%=request.getContextPath() %>/front-end/inform_cate/images/food2.jpg" alt="Forth slide">
					</div>		
				  </div>
			  <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
			    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
			    <span class="sr-only">Previous</span>
			  </a>
			  <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
			    <span class="carousel-control-next-icon" aria-hidden="true"></span>
			    <span class="sr-only">Next</span>
			  </a>
			</div>	
		</div>
		<hr>
	</div>
		
		  <!-- 食訊 -->
		<c:forEach var="foodinformVO" items="${fi_list}">	  
		  <div class="container-md">
			<div class="row justify-content-center article">
				<div class="col-md-1"></div>
				<div class="col-md-3 article img">
					<img src="<%=request.getContextPath() %>/PhotoReader?action=food_inform&pk_no=${foodinformVO.fi_no}">
			  	</div>
			  	<div class="col-md-6 article">
			  		<div class="col-md-10" style="display: inline-block;vertical-align: text-top;">	
						<h4 style="margin-top:25px;">${foodinformVO.fi_title}</h4>
						<p><fmt:formatDate value="${foodinformVO.fi_date}" pattern="yyyy-MM-dd HH:mm"/> 
							<b style="margin-left:10px;">Author: </b>${foodinformVO.fi_author}</p>
			  		</div>
			  		<div class="col-md-3 offset-7">
						<a href="<%=request.getContextPath() %>/food_inform.do?action=getOne_Display&fi_no=${foodinformVO.fi_no}">show detail</a> 	  			
			  		</div>
			  	</div>
			</div>	  	 			
		</div>
		</c:forEach>
			<div class="list_more" style="margin:0px auto 30px">
				<div class="row justify-content-center">
					<div class="col-md-2">
						<a class="col-md-12 btn btn-outline-secondary" href="<%=request.getContextPath() %>/front-end/food_inform/listAllFoodInform.jsp" target="_self">更多食訊</a>
					</div>
				</div>
			</div>	
		
	  		<!-- 運動新知 -->
			<c:forEach var="newsknowledgeVO" items="${news_list}">	  
			<div class="container-md">
				<div class="row justify-content-center article">
					<div class="col-md-1"></div>
					<div class="col-md-3 article img">
						<img src="<%=request.getContextPath() %>/PhotoReader?action=news_knowledge&pk_no=${newsknowledgeVO.news_no}">
				  	</div>
				  	<div class="col-md-6 article">
					  	<div class="col-md-10">
							<h4 style="margin-top:25px">${newsknowledgeVO.news_title}</h4>
							<p><fmt:formatDate value="${newsknowledgeVO.news_date}" pattern="yyyy-MM-dd HH:mm"/>
								<b style="margin-left:10px;">Author: </b>${newsknowledgeVO.news_author}</p>
					  	</div>
					  	<div class="col-md-3 offset-7">
							<a href="<%=request.getContextPath() %>/news_knowledge.do?action=getOne_Display&news_no=${newsknowledgeVO.news_no}" target="_self">show detail</a> 	  			
				  		</div>
				  	</div>
				</div>	  	
			</div>  		
			</c:forEach>
			<div class="list_more" style="margin:0px auto 30px">
				<div class="row justify-content-center">
					<div class="col-md-2">
						<a class="col-md-12 btn btn-outline-secondary" href="<%=request.getContextPath() %>/front-end/news_knowledge/listAllNewsKnowledge.jsp">更多新知</a>
					</div>
				</div>
			</div>
</div>
<!-- 首頁註腳 -->
<%@ include file="/front-end/homepage/footer.file"%>	
<!-- 首頁註腳 -->
	<script src="<%=request.getContextPath() %>/js/vendors/jquery/jquery-3.4.1.min.js"></script>
	<script src="<%=request.getContextPath() %>/js/vendors/popper/popper.min.js"></script>
	<script src="<%=request.getContextPath() %>/js/vendors/bootstrap/js/bootstrap.min.js"></script>
	<script src="<%=request.getContextPath() %>/js/jquery-ui/jquery.min.js"></script>
   	<script src="<%=request.getContextPath() %>/js/jquery-ui/jquery-ui.min.js"></script>			
	
</body>
</html>