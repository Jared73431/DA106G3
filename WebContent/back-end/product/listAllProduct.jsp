<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.product.model.*"%>
<%@ page import="com.util.controller.*"%>

<%
String feature = "F0004";
%>
<%@ include file="/back-end/backstage/authList.file" %>
<%@ include file="/back-end/backstage/navbar.file" %>

<%
    ProductService productSvc = new ProductService();
    List<ProductVO> list = productSvc.getAll();
    pageContext.setAttribute("list",list);
%>
<%
	Map<String, String> pro_status = new LinkedHashMap<String, String>();
	pro_status.put("1", "上架");
	pro_status.put("2", "下架");
	pageContext.setAttribute("pro_status", pro_status);
%>
<jsp:useBean id="productcateSvc" scope="page" class="com.productcate.model.ProductCateService" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>產品管理 - listAllProduct.jsp</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/js/vendors/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath() %>/js/bootstrap4-toggle/bootstrap4-toggle.min.css">
<style>
	/* 產品編號 */
	tbody td:first-child {
		width:2em;
  		font: 14px italic;
	}
	/* 產品類別 */
	tbody td:nth-child(2), thead th:nth-child(2) {
		width:12em;
		height:6.2em;
		text-align:center;
 	}
	/* 產品名稱 */
	tbody td:nth-child(3){
		width:16em;
		font: 14px italic;
 	}
	/* 產品單價 */
	tbody td:nth-child(4), thead th:nth-child(4){
		width:8em;
		text-align:center;
 	}	
	/* 產品狀態 */
	tbody td:nth-child(5),thead th:nth-child(5) {
		text-align:center;
		width:6em;
 	}
	/* 產品操作 */
	tbody td:nth-child(6),thead th:nth-child(6) {
		text-align:center;
		width:10em;
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
			<h4>商品管理<a href="<%=request.getContextPath() %>/back-end/backstage/index.jsp"><img src="<%=request.getContextPath()%>/js/back-end/images/p4.png" style="height:70px;"></a>
			<input type="button" class="btn btn-dark" onclick="location.href='<%=request.getContextPath() %>/back-end/backstage/index.jsp'" value="管理首頁"/>			
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
				<th scope="col" class="text-nowrap">產品編號</th>
				<th scope="col" class="text-nowrap">產品類別</th>
				<th scope="col" class="text-nowrap">產品名稱</th>
				<th scope="col" class="text-nowrap">產品單價</th>
				<th scope="col" class="text-nowrap">產品狀態</th>
				<th scope="col" class="text-nowrap">產品操作</th>	
		    </tr>
		  </thead>
		  <tbody>
			<c:forEach var="productVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">		  
			    <tr>
			      <td>${productVO.pro_no}</td>
			      <td>
			      	<c:forEach var="productcateVO" items="${productcateSvc.all}">
						<c:if test="${productVO.prc_no==productcateVO.prc_no}">
	                    	【${productcateVO.prc_name}】
                		</c:if>
					</c:forEach></td>			      
			      <td>${productVO.pro_name}</td>
			      <td>${productVO.pro_price}</td>			      
			      <td>
						<input name="${productVO.pro_no}" type="checkbox" ${(productVO.pro_status == 1) ? 'checked':'' } 
						data-toggle="toggle" data-size="sm" data-on="上架" data-off="下架">
				  </td>
			      			      
			      <td>
			      	<FORM METHOD="post" ACTION="<%=request.getContextPath() %>/product.do" style="margin-bottom: 0px;">
				     	<span>
				     		<input type="submit" value="詳情">
				     	</span>
					     	<input type="hidden" name="pro_no"  value="${productVO.pro_no}">
					     	<input type="hidden" name="whichPage"  value="<%=whichPage%>">
					     	<input type="hidden" name="action" value="getOne_For_Display">
					</FORM>			      	
				  	<FORM METHOD="post" ACTION="<%=request.getContextPath() %>/product.do" style="margin-bottom: 0px;">
					    <span>						    
						    <input type="submit" value="修改">
						</span>    
						    <input type="hidden" name="pro_no"  value="${productVO.pro_no}">
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
    	var pro_status;
    	var pro_no;

    	$(':checkbox').on('change',function(){
			pro_no=$(this).attr("name");
			if($(this).is(':checked')){
				console.log("on");
				var pro_status = 1;
			}else{
				console.log("off");
				var pro_status = 2;
			}
			console.log(pro_status);
			$.ajax({
				url: '<%=request.getContextPath()%>/product.do',
				type: "POST",
				data: {
					action : 'AJAX_For_Status',
					pro_no : pro_no,
					pro_status : pro_status
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