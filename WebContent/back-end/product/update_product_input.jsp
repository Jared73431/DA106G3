<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.product.model.*"%>

<%
String feature = "F0004";
%>
<%@ include file="/back-end/backstage/authList.file" %>
<%@ include file="/back-end/backstage/navbar.file" %>

<!DOCTYPE html>
<%
  ProductVO productVO = (ProductVO) request.getAttribute("productVO");
  String whichPage = (String) request.getAttribute("whichPage");
  // Gym.java (Concroller) 存入req的 productVO 物件 (包括幫忙取出的 productVO , 也包括輸入資料錯誤時的 productVO 物件)
%>
<%
	Map<String, String> pro_status = new LinkedHashMap<String, String>();
	pro_status.put("1", "上架");
	pro_status.put("2", "下架");
	pageContext.setAttribute("pro_status", pro_status);
%>
<jsp:useBean id="productcateSvc" scope="page" class="com.productcate.model.ProductCateService" /> 

<html>
<head>
<meta charset="UTF-8">
<title>產品資料修改 - update_product_input.jsp</title>
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
	text-align: left;
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
			<h4>商品修改<a href="<%=request.getContextPath() %>/back-end/product/listAllProduct.jsp?whichPage=<%= whichPage%>"><img src="<%=request.getContextPath()%>/js/back-end/images/p5.png" style="height:70px;"></a>
			<input type="button" class="btn btn-dark" onclick="location.href='<%=request.getContextPath() %>/back-end/product/listAllProduct.jsp?whichPage=<%= whichPage%>'" value="返回"/>			
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
				<th>產品編號:<font color=red><b>*</b></font></th>
				<td><%=productVO.getPro_no()%></td>
			</tr>
			<tr>
				<th>產品類別:</th>
				<td><select name="prc_no" size="1">
					<c:forEach var="productcateVO" items="${productcateSvc.all}">
						<c:if test="${productVO.prc_no==productcateVO.prc_no}">
			               <option value="${productcateVO.prc_no}" selected>【${productcateVO.prc_name}】</option>
		                </c:if>
					</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<th>產品名稱:</th>
				<td><input type="TEXT" name="pro_name" size="45" value="<%=productVO.getPro_name()%>" /></td>
			</tr>
			<tr>
				<th>產品定價:</th>
				<td><input type="TEXT" name="pro_price" size="45" value="<%=productVO.getPro_price()%>" /></td>
			</tr>
			<tr>
				<th>產品狀態:</th>
				<td>
					<select size="1" name="pro_status">
						<c:forEach var="status" items="${pro_status}">
							<option value="${status.key}"  ${(status.key == productVO.pro_status) ? 'selected':'' } >${status.value}
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
		</table>
		<table>
			<tr>
				<th>產品描述:</th>
			</tr>
		</table>
		
			<textarea name="pro_dis"><%=productVO.getPro_dis()%></textarea>
				<script>
					CKEDITOR.replace('pro_dis', {
			            language : 'zh-tw',				//設定語言
			            height: 400,					
			            width: 750,
			            uiColor : '#CCCCFF',
			            resize_enabled : false,			//避免使用者拖拉外框
		  	        });
				</script>
		<table>
			<tr>
				<th>產品圖片:</th>
				<td><input type="FILE" name="pro_pic" class="upload" size="45"/> <!-- 注意class是upload -->
			</tr>
		</table>
		<div>
				<img class="preview" style="max-width: 150px; max-height: 150px;" src="<%=request.getContextPath() %>/PhotoReader?action=product&pk_no=${productVO.pro_no}">
				<div class="size"></div>
		</div>
		<br>
		<input type="hidden" name="action" value="update">
		<input type="hidden" name="pro_no" value="<%=productVO.getPro_no()%>">
		<input type="hidden" name="pro_price0" value="<%=productVO.getPro_price()%>">
		<input type="hidden" name="pro_dis0" value="<%=productVO.getPro_dis()%>">
		<input type="hidden" name="whichPage"  value="<%=whichPage%>">
		<input type="submit" value="送出修改"></FORM>
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