<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.foodinform.model.*"%>
<%@ page import="java.util.*"%>
<%
String feature = "F0009";
%>
<%@ include file="/back-end/backstage/authList.file" %>
<%@ include file="/back-end/backstage/navbar.file" %>

<!DOCTYPE html>
<%
  	FoodInformVO foodinformVO = (FoodInformVO) request.getAttribute("foodinformVO");
	String whichPage = (String) request.getAttribute("whichPage");
	// Food.java (Concroller) 存入req的 foodinformVO 物件 (包括幫忙取出的 foodinformVO , 也包括輸入資料錯誤時的 foodinformVO 物件)
%>
<%
	Map<String, String> fi_status = new LinkedHashMap<String, String>();
	fi_status.put("1", "上架");
	fi_status.put("2", "下架");
	pageContext.setAttribute("fi_status", fi_status);
%>

<html>
<head>
<meta charset="UTF-8">
<title>食訊資料修改 - update_foodinform_input.jsp</title>

<link rel="stylesheet" href="<%=request.getContextPath() %>/js/vendors/bootstrap/css/bootstrap.min.css">
<script src="<%=request.getContextPath() %>/js/vendors/jquery/jquery-3.5.0.min.js"></script>
<script src="<%=request.getContextPath() %>/js/ckeditor/ckeditor.js"></script>
<style>
  	.table tbody td {
    	border: 0px solid #FFFFFF;
  	}
  /* 表格內容垂直置中 */
	.table th, .table td{
		vertical-align: middle;
	}  
</style>

</head>
<body style="background-color:#ECF5FF">

<div class="container-md">
	<div class="row justify-content-center">
		<div class="col-md-4" style="text-align: center;">	
			<h4>食訊修改<a href="<%=request.getContextPath() %>/back-end/food_inform/listAllFoodInform.jsp?whichPage=<%= whichPage%>"><img src="<%=request.getContextPath()%>/js/back-end/images/p5.png" style="height:70px;"></a>
			<input type="button" class="btn btn-dark" onclick="location.href='<%=request.getContextPath() %>/back-end/food_inform/listAllFoodInform.jsp?whichPage=<%= whichPage%>'" value="返回"/>			
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
		<FORM METHOD="post" ACTION="<%=request.getContextPath() %>/food_inform.do" name="form1" enctype="multipart/form-data">
		<table class="table col-md-12">
			<tr>
				<td>食訊編號:</td>
				<td><%=foodinformVO.getFi_no()%></td>
			</tr>
			<tr>
				<td>食訊標題:</td>
				<td><input type="TEXT" name="fi_title" id="fi_title" class="form-control" placeholder="請填寫食訊標題" aria-label="Fi title" aria-describedby="basic-addon1" 
						 value="<%=foodinformVO.getFi_title()%>" /></td>
			</tr>
			<tr>
				<td>食訊作者:</td>
				<td><input type="TEXT" name="fi_author" id="fi_author" class="form-control" placeholder="請填寫食訊標題" aria-label="Fi title" aria-describedby="basic-addon1" 
						 value="<%=foodinformVO.getFi_author()%>" /></td>
			</tr>
			<tr>
				<td>食訊日期:</td>
				<td><fmt:formatDate value="${foodinformVO.fi_date}" pattern="yyyy-MM-dd HH:mm"/></td>
			</tr>
			<tr>
				<td>食訊狀態:</td>
				<td>
					<select size="1" name="fi_status">
						<c:forEach var="status" items="${fi_status}">
							<option value="${status.key}"  ${(status.key == foodinformVO.fi_status) ? 'selected':'' } >${status.value}
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td>產品圖片:</td>
				<td><img src="<%=request.getContextPath() %>/PhotoReader?action=food_inform&pk_no=${foodinformVO.fi_no}" style="max-width: 100px; max-height: 100px;" class="preview img-fluid">
				<input type="FILE" name="fi_cover" size="45" class="upload btn"/><p class="size"></p></td>
			</tr>
				<tr>
				<td>食訊內容:</td>
			</tr>
		</table>
			<textarea name="fi_content"><%= foodinformVO.getFi_content() %></textarea>
				<script>
					CKEDITOR.replace('fi_content', {
			            language : 'zh-tw',				//設定語言
			            height: 400,					
			            width: 750,
			            uiColor : '#CCCCFF',
			            resize_enabled : false,			//避免使用者拖拉外框
					});
				</script>
		<br>
		<input type="hidden" name="action" value="update">
		<input type="hidden" name="fi_no" value="<%=foodinformVO.getFi_no()%>">
		<input type="hidden" name="fi_date" value="<fmt:formatDate value="${foodinformVO.fi_date}" pattern="yyyy-MM-dd HH:mm:ss"/>">
		<input type="hidden" name="whichPage"  value="<%=whichPage%>">
		<input type="submit" value="送出修改" class="col-md-2 offset-10 btn btn-secondary"></FORM>
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