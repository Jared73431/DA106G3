<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.newsknowledge.model.*"%>
<%@ page import="java.util.*"%>

<%
String feature = "F0009";
%>
<%@ include file="/back-end/backstage/authList.file" %>
<%@ include file="/back-end/backstage/navbar.file" %>

<%
  NewsKnowledgeVO newsknowledgeVO = (NewsKnowledgeVO) request.getAttribute("newsknowledgeVO");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新知新增 - addNewsKnowledge.jsp</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/js/vendors/bootstrap/css/bootstrap.min.css">
<script src="<%=request.getContextPath() %>/js/vendors/jquery/jquery-3.5.0.min.js"></script>
<script src="<%=request.getContextPath() %>/js/ckeditor/ckeditor.js"></script>
<style>
  .table tbody td {
    border: 0px solid #FFFFFF;
  }
  th, td {
    padding: 1px;
  }
</style>
</head>
<body style="background-color:#ECF5FF">

<div class="container-md">
	<div class="row justify-content-center">
		<div class="col-md-4" style="text-align: center;">	
			<h4>新知新增<a href="<%=request.getContextPath() %>/back-end/news_knowledge/listAllNewsKnowledge.jsp"><img src="<%=request.getContextPath()%>/js/back-end/images/p5.png" style="height:70px;"></a>
			<input type="button" class="btn btn-dark" onclick="location.href='<%=request.getContextPath() %>/back-end/news_knowledge/listAllNewsKnowledge.jsp'" value="返回"/>			
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
		<FORM METHOD="post" ACTION="<%=request.getContextPath() %>/news_knowledge.do" name="form1" enctype="multipart/form-data">
		<table class="table col-md-8 offset-2">
			<tbody>
				<tr>
					<td>新知標題:</td>
					<td>
						<input type="text" name="news_title" id="news_title" class="form-control" placeholder="請填寫新知標題" aria-label="News title" aria-describedby="basic-addon1"
							value="<%= (newsknowledgeVO==null)? "" : newsknowledgeVO.getNews_title()%>" />
					</td>
				</tr>
				<tr>
					<td>新知作者:</td>
					<td>
						<input type="text" name="news_author" id="news_author" class="form-control" placeholder="請填寫新知作者" aria-label="News author" aria-describedby="basic-addon1"
							value="<%= (newsknowledgeVO==null)? "" : newsknowledgeVO.getNews_author()%>" />				
					</td>
				</tr>
				<tr>
					<td>新知封面:</td>
						<td><input class="upload" type="FILE" name="news_cover" size="45"/>
					</td>
	 			</tr>
				<tr>
					<td>新知內容:</td>
				</tr>
			</tbody>	
		</table>
			<textarea name="news_content"><%= (newsknowledgeVO==null)? "" : newsknowledgeVO.getNews_content()%></textarea>
				<script>
					CKEDITOR.replace('news_content', {
			            language : 'zh-tw',				//設定語言
			            height: 400,
			            width: 750,
			            uiColor : '#ECF8FF',
			            resize_enabled : false			//避免使用者拖拉外框
			        });
				</script>

		<input type="hidden" name="action" value="insert">
		<input type="submit" value="送出新增" class="btn btn-light col-md-2 offset-10" style="margin-top:20px;"></FORM>	
	</div>			
	<div class="row justify-content-center">		
		<div class="col-md-3">
			<img class="preview img-fluid">
			<div class="size"></div>
		</div>
	</div>
</div>	
</body>
<script>
$(function (){
 
    function format_float(num, pos)
    {
        var size = Math.pow(10, pos);
        return Math.round(num * size) / size;
    }
 
    function preview(input) {
 
        if (input.files && input.files[0]) {
            var reader = new FileReader();
            
            reader.onload = function (e) {
                $('.preview').attr('src', e.target.result);
                var KB = format_float(e.total / 1024, 2);
                $('.size').text("檔案大小：" + KB + " KB");
            }
 
            reader.readAsDataURL(input.files[0]);
        }
    }
 
    $("body").on("change", ".upload", function (){
        preview(this);
    })
    
})
</script>

</html>