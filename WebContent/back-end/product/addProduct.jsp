<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.product.model.*"%>
<%@ page import="java.util.*"%>

<%
String feature = "F0004";
%>
<%@ include file="/back-end/backstage/authList.file" %>
<%@ include file="/back-end/backstage/navbar.file" %>

<%
  ProductVO productVO = (ProductVO) request.getAttribute("productVO");
  
%>
<jsp:useBean id="productcateSvc" scope="page" class="com.productcate.model.ProductCateService" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>產品資料新增 - addProduct.jsp</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/js/vendors/bootstrap/css/bootstrap.min.css">
<script src="<%=request.getContextPath() %>/js/ckeditor/ckeditor.js"></script>
<script src="<%=request.getContextPath() %>/js/vendors/jquery/jquery-3.5.0.min.js"></script>

<style>
  table#table-1 {
	background-color: #CCCCFF;
    border: 2px solid black;
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

<style>
  table {
	width: 750px;
	background-color: white;
	margin-top: 1px;
	margin-bottom: 1px;
  }
  table, th, td {
    border: 0px solid #CCCCFF;
  }
  th, td {
    padding: 1px;
  }
</style>

</head>
<body bgcolor='white'>

<div class="container-md">
	<div class="row justify-content-center">
		<div class="col-md-4" style="text-align: center;">	
			<h4>商品修改<a href="<%=request.getContextPath() %>/back-end/product/listAllProduct.jsp"><img src="<%=request.getContextPath()%>/js/back-end/images/p5.png" style="height:70px;"></a>
			<input type="button" class="btn btn-dark" onclick="location.href='<%=request.getContextPath() %>/back-end/product/listAllProduct.jsp'" value="返回"/>			
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

		<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/product.do" name="form1" enctype="multipart/form-data">
		<table>
			<tr>
				<th>產品類別:</th>
				<td>
					<select name="prc_no" size="1">
						<option value="" selected>【請選擇】</option>
						<c:forEach var="productcateVO" items="${productcateSvc.all}">
			            	<option value="${productcateVO.prc_no}">【${productcateVO.prc_name}】</option>>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<th>產品名稱:</th>
				<td><input type="TEXT" name="pro_name" size="45" 
					 value="<%= (productVO==null)? "早安城之美" : productVO.getPro_name()%>" /></td>
			</tr>
			<tr>
				<th>產品定價:</th>
				<td><input type="TEXT" name="pro_price" size="45" 
					 value="<%= (productVO==null)? "1000" : productVO.getPro_price()%>" /></td>
			</tr>
			<tr>
				<th>產品描述:</th>
			</tr>
		</table>
			<textarea name="pro_dis"></textarea>
				<script>
					CKEDITOR.replace('pro_dis', {
			            language : 'zh-tw',				//設定語言
			            height: 400,					
			            width: 750,
			            uiColor : '#CCCCFF',
			            resize_enabled : false,			//避免使用者拖拉外框
		  	        });
				</script>
		<br>
		<table>
			<tr>
				<th>產品圖片:</th>
				<td><input type="FILE" name="pro_pic" class="upload" size="45"/></td>
			</tr>	
		</table>
		<div>
				<img class="preview" style="max-width: 150px; max-height: 150px;">
				<div class="size"></div>
		</div>
		<br>
		<input type="hidden" name="action" value="insert">
		<input type="submit" value="新增產品">
		<input type="button" onclick="location.href='select_page.jsp';" value="取消">
		</FORM>
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