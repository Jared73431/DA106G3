<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.newsknowledge.model.*"%>
<%@ page import="com.util.controller.*"%>

<%
String feature = "F0009";
%>
<%@ include file="/back-end/backstage/authList.file" %>
<%@ include file="/back-end/backstage/navbar.file" %>

<%
    NewsKnowledgeService newsknowledgeSvc = new NewsKnowledgeService();
    List<NewsKnowledgeVO> list = newsknowledgeSvc.getAll();
    pageContext.setAttribute("list",list);
%>
<%
	Map<String, String> news_status = new LinkedHashMap<String, String>();
	news_status.put("1", "上架");
	news_status.put("2", "下架");
	pageContext.setAttribute("news_status", news_status);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新知管理 - listAllNewsKnowledge.jsp</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/js/vendors/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath() %>/js/bootstrap4-toggle/bootstrap4-toggle.min.css">
<style>
	/* 新知編號 */
	tbody td:first-child {
		width:2em;
  		font: 14px italic;
	}
	/* 新知標題 */
	tbody td:nth-child(2) {
		width:16em;
		height:6.2em;
 	}
	/* 新知作者 */
	tbody td:nth-child(3), tbody td:nth-child(4){
		width:8em;
		font: 14px italic;
 	}
	/* 新知日期 */
	tbody td:nth-child(4){
		width:6.5em;
 	}	
	/* 新知狀態 */
	tbody td:nth-child(5),thead th:nth-child(5) {
		text-align:center;
		width:6em;
 	}
	/* 新知操作 */
	tbody td:nth-child(6),thead th:nth-child(6) {
		text-align:center;
		width:7em;
 	}
 	tbody td:nth-child(6) form{
 		display:inline;
 	}
	/* 表格內容垂直置中 */
	.table th, .table td{
	  vertical-align: middle;
	}

</style>


</head>
<body bgcolor='white'>

<div class="container-md">
	<div class="row justify-content-center">
		<div class="col-md-4" style="text-align: center;">	
			<h4>運動新知管理<a href="<%=request.getContextPath() %>/back-end/backstage/index.jsp"><img src="<%=request.getContextPath()%>/js/back-end/images/p5.png" style="height:70px;"></a>
			<input type="button" class="btn btn-dark" onclick="location.href='<%=request.getContextPath() %>/back-end/backstage/index.jsp'" value="返回"/>			
			</h4>		
		</div>
	</div>
</div>

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
<div class="row justify-content-center">
	<div class="col-md-10">
		<div style="text-align:center;">
			<%@ include file="page1.file" %> 
		</div>
	    <table class="table table-hover">
		  <thead>
		    <tr>		    
		      <th scope="col" class="text-nowrap">新知編號</th>
		      <th scope="col" class="text-nowrap">新知標題</th>
		      <th scope="col" class="text-nowrap">新知作者</th>
		      <th scope="col" class="text-nowrap">新知日期</th>
		      <th scope="col" class="text-nowrap">新知狀態</th>		      
		      <th scope="col" class="text-nowrap">資料操作</th>      
		    </tr>
		  </thead>
		  <tbody>
			<c:forEach var="newsknowledgeVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
			    <tr>
			      <td>${newsknowledgeVO.news_no}</td>
			      <td>${newsknowledgeVO.news_title}</td>
			      <td>${newsknowledgeVO.news_author}</td>
			      <td><fmt:formatDate value="${newsknowledgeVO.news_date}" pattern="yyyy-MM-dd"/></td>			      
			      <td>
						<input name="${newsknowledgeVO.news_no}" type="checkbox" ${(newsknowledgeVO.news_status == 1) ? 'checked':'' } 
						data-toggle="toggle" data-size="sm" data-on="上架" data-off="下架">
				  </td>
			      <td>
			      	<FORM METHOD="post" ACTION="<%=request.getContextPath() %>/news_knowledge.do" style="margin-bottom: 0px;">
				     	<span>
				     		<input type="submit" value="詳情">
				     	</span>
					     	<input type="hidden" name="news_no"  value="${newsknowledgeVO.news_no}">
					     	<input type="hidden" name="whichPage"  value="<%=whichPage%>">
					     	<input type="hidden" name="action" value="getOne_For_Display">
					</FORM>			      	
				  	<FORM METHOD="post" ACTION="<%=request.getContextPath() %>/news_knowledge.do" style="margin-bottom: 0px;">
					    <span>						    
						    <input type="submit" value="修改">
						</span>    
						    <input type="hidden" name="news_no"  value="${newsknowledgeVO.news_no}">
			     			<input type="hidden" name="whichPage"  value="<%=whichPage%>">
			     			<input type="hidden" name="action" value="getOne_For_Update"></FORM>				    
			      </td>
			    </tr>
		    </c:forEach>		    
		  </tbody>
		</table>
		<div style="text-align:center;">
		<%@ include file="page2.file" %>
		</div>		
	</div>	
</div>
</div>
	<script src="<%=request.getContextPath() %>/js/vendors/jquery/jquery-3.4.1.min.js"></script>
	<script src="<%=request.getContextPath() %>/js/vendors/popper/popper.min.js"></script>
	<script src="<%=request.getContextPath() %>/js/vendors/bootstrap/js/bootstrap.min.js"></script>
	<script src="<%=request.getContextPath() %>/js/bootstrap4-toggle/bootstrap4-toggle.min.js"></script>
    <script>
    	/* Ajax操作狀態 */
    	var news_status;
    	var news_no;

    	$(':checkbox').on('change',function(){
			news_no=$(this).attr("name");
			if($(this).is(':checked')){
				console.log("on");
				var news_status = 1;
			}else{
				console.log("off");
				var news_status = 2;
			}
			console.log(news_status);
			console.log(news_no);
			$.ajax({
				url: '<%=request.getContextPath()%>/news_knowledge.do',
				type: "POST",
				data: {
					action : 'AJAX_For_Status',
					news_no : news_no,
					news_status : news_status
				},
				dataType: "text",
				error: function(xhr){
					console.log(xhr);
				},
				success: function(data){
					console.log(data);
				}
			});
		});
	</script>
</body>
</html>