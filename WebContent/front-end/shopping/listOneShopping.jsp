<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.product.model.*"%>
<%@ page import="com.productcate.model.*"%>
<%
  ProductVO productVO = (ProductVO) request.getAttribute("productVO"); 
  //ShoppingServlet.java(Concroller), 存入req的 productVO 物件

  // 取得ProductCate相關參數 讓版面變乾淨
  ProductCateService productcateSvc = new ProductCateService();
  
  // 取得目前價金
  String total_price = (String) session.getAttribute("shop_price");
  pageContext.setAttribute("total_price",total_price);
  
  String whichPage = (String) request.getAttribute("whichPage");
  pageContext.setAttribute("whichPage",whichPage);
%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>產品資料 - listOneShopping.jsp</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/js/vendors/bootstrap/css/bootstrap.min.css">
<link rel="icon" href="<%=request.getContextPath()%>/picture/logo.png">
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
		<div class="row justify-content-center article" style="margin:10px auto 10px;">
			<div class="col-md-10">
				<h3><%=productVO.getPro_name()%></h3>
				<%=productcateSvc.getOneProductCate(productVO.getPrc_no()).getPrc_name()%>
				<span class="fas fa-shopping-cart"></span>
				<span class="dollar-sign"></span>
				<span class="total_amount">
					<c:if test="${total_price > 0}">
						<a href="<%=request.getContextPath()%>/shopping.do?whichPage=<%=whichPage %>&action=getCart_Display">$${total_price}</a>
					</c:if>
				</span>				
			</div>
		</div>
		<div class="row justify-content-center article" style="margin:10px auto 10px;">
			<!-- 產品圖片 -->
			<div class="col-md-5 img">
				<img src="<%=request.getContextPath() %>/PhotoReader?action=product&pk_no=${productVO.pro_no}">			

				<div class="row article col-md-12" style="height:30px; margin:10px auto;">
						<!-- 產品單價 -->
					<div class="row article" style="height:30px; margin:10px auto;">
						<div class="col-md-6 offset-6" style="margin-top:10px;">
							<i>售價: $<%=productVO.getPro_price()%></i>
						</div>
						<div class="col-md-6 offset-6" style="margin-top:10px;">
							<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/shopping.do" style="margin-bottom: 0px;">
								數量：<select size="1" name="quantity">
									<c:forEach var="count" begin="1" end="10" >
				  						<option>${count}</option>
									</c:forEach>
								</select><br/><br/>
							     <input type="button" class="btn btn-primary" name="shopping" value="放入購物車">
								 <input type="hidden" name="pro_no"  value="${productVO.pro_no}">
							     <input type="hidden" name="prc_no"  value="${productVO.prc_no}">
							     <input type="hidden" name="pro_name"  value="${productVO.pro_name}">
							     <input type="hidden" name="pro_price"  value="${productVO.pro_price}">			     			     
							</FORM>			
						</div>					
					</div>
				</div>					
			</div>		
				<!-- 產品描述 -->		
			<div class="row col-md-7" style="margin:10px auto;">
				<div>
					<%=productVO.getPro_dis()%>			
				</div>								
			</div>
		</div>
	</div>
</body>
</html>