<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.product.model.*"%>

<%
String feature = "F0004";
%>
<%@ include file="/back-end/backstage/authList.file" %>
<%@ include file="/back-end/backstage/navbar.file" %>

<%
  ProductVO productVO = (ProductVO) request.getAttribute("productVO"); 
  //ProductServlet.java(Concroller), 存入req的 productVO 物件
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
<title>產品詳情 - listOneProduct.jsp</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/js/vendors/bootstrap/css/bootstrap.min.css">

<style>
 .img{
	height: 430px;
 	box-sizing: border-box;
	text-align:center;
}

 .article .img img{
 	max-height: 300px;
  	max-width: 100%;
}
 .shoptop{
 	color: #ffffff;
 	background-color: #E35240;
	border-color: #E35240;
 }

</style>

</head>
<body bgcolor='white'>

<div class="container-md">
	<div class="row justify-content-center" style="margin:10px auto;">
		<div class="col-md-4" style="text-align: center;">	
			<h4>商品詳情<a href="<%=request.getContextPath() %>/back-end/product/listAllProduct.jsp"><img src="<%=request.getContextPath()%>/js/back-end/images/p4.png" style="height:70px;"></a>
			<input type="button" class="btn btn-dark" onclick="location.href='<%=request.getContextPath() %>/back-end/product/listAllProduct.jsp'" value="商品列表"/>			
			</h4>
		</div>
	</div>
</div>

	<div class="container-md">
		<div class="row justify-content-center article" style="margin:10px auto 20px;">
			<div class="col-md-10">
				<hr>
				<h4>產品名稱:  <%=productVO.getPro_name()%></h4>
			</div>
		</div>
		<div class="row justify-content-center article" style="margin:10px auto 10px;">
			<!-- 產品圖片 -->
			<div class="col-md-5 img">
				<img src="<%=request.getContextPath() %>/PhotoReader?action=product&pk_no=${productVO.pro_no}">			

				<div class="row article" style="height:30px; margin:10px auto;">
						<!-- 產品單價 -->
					<div class="row" style="text-align:left; height:30px; margin:10px auto;">
						<div class="col-md-10 offset-2" style="margin-top:10px;">
							<h5>產品編號: <%=productVO.getPro_no()%></h5>
						</div>
						<div class="col-md-10 offset-2" style="margin-top:10px;">
							<b>產品類別: <%=productcateSvc.getOneProductCate(productVO.getPrc_no()).getPrc_name()%></b>
						</div>						
						<div class="col-md-10 offset-2" style="margin-top:10px;">
							<b>產品單價:   $<%=productVO.getPro_price()%></b>
						</div>
						<div class="col-md-10 offset-2" style="margin-top:10px;">
							<c:forEach var="status" items="${pro_status}">
								<c:if test="${(status.key == productVO.pro_status)}">
									<b>產品狀態: ${status.value}</b>
								</c:if>
							</c:forEach>
						</div>						
					</div>
				</div>					
			</div>		
				<!-- 產品描述 -->		
			<div class="row col-md-6" style="margin:10px auto;">
				<div>
					<%=productVO.getPro_dis()%>			
				</div>								
			</div>
		</div>
	</div>

</body>
</html>